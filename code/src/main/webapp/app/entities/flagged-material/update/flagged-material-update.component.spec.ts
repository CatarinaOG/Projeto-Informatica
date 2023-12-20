import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FlaggedMaterialFormService } from './flagged-material-form.service';
import { FlaggedMaterialService } from '../service/flagged-material.service';
import { IFlaggedMaterial } from '../flagged-material.model';

import { FlaggedMaterialUpdateComponent } from './flagged-material-update.component';

describe('FlaggedMaterial Management Update Component', () => {
  let comp: FlaggedMaterialUpdateComponent;
  let fixture: ComponentFixture<FlaggedMaterialUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let flaggedMaterialFormService: FlaggedMaterialFormService;
  let flaggedMaterialService: FlaggedMaterialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FlaggedMaterialUpdateComponent],
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
      .overrideTemplate(FlaggedMaterialUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FlaggedMaterialUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    flaggedMaterialFormService = TestBed.inject(FlaggedMaterialFormService);
    flaggedMaterialService = TestBed.inject(FlaggedMaterialService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const flaggedMaterial: IFlaggedMaterial = { id: 456 };

      activatedRoute.data = of({ flaggedMaterial });
      comp.ngOnInit();

      expect(comp.flaggedMaterial).toEqual(flaggedMaterial);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFlaggedMaterial>>();
      const flaggedMaterial = { id: 123 };
      jest.spyOn(flaggedMaterialFormService, 'getFlaggedMaterial').mockReturnValue(flaggedMaterial);
      jest.spyOn(flaggedMaterialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ flaggedMaterial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: flaggedMaterial }));
      saveSubject.complete();

      // THEN
      expect(flaggedMaterialFormService.getFlaggedMaterial).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(flaggedMaterialService.update).toHaveBeenCalledWith(expect.objectContaining(flaggedMaterial));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFlaggedMaterial>>();
      const flaggedMaterial = { id: 123 };
      jest.spyOn(flaggedMaterialFormService, 'getFlaggedMaterial').mockReturnValue({ id: null });
      jest.spyOn(flaggedMaterialService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ flaggedMaterial: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: flaggedMaterial }));
      saveSubject.complete();

      // THEN
      expect(flaggedMaterialFormService.getFlaggedMaterial).toHaveBeenCalled();
      expect(flaggedMaterialService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFlaggedMaterial>>();
      const flaggedMaterial = { id: 123 };
      jest.spyOn(flaggedMaterialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ flaggedMaterial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(flaggedMaterialService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
