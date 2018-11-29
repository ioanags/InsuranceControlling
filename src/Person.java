import java.util.Date;

public class Person extends Vehicle {
    private String name;


    public String getName() {
        return name;
    }



    public Person(String sign, Date date, boolean isValid, String vehicleName,String name) {

        super(sign, date, isValid, vehicleName);
        this.name = name;
    }

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder("Owner{");
        sb.append("name:'").append(getName()).append('\'');
        sb.append(", Vehicles name: '").append(getVehicleName()).append('\'');
        sb.append(", Vehicle plates: '").append(getSign()).append('\'');
        sb.append(", status: ").append(isValid());
        sb.append('}');
        return sb.toString();
    }
}

