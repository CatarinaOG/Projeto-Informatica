import { Component, OnDestroy, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, last, Observable, Subscription, switchMap, tap } from 'rxjs';
import { NgbModal, NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { IEditCell } from '../editCell.model'
import { specialFilter } from '../specialFilters.model'
import { IMaterial } from '../material.model';

import { PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, MaterialService } from '../service/material.service';
import { EditCellService } from '../service/editCell.service';
import { FilterOptions, IFilterOptions, IFilterOption } from 'app/shared/filter/filter.model';
import { IHistoryEntity } from '../historyEntity.model';
import { Coin } from '../../enumerations/coin.model'
import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';
import { MSG } from '../data/tooltipMsg';
import { currencyExchangeRates } from '../data/currencyExchangeRates';
import { TourService } from '../service/tour.service';
import { tourMessages } from '../data/tourMessage';


/**
 * Component responsible for showing the Material Table, and all the logic necessary to support it
 */
@Component({
  selector: 'jhi-material',
  templateUrl: './material.component.html',
})
export class MaterialComponent implements OnInit, OnDestroy, AfterViewInit {


  //@ViewChild(NgbAlert, { static: false }) alert!: NgbAlert | undefined;
  
  //@ViewChildren(NgbTooltip) tooltips!: QueryList<NgbTooltip>;

  /**
   * Reference to the Tooltip used to showcase the Link step of the guided tour
   * @type {NgbTooltip}
   */
  @ViewChild('t2') linkTourTooltip!: NgbTooltip; 

  /**
   * Reference to the Tooltip used to showcase the Edit Menu step of the guided tour
   * @type {NgbTooltip}
   */
  @ViewChild('t3') editMenuTooltip!: NgbTooltip; 

  /**
   * Reference to the Tooltip used to showcase the SAP Safety Stock cell step of the guided tour
   * @type {NgbTooltip}
   */
  @ViewChild('t4') sapSafetyStockToolTip!: NgbTooltip;
  
  /**
   * Reference to the Tooltip used to showcase the step regarding the line selection of the guided tour
   * @type {NgbTooltip}
   */
  @ViewChild('t5') selectEntryTooltip!: NgbTooltip;
  
  /**
   * Reference to the Tooltip used to showcase the flagging step of the guided tour
   * @type {NgbTooltip}
   */
  @ViewChild('t6') flagTooltip!: NgbTooltip; 

  /**
   * Reference to the Tooltip used to showcase the step regarding the commenting of a Material of the guided tour
   * @type {NgbTooltip}
   */
  @ViewChild('t7') commentTooltip!: NgbTooltip; 

  /**
   * Property where the current Alert Message is stored. Starts as "n/a", which means it's not active and will change whenever it is necessary to alert the user.
   * @type {string}
   */
  alertMessage = "n/a";

  /**
   * Property that stores the current Materials being shown to the user in the table page.
   * @type {IMaterial[]}
   */
  materials?: IMaterial[];

  /**
   * Property the current number of allowed reversable steps in the Undo.
   * Default value is 10
   * @type {number}
   */
  undoSize = 10;

  /**
   * Property that stores the current filter options applied.
   * @type {IFilterOptions}
   */
  filters: IFilterOptions = new FilterOptions();

  /**
   * Property that stores the current state of the "Currency" switch. Default value is true
   * @type {boolean}
   */
  currencyEUR  = true;

  /**
   * Property that stores the list of changes that can be reverted by pressing the Undo button in the user interface.
   * @type {IHistoryEntity[]}
   */
  history: IHistoryEntity[] = [];

  /**
   * Property that stores the Map with all the Tooltip messages, imported from the data/tooltipMsg file
   * @type {Map<string,string>}
   */
  toolTipMsg = MSG;

  /**
   * Property that stores the Map with all the currency exchange rates messages, imported from the data/currencyExchangeRates.ts file
   * @type {Map<string,number>}
   */
  currencyExchangeRates = currencyExchangeRates;

    /**
   * Property that stores the Map with all the messages displayed during the Guided Process tutorial, imported from the data/tourMessage.ts file
   * @type {Map<string,string>}
   */
  tourMsgs = tourMessages;

   /**
   * Property that stores the current visibility state of each column group. The keys are the 
   * @type {Map<string,boolean>}
   */
  visibility = new Map<string, boolean>();
  visibilityClass = new Map<string, string>();


   /**
   * Property that keeps track of if the Submit button has been clicked 
   * @type {boolean}
   */
  clickedSubmit = false;

  /**
   * Property that is meant to keep track of  keeps track of what filters are active regarding selected and unselected lines, as well as flagged and unflagged items. It also keeps track of what Material id's fall into each filter. 
   * @type {specialFilter[]}
   */
  specialFiltersList : specialFilter[] = [
    {name: "Selected", isActive: false, idList:[]},
    {name: "Unselected", isActive: false, idList:[]},
    {name: "Unedited", isActive: false, idList:[]},
    {name: "Flagged", isActive: false, idList:[]},
    {name: "Unflagged", isActive: false, idList:[]},
  ];

  /**
   * Property created by JHipster
   */
  predicate = 'id';

  /**
   * Property created by JHipster
   */
  ascending = true;

  /**
   * Property created by JHipster
   */
  isLoading = false;

  /**
   * Property created by JHipster
   */
  subscription: Subscription = new Subscription();

  /**
   * Property created by JHipster. Defines the number of items in each table page
   */
  itemsPerPage = 10;

  /**
   * Property created by JHipster. Defines the number of total items
   */
  totalItems = 0;

  /**
   * Property created by JHipster. Defines the current page
   */
  page = 1;

  /**
   * Property that keeps track of what cell is currently open to edit, to aid in the conditional render
   * @type {number[]}
   */
  private _isEditable : number[] = [-1,-1];



  /**
   * Constructor for the jhi-material component
   * @constructor
   * @param materialService 
   * @param editCellService 
   * @param tourService
   * @param activatedRoute 
   * @param router 
   * @param modalService 
   */
  constructor(
    protected materialService: MaterialService,
    protected editCellService: EditCellService,
    protected tourService: TourService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {
    this.visibility = new Map<string, boolean>([
      ["materialInfo", true],
      ["supplierDelay", false],
      ["safetyStock", false],
      ["safetyTime", false],
      ["inventory", true],
      ["edit", false]
    ])
  }



  /**
   * Property created by JHipster
   */
  trackId = (_index: number, item: IMaterial): number => this.materialService.getMaterialIdentifier(item);

  /**
   * Getter for the isEditable property
   * @returns the value of this._isEditable
   */
  get isEditable(): number[] {
    return this._isEditable;
  }
  
  /**
   * Setter for the isEditable property
   * @param {number[]} value - table position of the editable cell
   */  
  set isEditable(value: number[]) {
    this._isEditable = [value[0], value[1]]
  }


  /**
   * Init function of the Material Component, starts by loading the Materials to be shown in this table page and then subscribes to this.filters.filterChanges
   * Also removes all the filters assigned to id.in and id.notIn
   */
  ngOnInit(): void {
    this.load();
    this.filters.filterChanges.subscribe({
      next: filterOptions => this.handleNavigation(1, this.predicate, this.ascending, filterOptions),
      error(){
        alert('Error on Init')
      } 
    });
    this.filters.removeAllFiltersName("id.in");
    this.filters.removeAllFiltersName("id.notIn");
  }

  /**
   * This function subscribes to the tourService.index value, everytime this value is changed, calls the function defineStepTour with the new value as a parameter.
   */
  ngAfterViewInit(): void {
    this.subscription = this.tourService.index$.subscribe({
      next: value => this.defineStepTour(value),
      error(){
        alert('Error on AfterViewInit')
      } 
    });
  }



  // @HostListener('window:beforeunload', ['$event'])
  // unloadNotification($event: any) : void {
  //     if (this.editCellService.getSize() > 0 && !this.clickedSubmit) {
  //         $event.returnValue = true;
  //     }
  // }

  /**
 * This function decides what tooltip of the page to activate/show.
 * @param {number} value  the number of the step of the tour currently activated
 * 
 */
  defineStepTour(value: number) : void {
    if (this.linkTourTooltip) {this.linkTourTooltip.close()}
    if (this.editMenuTooltip) {this.editMenuTooltip.close()}
    if (this.sapSafetyStockToolTip) {this.sapSafetyStockToolTip.close()}
    if (this.selectEntryTooltip) {this.selectEntryTooltip.close()}
    if (this.flagTooltip) {this.flagTooltip.close()}
    if (this.commentTooltip) {this.commentTooltip.close()}

    switch(value) {
      case 0:  // material info header group 
        this.visibility.set("materialInfo", true);
        document.getElementById("materialInfoHeaderId")?.scrollIntoView({behavior: 'smooth', inline : 'end', block:'center'})
        break;

      case 1: // material info expand icon
        this.visibility.set("materialInfo", true);
        document.getElementById("materialInfoHeaderId")?.scrollIntoView({behavior: 'smooth', inline : 'end', block:'center'})
        break;

      case 2: // apontar para o link    
        if (!this.visibility.get("inventory")) {this.visibility.set("inventory", true)};
        document.getElementById("openSAPHeader")?.scrollIntoView({behavior: 'smooth', inline : 'start', block:'center'})
        this.linkTourTooltip.open()
        break;

      case 3: // No edit (temos que fazer expand do edit e focus)
        this.visibility.set("edit", true);
        document.getElementById("editMenuTooltip")?.scrollIntoView({behavior: 'smooth', inline : 'start', block:'center'})
        this.editMenuTooltip.open()
        break;
        
      case 4: // new sap safety stock e time
        this.visibility.set("edit", true);
        document.getElementById("sapsafetytimestock")?.focus()
        this.sapSafetyStockToolTip.open()
        break;

      case 5: // select
        this.visibility.set("edit", true);
        document.getElementById("selectEntryTooltipId")?.scrollIntoView({behavior: 'smooth', inline:'start', block:'center'})
        this.selectEntryTooltip.open()
        break;

      case 6: // flag
        this.visibility.set("edit", true);
        document.getElementById("flagTooltipId")?.scrollIntoView({behavior: 'smooth', inline : 'start', block:'center'})
        this.flagTooltip.open()
        break;

      case 7: // Comment
        this.visibility.set("edit", true);
        document.getElementById("commentTooltip")?.scrollIntoView({behavior: 'smooth', inline : 'start', block:'center'})
        this.commentTooltip.open()
        break;

      default:
        break;
    }
  }

  /**
   * Function that executes when the component is destroyed. Unsubscribes from the subscription property
   */
  ngOnDestroy(): void {
      this.subscription.unsubscribe()
  }

  /**
   * This function starts the tourService on input by the user
   */
  startTour() : void {
    this.tourService.start();
  }

  /**
   * Function starts a timer, after which, it resets the alertMessage value to "n/a"
   */
  autoDismissAlert() : void {
    setTimeout(() => {
      this.alertMessage="n/a";
    }, 5000); // 5000ms = 5 seconds
  }


  /**
   * Upon input by the user, it sends the checkbox value to editCellService
   * @param {any} event 
   */
  checkUncheckAll(event:any): void {
    this.editCellService.checkUncheckAll(event);
    event.stopPropagation();
  }


  /**
   * This function checks if the row in question is selected or not, changing the className of the row accordingly
   * @param {number} id - id of the Material shown in the row in question
   * @returns a class name
   */
  rowState(id :number): string {
    if (this.editCellService.hasMaterial(id) && this.editCellService.getMaterial(id)?.selected) {
      return "selected-row"
    }
    else {
      return "normal-row"
    }
  }


  /**
   * Function that calculates the className a cell.
   * @param {string} groupName name of the column group
   * @param {IMaterial} material Material name , optional value
   * @param {string} editColName column name, optional value
   * @returns a string value with the className that needs to be assigned to a certain cell
   */
  editCellClasses(groupName : string,material? : IMaterial,editColName? : string) : string {
    let returnVal = ""
    switch (groupName){
      case "materialInfo":
        if (this.visibility.get('materialInfo') === false){
          returnVal = "tableHide "
        }
        break;
      case "edit":
        if (this.visibility.get('edit') === false){
          returnVal = "tableHide "
        }
        if(material !== undefined && editColName !== undefined){
            returnVal += this.chooseColor(material,editColName);
        }
        break;
      case "supplierDelay":
        if (this.visibility.get('supplierDelay') === false){
          returnVal = "tableHide "
        }
        break;
      case "safetyStock":
        if (this.visibility.get('safetyStock') === false){
          returnVal = "tableHide "
        }
        break;
      case "safetyTime":
        if (this.visibility.get('safetyTime') === false){
          returnVal = "tableHide "
        }
        break;
      case "inventory":
        if (this.visibility.get('inventory') === false){
          returnVal = "tableHide "
        }
        break;
    }
    return returnVal
  }
  
  /**
   * Function that assigns a className for a certain cell based on checks regarding the values of the cell.
   * @param {IMaterial} material material being checked
   * @param {string} columnName name of the column
   * @returns a className
   */
  chooseColor(material : IMaterial , columnName : string) : string {
    let returnVal = ""
    switch (columnName){
      case ("SST"):
        if (this.editCellService.hasMaterial(material.id) && (this.editCellService.getMaterial(material.id)?.newSST !== material.newSAPSafetyStock)){
          returnVal = "textBlue"
        }
        else {
          returnVal= "textBlack"
        }
        break;
      case ("ST"):
        if (this.editCellService.hasMaterial(material.id) && (this.editCellService.getMaterial(material.id)?.newST !== material.newSAPSafetyTime)){
          returnVal = "textBlue"
        }
        else {
          returnVal= "textBlack"
        }
        break;
      case ("Comment"):
        if (this.editCellService.hasMaterial(material.id) && (this.editCellService.getMaterial(material.id)?.newComment !== material.comment)){
          returnVal = "textBlue"
        }
        else {
          returnVal = "textBlack"
        }
        break;

    }
    return returnVal
  }

  /**
   * Function that generates a placeholder (value shown in the input bar) value for a editable cell.
   * @param {string} columnName name of the column
   * @param {IMaterial} material 
   * @returns 
   */
  placeholderGeneratorNum(columnName : string, material : IMaterial) : number {
    let returnVal = 0;
    const editCell : IEditCell | undefined = this.editCellService.getMaterial(material.id)
    if (columnName === "SST"){
      if ((editCell !== undefined && (this.editCellService.getMaterial(material.id)?.newSST !== material.newSAPSafetyStock ))){
        returnVal = editCell.newSST
      }
      else if (material.newSAPSafetyStock) { returnVal = material.newSAPSafetyStock};
    }
    else if(columnName === "ST"){
      if ((editCell !== undefined && (this.editCellService.getMaterial(material.id)?.newST !== material.newSAPSafetyTime ))){
        returnVal = editCell.newST
      }
      else if (material.newSAPSafetyTime) {returnVal = material.newSAPSafetyTime};
    }
    return returnVal;
  }

  /**
   * Placeholder generator for the comment cell in the edit column group
   * @param {IMaterial} material
   * @returns 
   */
  placeholderGeneratorString(material : IMaterial) : string {
    let returnVal = "";
    const editCell : IEditCell | undefined = this.editCellService.getMaterial(material.id)
    if ((editCell !== undefined && (editCell.newComment !== material.comment ))){
      returnVal = editCell.newComment ?? ""
    }
    else if (material.comment) {returnVal = material.comment};

    return returnVal;
  }

  /**
   * Navigates to the changes page after a submission
   */
  routeToChangesPage() : void {
    this.router.navigate(['/changes-page']);
  }

  /**
   * Function that converts monetary values to a certain currency. If the value
   * @param {number} original original monetary value
   * @param {Coin} currency currency assigned to the line in question
   * @returns the converted value to be shown in the cell
   */
  currencyConverter(original: number | undefined | null, currency: Coin | null | undefined) : number {
    let returnVal = 0;
    if (original !== null && original !== undefined && currency !== null && currency !== undefined){
    returnVal = original;
    if (this.currencyEUR) {
      if (currency !== "EUR"){
        const rate = this.currencyExchangeRates.get(currency);
        if (rate !== undefined) {returnVal = (Math.round(original*rate*100))/100};
      }
    }}
    return returnVal;
  }

  /**
   * Function that chooses which value to shown in the cells in the edit column group. 
   * @param {string} columnName name of the column the cell belongs to
   * @param {IMaterial} material the material the cell belongs to
   * @returns 
   */
  cellValueGenerator(columnName : string, material : IMaterial) : number {

    let returnVal = 0;
    const editCell : IEditCell | undefined = this.editCellService.getMaterial(material.id)
  
    if (columnName === "SST"){
      if(editCell!==undefined && (editCell.newSST) !== material.proposedSST) {returnVal = editCell.newSST}
      else if ( material.newSAPSafetyStock && material.newSAPSafetyStock !== -1 ){
        returnVal = material.newSAPSafetyStock;
      }
      else if(material.proposedSST){
        returnVal = material.proposedSST
      }
    }
    else if (columnName === "ST"){
      if(editCell!==undefined && (editCell.newST) !== material.proposedST) {returnVal = editCell.newST}
      else if ( material.newSAPSafetyTime && material.newSAPSafetyTime !== -1 ){
        returnVal = material.newSAPSafetyTime;
      }
      else if(material.proposedST){
        returnVal = material.proposedST
      }
    }
    return returnVal;
  }

  /**
   * Function responsible for submitting the selected changes to the back-end
   */
  submitToSAP() : void{
    const list = this.editCellService.mapToSubmit();
    this.clickedSubmit = true;
    this.materialService.submitChanges(list).subscribe({
      next: (res) => {
        this.createAndShowDownloadFile(res, "DataChanged.xlsx", "application/vnd.ms-excel");
        this.load();
        this.alertMessage = "DATA WAS SUBMITTED SUCCESSFULLY";
        this.autoDismissAlert();
        this.routeToChangesPage();
      },
      error(){
        alert('Error on Submit')
      } 
    });
  };


  /**
   * Function responsabile for processing all the String messages received from the various emmitters used in the components that are in the material component
   * @param {string} messageText message received
   */
  receiveStringEvent(messageText : string) : void{
    if (messageText === "load"){
      this.load();
    }
    if (messageText === "Submit"){
      this.submitToSAP()
    }
    if (messageText === "Download"){
      this.materialService.exportFileAsExcel().subscribe({
        next: (res) => {
          this.createAndShowDownloadFile(res, "download.xlsx", "application/vnd.ms-excel");
        },
        error(){
          alert('Error on Submit')
        } 
      });  }
    if (messageText === "Undo"){
      this.undo()
    }
    if (messageText === "Check unselected"){
      this.resetFilters();
      this.receiveSpecialFilter("Unselected");
    }
  }

  /**
   * Function that asigns to the currencyEur variable the value received from the emitter
   * @param {boolean} event boolean received from an emitter in the jhi-options-bar component
   */
  receiveCurrencyVal(event : boolean) : void{
    this.currencyEUR = event;
  }


  /**
   * Function that asigns to the currencyEur variable the value received from the emitter
   * @param event - boolean received from an emitter in the jhi-options-bar component
   */
  receiveComment(event : any) : void{
    this.input(event,"newComment",event.id)
  }

  /**
   * Function that chooses the comment to be usend in the comment-modal component
   * @param {IMaterial} material
   * @returns either the comment present in the back end or the edited value 
   */
  pickComment(material : IMaterial) : string {
    return (((this.editCellService.hasMaterial(material.id) && (this.editCellService.getMaterial(material.id)?.newComment !== material.comment))) ? this.editCellService.getMaterial(material.id)?.newComment : material.comment) ?? ""
  }

  /**
   * Function responsible for the download a excel file
   * @param {any} content 
   * @param {string} fileName 
   * @param {string} contentType 
   */
  createAndShowDownloadFile = (content: any, fileName: string, contentType: string): void => {
    const a = document.createElement('a');
    const file = new Blob([content], { type: contentType });
    a.href = URL.createObjectURL(file);
    a.download = fileName;
    a.click();
    this.alertMessage="DATA DOWNLOAD INITIATED"
    this.autoDismissAlert();
  };


  /**
   * Function that receives the message sent by the emitter in the jhi-filter-display-cell component. It removes the filter passed as parameter
   * @param {IFilterOption} filter filter to be removed
   */
  receiveFilterRemoveMessage (filter : IFilterOption) : void{
    for(const value of filter.values){
      this.filters.removeFilter(filter.name, value);
    }
  }

  /**
   * Function that receives the message sent by the emitter in the jhi-filter-display-cell component. It removes the special filter passed as parameter.
   * @param spFilter 
   */
  receiveSpFilterRemoveMessage(spFilter : specialFilter) : void {
    this.specialFiltersList.forEach((actSpFilter) => {
      if (actSpFilter.name === spFilter.name){
        actSpFilter.isActive = false
        actSpFilter.idList = []
      }
    })

    this.applySpecialFilters()
  }

  /**
   * Function that receives the message sent by the emitter in the jhi-options-bar component. It adds a filter in whatever column name has been sent.
   * @param {any} event message received, contains the filter name and the filter value, in this case, text  
   */
  receiveTextFilter(event : any) : void{
    const filterName: string = event.filterName;
    const filterValue: string = event.filterText;
    if(filterValue === ''){
      this.filters.removeAllFiltersName("material.contains");
      this.filters.removeAllFiltersName("description.contains");
    }
    else{
      switch (filterName){
        case "Material Name":
          this.filters.removeAllFiltersName("material.contains")
          this.filters.addFilter("material.contains", filterValue);
          break;
        case "Material Description":
          this.filters.removeAllFiltersName("description.contains")
          this.filters.addFilter("description.contains",filterValue);
          break;
        case "Plant":
          this.filters.removeAllFiltersName("plant.equals")
          this.filters.addFilter("plant.equals",filterValue);
          break;
        case "MRP Controller":
          this.filters.removeAllFiltersName("mrpController.contains")
          this.filters.addFilter("mrpController.contains",filterValue);
          break;
        case "Selected Materials":
          this.receiveSpecialFilter(filterValue)
          break;
        case "Flagged Materials":
          this.receiveSpecialFilter(filterValue)
          break;
      }
    }
    this.load();
  }

  /**
   * Function that adds a special filter to the list of filters, according to a string value sent by an emitter in the jhi-options-bar component
   * @param {string} filterOp specifies the type of filter 
   */
  receiveSpecialFilter(filterOp:string) : void{
    this.specialFiltersList.forEach((actSpFilter) => {
      if (actSpFilter.name === filterOp){
        actSpFilter.isActive = true
        if(filterOp !== "Flagged" && filterOp !== "Unflagged"){
          actSpFilter.idList = this.getFilteredList(filterOp)
        }
      }
      // By activating Unedited, Selected and Unselected are deactivated
      if (filterOp === "Unedited" && actSpFilter.name !== filterOp){
        actSpFilter.isActive = false
        actSpFilter.idList = []
      }
      // By activating either Selected or Unselected, deactivate Unedited
      if ((filterOp === "Selected" || filterOp === "Unselected") && actSpFilter.name === "Unedited"){
        actSpFilter.isActive = false
        actSpFilter.idList = []
      }
      // Deactivate Flagged when Unflagged is received and vice versa
      if(filterOp === "Flagged" && actSpFilter.name === "Unflagged") {actSpFilter.isActive = false}
      if(filterOp === "Unflagged" && actSpFilter.name === "Flagged") {actSpFilter.isActive = false}
    })
    this.applySpecialFilters()
  }


  /**
   * Function that receives a message sent by an emitter on the jhi-options-bar component and, depending on the value of menuName, either changes the cap of undo or the size of the page.
   * @param {any} event message sent by the emitter
   */
  receiveDropdownNumber(event : any) : void{
    if ( event.menuName === "undo"){
      this.undoSize = event.menuValue;
      this.undoChangeSize();
    }
    else{
      this.itemsPerPage = event.menuValue;
      this.load()
    }
  }

  /**
   * Function that adds so called special filters
   */
  applySpecialFilters() : void{
    this.filters.removeAllFiltersName("flagMaterial.equals")
    this.filters.removeAllFiltersName("id.in")
    this.filters.removeAllFiltersName("id.notIn")

    this.specialFiltersList.forEach((spFilter) => {
      if(spFilter.isActive){
        if(spFilter.name === "Flagged"){
          this.filters.addFilter("flagMaterial.equals","true");
        }
        else if (spFilter.name === "Unflagged"){
          this.filters.addFilter("flagMaterial.equals","false");
        }
        else{
            let filterStr = "id.in"
            if(spFilter.name === "Unedited"){
              filterStr = "id.notIn"
            }

            spFilter.idList.forEach((num) => {
              this.filters.addFilter(filterStr,num.toString());
            })
        }
      }
    })
  }

  /**
   * Function that executes the getFilteredList function in the editCellService service
   * @param {string} filterOp 
   * @returns list of selected material's ids
   */
  getFilteredList(filterOp : string): number[] {
    return this.editCellService.getFilteredList(filterOp)
  }

  /**
   * Function that assesses the current flag value of a material to be passed to the jhi-flag-modal component
   * @param {number} id id of the material the cell refers to
   * @returns current flag value (edited or not)
   */
  getFlagVal(id : number) : boolean{
    let returnVal = false;
		const editedMaterial = this.editCellService.getMaterial(id);
    if (editedMaterial !== undefined){
      returnVal = editedMaterial.flag
    }
    else {
      const matValue = this.materials?.find(e => e.id === id)?.flagMaterial;
      if (matValue !== undefined && matValue !== null){
        returnVal = matValue
      }
    }
    return returnVal;
  }

  /**
   * Function that receives a message from an emitter on the jhi-options-bar component. The received message is used to add a filter refering to a numeric operator and a numeric column
   * @param {any} event received message 
   */
  receiveNumberFilter(event : any) : void{
    const filterName: string = event.filterName;

    const submitName: string = filterName + "." + event.operator
    this.filters.removeAllFiltersName(submitName)
    this.filters.addFilter(submitName, event.value);

    this.load();
  }

  /**
   * Function that receives a message from an emitter on the jhi-options-bar component. The received message is used to activate the upload of the file either with the intent of replacing the data or adding to the already existing data
   * @param {any} event contains the opType boolean (true for replace and false for add) and the file itself
   */
  onFileSelected(event:any): void {
    // true -> replace
    // false -> add
    const file : File = event.file ;
    const typeReplace : boolean = event.opType;
    
    if (typeReplace) {
      this.alertMessage="DATA BEING REPLACED, PLEASE AWAIT TABLE REFRESH"
      this.materialService.uploadFileReplace(file).subscribe({
        next: (response: HttpResponse<{}>) => {
          // Check the response status or other properties as needed
          if (response.status === 200) {
            this.alertMessage = "n/a";
            this.load();
            this.alertMessage = "DATA REPLACED SUCCESSFULLY";
            this.autoDismissAlert();
          }
        },
        error: () => {
          // alert("Error Uploading File");
          this.alertMessage = "ERROR UPLOADING FILE";
          this.autoDismissAlert();
        }
      });
    }
    else{
      this.alertMessage="DATA BEING ADDED, PLEASE AWAIT TABLE REFRESH"
      this.materialService.uploadFileAddOrUpdate(file).subscribe({
        next: (response: HttpResponse<{}>) => {
          if (response.status === 200) {
            this.alertMessage = "n/a";
            this.load();
            this.alertMessage = "DATA REPLACED SUCCESSFULLY";
            this.autoDismissAlert();
          }
        },error: () => {
          this.alertMessage = "ERROR UPLOADING FILE";
          this.autoDismissAlert();
        }
      });
    }
  }

  /**
   * Function that changes the value of the property isEditable as to make a certain cell of the table editable for the user
   * @param {number} a row 
   * @param {number} b column
   */
  makeEditable(a: number, b: number) : void{
    this._isEditable = [a,b];

  }



  /**
   * Function responsible for calculating the new average. If the row has not been edited, the value is the same as the one from the Backend, if it isn't, further calculations are needed and applied in this function.
   * @param {IMaterial} material 
   * @returns the result of the calculation of the new Average
   */
  calcNewValueAvg(material : IMaterial) : number {
    const editCell: IEditCell | undefined = this.editCellService.getMaterial(material.id)

    if (editCell) {
      const newDeltaSST = (editCell.newSST ) - (material.currSAPSafetyStock ?? 1);
      const newDeltaST = (editCell.newST) - (material.currentSAPSafeTime ?? 1);
      const unitCost = material.unitCost ?? 1
      return Number((newDeltaSST * unitCost + newDeltaST * unitCost * (material.avgDemand ?? 1)).toFixed(2));
    }

    else{ return Number((material.avgInventoryEffectAfterChange ?? 1).toFixed(2))};
  }

  /**
   * Function that is auxilary to the undo operation. It checks if the lastEntry being reverted means that the edited row reverts to it's original state
   * @param {IHistoryEntity} lastEntry 
   * @returns true or false, depending if the undo reverts the row to it's original state or not
   */
  checkEditCell(lastEntry : IHistoryEntity): boolean{
    const materialValue : IMaterial | undefined = this.materials?.find(e => e.id === lastEntry.materialId);
    const editCell: IEditCell | undefined = this.editCellService.getMaterial(lastEntry.materialId)
    switch (lastEntry.column){
      case "newSST":
        if (materialValue?.newSAPSafetyStock === lastEntry.oldValue){
          return (editCell?.newST !== materialValue.newSAPSafetyTime) || (editCell?.newComment !== materialValue.comment) || (editCell?.newFlag !== materialValue.flagMaterial)
        }
        break;
        
      case "newST":
        if (materialValue?.newSAPSafetyTime === lastEntry.oldValue){
          return (editCell?.newSST !== materialValue.newSAPSafetyStock) || (editCell?.newComment !== materialValue.comment) || (editCell?.newFlag !== materialValue.flagMaterial)
        }
        break;

      case "newComment":
        if (materialValue?.comment === lastEntry.oldValue){
          return (editCell?.newSST !== materialValue.newSAPSafetyStock) || (editCell?.newST !== materialValue.newSAPSafetyTime) || (editCell?.newFlag !== materialValue.flagMaterial)
        }
        break;
      
      default:
        return false;
    }
    return true;
  }




  /**
   * Function that creates an EditCell with the previous History values
   * @param lastEntry Most recent History entry
   */
  undoAux(lastEntry : IHistoryEntity) : void { 
    const editCell = this.createEditCell(lastEntry.materialId);
    if (lastEntry.column === "newSST" && typeof lastEntry.oldValue === "number"){
      // editCell.oldSST = editCell.newSST
      editCell.newSST = lastEntry.oldValue;
    }
    else if (lastEntry.column === "newST" && typeof lastEntry.oldValue === "number"){
      // editCell.oldST = editCell.newST
      editCell.newST = lastEntry.oldValue;
    }
    else if (lastEntry.column === "newComment" && typeof lastEntry.oldValue === "string"){
      // editCell.oldComment = editCell.newComment
      editCell.newComment = lastEntry.oldValue;
    }

    // checks if the edit cell is different from the material
    if(this.isEditCellDiffMaterial(editCell, lastEntry.materialId)) {
      this.editCellService.addMaterial(lastEntry.materialId, editCell)
    }
    else {
      this.editCellService.removeMaterial(lastEntry.materialId)
    }
    
  }


  /**
   * Function responsible for undoing an edit made, it starts by taking the oldest change currently in the History and then checks if that change being reverted means that the edited row reverts to it's original state. If it does, then that row must be reverted
   */
  undo() : void{
    if(this.history.length > 0){
      const lastEntry = this.history.pop();
      if(lastEntry){
        const editCell: IEditCell | undefined = this.editCellService.getMaterial(lastEntry.materialId)
        if(editCell){
          const checkEditCell = this.checkEditCell(lastEntry)
          switch(lastEntry.column){
            case "newSST":
              if(checkEditCell){
                if (typeof lastEntry.oldValue === "number") {
                  editCell.newSST = lastEntry.oldValue;
                  this.editCellService.addMaterial(lastEntry.materialId, editCell);
                }
              }
              else {
                this.editCellService.removeMaterial(editCell.materialId);
              }
              break;
            case "newST":
              if(checkEditCell){
                if ( typeof lastEntry.oldValue === "number"){
                  editCell.newST = lastEntry.oldValue;
                  this.editCellService.addMaterial(lastEntry.materialId, editCell);
                }
              }
              else {
                this.editCellService.removeMaterial(editCell.materialId);
              }
              break;
            case "newComment":
              if(checkEditCell){
                if ( typeof lastEntry.oldValue === "string"){
                  editCell.newComment = lastEntry.oldValue;
                  this.editCellService.addMaterial(lastEntry.materialId, editCell);
                }
              }
              else {
                this.editCellService.removeMaterial(editCell.materialId);
              }
              break;
          }
        }
        else{
          this.undoAux(lastEntry);
        }
      }
    }
  }

  /**
   * Function responsible for the changes that happen after a change in the size of the undo log.
   */
  undoChangeSize() : void{
    if(this.undoSize < this.history.length){
      for(let i = 0 ; i<this.history.length-this.undoSize;i++){
        this.history.shift()
      }
      this.alertMessage="UNDO SIZE CHANGED, OLDER STEPS WERE LOST"
      this.autoDismissAlert();
    }
    else{
      this.alertMessage="UNDO SIZE CHANGED, NO OLDER STEPS WERE LOST"
      this.autoDismissAlert();
    }
  }


  /**
   * Function that adds an edit to the History, it creates an instance of IHistoryEntity that gets pushed to the history property.
   * @param {string} col_name 
   * @param {number | string} new_value 
   * @param {number | string} old_value 
   * @param {number} material_id 
   */
  addToHistory(col_name : string, new_value : number | string, old_value : number | string, material_id : number) : void{
    // - if existe no history && nome da coluna está la
    //   adicionar ao índice e alterar new e old
    // - else
    //   criar nova entrada no history com indice a 0

    // check if value already exists in history, and if it does, update it
    const index = this.history.findIndex((historyEntity) => {historyEntity.materialId === material_id && historyEntity.column === col_name})
    // if it doesn't exist, create a new entry
    if(index === -1){
      const newEntry = <IHistoryEntity>{materialId : material_id, column : col_name,
                                      oldValue : old_value , currentValue : new_value};
      this.history.push(newEntry);
    }
    else{
      const newEntry = <IHistoryEntity>{}; // mudar old value
      newEntry.materialId = material_id;
      newEntry.oldValue = this.history[index].currentValue;
      newEntry.currentValue = new_value;
    }
    if(this.history.length > this.undoSize){
      this.history.shift();
    }

  }

  /**
   * Function that creates a EditCell 
   * @param {number} id Material ID
   * @returns created EditCell eith Material values
   */
  createEditCell(id: number): IEditCell{
    const editCell = <IEditCell>{};
    const material = this.materials?.find(e => e.id === id)
    editCell.materialId = material?.id ?? -1;
    editCell.materialName = material?.material ?? "";
    editCell.materialDesc = material?.description ?? "";
    editCell.abcClassification = material?.abcClassification ?? ABCClassification.A;
    editCell.plant = material?.plant ?? -1;
    editCell.mrpcontroller = material?.mrpController ?? "";
    editCell.newSST = material?.newSAPSafetyStock ?? -1;
    editCell.oldSST = editCell.newSST;
    editCell.newST = material?.newSAPSafetyTime ?? -1;
    editCell.oldST = editCell.newST;
    editCell.newComment = material?.comment ?? null;
    editCell.oldComment = editCell.newComment;
    editCell.selected = false;
    editCell.flag = material?.flagMaterial ?? false;
    editCell.newFlag = editCell.flag

    return editCell
  }

  /**
   * Function that checks if the user input is different from the original Material value
   * @param {any} event event that contains the new value to compare
   * @param {string} col_name Column name to compare
   * @param {number} id Material ID
   */
  isOldDiffNew(event: any , col_name : string, id : number): boolean {
    let returnValue : boolean = false;
    const material = this.materials?.find(e => e.id === id)
    switch (col_name){
      case "newSST" : 
        if (material?.newSAPSafetyStock !== Math.round(event.target.value)){
          returnValue = true; 
        }
        break;
      case "newST" : 
        if (material?.newSAPSafetyTime !== Math.round(event.target.value)){
          returnValue = true; 
        }
        break;
      case "newComment" : 
        if (material?.comment !== event.newComment){
          returnValue = true; 
        }
        break;
      case "flag" : 
        if (material?.flagMaterial !== event.flag){
          returnValue = true; 
        }
        break;
    }
    return returnValue
  }


  /**
   * Function that checks if the edit cell content is equal to the original material
   * @param {IEditCell} editCell Editcell variable to compare
   * @param {number} id Material ID to compare
   */
  isEditCellDiffMaterial (editCell: IEditCell, id: number): boolean {
    const material = this.materials?.find(e => e.id === id);

    return (editCell.newST !== material?.newSAPSafetyTime) || 
           (editCell.newSST !== material.newSAPSafetyStock) ||
           (editCell.newComment !== material.comment) || 
           (editCell.newFlag !== material.flagMaterial)
  }

  /**
   * Function that is responsible for receiving and treating any input that the user makes in any editable cell. If necessary, it creates an IEditCell that gets store via the addMaterial function in add
   * @param {any} event input received
   * @param {string} col_name name of the column of the input
   * @param {number} id id of the material that was changed
   */
  input(event: any, col_name : string, id: number): void {
    let editCell: IEditCell | undefined;
    let oldValue: number | string = 0;
    if (this.editCellService.hasMaterial(id)){
      editCell = this.editCellService.getMaterial(id);
    }
    else if (this.isOldDiffNew(event, col_name,id)){
      editCell = this.createEditCell(id)
    }

    if (editCell !== undefined){
      if (col_name === "newSST"){
        oldValue = editCell.newSST
        editCell.oldSST = editCell.newSST;
        editCell.newSST = Math.round(event.target.value);
      }
      if (col_name === "newST"){
        oldValue = editCell.newST;
        editCell.oldST = editCell.newST;
        editCell.newST = Math.round(event.target.value);
      }
      if (col_name === "newComment"){
        oldValue = editCell.newComment ?? "";
        editCell.oldComment = editCell.newComment;
        editCell.newComment = event.newComment;
      }
      if (col_name === "selected") {
        editCell.selected = event.target.checked;
      }
      if (col_name === "flag") {
        editCell.newFlag = event.flag
        if (event.flag) {
          editCell.dateFlag = event.date;
        }
        else {
          editCell.dateFlag = "n/a"
        }
      }

      // add to history
      if (col_name === "newComment"){
        this.addToHistory(col_name,event.newComment, oldValue, id);
      } 
      else if(col_name !== "flag"){
        this.addToHistory(col_name, Math.round(Number(event.target.value)), oldValue, id);
      } 

      // checks if the edit cell is different from the material
      if(this.isEditCellDiffMaterial(editCell, id)) {
        this.editCellService.addMaterial(id, editCell)
      }
      else {
        this.editCellService.removeMaterial(id)
      }
    }
    this._isEditable = [-1,-1]

  }

  /**
   * Function responsible for switching the visibility value of a column group based on user input
   * @param {any} event 
   */
  switchVisibility(event: any) : void {
    const col_name = event.name;
    if (this.visibility.get(col_name) === false){
      this.visibility.set(col_name, true)
    }
    else {
      this.visibility.set(col_name, false)
    }
  }

  /**
   * Function that executes the getUnselectedLines in the editCellService service, getting the number of lines that have been edited but not selected
   * @returns number of unselected lines
   */
  getUnselectedLines() : number{
      return this.editCellService.getUnselectedLines();
  }

  /**
   * Function that adds a filter in the ABCClassification Column
   * @param {any} event
   */
  filterABCClassif(event : any): void{
    if(event.opType){
      this.filters.addFilter("abcClassification.in", event.filterValue);
    }
    else{
      this.filters.removeFilter("abcClassification.in", event.filterValue);
    }
  }

  /**
   * Builder for the SAP link that is accessible through each cell in the dedicated column
   * @param materialID
   * @returns the resulting link
   */
  sapLinkBuilder (materialID : number) : string {
    let resultValue = "https://rb3p72a4.server.bosch.com:44300/sap/bc/gui/sap/its/webgui?~transaction=md04"
    if(this.materials !== undefined){
      const materialValue = this.materials?.find(e => e.id === materialID)
      if (materialValue !== undefined){
        resultValue += "&WERKS="+ materialValue.plant?.toString
        resultValue += "&DISPO=" + "dispoName"
        resultValue += "&MATNR=" + materialValue.material?.toString
      }
    }
    return resultValue
  }

  /**
   * Function that resets all the filters upon user request
   */
  resetFilters(): void{
    this.filters.clear();
    this.specialFiltersList.forEach((spFilters) => {
      spFilters.isActive = false;
      spFilters.idList = [];
    })
    this.load();
  }

  /**
   * Function that loads all the materials in the table page
   */
  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  /**
   * Function created by JHipster
   */
  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  /**
   * Function created by JHipster
   */
  navigateToPage(page = this.page): void {
    this.handleNavigation(page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  /**
   * Function created by JHipster
   */
  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending, this.filters.filterOptions))
    );
  }

  /**
   * Function created by JHipster
   */
  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
    this.filters.initializeFromParams(params);
  }

  /**
   * Function created by JHipster
   */
  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.materials = dataFromBody;
  }

  /**
   * Function created by JHipster
   */
  protected fillComponentAttributesFromResponseBody(data: IMaterial[] | null): IMaterial[] {
    return data ?? [];
  }

  /**
   * Function created by JHipster
   */
  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  /**
   * Function created by JHipster
   */
  protected queryBackend(
    page?: number,
    predicate?: string,
    ascending?: boolean,
    filterOptions?: IFilterOption[]
  ): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };
    filterOptions?.forEach(filterOption => {
      queryObject[filterOption.name] = filterOption.values;
    });
    return this.materialService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  /**
   * Function created by JHipster
   */
  protected handleNavigation(page = this.page, predicate?: string, ascending?: boolean, filterOptions?: IFilterOption[]): void {
    const queryParamsObj: any = {
      page,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };

    filterOptions?.forEach(filterOption => {
      queryParamsObj[filterOption.nameAsQueryParam()] = filterOption.values;
    });

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  /**
   * Function created by JHipster
   */
  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }
}
