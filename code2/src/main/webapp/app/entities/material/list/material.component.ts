import { Component, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {IEditCell} from '../editCell.model'
import { IMaterial } from '../material.model';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, MaterialService } from '../service/material.service';
import { MaterialDeleteDialogComponent } from '../delete/material-delete-dialog.component';


@Component({
  selector: 'jhi-material',
  templateUrl: './material.component.html',
})
export class MaterialComponent implements OnInit {
  materials?: IMaterial[];
  isLoading = false;

  predicate = 'id';
  ascending = true;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  isVisible = true;
  masterSelected=false;
  private _isEditable : number[] = [-1,-1];
  fileName = '';

  get isEditable(): number[] {
    return this._isEditable;
  }
  set isEditable(value: number[]) {
    this._isEditable = [value[0], value[1]]
    
  }

  visibility = new Map<string, boolean>([
    ["materialInfo", true],
    ["supplierDelay", true],
    ["safetyStock", true],
    ["safetyTime", true],
    ["inventory", true],
    ["edit", false]
  ]);

  firstTime = true;
  
  
  linhas = new Map<number,IEditCell>();

  constructor(
    protected materialService: MaterialService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IMaterial): number => this.materialService.getMaterialIdentifier(item);

  ngOnInit(): void {
    this.load();    
  }


  checkUncheckAll(event:any) {
    this.linhas.forEach(function(value,key) {
      value.selected = event.target.checked
    })

    event.stopPropagation();

  }

  func(id :number): String{
    if (this.linhas.has(id) && this.linhas.get(id)?.selected){
      return "selected-row"
    }
    else return "normal-row"
  }

  func2() : String{
    if (this.visibility.get('edit') === false){
      return " tableHide"
    }
    else{
      return ""
    }
  }


  mapToSubmit() : IEditCell[]{

    let list : IEditCell[] = [];
    
    this.linhas.forEach(function(value,key) {
      if (value.selected){
        list.push(value)
      }
    })

    return list;
  }

  submitToSAP() : any {

      let list = this.mapToSubmit();
      console.log("Lista : " , list)
      this.materialService.submitChanges(list).subscribe({
        next: (res: any) => {       
          this.load()
          console.log("Estamos a recarregar")
          this.cleanList();
        },error:(error:any) =>{
          alert("Error Uploading Values")
        }
      });

    return;
  }

  cleanList() {
    this.linhas.clear();
  }

  onFileSelected(event:any, typeReplace : boolean) {
    //true -> replace
    //false -> add
    const file:File = event.target.files[0];
    if (file && typeReplace) {
      this.materialService.uploadFileReplace(file).subscribe({
        next: (res: any) => {       
          this.load()
        },error:(error:any) =>{
          alert("Error Uploading File")
        }
      });
    }
    else if (file && !typeReplace){
      this.materialService.uploadFileAddOrUpdate(file).subscribe({
        next: (res: any) => {
          this.load()
        },error:(error:any) =>{
          alert("Error Uploading File")
        }
      });
    }
  }

  makeEditable(a: number, b: number){
    this._isEditable = [a,b];

  }
  
  selectRow(checked : boolean, id : number) : boolean {
    var editCell: IEditCell | undefined;
    if (checked) {
      // get row with id and change background color
      return true
    }
    else {
      // get row with id and reset background color
      return false
    }
  }


  calcNewValueAvg(material : IMaterial) : number {
    var editCell: IEditCell | undefined;
    editCell = this.linhas.get(material.id)
    
    if (editCell){
      var newDeltaSST = (editCell.newSST ?? 1) - (material.currSAPSafetyStock ?? 1);
      var newDeltaST = (editCell.newST ?? 1) - (material.currentSAPSafeTime ?? 1);
      var unitCost = material.unitCost ?? 1
      return Number((newDeltaSST * unitCost + newDeltaST * unitCost * (material.avgDemand ?? 1)).toFixed(2));
    }

    else {
      return Number((material.avgInventoryEffectAfterChange ?? 1).toFixed(2));
    }
   
  }


  input(event: any, col_name : string, id: number) {

    var editCell: IEditCell | undefined;

    if (this.linhas.has(id)){
      editCell = this.linhas.get(id);
    } 
    else{
      editCell = <IEditCell>{};
      editCell.materialId = this.materials?.find(e => e.id == id)?.id ?? -1;
      editCell.newSST = this.materials?.find(e => e.id === id)?.proposedSST ?? -1;
      editCell.newST = this.materials?.find(e => e.id === id)?.proposedST ?? -1;
      editCell.newComment = this.materials?.find(e => e.id === id)?.comment ?? "";
      editCell.selected = false;
      editCell.flag = this.materials?.find(e => e.id === id)?.flagMaterial ?? false;
    }
    if (editCell){
      if (col_name == "newSST") editCell.newSST = Math.round(event.target.value);
      if (col_name == "newST") editCell.newST = Math.round(event.target.value);
      if (col_name == "newComment") editCell.newComment = event.target.value;
      if (col_name == "selected") editCell.selected = this.selectRow(event.target.checked, id);
      if (col_name == "flag") editCell.flag = !editCell.flag
      console.log("value " + col_name + "changed")
      console.log(editCell)
      this.linhas.set(id, editCell)
      this._isEditable = [-1,-1]
    }
    
  }
  
  switchVisibility(event: Event, col_name :any) : void {
    var cl = document.getElementsByClassName(col_name)
    if (this.visibility.get(col_name) == false){
      this.visibility.set(col_name, true)
      console.log("Is visible equals to " , this.visibility.get(col_name))
      for(let i = 0; i<cl.length;i++){
        cl[i].classList.remove("tableHide")
      }
    }
    else {
      this.visibility.set(col_name, false)
      console.log("Is visible equals to " , this.visibility.get(col_name))
      for(let i = 0; i<cl.length;i++){
        cl[i].classList.add("tableHide")
      }
    }
    event.stopPropagation();
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
        },
      });
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending);
  }

  navigateToPage(page = this.page): void {
    this.handleNavigation(page, this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
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

  protected queryBackend(page?: number, predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.materialService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page = this.page, predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      page,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };

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
