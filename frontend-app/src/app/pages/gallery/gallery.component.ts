import {Component, OnInit} from '@angular/core';
import {ProductService} from 'src/app/services/product.service';
import {Product} from "../../model/product";
import {ActivatedRoute} from "@angular/router";

export const GALLERY_URL = "product";
const DEFAULT_PAGE_SIZE: number = 3;
const DEFAULT_PAGE_NUMBER: number = 1;

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss']
})
export class GalleryComponent implements OnInit {
  products: Product[] = [];
  totalPages: number = 1;
  totalElements: number = 1;
  elementsPerPage: number = DEFAULT_PAGE_SIZE;
  pageNumber: number = DEFAULT_PAGE_NUMBER;
  isError: boolean = false;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
      this.route.queryParams.subscribe(params => {
        let pageNumber = +params['page'];
        this.pageNumber = isNaN(pageNumber) ? DEFAULT_PAGE_NUMBER : pageNumber;
        let elementsPerPage = +params['size'];
        this.elementsPerPage = isNaN(elementsPerPage) ? DEFAULT_PAGE_SIZE : elementsPerPage;
      });
      this.retrieveProducts(this.pageNumber, this.elementsPerPage);
  }

  public retrieveProducts(pageNumber: number, elementsPerPage: number) {
    this.productService.findAll(pageNumber, elementsPerPage)
      .then(response => {
        this.products = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
        this.elementsPerPage = response.size;
        this.pageNumber = response.number + 1; // First page number is 0.
      })
      .catch(error => {
        console.error(error);
        this.isError = true;
      })
  }

}
