import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { Authority } from 'app/config/authority.constants';
import { TourService } from '../service/tour.service';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { tourMessages } from '../data/tourMessage';

/**
 * Component responsible for the options bar shown to the user and the functions necessary to support it
 */
@Component({
  selector: 'jhi-options-bar',
  templateUrl: './optionsBar.html',
})
export class OptionsBarComponent implements OnInit {

  /**
   * Table size, passed by the jhi-material component
   * @type {number}
   */
  @Input() tableSize! : number;

  /**
   * Current history size, passed by the jhi-material component
   * @type {number}
   */
  @Input() historyLength! : number; 

  /**
   * Number of edited rows, passed by the jhi-material component
   * @type {number}
   */
  @Input() changesListLength! : number;

  /**
   * Number of edited, but unselected, passed by the jhi-material component
   * @type {number}
   */
  @Input() unselectedLines! : number;

  /**
   * Current state of the local currency/euro switch, passed by the jhi-material component
   * @type {number}
   */
  @Input() currencyEUR ! : boolean;

  /**
   * Emitter of string values to jhi-material component
   * @type {EventEmitter<string>}
   */
  @Output() stringEmitter = new EventEmitter<string>();

  /**
   * Emitter to jhi-material component, used to send the file and operation type (true -> replace, false -> add)
   * @type {EventEmitter<{opType :boolean, file : File}>}
   */
  @Output() fileEmitter = new EventEmitter<{opType :boolean, file : File}>();

  /**
   * Emitter to jhi-material component, used to send the name of the column to which the filter will be applied and the filter text itself
   * @type {EventEmitter<{filterName : string, filterText : string}>}
   */
  @Output() textFilterEmitter = new EventEmitter<{filterName : string, filterText : string}>();

  /**
   * Emitter to jhi-material component, used to send which value is to be filtered
   * @type {EventEmitter<{opType : boolean, filterValue : string}>}
   */
  @Output() abcFilterEmitter = new EventEmitter<{opType : boolean, filterValue : string}>();

  /**
   * Emitter to jhi-material component, used to send the name of the column to which the filter will be applied, the operation (less than, greater than, equals etc) and the filter value itself
   * @type {EventEmitter<{filterName : string, operator : string, value : number}>}
   */
  @Output() numberFilterEmitter = new EventEmitter<{filterName : string, operator : string, value : number}>();

  /**
   * Emitter of the dropdown numbered values to jhi-material component. sends the menuName (dropdown or undo) and the dropdown value
   * @type {EventEmitter<{menuName:string, menuValue : number}>}
   */
  @Output() dropdownNumberEmitter = new EventEmitter<{menuName:string, menuValue : number}>();

  /**
   * Emitter of currency switch value
   * @type {EventEmitter<boolean>}
   */
  @Output() currencyValEmitter = new EventEmitter<boolean>();

   /**
   * Reference to the Tooltip used to showcase the Filters step of the guided tour
   * @type {NgbTooltip}
   */
  @ViewChild('t8') filtersTooltip!: NgbTooltip;

   /**
   * Reference to the Tooltip used to showcase the File Options step of the guided tour
   * @type {NgbTooltip}
   */
  @ViewChild('t9') fileTooltip!: NgbTooltip; 

   /**
   * Reference to the Tooltip used to showcase the Table Size options step of the guided tour
   * @type {NgbTooltip}
   */
  @ViewChild('t10') tableSizeTooltip!: NgbTooltip; 

   /**
   * Reference to the Tooltip used to showcase the Undo step of the guided tour
   * @type {NgbTooltip}
   */
  @ViewChild('t11') undoTooltip!: NgbTooltip; 

   /**
   * Reference to the Tooltip used to showcase the Undo options step of the guided tour
   * @type {NgbTooltip}
   */
  @ViewChild('t12') undoToolTip2!: NgbTooltip;

   /**
   * Reference to the Tooltip used to showcase the Currency switch step of the guided tour
   * @type {NgbTooltip}
   */
  @ViewChild('t13') switchTooltip!: NgbTooltip;
  
   /**
   * Reference to the Tooltip used to showcase the Submit step of the guided tour
   * @type {NgbTooltip}
   */
  @ViewChild('t14') submitTooltip!: NgbTooltip; 

  /**
   * Property that stores the Map with all the messages displayed during the Guided Process tutorial, imported from the data/tourMessage.ts file
   * @type {Map<string,string>}
   */
  tourMsgs = tourMessages

  /**
   * Property that keeps track of open input filters using a Map that has the filter name as key and boolean values
   * @type {Map<string,boolean>}
   */
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

  /**
   * Property that stores the current index of the guided tour messages
   * @type {number}
   */
  index = 0;

  /**
   * Property that stores the current max number of reverseable steps
   * @type {number}
   */
  undoSize = 10;

  /**
   * Constructor for the component
   * @constructor
   * @param accountService
   * @param tourService
   */
  constructor( private accountService: AccountService , protected tourService: TourService ) {}

