import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EffectsComponent } from './list/effects.component';
import { EffectsDetailComponent } from './detail/effects-detail.component';
import { EffectsUpdateComponent } from './update/effects-update.component';
import EffectsResolve from './route/effects-routing-resolve.service';

const effectsRoute: Routes = [
  {
    path: '',
    component: EffectsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EffectsDetailComponent,
    resolve: {
      effects: EffectsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EffectsUpdateComponent,
    resolve: {
      effects: EffectsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EffectsUpdateComponent,
    resolve: {
      effects: EffectsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default effectsRoute;
