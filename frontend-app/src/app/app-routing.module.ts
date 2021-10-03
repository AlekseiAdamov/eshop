import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {GALLERY_URL, GalleryComponent} from "./pages/gallery/gallery.component";

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
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
