import {Component,  Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { Coin } from '../../enumerations/coin.model'
import { currencyExchangeRates } from '../data/currencyExchangeRates';

/**
 * Component that displays a monetary value and a tooltip when that value is hovered
 */
@Component({
	selector: 'jhi-currency-tooltip-cell',
	templateUrl: './currencyToolTipCell.html',
})
export class CurrencyToolTipCellComponent implements OnInit , OnChanges{
    
    /**
     * Constant value that stores the currency  exchange rates to be used in calculations
     * @type {Map<string,number>}
     */
    currencyExchangeRates = currencyExchangeRates;

   
    /**
     * Original value, passed by the jhi-material component
     * @type {number | null | undefined}
     */
    @Input() value! : number | null | undefined;

    /**
     * Currency the monetary value is on
     * @type {Coin | null | undefined}
     */
    @Input() currency!: Coin | null | undefined;

    /**
     * Currency boolean indicating wether the value is local Currency or not
     * @type {boolean}
     */
    @Input() currencyEUR! : boolean

    /**
     * Reference to the tooltip used to show the message
     * @type {NgbTooltip}
     */
    @ViewChild('t') tooltip!: NgbTooltip;

    /**
     * Property the stores the message to be shown in the tooltip
     * @type {string}
     */
    messageVal = "";

    /**
     * Function that executes the update to the message everytime the currencyEUR value is changed
     * @param changes 
     */
    ngOnChanges(changes: SimpleChanges): void {
        if ('currencyEUR' in changes) {
          this.updateMessage();
        }
      }

    /**
     * Function that executes when the component is initialized, executes updateMessage
     */
    ngOnInit(): void {
        this.updateMessage();
    }

    /**
     * Function that defines the value of messageVal, depending on wether the monetary value was converted or not
     */
    updateMessage() : void{
        if (this.currency !== "EUR" && this.currencyEUR && this.currency !== null && this.currency !== undefined){
            this.messageVal = "The value displayed was converted to EUR, with a " + this.currencyExchangeRates.get(this.currency)?.toString() + " exchange rate from it's value in " + this.currency
        }
        else {
            this.messageVal = "The value displayed is in " + this.currency
        }
    }




}