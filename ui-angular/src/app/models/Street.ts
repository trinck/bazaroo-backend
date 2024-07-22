import {GeoZone} from "./GeoZone";

export interface Street{
  id:String,
  name:String,
  zip:number,
  cityId:String,
  locations:GeoZone[]
}
