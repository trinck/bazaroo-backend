import {Component, OnInit, ViewChild} from '@angular/core';
import {City} from "../../models/City";
import {Street} from "../../models/Street";
import {MatListOption, MatSelectionList} from "@angular/material/list";
import {DrawerService} from "../../services/drawer.service";
import {LocationService} from "../../services/location.service";
import {Country} from "../../models/Country";

@Component({
  selector: 'app-city-select-dialog',
  templateUrl: './city-select-dialog.component.html',
  styleUrl: './city-select-dialog.component.css'
})
export class CitySelectDialogComponent implements OnInit{

  cities?: City[];
  streets?:Street[]= undefined;
  @ViewChild("listStreet") listStreet?: MatSelectionList;
  slideOut = false;
  currentCity! :City;

  ngOnInit(): void {
    //this.cities = this.locationService.getCitiesTest();

    this.locationService.getCountries().subscribe(value => {
      this.cities = (value as Country[]).at(0)?.cities;
    })

  }

  constructor(public drawerService: DrawerService, private locationService:LocationService) {

  }
  clear (){

  }



  selectAll() {
  }

  next(c: City) {
    this.currentCity = c;
    this.slideOut = true;
  }

  back() {
    this.slideOut= false;
  }

  onSelectedStreet(street:Street){
    this.locationService.setSelectedCity({street:street, city: this.currentCity});
  }
}
