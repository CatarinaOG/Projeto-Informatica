import { Component,Input, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';


@Component({
	selector: 'jhi-tooltip-cell',
	templateUrl: './tooltipCell.html',
})
export class ValueCellComponent implements AfterViewInit, OnInit{
    
    @Input() oldValue! : number | null | undefined;
    @Input() generatedValue!: number;
    @ViewChild('t') tooltip!: NgbTooltip;
    messageVal = "";
    percentageVal = "";

    ngOnInit(): void {
        if(this.oldValue !== null && this.oldValue !== undefined){
            this.percentageVal = Math.round(((this.generatedValue - this.oldValue)/this.oldValue)*100).toString()
            this.messageVal =  this.percentageVal + "%"
        }
    }

    ngAfterViewInit(): void {
        this.openToolTip();
    }
    openToolTip() : void{
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