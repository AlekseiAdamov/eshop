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

    if (productFilter?.name != undefined && productFilter?.name != "") {
      params = params.append('name', productFilter.name);
    }
    if (productFilter?.minPrice != undefined && productFilter?.minPrice > 0) {
      params = params.append('minPrice', productFilter.minPrice);
    }
    if (productFilter?.maxPrice != undefined && productFilter?.maxPrice > 0) {
      params = params.append('maxPrice', productFilter.maxPrice);
    }

    params = params.append("page", page != undefined ? page : 1)
      .append("size", 6);
    return this.http.get<Page>('/api/v1/product/all', {params: params});
  }
}
