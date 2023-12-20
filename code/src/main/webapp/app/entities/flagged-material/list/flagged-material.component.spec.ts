import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FlaggedMaterialService } from '../service/flagged-material.service';

import { FlaggedMaterialComponent } from './flagged-material.component';

describe('FlaggedMaterial Management Component', () => {
  let comp: FlaggedMaterialComponent;
  let fixture: ComponentFixture<FlaggedMaterialComponent>;
  let service: FlaggedMaterialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'flagged-material', component: FlaggedMaterialComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [FlaggedMaterialComponent],
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
      .overrideTemplate(FlaggedMaterialComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FlaggedMaterialComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FlaggedMaterialService);

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
    expect(comp.flaggedMaterials?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to flaggedMaterialService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getFlaggedMaterialIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getFlaggedMaterialIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
