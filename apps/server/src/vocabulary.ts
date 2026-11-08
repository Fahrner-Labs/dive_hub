// Dive Hub's own words for values that dive computers record (ADR 0015). Every Source adapter maps
// its vocabulary onto these; the API publishes them as enums and clients translate them.
// Names follow UDDF where it has a term (ADR 0003).

export const WATER_TYPES = ['fresh', 'salt', 'brackish', 'en13319', 'custom'] as const;
export type WaterType = (typeof WATER_TYPES)[number];

/**
 * The water at a Dive site (ADR 0025). A Dive's water type is its site's; the device-only settings
 * (EN 13319, custom density) stay on the Recording as the computer's setting.
 */
export const SITE_WATER_TYPES = ['fresh', 'salt', 'brackish'] as const satisfies readonly WaterType[];
export type SiteWaterType = (typeof SITE_WATER_TYPES)[number];

/** Density in kg/m³ that a computer assumes for each setting (Garmin's FIT profile: salt 1025, EN 13319 1020). */
export const WATER_DENSITY: Partial<Record<WaterType, number>> = { fresh: 1000, salt: 1025, en13319: 1020 };

export const DIVE_MODES = ['open_circuit', 'ccr', 'scr', 'gauge', 'apnea'] as const;
export type DiveMode = (typeof DIVE_MODES)[number];

/** Decompression models are proper names; clients show them, they don't translate them. */
export const DECO_MODELS = ['buhlmann_zhl16c', 'suunto_fused_rgbm', 'suunto_fused2_rgbm'] as const;
export type DecoModel = (typeof DECO_MODELS)[number];

/** Whether a gas is breathed directly or is a rebreather's diluent. */
export const GAS_CIRCUITS = ['open_circuit', 'diluent'] as const;
export type GasCircuit = (typeof GAS_CIRCUITS)[number];

/**
 * What a dive computer itself noted during a dive (ADR 0036), shown beside Dive Hub's findings, never merged with
 * them. Alerts that say nothing about the dive (dismissed, battery, setpoint switches, the User's own time and depth
 * alarms) have no word here and are left out. Suunto's deep stops and its mandatory safety stop (after a fast ascent)
 * and a tank pod's low pressure have their own words (ADR 0037).
 */
export const COMPUTER_EVENTS = [
  'ascent_critical', 'safety_stop_started', 'safety_stop_broken', 'safety_stop_complete', 'approaching_ndl', 'ndl_reached',
  'approaching_first_stop', 'ceiling_broken', 'deco_stop_cleared', 'deco_complete', 'po2_warning', 'po2_high', 'po2_low',
  'cns_warning', 'cns_critical', 'otu_warning', 'otu_critical',
  'safety_stop_mandatory', 'deep_stop_started', 'deep_stop_broken', 'tank_pressure_low',
] as const;
export type ComputerEvent = (typeof COMPUTER_EVENTS)[number];

/** WGS84 degrees. */
export interface Position {
  latitude: number;
  longitude: number;
}

/** Sources a Site import fetches from, in the order a run asks them (ADR 0021). */
export const IMPORT_SOURCES = ['osm', 'wikidata', 'ssi'] as const;
export type ImportSource = (typeof IMPORT_SOURCES)[number];

/** A service a person has an account at, as an external Diver's accounts record it (ADR 0028). */
export const DIVER_SOURCES = ['ssi', 'padi'] as const;
export type DiverSource = (typeof DIVER_SOURCES)[number];

/** What kind of gear an Equipment item is (ADR 0034); an open list the User may extend with `other`. */
export const EQUIPMENT_CATEGORIES = [
  'regulator', 'bcd', 'wing', 'backplate', 'cylinder', 'drysuit', 'wetsuit', 'dive_computer', 'transmitter', 'light', 'camera', 'other',
] as const;
export type EquipmentCategory = (typeof EQUIPMENT_CATEGORIES)[number];

/** Whether an Equipment item is still in use; a used item is retired, lost or sold, never deleted (ADR 0034). */
export const EQUIPMENT_STATUSES = ['in_use', 'retired', 'lost', 'sold'] as const;
export type EquipmentStatus = (typeof EQUIPMENT_STATUSES)[number];

/** How an exception to an item's mode is recorded on a Dive (ADR 0034): put on it, or taken off it. */
export const EQUIPMENT_USES = ['put_on', 'taken_off'] as const;
export type EquipmentUse = (typeof EQUIPMENT_USES)[number];
