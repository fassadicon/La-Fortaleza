package sample;

public class venTableData {

    String venID, venName, venAdd, venCPerson, venTel, venFax;

    venTableData(String venID, String venName, String venAdd, String venCPerson, String venTel, String venFax) {
        this.venID = venID;
        this.venName = venName;
        this.venAdd = venAdd;
        this.venCPerson = venCPerson;
        this.venTel = venTel;
        this.venFax = venFax;
    }

    public String getVenID() {
        return venID;
    }

    public String getVenName() {
        return venName;
    }

    public String getVenAdd() {
        return venAdd;
    }

    public String getVenCPerson() {
        return venCPerson;
    }

    public String getVenTel() {
        return venTel;
    }

    public String getVenFax() {
        return venFax;
    }
}
