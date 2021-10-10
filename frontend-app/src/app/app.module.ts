import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavBarComponent} from './components/nav-bar/nav-bar.component';
import {FooterComponent} from './components/footer/footer.component';
import {GalleryComponent} from './pages/gallery/gallery.component';
import {HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {PaginationComponent} from './components/pagination/pagination.component';
import {ProductFilterComponent} from './components/product-filter/product-filter.component';
import {ProductGalleryComponent} from './components/product-gallery/product-gallery.component';
import {CartComponent} from './pages/cart/cart.component';
import {LoginComponent} from './pages/login/login.component';
import {OrderComponent} from './pages/order/order.component';
import {RegisterComponent} from './pages/register/register.component';
import {ProductComponent} from './pages/product/product.component';
import {UnauthorizedInterceptor} from "./helpers/unauthorized-interceptor";

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    FooterComponent,
    GalleryComponent,
    PaginationComponent,
    ProductFilterComponent,
    ProductGalleryComponent,
    CartComponent,
    LoginComponent,
    OrderComponent,
    RegisterComponent,
    ProductComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions({cookieName: 'XSRF-TOKEN'})
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: UnauthorizedInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
