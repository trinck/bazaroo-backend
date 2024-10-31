import {CheckUnit} from "./CheckUnit";

export interface CategoryFieldRadioCheck{

  id:number,
  fieldName:string,
  type:string,
  fieldCheckUnits: CheckUnit[]
}
