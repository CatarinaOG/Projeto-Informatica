/**
 * Constant that stores all the messages used in the tooltips in the Guided Process
 */
export const tourMessages: Map<number, string> = new Map<number, string>([
    [0, "The columns are organized in groups. In this one, you can see basic information about the Material"],
    [1, "Here you can colapse and expand each of the column groups"],
    [2, "Here you can be redirected to the SAP page of the Material"],
    [3, "Here you can edit values and information about a Material"],
    [4, "Here you can edit the values of the columns (New SAP Safety Time/Stock) by clicking on each one. When a value is edited the number will turn blue"],
    [5, "When a value of a material has been edited, you can select that row for submission"],
    [6, "If you choose, you can also flag the material as a special case, and put a expiration date for that flag in the Pop Up"],
    [7, "Here you can add or edit a comment on the Material by pressing the pencil button. If a comment is truncated, you can hover over it to check the full comment"],
    [8, "Here you may active several filters on the shown Materials. You can select a column group name and then a specific column and activate a filter for it"],
    [9, "According with your authorization level, you may upload new data to the data base , or simply download the existing data"],
    [10, "You can also define the number of table rows shown in each page"],
    [11, "You can press this button to Undo a change you made that has not been submitted"],
    [12,"You can also select the Undo memory size (number of reversable steps) in the dropdown menu. If you select a memory size shorter than the number of changes made, older changes will be unreversable"],
    [13, "Here you can switch between having all the monetary values converted to Euros or maintain the original currency"],
    [14, "Here you can submit all the selected rows. A file will be downloaded will all the rows changed and you will be redirected to a temporary page will a review of all the changes"],
  ]);