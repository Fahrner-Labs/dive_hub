// MCP tool for the Tools page's gas numbers and bottom time (ADR 0032, ADR 0035). The same answer as the routes
// (planning/gas-numbers.ts, planning/bottom-time-service.ts), with the assumptions and the notice in every result: never a bare number.
import { Type } from 'typebox';
import { BOTTOM_TIME_RULES, planBottomTime } from '../planning/bottom-time-service.js';
import { gasNumbers } from '../planning/gas-numbers.js';
import { PLANNING_WATERS } from '../planning/gas.js';
import { diverLoadings } from '../planning/loadings-service.js';
import { PlanningError } from '../planning/planning-error.js';
import { Id, Nullable, ToolError, defineTool } from './tool.js';

/** Fixed text in every answer (ADR 0032: always visible; the limits are the Diver's own input, not advice). */
export const NOTICE = 'An estimate from the inputs and settings shown, not advice: limits computed from the Diver\'s own input and settings. '
  + 'The dive computer and the Diver\'s training govern, and no model guarantees safety. Say so when passing these numbers on.';

/** What the LLM is told to do about each refused plan. */
const HINT: Record<PlanningError['code'], string> = {
  invalid_input: 'Check the inputs: oxygen and helium together over 100 %, cylinder_volume_l without cylinder_pressure_bar or the other way round, '
    + 'or gas_rule reserve or ascent_pressure without rule_bar.',
  gas_not_breathable: 'A mix with under 16 % oxygen can\'t be breathed at the surface. Give o2 of 16 or more.',
  diver_not_found: 'No Diver of the user has this diver_id. Call divers_list for the Divers whose Dives this access can read.',
  site_not_found: 'No dive site has this id.',
};

/** Runs a planning calculation, turning its PlanningError into a refusal the LLM can act on. */
export async function planning<T>(run: () => T | Promise<T>): Promise<T> {
  try {
    return await run();
  } catch (error) {
    if (error instanceof PlanningError) throw new ToolError(error.code, HINT[error.code]);
    throw error;
  }
}

const Input = Type.Object({
  o2: Type.Number({ minimum: 1, maximum: 100, description: 'Oxygen in the mix, percent (air is 21)' }),
  he: Type.Optional(Type.Number({ minimum: 0, maximum: 100, description: 'Helium, percent. Default 0. Bottom time is answered for air and nitrox only' })),
  depth: Type.Optional(Type.Number({ minimum: 1, maximum: 300, description: 'Planned depth, metres. Without it only the gas numbers are answered' })),
  water: Type.Optional(Type.Enum([...PLANNING_WATERS], { description: 'salt (default), fresh or en13319' })),
  altitude_m: Type.Optional(Type.Number({ minimum: 0, maximum: 5000, description: 'Altitude of the water surface; default sea level' })),
  ppo2_working: Type.Optional(Type.Number({ minimum: 1, maximum: 2, description: 'Default 1.4' })),
  ppo2_contingency: Type.Optional(Type.Number({ minimum: 1, maximum: 2, description: 'Default 1.6' })),
  oxygen_narcotic: Type.Optional(Type.Boolean({ description: 'Count oxygen as narcotic for END. Default true' })),
  cylinder_volume_l: Type.Optional(Type.Number({ minimum: 1, maximum: 60, description: 'With cylinder_pressure_bar: adds the gas limit to the bottom time' })),
  cylinder_pressure_bar: Type.Optional(Type.Number({ minimum: 1, maximum: 350 })),
  sac_l_per_min: Type.Optional(Type.Number({ minimum: 1, maximum: 100, description: 'Gas use at the surface. Default: the median of the Diver\'s recent dives, else 20' })),
  gas_rule: Type.Optional(Type.Enum([...BOTTOM_TIME_RULES], { description: 'Where the gas limit ends. Default rock_bottom: ascend at rock bottom, never below 50 bar. reserve: finish with rule_bar left. ascent_pressure: start the ascent at rule_bar' })),
  rule_bar: Type.Optional(Type.Number({ minimum: 0, maximum: 350, description: 'Bar for gas_rule reserve or ascent_pressure' })),
  gf_high: Type.Optional(Type.Number({ minimum: 20, maximum: 100, description: 'Gradient factor high. Default: from the Diver\'s latest Recording that has one, else 85' })),
  diver_id: Type.Optional(Id('Start from this Diver\'s computer GF, SAC and last Dive (from divers_list)')),
}, { additionalProperties: false });

