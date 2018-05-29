package com.esiea.mydaily.JsonTraitment;

public class Kiosk {

    @Override
    public String toString() {
        return "Kiosk{" +
                "formatted_address='" + formatted_address + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", opening_hours=" + opening_hours +
                '}';
    }

    private String formatted_address;
    private String name;
    private String icon;
    private Opening_hours opening_hours;

    public Kiosk(String formatted_address, String name, String icon, Opening_hours opening_hours) {
        this.formatted_address = formatted_address;
        this.name = name;
        this.icon = icon;
        this.opening_hours = opening_hours;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Opening_hours getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(Opening_hours opening_hours) {
        this.opening_hours = opening_hours;
    }



    //Class
    public static class Opening_hours {

        private String open_now;

        public Opening_hours(String open_now) {
            this.open_now = open_now;
        }

        public String getOpen_now() {
            return open_now;
        }

        public void setOpen_now(String open_now) {
            this.open_now = open_now;
        }

        @Override
        public String toString() {
            return "Opening_hours{" +
                    "open_now='" + open_now + '\'' +
                    '}';
        }
    }
}
