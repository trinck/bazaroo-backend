import {
  AfterViewChecked,
  ChangeDetectorRef,
  Component,
  computed,
  Input,
  OnInit,
  signal,
  WritableSignal
} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {CategoryService} from "../../services/category.service";
import {CategoryField} from "../../models/CategoryField";
import {CategoryFieldRadioCheck} from "../../models/CategoryFieldRadioCheck";
import {AnnounceType} from "../../models/AnnounceType";

@Component({
  selector: 'app-bzr-step',
  templateUrl: './bzr-step.component.html',
  styleUrl: './bzr-step.component.css'
})
export class BzrStepComponent implements OnInit, AfterViewChecked{
 @Input() formGroup!: FormGroup;
 @Input() infoText? : string;
 @Input() infoTitle? : string;
 @Input() type : WritableSignal<AnnounceType|undefined> = signal(undefined);
 fieldTypesNames = ["SHORT_TEXT", "TEXT", "BOOLEAN"];
 fieldCheckName = ["CHECK_BOX","RADIO"];

fields = computed(() => {

  this.formGroup.setControl('objectFields',new FormArray([]), {emitEvent:false});
 let details : (CategoryField | CategoryFieldRadioCheck)[]  = [];
  let moreDetails :  (CategoryField | CategoryFieldRadioCheck)[] = [];
  let dtTmp = this.type()?.fields.filter(value => this.fieldTypesNames.includes(value.type));
  dtTmp?.forEach(value =>{
      let formGroup = new FormGroup({
        name: new FormControl(value.fieldName),
        dataValue: new FormControl(''),
        type: new FormControl(value.type)
      });


      (this.formGroup.get("objectFields") as FormArray).controls.push(formGroup);
      details.push(value);
  });

  let dtTmp2 =  this.type()?.fields.filter(value =>this.fieldCheckName.includes(value.type));
  dtTmp2?.forEach(value=>{
    let formArray = this.getArrayFormFromRadioCheck(value as CategoryFieldRadioCheck);
    (this.formGroup.get("objectFields") as FormArray).controls.push(new FormGroup({
      name: new FormControl(value.fieldName),
      checkUnits: formArray,
      type: new FormControl(value.type)
    }));

    if(value.type == "RADIO"){
      details.push(value);
    }else{
      moreDetails.push(value);
    }
  });

  return  {details: details, moreDetails: moreDetails};
})


 constructor(private categoryService: CategoryService, private cdr: ChangeDetectorRef) {

 }

  ngOnInit(): void {

  }



  getObjectFieldsChildIndex(field: CategoryField | CategoryFieldRadioCheck){
    return (this.formGroup.get("objectFields") as FormArray).controls.findIndex(value => {
     return  field.fieldName == (value as FormGroup).get("name")?.value;
    });
  }



  getArrayFormFromRadioCheck(radioCheck:  CategoryFieldRadioCheck){
    return new FormArray(radioCheck.fieldCheckUnits.map(value1 => new FormGroup({
      checked: new FormControl(false),
      dataValue: new FormControl(value1.dataValue),
      name: new FormControl(value1.name)
    })));

  }

  protected readonly Object = Object;

  ngAfterViewChecked(): void {
    this.cdr.detectChanges();
  }
}
