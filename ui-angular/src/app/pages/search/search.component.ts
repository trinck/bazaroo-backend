import {Component, OnInit} from '@angular/core';
import {AnnouncesService} from "../../services/announces.service";
import {Announce} from "../../models/Announce";
import {Router} from "@angular/router";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent implements OnInit{


  announces :Announce[] = [];
  announces_vip:any;
  pages = 50;
  size: number = 6;
  page: number = 1;
  totalItems: number = 4 * 99;

  constructor(private announceServices: AnnouncesService, private router:Router) {
  }
  ngOnInit(): void {

     this.announceServices.announces$.subscribe(value => {
       this.announces = value.content as Announce[];
       this.pages = value.totalPages;
       this.totalItems = value.totalElements;
     });

    this.announceServices.getAnnounces(this.size, Math.abs(this.page - 1));


    this.announces_vip = [
      {title:"Pc asus i7 dernière den", description:"elegivgleigvleie", media:"assets/images/pngegg (20).png", price:25 },
      {title:"voiture futuriste elec", description:"elegivgleigvleie", media:"assets/images/voiture.jpg", price:25 },
    ]
  }


  toPage($event: number) {
    this.page = $event;
    this.announceServices.getAnnounces(this.size, Math.abs(this.page - 1));
  }



}
