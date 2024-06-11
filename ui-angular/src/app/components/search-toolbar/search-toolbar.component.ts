import {Component, signal} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {CategoriesDialogComponent} from "../categories-dialog/categories-dialog.component";
import {Category} from "../../models/Category";
import {City} from "../../models/City";
import {CitiesDialogComponent} from "../cities-dialog/cities-dialog.component";

@Component({
  selector: 'app-search-toolbar',
  templateUrl: './search-toolbar.component.html',
  styleUrl: './search-toolbar.component.css'
})
export class SearchToolbarComponent {
  category: Category= {title:"Informatique", icon:"url de l'icon",
    subCategories:[
      {title:"Asus i7 4eg", icon: "icon1"},
      {title:"Samsung Laoptop", icon: "icon1", subCategories:[
          {title:"Tablette", icon: "icon1",parentId:"f14ff8fv688"}
        ]},
      {title:"Tablette apple", icon: "icon1"}
    ]}
  city: City = {name:"Libreville", location:{name:"banco", latitude:85, longitude:3.5}}
  constructor(public dialog:MatDialog) {
  }

   openCategoryDialog(){
   const dialog = this.dialog.open(CategoriesDialogComponent,{data:{title:this.category.title,icon:this.category.icon}})
  }
  openCitiesDialog(){
    const dialog = this.dialog.open(CitiesDialogComponent,{data:{name:this.city.name,location: this.city.location}})
  }

}
