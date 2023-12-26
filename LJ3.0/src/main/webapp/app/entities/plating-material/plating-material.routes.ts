import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PlatingMaterialComponent } from './list/plating-material.component';
import { PlatingMaterialDetailComponent } from './detail/plating-material-detail.component';
import { PlatingMaterialUpdateComponent } from './update/plating-material-update.component';
import PlatingMaterialResolve from './route/plating-material-routing-resolve.service';

const platingMaterialRoute: Routes = [
  {
    path: '',
    component: PlatingMaterialComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlatingMaterialDetailComponent,
    resolve: {
      platingMaterial: PlatingMaterialResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlatingMaterialUpdateComponent,
    resolve: {
      platingMaterial: PlatingMaterialResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlatingMaterialUpdateComponent,
    resolve: {
      platingMaterial: PlatingMaterialResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default platingMaterialRoute;
