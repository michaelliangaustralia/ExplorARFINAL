package model;

/**
 * Created by carregliu on 7/10/2017.
 */

public class Event {
    private int eID;
    private String eName;
    private String eLocation;
    private String eDate;
    private String eStartTime;
    private String eEndTime;
    private String ePrice;
    private String eDesc;
    private String eType;

    public int geteID() {
        return eID;
    }

    public void seteID(int eID1) {
        this.eID = eID1;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName1) {
        this.eName = eName1;
    }

    public String geteLocation() {
        return eLocation;
    }

    public void seteLocation(String eLocation1) {
        this.eDate = eLocation1;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate1) {
        this.eDate = eDate1;
    }

    public String geteStartTime() {
        return eStartTime;
    }

    public void seteStartTime(String eStartTime1) {
        this.eStartTime = eStartTime1;
    }

    public String geteEndTime() {
        return eEndTime;
    }

    public void seteEndTime(String eEndTime1) {
        this.eEndTime = eEndTime1;
    }

    public String getePrice() {
        return ePrice;
    }

    public void setePrice(String ePrice1) {
        this.ePrice = ePrice1;
    }

    public String geteDesc() {
        return eDesc;
    }

    public void seteDesc(String eDesc1) {
        this.eDesc = eDesc1;
    }

    public String geteType() {
        return eType;
    }

    public void seteType(String eType1) {
        this.eType = eType1;
    }
}
