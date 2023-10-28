import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DiaboFormService } from './diabo-form.service';
import { DiaboService } from '../service/diabo.service';
import { IDiabo } from '../diabo.model';

import { DiaboUpdateComponent } from './diabo-update.component';

describe('Diabo Management Update Component', () => {
  let comp: DiaboUpdateComponent;
  let fixture: ComponentFixture<DiaboUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let diaboFormService: DiaboFormService;
  let diaboService: DiaboService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DiaboUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DiaboUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DiaboUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    diaboFormService = TestBed.inject(DiaboFormService);
    diaboService = TestBed.inject(DiaboService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const diabo: IDiabo = { id: 456 };

      activatedRoute.data = of({ diabo });
      comp.ngOnInit();

      expect(comp.diabo).toEqual(diabo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDiabo>>();
      const diabo = { id: 123 };
      jest.spyOn(diaboFormService, 'getDiabo').mockReturnValue(diabo);
      jest.spyOn(diaboService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diabo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: diabo }));
      saveSubject.complete();

      // THEN
      expect(diaboFormService.getDiabo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(diaboService.update).toHaveBeenCalledWith(expect.objectContaining(diabo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDiabo>>();
      const diabo = { id: 123 };
      jest.spyOn(diaboFormService, 'getDiabo').mockReturnValue({ id: null });
      jest.spyOn(diaboService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diabo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: diabo }));
      saveSubject.complete();

      // THEN
      expect(diaboFormService.getDiabo).toHaveBeenCalled();
      expect(diaboService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDiabo>>();
      const diabo = { id: 123 };
      jest.spyOn(diaboService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diabo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(diaboService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
