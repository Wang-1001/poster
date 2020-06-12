import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { BillInfoService } from 'app/entities/bill-info/bill-info.service';
import { IBillInfo, BillInfo } from 'app/shared/model/bill-info.model';

describe('Service Tests', () => {
  describe('BillInfo Service', () => {
    let injector: TestBed;
    let service: BillInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IBillInfo;
    let expectedResult: IBillInfo | IBillInfo[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BillInfoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BillInfo(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            billTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BillInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            billTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            billTime: currentDate,
          },
          returnedFromService
        );

        service.create(new BillInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BillInfo', () => {
        const returnedFromService = Object.assign(
          {
            billUserName: 'BBBBBB',
            billPic: 'BBBBBB',
            billWord: 'BBBBBB',
            billAuthor: 'BBBBBB',
            billTime: currentDate.format(DATE_TIME_FORMAT),
            billLayoutMode: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            billTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BillInfo', () => {
        const returnedFromService = Object.assign(
          {
            billUserName: 'BBBBBB',
            billPic: 'BBBBBB',
            billWord: 'BBBBBB',
            billAuthor: 'BBBBBB',
            billTime: currentDate.format(DATE_TIME_FORMAT),
            billLayoutMode: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            billTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a BillInfo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
