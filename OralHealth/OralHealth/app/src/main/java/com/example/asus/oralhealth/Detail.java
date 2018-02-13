package com.example.asus.oralhealth;

/**
 * Created by Asus on 13/2/2561.
 */

public class Detail {
    private String id,schoolName;

    public Detail(String id, String schoolName){
        this.setId(id);
        this.setSchoolName(schoolName);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
