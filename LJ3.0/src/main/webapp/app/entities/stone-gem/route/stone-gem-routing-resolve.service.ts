import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStoneGem } from '../stone-gem.model';
import { StoneGemService } from '../service/stone-gem.service';

export const stoneGemResolve = (route: ActivatedRouteSnapshot): Observable<null | IStoneGem> => {
  const id = route.params['id'];
  if (id) {
    return inject(StoneGemService)
      .find(id)
      .pipe(
        mergeMap((stoneGem: HttpResponse<IStoneGem>) => {
          if (stoneGem.body) {
            return of(stoneGem.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default stoneGemResolve;
