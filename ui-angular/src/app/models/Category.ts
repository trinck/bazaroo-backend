

export interface Category{

  title:String,
  icon:String,
  parentId?:String,
  subCategories?:Category[]
}
