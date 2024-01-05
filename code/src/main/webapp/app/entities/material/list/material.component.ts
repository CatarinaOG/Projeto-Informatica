import { Component, OnDestroy, OnInit, ViewChild,ViewChildren,HostListener, QueryList, AfterViewInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Observable, Subscription, switchMap, tap } from 'rxjs';
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

import { NgbAlert } from '@ng-bootstrap/ng-bootstrap';
import { ABCClassification } from 'app/entities/enumerations/abc-classification.model';

import { MSG } from '../data/tooltipMsg';
import { currencyExchangeRates } from '../data/currencyExchangeRates';
import { TourService } from '../service/tour.service';
import { tourMessages } from '../data/tourMessage';



@Component({
  selector: 'jhi-material',
  templateUrl: './material.component.html'
})



export class MaterialComponent implements OnInit , OnDestroy , AfterViewInit{

  @ViewChild(NgbAlert, { static: false }) alert!: NgbAlert | undefined;
  @ViewChildren(NgbTooltip) tooltips!: QueryList<NgbTooltip>;
  @ViewChild('t2') linkTourTooltip!: NgbTooltip; 
  @ViewChild('t3') editMenuTooltip!: NgbTooltip; 
  @ViewChild('t4') sapSafetyStockToolTip!: NgbTooltip; 
  @ViewChild('t5') selectEntryTooltip!: NgbTooltip; 
  @ViewChild('t6') flagTooltip!: NgbTooltip; 
  @ViewChild('t7') commentTooltip!: NgbTooltip; 

  alertMessage = "n/a";

  materials?: IMaterial[];
  isLoading = false;
  undoSize = 10;
  predicate = 'id';
  ascending = true;
  filters: IFilterOptions = new FilterOptions();
  currencyEUR  = true;

  history: IHistoryEntity[] = [];
  subscription: Subscription = new Subscription();



  itemsPerPage = 10;// ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  selectedFilterState = false;

  toolTipMsg = MSG;
  currencyExchangeRates = currencyExchangeRates;
  tourMsgs = tourMessages;

  isVisible = true;
  masterSelected=false;
  visibility = new Map<string, boolean>();
  fileName = '';
  firstTime = true;

  clickedSubmit = false;
  message:string | undefined;

  specialFiltersList : specialFilter[] = [
    {name: "Selected", isActive: false, idList:[]},
    {name: "Unselected", isActive: false, idList:[]},
    {name: "Unedited", isActive: false, idList:[]},
    {name: "Flagged", isActive: false, idList:[]},
    {name: "Unflagged", isActive: false, idList:[]},
  ];

  options = {
    autoClose: false,
    keepAfterRouteChange: false
};

  private _isEditable : number[] = [-1,-1];
  get isEditable(): number[] {
    return this._isEditable;
  }
  set isEditable(value: number[]) {
    this._isEditable = [value[0], value[1]]
  }


