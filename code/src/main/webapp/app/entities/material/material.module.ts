import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MaterialComponent } from './list/material.component';
import { MaterialRoutingModule } from './route/material-routing.module';
import { OptionsBar} from './options-bar/optionsBar'
import { FilterDisplayCell } from './filter-display-cell/filterDisplayCell'
import { FilterForm} from './filter-form/filterForm'
import { FlagModal } from './flag-modal/flagModal'
import { HeaderGroup } from './header-group/header-group.component'
import { HeaderColumn} from './header-column/header-column.component'
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { SubmitModal } from './submit-modal/submitModal';
import { ValueCell } from './tooltipCell/tooltipCell'
import { CurrencyToolTipCell } from './currencyToolTipCell/currencyToolTipCell';
import { CommentModal } from './comment-modal/commentModal'



@NgModule({
  imports: [SharedModule, MaterialRoutingModule,NgbTooltipModule],
  declarations: [MaterialComponent, OptionsBar, FilterDisplayCell, FilterForm, FlagModal, HeaderGroup, HeaderColumn, SubmitModal, ValueCell, CurrencyToolTipCell, CommentModal],
  exports: [HeaderGroup, FlagModal, FilterDisplayCell]
})
export class MaterialModule {}
