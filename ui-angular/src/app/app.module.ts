import { NgModule } from '@angular/core';
import {BrowserModule, Meta, Title} from '@angular/platform-browser';

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
import { MediasViewSlideComponent } from './components/medias-view-slide/medias-view-slide.component';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fab } from '@fortawesome/free-brands-svg-icons';
import { BzrMapViewComponent } from './components/bzr-map-view/bzr-map-view.component';
import {MomentModule} from "ngx-moment";
import { BzrPaginatorComponent } from './components/bzr-paginator/bzr-paginator.component';
import {MatRipple} from "@angular/material/core";
import { AddAnnounceComponent } from './pages/add-announce/add-announce.component';
import { BzrAppComponent } from './pages/bzr-app/bzr-app.component';
import { BzrStepperComponent } from './components/bzr-stepper/bzr-stepper.component';
import {MatStepperModule} from "@angular/material/stepper";
import { BzrStepComponent } from './components/bzr-step/bzr-step.component';
import { InfoCardComponent } from './components/info-card/info-card.component';
import { MediasUploaderComponent } from './components/medias-uploader/medias-uploader.component';
import { CategoryTreeComponent } from './components/category-tree/category-tree.component';
import {CategorySelectDialogComponent} from "./components/category-select-dialog/category-select-dialog.component";
import { BzrRadioComponent } from './components/bzr-radio/bzr-radio.component';
import { CitySelectDialogComponent } from './components/city-select-dialog/city-select-dialog.component';
import { PreviewAnnounceComponent } from './components/preview-announce/preview-announce.component';
import { BzrCheckboxComponent } from './components/bzr-checkbox/bzr-checkbox.component';
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
    MediasViewSlideComponent,
    BzrMapViewComponent,
    BzrPaginatorComponent,
    AddAnnounceComponent,
    BzrAppComponent,
    BzrStepperComponent,
    BzrStepComponent,
    InfoCardComponent,
    MediasUploaderComponent,
    CategoryTreeComponent,
    CategorySelectDialogComponent,
    BzrRadioComponent,
    CitySelectDialogComponent,
    PreviewAnnounceComponent,
    BzrCheckboxComponent
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
    MomentModule,
    FontAwesomeModule,
    MatStepperModule,
    NgxMapboxGLModule.withConfig({
      accessToken: 'pk.eyJ1IjoibW91bG91bmd1aSIsImEiOiJjbDM0bDdhcWowNWwyM2twNXNubGRkaWh6In0.k6mHuqWTuQx3IF6RWmu8OQ' // Remplacez par votre clé API Mapbox
    }),
    MatRipple
  ],
  providers: [
    provideAnimationsAsync(), Meta, Title
  ],
  bootstrap: [AppComponent]
})
export class AppModule {


  constructor(library: FaIconLibrary) {
    library.addIconPacks(fas, far,fab);
  }
}
