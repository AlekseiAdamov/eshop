import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {GALLERY_URL, GalleryComponent} from "./pages/gallery/gallery.component";
import {ProductComponent, PRODUCT_URL} from "./pages/product/product.component";
import {CART_URL, CartComponent} from "./pages/cart/cart.component";
import {OrderComponent, ORDERS_URL} from "./pages/order/order.component";

const routes: Routes = [
  {
    path: "",
    pathMatch: "full",
    redirectTo: GALLERY_URL
  },
  {
    path: GALLERY_URL,
    component: GalleryComponent
  },
  {
    path: PRODUCT_URL,
    component: ProductComponent
  },
  {
    path: CART_URL,
    component: CartComponent
  },
  {
    path: ORDERS_URL,
    component: OrderComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
