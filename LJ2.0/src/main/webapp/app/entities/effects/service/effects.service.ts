import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEffects, NewEffects } from '../effects.model';

export type PartialUpdateEffects = Partial<IEffects> & Pick<IEffects, 'id'>;

export type EntityResponseType = HttpResponse<IEffects>;
export type EntityArrayResponseType = HttpResponse<IEffects[]>;

@Injectable({ providedIn: 'root' })
export class EffectsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/effects');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(effects: NewEffects): Observable<EntityResponseType> {
    return this.http.post<IEffects>(this.resourceUrl, effects, { observe: 'response' });
  }

  update(effects: IEffects): Observable<EntityResponseType> {
    return this.http.put<IEffects>(`${this.resourceUrl}/${this.getEffectsIdentifier(effects)}`, effects, { observe: 'response' });
  }

  partialUpdate(effects: PartialUpdateEffects): Observable<EntityResponseType> {
    return this.http.patch<IEffects>(`${this.resourceUrl}/${this.getEffectsIdentifier(effects)}`, effects, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEffects>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEffects[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEffectsIdentifier(effects: Pick<IEffects, 'id'>): number {
    return effects.id;
  }

  compareEffects(o1: Pick<IEffects, 'id'> | null, o2: Pick<IEffects, 'id'> | null): boolean {
    return o1 && o2 ? this.getEffectsIdentifier(o1) === this.getEffectsIdentifier(o2) : o1 === o2;
  }

  addEffectsToCollectionIfMissing<Type extends Pick<IEffects, 'id'>>(
    effectsCollection: Type[],
    ...effectsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const effects: Type[] = effectsToCheck.filter(isPresent);
    if (effects.length > 0) {
      const effectsCollectionIdentifiers = effectsCollection.map(effectsItem => this.getEffectsIdentifier(effectsItem)!);
      const effectsToAdd = effects.filter(effectsItem => {
        const effectsIdentifier = this.getEffectsIdentifier(effectsItem);
        if (effectsCollectionIdentifiers.includes(effectsIdentifier)) {
          return false;
        }
        effectsCollectionIdentifiers.push(effectsIdentifier);
        return true;
      });
      return [...effectsToAdd, ...effectsCollection];
    }
    return effectsCollection;
  }
}
