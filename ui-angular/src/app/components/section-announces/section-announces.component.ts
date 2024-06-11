import {Component, OnInit} from '@angular/core';
import {AnnouncesService} from "../../services/announces.service";

@Component({
  selector: 'app-section-announces',
  templateUrl: './section-announces.component.html',
  styleUrl: './section-announces.component.css'
})
export class SectionAnnouncesComponent implements OnInit{

  announces!: any;
  url!:String;

  ngOnInit(): void {
    this.announces = [
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/pngegg (20).png", price:25 },
      {title:"voiture futuriste elec", description:"elegivgleigvleie", media:"assets/images/voiture.jpg", price:25 },
      {title:"Education", description:"elegivgleigvleie", media:"assets/images/Browser stats.gif", price:25 },
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/voiture.jpg", price:25 },
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/pngegg (20).png", price:25 },
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/pngegg (29).png", price:25 },
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/pngegg (30).png", price:25 }
    ]
  }

  constructor(private announcesService: AnnouncesService) {
  }

}
