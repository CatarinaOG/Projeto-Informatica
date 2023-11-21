import { Component, EventEmitter, OnInit, Output } from '@angular/core';
@Component({
  selector: 'options-bar',
  templateUrl: './optionsBar.html',
})
export class OptionsBar implements OnInit {
  constructor() { }
  ngOnInit(): void { }

  @Output() stringEmitter = new EventEmitter<string>();
  @Output() fileEmitter = new EventEmitter<{opType :boolean, file : File}>();
  @Output() textFilterEmitter = new EventEmitter<{filterName : string, filterText : string}>();
  @Output() abcFilterEmitter = new EventEmitter<{opType : boolean, filterValue : string}>();

  filterStatus = new Map<string,boolean>([
    ["Material Name", false],
    ["Material Description",false],
    ["ABC Classification", false],
    ["Avg Supplier Delay",false],
    ["Max Supplier Delay", false],
    ["Current SAP Safety Stock", false],
    ["Proposed Safety Stock",false],
    ["Delta Safety Stock", false],
    ["Current SAP Safety Time", false],
    ["Proposed Safety Time",false],
    ["Delta Safety Time", false],
    ["Service Level", false],
    ["Current Inventory", false],
    ["Avg. Inv. Effect After Change", false]
  ]);

    sendLoad() {
        this.stringEmitter.emit("load");
    }

    sendFilterReset() {
        this.stringEmitter.emit("FilterReset")
    }

    sendFileMessage(event : any,opType : boolean) : void {
        //true -> replace
        //false -> add
        console.log("Entrou na função")
        const file:File = event.target.files[0];
        if (file){
            this.fileEmitter.emit({opType,file})
        }
    }

    changeStatus(name : string) : void {
        for(let pair of this.filterStatus){
          this.filterStatus.set(pair[0], false);
        }
        this.filterStatus.set(name, true);
    }

    checkStatus(): boolean {
        let res = false
        this.filterStatus.forEach((value, key) => {
          if (value) res = true
        })
        return res
    }

    sendFilterText(event : any) : void {
        console.log(event.target.value)
        if (this.filterStatus.get("Material Name")){
            this.textFilterEmitter.emit({filterName : "Material Name", filterText : event.target.value})
        }
        else{
            this.textFilterEmitter.emit({filterName : "Material Description", filterText : event.target.value})      
        }
    }

    sendABCFilter(event : any,  value: string) : void {
        this.abcFilterEmitter.emit({opType: event.target.checked, filterValue : value})
    }
};