import { Component, Input, OnInit } from '@angular/core';
import { Map, NavigationControl, FullscreenControl, ScaleControl, MapboxEvent, GeolocateControl } from 'mapbox-gl';
import { EventData } from "@angular/cdk/testing";
import { Announce } from "../../models/Announce";
import { AnnouncesService } from "../../services/announces.service";
import * as turf from '@turf/turf';
import {GeoZone} from "../../models/GeoZone";


@Component({
  selector: 'app-bzr-map',
  templateUrl: './bzr-map.component.html',
  styleUrl: './bzr-map.component.css'
})
export class BzrMapComponent implements OnInit {


  longitude = -7.603869; // Exemple de longitude
  latitude = 33.589886;    // Exemple de latitude
  radius = 5;
  @Input() height = 400;
  map?: Map;
  geolocation = new GeolocateControl({
    trackUserLocation: true,
    showUserLocation: true,
    showAccuracyCircle: true,
    showUserHeading: true,
    positionOptions: {
      enableHighAccuracy: true
    }

  });

  announces!: Announce[];

  currentLngLat?: [lng: number, lat: number];

  constructor(private announceService: AnnouncesService) {
  }


  onMapCreate(mapRef: Map) {
    this.map = mapRef;
    if (this.map) {
      this.map.addControl(new NavigationControl(), "top-right");
      this.map.addControl(new FullscreenControl(), "bottom-right");
      this.map.addControl(this.geolocation);


    }
  }


  onMapLoad($event: MapboxEvent & EventData) {
    this.geolocation.trigger();
  }

  ngOnInit(): void {
    this.announces = this.announceService.getAnnouncesTest();

  }


  distanceBetween(from:GeoZone, to: GeoZone ){

    const turfFrom = turf.point([from.longitude, from.latitude]);
    const turfTo = turf.point([to.longitude, to.latitude]);
    return turf.distance(turfFrom, turfTo, {units:"kilometres"});
  }

}
