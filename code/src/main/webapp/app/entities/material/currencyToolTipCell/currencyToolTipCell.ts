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
        ["GBP", 1.157],
        ["AUD", 0.613],
        ["JPY", 0.006],
        ["CNY", 0.128],
        ["CAD", 0.683],
        ["CHF", 1.054],
        ["HKD", 0.117],
        ["SGD", 0.687],
        ["SEK", 0.089],
        ["KRW", 0.0007],
        ["NOK", 0.088],
        ["NZD", 0.568],
        ["INR", 0.011],
        ["MXN", 0.053],
        ["TWD", 0.029],
        ["ZAR", 0.049],
        ["BRL", 0.186],
        ["DKK", 0.134],
        ["PLN", 0.231],
        ["THB", 0.026],
        ["ILS", 0.249],
        ["IDR", 0.00006],
        ["CZK", 0.040],
        ["AED", 0.249],
        ["TRY", 0.031],
        ["HUF", 0.002],
        ["CLP", 0.001],
        ["SAR", 0.244],
        ["PHP", 0.016],
        ["MYR", 0.195],
        ["COP", 0.0002],
        ["RUB", 0.010],
        ["RON", 0.201],
        ["PEN", 0.244],
        ["BHD", 2.435],
        ["BGN", 0.511]
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