package sample;

public class itemTableData {
    String itemID, itemName, itemDesc, itemUnit;
    double itemPPU;

    itemTableData (String itemID, String itemName, String itemDesc, String itemUnit, Double itemPPU) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.itemUnit = itemUnit;
        this.itemPPU = itemPPU;
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public double getItemPPU() {
        return itemPPU;
    }
}
