import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent implements OnInit{


  announces:any;
  announces_vip:any;

  ngOnInit(): void {

    this.announces = [
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/pngegg (20).png", price:25 },
      {title:"voiture futuriste elec", description:"elegivgleigvleie", media:"assets/images/voiture.jpg", price:25 },
      {title:"Education", description:"elegivgleigvleie", media:"assets/images/Browser stats.gif", price:25 },
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/voiture.jpg", price:25 },
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/pngegg (20).png", price:25 },
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/icons/logo1.png", price:25 },
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/pngegg (30).png", price:25 }
    ]

    this.announces_vip = [
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/pngegg (20).png", price:25 },
      {title:"voiture futuriste elec", description:"elegivgleigvleie", media:"assets/images/voiture.jpg", price:25 },
    ]
  }


}
