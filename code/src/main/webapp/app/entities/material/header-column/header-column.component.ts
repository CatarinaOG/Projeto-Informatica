import { Component, Input} from '@angular/core';

@Component({
  selector: 'header-column',
  templateUrl: './header-column.component.html',
})
export class HeaderColumn {

@Input() headerName!: string;
@Input() tooltipMsg!: string | undefined; 

  constructor( ) { }

};