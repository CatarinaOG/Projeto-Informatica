import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMaterial } from '../material.model';
import { MaterialService } from '../service/material.service';

@Injectable({ providedIn: 'root' })
export class MaterialRoutingResolveService implements Resolve<IMaterial | null> {
  constructor(protected service: MaterialService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMaterial | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((material: HttpResponse<IMaterial>) => {
          if (material.body) {
            return of(material.body);
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
