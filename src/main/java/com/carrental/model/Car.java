package com.carrental.model;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Car car = (Car) obj;
        return (makeOfCar != null && makeOfCar.equals(car.makeOfCar) && model != null && model.equals(car.model) && pricePerDay == car.pricePerDay && transmission != null && transmission.equals(car.transmission) && navigator == car.navigator);
    }

    @Override
    public int hashCode(){
        int result = (makeOfCar == null) ? 0 : makeOfCar.hashCode();
        result+= (model == null) ? 0 : model.hashCode();
        result+=pricePerDay;
        result+=(transmission == null) ? 0 : transmission.hashCode();
        result+=(!navigator) ? 0 : 1;
        return (result/10);
    }

}
