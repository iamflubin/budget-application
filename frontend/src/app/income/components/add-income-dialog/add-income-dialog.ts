import { ChangeDetectionStrategy, Component, inject, signal, Signal } from '@angular/core';
import { BrnDialogRef, injectBrnDialogContext } from '@spartan-ng/brain/dialog';
import { IncomeRequest } from '../../income';
import { HlmDialogDescription, HlmDialogHeader, HlmDialogTitle } from '@spartan-ng/helm/dialog';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { formatISO, startOfToday } from 'date-fns';
import { Observable } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

import { HlmFormFieldImports } from '@spartan-ng/helm/form-field';
import { HlmInputImports } from '@spartan-ng/helm/input';
import { HlmLabelImports } from '@spartan-ng/helm/label';
import { HlmDatePickerImports } from '@spartan-ng/helm/date-picker';
import { HlmButtonImports } from '@spartan-ng/helm/button';
import { LoadingButton } from '../../../_shared/components/loading-button/loading-button';

@Component({
  selector: 'app-add-income-dialog',
  imports: [
    HlmDialogHeader,
    HlmDialogTitle,
    HlmDialogDescription,
    ReactiveFormsModule,
    HlmFormFieldImports,
    HlmInputImports,
    HlmLabelImports,
    HlmDatePickerImports,
    HlmButtonImports,
    LoadingButton,
  ],
  templateUrl: './add-income-dialog.html',
  styleUrl: './add-income-dialog.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  host: {
    class: 'flex flex-col gap-4',
  },
})
export class AddIncomeDialog {
  private readonly dialogRef = inject<BrnDialogRef<IncomeRequest>>(BrnDialogRef);
  private readonly dialogContext = injectBrnDialogContext<{
    loading: Signal<boolean>;
    onAddIncome: (income: IncomeRequest) => Observable<void>;
  }>();
  private readonly fb = inject(NonNullableFormBuilder);
  private readonly onAddIncome = this.dialogContext.onAddIncome;
  protected readonly loading = this.dialogContext.loading;
  protected readonly error = signal('');

  protected readonly form = this.fb.group({
    name: ['', Validators.required],
    amount: [1, [Validators.required, Validators.min(1)]],
    date: [startOfToday(), Validators.required],
  });

  get f() {
    return this.form.controls;
  }

  addIncome(): void {
    this.form.markAllAsTouched();
    if (!this.form.valid) {
      return;
    }
    const { name, amount, date } = this.form.getRawValue();
    const income: IncomeRequest = {
      name,
      amount,
      date: formatISO(date, { representation: 'date' }),
    };
    this.disableClose();
    this.onAddIncome(income).subscribe({
      next: () => {
        this.enableClose();
        this.dialogRef.close();
      },
      error: (err: unknown) => {
        if (!(err instanceof HttpErrorResponse)) {
          console.error(err);
          return;
        }
        this.error.set(err.error.message);
        this.enableClose();
      },
    });
  }

  protected onCancel(): void {
    this.dialogRef.close();
  }

  private enableClose(): void {
    this.dialogRef.updateOptions({
      disableClose: false,
    });
  }

  private disableClose(): void {
    this.dialogRef.updateOptions({
      disableClose: true,
    });
  }
}
