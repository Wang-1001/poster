import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBillType, BillType } from 'app/shared/model/bill-type.model';
import { BillTypeService } from './bill-type.service';
import { BillTypeComponent } from './bill-type.component';
import { BillTypeDetailComponent } from './bill-type-detail.component';
import { BillTypeUpdateComponent } from './bill-type-update.component';

@Injectable({ providedIn: 'root' })
export class BillTypeResolve implements Resolve<IBillType> {
  constructor(private service: BillTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBillType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((billType: HttpResponse<BillType>) => {
          if (billType.body) {
            return of(billType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BillType());
  }
}

export const billTypeRoute: Routes = [
  {
    path: '',
    component: BillTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'posterServerApp.billType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BillTypeDetailComponent,
    resolve: {
      billType: BillTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'posterServerApp.billType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BillTypeUpdateComponent,
    resolve: {
      billType: BillTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'posterServerApp.billType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BillTypeUpdateComponent,
    resolve: {
      billType: BillTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'posterServerApp.billType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
