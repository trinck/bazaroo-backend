import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Announce} from "../models/Announce";
import {FormGroup} from "@angular/forms";
import {AnnounceType} from "../models/AnnounceType";
import {Subject} from "rxjs";
import {MapListFromServer} from "../models/MapListFromServer";


@Injectable({
  providedIn: 'root'
})
export class AnnouncesService {


  baseUrl = "http://193.203.191.159:8888/ANNOUNCES-SERVICE/announces";
  private announcesSource = new Subject<MapListFromServer>();
  announces$ = this.announcesSource .asObservable();

  constructor(private http:HttpClient) { }


  getAnnounces(size=5, page=0){
     this.http.get(`${this.baseUrl}?size=${size}&page=${page}`).subscribe(value => {
       this.announcesSource.next(value as MapListFromServer);
     })
  }

  getAnnounce(id:string){
   return this.http.get(`${this.baseUrl}/${id}`);
  }

  createAnnounce(formGroup: FormGroup, type: AnnounceType|undefined){
   return  this.http.post(`${this.baseUrl}/${type?.id}`,formGroup.value);
  }



  getAnnouncesTest():Announce[]{


    return [
      {
        title:"À vendre : Magnifique Sedan de Luxe",
        postedAt:new Date(),
        category:{title:"vehicules hau de gamme", color:"blue", iconUrl:"shop"},
        id:"ggi6c8dc6cfc",
        status:"active",
        userId:"vf7d7d677dd7ddojfff",
        fields:[
          {type:"SHORT_TEXT", id:"sb8b8r", name:"Marque et Modèle", dataValue:"Lexus ES 350"},
          {type:"SHORT_TEXT", id:"sb8b8r", name:"Année", dataValue:"2021"},
          {type:"SHORT_TEXT", id:"sb8b8r", name:"Kilométrage", dataValue:"15,000 km"},
          {type:"SHORT_TEXT", id:"sb8b8r", name:"Couleur", dataValue:"Gris métallisé"},
          {type:"RADIO", id:"sb8b8r", name:"Etat", checkUnits:[
              {name:"Neuf", dataValue:"Neuf", id:1, checked:true},
              {name:"Second choix", dataValue:"Second choix", id:2, checked:false}
            ]}

        ],
        price:38000,
        location:{id:"gb8reen8t", name:"Banco", latitude: 33.589899, longitude:-7.603870},
        medias:[{name:"hei", id:25, url:"assets/images/v1.jpeg", size:8555,path:"medias/store", type:"image/png"},
          {name:"hei", id:25, url:"assets/images/v3.jpeg", size:8555,path:"medias/store", type:"image/png"},
          {name:"hei", id:25, url:"assets/images/voiture.jpg", size:8555,path:"medias/store", type:"image/jpg"},
          {name:"marketing", id:24, url:"assets/images/Marketing.mp4", size:8555,path:"medias/store", type:"video/mp4"}
        ],
        description:"Découvrez l'élégance et la performance avec cette superbe voiture, idéale pour les amateurs de conduite raffinée et de confort haut de gamme."
      },
      {
        title:"Une voiture confortable",
        fields:[],
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
        fields:[],
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
