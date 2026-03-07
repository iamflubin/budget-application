import { LogLevel } from 'angular-auth-oidc-client';

export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8080',
  oidc: {
    authority: 'http://localhost:8082/realms/budget-dev',
    clientId: 'angular-spa',
    logLevel: LogLevel.Debug,
  },
};
