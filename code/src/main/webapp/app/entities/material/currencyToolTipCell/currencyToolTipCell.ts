import { AfterContentInit, Component,  Input, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { Coin } from '../../enumerations/coin.model'


@Component({
	selector: 'currency-tooltip-cell',
	templateUrl: './currencyToolTipCell.html',
})
export class CurrencyToolTipCell implements OnInit{
    
    currencyExchangeRates = new Map <string, number>([
        ["EUR", 1],
        ["USD", 0.915],
        ["GBP", 1.158],
        ["NZD", 0.568],
        ["DKK", 0.134],
        ["RUB", 0.010],
        ["INR", 0.011],
        ["HKD", 0.117],
        ["SGD", 0.687],
        ["CHF", 1.055],
        ["TRY", 0.031],
        ["ILS", 0.249],
        ["CAD", 0.683],
        ["CNY", 0.129]
    ]);
    	
    @Input() value! : number | null | undefined;
    @Input() currency!: Coin | null | undefined;
    @Input() currencyEUR! : boolean
    @ViewChild('t') tooltip!: NgbTooltip;
    messageVal : string = "";

    ngOnChanges(changes: SimpleChanges): void {
        if ('currencyEUR' in changes) {
          this.updateMessage();
        }
      }

    updateMessage() : void{
        console.log("CurrencyEUR value is :", this.currencyEUR)
        if (this.currency !== "EUR" && this.currencyEUR && this.currency !== null && this.currency !== undefined){
            this.messageVal = "The value displayed was converted to EUR, with a " + this.currencyExchangeRates.get(this.currency) + " exchange rate from it's value in " + this.currency
        }
        else {
            this.messageVal = "The value displayed is in " + this.currency
        }
    }


    ngOnInit(): void {
        this.updateMessage();
    }

}