package Dashboard;

import java.util.Arrays;
import java.util.List;

public class Car {
    private String RegistrationNumber;
    private int Year;
    private List<String> Color;
    private String CarMake;
    private String CarModel;
    private int Price;

    public Car(String registrationNumber, int year, List<String> color, String carMake, String carModel, int price) {
        RegistrationNumber = registrationNumber;
        Year = year;
        Color = color;
        CarMake = carMake;
        CarModel = carModel;
        Price = price;
    }

    public static List<String> getColor(String colors) {
        String[] colorSet = colors.split(";");
        return Arrays.asList(colorSet);
    }

    public String getRegistrationNumber() {
        return RegistrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        RegistrationNumber = registrationNumber;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public List<String> getColor() {
        return Color;
    }

    public void setColor(List<String> color) {
        Color = color;
    }

    public String getCarMake() {
        return CarMake;
    }

    public void setCarMake(String carMake) {
        CarMake = carMake;
    }

    public String getCarModel() {
        return CarModel;
    }

    public void setCarModel(String carModel) {
        CarModel = carModel;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    @Override
    public String toString() {
        return "Car: \n" +
                "  CarMake = " + CarMake + '\n' +
                "  CarModel = " + CarModel + '\n' +
                "  RegistrationNumber = " + RegistrationNumber + '\n' +
                "  Year = " + Year + "\n"+
                "  Color = " + Color + '\n'+
                "  Price = " + Price + '\n';
    }

    public String getSaveableData() {
        String out=RegistrationNumber+ ','+ Year+',';
        for (String color : Color) {
            out += color +',';
        }
        if (Color.size() == 2) out+=',';
        if (Color.size() == 1) out+=",,";
        out += CarMake+','+CarModel+','+Price+"\n";
        return out;
    }
}

