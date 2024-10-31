import { NgModule } from '@angular/core';
import {Router, RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {BoutiqueComponent} from "./pages/boutique/boutique.component";
import {MagazineComponent} from "./pages/magazine/magazine.component";
import {SearchComponent} from "./pages/search/search.component";
import {EntryHomeComponent} from "./pages/entry-home/entry-home.component";
import {AnnounceViewComponent} from "./pages/announce-view/announce-view.component";
import {AddAnnounceComponent} from "./pages/add-announce/add-announce.component";

const routes: Routes = [
  {path:"home", component: HomeComponent, children:[
      {path:"search", component: SearchComponent, data:{breadcrumb:"search"}},
      {path:"", component: EntryHomeComponent,  data:{breadcrumb:"home"}}
    ],  data:{breadcrumb:"home"}},
  {path:"boutiques", component: BoutiqueComponent},
  {path:"magazines", component: MagazineComponent},
  {path:"announceView/:id", component: AnnounceViewComponent},
  {path:"", redirectTo:"/home", pathMatch:"full"},
  {path:"create-announce", component: AddAnnounceComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
