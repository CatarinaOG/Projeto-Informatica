import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDiabo, NewDiabo } from '../diabo.model';

export type PartialUpdateDiabo = Partial<IDiabo> & Pick<IDiabo, 'id'>;

export type EntityResponseType = HttpResponse<IDiabo>;
export type EntityArrayResponseType = HttpResponse<IDiabo[]>;

@Injectable({ providedIn: 'root' })
export class DiaboService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/diabos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(diabo: NewDiabo): Observable<EntityResponseType> {
    return this.http.post<IDiabo>(this.resourceUrl, diabo, { observe: 'response' });
  }

  update(diabo: IDiabo): Observable<EntityResponseType> {
    return this.http.put<IDiabo>(`${this.resourceUrl}/${this.getDiaboIdentifier(diabo)}`, diabo, { observe: 'response' });
  }

  partialUpdate(diabo: PartialUpdateDiabo): Observable<EntityResponseType> {
    return this.http.patch<IDiabo>(`${this.resourceUrl}/${this.getDiaboIdentifier(diabo)}`, diabo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDiabo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDiabo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDiaboIdentifier(diabo: Pick<IDiabo, 'id'>): number {
    return diabo.id;
  }

  compareDiabo(o1: Pick<IDiabo, 'id'> | null, o2: Pick<IDiabo, 'id'> | null): boolean {
    return o1 && o2 ? this.getDiaboIdentifier(o1) === this.getDiaboIdentifier(o2) : o1 === o2;
  }

  addDiaboToCollectionIfMissing<Type extends Pick<IDiabo, 'id'>>(
    diaboCollection: Type[],
    ...diabosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const diabos: Type[] = diabosToCheck.filter(isPresent);
    if (diabos.length > 0) {
      const diaboCollectionIdentifiers = diaboCollection.map(diaboItem => this.getDiaboIdentifier(diaboItem)!);
      const diabosToAdd = diabos.filter(diaboItem => {
        const diaboIdentifier = this.getDiaboIdentifier(diaboItem);
        if (diaboCollectionIdentifiers.includes(diaboIdentifier)) {
          return false;
        }
        diaboCollectionIdentifiers.push(diaboIdentifier);
        return true;
      });
      return [...diabosToAdd, ...diaboCollection];
    }
    return diaboCollection;
  }
}
