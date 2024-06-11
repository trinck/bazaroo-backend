import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogConfig, MatDialogRef} from "@angular/material/dialog";
import {SearchToolbarComponent} from "../search-toolbar/search-toolbar.component";
import {Category} from "../../models/Category";
import {City} from "../../models/City";

@Component({
  selector: 'app-cities-dialog',
  templateUrl: './cities-dialog.component.html',
  styleUrl: './cities-dialog.component.css'
})
export class CitiesDialogComponent implements OnInit{
  ngOnInit(): void {
    const matDialogConfig = new MatDialogConfig()
    matDialogConfig.position = { right: `0`, top: `0` };
    this.dialogRef.updatePosition(matDialogConfig.position);
  }

  constructor(public dialogRef: MatDialogRef<SearchToolbarComponent>,
              @Inject(MAT_DIALOG_DATA) public data: City,) {
    this.dialogRef.addPanelClass('border-radius-dialog');
  }

}
