import {Category} from "./Category";

export interface FlatCategoryNode{

  name:String, expandable:boolean,level:number, category:Category, parentId:String|undefined, iconUrl:String, color:String

}
