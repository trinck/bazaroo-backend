import { NgModule } from '@angular/core';
import {Router, RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {BoutiqueComponent} from "./pages/boutique/boutique.component";
import {MagazineComponent} from "./pages/magazine/magazine.component";
import {SearchComponent} from "./pages/search/search.component";

const routes: Routes = [
  {path:"home", component: HomeComponent},
  {path:"boutiques", component: BoutiqueComponent},
  {path:"search", component: SearchComponent},
  {path:"magazines", component: MagazineComponent},
  {path:"", component:HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
