import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'plating-material',
    data: { pageTitle: 'PlatingMaterials' },
    loadChildren: () => import('./plating-material/plating-material.routes'),
  },
  {
    path: 'stone-gem',
    data: { pageTitle: 'StoneGems' },
    loadChildren: () => import('./stone-gem/stone-gem.routes'),
  },
  {
    path: 'effects',
    data: { pageTitle: 'Effects' },
    loadChildren: () => import('./effects/effects.routes'),
  },
  {
    path: 'material-side-effects',
    data: { pageTitle: 'MaterialSideEffects' },
    loadChildren: () => import('./material-side-effects/material-side-effects.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
