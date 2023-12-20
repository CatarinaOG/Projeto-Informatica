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

export type PartialUpdateFlaggedMaterial = Partial<IFlaggedMaterial> & Pick<IFlaggedMaterial, 'id'>;

type RestOf<T extends IFlaggedMaterial | NewFlaggedMaterial> = Omit<T, 'flagExpirationDate' | 'dateOfUpdatedSS' | 'dateOfUpdatedST'> & {
  flagExpirationDate?: string | null;
  dateOfUpdatedSS?: string | null;
  dateOfUpdatedST?: string | null;
};

export type RestFlaggedMaterial = RestOf<IFlaggedMaterial>;

export type NewRestFlaggedMaterial = RestOf<NewFlaggedMaterial>;

export type PartialUpdateRestFlaggedMaterial = RestOf<PartialUpdateFlaggedMaterial>;

export type EntityResponseType = HttpResponse<IFlaggedMaterial>;
export type EntityArrayResponseType = HttpResponse<IFlaggedMaterial[]>;

@Injectable({ providedIn: 'root' })
export class FlaggedMaterialService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/flagged-materials');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(flaggedMaterial: NewFlaggedMaterial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(flaggedMaterial);
    return this.http
      .post<RestFlaggedMaterial>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(flaggedMaterial: IFlaggedMaterial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(flaggedMaterial);
    return this.http
      .put<RestFlaggedMaterial>(`${this.resourceUrl}/${this.getFlaggedMaterialIdentifier(flaggedMaterial)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(flaggedMaterial: PartialUpdateFlaggedMaterial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(flaggedMaterial);
    return this.http
      .patch<RestFlaggedMaterial>(`${this.resourceUrl}/${this.getFlaggedMaterialIdentifier(flaggedMaterial)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFlaggedMaterial>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFlaggedMaterial[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFlaggedMaterialIdentifier(flaggedMaterial: Pick<IFlaggedMaterial, 'id'>): number {
    return flaggedMaterial.id;
  }

  compareFlaggedMaterial(o1: Pick<IFlaggedMaterial, 'id'> | null, o2: Pick<IFlaggedMaterial, 'id'> | null): boolean {
    return o1 && o2 ? this.getFlaggedMaterialIdentifier(o1) === this.getFlaggedMaterialIdentifier(o2) : o1 === o2;
  }

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

  protected convertDateFromClient<T extends IFlaggedMaterial | NewFlaggedMaterial | PartialUpdateFlaggedMaterial>(
    flaggedMaterial: T
  ): RestOf<T> {
    return {
      ...flaggedMaterial,
      flagExpirationDate: flaggedMaterial.flagExpirationDate?.format(DATE_FORMAT) ?? null,
      dateOfUpdatedSS: flaggedMaterial.dateOfUpdatedSS?.format(DATE_FORMAT) ?? null,
      dateOfUpdatedST: flaggedMaterial.dateOfUpdatedST?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restFlaggedMaterial: RestFlaggedMaterial): IFlaggedMaterial {
    return {
      ...restFlaggedMaterial,
      flagExpirationDate: restFlaggedMaterial.flagExpirationDate ? dayjs(restFlaggedMaterial.flagExpirationDate) : undefined,
      dateOfUpdatedSS: restFlaggedMaterial.dateOfUpdatedSS ? dayjs(restFlaggedMaterial.dateOfUpdatedSS) : undefined,
      dateOfUpdatedST: restFlaggedMaterial.dateOfUpdatedST ? dayjs(restFlaggedMaterial.dateOfUpdatedST) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFlaggedMaterial>): HttpResponse<IFlaggedMaterial> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFlaggedMaterial[]>): HttpResponse<IFlaggedMaterial[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
