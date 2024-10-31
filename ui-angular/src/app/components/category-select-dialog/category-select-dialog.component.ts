import {Component, OnInit} from '@angular/core';
import {Category} from "../../models/Category";
import {CategoryService} from "../../services/category.service";
import {DrawerService} from "../../services/drawer.service";

@Component({
  selector: 'app-category-select-dialog',
  templateUrl: './category-select-dialog.component.html',
  styleUrl: './category-select-dialog.component.css'
})
export class CategorySelectDialogComponent implements OnInit{

  constructor(private categoryService: CategoryService, private drawerService: DrawerService) {
  }

  ngOnInit(): void {

  }


  setCategory($event: Category) {
    /*if($event.subCategories == undefined){
      alert("pas de sous category");
      this.categoryService.setSelectedCategoryForm($event);
      this.toggleDialog();

    }*/

    this.categoryService.setSelectedCategoryForm($event);
    this.toggleDialog();

  }

  toggleDialog(){
    this.drawerService.toggleDrawer("drawer","category-select-dialog");
  }
}
