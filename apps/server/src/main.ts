import { fileURLToPath } from 'node:url';
import { runRestoreCli } from './backups/restore-cli.js';
import { loadConfig } from './config.js';
import { start } from './start.js';

const config = loadConfig();

// The operator's restore path (plan FAH-175) runs instead of the server, in a one-off container:
// `docker compose run --rm app node dist/main.js restore-backup <archive>`.
if (process.argv[2] === 'restore-backup') {
  // exitCode, not exit(): stdout and stderr must drain before the process ends.
  process.exitCode = await runRestoreCli(process.argv.slice(3), {
    dataDir: config.dataDir,
    databaseUrl: config.databaseUrl,
    // src/ (dev) and dist/ (image) both sit one level below the drizzle/ folder.
    migrationsFolder: fileURLToPath(new URL('../drizzle', import.meta.url)),
    encryptionKey: config.encryptionKey,
  });
} else {
  const server = await start(config);
  const shutdown = async () => {
    await server.stop();
    process.exit(0);
  };
  process.once('SIGINT', shutdown);
  process.once('SIGTERM', shutdown);
}
