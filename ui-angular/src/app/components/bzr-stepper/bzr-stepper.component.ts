import {Component, OnInit, signal, WritableSignal} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {DrawerService} from "../../services/drawer.service";
import {CategoryService} from "../../services/category.service";
import {Category} from "../../models/Category";
import {City} from "../../models/City";
import {AnnounceType} from "../../models/AnnounceType";
import {LocationService} from "../../services/location.service";
import {Street} from "../../models/Street";
import {AnnouncesService} from "../../services/announces.service";
import {MediasUploadService} from "../../services/medias-upload.service";

@Component({
  selector: 'app-bzr-stepper',
  templateUrl: './bzr-stepper.component.html',
  styleUrl: './bzr-stepper.component.css'
})
export class BzrStepperComponent implements OnInit{

  currentCategory : Category | undefined;
  announceType :WritableSignal<AnnounceType|undefined> = signal(undefined);
  currentStreet : { street: Street; city: City } | undefined;


  firstFormGroup = this._formBuilder.group({
    tel: ['', Validators.required]

  });
  secondFormGroup = this._formBuilder.group({
    price:[''],
    title:['',Validators.required],
    description:['']
  });

  constructor(private locationService:LocationService ,private _formBuilder: FormBuilder, private drawerService: DrawerService, private categoryService:CategoryService, private announcesService: AnnouncesService,private mediaService: MediasUploadService) {

    this.categoryService.selectedCategoryForm$.subscribe(value => {

        this.currentCategory = value;
        this.announceType.set(value.types?.at(0));
    })

    this.locationService.selectedCityForm$.subscribe(value => {
      this.currentStreet = value;
    })
  }

  ngOnInit(): void {
  }


  openCategoryDrawer(){
    this.drawerService.toggleDrawer("drawer","category-select-dialog");
  }

  onTypeChecked($event: Event, type: AnnounceType) {
    const inputElement = $event.target as HTMLInputElement;
    if (inputElement.checked) {
     this.announceType.set(type);

    }
  }

  openCityDrawer() {
    this.drawerService.toggleDrawer("drawer","city-select-dialog");
  }

  onSubmit(){
    if(this.secondFormGroup.valid){
      /*const formData = new FormData();
      formData.append('title', this.secondFormGroup.controls.title.value as string);
      formData.append("tel",this.firstFormGroup.controls.tel.value as string);
      formData.append("price",this.secondFormGroup.controls.price.value as string);
      formData.append("description",this.secondFormGroup.controls.description.value as string);
      formData.append("cityId",this.currentStreet?.city.id as string);*/

      let form = this._formBuilder.group({
        objectFields:new FormArray((this.secondFormGroup.get("objectFields") as unknown as FormArray).controls),
        tel: this.firstFormGroup.controls.tel,
        price:this.secondFormGroup.controls.price,
        title:this.secondFormGroup.controls.title,
        description:this.secondFormGroup.controls.description,
        streetId: this.currentStreet?.street.id

      });

      this.announcesService.createAnnounce(form, this.announceType()).subscribe(response => {

        if(this.mediaService.images.length > 0){
          const formData = new FormData();
          for (const file of this.mediaService.images) {
            formData.append("files", file);
          }

          let announceId = (response as any).id;
          this.mediaService.addAllMedias(announceId, formData).subscribe(value => {
            console.log(value);
          })
        }
      })



    }
  }





}
