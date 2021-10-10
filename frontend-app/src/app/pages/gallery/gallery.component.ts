import {Component, OnInit} from '@angular/core';
import {ProductFilterDto} from "../../model/product-filter";
import {ProductService} from "../../services/product.service";
import {Product} from "../../model/product";
import {Page} from "../../model/page";

export const GALLERY_URL = 'product';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss']
})
export class GalleryComponent implements OnInit {

  products: Product[] = [];
  page?: Page;
  productFilter?: ProductFilterDto;
  pageNumber: number = 1;

  constructor(public productService: ProductService) {
  }

  ngOnInit(): void {
    this.productService.findAll()
      .subscribe(
        res => {
          console.log("Loading products");
          this.page = res;
          this.products = res.content;
        },
        err => {
          GalleryComponent.logError(err);
        });
  }

  filterApplied($event: ProductFilterDto) {
    console.log($event);
    this.productFilter = $event;
    this.productService.findAll($event, 1)
      .subscribe(
        res => {
          this.page = res;
          this.products = res.content;
          this.pageNumber = 1;
        },
        err => {
          GalleryComponent.logError(err);
        });
  }

  goToPage($event: number) {
    this.productService.findAll(this.productFilter, $event)
      .subscribe(
        res => {
          this.page = res;
          this.products = res.content;
          this.pageNumber = res.number + 1;
        },
        err => {
          GalleryComponent.logError(err);
        });
  }

  private static logError(err: Error) {
    console.log(`Can't load products: ${err.message}`);
  }
}
