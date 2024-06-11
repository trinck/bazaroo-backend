import {Component, Inject, OnInit} from '@angular/core';
import {SearchToolbarComponent} from "../search-toolbar/search-toolbar.component";
import {MAT_DIALOG_DATA, MatDialogConfig, MatDialogRef} from "@angular/material/dialog";
import {Category} from "../../models/Category";
import {FlatTreeControl} from "@angular/cdk/tree";
import {FlatCategoryNode} from "../../models/FlatCategoryNode";
import {MatTreeFlatDataSource, MatTreeFlattener} from "@angular/material/tree";

@Component({
  selector: 'app-categories-dialog',
  templateUrl: './categories-dialog.component.html',
  styleUrl: './categories-dialog.component.css'
})
export class CategoriesDialogComponent implements OnInit{


  list: Category[]= [
    {title:"Asus i7 4eg", icon: "icon1"},
    {title:"Samsung Laoptop", icon: "icon1", subCategories:[
        {title:"Tablette", icon: "icon1", parentId:"okgkùekg7ege7gg"}
      ]},

    {title:"Samsung Laoptop", icon: "icon1", subCategories:[
        {title:"Tablette", icon: "icon1",parentId:"r5rggrr8g8gr8", subCategories:[
            {title:"Samsung Laoptop",parentId:"e88e6g86rg8", icon: "icon1", subCategories:[
                {title:"Tablette", icon: "icon1",parentId:"r77rr6r335zrg"}
              ]}
          ]}
      ]}
  ];

  private _transformer = (node: Category, level: number) => {
    return {
      expandable: !!node.subCategories && node.subCategories.length > 0,
      name: node.title,
      level: level,
    };
  };


  treeControl = new FlatTreeControl<FlatCategoryNode, FlatCategoryNode>(
    node => node.level,
    node => node.expandable
  );


  treeFlattener = new MatTreeFlattener(
    this._transformer,
    node => node.level,
    node => node.expandable,
    node => node.subCategories,
  );

  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  hasChild = (_: number, node: FlatCategoryNode) => node.expandable;


  constructor(
    public dialogRef: MatDialogRef<SearchToolbarComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Category,
  ) {
    this.list.push(this.data);
    this.dataSource.data = this.list;
    this.dialogRef.addPanelClass('border-radius-dialog');
  }

  ngOnInit(): void {
    const matDialogConfig = new MatDialogConfig()
    matDialogConfig.position = { right: `0`, top: `0` };
    this.dialogRef.updatePosition(matDialogConfig.position);
  }



}
