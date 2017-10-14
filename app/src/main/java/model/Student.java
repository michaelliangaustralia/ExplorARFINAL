package model;

/**
 * Created by Kev on 17/9/17.
 */
public class Student {
    private int zID;
    private String name;
    private String email;
    private String password;

    public int getzID() {
        return zID;
    }

    public void setzID(int zId) {
        this.zID = zId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}


