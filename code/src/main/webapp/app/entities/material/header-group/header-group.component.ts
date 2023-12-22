import { Component, EventEmitter, Input, OnInit, Output, ViewChild, OnDestroy } from '@angular/core';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { TourService } from '../service/tour.service';
import { tourMessages } from '../data/tourMessage';
import { Subscription } from 'rxjs';

@Component({
  selector: 'header-group',
  templateUrl: './header-group.component.html',
})
export class HeaderGroup implements OnInit {

  @Input() headerName!: string; 
  @Input() visibilityValue!: boolean | undefined;
  @Input() groupName! : string;
  @Output() visibilityEmitter = new EventEmitter<{name : string, visibility : boolean}>();

  @ViewChild('t1', { static: true }) headerToolTip!: NgbTooltip; 
  @ViewChild('t2', { static: true }) btnToolTip!: NgbTooltip;

  tourMsgs = tourMessages
  index: number = 0;

  private subscription: Subscription = new Subscription();

  constructor(public tourService: TourService) { }
  ngOnInit(): void {
    this.subscription = this.tourService.index$.subscribe(value =>  {
      this.index = value;
      this.defineStepTour(value);
    })
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe()
  }

  sendVisibilityMessage() : void {
    this.visibilityEmitter.emit({name : this.groupName,visibility : !this.visibilityValue})
  }
  
  defineStepTour(value: number) {
    if(this.headerToolTip) this.headerToolTip.close()
    if(this.btnToolTip) this.btnToolTip.close()

    switch(value) {
      case 0:
        document.getElementById("Material Info")?.focus()
        if (this.headerToolTip && this.headerName === "Material Info") this.headerToolTip.open()
        break;

      case 1:
        if (this.btnToolTip && this.headerName === "Material Info") this.btnToolTip.open()
        break;
       
      default:
        break;
    }
  }


};