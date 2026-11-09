/** The places of the main navigation, and Settings at the sidebar's foot. */
export type NavSection = 'logbook' | 'divers' | 'equipment' | 'sites' | 'centres' | 'tools' | 'settings';

/**
 * The place an address ("/sites/<id>?…") belongs to, by its first path segment. The account and admin pages are part of
 * Settings (UI overhaul, "The shell").
 */
export function navSection(route: string): NavSection | null {
  const first = route.split('?')[0]!.split('/')[1] ?? '';
  if (first === '' || first === 'dives' || first === 'review') return 'logbook';
  if (first === 'settings' || first === 'account' || first === 'admin') return 'settings';
  return first === 'divers' || first === 'equipment' || first === 'sites' || first === 'centres' || first === 'tools' ? first : null;
}
