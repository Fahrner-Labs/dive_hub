// What an Equipment operation refuses with (ADR 0034); its own file so services and routes share it (ADR 0053).
export class EquipmentError extends Error {
  constructor(
    readonly code:
      | 'diver_not_found' | 'equipment_not_found' | 'equipment_changed' | 'equipment_in_use'
      | 'equipment_use_mismatch' | 'schedule_not_found' | 'schedule_interval_required'
      | 'record_not_found' | 'equipment_item_not_cylinder' | 'invalid_input',
  ) {
    super(code);
  }
}
