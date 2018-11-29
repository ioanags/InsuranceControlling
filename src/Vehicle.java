import java.util.Date;
public class Vehicle {
    public String plate;
    private Date date;
    private boolean isValid;
    private String vehicleName;

   public Vehicle(String plate,Date date,boolean isValid,String vehicleName) {
        this.plate = plate;
        this.date = date;
        this.isValid = isValid;
        this.vehicleName = vehicleName;

    }

    public String getSign() {
        return plate;
    }

    public Date getDate() {
        return date;
    }

    public boolean isValid() {
        return isValid;
    }

    public  String getVehicleName() {
        return vehicleName;
    }


}
