import {Component, Input, OnInit} from '@angular/core';
import {MediasUploadService} from "../../services/medias-upload.service";
import {FormGroup} from "@angular/forms";
import {LocationService} from "../../services/location.service";
import {Street} from "../../models/Street";
import {City} from "../../models/City";

@Component({
  selector: 'app-preview-announce',
  templateUrl: './preview-announce.component.html',
  styleUrl: './preview-announce.component.css'
})
export class PreviewAnnounceComponent implements OnInit{

  medias! : {imagePreview: string[], images: File[]};
  @Input({required:true})step2FormGroup!:FormGroup;
  @Input({required:true})step1FormGroup!:FormGroup;
  location: {street: Street,city: City } | undefined ;

  constructor(private mediaUploadService: MediasUploadService, private locationService: LocationService) {
  }

  ngOnInit(): void {
    this.medias = this.mediaUploadService.getMedias();
    this.locationService.selectedCityForm$.subscribe(value => {
      this.location = value;
    })
  }
}
