import {Component, OnInit} from '@angular/core';
import {ProductService} from 'src/app/services/product.service';
import {Product} from "../../model/product";
import {Pageable} from "../../model/pageable";

export const GALLERY_URL = "product";

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss']
})
export class GalleryComponent implements OnInit {

  products: Product[] = [];
  totalPages: number = 0;
  totalElements: number = 0;
  elementsPerPage: number = 0;
  pageNumber: number = 0;
  isError: boolean = false;

  constructor(private productService: ProductService) {
  }

  ngOnInit(): void {
    this.retrieveProducts();
  }

  private retrieveProducts() {
    this.productService.findAll()
      .then(response => {
        this.products = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
        this.elementsPerPage = response.size;
        this.pageNumber = response.number;
      })
      .catch(error => {
        console.error(error);
        this.isError = true;
      })
  }

}
