package model;

import helpers.Helper;
import io.qameta.allure.Step;

public class CourierRegistrationModel {
    private String login;
    private String password;
    private String firstName;


    public CourierRegistrationModel(){}


    public CourierRegistrationModel(String login, String password, String firstName){
        this.login = Helper.uniqueizeString(login);
        this.password = Helper.uniqueizeString(password);
        this.firstName = Helper.uniqueizeString(firstName);
    }


    public CourierRegistrationModel(String login, String password){
        this.login = Helper.uniqueizeString(login);
        this.password = Helper.uniqueizeString(password);
    }


    public String getLogin() {
        return login;
    }


    public void setLogin(String login) {
        this.login = login;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    @Step("Create CourierRegistrationModel object")
    public static CourierRegistrationModel createCourier(String login, String password, String name){
        return new CourierRegistrationModel(login, password, name);
    }


    @Step("Create CourierRegistrationModel object without name")
    public static CourierRegistrationModel createCourier(String login, String password){
        return new CourierRegistrationModel(login, password);
    }


    @Step("Make CourierRegistrationModel object without login")
    public void deleteLoginFromCourier(){
        setLogin(null);
    }


    @Step("Make CourierRegistrationModel object without password")
    public void deletePassFromCourier(){
        setPassword(null);
    }
}