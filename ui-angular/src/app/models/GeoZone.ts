import {Street} from "./Street";

export interface GeoZone{
  id:String,
  latitude:number,
  longitude:number,
  name:String,
  streetId?:String
}
