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
    ["Plant",false],
    ["MRP Controller",false],
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
  index = 0;

  undoSize = 10;

  constructor( private accountService: AccountService , public tourService: TourService ) {}
  ngOnInit(): void {
    this.tourService.index$.subscribe(value =>  {
        this.index = value;
        this.defineStepTour(value);
      })
   }

   startTour(): void {
    this.tourService.start()
   }

   defineStepTour(value: number) : void {
    if (this.filtersTooltip !== undefined) this.filtersTooltip.close()
    if (this.fileTooltip !== undefined) this.fileTooltip.close()
    if (this.tableSizeTooltip !== undefined) this.tableSizeTooltip.close()
    if (this.undoTooltip !== undefined) this.undoTooltip.close()
    if (this.undoToolTip2 !== undefined) this.undoToolTip2.close()
    if (this.switchTooltip !== undefined) this.switchTooltip.close()
    if (this.submitTooltip !== undefined) this.submitTooltip.close()

    switch(value) {
      case 8: // filters dropdown menu
        document.getElementById("dropdownMenuButtonFilterOptions")?.focus()
        this.filtersTooltip.open()
        break;

      case 9:  // file options dropdown menu
        document.getElementById("dropdownMenuButtonFileOptions")?.focus()
        this.fileTooltip.open()
        break;

      case 10:  // table size dropdown menu
        document.getElementById("dropdownMenuButtonTableSize")?.focus()
        this.tableSizeTooltip.open()
        break;

      case 11:  // undo btn
        document.getElementById("undoTooltipId")?.focus()
        this.undoTooltip.open()
        break;

      case 12: // undo dropdown menu
        document.getElementById("undoTooltipId")?.focus()
        this.undoToolTip2.open()
        break;
    
      case 13: // switch currency
        document.getElementById("switchCurrencyId")?.focus()
        this.switchTooltip.open()
        break;

      case 14: // submit
        document.getElementById("submitTooltipId")?.focus()
        this.submitTooltip.open()
        break;

      default:
        break;
    }
  }

  checkDropDown(open:boolean) {
    if(!open){
      for(const pair of this.filterStatus){
        this.filterStatus.set(pair[0], false);
      }
    }
  }

    sendLoad(): void{
        this.stringEmitter.emit("load");
    }

    sendFileMessage(event : any,opType : boolean) : void {
        // true -> replace
        // false -> add
        console.log("FILE OPERATION IS: ", opType)
        const file:File = event.target.files[0];
        this.fileEmitter.emit({opType,file})
    }

    changeStatus(name : string) : void {
        for(const pair of this.filterStatus){
          this.filterStatus.set(pair[0], false);
        }
        this.filterStatus.set(name, true);
    }

    checkStatus(): boolean {
        let res = false
        this.filterStatus.forEach((value) => {
          if (value) {res = true}
        })
        return res
    }

    sendFilterText(event : any) : void {
        if (this.filterStatus.get("Material Name")){
            this.textFilterEmitter.emit({filterName : "Material Name", filterText : event.target.value})
        }
        else if (this.filterStatus.get("Material Description")){
            this.textFilterEmitter.emit({filterName : "Material Description", filterText : event.target.value})      
        }
        else if (this.filterStatus.get("MRP Controller")) {
          this.textFilterEmitter.emit({filterName : "MRP Controller", filterText : event.target.value})    
        }
        else if (this.filterStatus.get("Plant")) {
          this.textFilterEmitter.emit({filterName : "Plant", filterText : event.target.value.toString()})
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
        for(const pair of this.filterStatus){
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

    submitToSAP(event : any): void{
        if (event.confirm) {this.stringEmitter.emit("Submit")}
        else {this.stringEmitter.emit("Check unselected")}
    }

    sendUndo() : void{
        this.stringEmitter.emit("Undo")
    }

    sendNumber(menuName : string ,dropdownChoice : number) : void{
        this.dropdownNumberEmitter.emit({ menuName ,menuValue : dropdownChoice})
        if(menuName === "undo"){
            this.undoSize =  dropdownChoice;
        }
    }

    optionStatus(dropdownValue : number) : boolean{
        if (dropdownValue === this.undoSize) {return true}
        else {return false}
    }

    toggleCheckbox() : void{
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

