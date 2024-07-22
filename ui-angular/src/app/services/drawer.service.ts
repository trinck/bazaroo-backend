import { Injectable } from '@angular/core';
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DrawerService {



  private toggleDrawerSource = new Subject<{drawerId: string, content: string}>();
  toggleDrawer$ = this.toggleDrawerSource.asObservable();

  toggleDrawer(drawerId: string, content: string) {
    this.toggleDrawerSource.next({ drawerId, content });
  }
  constructor() { }
}
