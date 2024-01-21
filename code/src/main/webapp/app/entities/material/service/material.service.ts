import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMaterial, NewMaterial } from '../material.model';

/**
 * Type created by JHipster
 */
export type PartialUpdateMaterial = Partial<IMaterial> & Pick<IMaterial, 'id'>;

/**
 * Type created by JHipster
 */
type RestOf<T extends IMaterial | NewMaterial> = Omit<
  T,
  'flagExpirationDate' | 'dateNewSS' | 'datNewST' | 'datePreviousSS' | 'datePreviousST'
> & {
  flagExpirationDate?: string | null;
  dateNewSS?: string | null;
  datNewST?: string | null;
  datePreviousSS?: string | null;
  datePreviousST?: string | null;
};

/**
 * Type created by JHipster
 */
export type RestMaterial = RestOf<IMaterial>;

/**
 * Type created by JHipster
 */
export type NewRestMaterial = RestOf<NewMaterial>;

/**
 * Type created by JHipster
 */
export type PartialUpdateRestMaterial = RestOf<PartialUpdateMaterial>;

/**
 * Type created by JHipster
 */
export type EntityResponseType = HttpResponse<IMaterial>;

/**
 * Type created by JHipster
 */
export type EntityArrayResponseType = HttpResponse<IMaterial[]>;

/**
 * Service responsible for operations regarding Materials
 */
@Injectable({ providedIn: 'root' })
export class MaterialService {

  /**
   * Resource url, created by JHipster
   */
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/materials');

  /**
   * Constructor for the material service
   * @constructor
   * @param http 
   * @param applicationConfigService 
   */
  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}


  /**
   * Function that creates a material, created by JHipster
   * @param material 
   * @returns 
   */
  create(material: NewMaterial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(material);
    return this.http
      .post<RestMaterial>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  /**
   * Function that updates a material, created by JHipster
   * @param material 
   * @returns 
   */
  update(material: IMaterial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(material);
    return this.http
      .put<RestMaterial>(`${this.resourceUrl}/${this.getMaterialIdentifier(material)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  /**
   * Function that partially updates the Material, created by JHipster
   * @param material 
   * @returns 
   */
  partialUpdate(material: PartialUpdateMaterial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(material);
    return this.http
      .patch<RestMaterial>(`${this.resourceUrl}/${this.getMaterialIdentifier(material)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  /**
   * Function that finds a material
   * @param id 
   * @returns 
   */
  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMaterial>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  /**
   * Function created by JHipster
   * @param req 
   * @returns 
   */
  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMaterial[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  /**
   * Function that deletes a material, created by JHipster
   * @param id
   * @returns 
   */
  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  /**
   * Getter for the material id
   * @param material 
   * @returns 
   */
  getMaterialIdentifier(material: Pick<IMaterial, 'id'>): number {
    return material.id;
  }

  /**
   * Function created by JHipster
   * @param o1 
   * @param o2 
   * @returns 
   */
  compareMaterial(o1: Pick<IMaterial, 'id'> | null, o2: Pick<IMaterial, 'id'> | null): boolean {
    return o1 && o2 ? this.getMaterialIdentifier(o1) === this.getMaterialIdentifier(o2) : o1 === o2;
  }

  /**
   * Function created by JHipster
   * @param materialCollection 
   * @param materialsToCheck 
   * @returns 
   */
  addMaterialToCollectionIfMissing<Type extends Pick<IMaterial, 'id'>>(
    materialCollection: Type[],
    ...materialsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const materials: Type[] = materialsToCheck.filter(isPresent);
    if (materials.length > 0) {
      const materialCollectionIdentifiers = materialCollection.map(materialItem => this.getMaterialIdentifier(materialItem)!);
      const materialsToAdd = materials.filter(materialItem => {
        const materialIdentifier = this.getMaterialIdentifier(materialItem);
        if (materialCollectionIdentifiers.includes(materialIdentifier)) {
          return false;
        }
        materialCollectionIdentifiers.push(materialIdentifier);
        return true;
      });
      return [...materialsToAdd, ...materialCollection];
    }
    return materialCollection;
  }

  /**
   * Function responsible for sending submitted data to the back-end
   * @param {any[]} data - data being submitted
   * @returns response to the post request
   */
  submitChanges(data : any[]): Observable<Blob>{
    return this.http.post<Blob>(`${this.resourceUrl}/submitChanges/`,data, {
      responseType: 'blob' as 'json',
      headers: {
        Accept: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      },
    });
  }

  /**
   * Function that uploads a file to the back end with the purpose of replacing the data already stored.
   * @param {File} file 
   * @returns response to the post request
   */
  uploadFileReplace(file: File): Observable<HttpResponse<{}>> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.resourceUrl}/uploadFileReplace`, formData, { observe: 'response' });
  }

  /**
   * Function that uploads a file to the back end with the purpose of adding to the data already stored.
   * @param {File} file 
   * @returns response to the post request
   */
  uploadFileAddOrUpdate(file: File): Observable<HttpResponse<{}>> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.resourceUrl}/uploadFileAddOrUpdate`, formData, { observe: 'response' });
  }

  /**
   * Function that serves as a getter for the file that the user asked to dowload
   * @returns exported file
   */
  exportFileAsExcel(): Observable<Blob> {
    return this.http.get<Blob>(`${this.resourceUrl}/download/`, {
      responseType: 'blob' as 'json',
      headers: {
        Accept: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      },
    });
  }

  /**
   * Function created by JHipster
   * @param material 
   * @returns 
   */
  protected convertDateFromClient<T extends IMaterial | NewMaterial | PartialUpdateMaterial>(material: T): RestOf<T> {
    return {
      ...material,
      flagExpirationDate: material.flagExpirationDate?.format(DATE_FORMAT) ?? null,
      dateNewSS: material.dateNewSS?.format(DATE_FORMAT) ?? null,
      datNewST: material.datNewST?.format(DATE_FORMAT) ?? null,
      datePreviousSS: material.datePreviousSS?.format(DATE_FORMAT) ?? null,
      datePreviousST: material.datePreviousST?.format(DATE_FORMAT) ?? null,
    };
  }

  /**
   * Function created by JHipster
   * @param restMaterial 
   * @returns 
   */
  protected convertDateFromServer(restMaterial: RestMaterial): IMaterial {
    return {
      ...restMaterial,
      flagExpirationDate: restMaterial.flagExpirationDate ? dayjs(restMaterial.flagExpirationDate) : undefined,
      dateNewSS: restMaterial.dateNewSS ? dayjs(restMaterial.dateNewSS) : undefined,
      datNewST: restMaterial.datNewST ? dayjs(restMaterial.datNewST) : undefined,
      datePreviousSS: restMaterial.datePreviousSS ? dayjs(restMaterial.datePreviousSS) : undefined,
      datePreviousST: restMaterial.datePreviousST ? dayjs(restMaterial.datePreviousST) : undefined,
    };
  }

  /**
   * Function created by JHipster
   * @param res 
   * @returns 
   */
  protected convertResponseFromServer(res: HttpResponse<RestMaterial>): HttpResponse<IMaterial> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  /**
   * Function created by JHipster
   * @param res 
   * @returns 
   */
  protected convertResponseArrayFromServer(res: HttpResponse<RestMaterial[]>): HttpResponse<IMaterial[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
