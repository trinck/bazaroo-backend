import {Component, Input, OnInit} from '@angular/core';
import {AnnouncesService} from "../../services/announces.service";


@Component({
  selector: 'app-announce',
  templateUrl: './announce.component.html',
  styleUrl: './announce.component.css'
})
export class AnnounceComponent implements OnInit{



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

  constructor(private announcesService: AnnouncesService) {
  }



}
