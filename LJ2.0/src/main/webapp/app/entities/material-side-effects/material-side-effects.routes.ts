import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MaterialSideEffectsComponent } from './list/material-side-effects.component';
import { MaterialSideEffectsDetailComponent } from './detail/material-side-effects-detail.component';
import { MaterialSideEffectsUpdateComponent } from './update/material-side-effects-update.component';
import MaterialSideEffectsResolve from './route/material-side-effects-routing-resolve.service';

const materialSideEffectsRoute: Routes = [
  {
    path: '',
    component: MaterialSideEffectsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MaterialSideEffectsDetailComponent,
    resolve: {
      materialSideEffects: MaterialSideEffectsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MaterialSideEffectsUpdateComponent,
    resolve: {
      materialSideEffects: MaterialSideEffectsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MaterialSideEffectsUpdateComponent,
    resolve: {
      materialSideEffects: MaterialSideEffectsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default materialSideEffectsRoute;
