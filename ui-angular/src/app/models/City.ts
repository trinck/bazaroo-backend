import {Street} from "./Street";

export interface City {
  name:String,
  id:String,
  countryId:String,
  streets?: Street[]
}
