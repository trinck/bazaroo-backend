import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-bzr-paginator',
  templateUrl: './bzr-paginator.component.html',
  styleUrl: './bzr-paginator.component.css'
})
export class BzrPaginatorComponent implements OnInit{

  @Input()totalPages = new Array(100);
  @Input()pages = 100;
  @Input()itemsPerPage = 40;
  @Input()totalItems = 40 * 99;
  @Input()currentPage = 1;
  @Input() buttonsToShow = 8;
  step = 1;
  @Output()pageChanged = new EventEmitter<number>();


  get paginationButtons(): (number | string)[] {
    const totalButtons = 7; // Number of pagination buttons to show
    const buttons: (number | string)[] = [];

    if (this.pages <= totalButtons) {
      // If total pages less than or equal to totalButtons, show all buttons
      for (let i = 1; i <= this.pages; i++) {
        buttons.push(i);
      }
    } else {
      // Always show the first and last page
      buttons.push(1);

      let startPage = Math.max(2, this.currentPage - 2);
      let endPage = Math.min(this.pages - 1, this.currentPage + 2);

      if (startPage > 2) {
        buttons.push('...');
      }

      for (let i = startPage; i <= endPage; i++) {
        buttons.push(i);
      }

      if (endPage < this.pages - 1) {
        buttons.push('...');
      }

      buttons.push(this.pages );
    }

    return buttons;
  }






  toPageIndex(index: number| string) {
    if(index == "...") return;
    this.pageChanged.emit(index as number);
  }

  toPageNext() {
    if((this.pages ) > this.currentPage){
      this.pageChanged.emit(this.currentPage + this.step);
    }

  }

  toPageBefore() {
    if(this.currentPage > 1){
      this.pageChanged.emit(this.currentPage - this.step);
    }
  }

  ngOnInit(): void {
  }
}
