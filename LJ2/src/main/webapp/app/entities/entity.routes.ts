import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'plating-material',
    data: { pageTitle: 'lj2App.platingMaterial.home.title' },
    loadChildren: () => import('./plating-material/plating-material.routes'),
  },
  {
    path: 'stone-gem',
    data: { pageTitle: 'lj2App.stoneGem.home.title' },
    loadChildren: () => import('./stone-gem/stone-gem.routes'),
  },
  {
    path: 'effects',
    data: { pageTitle: 'lj2App.effects.home.title' },
    loadChildren: () => import('./effects/effects.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
