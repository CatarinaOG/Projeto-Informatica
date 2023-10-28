import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDiabo } from '../diabo.model';
import { DiaboService } from '../service/diabo.service';

@Injectable({ providedIn: 'root' })
export class DiaboRoutingResolveService implements Resolve<IDiabo | null> {
  constructor(protected service: DiaboService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDiabo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((diabo: HttpResponse<IDiabo>) => {
          if (diabo.body) {
            return of(diabo.body);
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
