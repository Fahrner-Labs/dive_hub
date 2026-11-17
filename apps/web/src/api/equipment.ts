import { queryOptions } from '@tanstack/react-query';
import { keys } from './keys.ts';
import { api, request, type Ok } from './request.ts';

/** An Equipment item with its schedules and their computed due (ADR 0034). */
export type EquipmentItemView = Ok<'/api/divers/{id}/equipment'>[number];
export type EquipmentScheduleView = EquipmentItemView['schedules'][number];
export type EquipmentScheduleDue = NonNullable<EquipmentScheduleView['due']>;
export type EquipmentDetailView = Ok<'/api/equipment/{id}'>;
export type EquipmentRecordView = EquipmentDetailView['records'][number];
export type EquipmentDueView = Ok<'/api/equipment/due'>[number];
export type EquipmentCategory = EquipmentItemView['category'];
export type EquipmentStatus = EquipmentItemView['equipmentStatus'];
/** A Dive's gear: what its items say without an explicit use, and its put-on/taken-off exceptions. */
export type DiveGearView = Ok<'/api/dives/{id}/equipment'>;
export type DiveGearItemView = DiveGearView['putOn'][number];

/** The cylinder catalogue (ADR 0031): a list in the server's code, the same until the next release. */
export const cylinderCatalogueQuery = () =>
  queryOptions({ queryKey: ['cylinder-catalogue'] as const, queryFn: async () => request(api.GET('/api/cylinder-catalogue')), staleTime: Infinity });
/** A Diver's Equipment items with their schedules and computed due (ADR 0034). */
export const equipmentOfDiverQuery = (diverId: string) => queryOptions({
  queryKey: keys.equipmentOfDiver(diverId),
  queryFn: async () => request(api.GET('/api/divers/{id}/equipment', { params: { path: { id: diverId } } })),
});
/** Every schedule due, due soon or overdue across the User's Divers: the navigation's count and the logbook's notice. */
export const equipmentDueQuery = () => queryOptions({
  queryKey: keys.equipmentDue,
  queryFn: async () => request(api.GET('/api/equipment/due')),
});
/** One item with its usage, records and the last Cylinder that named it. */
export const equipmentItemQuery = (id: string) => queryOptions({
  queryKey: keys.equipmentItem(id),
  queryFn: async () => request(api.GET('/api/equipment/{id}', { params: { path: { id } } })),
});
/** The Dive's gear: derived items and the put-on/taken-off exceptions (ADR 0034). */
export const diveGearQuery = (diveId: string) => queryOptions({
  queryKey: keys.diveGear(diveId),
  queryFn: async () => request(api.GET('/api/dives/{id}/equipment', { params: { path: { id: diveId } } })),
});
