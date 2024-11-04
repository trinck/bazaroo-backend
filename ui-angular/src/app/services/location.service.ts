import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Country} from "../models/Country";
import {City} from "../models/City";
import {Street} from "../models/Street";
import {BehaviorSubject} from "rxjs";
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class LocationService {

 private selectedCityFormSource = new BehaviorSubject<{street:Street, city:City } | undefined>(undefined);
  selectedCityForm$ = this.selectedCityFormSource.asObservable();

  setSelectedCity(location:{street: Street, city:City}){
    this.selectedCityFormSource.next(location);
  }



  constructor(private http: HttpClient) { }
  baseUrl = environment.apiUrl+"/LOCATION-SERVICE/countries";
  streets: Street[] = [
    {id:"vb5r6b6r", name:"Pk8", cityId:"dv86rb", locations:[], zip:2700},
    {id:"5dv6ez68", name:"Akanda", cityId:"dv86rb", locations:[], zip:2700},
    {id:"8v8e886", name:"Charbonage", cityId:"dv86rb", locations:[], zip:2700},
    {id:"5ccd", name:"Sablière", cityId:"dv86rb", locations:[], zip:2700},
    {id:"4dd4d", name:"Dragage", cityId:"dv86rb", locations:[], zip:2700},
    {id:"c5ze88g", name:"Louis", cityId:"dv86rb", locations:[], zip:2700}
  ]
  countries: Country[] = [ {name:"Gabon", code:"GAB", id:"jrbe5e68tn", cities:[

      {countryId:"jrbe5e68tn", id:"dv86rb",name:"LIBREVILLE",streets:this.streets},
      {countryId:"jrbe5e68tn", id:"69efr",name:"PORT-GENTIL", streets:[]},
      {countryId:"jrbe5e68tn", id:"8dr6r8r",name:"FRANCEVILLEL", streets:[] },
      {countryId:"jrbe5e68tn", id:"ev886vr",name:"MAKOKOU", streets:[]},
      {countryId:"jrbe5e68tn", id:"f8f86f",name:"TCHIBANGA", streets:[]},
      {countryId:"jrbe5e68tn", id:"f868f6f6",name:"OYEM", streets:[]}
    ]}];


  getCountriesTest(): Country[]{
    return this.countries;
  }

  getCitiesTest(country:String="Gabon"):City[]{
    return [
      {countryId:"jrbe5e68tn", id:"dv86rb",name:"LIBREVILLE",streets:this.streets},
      {countryId:"jrbe5e68tn", id:"69efr",name:"PORT-GENTIL", streets:[
          {id:"vb5r6b6r", name:"Cafee", cityId:"69efr", locations:[], zip:2700},
          {id:"5dv6ez68", name:"Hotel", cityId:"69efr", locations:[], zip:2700}
        ]},
      {countryId:"jrbe5e68tn", id:"8dr6r8r",name:"FRANCEVILLEL", streets:[
          {id:"vb5r6b6r", name:"Cafee", cityId:"8dr6r8r", locations:[], zip:2700},
          {id:"5dv6ez68", name:"Hotel", cityId:"8dr6r8r", locations:[], zip:2700}
        ] },
      {countryId:"jrbe5e68tn", id:"ev886vr",name:"MAKOKOU", streets:[
          {id:"vb5r6b6r", name:"Louis", cityId:"ev886vr", locations:[], zip:2700},
          {id:"5dv6ez68", name:"menier", cityId:"ev886vr", locations:[], zip:2700}
        ]},
      {countryId:"jrbe5e68tn", id:"f8f86f",name:"TCHIBANGA", streets:[
          {id:"vb5r6b6r", name:"Cafee", cityId:"f8f86f", locations:[], zip:2700},
          {id:"5dv6ez68", name:"Hotel", cityId:"f8f86f", locations:[], zip:2700}
        ]},
      {countryId:"jrbe5e68tn", id:"f868f6f6",name:"OYEM", streets:[
          {id:"vb5r6b6r", name:"Cafee", cityId:"f868f6f6", locations:[], zip:2700},
          {id:"5dv6ez68", name:"Hotel", cityId:"f868f6f6", locations:[], zip:2700}
        ]}
    ]
  }

  getCountries(size:number=5, page:number=0){
    return this.http.get(`${this.baseUrl}`)
  }


}
