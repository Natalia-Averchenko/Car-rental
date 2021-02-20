public class Car {

    private int carId;
    private String makeOfCar;
    private String model;
    private int pricePerDay;
    //private String fuel;
    private String transmission;
    //private boolean conditioner;
    private boolean navigator;

    public Car(String makeOfCar, String model, int pricePerDay /*, String fuel */, String transmission /*, boolean conditioner*/, boolean navigator) {
        this.makeOfCar = makeOfCar;
        this.model = model;
        this.pricePerDay = pricePerDay;
        //this.fuel = fuel;
        this.transmission = transmission;
        //this.conditioner = conditioner;
        this.navigator = navigator;
    }

    public void setCarId(int carId){
        this.carId = carId;
    }

    public void setMakeOfCar(String makeOfCar) {
        this.makeOfCar = makeOfCar;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
    public void setNavigator(boolean navigator) {
        this.navigator = navigator;
    }


    public int getCarId() {
        return this.carId;
    }

    public String getMakeOfCar() {
        return this.makeOfCar;
    }

    public String getModel() {
        return this.model;
    }

    public int getPricePerDay() {
        return this.pricePerDay;
    }

    public String getTransmission() {
        return this.transmission;
    }

    public boolean getNavigator() {
        return this.navigator;
    }

    @Override
    public String toString(){
        return "Car ID: " +this.getCarId()+"\t\tMake of car: " + this.getMakeOfCar() + "\t\tModel of car: " + this.getModel() +"\t\tPrice per day: " +this.getPricePerDay() +"\t\tTransmission: " + this.getTransmission() +"\t\tNavigator: " + this.getNavigator();
    }
}
