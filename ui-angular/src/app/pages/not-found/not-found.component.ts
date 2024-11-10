import {Component, OnInit} from '@angular/core';
import {Meta} from "@angular/platform-browser";

@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html',
  styleUrl: './not-found.component.css'
})
export class NotFoundComponent implements OnInit{

  constructor(private meta:Meta) {

  }

  ngOnInit(): void {
    this.meta.addTags([{name: 'prerender-status-code', content:"404"}]);
  }
}