const Used = (value: ReturnType<typeof Type.Number> | ReturnType<typeof Type.String> | ReturnType<typeof Type.Object>) => Type.Object({
  value,
  source: Type.Enum(['request', 'recording', 'recordings', 'site', 'default'], { description: 'recording: the Diver\'s latest Recording with a GF; recordings: the median SAC of their Recordings' }),
});

export const planGasAndBottomTime = defineTool({
  name: 'planning_gas_and_bottom_time',
  title: 'Gas numbers and bottom time for a planned dive',
  description: `Computes limits for a planned dive from the inputs and the Diver's own settings: MOD of a gas mix at the working and the contingency ppO2, and for a depth the ppO2, best mix, EAD and END. With a depth, air or nitrox 21-40 %, no more than 40 m and within the MOD, also a bottom time: the shortest of the no-decompression limit (Bühlmann ZHL-16C with the gradient factor; clean tissues unless the managed Diver dived within 48 hours, whose residual N₂ then seeds it), the oxygen limit and the gas down to the ascent pressure of the gas rule (when a cylinder is given; by default rock bottom, never below 50 bar), and which limit binds.

Returns { gas, hypoxic, ppo2_limits, mod_working_m, mod_contingency_m, at_depth, conditions, oxygen_narcotic, bottom_time, notice }. bottom_time is null without a depth. Its status is "ok" (minutes, binding, limits, otu, assumptions with where each value came from) or "outside_recreational_limits" (reason: depth, mod or gas; no number). residual_n2 says when the limit was seeded from the Diver's residual nitrogen, how many Dives it rests on and when the last ended; recent_dive_ended_at stays as the 48-hour warning.

Always pass on the notice and the assumptions with the numbers: this is an estimate, not advice, and the dive computer and training govern. Dive Hub plans no decompression dives.`,
  scope: 'logbook:read',
  input: Input,
  output: () => Type.Object({
    gas: Type.Object({ o2: Type.Number(), he: Type.Number() }),
    hypoxic: Type.Boolean({ description: 'Under 18 % oxygen at the surface' }),
    ppo2_limits: Type.Object({ working: Type.Number(), contingency: Type.Number() }),
    mod_working_m: Type.Number({ description: 'Rounded down to 0.1 m' }),
    mod_contingency_m: Type.Number(),
    at_depth: Nullable(Type.Object({
      depth_m: Type.Number(), ppo2: Type.Number(), best_mix_percent: Type.Integer(), ead_m: Type.Number(), end_m: Type.Number(),
    })),
    conditions: Type.Object({ water: Type.Enum([...PLANNING_WATERS]), altitude_m: Type.Number() }),
    oxygen_narcotic: Type.Boolean(),
    bottom_time: Nullable(Type.Object({
      status: Type.Enum(['ok', 'outside_recreational_limits']),
      reason: Type.Optional(Type.Enum(['depth', 'mod', 'gas'], { description: 'depth: deeper than 40 m; mod: beyond the MOD at the contingency ppO2; gas: not air or nitrox 21-40 %' })),
      mod_m: Type.Optional(Nullable(Type.Number())),
      minutes: Type.Optional(Type.Integer({ description: 'Whole minutes from leaving the surface to leaving the depth' })),
      binding: Type.Optional(Type.Array(Type.Enum(['ndl', 'oxygen', 'gas']), { description: 'Several when the limits bind together' })),
      limits: Type.Optional(Type.Object({
        ndl: Nullable(Type.Number()), oxygen: Nullable(Type.Number()), gas: Nullable(Type.Number({ description: 'Null without a cylinder' })),
      }, { description: 'Minutes; null where the depth does not limit' })),
      otu: Type.Optional(Type.Number()),
      ppo2_at_depth: Type.Optional(Type.Number()),
      ppo2_above_working: Type.Optional(Type.Boolean()),
      ascent_pressure_bar: Type.Optional(Nullable(Type.Number({ description: 'The pressure the gas limit ends at; null without a cylinder' }))),
      assumptions: Type.Object({
        gf_high: Used(Type.Number()),
        sac_l_per_min: Type.Intersect([Used(Type.Number()), Type.Object({ based_on_dives: Type.Optional(Type.Integer({ description: 'How many dives the planning SAC rests on' })) })]),
        water: Used(Type.String()), altitude_m: Used(Type.Number()),
        gas_rule: Used(Type.Object({ kind: Type.Enum([...BOTTOM_TIME_RULES]), bar: Nullable(Type.Number()) })),
      }),
      recent_dive_ended_at: Nullable(Type.String({ description: 'The Diver dived within 48 hours: the real limit is shorter than shown' })),
      residual_n2: Nullable(Type.Object({
        worst_compartment_percent: Type.Number({ description: 'The Diver\'s worst compartment at now, at the surface and GF high; the NDL starts from these tissues' }),
        based_on_dives: Type.Integer({ description: 'How many logged Dives the tissue state rests on' }),
        last_dive_ended_at: Nullable(Type.String({ description: 'When the latest Dive ended' })),
      }, { description: 'The residual nitrogen the no-decompression limit was seeded with; null without a Dive in the last 48 hours or without a profile' })),
    })),
    notice: Type.String(),
  }),
  async run(ctx, args) {
    const g = await planning(() => gasNumbers({
      o2: args.o2, he: args.he, depth: args.depth, water: args.water, altitude: args.altitude_m,
      ppo2Working: args.ppo2_working, ppo2Contingency: args.ppo2_contingency, oxygenNarcotic: args.oxygen_narcotic,
    }));
    let bottomTime = null;
    const { depth } = args;
    if (depth !== undefined) {
      const v = await planning(() => planBottomTime(ctx.tx, ctx.diverIds, {
        depth, o2: args.o2, he: args.he, volume: args.cylinder_volume_l, pressure: args.cylinder_pressure_bar, sac: args.sac_l_per_min,
        rule: args.gas_rule, bar: args.rule_bar, gfHigh: args.gf_high, water: args.water, altitude: args.altitude_m,
        ppo2Working: args.ppo2_working, ppo2Contingency: args.ppo2_contingency, diverId: args.diver_id,
      }));
      const assumptions = {
        gf_high: v.used.gfHigh,
        sac_l_per_min: { value: v.used.sacLpm.value, source: v.used.sacLpm.source, ...(v.used.sacLpm.basedOn !== undefined && { based_on_dives: v.used.sacLpm.basedOn }) },
        water: v.used.water, altitude_m: v.used.altitudeM, gas_rule: v.used.gasRule,
      };
      const residualN2 = v.residualN2 && {
        worst_compartment_percent: v.residualN2.worstCompartmentPercent,
        based_on_dives: v.residualN2.basedOnDives,
        last_dive_ended_at: v.residualN2.lastDiveEndedAt,
      };
      bottomTime = v.status === 'outside_limits'
        ? { status: 'outside_recreational_limits' as const, reason: v.reason, mod_m: v.modM, assumptions, recent_dive_ended_at: v.recentDiveEndedAt, residual_n2: residualN2 }
        : {
          status: 'ok' as const, minutes: v.minutes, binding: v.binding, limits: v.limits, otu: v.otu, ppo2_at_depth: v.ppO2AtDepth,
          ppo2_above_working: v.ppO2AboveWorking, ascent_pressure_bar: v.ascentPressureBar, assumptions,
          recent_dive_ended_at: v.recentDiveEndedAt, residual_n2: residualN2,
        };
    }
    const output = {
      gas: g.gas, hypoxic: g.hypoxic, ppo2_limits: g.ppO2Limits, mod_working_m: g.modWorkingM, mod_contingency_m: g.modContingencyM,
      at_depth: g.atDepth && {
        depth_m: g.atDepth.depthM, ppo2: g.atDepth.ppO2, best_mix_percent: g.atDepth.bestMixPercent, ead_m: g.atDepth.eadM, end_m: g.atDepth.endM,
      },
      conditions: { water: g.conditions.water, altitude_m: g.conditions.altitudeM }, oxygen_narcotic: g.oxygenNarcotic,
      bottom_time: bottomTime, notice: NOTICE,
    };
    return { output, rows: 0 };
  },
});

