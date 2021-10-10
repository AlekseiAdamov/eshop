import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AddLineItemDto} from "../model/add-line-item-dto";
import {Observable} from "rxjs";
import {AllCartDto} from "../model/all-cart-dto";
import {LineItem} from '../model/line-item';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<AllCartDto> {
    return this.http.get<AllCartDto>('api/v1/cart/all');
  }

  public addToCart(dto: AddLineItemDto) {
    return this.http.post<any>('api/v1/cart', dto);
  }

  public removeItem(lineItem: LineItem) {
    const options = {
      body: lineItem
    };
    this.http.delete('api/v1/cart', options)
      .subscribe(result => {
        console.log(result)
      });
  }

  public decrement(lineItem: LineItem) {
    this.http.put('api/v1/cart/decrement', lineItem)
      .subscribe(result => {
        console.log(result)
      });
  }

  public increment(lineItem: LineItem) {
    this.http.put('api/v1/cart/increment', lineItem)
      .subscribe(result => {
        console.log(result)
      });
  }

  public clearCart() {
    this.http.delete('api/v1/cart/clear')
      .subscribe(result => {
        console.log(result);
      });
  }
}
