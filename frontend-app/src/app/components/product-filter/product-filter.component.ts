import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {ProductFilterDto} from "../../model/product-filter";

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.scss']
})
export class ProductFilterComponent implements OnInit {

  @Output() filterApplied = new EventEmitter<ProductFilterDto>();

  public productFilter: ProductFilterDto = new ProductFilterDto("", 0, 0);

  constructor() {
  }

  ngOnInit(): void {
  }

  applyFilter() {
    this.filterApplied.emit(this.productFilter);
  }

  clearFilter() {
    if (this.productFilter.name != ""
      || this.productFilter.minPrice != 0
      || this.productFilter.maxPrice != 0) {
      this.productFilter = new ProductFilterDto("", 0, 0);
      this.applyFilter();
    }
  }
}
