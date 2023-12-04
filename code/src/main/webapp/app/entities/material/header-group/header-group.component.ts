import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'header-group',
  templateUrl: './header-group.component.html',
})
export class HeaderGroup implements OnInit {

@Input() headerName!: string; 
@Input() visibilityValue!: boolean | undefined;
@Input() groupName! : string;
@Output() visibilityEmitter = new EventEmitter<{name : string, visibility : boolean}>();

  constructor( ) { }
  ngOnInit(): void { }

  sendVisibilityMessage() : void {
    this.visibilityEmitter.emit({name : this.groupName,visibility : !this.visibilityValue})
  }
};