import { Component, OnDestroy, OnInit, ViewChild,ViewChildren,HostListener, QueryList } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, last, Observable, switchMap, tap } from 'rxjs';
import { NgbModal, NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { IEditCell } from '../editCell.model'
import { specialFilter } from '../specialFilters.model'
import { IMaterial } from '../material.model';
import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, MaterialService } from '../service/material.service';
import { MaterialDeleteDialogComponent } from '../delete/material-delete-dialog.component';
import { FilterOptions, IFilterOptions, IFilterOption } from 'app/shared/filter/filter.model';
import { IHistoryEntity } from '../historyEntity.model';
import { Coin } from '../../enumerations/coin.model'

import { NgbAlert } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-material',
  templateUrl: './material.component.html'
})



export class MaterialComponent implements OnInit , OnDestroy {

  @ViewChild(NgbAlert, { static: false }) alert!: NgbAlert | undefined;
  @ViewChildren(NgbTooltip) tooltips!: QueryList<NgbTooltip>;

  alertMessage = "n/a";

  materials?: IMaterial[];
  isLoading = false;
  undoSize = 10;
  predicate = 'id';
  ascending = true;
  filters: IFilterOptions = new FilterOptions();
  currencyEUR : boolean = true;

  history: IHistoryEntity[] = [];

  msg = new Map<string, string>([
    ["Material", "Material"],
    ["Material Description", "Material Description"],
    ["ABC Classification", "ABC Classification"],
    ["Plant", "Plant"],
    ["MRP Controller", "MRP Controller"],
    ["Avg Supplier Delay", "Avg Supplier Delay"],
    ["Max Supplier Delay", "Max Supplier Delay"],
    ["Current Sap Safety Stock", "Current Sap Safety Stock"],
    ["Proposed Safety Stock", "Proposed Safety Stock"],
    ["Current Sap Safety Time", "Current Sap Safety Time"],
    ["Proposed Safety Time", "Proposed Safety Time"],
    ["Service Level", "Service Level"],
    ["Open SAP Md 04", "Open SAP Md 04"],
    ["Current Inventory Value", "Current Inventory Value"],
    ["Delta Safety Stock", "Delta Safety Stock"],
    ["Delta Safety Time", "Delta Safety Time"],
    ["Average Inventory Effect After Change", "Average Inventory Effect After Change"],
    ["New SAP Safety Stock", "New SAP Safety Stock"],
    ["New SAP Safety Time", "New SAP Safety Time"],
    ["Select Entries For Change", "Select Entries For Change"],
    ["Flag Material as Special Case", "Flag Material as Special Case"],
    ["Comment", "Comment"],
  ]);


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

  itemsPerPage = 10;//ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  selectedFilterState = false;



  isVisible = true;
  masterSelected=false;
  visibility = new Map<string, boolean>();
  fileName = '';
  firstTime = true;
  linhas = new Map<number,IEditCell>();

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
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
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
    unloadNotification($event: any) {
        if (this.linhas.size > 0) {
            $event.returnValue =true;
        }
    }



  trackId = (_index: number, item: IMaterial): number => this.materialService.getMaterialIdentifier(item);


  ngOnInit(): void {
    this.load();
    this.filters.filterChanges.subscribe(filterOptions => this.handleNavigation(1, this.predicate, this.ascending, filterOptions));
    this.filters.removeAllFiltersName("id.in");
    this.filters.removeAllFiltersName("id.notIn");
  }

  ngOnDestroy(): void {
      if (this.linhas.size > 0) {
        if (!confirm("You have unsaved changed. Do you wish to proceed?")){
        }
      }
  }


  autoDismissAlert() {
    setTimeout(() => {
      this.alertMessage="n/a";
    }, 5000); // 5000ms = 5 seconds
  }


  checkUncheckAll(event:any): void {
    this.linhas.forEach(function(value,key) {
      value.selected = event.target.checked
    })
    event.stopPropagation();

  }

