package il.co.expertize.androidapplicationjava.Models;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Travel {

    private String travelId = "id";
    private String clientName;
    private String clientPhone;
    private String clientEmail;
    private String numOfPassengers;
    private HashMap<String, Boolean> company;
    //@TypeConverters(UserLocationConverter.class)
    private UserLocation travelLocation;
    private String departureAddress;
    private String destinationAddress;
    //@TypeConverters(RequesType.class)
    private static RequesType requesType;
    //@TypeConverters(DateConverter.class)
    private Date travelDate;
    //@TypeConverters(DateConverter.class)
    private Date arrivalDate;
    private String applicationDate;

// region getters and setters

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(String departureAddress) {
        this.departureAddress = departureAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public UserLocation getTravelLocation() {
        return travelLocation;
    }

    public void setTravelLocation(UserLocation travelLocation) {
        this.travelLocation = travelLocation;
    }

    public RequesType getRequesType() {
        return requesType;
    }

    public void setRequesType(RequesType requesType) {
        this.requesType = requesType;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public HashMap<String, Boolean> getCompany() {
        return company;
    }

    public void setCompany(HashMap<String, Boolean> company) {
        this.company = company;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getNumOfPassengers() {
        return numOfPassengers;
    }

    public void setNumOfPassengers(String numOfPassengers) {
        this.numOfPassengers = numOfPassengers;
    }
    // endregion


    public Travel() {
    }

    public static class DateConverter {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

        //@TypeConverter
        public Date fromTimestamp(String date) throws ParseException {
            return (date == null ? null : format.parse(date));
        }

        //@TypeConverter
        public String dateToTimestamp(Date date) {
            return date == null ? null : format.format(date);
        }
    }

    public enum RequesType {
        sent(0), accepted(1), run(2), close(3), paid(4);
        private final Integer code;

        RequesType(Integer value) {
            this.code = value;
        }

        public Integer getCode() {
            return code;
        }

        //@TypeConverter
        public static RequesType getType(Integer numeral) {
            for (RequesType ds : values())
                if (ds.code.equals(numeral))
                    return ds;
            return null;
        }

        //@TypeConverter
        public static Integer getTypeInt(RequesType requesType) {
            if (requesType != null)
                return requesType.code;
            return null;
        }

        @NonNull
        @Override
        public String toString() {
            return requesType.toString();
        }
    }

    public static class CompanyConverter {
        //@TypeConverter
        public static HashMap<String, Boolean> CreateHashMapfromString(String value) {
            if (value == null || value.isEmpty())
                return null;
            String[] mapString = value.split(","); //split map into array of (string,boolean) strings
            HashMap<String, Boolean> hashMap = new HashMap<>();
            for (String s1 : mapString) //for all (string,boolean) in the map string
            {
                if (!s1.isEmpty()) {//is empty maybe will needed because the last char in the string is ","
                    String[] s2 = s1.split(":"); //split (string,boolean) to company string and boolean string.
                    Boolean aBoolean = Boolean.parseBoolean(s2[1]);
                    hashMap.put(/*company string:*/s2[0], aBoolean);
                }
            }
            return hashMap;
        }

        //@TypeConverter
        public String asString(HashMap<String, Boolean> map) {
            if (map == null)
                return null;
            StringBuilder mapString = new StringBuilder();
            for (Map.Entry<String, Boolean> entry : map.entrySet())
                mapString.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
            return mapString.toString();
        }
    }

    public static class UserLocationConverter {
        //@TypeConverter
        public static UserLocation UserLocationFromString(String value) {
            if (value == null || value.equals(""))
                return null;
            double lat = Double.parseDouble(value.split(" ")[0]);
            double lang = Double.parseDouble(value.split(" ")[1]);
            return new UserLocation(lat, lang);
        }

        //@TypeConverter
        public static String UserLocationAsString(UserLocation warehouseUserLocation) {
            return warehouseUserLocation == null ? "" : warehouseUserLocation.getLat() + " " + warehouseUserLocation.getLon();
        }
    }
}

