import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Announce} from "../models/Announce";


@Injectable({
  providedIn: 'root'
})
export class AnnouncesService {


  baseUrl = "";
  constructor(private http:HttpClient) { }


  getAnnounces(size=5, page=0){
    return this.http.get("announces");
  }



  getAnnouncesTest():Announce[]{


    return [
      {
        title:"Une voiture à prix bas",
        cityId: "gilgldid6f886fddd7",
        category:{title:"vehicules hau de gamme", color:"blue", iconUrl:"shop"},
        id:"ggi6c8dc6cfc",
        status:"active",
        userId:"vf7d7d677dd7ddojfff",
        price:2500,
        location:{id:"gb8reen8t", name:"Banco", latitude: 33.589899, longitude:-7.603870},
        medias:[{name:"hei", id:25, url:"assets/images/announce.PNG", size:8555,path:"medias/store", type:"image/png"},
          {name:"hei", id:25, url:"assets/images/announce.PNG", size:8555,path:"medias/store", type:"image/png"},
          {name:"hei", id:25, url:"assets/images/voiture.jpg", size:8555,path:"medias/store", type:"image/jpg"}]
      },
      {
        title:"Une voiture confortable",
        cityId: "gilgldid6f886fddd7",
        category:{title:"vehicules hau de gamme", color:"blue", iconUrl:"shop"},
        id:"ggi6c8dc6cfc",
        status:"active",
        userId:"vf7d7d677dd7ddojfff",
        price:2500,
        location:{id:"gb8reen8t", name:"Banco", latitude: 33.589886, longitude: -7.603869},
        medias:[
          {name:"hei", id:25, url:"assets/images/announce.PNG", size:8555,path:"medias/store", type:"image/png"},
          {name:"hei", id:25, url:"assets/images/announce.PNG", size:8555,path:"medias/store", type:"image/png"},
          {name:"hei", id:25, url:"assets/images/voiture.jpg", size:8555,path:"medias/store", type:"image/jpg"},
          {name:"hei", id:25, url:"assets/images/Browser stats.gif", size:8555,path:"medias/store", type:"image/gif"}
        ]
      },
      {
        title:"Une voiture de grande qualité",
        cityId: "gilgldid6f886fddd7",
        category:{title:"vehicules haut de gamme", color:"blue", iconUrl:"shop"},
        id:"ggi6c8dc6cfc",
        status:"active",
        userId:"vf7d7d677dd7ddojfff",
        price:2500,
        location:{id:"gb8reen8t", name:"Banco", latitude: 8.78151, longitude:-0.71929},
        medias:[
          {name:"hei", id:25, url:"assets/images/Browser stats.gif", size:8555,path:"medias/store", type:"image/gif"}
        ]
      }


    ]
  }

}
