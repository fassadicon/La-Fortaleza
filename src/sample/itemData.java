package sample;

public class itemData {

    int itemQuantity;
    String itemID, itemName, itemDesc, itemUnit;
    Double itemPPU, itemAmount;

    itemData (String itemID, String itemName, String itemDesc, int itemQuantity, String itemUnit, Double itemPPU, Double itemAmount) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.itemQuantity = itemQuantity;
        this.itemUnit = itemUnit;
        this.itemPPU = itemPPU;
        this.itemAmount = itemAmount;
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

    public int getItemQuantity() {
        return itemQuantity;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public Double getItemPPU() {
        return itemPPU;
    }

    public Double getItemAmount() {
        return itemAmount;
    }
}
