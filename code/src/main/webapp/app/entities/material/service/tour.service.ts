import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

/**
 * Service responsible for managing the Guided Process
 */
@Injectable({ providedIn: 'root' })
export class TourService {

    /**
     * Default/starting value is -1
     */
    private variableSubject = new BehaviorSubject<number>(-1)

    /**
     * Index property , keeps track of which step the guided process is currently on
     */
    index$ = this.variableSubject.asObservable();

    /**
     * Property that stores the maximum number of steps in the guided process, if the number is increased and new steps are added, all that needs to change is the number in the constructor
     */
    private maxSteps: number;

    /**
     * Constructor for the service, it sets the maximum number of steps to 15
     * @constructor 
     */
    constructor () {
        this.maxSteps = 15;
    }

    /**
     * When the service is started, the index is changed to 0
     */
    start() : void{
        this.variableSubject.next(0);
    }

    /**
     * Getter for index value
     * @returns the index of the message currently being shown
     */
    getIndex() : number{
        return this.variableSubject.value;
    }
  
    /**
     * Function responsible for increasing the step index
     * @param {any} event 
     */
    nextStep(event: any) :void {
        if (this.variableSubject.value === this.maxSteps) { this.variableSubject.next(-1)}
        else {this.variableSubject.next(this.variableSubject.value + 1)}
        event.stopPropagation();
    }

    /**
     * Function responsible for decreasing the step index
     * @param {any} event 
     */
    previousStep(event: any) :void {
        if (this.variableSubject.value !== 0) { this.variableSubject.next(this.variableSubject.value - 1)}
        event.stopPropagation();
    }

    /**
     * Function responsible for the cancelation of the Guided Process
     * @param event 
     */
    cancel(event: any) : void{
        this.variableSubject.next(-1)
        event.stopPropagation();
    }
}
