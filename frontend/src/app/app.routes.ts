import { Routes } from '@angular/router';
import { Dashboard } from './dashboard/dashboard';
import { authGuard } from './auth/auth-guard';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'dashboard',
  },
  {
    path: '',
    canActivate: [authGuard],
    children: [
      {
        path: 'dashboard',
        component: Dashboard,
      },
      {
        path: 'incomes',
        loadChildren: () => import('./income/income.routes').then((m) => m.INCOME_ROUTES),
      },
    ],
  },
  {
    path: '**',
    redirectTo: '',
  },
];
