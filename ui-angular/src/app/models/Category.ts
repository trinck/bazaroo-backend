import {AnnounceType} from "./AnnounceType";


export interface Category{

  title:String,
  id?:String,
  iconUrl:String,
  color:String,
  parentCategoryId?:String,
  subCategories?:Category[],
  description?:String,
  types?: AnnounceType[]

}
