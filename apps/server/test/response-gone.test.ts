// A response that a client disconnected must still let the work after it run: the setup restore exits the app.
import { EventEmitter } from 'node:events';
import { describe, expect, it } from 'vitest';
import { whenResponseGone } from '../src/http/response-gone.js';

describe('when a response is gone', () => {
  it('fires when the answer finishes', () => {
    const response = new EventEmitter();
    let calls = 0;
    whenResponseGone(response, () => { calls += 1; });
    response.emit('finish');
    expect(calls).toBe(1);
  });

  it('fires when the connection closes without a finish', () => {
    const response = new EventEmitter();
    let calls = 0;
    whenResponseGone(response, () => { calls += 1; });
    response.emit('close');
    expect(calls).toBe(1);
  });

  it('fires on an error, and only once for whichever event comes first', () => {
    const response = new EventEmitter();
    let calls = 0;
    whenResponseGone(response, () => { calls += 1; });
    response.emit('error', new Error('the socket went away'));
    response.emit('finish');
    response.emit('close');
    expect(calls).toBe(1);
  });

  it('fires at once when the client disconnected before the listeners were attached', () => {
    // The reviewer's reproduction: the socket died during the restore, so Node's `close` is already past.
    const response = Object.assign(new EventEmitter(), { destroyed: true, writableFinished: false });
    let calls = 0;
    whenResponseGone(response, () => { calls += 1; });
    expect(calls).toBe(1);
    // A late event must not run the callback a second time.
    response.emit('close');
    expect(calls).toBe(1);
  });

  it('fires at once when the answer finished before the listeners were attached', () => {
    const response = Object.assign(new EventEmitter(), { destroyed: false, writableFinished: true });
    let calls = 0;
    whenResponseGone(response, () => { calls += 1; });
    expect(calls).toBe(1);
  });
});
