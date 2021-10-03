export class ProductFilterDto {

  constructor(public name: string,
              public minPrice: number,
              public maxPrice: number) {
  }
}
