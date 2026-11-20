import { Component, type ReactNode } from 'react';
import { captureError } from '../lib/diagnostics.ts';

/**
 * Keeps a failing part of a page (e.g. a chart) from blanking the whole page: shows `fallback`
 * instead and logs the error. React still needs a class component for this. The failure also joins the local
 * diagnostics buffer, so the User can report it (FAH-292, ADR 0080); capture never throws.
 */
export class ErrorBoundary extends Component<{ fallback: ReactNode; children: ReactNode }, { failed: boolean }> {
  override state = { failed: false };

  static getDerivedStateFromError() {
    return { failed: true };
  }

  override componentDidCatch(error: unknown) {
    console.error(error);
    captureError(error);
  }

  override render() {
    return this.state.failed ? this.props.fallback : this.props.children;
  }
}
