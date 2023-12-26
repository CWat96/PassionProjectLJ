import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlatingMaterial } from '../plating-material.model';
import { PlatingMaterialService } from '../service/plating-material.service';

export const platingMaterialResolve = (route: ActivatedRouteSnapshot): Observable<null | IPlatingMaterial> => {
  const id = route.params['id'];
  if (id) {
    return inject(PlatingMaterialService)
      .find(id)
      .pipe(
        mergeMap((platingMaterial: HttpResponse<IPlatingMaterial>) => {
          if (platingMaterial.body) {
            return of(platingMaterial.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default platingMaterialResolve;
