import { HttpContextToken, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { catchError, of, switchMap, take } from 'rxjs';

// Per-request opt-out
export const SKIP_AUTH = new HttpContextToken<boolean>(() => false);

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  // allow explicit opt-out
  if (req.context.get(SKIP_AUTH)) {
    return next(req);
  }

  if (req.headers.has('Authorization')) {
    return next(req);
  }

  const oidc = inject(OidcSecurityService);

  return oidc.getAccessToken().pipe(
    take(1),
    catchError(() => of('')),
    switchMap((token) => {
      if (!token) {
        return next(req);
      }
      const authReq = req.clone({
        setHeaders: { Authorization: `Bearer ${token}` },
      });
      return next(authReq);
    }),
  );
};
