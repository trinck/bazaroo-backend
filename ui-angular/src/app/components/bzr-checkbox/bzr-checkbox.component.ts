import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-bzr-checkbox',
  templateUrl: './bzr-checkbox.component.html',
  styleUrl: './bzr-checkbox.component.css'
})
export class BzrCheckboxComponent implements OnInit{


  @Input({required:true}) formGroupName$!: number;
  @Input({required:true}) formArrayName!: string;
  @Input({required:true}) formGroup!: FormGroup;
  formArray! : FormArray;

  ngOnInit(): void {
    this.formArray = (this.formGroup.get(this.formArrayName) as FormArray).controls?.at(this.formGroupName$)?.get("checkUnits") as FormArray;
  }
}
