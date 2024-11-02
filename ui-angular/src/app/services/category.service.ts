import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Category} from "../models/Category";
import {BehaviorSubject, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  baseUrl = "http://mts-gateway-service:8888/ANNOUNCES-SERVICE/categories?"
  categories:Category[]=[];
  constructor(private http: HttpClient) { }
  private selectedCategoryFormSource = new Subject<Category>();
  selectedCategoryForm$ = this.selectedCategoryFormSource.asObservable();
  private selectedCategorySource = new BehaviorSubject<Category | null>(null);
  selectedCategory$ = this.selectedCategorySource.asObservable();

  setSelectedCategory(category: Category) {
    this.selectedCategorySource.next(category);
  }

  setSelectedCategoryForm(category: Category) {
    this.selectedCategoryFormSource.next(category);
  }

  getCategoriesTest(): Category[]{

    return [
      {title:"Pc portable", iconUrl: "computer", color:"category-icon-violet", types: [
          {id:"d55dg5ggd55", name:"demande", fields:[
            {id:1, fieldName:"Marque", type: "SHORT_TEXT"},
              {id:2, fieldName:"Taille d'écran", type: "SHORT_TEXT"},
              {id:3, fieldName:"Ram", type: "SHORT_TEXT"},
              {id:4, fieldName:"Capacité", type: "SHORT_TEXT"},
              {id:5, fieldName:"Processeur", type: "SHORT_TEXT"},
              {id:6, fieldName:"Etat", type: "RADIO", fieldCheckUnits:[
                  {id:1, name:"Neuf", dataValue:"Neuf", checked:true},
                  {id:2, name:"Reconditionné", dataValue:"Reconditionné", checked:false},
                  {id:3, name:"Utilisé", dataValue:"Utilisé", checked:false}
                ]},
              {id:5, fieldName:"Caractéristiques", type: "CHECK_BOX", fieldCheckUnits:[
                  {id: 7, name: "Cam", dataValue: "Cam", checked: false},
                  {id: 9, name: "Ports usb c", dataValue: "Ports usb c", checked: false},
                  {id: 10, name: "Ecran tactille", dataValue: "Ecran tactille", checked: false},
                  {id: 11, name: "Entrée sim", dataValue: "Entrée sim", checked: false},
                  {id: 12, name: "2 cartes graphique", dataValue: "2 cartes graphique", checked: false},
                ]}
            ]},

          {name:"Vente",id:"ihhezg85", fields:[]}
        ]},
      {title:"Bazaroo market",color:"category-icon-green", iconUrl: "store", subCategories:[
          {title:"Chaussure",color:"category-icon-green", iconUrl: "shoe-prints", parentCategoryId:"okgkùekg7ege7gg"}
        ]},

      {title:"Vihicules",color:"category-icon-red", iconUrl: "car", subCategories:[
          {title:"Motos",color: "category-icon-red", iconUrl: "motorcycle",parentCategoryId:"r5rggrr8g8gr8", subCategories:[
              {title:"Motors 4x4",color:"category-icon-red",parentCategoryId:"e88e6g86rg8", iconUrl: "motorcycle", subCategories:[
                  {title:"Motors compete", iconUrl: "motorcycle",color:"category-icon-red",parentCategoryId:"r77rr6r335zrg"}
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