  /**
   * Subscribes the component to the tourService index value
   */
  ngOnInit(): void {
    this.tourService.index$.subscribe(value =>  {
        this.index = value;
        this.defineStepTour(value);
      })
   }

   /**
    * Function that starts the guided process service
    */
   startTour(): void {
    this.tourService.start()
   }

   /**
    * Function that defines the current step the tour is in, activating the corresponding tooltip
    * @param {number} value - current tour step 
    */
   defineStepTour(value: number) : void {
    this.filtersTooltip.close()
    this.fileTooltip.close()
    this.tableSizeTooltip.close()
    this.undoTooltip.close()
    this.undoToolTip2.close()
    this.switchTooltip.close()
    this.submitTooltip.close()

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

  /**
   * Function that, in case the dropwdown is not open, sets all the filter status pairs to false
   * @param open 
   */
  checkDropDown(open:boolean): void {
    if(!open){
      for(const pair of this.filterStatus){
        this.filterStatus.set(pair[0], false);
      }
    }
  }

    /**
     * Sends message to trigger a load via the stringEmitter
     */
    sendLoad(): void{
        this.stringEmitter.emit("load");
    }

    /**
     * Sends message to trigger a file upload via the fileEmitter
     * @param {any} event the file to be uploaded 
     * @param {boolean} opType flag that distinguishes the operation type
     */
    sendFileMessage(event : any,opType : boolean) : void {
        // true -> replace
        // false -> add
        const file:File = event.target.files[0];
        this.fileEmitter.emit({opType,file})
    }

    /**
     * Changes the status of one of the filters
     * @param {string} name 
     */
    changeStatus(name : string) : void {
        for(const pair of this.filterStatus){
          this.filterStatus.set(pair[0], false);
        }
        this.filterStatus.set(name, true);
    }

    /**
     * Checks the status of the filters
     * @returns 
     */
    checkStatus(): boolean {
        let res = false
        this.filterStatus.forEach((value) => {
          if (value) {res = true}
        })
        return res
    }

    /**
     * Sends the message via the textFilterEmitter regarding what text filter is to be applied and the value
     * @param event 
     */
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

    /**
     * Sends the special filter prompt via the textFilterEmitter
     * @param filterNameOp 
     */
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

    /**
     * Function that receives the message sent by the jhi-filter-form, containing the name of the column to be filtered, the operator and the value to be applied
     * @param event 
     */
    receiveFilterNumberMessage(event : any) : void{
        for(const pair of this.filterStatus){
            this.filterStatus.set(pair[0], false)
        }

        this.numberFilterEmitter.emit({filterName : event.filterName,  operator : event.operator, value : event.value})
        
    }

    /**
     * Function that sends the "Download" prompt via stringEmitter to the jhi-material-component
     */
    downloadExcel() : void{
        this.stringEmitter.emit("Download");
    }

    /**
     * Function that sends the abc Classification filter value via the abcFilterEmitter to the jhi-material-component
     * @param {any} event 
     * @param {string} value
     */
    sendABCFilter(event : any,  value: string) : void {
        this.abcFilterEmitter.emit({opType: event.target.checked, filterValue : value})
    }

    /**
     * Function that sends the "Submit" or the Check unselected prompt via stringEmitter to the jhi-material-component
     * @param {any} event 
     */
    submitToSAP(event : any): void{
        if (event.confirm) {this.stringEmitter.emit("Submit")}
        else {this.stringEmitter.emit("Check unselected")}
    }

    /**
     * Function that sends the "undo" prompt via stringEmitter to the jhi-material-component
     */
    sendUndo() : void{
        this.stringEmitter.emit("Undo")
    }

    /**
     * Function that sends the menuName and the dropdownChoice to the jhi-material component via the dropdownNumberEmitter
     * @param {string} menuName either undo or table size
     * @param {number} dropdownChoice 
     */
    sendNumber(menuName : string ,dropdownChoice : number) : void{
        this.dropdownNumberEmitter.emit({ menuName ,menuValue : dropdownChoice})
        if(menuName === "undo"){
            this.undoSize =  dropdownChoice;
        }
    }

    /**
     * Checks if the value passed as an argument is equal to the UndoSize, used in the conditional rendering of the check mark next to the option
     * @param {number} dropdownValue 
     * @returns {boolean}
     */
    optionStatus(dropdownValue : number) : boolean{
        if (dropdownValue === this.undoSize) {return true}
        else {return false}
    }

    /**
     * Changes the value of currencyEur and sends that value to the jhi-material component
     */
    toggleSwitch() : void{
        if(this.currencyEUR){
            this.currencyValEmitter.emit(false)
        }
        else {
            this.currencyValEmitter.emit(true)
        }
    }

    /**
     * Function used to check if the current value is an Admin
     * @returns a boolean value
     */
    isAdmin () : boolean {
        return this.accountService.hasAnyAuthority(Authority.ADMIN)
    }

};

