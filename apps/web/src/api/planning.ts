import { keepPreviousData, queryOptions } from '@tanstack/react-query';
import { BOTTOM_TIME_RULES, type ToolsParams } from '../lib/tools.ts';
import { api, request, type Ok } from './request.ts';

/** The Tools page (ADR 0032): computed on request; the inputs come from the page's address. */
export type ToolsGas = Ok<'/api/tools/gas'>;
export type ToolsBottomTime = Ok<'/api/tools/bottom-time'>;
type ToolsInput = ToolsParams;
/** A gas plan (ADR 0033). */
export type ToolsPlan = Ok<'/api/tools/gas-plan', 'post'>;
export type ToolsPlanDiver = Extract<ToolsPlan, { status: 'ok' }>['divers'][number];
/** The lead estimate (ADR 0031): computed on request for a Diver the User keeps, in the unit the User counts in. */
export type LeadEstimate = Ok<'/api/divers/{id}/lead-estimate'>;

/** Leaves out what is undefined (the address holds only what was typed); the type claims every key, which the query's optional keys accept. */
const defined = <T extends object>(o: T) =>
  Object.fromEntries(Object.entries(o).filter(([, v]) => v !== undefined)) as { [K in keyof T]: Exclude<T[K], undefined> };

/** While the inputs are complete the last answer stays on screen until the next arrives, so the page doesn't blink; with incomplete inputs there is no answer. */
export const toolsGasQuery = (p: ToolsInput) => {
  const enabled = p.o2 !== undefined && p.o2 > 0;
  return queryOptions({
    queryKey: ['tools', 'gas', p] as const, queryFn: async () => request(api.GET('/api/tools/gas', {
    params: { query: defined({ o2: p.o2!, he: p.he, depth: p.depth, water: p.water, altitude: p.altitude, ppo2Working: p.pw, ppo2Contingency: p.pc, oxygenNarcotic: p.narcotic }) },
  })), enabled, retry: false,
    ...(enabled && { placeholderData: keepPreviousData }),
  });
};

/** A single bottom time knows three rules; the turn rules are for a plan with a return (ADR 0033). */
const bottomTimeRule = (p: ToolsInput) =>
  p.rule !== undefined && (BOTTOM_TIME_RULES as readonly string[]).includes(p.rule) ? { rule: p.rule as 'rock_bottom' | 'reserve' | 'ascent_pressure', bar: p.bar } : {};

export const toolsBottomTimeQuery = (p: ToolsInput, diverId: string | undefined, ready: boolean) => {
  const enabled = ready && p.o2 !== undefined && p.o2 > 0 && p.depth !== undefined && p.depth > 0;
  return queryOptions({
    queryKey: ['tools', 'bottom-time', p, diverId] as const, queryFn: async () => request(api.GET('/api/tools/bottom-time', {
    params: { query: defined({
      depth: p.depth!, o2: p.o2!, he: p.he, volume: p.volume, pressure: p.pressure, sac: p.sac, ...bottomTimeRule(p), gfHigh: p.gf,
      water: p.water, altitude: p.altitude, ppo2Working: p.pw, ppo2Contingency: p.pc, diverId,
    }) },
  })), enabled, retry: false,
    ...(enabled && { placeholderData: keepPreviousData }),
  });
};

export const toolsPlanQuery = (p: ToolsInput) => {
  const enabled = (p.levels?.length ?? 0) > 0 && (p.divers?.length ?? 0) > 0;
  return queryOptions({
    queryKey: ['tools', 'plan', p] as const, queryFn: async () => request(api.POST('/api/tools/gas-plan', {
      body: defined({
        // The plan's own mix (air when empty), never the one of "Your dive"; no helium: a plan is air or nitrox.
        o2: p.mix ?? 21, water: p.water, altitude: p.altitude, rule: p.rule, bar: p.bar, ppo2Contingency: p.pc, descentRate: p.dr, ascentRate: p.ar,
        levels: (p.levels ?? []).map((l) => ({ depth: l.depth, minutes: l.minutes })),
        divers: (p.divers ?? []).map((d) => defined({ name: d.name, volume: d.volume, pressure: d.pressure, sac: d.sac, diverId: d.diverId })),
      }),
    })), enabled, retry: false,
    ...(enabled && { placeholderData: keepPreviousData }),
  });
};

/** Needs a Diver, the water, the suit and Cylinders with a volume each; the suit's details may stay empty (the answer says so). */
export const leadEstimateQuery = (p: ToolsInput, diverId: string | undefined, unit: 'kg' | 'lb') => {
  const enabled = diverId !== undefined && p.leadWater !== undefined && p.suit !== undefined
    && (p.cylinders?.length ?? 0) > 0 && p.cylinders!.every((c) => c.volumeL > 0);
  return queryOptions({
    queryKey: ['tools', 'lead', p, diverId, unit] as const, queryFn: async () => request(api.GET('/api/divers/{id}/lead-estimate', {
    params: {
      path: { id: diverId! },
      query: defined({
        water: p.leadWater!, suit: p.suit!, suitThicknessMm: p.suit === 'wetsuit' || p.suit === 'semidry' ? p.thicknessMm : undefined,
        suitHood: p.suit && p.suit !== 'none' ? p.hood : undefined, suitUndergarment: p.suit === 'drysuit' ? p.undergarment : undefined,
        cylinders: JSON.stringify((p.cylinders ?? []).map((c) => defined({ volumeL: c.volumeL, material: c.material }))), unit,
      }),
    },
  })), enabled, retry: false,
    ...(enabled && { placeholderData: keepPreviousData }),
  });
};
