import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStoneGem, NewStoneGem } from '../stone-gem.model';

export type PartialUpdateStoneGem = Partial<IStoneGem> & Pick<IStoneGem, 'id'>;

export type EntityResponseType = HttpResponse<IStoneGem>;
export type EntityArrayResponseType = HttpResponse<IStoneGem[]>;

@Injectable({ providedIn: 'root' })
export class StoneGemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/stone-gems');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(stoneGem: NewStoneGem): Observable<EntityResponseType> {
    return this.http.post<IStoneGem>(this.resourceUrl, stoneGem, { observe: 'response' });
  }

  update(stoneGem: IStoneGem): Observable<EntityResponseType> {
    return this.http.put<IStoneGem>(`${this.resourceUrl}/${this.getStoneGemIdentifier(stoneGem)}`, stoneGem, { observe: 'response' });
  }

  partialUpdate(stoneGem: PartialUpdateStoneGem): Observable<EntityResponseType> {
    return this.http.patch<IStoneGem>(`${this.resourceUrl}/${this.getStoneGemIdentifier(stoneGem)}`, stoneGem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStoneGem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStoneGem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStoneGemIdentifier(stoneGem: Pick<IStoneGem, 'id'>): number {
    return stoneGem.id;
  }

  compareStoneGem(o1: Pick<IStoneGem, 'id'> | null, o2: Pick<IStoneGem, 'id'> | null): boolean {
    return o1 && o2 ? this.getStoneGemIdentifier(o1) === this.getStoneGemIdentifier(o2) : o1 === o2;
  }

  addStoneGemToCollectionIfMissing<Type extends Pick<IStoneGem, 'id'>>(
    stoneGemCollection: Type[],
    ...stoneGemsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const stoneGems: Type[] = stoneGemsToCheck.filter(isPresent);
    if (stoneGems.length > 0) {
      const stoneGemCollectionIdentifiers = stoneGemCollection.map(stoneGemItem => this.getStoneGemIdentifier(stoneGemItem)!);
      const stoneGemsToAdd = stoneGems.filter(stoneGemItem => {
        const stoneGemIdentifier = this.getStoneGemIdentifier(stoneGemItem);
        if (stoneGemCollectionIdentifiers.includes(stoneGemIdentifier)) {
          return false;
        }
        stoneGemCollectionIdentifiers.push(stoneGemIdentifier);
        return true;
      });
      return [...stoneGemsToAdd, ...stoneGemCollection];
    }
    return stoneGemCollection;
  }
}
