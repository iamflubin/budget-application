import { ChangeDetectionStrategy, Component, computed, input } from '@angular/core';
import { ButtonVariants, HlmButton } from '@spartan-ng/helm/button';
import { HlmSpinnerImports } from '@spartan-ng/helm/spinner';

@Component({
  selector: 'app-loading-button',
  imports: [HlmButton, HlmSpinnerImports],
  template: `
    <button hlmBtn [size]="size()" [variant]="variant()" [disabled]="loading()">
      @if (loading()) {
        <hlm-spinner />
      }
      {{ finalLabel() }}
    </button>
  `,
  styleUrl: './loading-button.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoadingButton {
  readonly loading = input(false);
  readonly label = input<string>('Submit');
  readonly variant = input<ButtonVariants['variant']>('default');
  readonly size = input<ButtonVariants['size']>('default');
  readonly finalLabel = computed(() => (this.loading() ? 'Loading...' : this.label()));
}
