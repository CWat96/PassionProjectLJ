import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMaterialSideEffects } from '../material-side-effects.model';
import { MaterialSideEffectsService } from '../service/material-side-effects.service';

export const materialSideEffectsResolve = (route: ActivatedRouteSnapshot): Observable<null | IMaterialSideEffects> => {
  const id = route.params['id'];
  if (id) {
    return inject(MaterialSideEffectsService)
      .find(id)
      .pipe(
        mergeMap((materialSideEffects: HttpResponse<IMaterialSideEffects>) => {
          if (materialSideEffects.body) {
            return of(materialSideEffects.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default materialSideEffectsResolve;
