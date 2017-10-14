package model;

/**
 * Created by Kev on 17/9/17.
 */

public class Organization {
    private int oID;
    private String oName;
    private String oEmail;
    private String oPassword;

    public int getoID() {
        return oID;
    }

    public void setoID(int oId) {
        this.oID = oId;
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName1) {
        this.oName = oName1;
    }

    public String getoEmail() {
        return oEmail;
    }

    public void setoEmail(String oEmail1) {
        this.oEmail = oEmail1;
    }

    public String getoPassword() {
        return oPassword;
    }

    public void setoPassword(String oPassword1) {
        this.oPassword = oPassword1;
    }
}
