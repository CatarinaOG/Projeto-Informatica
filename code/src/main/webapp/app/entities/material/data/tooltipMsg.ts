/**
 * Constant that stores all the tooltip messages shown in the column headers in the table
 */
export const MSG : Map<string,string> = new Map<string, string>([
    ["Material", "The material number uniquely identifies a material in the SAP System"],
    ["Material Description", "A short text describing a material"],
    ["ABC Classification", "The ABC Classification is a frequently used analytical method to classify objects (Customers, Products or Employees) based on a particular measure (Revenue or Profit)"],
    ["Plant", "A plant is an operational facility within a company code"],
    ["MRP Controller", "The MRP controller is responsible for material requirements planning and material availability"],
    ["Avg Supplier Delay", "Average variation of the difference between the planned delivery date and the actual delivery date of the supplier for a specific material within the previous year"],
    ["Max Supplier Delay", "Maximum variation of the difference between the planned delivery date and the actual delivery date of the supplier for a specific material within the previous year"],
    ["Current Sap Safety Stock", "Current Safety Stock specified for a particular material"],
    ["Proposed Safety Stock", "Proposed Safety Stock specified for a particular material"],
    ["Current Sap Safety Time", "Current Safety Time specified for a particular material"],
    ["Proposed Safety Time", "Proposed  Safety Time specified for a particular material"],
    ["Service Level", "Service levels determines the percentage of delivered goods from an order compared to the total order, aiming to meet demand and fulfill customer requirements"],
    ["Current Inventory Value", "The monetary value of the current stock"],
    ["Delta Safety Stock", "This value is calculated by: (Proposed SAP Safety Stock - Current SAP Safety Stock)"],
    ["Delta Safety Time", "This value is calculated by: (Proposed SAP Safety Time - Current SAP Safety Time)"],
    // ["Delta Safety Stock Altered", "This value is calculated by: (New SAP Safety Stock - Current SAP Safety Stock)"],
    // ["Delta Safety Time Altered", "This value is calculated by: (New SAP Safety Time - Current SAP Safety Time)"],

    ["Average Inventory Effect After Change", "This value is calculated by: (NewDeltaSST * UnitCost) + (NewDeltaST * UnitCost * AvgDemand)"],

    ["New SAP Safety Stock", "New SAP Safety Stock value"],
    ["New SAP Safety Time", "New SAP Safety Time value"],
    ["Select Entries For Change", "Select Entries To Submit Changes"],
    ["Flag Material as Special Case", "Flag Material as Special Case"],
    ["Comment", "Comment"],
  ]);