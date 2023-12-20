import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FlaggedMaterialDetailComponent } from './flagged-material-detail.component';

describe('FlaggedMaterial Management Detail Component', () => {
  let comp: FlaggedMaterialDetailComponent;
  let fixture: ComponentFixture<FlaggedMaterialDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FlaggedMaterialDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ flaggedMaterial: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FlaggedMaterialDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FlaggedMaterialDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load flaggedMaterial on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.flaggedMaterial).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
