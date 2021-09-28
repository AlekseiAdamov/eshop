import {Component, OnInit} from '@angular/core';

export const PRODUCT_URL = "product/:id"

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }
}
