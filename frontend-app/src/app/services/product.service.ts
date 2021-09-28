import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Page} from "../model/page";
import {ProductFilterDto} from "../model/product-filter";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) {
  }

  public findAll(productFilter?: ProductFilterDto, page?: number): Observable<Page> {
    let params = new HttpParams();
    console.log(productFilter);
    if (productFilter?.name != null) {
      params = params.append('name', productFilter?.name);
    }
    if (productFilter?.minPrice != null) {
      params = params.append('minPrice', productFilter?.minPrice);
    }
    if (productFilter?.maxPrice != null) {
      params.append('maxPrice', productFilter?.maxPrice);
    }

    params = params.append("page", page != null ? page : 1);
    params = params.append("size", 6);
    return this.http.get<Page>('/api/v1/product/all', {params: params});
  }
}
