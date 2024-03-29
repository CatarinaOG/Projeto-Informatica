import { Component, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFlaggedMaterial } from '../flagged-material.model';

import { PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, FlaggedMaterialService } from '../service/flagged-material.service';
import { FilterOptions, IFilterOptions, IFilterOption } from 'app/shared/filter/filter.model';

/**
 * Flagged Material Page
 */
@Component({
  selector: 'jhi-flagged-material',
  templateUrl: './flagged-material.component.html',
})
export class FlaggedMaterialComponent implements OnInit {

  /**
   * Group of Flagged Materials present in the system
   * @type {IFlaggedMaterial[]}
   */
  flaggedMaterials?: IFlaggedMaterial[];

  /**
   * Property created by JHipster
   */
  isLoading = false;

  /**
   * Property created by JHipster
   */
  predicate = 'id';

  /**
   * Property created by JHipster
   */
  ascending = true;

  /**
   * Table filters active in the moment
   * @type {IFilterOptions}
   */
  filters: IFilterOptions = new FilterOptions();

  /**
   * Flagged Materials per page in Table
   * @type {number}
   */
  itemsPerPage = 10;

  /**
   * Property created by JHipster
   */
  totalItems = 0;

  /**
   * Property created by JHipster
   */
  page = 1;

  
  /**
   * Constructor for the jhi-flagged-material component
   * @constructor
   * @param flaggedMaterialService 
   * @param activatedRoute 
   * @param router 
   * @param modalService 
   */
  constructor(
    protected flaggedMaterialService: FlaggedMaterialService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal
  ) {}


  /**
   * Created by JHipster
   */
  trackId = (_index: number, item: IFlaggedMaterial): number => this.flaggedMaterialService.getFlaggedMaterialIdentifier(item);

  /**
   * Function created by JHipster
   */
  ngOnInit(): void {
    this.load();

    this.filters.filterChanges.subscribe(filterOptions => this.handleNavigation(1, this.predicate, this.ascending, filterOptions));
  }
  

  /**
   * Function created by JHipster
   */
  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  /**
   * Receives the event of Table Size chosen by the user in a child component
   * @param {any} event Event from the Child Component, containing the user option
   */
  receiveTableSize(event : any) : void{
      this.itemsPerPage = event;
      this.load()
  }

  /**
   * Function created by JHipster
   */
  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  /**
   * Function created by JHipster
   * @param {number} page
   */
  navigateToPage(page = this.page): void {
    this.handleNavigation(page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  /**
   * Function responsabile for processing all the String messages received from the various emmitters used in the jhi-options-bar component
   * @param {event} event message received
   */
  receiveStringEvent(event: any): void {
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
      }
    }
    this.load();
  }

  /**
   * Function that adds a filter in the ABC Classification Column
   * @param {any} event
   */
  filterABCClassification(event: any): void {
    if(event.opType){
      this.filters.addFilter("abcClassification.in", event.filterValue);
    }
    else{
      this.filters.removeFilter("abcClassification.in", event.filterValue);
    }
  }

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
    this.flaggedMaterials = dataFromBody;
  }

  /**
   * Function created by JHipster
   */
  protected fillComponentAttributesFromResponseBody(data: IFlaggedMaterial[] | null): IFlaggedMaterial[] {
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
    return this.flaggedMaterialService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
