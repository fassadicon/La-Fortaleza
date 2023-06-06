package sample;

public class finalOrderData {

    String POnum, itemID;
    int itemQuantity;
    double itemAmount;

    finalOrderData(String POnum, String itemID, int itemQuantity, double itemAmount) {
        this.POnum = POnum;
        this.itemID = itemID;
        this.itemQuantity = itemQuantity;
        this.itemAmount = itemAmount;
    }

    public String getPOnum() {
        return POnum;
    }

    public String getItemID() {
        return itemID;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public double getItemAmount() {
        return itemAmount;
    }
}
