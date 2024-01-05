import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFlaggedMaterial } from '../flagged-material.model';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, FlaggedMaterialService } from '../service/flagged-material.service';
import { FlaggedMaterialDeleteDialogComponent } from '../delete/flagged-material-delete-dialog.component';
import { SortService } from 'app/shared/sort/sort.service';
import { FlagModal } from 'app/entities/material/flag-modal/flagModal';

@Component({
  selector: 'jhi-flagged-material',
  templateUrl: './flagged-material.component.html',
})
export class FlaggedMaterialComponent implements OnInit {
  flaggedMaterials?: IFlaggedMaterial[];
  isLoading = false;

  visibility = new Map<string, boolean>();

  predicate = 'id';
  ascending = true;

  constructor(
    protected flaggedMaterialService: FlaggedMaterialService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
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

  trackId = (_index: number, item: IFlaggedMaterial): number => this.flaggedMaterialService.getFlaggedMaterialIdentifier(item);

  ngOnInit(): void {
    this.load();
  }

  delete(flaggedMaterial: IFlaggedMaterial): void {
    const modalRef = this.modalService.open(FlaggedMaterialDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.flaggedMaterial = flaggedMaterial;
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
    this.handleNavigation(this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.flaggedMaterials = this.refineData(dataFromBody);
  }

  protected refineData(data: IFlaggedMaterial[]): IFlaggedMaterial[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IFlaggedMaterial[] | null): IFlaggedMaterial[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.flaggedMaterialService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
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


  editCellClasses(colName: string, material?: IFlaggedMaterial, editColName? : string) : string {
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
    return returnVal
  }

  switchVisibility(event: any) : void {
    var col_name = event.name;
    if (this.visibility.get(col_name) == false){
      this.visibility.set(col_name, true)
    }
    else {
      this.visibility.set(col_name, false)
    }
  }


}
