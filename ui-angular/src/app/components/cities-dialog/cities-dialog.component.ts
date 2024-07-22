import {Component, Inject, OnInit, viewChild, ViewChild} from '@angular/core';
import {DrawerService} from "../../services/drawer.service";
import {LocationService} from "../../services/location.service";
import {City} from "../../models/City";
import {MatListOption, MatSelectionList} from "@angular/material/list";
import {Street} from "../../models/Street";

@Component({
  selector: 'app-cities-dialog',
  templateUrl: './cities-dialog.component.html',
  styleUrl: './cities-dialog.component.css'
})
export class CitiesDialogComponent implements OnInit{

  cities?: City[];
  streets?:Street[]= undefined;
  @ViewChild("listCity") list?: MatSelectionList;
  @ViewChild("listStreet") listStreet?: MatSelectionList;
  slideOut = false;
  currentListOption? :MatListOption;

  ngOnInit(): void {
    this.cities = this.locationService.getCitiesTest();

  }

  constructor(public drawerService: DrawerService, private locationService:LocationService) {

  }
clear (){
    this.list?.selectedOptions.clear();
    this.listStreet?.selectedOptions.clear();
}



  selectAll() {
    this.list?.selectAll();
  }

  next(c: City, lO:MatListOption) {

    if( this.list?.selectedOptions.isEmpty() || (this.list?.selectedOptions?.selected?.length == 1 && this.list?.selectedOptions?.selected.at(0) === lO)){
      this.streets = c.streets;
      this.slideOut = true;
    }else if(this.listStreet?.selectedOptions){
      this.listStreet.deselectAll();
    }
    this.currentListOption = lO;
  }

  back() {
    if(!this.listStreet?.selectedOptions.isEmpty()){
      this.currentListOption?._setSelected(true);
    }
    this.slideOut= false;
  }

}
