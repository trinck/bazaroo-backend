import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Category} from "../models/Category";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  baseUrl = "http://localhost:8888/ANNOUNCES-SERVICE/categories?"
  categories:Category[]=[];
  constructor(private http: HttpClient) { }

  private selectedCategorySource = new BehaviorSubject<Category | null>(null);
  selectedCategory$ = this.selectedCategorySource.asObservable();

  setSelectedCategory(category: Category) {
    this.selectedCategorySource.next(category);
  }

  getCategoriesTest(): Category[]{

    return [
      {title:"Pc portable", iconUrl: "computer/category-icon-violet", color:"category-icon-violet"},
      {title:"Bazaroo market",color:"category-icon-green", iconUrl: "store/category-icon-green", subCategories:[
          {title:"Chaussure",color:"category-icon-green", iconUrl: "sports_esports/category-icon-green", parentCategoryId:"okgkùekg7ege7gg"}
        ]},

      {title:"Vihicules",color:"category-icon-red", iconUrl: "directions_car/category-icon-red", subCategories:[
          {title:"Motos",color: "category-icon-red", iconUrl: "two_wheeler/category-icon-red",parentCategoryId:"r5rggrr8g8gr8", subCategories:[
              {title:"Motors 4x4",color:"category-icon-red",parentCategoryId:"e88e6g86rg8", iconUrl: "two_wheeler/category-icon-red", subCategories:[
                  {title:"Motors compete", iconUrl: "two_wheeler/category-icon-red",color:"category-icon-red",parentCategoryId:"r77rr6r335zrg"}
                ]}
            ]}
        ]}
    ]
  }


  getCategories(size:number=5, page:number=0){
   return this.http.get(`${this.baseUrl+"size="+size+"&page="+page}`)
  }

  toCategory(data:any):Category{
    return {...data}
  }

}
