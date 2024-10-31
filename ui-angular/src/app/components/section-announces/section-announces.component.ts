import {Component, Input, OnInit} from '@angular/core';
import {AnnouncesService} from "../../services/announces.service";
import {Route, Router} from "@angular/router";

@Component({
  selector: 'app-section-announces',
  templateUrl: './section-announces.component.html',
  styleUrl: './section-announces.component.css'
})
export class SectionAnnouncesComponent implements OnInit{

  announces!: any;

 @Input() url:String | null = null;
  scrollStep = 30;
  ngOnInit(): void {
    this.announces = [
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/pngegg (20).png", price:25, id:"hfhld" },
      {title:"voiture futuriste elec", description:"elegivgleigvleie", media:"assets/images/voiture.jpg", price:25, id:"of5f8gn868f8nft" },
      {title:"Education", description:"elegivgleigvleie", media:"assets/images/Browser stats.gif", price:25, id:"5r88nt6en88tn6tn" },
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/voiture.jpg", price:25, id:"ireb68tnete8nrn3tne" },
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/pngegg (20).png", price:25, id:"gdbrren868entn8tent" },
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/pngegg (29).png", price:25, id:"hgudsb7rbr7rne" },
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/pngegg (30).png", price:25, id:"iuf5nen878te8nten" }
    ]
  }

  constructor(private announcesService: AnnouncesService, private router:Router) {
  }

    protected readonly alert = alert;

  scrollingRight($event: HTMLDivElement) {
    let card = document.querySelectorAll("mat-card")[0];
     $event.scrollBy({left:card.clientWidth + this.scrollStep, behavior:'smooth'});
  }

  scrollingLeft($event: HTMLDivElement) {
    let card = document.querySelectorAll("mat-card")[0];
    $event.scrollBy({left:- (card.clientWidth + this.scrollStep), behavior:'smooth'});
  }

  viewAnnounce(an: any) {
    let url = "announceView"
    this.router.navigate([url, an.id]).then(value => console.info("navigate to announce-view "+value));
  }


}
