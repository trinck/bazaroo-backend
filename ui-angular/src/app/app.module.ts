import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { AppToolbarComponent } from './components/app-toolbar/app-toolbar.component';
import {MatToolbar, MatToolbarModule} from "@angular/material/toolbar";
import {MatFormField, MatFormFieldModule, MatPrefix} from "@angular/material/form-field";
import {MatInput, MatInputModule} from "@angular/material/input";
import {MatMenu, MatMenuModule} from "@angular/material/menu";
import { HomeComponent } from './pages/home/home.component';
import { BoutiqueComponent } from './pages/boutique/boutique.component';
import { MagazineComponent } from './pages/magazine/magazine.component';
import { FooterComponent } from './components/footer/footer.component';
import {MatSelect, MatSelectModule} from "@angular/material/select";
import { BannerPubComponent } from './components/banner-pub/banner-pub.component';
import { SectionAnnouncesComponent } from './components/section-announces/section-announces.component';
import {MatCard, MatCardModule} from "@angular/material/card";
import {NgOptimizedImage} from "@angular/common";
import { AnnounceComponent } from './components/announce/announce.component';
import { SearchComponent } from './pages/search/search.component';
import { EntryHomeComponent } from './pages/entry-home/entry-home.component';
import { SearchToolbarComponent } from './components/search-toolbar/search-toolbar.component';
import { AnnounceVipComponent } from './components/announce-vip/announce-vip.component';
import {MatDrawerContainer, MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule, MatNavList} from "@angular/material/list";
import {ReactiveFormsModule} from "@angular/forms";
import {MatDialogModule} from "@angular/material/dialog";
import { CategoriesDialogComponent } from './components/categories-dialog/categories-dialog.component';
import { CitiesDialogComponent } from './components/cities-dialog/cities-dialog.component';
import {MatTreeModule} from "@angular/material/tree";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {MatBadgeModule} from "@angular/material/badge";
import {NgxMapboxGLModule} from "ngx-mapbox-gl";
import { BzrMapComponent } from './components/bzr-map/bzr-map.component';
import { BreadcrumbComponent } from './components/breadcrumb/breadcrumb.component';
import { AnnounceViewComponent } from './pages/announce-view/announce-view.component';
import { TextIconComponent } from './components/text-icon/text-icon.component';

@NgModule({
  declarations: [
    AppComponent,
    AppToolbarComponent,
    HomeComponent,
    BoutiqueComponent,
    MagazineComponent,
    FooterComponent,
    BannerPubComponent,
    SectionAnnouncesComponent,
    AnnounceComponent,
    SearchComponent,
    EntryHomeComponent,
    SearchToolbarComponent,
    AnnounceVipComponent,
    CategoriesDialogComponent,
    CitiesDialogComponent,
    BzrMapComponent,
    BreadcrumbComponent,
    AnnounceViewComponent,
    TextIconComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatSlideToggleModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatPrefix,
    MatFormFieldModule,
    MatInputModule,
    MatMenuModule,
    MatSelectModule,
    MatCardModule,
    NgOptimizedImage,
    MatSidenavModule,
    MatListModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatTreeModule,
    MatButtonToggleModule,
    HttpClientModule,
    MatBadgeModule,
    NgxMapboxGLModule.withConfig({
      accessToken: 'pk.eyJ1IjoibW91bG91bmd1aSIsImEiOiJjbDM0bDdhcWowNWwyM2twNXNubGRkaWh6In0.k6mHuqWTuQx3IF6RWmu8OQ' // Remplacez par votre clé API Mapbox
    })
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
