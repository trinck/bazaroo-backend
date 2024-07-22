import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AnnouncesService} from "../../services/announces.service";
import {Announce} from "../../models/Announce";

@Component({
  selector: 'app-announce-view',
  templateUrl: './announce-view.component.html',
  styleUrl: './announce-view.component.css'
})
export class AnnounceViewComponent implements OnInit{

  id! : string|null;
  announces!: Announce[] ;
  announce! : Announce;
  constructor(private activatedRoute: ActivatedRoute, private announcesService: AnnouncesService) {
     this.activatedRoute.paramMap.subscribe(value => {
       this.id = value.get("id")
     })

  }

  ngOnInit(): void {

    this.announces = this.announcesService.getAnnouncesTest();
    this.announce = this.announces[0];
  }


  scrollingLeft(medias_view: HTMLDivElement) {
    let mediaContainer = document.querySelector(".media-view");
    let width = mediaContainer? mediaContainer.clientWidth +5: 300;
    medias_view.scrollBy({left:-width, behavior:'smooth'});
  }

  scrollingRight(medias_view: HTMLDivElement) {
    let mediaContainer = document.querySelector(".media-view");
    let width = mediaContainer ? mediaContainer.clientWidth +5 : 300;
    medias_view.scrollBy({left:width, behavior:'smooth'});

  }
}
