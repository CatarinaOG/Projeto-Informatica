import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { TourService } from '../service/tour.service';
import { tourMessages } from '../data/tourMessage';
import { Subscription } from 'rxjs';

/**
 * Component that displays a header for a group of columns
 */
@Component({
  selector: 'jhi-header-group',
  templateUrl: './header-group.component.html',
})
export class HeaderGroupComponent implements OnInit ,OnDestroy {

  /**
  * Header name to be displayed, passed by the jhi-material component
  * @type {string}
   */
  @Input() headerName!: string; 

  /**
   * Visibility value passed by the jhi-material component, used in the render of the expand/colapse icon
   * @type {boolean | undefined}
   */
  @Input() visibilityValue!: boolean | undefined;

  /**
   * Group name "code", passed by the jhi-material component
   */
  @Input() groupName! : string;

  /**
   * Emitter that sends the visibility value of this group header to the jhi-material component
   * @type {EventEmitter<{name : string, visibility : boolean}>}
   */
  @Output() visibilityEmitter = new EventEmitter<{name : string, visibility : boolean}>();

  /**
   * Reference to the tooltip used to display the guided process step regarding the column group header
   * @type {NgbTooltip}
   */
  @ViewChild('t1', { static: true }) headerToolTip!: NgbTooltip; 

  /**
   * Reference to the tooltip used to display the guided process step regarding the expand/colapse button
   * @type {NgbTooltip}
   */
  @ViewChild('t2', { static: true }) btnToolTip!: NgbTooltip;

  /**
   * Constant that stores the messages used in the guided process tooltips
   */
  tourMsgs = tourMessages

  /**
   * Index of the guided process steps 
   * @type {number}
   */
  index = 0;

  /**
   * Subscription property where the index subscription will be stored
   */
  private subscription: Subscription = new Subscription();

  /**
   * Constructor of the component.
   * @constructor
   * @param {TourService} tourService 
   */
  constructor(protected tourService: TourService) { }

  /**
   * Function that executes when the component is initialized, subscribes the component to the index of the guided process tour service
   */
  ngOnInit(): void {
    this.subscription = this.tourService.index$.subscribe(value =>  {
      this.index = value;
      this.defineStepTour(value);
    })
  }

  /**
   * Function that executes when the component is destroyed , unsubscribes the component from the index subscription
   */
  ngOnDestroy(): void {
    this.subscription.unsubscribe()
  }

  /**
   * Function that uses the visibilityEmitter to send the groupName and the visibility (true or false) when the user expands or colapses the column group
   */
  sendVisibilityMessage() : void {
    this.visibilityEmitter.emit({name : this.groupName,visibility : !this.visibilityValue})
  }

  /**
   * Function that determines which tooltip is to be triggered according to the index value the component is subscribed to
   * @param {number} value 
   */
  defineStepTour(value: number) : void {
    this.headerToolTip.close()
    this.btnToolTip.close()

    switch(value) {
      case 0:
        document.getElementById("Material Info")?.focus()
        if (this.headerName === "Material Info") {
          this.headerToolTip.open()
        }
        break;

      case 1:
        if (this.headerName === "Material Info"){
          this.btnToolTip.open()
        } 
        break;
       
      default:
        break;
    }
  }

};