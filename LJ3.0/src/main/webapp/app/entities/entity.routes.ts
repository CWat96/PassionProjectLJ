import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'plating-material',
    data: { pageTitle: 'labeledJewelryApp.platingMaterial.home.title' },
    loadChildren: () => import('./plating-material/plating-material.routes'),
  },
  {
    path: 'stone-gem',
    data: { pageTitle: 'labeledJewelryApp.stoneGem.home.title' },
    loadChildren: () => import('./stone-gem/stone-gem.routes'),
  },
  {
    path: 'effects',
    data: { pageTitle: 'labeledJewelryApp.effects.home.title' },
    loadChildren: () => import('./effects/effects.routes'),
  },
  {
    path: 'material-side-effects',
    data: { pageTitle: 'labeledJewelryApp.materialSideEffects.home.title' },
    loadChildren: () => import('./material-side-effects/material-side-effects.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
