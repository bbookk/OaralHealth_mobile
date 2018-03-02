package com.example.asus.oralhealth;

import java.util.List;

/**
 * Created by Asus on 3/3/2561.
 */

public class Result {

    private String id;
    private String name;
    private List<Status> statusList;

    public class Status {
        private String teeth11;
        private String teeth12;
        private String teeth13;
        private String teeth14;
        private String teeth15;
        private String teeth16;
        private String teeth17;
        private String teeth18;

        public String getTeeth12() {
            return teeth12;
        }

        public void setTeeth12(String teeth12) {
            this.teeth12 = teeth12;
        }

        public String getTeeth13() {
            return teeth13;
        }

        public void setTeeth13(String teeth13) {
            this.teeth13 = teeth13;
        }

        public String getTeeth14() {
            return teeth14;
        }

        public void setTeeth14(String teeth14) {
            this.teeth14 = teeth14;
        }

        public String getTeeth15() {
            return teeth15;
        }

        public void setTeeth15(String teeth15) {
            this.teeth15 = teeth15;
        }

        public String getTeeth16() {
            return teeth16;
        }

        public void setTeeth16(String teeth16) {
            this.teeth16 = teeth16;
        }

        public String getTeeth17() {
            return teeth17;
        }

        public void setTeeth17(String teeth17) {
            this.teeth17 = teeth17;
        }

        public String getTeeth18() {
            return teeth18;
        }

        public void setTeeth18(String teeth18) {
            this.teeth18 = teeth18;
        }

        public String getTeeth11() {

            return teeth11;
        }

        public void setTeeth11(String teeth11) {
            this.teeth11 = teeth11;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

}
