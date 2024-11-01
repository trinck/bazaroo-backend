import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {MatTree, MatTreeFlatDataSource, MatTreeFlattener} from "@angular/material/tree";
import {Category} from "../../models/Category";
import {FlatTreeControl} from "@angular/cdk/tree";
import {FlatCategoryNode} from "../../models/FlatCategoryNode";
import {CategoryService} from "../../services/category.service";

@Component({
  selector: 'app-category-tree',
  templateUrl: './category-tree.component.html',
  styleUrl: './category-tree.component.css'
})
export class CategoryTreeComponent implements OnInit{




  @Output()nodeChange: EventEmitter<Category> = new EventEmitter<Category>();

  @ViewChild("tree1") tree1 : MatTree<any>|undefined=undefined;

  private _transformer = (node: Category, level: number) => {
    return {
      expandable: !!node.subCategories && node.subCategories.length > 0,
      name: node.title,
      level: level,
      category: node,
      parentId: node.parentCategoryId,
      iconUrl: node.iconUrl,
      color: node.color
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

  currentLevel = 0;
  currentNode: FlatCategoryNode | null = null;
  slideTree =false;
  currentDataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  constructor(private categoriesService:CategoryService
  ) {

  }

  ngOnInit(): void {
     this.categoriesService.getCategories().subscribe(value => {
     this.dataSource.data = (value as any).content as Category[];
     })

    //this.dataSource.data = this.categoriesService.getCategories()

  }


  onNodeClick(node: FlatCategoryNode, $event: MouseEvent) {

    this.currentNode = node;
    if (this.treeControl.getLevel(node) === 1 && node.expandable && this.currentLevel == 0) {
      this.currentLevel = 1;
      this.slideTree=true;
      this.currentDataSource.data = [node.category]

    }
    this.nodeChange.emit(node.category);
  }

  goBack() {
    this.currentLevel = 0;
    this.currentNode = null;
    this.slideTree=false;
  }

}