  constructor(
    protected materialService: MaterialService,
    public editCellService: EditCellService,
    public tourService: TourService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal,
    // private tourService: TourService
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

  @HostListener('window:beforeunload', ['$event'])
    unloadNotification($event: any) : void {
        if (this.editCellService.getSize() > 0 && !this.clickedSubmit) {
            $event.returnValue = true;
        }
    }



  trackId = (_index: number, item: IMaterial): number => this.materialService.getMaterialIdentifier(item);


  ngOnInit(): void {
    this.load();
    this.filters.filterChanges.subscribe(filterOptions => this.handleNavigation(1, this.predicate, this.ascending, filterOptions));
    this.filters.removeAllFiltersName("id.in");
    this.filters.removeAllFiltersName("id.notIn");

  }

  ngAfterViewInit(): void {
    this.subscription = this.tourService.index$.subscribe(value =>  {
      this.defineStepTour(value);
    });
  }


  defineStepTour(value: number) : void {
    this.linkTourTooltip.close()
    this.editMenuTooltip.close()
    this.sapSafetyStockToolTip.close()
    this.selectEntryTooltip.close()
    this.flagTooltip.close()
    this.commentTooltip.close()

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


  ngOnDestroy(): void {
      if (this.editCellService.getSize() > 0) {
        if (!this.clickedSubmit && !confirm("You have unsaved changed. Do you wish to proceed?")){
        }
      }
      this.subscription.unsubscribe()
  }

  startTour() : void {
    this.tourService.start();
  }


  autoDismissAlert() : void {
    setTimeout(() => {
      this.alertMessage="n/a";
    }, 5000); // 5000ms = 5 seconds
  }


  checkUncheckAll(event:any): void {
    this.editCellService.getUncheckAll(event);
    event.stopPropagation();
  }



  func(id :number): string {
    if (this.editCellService.hasMaterial(id) && this.editCellService.getMaterial(id)?.selected) {
      return "selected-row"
    }
    else {
      return "normal-row"
    }
  }

  editCellClasses(colName : string,material? : IMaterial,editColName? : string) : string {
    let returnVal = ""
    if (colName === "materialInfo" && this.visibility.get('materialInfo') === false){
      returnVal = "tableHide "
    }
    else if (colName === "edit" && this.visibility.get('edit') === false){
      returnVal = "tableHide "
    }
    else if (colName === "supplierDelay" && this.visibility.get('supplierDelay') === false){
      returnVal = "tableHide "
    }
    else if (colName === "safetyStock" && this.visibility.get('safetyStock') === false){
      returnVal = "tableHide "
    }
    else if (colName === "safetyTime" && this.visibility.get('safetyTime') === false){
      returnVal = "tableHide "
    }
    else if (colName === "inventory" && this.visibility.get('inventory') === false){
      returnVal = "tableHide "
    }
    else{
      returnVal = ""
      if (colName!== "header"){
        if(material !== undefined && editColName !== undefined){
          returnVal += this.chooseColor(material,editColName);
        }
      }
    }
    return returnVal
  }

  chooseColor(material : IMaterial , valueName : string) : string {
    let returnVal = ""
    switch (valueName){
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

  placeholderGeneratorNum(valueName : string, material : IMaterial) : number {
    let returnVal = 0;
    const editCell : IEditCell | undefined = this.editCellService.getMaterial(material.id)
    if (valueName === "SST"){
      if ((editCell !== undefined && (this.editCellService.getMaterial(material.id)?.newSST !== material.newSAPSafetyStock ))){
        returnVal = editCell.newSST
      }
      else if (material.newSAPSafetyStock) { returnVal = material.newSAPSafetyStock};
    }
    else if(valueName === "ST"){
      if ((editCell !== undefined && (this.editCellService.getMaterial(material.id)?.newST !== material.newSAPSafetyTime ))){
        returnVal = editCell.newST
      }
      else if (material.newSAPSafetyTime) {returnVal = material.newSAPSafetyTime};
    }
    return returnVal;
  }

  placeholderGeneratorString(material : IMaterial) : string {
    let returnVal = "";
    const editCell : IEditCell | undefined = this.editCellService.getMaterial(material.id)
    if ((editCell !== undefined && (editCell.newComment !== material.comment ))){
      returnVal = editCell.newComment ?? ""
    }
    else if (material.comment) {returnVal = material.comment};

    return returnVal;
  }



  routeToChangesPage() : void {
    this.router.navigate(['/changes-page']);
  }





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


  cellValueGenerator(valueName : string, material : IMaterial) : number {

  let returnVal = 0;
  const editCell : IEditCell | undefined = this.editCellService.getMaterial(material.id)

  if (valueName === "SST"){
    if(editCell!==undefined && (editCell.newSST) !== material.proposedSST) {returnVal = editCell.newSST}
    else if ( material.newSAPSafetyStock && material.newSAPSafetyStock !== -1 ){
      returnVal = material.newSAPSafetyStock;
    }
    else if(material.proposedSST){
      returnVal = material.proposedSST
    }
  }
  else if (valueName === "ST"){
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



  submitToSAP() : void{
    const list = this.editCellService.mapToSubmit();
    this.clickedSubmit = true;
    if (list.length === 0) {
      alert("No lines were selected");
    }
    else {
      this.materialService.submitChanges(list).subscribe((res) => {
        this.createAndShowDownloadFile(res, "DataChanged.xlsx", "application/vnd.ms-excel");
        this.load()
        this.alertMessage="DATA WAS SUBMITTED SUCCESSFULLY"
        this.autoDismissAlert();
        this.routeToChangesPage();
      });
      }
    };



  receiveStringEvent(messageText : string) : void{
    this.message = messageText;
    if (this.message === "load"){
      this.load();
    }
    if (this.message === "Submit"){
      this.submitToSAP()
    }
    if (this.message === "Download"){
      this.materialService
      .exportFileAsExcel()
      .subscribe((res) =>
        this.createAndShowDownloadFile(res, "dowload.xlsx", "application/vnd.ms-excel")
      );  }
    if (this.message === "Undo"){
      this.undo()
    }
    if (this.message === "Check unselected"){
      this.resetFilters();
      this.receiveSpecialFilter("Unselected");
    }
  }

  receiveCurrencyVal(event : boolean) : void{
    this.currencyEUR = event;
  }

  receiveComment(event : any) : void{
    this.input(event,"newComment",event.id)
  }

  pickComment(material : IMaterial) : string {
    // let returnVal : string = this.materials?.find(e => e.id === id)?.comment ?? "";
    // if (this.editCellService.getMaterial(id)){
    //   returnVal = this.editCellService.getMaterial(id)?.newComment ?? "";
    // }
    return (((this.editCellService.hasMaterial(material.id) && (this.editCellService.getMaterial(material.id)?.newComment !== material.comment))) ? this.editCellService.getMaterial(material.id)?.newComment : material.comment) ?? ""
  }


  createAndShowDownloadFile = (content: any, fileName: string, contentType: string): void => {
    const a = document.createElement('a');
    const file = new Blob([content], { type: contentType });
    a.href = URL.createObjectURL(file);
    a.download = fileName;
    a.click();
    this.alertMessage="DATA DOWNLOAD INITIATED"
    this.autoDismissAlert();
  };


  receiveFilterRemoveMessage (filter : IFilterOption) : void{
    for(const value of filter.values){
      this.filters.removeFilter(filter.name, value);
    }
  }

  receiveSpFilterRemoveMessage(spFilter : specialFilter) : void {
    this.specialFiltersList.forEach((actSpFilter) => {
      if (actSpFilter.name === spFilter.name){
        actSpFilter.isActive = false
        actSpFilter.idList = []
      }
    })

    this.applySpecialFilters()
  }

  receiveTextFilter(event : any) : void{
    const filterName :string = event.filterName;
    const filterValue : string = event.filterText;
    if(filterValue === ''){
      this.filters.removeAllFiltersName("material.contains");
      this.filters.removeAllFiltersName("description.contains");
    }
    else{
      if (filterName === "Material Name"){
        this.filters.removeAllFiltersName("material.contains")
        this.filters.addFilter("material.contains", filterValue);
      }
      else if (filterName === "Material Description"){
        this.filters.removeAllFiltersName("description.contains")
        this.filters.addFilter("description.contains",filterValue);
      }
      else if (filterName === "Selected Materials" || filterName === "Flagged Materials"){
        this.receiveSpecialFilter(filterValue)
      }
    }
    this.load();
  }

  receiveSpecialFilter(filterOp:string) : void{
    this.specialFiltersList.forEach((actSpFilter) => {
      if (actSpFilter.name === filterOp){
        actSpFilter.isActive = true
        if(filterOp !== "Flagged" && filterOp !== "Unflagged"){
          actSpFilter.idList = this.getSelectedList(filterOp)
        }
      }
      // Ao ativar Unedited, desativar Selected e Unselected
      if (filterOp === "Unedited" && actSpFilter.name !== filterOp){
        actSpFilter.isActive = false
        actSpFilter.idList = []
      }
      // Ao ativar Selected ou Unselected, desativar Unedited
      if ((filterOp === "Selected" || filterOp === "Unselected") && actSpFilter.name === "Unedited"){
        actSpFilter.isActive = false
        actSpFilter.idList = []
      }
      // Desativa Flagged quando Unflagged e Vice-Versa
      if(filterOp === "Flagged" && actSpFilter.name === "Unflagged") {actSpFilter.isActive = false}
      if(filterOp === "Unflagged" && actSpFilter.name === "Flagged") {actSpFilter.isActive = false}
    })

    this.applySpecialFilters()
  }


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

  getSelectedList(filterOp : string): number[] {
    return this.editCellService.getSelectedList(filterOp)
  }


  getFlagVal(id : number) : boolean{
    let returnVal = false;
		const editedMaterial = this.editCellService.getMaterial(id);
    if (editedMaterial !== undefined && editedMaterial !== null){
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

  receiveNumberFilter(event : any) : void{
    const filterName :string = event.filterName;

    const submitName : string = filterName + "." + event.operator
    this.filters.removeAllFiltersName(submitName)
    this.filters.addFilter(submitName, event.value);

    this.load();
  }


  onFileSelected(event:any): void {
    // true -> replace
    // false -> add
    const file : File = event.file ;
    const typeReplace : boolean = event.opType;
    if (file && typeReplace) {
      this.materialService.uploadFileReplace(file).subscribe({
        next: () => {
          this.load()
          this.alertMessage="DATA REPLACED SUCCESSFULLY"
          this.autoDismissAlert();
        },error:() =>{
          alert("Error Uploading File")
        }
      });
    }
    else if (file && !typeReplace){
      this.materialService.uploadFileAddOrUpdate(file).subscribe({
        next: () => {
          this.load()
          this.alertMessage="DATA ADDED SUCCESSFULLY"
          this.autoDismissAlert();
        },error:() =>{
          alert("Error Uploading File")
        }
      });
    }
  }

  makeEditable(a: number, b: number) : void{
    this._isEditable = [a,b];

  }



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


  checkEditCell(lastEntry : IHistoryEntity): boolean{
    const materialValue : IMaterial | undefined = this.materials?.find(e => e.id === lastEntry?.materialId);
    switch (lastEntry.column){
      case "newSST":
        if (materialValue?.newSAPSafetyStock === lastEntry.oldValue){
          return (this.editCellService.getMaterial(lastEntry.materialId)?.newST !== materialValue?.newSAPSafetyTime) || (this.editCellService.getMaterial(lastEntry.materialId)?.newComment !== materialValue?.comment) || (this.editCellService.getMaterial(lastEntry.materialId)?.flag !== materialValue?.flagMaterial)
        }
        break;
        
      case "newST":
        if (materialValue?.newSAPSafetyTime === lastEntry.oldValue){
          return (this.editCellService.getMaterial(lastEntry.materialId)?.newSST !== materialValue?.newSAPSafetyStock) || (this.editCellService.getMaterial(lastEntry.materialId)?.newComment !== materialValue?.comment) || (this.editCellService.getMaterial(lastEntry.materialId)?.flag !== materialValue?.flagMaterial)
        }
        break;

      case "newComment":
        if (materialValue?.comment === lastEntry.oldValue){
          return (this.editCellService.getMaterial(lastEntry.materialId)?.newSST !== materialValue?.newSAPSafetyStock) || (this.editCellService.getMaterial(lastEntry.materialId)?.newST !== materialValue?.newSAPSafetyTime) || (this.editCellService.getMaterial(lastEntry.materialId)?.flag !== materialValue?.flagMaterial)
        }
        break;
      
      default:
        return false;
    }
    return false;
  }


  undo() : void{
    if(this.history.length > 0){
      let lastEntry = this.history.pop();
      if(lastEntry){
        const editCell: IEditCell | undefined = this.editCellService.getMaterial(lastEntry.materialId)
        if(editCell){
          switch(lastEntry.column){
            case "newSST":
              if(this.checkEditCell(lastEntry)){
                if ( typeof lastEntry.oldValue === "number"){
                  editCell.newSST = lastEntry.oldValue;
                  this.editCellService.addMaterial(lastEntry.materialId, editCell);
                }
              }
              else {
                this.editCellService.removeMaterial(editCell.materialId);
              }
              break;
            case "newST":
              if(this.checkEditCell(lastEntry)){
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
              if(this.checkEditCell(lastEntry)){
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
      }
    }
  }

  undoChangeSize(){
    if(this.undoSize < this.history.length){
      for(let i = 0 ; i<this.history.length-this.undoSize;i++){
        this.history.shift()
      }
      this.alertMessage="UNDO SIZE CHANGED, OLDER STEPS WERE LOST"
      this.autoDismissAlert();
    }
    else{
      this.alertMessage="UNDO SIZE CHANGED, NO OLDER STEPS WERE LOST"

    }
  }


  addToHistory(col_name : string, new_value : number | string, old_value : number | string, material_id : number) : void{
    // - if existe no history && nome da coluna está la
    //   adicionar ao índice e alterar new e old
    // - else
    //   criar nova entrada no history com indice a 0

    // check if value already exists in history, and if it does, update it
    let index = this.history.findIndex((historyEntity) => {historyEntity.materialId == material_id && historyEntity.column == col_name})
    // if it doesn't exist, create a new entry
    if(index === -1){
      let newEntry = <IHistoryEntity>{materialId : material_id, column : col_name,
                                      oldValue : old_value , currentValue : new_value};
      this.history.push(newEntry);
    }
    else{
      let newEntry = <IHistoryEntity>{}; // mudar old value
      newEntry.materialId = material_id;
      newEntry.oldValue = this.history[index].currentValue;
      newEntry.currentValue = new_value;
    }
    if(this.history.length > this.undoSize){
      this.history.shift();
    }
  }

  input(event: any, col_name : string, id: number): void {
    let editCell: IEditCell | undefined;
    let oldValue: number | string = 0;
    if (this.editCellService.hasMaterial(id)){
      editCell = this.editCellService.getMaterial(id);
    }
    else{
      editCell = <IEditCell>{};
      const material = this.materials?.find(e => e.id === id)
      editCell.materialId = material?.id ?? -1;
      editCell.materialName = material?.material ?? ""
      editCell.materialDesc = material?.description ?? ""
      editCell.abcClassification = material?.abcClassification ?? ABCClassification.A
      editCell.plant = material?.plant ?? ""
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
    }
    if (editCell){
      if (col_name === "newSST"){
        oldValue = editCell.newSST
        editCell.oldSST = editCell.newSST;
        editCell.newSST = Math.round(event.target.value);
      }
      if (col_name === "newST"){
        //this.openTooltip(toolTip);
        oldValue = editCell.newST;
        editCell.oldST = editCell.newST;
        editCell.newST = Math.round(event.target.value);
      }
      if (col_name === "newComment"){
        oldValue = editCell.newComment ?? "";
        editCell.oldComment = editCell.oldComment;
        editCell.newComment = event.newComment;
      }
      if (col_name === "selected") {
        editCell.selected = event.target.checked;
        //this.selectedMaterials.push(this.materials?.find(e => e.id === id)?.material ?? "");
        // const materialCopy = this.materials?.find(e => e.id=== id);
        // if (materialCopy !== undefined){
        //   this.selectedMaterials.push(materialCopy);
        //   console.log("Seleted List", this.selectedMaterials)
        // }
      }
      if (col_name === "flag") {
        editCell.newFlag = event.flag
        if (event.flag) {
          editCell.dateFlag = event.date;
        }
      }
      this.editCellService.addMaterial(id, editCell)
      this._isEditable = [-1,-1]
      if (col_name === "newComment") this.addToHistory(col_name,event.newComment, oldValue, id);
      else if(col_name !== "flag") this.addToHistory(col_name, Math.round(Number(event.target.value)), oldValue, id);
    }
  }

  // openTooltip(toolTip: NgbTooltip | undefined) {
  //   toolTip?.open();
  // }


  // filterSelected(){

  //   // this.selectedFilterState = true;
  //   // this.totalItems = this.selectedMaterials.length;
  //   // this.materials = this.selectedMaterials.splice(0,5);
  //   // this.page = 1;
  // }


  switchVisibility(event: any) : void {
    var col_name = event.name;
    if (this.visibility.get(col_name) == false){
      this.visibility.set(col_name, true)
    }
    else {
      this.visibility.set(col_name, false)
    }
  }

  getUnselectedLines() : number{
      return this.editCellService.getUnselectedLines();
  }






  filterABCClassif(event : any): void{
    if(event.opType){
      this.filters.addFilter("abcClassification.in", event.filterValue);
    }
    else{
      this.filters.removeFilter("abcClassification.in", event.filterValue);
    }
  }


  sapLinkBuilder (materialID : number) : string {
    //https://rb3p72a4.server.bosch.com:44300/sap/bc/gui/sap/its/webgui?~transaction=md04&WERKS=208M&DISPO=208M&MATNR=012.1B0.0664-02
    let resultValue = "https://rb3p72a4.server.bosch.com:44300/sap/bc/gui/sap/its/webgui?~transaction=md04"
    if(this.materials !== null && this.materials !== undefined){
      let materialValue = this.materials?.find(e => e.id == materialID)
      if (materialValue !==null && materialValue !== undefined){
        resultValue += "&WERKS="+ "werksname"
        resultValue += "&DISPO=" + "dispoName"
        resultValue += "&MATNR=" + materialValue.material
      }
    }

    return resultValue
  }

  resetFilters(): void{
    this.filters.clear();
    this.specialFiltersList.forEach((spFilters) => {
      spFilters.isActive = false;
      spFilters.idList = [];
    })
    this.load();
  }


  // delete(material: IMaterial): void {
  //   const modalRef = this.modalService.open(MaterialDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
  //   modalRef.componentInstance.material = material;
  //   // unsubscribe not needed because closed completes on modal close
  //   modalRef.closed
  //     .pipe(
  //       filter(reason => reason === ITEM_DELETED_EVENT),
  //       switchMap(() => this.loadFromBackendWithRouteInformations())
  //     )
  //     .subscribe({
  //       next: (res: EntityArrayResponseType) => {
  //         this.onResponseSuccess(res);
  //       },error:(error:any) =>{
  //         alert("Error Deleting")
  //       }
  //     });
  // }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },error:(error:any) =>{
        alert("Error Loading")
      }
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  navigateToPage(page = this.page): void {
     this.handleNavigation(page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending, this.filters.filterOptions))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
    this.filters.initializeFromParams(params);
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.materials = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IMaterial[] | null): IMaterial[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

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

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }
}