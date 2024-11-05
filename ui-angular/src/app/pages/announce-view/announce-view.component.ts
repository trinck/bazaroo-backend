import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AnnouncesService} from "../../services/announces.service";
import {Announce} from "../../models/Announce";
import {ShortText} from "../../models/ShortText";
import {CheckUnit} from "../../models/CheckUnit";
import {Meta, Title} from "@angular/platform-browser";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-announce-view',
  templateUrl: './announce-view.component.html',
  styleUrl: './announce-view.component.css'
})
export class AnnounceViewComponent implements OnInit{

  id! : string;
  announces!: Announce[] ;
  announce! : Announce;
  url!:string;
  fieldsDeep0 = ['SHORT_TEXT', 'TEXT', 'BOOLEAN','RADIO'];
  constructor(private activatedRoute: ActivatedRoute, private announcesService: AnnouncesService, private meta:Meta,private title:Title) {
     this.activatedRoute.paramMap.subscribe(value => {
       this.id = value.get("id")!
     })

    activatedRoute.url.subscribe(value => {
      const baseUrl = window.location.origin;
      this.url = baseUrl+"/"+value.join("/");
    })

  }

  ngOnInit(): void {

     this.announcesService.getAnnounce(this.id).subscribe(value => {
       this.announce = value as Announce;
          this.setPreviewParams();
     });

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

  protected readonly Object = Object;

  getCheckedRatioField(checkUnis: any[]){
    let listCheck = checkUnis as CheckUnit[];
   return listCheck.filter(value => {
     return value.checked;
    }).at(0);


  }

  setPreviewParams(){
    this.title.setTitle( this.announce.title as string);
    this.meta.addTags([
      { name: 'description', content: this.announce.description as string },
      { property: 'og:title', content: this.announce.title as string },
      { property: 'og:description', content: this.announce.description as string },
      { property: 'og:image', content: this.announce.medias?.at(0)?.url as string },
      { property: 'og:url', content: this.url },
      { property: 'og:type', content: 'website' },
      { name: 'twitter:card', content: 'summary_large_image' },
      { name: 'twitter:title', content: this.announce.title as string },
      { name: 'twitter:description', content: this.announce.description as string },
      { name: 'twitter:image', content: this.announce.medias?.at(0)?.url as string},
      { name: 'twitter:url', content: this.url }
    ]);

  }

}
