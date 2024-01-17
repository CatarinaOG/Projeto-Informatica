import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MaterialComponent } from './list/material.component';
import { MaterialRoutingModule } from './route/material-routing.module';
import { OptionsBarComponent } from './options-bar/optionsBar'
import { FilterDisplayCellComponent } from './filter-display-cell/filterDisplayCell'
import { FilterFormComponent } from './filter-form/filterForm'
import { FlagModalComponent } from './flag-modal/flagModal'
import { HeaderGroupComponent } from './header-group/header-group.component'
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { SubmitModalComponent } from './submit-modal/submitModal';
import { ValueCellComponent } from './tooltipCell/tooltipCell'
import { CurrencyToolTipCellComponent } from './currencyToolTipCell/currencyToolTipCell';
import { CommentModalComponent } from './comment-modal/commentModal'



@NgModule({
  imports: [SharedModule, MaterialRoutingModule,NgbTooltipModule],
  declarations: [MaterialComponent, OptionsBarComponent, FilterDisplayCellComponent, FilterFormComponent, FlagModalComponent, HeaderGroupComponent, SubmitModalComponent, ValueCellComponent, CurrencyToolTipCellComponent, CommentModalComponent],
  exports: [FilterDisplayCellComponent],
})
export class MaterialModule {}
