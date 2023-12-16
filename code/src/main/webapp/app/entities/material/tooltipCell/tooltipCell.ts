import { Component, inject, TemplateRef, Input, Output, EventEmitter, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { NgbDateStruct, NgbModal, NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import {formatDate} from '@angular/common';


@Component({
	selector: 'tooltip-cell',
	templateUrl: './tooltipCell.html',
})
export class ValueCell implements AfterViewInit, OnInit{
    
    
	
    @Input() oldValue! : number | null | undefined;
    @Input() generatedValue!: number;
    @ViewChild('t') tooltip!: NgbTooltip;
    messageVal : string = "";
    percentageVal : string = "";

    ngOnInit(): void {
        if(this.oldValue !== null && this.oldValue !== undefined){
            this.percentageVal = Math.round(((this.generatedValue - this.oldValue)/this.oldValue)*100).toString()
            this.messageVal =  this.percentageVal + "%"
        }
    }

    ngAfterViewInit(): void {
        this.openToolTip();
    }
    openToolTip(){
        console.log("Old Value is:" , this.oldValue)
        console.log("Generated Value is:" , this.generatedValue)
        if(this.oldValue !== null && this.oldValue !== undefined){
            if( this.generatedValue > (this.oldValue * 2) || this.generatedValue < (this.oldValue/2)){
                this.tooltip.open();
                setTimeout(() => {
                    this.tooltip.close();
                  }, 6000);
            }
        }
    }
}