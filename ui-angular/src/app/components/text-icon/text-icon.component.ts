import {Component, Input} from '@angular/core';
import {Text} from "../../models/Text";
import {Radio_Check} from "../../models/Radio_Check";

@Component({
  selector: 'app-text-icon',
  templateUrl: './text-icon.component.html',
  styleUrl: './text-icon.component.css'
})
export class TextIconComponent {

 @Input({alias:"font-size"}) font_size = "15px";
 @Input({alias:"icon-name"}) icon_name = "";
 @Input({alias:"space-between"}) gap = "5px";

 constructor() {

 }
}
