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
import { BanierPubComponent } from './components/banier-pub/banier-pub.component';
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

@NgModule({
  declarations: [
    AppComponent,
    AppToolbarComponent,
    HomeComponent,
    BoutiqueComponent,
    MagazineComponent,
    FooterComponent,
    BanierPubComponent,
    SectionAnnouncesComponent,
    AnnounceComponent,
    SearchComponent,
    EntryHomeComponent,
    SearchToolbarComponent,
    AnnounceVipComponent,
    CategoriesDialogComponent,
    CitiesDialogComponent,
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
    MatButtonToggleModule
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
