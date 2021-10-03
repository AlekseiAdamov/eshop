import {Component, OnInit} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {CART_URL} from "../../pages/cart/cart.component";
import {GALLERY_URL} from "../../pages/gallery/gallery.component";
import {ORDERS_URL} from "../../pages/order/order.component";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {

  isProductGalleryPage: boolean = false;
  isCartPage: boolean = false;
  isOrderPage: boolean = false;

  constructor(private router: Router,
              public auth: AuthService) {
  }

  ngOnInit(): void {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.isProductGalleryPage = event.url === '/' || event.url === '/' + GALLERY_URL;
        this.isCartPage = event.url === '/' + CART_URL;
        this.isOrderPage = event.url === '/' + ORDERS_URL;
      }
    })
  }

  logout() {
    this.auth.logout();
    this.router.navigateByUrl("/");
  }
}
