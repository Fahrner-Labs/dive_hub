import { describe, expect, it } from 'vitest';
import { emptyRedactionCounters, redactLogObject, redactValue } from '../src/support/redact.js';

describe('the support-bundle redactor', () => {
  it('censors values under denied key names at any depth, arrays included', () => {
    const counters = emptyRedactionCounters();
    const input = {
      Token: 'top',
      nested: { password: 'pw', deeper: { client_secret: 's' } },
      list: [{ apiKey: 'k' }, { Authorization: 'Bearer x' }, { cookie: 'c' }, { passPhrase: 'p' }, { credentials: { user: 'u' } }],
      safe: 'kept',
    };
    expect(redactLogObject(input, counters)).toEqual({
      Token: '‹redacted›',
      nested: { password: '‹redacted›', deeper: { client_secret: '‹redacted›' } },
      list: [{ apiKey: '‹redacted›' }, { Authorization: '‹redacted›' }, { cookie: '‹redacted›' }, { passPhrase: '‹redacted›' }, { credentials: '‹redacted›' }],
      safe: 'kept',
    });
    expect(counters).toEqual({ keysCensored: 8, emailsMasked: 0, ipsMasked: 0 });
  });

  it('masks e-mail addresses and IP addresses in free text', () => {
    const counters = emptyRedactionCounters();
    const messages = [
      'contact ops@example.com now',
      'from foo.bar+tag@example.co.uk',
      'address 192.168.1.10 and 8.8.8.8',
      'peer 2001:db8::1',
      'loopback ::1',
      'full 2001:0db8:85a3:0000:0000:8a2e:0370:7334',
    ];
    const redacted = messages.map((message) => redactValue(message, 'msg', counters));
    expect(redacted[0]).toBe('contact ‹email› now');
    expect(redacted[1]).toBe('from ‹email›');
    expect(redacted[2]).toBe('address ‹ip› and ‹ip›');
    expect(redacted[3]).toBe('peer ‹ip›');
    expect(redacted[4]).toBe('loopback ‹ip›');
    expect(redacted[5]).toBe('full ‹ip›');
    expect(counters).toEqual({ keysCensored: 0, emailsMasked: 2, ipsMasked: 5 });
  });

  it('masks the msg field itself without censoring the whole entry', () => {
    const counters = emptyRedactionCounters();
    const entry = redactLogObject({ msg: 'wrote to foo@example.com from 10.0.0.1', safe: 'kept' }, counters);
    expect(entry).toEqual({ msg: 'wrote to ‹email› from ‹ip›', safe: 'kept' });
    expect(counters).toEqual({ keysCensored: 0, emailsMasked: 1, ipsMasked: 1 });
  });

  it('leaves non-personal text and malformed addresses alone', () => {
    const counters = emptyRedactionCounters();
    expect(redactValue('user@localhost and 1.2.3 and 999.1.1.1', 'msg', counters)).toBe('user@localhost and 1.2.3 and 999.1.1.1');
    expect(counters).toEqual({ keysCensored: 0, emailsMasked: 0, ipsMasked: 0 });
  });

  it('is idempotent and does not count a second pass', () => {
    const input = {
      token: 'raw',
      msg: 'mail ops@example.com from 203.0.113.5',
      list: [{ ip: '198.51.100.7' }],
    };
    const firstCounters = emptyRedactionCounters();
    const first = redactLogObject(input, firstCounters);
    const secondCounters = emptyRedactionCounters();
    const second = redactLogObject(first, secondCounters);
    expect(second).toEqual(first);
    expect(secondCounters).toEqual({ keysCensored: 0, emailsMasked: 0, ipsMasked: 0 });
  });

  it('stays bounded on a 10 KB adversarial string', () => {
    const adversarial = [
      `${'a'.repeat(5_000)}@${'b'.repeat(5_000)}`,
      '1.'.repeat(5_000),
      ':'.repeat(5_000),
      `${'ab:'.repeat(2_500)}@`,
    ];
    const started = performance.now();
    for (const value of adversarial) {
      redactValue(value, 'msg', emptyRedactionCounters());
    }
    expect(performance.now() - started).toBeLessThan(1_000);
  });
});