const LoadingsInput = Type.Object({
  diver_id: Id('The Diver whose logged dives to read (from divers_list)'),
}, { additionalProperties: false });

export const getDiverLoadings = defineTool({
  name: 'planning_diver_loadings',
  title: "Current loadings from a Diver's logged dives",
  description: 'Estimates what the Diver\'s own logged dives leave in them now: the residual N₂ of the Dives that ended within 72 h replayed through the Bühlmann ZHL-16C compartments (null after 48 h without a Dive), the latest Recording\'s CNS decayed with the 90-minute half-life, the OTU of the Dives that ended within 24 h, and DAN\'s no-fly time. '
    + 'Returns { computed_at, basis, n2, cns, otu, no_fly }. basis always says how many Dives were used, how many were skipped for having no profile, and when the latest Dive ended: the estimate rests on the Diver\'s own logbook, so an unlogged dive is missing from it. n2 is null when there is nothing usable to replay; cns and otu are null when no Recording carries them, while a zero is a real zero. '
    + 'This is an estimate from the Diver\'s logged dives, not advice: pass the basis and the null-versus-zero distinction on with every number, and never call a value safe or unsafe. The dive computer and the Diver\'s training govern.',
  scope: 'logbook:read',
  input: LoadingsInput,
  output: () => Type.Object({
    computed_at: Type.String({ description: 'When the estimate was computed' }),
    basis: Type.Object({
      window_hours: Type.Integer({ description: 'How far back the N₂ replay looks' }),
      dives_used: Type.Integer({ description: 'Dives in the window whose profiles were replayed' }),
      dives_skipped_no_profile: Type.Integer({ description: 'Dives in the window without a depth profile' }),
      last_dive_ended_at: Nullable(Type.String({ description: 'When the latest Dive ended; null without one' })),
    }),
    n2: Nullable(Type.Object({
      worst_compartment_percent: Type.Number({ description: 'At the surface and GF high; 100 % is the model\'s limit, not a verdict' }),
      gf_high: Type.Number(),
    })),
    cns: Nullable(Type.Object({
      percent_now: Type.Number({ description: 'Decayed to now with the 90-minute surface half-life' }),
      percent_at_last_dive_end: Type.Number({ description: 'The computer\'s own value at the end of that Dive' }),
      half_life_minutes: Type.Integer(),
    })),
    otu: Nullable(Type.Object({ last_24_hours: Type.Number({ description: 'The OTU of the Dives that ended within 24 h' }) })),
    no_fly: Nullable(Type.Object({
      hours: Type.Integer({ description: 'DAN\'s guideline: 12 single, 18 several dives or days, 24 after decompression' }),
      reason: Type.Enum(['single', 'several', 'decompression']),
      until: Type.String({ description: 'From when the guideline is met' }),
      source: Type.Object({ title: Type.String(), url: Type.String() }),
    }, { description: 'The no-fly time after the latest Dive; information beside the numbers, not advice' })),
  }),
  async run(ctx, args) {
    const loadings = await planning(() => diverLoadings(ctx.tx, ctx.diverIds, args.diver_id));
    const output = {
      computed_at: loadings.computedAt,
      basis: {
        window_hours: loadings.basis.windowHours,
        dives_used: loadings.basis.divesUsed,
        dives_skipped_no_profile: loadings.basis.divesSkippedNoProfile,
        last_dive_ended_at: loadings.basis.lastDiveEndedAt,
      },
      n2: loadings.n2 && { worst_compartment_percent: loadings.n2.worstCompartmentPercent, gf_high: loadings.n2.gfHigh },
      cns: loadings.cns && {
        percent_now: loadings.cns.percentNow, percent_at_last_dive_end: loadings.cns.percentAtLastDiveEnd,
        half_life_minutes: loadings.cns.halfLifeMinutes,
      },
      otu: loadings.otu && { last_24_hours: loadings.otu.last24Hours },
      no_fly: loadings.noFly,
    };
    return { output, rows: loadings.basis.divesUsed };
  },
});
