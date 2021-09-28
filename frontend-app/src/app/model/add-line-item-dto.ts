export class AddLineItemDto {

  constructor(public productId: number,
              public quantity: number,
              public color: string,
              public material: string) {
  }
}
