import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlatingMaterial, NewPlatingMaterial } from '../plating-material.model';

export type PartialUpdatePlatingMaterial = Partial<IPlatingMaterial> & Pick<IPlatingMaterial, 'id'>;

export type EntityResponseType = HttpResponse<IPlatingMaterial>;
export type EntityArrayResponseType = HttpResponse<IPlatingMaterial[]>;

@Injectable({ providedIn: 'root' })
export class PlatingMaterialService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plating-materials');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(platingMaterial: NewPlatingMaterial): Observable<EntityResponseType> {
    return this.http.post<IPlatingMaterial>(this.resourceUrl, platingMaterial, { observe: 'response' });
  }

  update(platingMaterial: IPlatingMaterial): Observable<EntityResponseType> {
    return this.http.put<IPlatingMaterial>(`${this.resourceUrl}/${this.getPlatingMaterialIdentifier(platingMaterial)}`, platingMaterial, {
      observe: 'response',
    });
  }

  partialUpdate(platingMaterial: PartialUpdatePlatingMaterial): Observable<EntityResponseType> {
    return this.http.patch<IPlatingMaterial>(`${this.resourceUrl}/${this.getPlatingMaterialIdentifier(platingMaterial)}`, platingMaterial, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlatingMaterial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlatingMaterial[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlatingMaterialIdentifier(platingMaterial: Pick<IPlatingMaterial, 'id'>): number {
    return platingMaterial.id;
  }

  comparePlatingMaterial(o1: Pick<IPlatingMaterial, 'id'> | null, o2: Pick<IPlatingMaterial, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlatingMaterialIdentifier(o1) === this.getPlatingMaterialIdentifier(o2) : o1 === o2;
  }

  addPlatingMaterialToCollectionIfMissing<Type extends Pick<IPlatingMaterial, 'id'>>(
    platingMaterialCollection: Type[],
    ...platingMaterialsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const platingMaterials: Type[] = platingMaterialsToCheck.filter(isPresent);
    if (platingMaterials.length > 0) {
      const platingMaterialCollectionIdentifiers = platingMaterialCollection.map(
        platingMaterialItem => this.getPlatingMaterialIdentifier(platingMaterialItem)!,
      );
      const platingMaterialsToAdd = platingMaterials.filter(platingMaterialItem => {
        const platingMaterialIdentifier = this.getPlatingMaterialIdentifier(platingMaterialItem);
        if (platingMaterialCollectionIdentifiers.includes(platingMaterialIdentifier)) {
          return false;
        }
        platingMaterialCollectionIdentifiers.push(platingMaterialIdentifier);
        return true;
      });
      return [...platingMaterialsToAdd, ...platingMaterialCollection];
    }
    return platingMaterialCollection;
  }
}
