import {Component, Input} from '@angular/core';
import {Announce} from "../../models/Announce";

@Component({
  selector: 'app-medias-view-slide',
  templateUrl: './medias-view-slide.component.html',
  styleUrl: './medias-view-slide.component.css'
})
export class MediasViewSlideComponent {


@Input()announce!: Announce;


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

    protected readonly alert = alert;
}
