import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { Authority } from 'app/config/authority.constants';
import { Subscription } from 'rxjs';
import { TourService } from '../service/tour.service';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { tourMessages } from '../data/tourMessage';

@Component({
  selector: 'options-bar',
  templateUrl: './optionsBar.html',
})
export class OptionsBar implements OnInit {

  @Input() tableSize! : number;
  @Input() historyLength! : number; 
  @Input() changesListLength! : number;
  @Input() unselectedLines! : number;
  @Input() currencyEUR ! : boolean;
  @Output() stringEmitter = new EventEmitter<string>();
  @Output() fileEmitter = new EventEmitter<{opType :boolean, file : File}>();
  @Output() textFilterEmitter = new EventEmitter<{filterName : string, filterText : string}>();
  @Output() abcFilterEmitter = new EventEmitter<{opType : boolean, filterValue : string}>();
  @Output() numberFilterEmitter = new EventEmitter<{filterName : string, operator : string, value : number}>();
  @Output() dropdownNumberEmitter = new EventEmitter<{menuName:string, menuValue : number}>();
  @Output() currencyValEmitter = new EventEmitter<boolean>();

  @ViewChild('t8') filtersTooltip!: NgbTooltip;
  @ViewChild('t9') fileTooltip!: NgbTooltip; 
  @ViewChild('t10') tableSizeTooltip!: NgbTooltip; 
  @ViewChild('t11') undoTooltip!: NgbTooltip; 
  @ViewChild('t12') undoToolTip2!: NgbTooltip;
  @ViewChild('t13') switchTooltip!: NgbTooltip; 
  @ViewChild('t14') submitTooltip!: NgbTooltip; 
  tourMsgs = tourMessages

  filterStatus = new Map<string,boolean>([
    ["Material Name", false],
    ["Material Description",false],
    ["ABC Classification", false],
    ["Avg Supplier Delay",false],
    ["Max Supplier Delay", false],
    ["Current SAP Safety Stock", false],
    ["Proposed Safety Stock",false],
    ["Delta Safety Stock", false],
    ["Current SAP Safety Time", false],
    ["Proposed Safety Time",false],
    ["Delta Safety Time", false],
    ["Service Level", false],
    ["Current Inventory", false],
    ["Avg. Inv. Effect After Change", false]
  ]);
  index: number = 0;

  undoSize : number= 10;
  private subscription: Subscription = new Subscription();

  constructor( private accountService: AccountService , public tourService: TourService ) {}
  ngOnInit(): void {
    this.subscription = this.tourService.index$.subscribe(value =>  {
        this.index = value;
        this.defineStepTour(value);
      })
   }


   defineStepTour(value: number) {
    if(this.filtersTooltip) this.filtersTooltip.close()
    if(this.fileTooltip) this.fileTooltip.close()
    if(this.tableSizeTooltip) this.tableSizeTooltip.close()
    if(this.undoTooltip) this.undoTooltip.close()
    if(this.undoToolTip2) this.undoToolTip2.close()
    if(this.switchTooltip) this.switchTooltip.close()
    if(this.submitTooltip) this.submitTooltip.close()

    switch(value) {
      case 8: // filters dropdown menu
        document.getElementById("dropdownMenuButtonFilterOptions")?.focus()
        if (this.filtersTooltip ) this.filtersTooltip.open()
        break;

      case 9:  // file options dropdown menu
        document.getElementById("dropdownMenuButtonFileOptions")?.focus()
        if (this.fileTooltip ) this.fileTooltip.open()
        break;

      case 10:  // table size dropdown menu
        document.getElementById("dropdownMenuButtonTableSize")?.focus()
        if (this.tableSizeTooltip ) this.tableSizeTooltip.open()
        break;

      case 11:  // undo btn
        document.getElementById("undoTooltipId")?.focus()
        if (this.undoTooltip ) this.undoTooltip.open()
        break;

      case 12: // undo dropdown menu
        document.getElementById("undoTooltipId")?.focus()
        if(this.undoToolTip2) this.undoToolTip2.open()
        break;
    
      case 13: // switch currency
        document.getElementById("switchCurrencyId")?.focus()
        if (this.switchTooltip ) this.switchTooltip.open()
        break;

      case 14: // submit
        document.getElementById("submitTooltipId")?.focus()
        if (this.submitTooltip ) this.submitTooltip.open()
        break;

      default:
        break;
    }
  }



    sendLoad(): void{
        this.stringEmitter.emit("load");
    }

    sendFileMessage(event : any,opType : boolean) : void {
        //true -> replace
        //false -> add
        console.log("Entrou na função")
        const file:File = event.target.files[0];
        if (file){
            this.fileEmitter.emit({opType,file})
        }
    }

    changeStatus(name : string) : void {
        for(let pair of this.filterStatus){
          this.filterStatus.set(pair[0], false);
        }
        this.filterStatus.set(name, true);
    }

    checkStatus(): boolean {
        let res = false
        this.filterStatus.forEach((value, key) => {
          if (value) res = true
        })
        return res
    }

    sendFilterText(event : any) : void {
        console.log(event.target.value)
        if (this.filterStatus.get("Material Name")){
            this.textFilterEmitter.emit({filterName : "Material Name", filterText : event.target.value})
        }
        else if (this.filterStatus.get("Material Description")){
            this.textFilterEmitter.emit({filterName : "Material Description", filterText : event.target.value})      
        }
    }

    sendFilterSpecial(filterNameOp: string) : void {

        switch (filterNameOp) {
            case 'Selected':
                this.textFilterEmitter.emit({filterName : "Selected Materials", filterText : filterNameOp})
                break;
            case 'Unselected':
                this.textFilterEmitter.emit({filterName : "Selected Materials", filterText : filterNameOp})
                break;
            case 'Unedited':
                this.textFilterEmitter.emit({filterName : "Selected Materials", filterText : filterNameOp})
                break;
            case 'Flagged':
                this.textFilterEmitter.emit({filterName : "Flagged Materials", filterText : filterNameOp})
                break;
            case 'Unflagged':
                this.textFilterEmitter.emit({filterName : "Flagged Materials", filterText : filterNameOp})
                break;
            default:
                break;
        }

    }

    receiveFilterNumberMessage(event : any) : void{
        for(let pair of this.filterStatus){
            this.filterStatus.set(pair[0], false)
        }

        this.numberFilterEmitter.emit({filterName : event.filterName,  operator : event.operator, value : event.value})
        
    }

    downloadExcel() : void{
        this.stringEmitter.emit("Download");
    }

    sendABCFilter(event : any,  value: string) : void {
        this.abcFilterEmitter.emit({opType: event.target.checked, filterValue : value})
    }

    submitToSAP(event : any){
        if (event.confirm) this.stringEmitter.emit("Submit")
        else this.stringEmitter.emit("Check unselected")
    }

    sendUndo(){
        this.stringEmitter.emit("Undo")
    }

    sendNumber(menuName : string ,dropdownChoice : number){
        this.dropdownNumberEmitter.emit({ menuName : menuName ,menuValue : dropdownChoice})
        if(menuName === "undo"){
            this.undoSize =  dropdownChoice;
        }
    }

    optionStatus(dropdownValue : number) : boolean{
        if (dropdownValue === this.undoSize) return true
        else return false
    }

    toggleCheckbox(){
        if(this.currencyEUR){
            this.currencyValEmitter.emit(false)
        }
        else {
            this.currencyValEmitter.emit(true)
        }
    }

    isAdmin () : boolean {
        return this.accountService.hasAnyAuthority(Authority.ADMIN)
    }

};

