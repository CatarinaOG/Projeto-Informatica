import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFlaggedMaterial, NewFlaggedMaterial } from '../flagged-material.model';

/**
 * Type created by JHipster
 */
export type PartialUpdateFlaggedMaterial = Partial<IFlaggedMaterial> & Pick<IFlaggedMaterial, 'id'>;

/**
 * Type created by JHipster
 */
type RestOf<T extends IFlaggedMaterial | NewFlaggedMaterial> = Omit<T, 'flagExpirationDate'> & {
  flagExpirationDate?: string | null;
};

/**
 * Type created by JHipster
 */
export type RestFlaggedMaterial = RestOf<IFlaggedMaterial>;

/**
 * Type created by JHipster
 */
export type NewRestFlaggedMaterial = RestOf<NewFlaggedMaterial>;

/**
 * Type created by JHipster
 */
export type PartialUpdateRestFlaggedMaterial = RestOf<PartialUpdateFlaggedMaterial>;

/**
 * Type created by JHipster
 */
export type EntityResponseType = HttpResponse<IFlaggedMaterial>;

/**
 * Type created by JHipster
 */
export type EntityArrayResponseType = HttpResponse<IFlaggedMaterial[]>;


/**
 * Service responsible for operations regarding Flagged Materials
 */
@Injectable({ providedIn: 'root' })
export class FlaggedMaterialService {

  /**
   * Resource url, created by JHipster
   */
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/flagged-materials');

  /**
   * Constructor for the flagged material service
   * @constructor
   * @param http 
   * @param applicationConfigService 
   */
  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}


  /**
   * Function that creates a flagged material, created by JHipster
   * @param flaggedMaterial Flagged material to be created
   * @returns Back-end response
   */
  create(flaggedMaterial: NewFlaggedMaterial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(flaggedMaterial);
    return this.http
      .post<RestFlaggedMaterial>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  /**
   * Function that updates a given Flagged Material, created by JHipster
   * @param flaggedMaterial Flagged Material to be updated
   * @returns Back-end response
   */
  update(flaggedMaterial: IFlaggedMaterial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(flaggedMaterial);
    return this.http
      .put<RestFlaggedMaterial>(`${this.resourceUrl}/${this.getFlaggedMaterialIdentifier(flaggedMaterial)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  /**
   * Function that updates a Flagged Material, crated by the group.
   * If the flag value is false, the Flagged Material will be deleted from the data base.s
   * @param flaggedMaterial Flagged Material to be updated
   * @returns Back-end response
   */
  updateFlagged(flaggedMaterial: IFlaggedMaterial): Observable<EntityResponseType> {
    const data = [{
      material: flaggedMaterial.material,
      dateFlag: flaggedMaterial.flagExpirationDate?.format("YYYY-MM-DD"),
      flag: flaggedMaterial.flagMaterial
    }]

    return this.http
    .post<RestFlaggedMaterial>(`${this.resourceUrl}/update-flag`, data, { observe: 'response' })
    .pipe(map(res => this.convertResponseFromServer(res)))
  }

  /**
   * Function that partially updates the Material, created by JHipster
   * @param flaggedMaterial Flagged Material to be partially updated
   * @returns Back-end response
   */
  partialUpdate(flaggedMaterial: PartialUpdateFlaggedMaterial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(flaggedMaterial);
    return this.http
      .patch<RestFlaggedMaterial>(`${this.resourceUrl}/${this.getFlaggedMaterialIdentifier(flaggedMaterial)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  /**
   * Function that finds a material
   * @param id 
   * @returns 
   */
  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFlaggedMaterial>(`${this.resourceUrl}/${id}`, { observe: 'response' })
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
      .get<RestFlaggedMaterial[]>(this.resourceUrl, { params: options, observe: 'response' })
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
   * Getter for the Flagged Material id, created by JHipster
   * @param flaggedMaterial 
   * @returns 
   */
  getFlaggedMaterialIdentifier(flaggedMaterial: Pick<IFlaggedMaterial, 'id'>): number {
    return flaggedMaterial.id;
  }

  /**
   * Function that compares 2 Flagged Materials, created by JHipster
   * @param o1 
   * @param o2 
   * @returns 
   */
  compareFlaggedMaterial(o1: Pick<IFlaggedMaterial, 'id'> | null, o2: Pick<IFlaggedMaterial, 'id'> | null): boolean {
    return o1 && o2 ? this.getFlaggedMaterialIdentifier(o1) === this.getFlaggedMaterialIdentifier(o2) : o1 === o2;
  }

  /**
   * Function created by JHipster
   * @param flaggedMaterialCollection 
   * @param flaggedMaterialsToCheck 
   * @returns 
   */
  addFlaggedMaterialToCollectionIfMissing<Type extends Pick<IFlaggedMaterial, 'id'>>(
    flaggedMaterialCollection: Type[],
    ...flaggedMaterialsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const flaggedMaterials: Type[] = flaggedMaterialsToCheck.filter(isPresent);
    if (flaggedMaterials.length > 0) {
      const flaggedMaterialCollectionIdentifiers = flaggedMaterialCollection.map(
        flaggedMaterialItem => this.getFlaggedMaterialIdentifier(flaggedMaterialItem)!
      );
      const flaggedMaterialsToAdd = flaggedMaterials.filter(flaggedMaterialItem => {
        const flaggedMaterialIdentifier = this.getFlaggedMaterialIdentifier(flaggedMaterialItem);
        if (flaggedMaterialCollectionIdentifiers.includes(flaggedMaterialIdentifier)) {
          return false;
        }
        flaggedMaterialCollectionIdentifiers.push(flaggedMaterialIdentifier);
        return true;
      });
      return [...flaggedMaterialsToAdd, ...flaggedMaterialCollection];
    }
    return flaggedMaterialCollection;
  }

  /**
   * Function created by JHipster
   * @param flaggedMaterial 
   * @returns 
   */
  protected convertDateFromClient<T extends IFlaggedMaterial | NewFlaggedMaterial | PartialUpdateFlaggedMaterial>(
    flaggedMaterial: T
  ): RestOf<T> {
    return {
      ...flaggedMaterial,
      flagExpirationDate: flaggedMaterial.flagExpirationDate?.format(DATE_FORMAT) ?? null,
    };
  }

  /**
   * Function created by JHipster
   * @param restFlaggedMaterial 
   * @returns 
   */
  protected convertDateFromServer(restFlaggedMaterial: RestFlaggedMaterial): IFlaggedMaterial {
    return {
      ...restFlaggedMaterial,
      flagExpirationDate: restFlaggedMaterial.flagExpirationDate ? dayjs(restFlaggedMaterial.flagExpirationDate) : undefined,
    };
  }

  /**
   * Function created by JHipster
   * @param res 
   * @returns 
   */
  protected convertResponseFromServer(res: HttpResponse<RestFlaggedMaterial>): HttpResponse<IFlaggedMaterial> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  /**
   * Function created by JHipster
   * @param res 
   * @returns 
   */
  protected convertResponseArrayFromServer(res: HttpResponse<RestFlaggedMaterial[]>): HttpResponse<IFlaggedMaterial[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
