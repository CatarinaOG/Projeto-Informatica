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

    start() : void{
        this.variableSubject.next(0);
    }

    getIndex() : number{
        return this.variableSubject.value;
    }
  
    nextStep(event: any) :void {
        if (this.variableSubject.value === this.maxSteps) { this.variableSubject.next(-1)}
        else {this.variableSubject.next(this.variableSubject.value + 1)}
        event.stopPropagation();
    }

    previousStep(event: any) :void {
        if (this.variableSubject.value !== 0) { this.variableSubject.next(this.variableSubject.value - 1)}
        event.stopPropagation();
    }

    cancel(event: any) : void{
        this.variableSubject.next(-1)
        event.stopPropagation();
    }
}
