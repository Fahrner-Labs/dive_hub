// The admin support bundle's own rules (FAH-291): the archive is built on demand and leaves as a file, its filename
// set by the server's Content-Disposition.

/** Downloading builds the archive on the server; the admin's session cookie authenticates it. */
export const SUPPORT_BUNDLE_DOWNLOAD_HREF = '/api/admin/support-bundle/download';
