import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBillInfo, BillInfo } from 'app/shared/model/bill-info.model';
import { BillInfoService } from './bill-info.service';
import { BillInfoComponent } from './bill-info.component';
import { BillInfoDetailComponent } from './bill-info-detail.component';
import { BillInfoUpdateComponent } from './bill-info-update.component';

@Injectable({ providedIn: 'root' })
export class BillInfoResolve implements Resolve<IBillInfo> {
  constructor(private service: BillInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBillInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((billInfo: HttpResponse<BillInfo>) => {
          if (billInfo.body) {
            return of(billInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BillInfo());
  }
}

export const billInfoRoute: Routes = [
  {
    path: '',
    component: BillInfoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'posterServerApp.billInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BillInfoDetailComponent,
    resolve: {
      billInfo: BillInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'posterServerApp.billInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BillInfoUpdateComponent,
    resolve: {
      billInfo: BillInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'posterServerApp.billInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BillInfoUpdateComponent,
    resolve: {
      billInfo: BillInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'posterServerApp.billInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
