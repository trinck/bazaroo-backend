import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-announce-vip',
  templateUrl: './announce-vip.component.html',
  styleUrl: './announce-vip.component.css'
})
export class AnnounceVipComponent {


  @Input()
  announce: any;

  @Input({
    alias:"width"
  }) width:number = 340;


  @Input(
    {alias:"height"}
  ) height:number = 250;


  ngOnInit(): void {

  }

}
