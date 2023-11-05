import { AfterContentInit, Component, OnInit } from '@angular/core';
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
export class MaterialComponent implements OnInit , AfterContentInit{
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

  ngAfterContentInit(){
    var cl = document.getElementsByClassName('edit')
    console.log("CL is ", cl)

    for(let i = 0; i<cl.length;i++){
      cl[i].classList.add("tableHide")
    }
    console.log("Class list is:",cl)
  }

  onFileSelected(event:any) {

    const file:File = event.target.files[0];

    if (file) {

        this.fileName = file.name;

        const formData = new FormData();

        formData.append("thumbnail", file);

        console.log("The filename is " , this.fileName);

    }
  }

  makeEditable(a: number, b: number){
    var cl = document.getElementsByClassName('edit tableHide')
    console.log("Cl is :", cl)
    if(cl){
      if (cl[0]){
        cl[0].classList.remove("tableHide")
      }
    }
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

  input(event: any, col_name : string, id: number) {

    var editCell: IEditCell | undefined;

    if (this.linhas.has(id)){
      editCell = this.linhas.get(id);
    } 
    else{
      editCell = <IEditCell>{};
      editCell.newSSS = -1;
      editCell.newSST = -1;
      editCell.newComment = "n/a";
      editCell.selected = false;
    }
    if (editCell){
      if (col_name == "newSSS") editCell.newSSS = event.target.value;
      if (col_name == "newSST") editCell.newSST = event.target.value;
      if (col_name == "newComment") editCell.newComment = event.target.value;
      if (col_name == "selected") editCell.selected = this.selectRow(event.target.checked, id);
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
