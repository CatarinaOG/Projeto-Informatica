import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFlaggedMaterial } from '../flagged-material.model';
import { FlaggedMaterialService } from '../service/flagged-material.service';

@Injectable({ providedIn: 'root' })
export class FlaggedMaterialRoutingResolveService implements Resolve<IFlaggedMaterial | null> {
  constructor(protected service: FlaggedMaterialService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFlaggedMaterial | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((flaggedMaterial: HttpResponse<IFlaggedMaterial>) => {
          if (flaggedMaterial.body) {
            return of(flaggedMaterial.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
