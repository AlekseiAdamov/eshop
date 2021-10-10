import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {GALLERY_URL, GalleryComponent} from "./pages/gallery/gallery.component";
import {PRODUCT_URL, ProductComponent} from "./pages/product/product.component";
import {CART_URL, CartComponent} from "./pages/cart/cart.component";
import {OrderComponent, ORDERS_URL} from "./pages/order/order.component";
import {LOGIN_URL, LoginComponent} from "./pages/login/login.component";
import {REGISTER_URL, RegisterComponent} from "./pages/register/register.component";
import {AuthGuard} from "./helpers/auth-guard";

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
    path: LOGIN_URL,
    component: LoginComponent
  },
  {
    path: REGISTER_URL,
    component: RegisterComponent
  },
  {
    path: ORDERS_URL,
    component: OrderComponent,
    canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
