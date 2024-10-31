import {Component, Input, OnInit} from '@angular/core';
import {AnnouncesService} from "../../services/announces.service";
import {Announce} from "../../models/Announce";
import {Router} from "@angular/router";


@Component({
  selector: 'app-announce',
  templateUrl: './announce.component.html',
  styleUrl: './announce.component.css'
})
export class AnnounceComponent implements OnInit{



  @Input( {required:true})
   announce?: Announce;

  @Input({
    alias:"width"
  }) width:number = 340;


  @Input(
    {alias:"height"}
  ) height:number = 250;



  ngOnInit(): void {

  }

  constructor(private announcesService: AnnouncesService, private router: Router) {
  }


  viewAnnounce() {
    let url = "announceView";
    this.router.navigate([url, this.announce?.id]).then(value => console.info("navigate to announce-view "+value));
  }

  protected readonly alert = alert;
}
