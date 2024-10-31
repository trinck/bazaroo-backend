import { AnnounceType } from "./AnnounceType";
import { Category } from "./Category";
import { GeoZone } from "./GeoZone";
import {Media} from "./Media";
import {Text} from "./Text";
import {Radio_Check} from "./Radio_Check";
import {ShortText} from "./ShortText";
import {BzBoolean} from "./BzBoolean";
import {Street} from "./Street";

export interface Announce {

  id : string,
  type? : AnnounceType,
  price?: number,
  street? : Street,
  location : GeoZone,
  title? : string,
  postedAt? : Date,
  address? : string,
  tel? : string,
  description? : string,
  userId : string,
  status : string,
  category : Category,
  fields : (Text|Radio_Check|ShortText|BzBoolean)[],
  medias : Media[]
}
