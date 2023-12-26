import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEffects } from '../effects.model';
import { EffectsService } from '../service/effects.service';

export const effectsResolve = (route: ActivatedRouteSnapshot): Observable<null | IEffects> => {
  const id = route.params['id'];
  if (id) {
    return inject(EffectsService)
      .find(id)
      .pipe(
        mergeMap((effects: HttpResponse<IEffects>) => {
          if (effects.body) {
            return of(effects.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default effectsResolve;
