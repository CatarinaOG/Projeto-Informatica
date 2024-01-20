import { Component,Input, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';

/**
 * Component that is both responsible for showing the value of NewSAPSafety Stock and Time and shows tooltips if the difference after an edited value is too large
 */
@Component({
	selector: 'jhi-tooltip-cell',
	templateUrl: './tooltipCell.html',
})
export class ValueCellComponent implements AfterViewInit, OnInit{
     /**
   * Property passed by the jhi-material component, old value of the cell used for comparison
   * @type {number | null | undefined}
   */
    @Input() oldValue! : number | null | undefined;

    /**
   * Property passed by the jhi-material component, new value of the cell used for comparison
   * @type {number}
   */
    @Input() generatedValue!: number;

    /**
   * Reference to the Tooltip used to showcase the Link step of the guided tour
   * @type {NgbTooltip}
   */
    @ViewChild('t') tooltip!: NgbTooltip;

    /**
     * Property that stores the message to be shown in the tooltip
     * @type {string}
     */
    messageVal = "";

    /**
     * Property that stores the percentage value to be shown in the tooltip
     * @type {string}
     */
    percentageVal = "";


    /**
     * Function that executes when the component is initialized. calculates the percentage value to be shown
     */
    ngOnInit(): void {
        if(this.oldValue !== null && this.oldValue !== undefined){
            this.percentageVal = Math.round(((this.generatedValue - this.oldValue)/this.oldValue)*100).toString()
            this.messageVal =  this.percentageVal + "%"
        }
    }

    /**
     * Function that executes after que component view is initialized, executes the openToolTip function
     */
    ngAfterViewInit(): void {
        this.openToolTip();
    }

    /**
     * Function that opens the tooltip with the message if the new value is more than double the value of the original (old) one or less than half.
     */
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