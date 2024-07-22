import {AfterContentInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {Breadcrumb} from "../../models/Breadcrumb";
import {ActivatedRoute, ActivatedRouteSnapshot, NavigationEnd, Router} from "@angular/router";
import {filter} from "rxjs";
import {CategoryService} from "../../services/category.service";
import {Category} from "../../models/Category";

@Component({
  selector: 'app-breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrl: './breadcrumb.component.css'
})
export class BreadcrumbComponent implements OnInit, AfterContentInit{

  breadcrumbs: Breadcrumb[] = [];
  currentCategory: Category | null=null;




  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute, private categoryService: CategoryService
  ) { }

  ngOnInit() {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.breadcrumbs = this.buildBreadcrumbs(this.activatedRoute.root.snapshot.root);
    });

    this.categoryService.selectedCategory$.subscribe(category => {
      this.currentCategory = category;
      this.breadcrumbs = this.buildBreadcrumbs(this.activatedRoute.root.snapshot.root);
    });


  }

  buildBreadcrumbs(route: ActivatedRouteSnapshot, url: string = '', breadcrumbs: Breadcrumb[] = []): Breadcrumb[] {
    const children: ActivatedRouteSnapshot[] = route.children;

    if (children.length === 0) {
      return breadcrumbs;
    }

    for (const child of children) {
      if (child.routeConfig && child.routeConfig.data && child.routeConfig.data['breadcrumb']) {
        const routeURL: string = child.url.map(segment => segment.path).join('/');
        url += `/${routeURL}`;

        const breadcrumb: Breadcrumb = {
          label: child.routeConfig.data['breadcrumb'],
          url: url
        };
        breadcrumbs.push(breadcrumb);
      }

      return this.buildBreadcrumbs(child, url, breadcrumbs);
    }

    return breadcrumbs;
  }

  ngAfterContentInit(): void {

  }


}
