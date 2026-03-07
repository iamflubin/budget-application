import { CanActivateFn } from '@angular/router';
import { map, take } from 'rxjs';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = () => {
  const oidcSecurityService = inject(OidcSecurityService);
  return oidcSecurityService.isAuthenticated$.pipe(
    take(1),
    map(({ isAuthenticated }) => {
      if (!isAuthenticated) {
        oidcSecurityService.authorize();
        return false;
      }
      return true;
    }),
  );
};
