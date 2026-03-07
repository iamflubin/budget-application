import { LogLevel } from 'angular-auth-oidc-client';

export const environment = {
  production: true,
  apiBaseUrl: 'https://example.com',
  oidc: {
    authority: 'https://example.com/realms/budget',
    clientId: 'angular-spa',
    logLevel: LogLevel.Error,
  },
};
