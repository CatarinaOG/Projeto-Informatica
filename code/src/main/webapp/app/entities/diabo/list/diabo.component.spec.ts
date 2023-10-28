import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DiaboService } from '../service/diabo.service';

import { DiaboComponent } from './diabo.component';

describe('Diabo Management Component', () => {
  let comp: DiaboComponent;
  let fixture: ComponentFixture<DiaboComponent>;
  let service: DiaboService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'diabo', component: DiaboComponent }]), HttpClientTestingModule],
      declarations: [DiaboComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(DiaboComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DiaboComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DiaboService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.diabos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to diaboService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getDiaboIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getDiaboIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
