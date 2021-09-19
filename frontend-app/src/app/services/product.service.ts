import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Page} from "../model/page";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) {
  }

  public findAll(pageNumber: number, elementsPerPage: number) {
    let params = new HttpParams()
      .append('page', pageNumber)
      .append('size', elementsPerPage);
    return this.http.get<Page>("/api/v1/product/all", {params: params}).toPromise();
  }
}