  func(id :number): string {
    if (this.linhas.has(id) && this.linhas.get(id)?.selected) {
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
        if (this.linhas.has(material.id) && (this.linhas.get(material.id)?.newSST !== material.newSAPSafetyStock)){
          returnVal = "textBlue"
        }
        else {
          returnVal= "textBlack"
        }
        break;
      case ("ST"):
        if (this.linhas.has(material.id) && (this.linhas.get(material.id)?.newST !== material.newSAPSafetyTime)){
          returnVal = "textBlue"
        }
        else {
          returnVal= "textBlack"
        }
        break;
      case ("Comment"):
        if (this.linhas.has(material.id) && (this.linhas.get(material.id)?.newComment !== material.comment)){
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
    let editCell : IEditCell | undefined = this.linhas.get(material.id)
    if (valueName === "SST"){
      if ((editCell !== undefined && (this.linhas.get(material.id)?.newSST !== material.newSAPSafetyStock ))){
        returnVal = editCell.newSST
      }
      else if (material.newSAPSafetyStock) returnVal = material.newSAPSafetyStock;
    }
    else if(valueName === "ST"){
      if ((editCell !== undefined && (this.linhas.get(material.id)?.newST !== material.newSAPSafetyTime ))){
        returnVal = editCell.newST
      }
      else if (material.newSAPSafetyTime) returnVal = material.newSAPSafetyTime;
    }
    return returnVal;
  }

  placeholderGeneratorString(material : IMaterial) : string {
    let returnVal = "";
    let editCell : IEditCell | undefined = this.linhas.get(material.id)
    if ((editCell !== undefined && (editCell.newComment !== material.comment ))){
      returnVal = editCell.newComment ?? ""
    }
    else if (material.comment) returnVal = material.comment;

    return returnVal;
  }


  currencyConverter( original : number | undefined | null , currency : Coin | null | undefined) : number{
    let returnVal = 0;
    if (original !== null && original !== undefined && currency !== null && currency !== undefined){
    returnVal = original;
    if (this.currencyEUR){
      if (currency !== "EUR"){
        let rate = this.currencyExchangeRates.get(currency);
        if (rate !== undefined) returnVal = (Math.round(original*rate*100))/100;
      }
    }}
    return returnVal;
  }


  cellValueGenerator(valueName : string, material : IMaterial) : number {

  let returnVal = 0;
  let editCell : IEditCell | undefined = this.linhas.get(material.id)

  if (valueName === "SST"){
    if(editCell!==undefined && (editCell.newSST) !== material.proposedSST) returnVal = editCell.newSST
    else if ( material.newSAPSafetyStock && material.newSAPSafetyStock !== -1 ){
      returnVal = material.newSAPSafetyStock;
    }
    else if(material.proposedSST){
      returnVal = material.proposedSST
    }
  }
  else if (valueName === "ST"){
    if(editCell!==undefined && (editCell.newST) !== material.proposedST) returnVal = editCell.newST
    else if ( material.newSAPSafetyTime && material.newSAPSafetyTime !== -1 ){
      returnVal = material.newSAPSafetyTime;
    }
    else if(material.proposedST){
      returnVal = material.proposedST
    }
  }
  return returnVal;
}


  mapToSubmit(): IEditCell[] {

    const list : IEditCell[] = [];

    this.linhas.forEach((value,key) => {
      if (value.selected){
        list.push(value);
        this.linhas.delete(key);
      }
    })
    return list
  }

  submitToSAP(){
    let list = this.mapToSubmit();
    if (list.length == 0) {
      alert("No lines were selected");
    }
    else {
      this.materialService.submitChanges(list).subscribe((res) => {
        this.createAndShowDownloadFile(res, "DataChanged.xlsx", "application/vnd.ms-excel");
        this.load()
        this.alertMessage="DATA WAS SUBMITTED SUCCESSFULLY"
        this.autoDismissAlert();
      })
      }
    };


  message:string | undefined;

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
      this.receiveSpecialFilter("Unselected");
    }
  }

