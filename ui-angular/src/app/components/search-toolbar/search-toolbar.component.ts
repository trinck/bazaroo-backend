import {Component, signal} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {CategoriesDialogComponent} from "../categories-dialog/categories-dialog.component";
import {Category} from "../../models/Category";
import {City} from "../../models/City";
import {CitiesDialogComponent} from "../cities-dialog/cities-dialog.component";
import {DrawerService} from "../../services/drawer.service";

@Component({
  selector: 'app-search-toolbar',
  templateUrl: './search-toolbar.component.html',
  styleUrl: './search-toolbar.component.css'
})
export class SearchToolbarComponent {
  category: Category= {title:"Informatique", iconUrl:"url de l'icon",color:"category-icon-red"}
  city: City = {id:"r4re454e",name:"Libreville", countryId:"rzgrhe5h455he"}
  constructor(public dialog:MatDialog, private drawerService: DrawerService) {
  }

   openCategoryDialog(){
   const dialog = this.dialog.open(CategoriesDialogComponent,{data:{title:this.category.title,icon:this.category.iconUrl}})
  }
  openCitiesDialog(){
    const dialog = this.dialog.open(CitiesDialogComponent,{data:{name:this.city.name}})
  }

  openCategoryDrawer(){
    this.drawerService.toggleDrawer("drawer","category")
  }

  openCityDrawer(){
    this.drawerService.toggleDrawer("drawer","city")
  }
}
