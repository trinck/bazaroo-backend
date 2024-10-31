import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-bzr-radio',
  templateUrl: './bzr-radio.component.html',
  styleUrl: './bzr-radio.component.css'
})
export class BzrRadioComponent implements OnInit{

 @Input({required:true}) formGroupName$!: number;
 @Input({required:true}) formArrayName!: string;
 @Input({required:true}) formGroup!: FormGroup;
 formArray! : FormArray;

  private currentRadioChecked:null|number = null;

  ngOnInit(): void {
    this.formArray = (this.formGroup.get(this.formArrayName) as FormArray).controls?.at(this.formGroupName$)?.get("checkUnits") as FormArray;
    this.formArray.valueChanges.subscribe(values => this.updateRadioSelection(values) );
  }




  updateRadioSelection(values: any[]) {
    if(this.currentRadioChecked == null) {
      this.currentRadioChecked = values.findIndex(radio => radio.checked);
    } else{
      this.currentRadioChecked = values.findIndex((radio, index) => radio.checked && index!==this.currentRadioChecked);
    }

    this.formArray?.controls.forEach((control, index) => {
      if (index !== this.currentRadioChecked && control.get("checked")?.value === true) {
        control.get("checked")?.setValue(false, { emitEvent: false });
      }
    });

  }

}
