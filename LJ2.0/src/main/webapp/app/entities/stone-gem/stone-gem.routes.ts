import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { StoneGemComponent } from './list/stone-gem.component';
import { StoneGemDetailComponent } from './detail/stone-gem-detail.component';
import { StoneGemUpdateComponent } from './update/stone-gem-update.component';
import StoneGemResolve from './route/stone-gem-routing-resolve.service';

const stoneGemRoute: Routes = [
  {
    path: '',
    component: StoneGemComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StoneGemDetailComponent,
    resolve: {
      stoneGem: StoneGemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StoneGemUpdateComponent,
    resolve: {
      stoneGem: StoneGemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StoneGemUpdateComponent,
    resolve: {
      stoneGem: StoneGemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default stoneGemRoute;
