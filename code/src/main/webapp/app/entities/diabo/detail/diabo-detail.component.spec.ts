import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiaboDetailComponent } from './diabo-detail.component';

describe('Diabo Management Detail Component', () => {
  let comp: DiaboDetailComponent;
  let fixture: ComponentFixture<DiaboDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DiaboDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ diabo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DiaboDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DiaboDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load diabo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.diabo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
