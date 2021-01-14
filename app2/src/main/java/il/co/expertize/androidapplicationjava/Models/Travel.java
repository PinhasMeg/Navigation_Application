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
    private String numberOfPassenger;
    private HashMap<String, Boolean> company;
    //@TypeConverters(UserLocationConverter.class)
    private UserLocation travelDepartureLocation;
    private String departure_address;
    private String destination_address;
    //@TypeConverters(RequestType.class)
    private static RequestType request_type;
    //@TypeConverters(DateConverter.class)
    private Date departure_date;
    //@TypeConverters(DateConverter.class)
    private Date return_date;


// region getters and setters
    public String getDeparture_address() {
        return departure_address;
    }
    public void setDeparture_address(String departure_address) {
        this.departure_address = departure_address;
    }
    public String getDestination_address() {
        return destination_address;
    }
    public void setDestination_address(String destination_address) {
        this.destination_address = destination_address;
    }
    public String getTravelId() {
        return travelId;
    }
    public void setTravelId(String travelId) { this.travelId = travelId;}
    public UserLocation getTravelDepartureLocation() {
        return travelDepartureLocation;
    }
    public void setTravelDepartureLocation(UserLocation travelLocation) {
        this.travelDepartureLocation = travelLocation;
    }
    public RequestType getRequesType() {
        return request_type;
    }
    public void setRequesType(RequestType requesType) {
        this.request_type = requesType;
    }
    public Date getDeparture_date() {
        return departure_date;
    }
    public void setDeparture_date(Date travelDate) {
        this.departure_date = travelDate;
    }
    public Date getReturn_date() {
        return return_date;
    }
    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
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
    public String getNumberOfPassenger() {
        return numberOfPassenger;
    }
    public void setNumberOfPassenger(String numberOfPassenger) {
        this.numberOfPassenger = numberOfPassenger;
    }
    // endregion


    public Travel() {}

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

    public enum RequestType {
        sent(0), accepted(1), run(2), close(3), paid(4);
        private final Integer code;
        RequestType(Integer value) {this.code = value;}
        public Integer getCode() {return code;}
        //@TypeConverter
        public static RequestType getType(Integer numeral) {
            for (RequestType ds : values())
                if (ds.code.equals(numeral))
                    return ds;
            return null;
        }
        //@TypeConverter
        public static Integer getTypeInt(RequestType requestType) {
            if (requestType != null)
                return requestType.code;
            return null;
        }

        @NonNull
        @Override
        public String toString() {
            return request_type.toString();
        }
    }

    public static class CompanyConverter {
        //@TypeConverter
        public static HashMap<String, Boolean> CreateHashMapfromString(String value)
        {
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

