import {Component, HostListener} from '@angular/core';
import {MediasUploadService} from "../../services/medias-upload.service";

@Component({
  selector: 'app-medias-uploader',
  templateUrl: './medias-uploader.component.html',
  styleUrl: './medias-uploader.component.css'
})
export class MediasUploaderComponent {

  mediasPreview: string[] = [];
  images : File[] = [];
  files!: {imagePreview:string[], images: File[]} ;
  protected dragAreaClass: string = "";
  textLabel = "Faites glisser une image à télécharger ou cliquez pour en rechercher une";
  constructor(private mediasUploadService: MediasUploadService) {}

  onDragOver(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    this.dragAreaClass = 'dragging';
    this.textLabel = "Déposez ici";
  }

  onDragLeave(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    this.dragAreaClass = '';
    this.textLabel = "Faites glisser une image à télécharger ou cliquez pour en rechercher une";
  }

 onDrop(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    this.dragAreaClass = '';
   this.textLabel = "Faites glisser une image à télécharger ou cliquez pour en rechercher une";
    const files = event.dataTransfer?.files;
    if (files) {
      this.files = this.mediasUploadService.previewMedias(files);
    }
  }






  onFileChange(event: any) {
    const files = event.target.files;
    if (files) {
      this.files = this.mediasUploadService.previewMedias(files);
    }
  }


  removeImage(index: number) {

    this.files?.imagePreview.splice(index, 1);
    this.files?.images.splice(index, 1);

  }
}
