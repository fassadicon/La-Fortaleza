package sample;

public class POData {

    String POnum, createDate, deliverDate, termsOfPay, modeOfPay, validDate, preparedBy, approvedBy, venID;
    int branchNo;
    double totalAmount;

    POData (String POnum, String createDate, String deliverDate, String termsOfPay, String modeOfPay, String validDate,
            double totalAmount, String preparedBy, String approvedBy, String venID, int branchNo) {
        this.POnum = POnum;
        this.createDate = createDate;
        this.deliverDate = deliverDate;
        this.termsOfPay = termsOfPay;
        this.modeOfPay = modeOfPay;
        this.validDate = validDate;
        this.totalAmount = totalAmount;
        this.preparedBy = preparedBy;
        this.approvedBy = approvedBy;
        this.venID = venID;
        this.branchNo = branchNo;
    }

    public String getPOnum() {
        return POnum;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public String getTermsOfPay() {
        return termsOfPay;
    }

    public String getModeOfPay() {
        return modeOfPay;
    }

    public String getValidDate() {
        return validDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public String getVenID() {
        return venID;
    }

    public int getBranchNo() {
        return branchNo;
    }
}
