import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class MediasUploadService {

   imagePreviews: string[] = [];
   images: File[]= [] ;
  baseUrl = environment.apiUrl+"/IMAGES-SERVICE/mediaStore/adverts/";
  constructor(private http:HttpClient) { }

  previewMedias(files: FileList) {

    Array.from(files).forEach(file => {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imagePreviews.push(e.target.result);
        this.images.push(file);
      };
      reader.readAsDataURL(file);
    });
    return {imagePreview:this.imagePreviews, images: this.images};
  }


  getMedias(){
    return {imagePreview:this.imagePreviews, images: this.images};
  }

  addAllMedias(announceId: string, form: FormData){

    return this.http.post(`${this.baseUrl}/${announceId}/save-all`, form);
  }

}
