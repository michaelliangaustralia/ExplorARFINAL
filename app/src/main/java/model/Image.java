package model;

import java.sql.Blob;

/**
 * Created by michaelliang on 7/10/17.
 *
 * IMAGE DATABASE
 */

public class Image {
    private int iId;
    private Blob iImage;
    private String iDescription;

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public Blob getiImage() {
        return iImage;
    }

    public void setiImage(Blob iImage) {
        this.iImage = iImage;
    }

    public String getiDescription() {
        return iDescription;
    }

    public void setiDescription(String iDescription) {
        this.iDescription = iDescription;
    }

}
