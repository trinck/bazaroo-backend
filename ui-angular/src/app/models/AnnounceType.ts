import {CategoryField} from "./CategoryField";
import {CategoryFieldRadioCheck} from "./CategoryFieldRadioCheck";

export interface AnnounceType{

   id:String,
  name:String,
 fields :(CategoryField|CategoryFieldRadioCheck)[]
}
