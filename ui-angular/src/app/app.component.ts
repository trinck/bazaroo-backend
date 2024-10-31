import {Component, OnInit, ViewChild} from '@angular/core';
import {DrawerService} from "./services/drawer.service";
import {MatDrawer} from "@angular/material/sidenav";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements  OnInit{
  title = 'front-end';

  @ViewChild('drawer') drawer: MatDrawer | undefined;
  @ViewChild('sidenav') sidenav: MatDrawer | undefined;
  drawerContent!: string;
  sidenavContent!: string;

  constructor(private drawerService: DrawerService, protected activatedRoute: ActivatedRoute) {


  }


  ngOnInit() {
    this.drawerService.toggleDrawer$.subscribe(({ drawerId, content }) => {
      if (drawerId === 'drawer') {
        this.drawerContent = content;
        this.drawer?.toggle();
      } else if (drawerId === 'sidenav') {
        this.sidenavContent = content;
        this.sidenav?.toggle();
      }
    });
  }
}
