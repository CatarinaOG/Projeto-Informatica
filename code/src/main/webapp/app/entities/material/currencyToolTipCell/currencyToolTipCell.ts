import { AfterContentInit, Component,  Input, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { Coin } from '../../enumerations/coin.model'
import { currencyExchangeRates } from '../list/currencyExchangeRates';


@Component({
	selector: 'currency-tooltip-cell',
	templateUrl: './currencyToolTipCell.html',
})
export class CurrencyToolTipCell implements OnInit{
    currencyExchangeRates = currencyExchangeRates;

   
    	
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