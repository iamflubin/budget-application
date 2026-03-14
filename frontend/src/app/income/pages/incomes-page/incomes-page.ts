import { ChangeDetectionStrategy, Component, DestroyRef, inject } from '@angular/core';
import { HlmButtonImports } from '@spartan-ng/helm/button';
import { HlmDialogService } from '@spartan-ng/helm/dialog';
import { AddIncomeDialog } from '../../components/add-income-dialog/add-income-dialog';
import { IncomeService } from '../../income-service';
import { IncomeRequest } from '../../income';
import { Observable } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-incomes-page',
  imports: [HlmButtonImports],
  templateUrl: './incomes-page.html',
  styleUrl: './incomes-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class IncomesPage {
  private readonly hlmDiagService = inject(HlmDialogService);
  private readonly incomeService = inject(IncomeService);
  private readonly destroyRef = inject(DestroyRef);

  protected openAddDialog(): void {
    this.hlmDiagService.open(AddIncomeDialog, {
      context: {
        loading: this.incomeService.loading,
        onAddIncome: (income: IncomeRequest) => this.addIncome(income),
      },
      contentClass: 'md:min-w-md',
      panelClass: 'w-full md:w-auto',
    });
  }

  private addIncome(income: IncomeRequest): Observable<void> {
    return this.incomeService.saveIncome(income).pipe(takeUntilDestroyed(this.destroyRef));
  }
}
