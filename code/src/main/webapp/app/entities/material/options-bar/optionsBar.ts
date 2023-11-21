import { Component, EventEmitter, OnInit, Output } from '@angular/core';
@Component({
  selector: 'options-bar',
  templateUrl: './optionsBar.html',
})
export class OptionsBar implements OnInit {
  constructor() { }
  ngOnInit(): void { }

  @Output() messageEvent = new EventEmitter<string>();
  @Output() fileEmitter = new EventEmitter<{opType :boolean, fileName : string}>();

    sendLoad(){
        this.messageEvent.emit("load");
    }

    sendFilterReset(){
        this.messageEvent.emit("FilterReset")
    }

    sendFileMessage($event,op_Type){
        
    }

};