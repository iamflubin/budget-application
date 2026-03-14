import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IncomeRequest } from './income';
import { environment } from '../../environments/environment';
import { delay, finalize, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class IncomeService {
  private readonly baseUrl = `${environment.apiBaseUrl}/v1/incomes`;
  private readonly http = inject(HttpClient);
  private readonly _loading = signal(false);
  readonly loading = this._loading.asReadonly();

  saveIncome(income: IncomeRequest): Observable<void> {
    this._loading.set(true);
    return this.http.post<void>(this.baseUrl, income).pipe(
      delay(5000),
      finalize(() => this._loading.set(false)),
    );
  }
}
