import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMaterialSideEffects, NewMaterialSideEffects } from '../material-side-effects.model';

export type PartialUpdateMaterialSideEffects = Partial<IMaterialSideEffects> & Pick<IMaterialSideEffects, 'id'>;

export type EntityResponseType = HttpResponse<IMaterialSideEffects>;
export type EntityArrayResponseType = HttpResponse<IMaterialSideEffects[]>;

@Injectable({ providedIn: 'root' })
export class MaterialSideEffectsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/material-side-effects');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(materialSideEffects: NewMaterialSideEffects): Observable<EntityResponseType> {
    return this.http.post<IMaterialSideEffects>(this.resourceUrl, materialSideEffects, { observe: 'response' });
  }

  update(materialSideEffects: IMaterialSideEffects): Observable<EntityResponseType> {
    return this.http.put<IMaterialSideEffects>(
      `${this.resourceUrl}/${this.getMaterialSideEffectsIdentifier(materialSideEffects)}`,
      materialSideEffects,
      { observe: 'response' },
    );
  }

  partialUpdate(materialSideEffects: PartialUpdateMaterialSideEffects): Observable<EntityResponseType> {
    return this.http.patch<IMaterialSideEffects>(
      `${this.resourceUrl}/${this.getMaterialSideEffectsIdentifier(materialSideEffects)}`,
      materialSideEffects,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMaterialSideEffects>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMaterialSideEffects[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMaterialSideEffectsIdentifier(materialSideEffects: Pick<IMaterialSideEffects, 'id'>): number {
    return materialSideEffects.id;
  }

  compareMaterialSideEffects(o1: Pick<IMaterialSideEffects, 'id'> | null, o2: Pick<IMaterialSideEffects, 'id'> | null): boolean {
    return o1 && o2 ? this.getMaterialSideEffectsIdentifier(o1) === this.getMaterialSideEffectsIdentifier(o2) : o1 === o2;
  }

  addMaterialSideEffectsToCollectionIfMissing<Type extends Pick<IMaterialSideEffects, 'id'>>(
    materialSideEffectsCollection: Type[],
    ...materialSideEffectsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const materialSideEffects: Type[] = materialSideEffectsToCheck.filter(isPresent);
    if (materialSideEffects.length > 0) {
      const materialSideEffectsCollectionIdentifiers = materialSideEffectsCollection.map(
        materialSideEffectsItem => this.getMaterialSideEffectsIdentifier(materialSideEffectsItem)!,
      );
      const materialSideEffectsToAdd = materialSideEffects.filter(materialSideEffectsItem => {
        const materialSideEffectsIdentifier = this.getMaterialSideEffectsIdentifier(materialSideEffectsItem);
        if (materialSideEffectsCollectionIdentifiers.includes(materialSideEffectsIdentifier)) {
          return false;
        }
        materialSideEffectsCollectionIdentifiers.push(materialSideEffectsIdentifier);
        return true;
      });
      return [...materialSideEffectsToAdd, ...materialSideEffectsCollection];
    }
    return materialSideEffectsCollection;
  }
}
