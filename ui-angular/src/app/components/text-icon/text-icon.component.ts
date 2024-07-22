import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-text-icon',
  templateUrl: './text-icon.component.html',
  styleUrl: './text-icon.component.css'
})
export class TextIconComponent {

 @Input({alias:"font-size"}) font_size = "15px";
 @Input({alias:"icon-name"}) icon_name = "";
 @Input({alias:"space-between"}) gap = "5px"
}
