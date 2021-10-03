import {Component, OnInit} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {AllCartDto} from "../../model/all-cart-dto";
import {LineItem} from "../../model/line-item";

export const CART_URL = 'cart'

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  content?: AllCartDto;

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
    this.cartService.findAll().subscribe(
      result => {
        this.content = result;
      }
    );
  }

  removeItem(lineItem: LineItem) {
    this.cartService.removeItem(lineItem);
    let lineItems = this.content?.lineItems;
    if (this.content == undefined || lineItems == undefined) {
      return;
    }
    lineItems.splice(lineItems.indexOf(lineItem), 1);
    this.content.subtotal -= lineItem.itemTotal;
  }

  decrement(lineItem: LineItem) {
    this.cartService.decrement(lineItem);
    let lineItems = this.content?.lineItems;
    if (this.content == undefined || lineItems == undefined) {
      return;
    }
    let cartItem = lineItems[lineItems.indexOf(lineItem)];
    if (cartItem.quantity > 1) {
      cartItem.quantity--;
      const price = cartItem.productDto.price;
      cartItem.itemTotal -= price;
      this.content.subtotal -= price;
    } else {
      this.removeItem(lineItem);
    }
  }

  increment(lineItem: LineItem) {
    this.cartService.increment(lineItem);
    let lineItems = this.content?.lineItems;
    if (this.content == undefined || lineItems == undefined) {
      return;
    }
    let cartItem = lineItems[lineItems.indexOf(lineItem)];
    cartItem.quantity++;
    const price = cartItem.productDto.price;
    cartItem.itemTotal += price;
    this.content.subtotal += price;
  }

  clearCart() {
    this.cartService.clearCart();
    if (this.content == undefined) {
      return;
    }
    this.content.lineItems = [];
    this.content.subtotal = 0;
  }
}
