package sample;

public class branchData {
    String brCPerson, brAdd, brTel, brFax;
    int brNo;
    branchData(int brNo, String brCPerson, String brAdd, String brTel, String brFax) {
        this.brNo = brNo;
        this.brCPerson = brCPerson;
        this.brAdd = brAdd;
        this.brTel = brTel;
        this.brFax = brFax;
    }

    public int getBrNo() {
        return brNo;
    }

    public String getBrCPerson() {
        return brCPerson;
    }

    public String getBrAdd() {
        return brAdd;
    }

    public String getBrTel() {
        return brTel;
    }

    public String getBrFax() {
        return brFax;
    }
}
