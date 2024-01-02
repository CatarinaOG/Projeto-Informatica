import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TourService {

    private variableSubject = new BehaviorSubject<number>(-1)
    index$ = this.variableSubject.asObservable();
    private maxSteps: number;

    constructor () {
        this.maxSteps = 15;
    }

    start(){
        this.variableSubject.next(0);
    }

    getIndex() {
        return this.variableSubject.value;
    }
  
    nextStep(event: any) {
        if (this.variableSubject.value === this.maxSteps) this.variableSubject.next(-1);
        else this.variableSubject.next(this.variableSubject.value + 1)
        event.stopPropagation();
    }

    previousStep(event: any) {
        if (this.variableSubject.value !== 0) this.variableSubject.next(this.variableSubject.value - 1)
        event.stopPropagation();
    }

    cancel(event: any){
        this.variableSubject.next(-1)
        event.stopPropagation();
    }
}
