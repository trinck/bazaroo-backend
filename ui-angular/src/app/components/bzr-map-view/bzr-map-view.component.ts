import {Component, Input, OnInit} from '@angular/core';
import {FullscreenControl, GeolocateControl, Map, MapboxEvent, NavigationControl} from "mapbox-gl";
import {AnnouncesService} from "../../services/announces.service";
import {GeoZone} from "../../models/GeoZone";

@Component({
  selector: 'app-bzr-map-view',
  templateUrl: './bzr-map-view.component.html',
  styleUrl: './bzr-map-view.component.css'
})
export class BzrMapViewComponent implements OnInit{


  longitude = -7.603869; // Exemple de longitude
  latitude = 33.589886;    // Exemple de latitude
  @Input()geoZone!: GeoZone;
  @Input() height = 400;
  map?: Map;
  constructor(private announceService: AnnouncesService) {
  }


  onMapCreate(mapRef: Map) {
    this.map = mapRef;
    if (this.map) {
      this.map.addControl(new NavigationControl(), "top-right");
    }
  }

  ngOnInit(): void {

  }


}