  receiveCurrencyVal(event : boolean){
    this.currencyEUR = event;
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
    for(let value of filter.values){
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

  receiveSpecialFilter(filterOp:string){
    this.specialFiltersList.forEach((actSpFilter) => {
      if (actSpFilter.name === filterOp){
        actSpFilter.isActive = true
        if(filterOp !== "Flagged" && filterOp !== "Unflagged")
          actSpFilter.idList = this.getSelectedList(filterOp)
      }
      //Ao ativar Unedited, desativar Selected e Unselected
      if (filterOp === "Unedited" && actSpFilter.name !== filterOp){
        actSpFilter.isActive = false
        actSpFilter.idList = []
      }
      //Ao ativar Selected ou Unselected, desativar Unedited
      if ((filterOp === "Selected" || filterOp === "Unselected") && actSpFilter.name === "Unedited"){
        actSpFilter.isActive = false
        actSpFilter.idList = []
      }
      //Desativa Flagged quando Unflagged e Vice-Versa
      if(filterOp === "Flagged" && actSpFilter.name === "Unflagged") actSpFilter.isActive = false
      if(filterOp === "Unflagged" && actSpFilter.name === "Flagged") actSpFilter.isActive = false
    })

    this.applySpecialFilters()
    // let index = -1;
    // if(filterOp === "Selected")
    //   index = 0;
    // else if(filterOp === "Unselected")
    //   index = 1
    // else if(filterOp === "Unedited")
    //   index = 2;
    // this.specialFiltersList[index].isActive = true
    // this.specialFiltersList[index].idList = this.getSelectedList(filterOp)

    // this.applySpecialFilters()
  }


  receiveDropdownNumber(event : any){
    if ( event.menuName === "undo"){
      this.undoSize = event.menuValue;
      this.undoChangeSize();
    }
    else{
      this.itemsPerPage = event.menuValue;
      this.load()
    }
  }

  applySpecialFilters(){
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
          if(spFilter.name === "Unedited")
            filterStr = "id.notIn"

          spFilter.idList.forEach((num) => {
            this.filters.addFilter(filterStr,num.toString());
          })
        }
      }
    })
  }

  getSelectedList(filterOp : string): number[] {
    let res : number[] = []
    this.linhas.forEach((linha) => {
      if(filterOp === "Unedited"){
        res.push(linha.materialId);
      }
      else{
        if (linha.selected && filterOp === "Selected")
          res.push(linha.materialId);
        if (!linha.selected && filterOp === "Unselected")
          res.push(linha.materialId);
      }
    })
    return res;
  }


  receiveFlagEmission(emission : any){
    let editCell: IEditCell | undefined;


    if (this.linhas.has(emission.id)){
      editCell = this.linhas.get(emission.id);
    }
    else{
      editCell = <IEditCell>{};
      editCell.materialId = this.materials?.find(e => e.id == emission.id)?.id ?? -1;
      editCell.newSST = this.materials?.find(e => e.id === emission.id)?.newSAPSafetyStock ?? -1;
      editCell.newST = this.materials?.find(e => e.id === emission.id)?.newSAPSafetyTime ?? -1;
      editCell.newComment = this.materials?.find(e => e.id === emission.id)?.comment ?? null;
      editCell.selected = false;
      editCell.flag = this.materials?.find(e => e.id === emission.id)?.flagMaterial ?? false;
    }
    if (editCell){
      editCell.flag = emission.flag
      if (emission.flag){
        editCell.dateFlag = emission.date;
      }
      this.linhas.set(emission.id, editCell)
    }
  }


  receiveNumberFilter(event : any) : void{
    const filterName :string = event.filterName;

    const submitName : string = filterName + "." + event.operator
    this.filters.removeAllFiltersName(submitName)
    this.filters.addFilter(submitName, event.value);

    this.load();
  }


  onFileSelected(event:any): void {
    //true -> replace
    //false -> add
    const file : File = event.file ;
    const typeReplace : boolean = event.opType;
    if (file && typeReplace) {
      this.materialService.uploadFileReplace(file).subscribe({
        next: (res: any) => {
          this.load()
          this.alertMessage="DATA REPLACED SUCCESSFULLY"
          this.autoDismissAlert();
        },error:(error:any) =>{
          alert("Error Uploading File")
        }
      });
    }
    else if (file && !typeReplace){
      this.materialService.uploadFileAddOrUpdate(file).subscribe({
        next: (res: any) => {
          this.load()
          this.alertMessage="DATA ADDED SUCCESSFULLY"
          this.autoDismissAlert();
        },error:(error:any) =>{
          alert("Error Uploading File")
        }
      });
    }
  }

  makeEditable(a: number, b: number){
    this._isEditable = [a,b];

  }



  calcNewValueAvg(material : IMaterial) : number {
    let editCell: IEditCell | undefined;
    editCell = this.linhas.get(material.id)

    if (editCell){
      const newDeltaSST = (editCell.newSST ?? 1) - (material.currSAPSafetyStock ?? 1);
      const newDeltaST = (editCell.newST ?? 1) - (material.currentSAPSafeTime ?? 1);
      const unitCost = material.unitCost ?? 1
      return Number((newDeltaSST * unitCost + newDeltaST * unitCost * (material.avgDemand ?? 1)).toFixed(2));
    }

    else {
      return Number((material.avgInventoryEffectAfterChange ?? 1).toFixed(2));
    }

  }


  checkEditCell(lastEntry : IHistoryEntity): boolean{
    let materialValue : IMaterial | undefined = this.materials?.find(e => e.id === lastEntry?.materialId);
    switch (lastEntry.column){
      case "newSST":
        if (materialValue?.newSAPSafetyStock === lastEntry.oldValue){
          return (this.linhas.get(lastEntry.materialId)?.newSST !== materialValue?.proposedSST) || (this.linhas.get(lastEntry.materialId)?.newComment !== "")
        }
        break;
      case "newST":
        if (materialValue?.newSAPSafetyTime === lastEntry.oldValue){
          return (this.linhas.get(lastEntry.materialId)?.newST !== materialValue?.proposedST) || (this.linhas.get(lastEntry.materialId)?.newComment !== "")
        }
        break;
    }
    return false;
  }


  undo() : void{
    if(this.history.length > 0){
      let lastEntry = this.history.pop();
      if(lastEntry){
        let editCell: IEditCell | undefined;
        editCell = this.linhas.get(lastEntry.materialId)
        if(editCell){
          switch(lastEntry.column){
            case "newSST":
              if(!this.checkEditCell(lastEntry)){
                editCell.newSST = lastEntry.oldValue;
                this.linhas.set(lastEntry.materialId, editCell);
                console.log("entrou no braço A SST")
              }
              else {
                this.linhas.delete(editCell.materialId);
                console.log("entrou no braço B SST")
              }
              break;
            case "newST":
              if(!this.checkEditCell(lastEntry)){
                editCell.newST = lastEntry.oldValue;
                this.linhas.set(lastEntry.materialId, editCell);
                console.log("entrou no braço A")
              }
              else {
                this.linhas.delete(editCell.materialId);
                console.log("entrou no braço B")
              }
              break;
          }
          console.log(this.linhas)
        }
      }
    }
  }

  undoChangeSize(){
    if(this.undoSize < this.history.length){
      for(let i = 0 ; i<this.history.length-this.undoSize;i++){
        this.history.shift()
      }
      this.alertMessage="UNDO SIZE CHANGED, OLDER STEPS WHERE LOST"
      this.autoDismissAlert();
    }
  }


  addToHistory(col_name : string, new_value : number , old_value : number, material_id : number) : void{
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
    let oldValue = 0;
    if (this.linhas.has(id)){
      editCell = this.linhas.get(id);
    }
    else{
      editCell = <IEditCell>{};
      editCell.materialId = this.materials?.find(e => e.id == id)?.id ?? -1;
      editCell.newSST = this.materials?.find(e => e.id === id)?.newSAPSafetyStock ?? -1;
      editCell.newST = this.materials?.find(e => e.id === id)?.newSAPSafetyTime ?? -1;
      editCell.newComment = this.materials?.find(e => e.id === id)?.comment ?? null;
      editCell.selected = false;
      editCell.flag = this.materials?.find(e => e.id === id)?.flagMaterial ?? false;
    }
    if (editCell){
      if (col_name === "newSST"){
        oldValue = editCell.newSST
        editCell.newSST = Math.round(event.target.value);
      }
      if (col_name === "newST"){
        //this.openTooltip(toolTip);
        oldValue = editCell.newST;
        editCell.newST = Math.round(event.target.value);
      }
      if (col_name === "newComment") editCell.newComment = event.target.value;
      if (col_name === "selected") {
        editCell.selected = event.target.checked;
        //this.selectedMaterials.push(this.materials?.find(e => e.id === id)?.material ?? "");
        // const materialCopy = this.materials?.find(e => e.id=== id);
        // if (materialCopy !== undefined){
        //   this.selectedMaterials.push(materialCopy);
        //   console.log("Seleted List", this.selectedMaterials)
        // }
      }
      if (col_name === "flag") editCell.flag = !editCell.flag
      this.linhas.set(id, editCell)
      this._isEditable = [-1,-1]
      this.addToHistory(col_name, Math.round(Number(event.target.value)), oldValue, id);
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
    let count = 0;
    this.linhas.forEach((value: IEditCell, key: number) => {
        if(!value.selected){
            count++;
        }
    });
    return count;
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


  delete(material: IMaterial): void {
    const modalRef = this.modalService.open(MaterialDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.material = material;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },error:(error:any) =>{
          alert("Error Deleting")
        }
      });
  }

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
