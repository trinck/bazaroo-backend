import { AnnounceType } from "./AnnounceType";
import { Category } from "./Category";
import { Field } from "./Field";
import { GeoZone } from "./GeoZone";
import {Media} from "./Media";

export interface Announce {

  id : String,
  type? : AnnounceType,
  price?: Number,
  cityId : String,
  locationId? : String,
  location : GeoZone,
  title? : String,
  postedAt? : Date,
  address? : String,
  description? : String,
  userId : String,
  status : String,
  category : Category,
  fields? : Field[],
  medias? : Media[]
}
