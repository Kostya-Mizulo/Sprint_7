package model;

import helpers.Helper;
import io.qameta.allure.Step;

public class CourierModel {
    private String login;
    private String password;
    private String firstName;


    public CourierModel(){}
    public CourierModel(String login, String password, String firstName){
        this.login = Helper.uniqueizeString(login);
        this.password = Helper.uniqueizeString(password);
        this.firstName = Helper.uniqueizeString(firstName);
    }

    public CourierModel(String login, String password){
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


    @Step("Create Courier object")
    public static CourierModel createCourier(String login, String password, String name){
        return new CourierModel(login, password, name);
    }

    @Step("Make Courier object without login")
    public void deleteLoginFromCourier(){
        setLogin(null);
    }

    @Step("Make Courier object without password")
    public void deletePassFromCourier(){
        setPassword(null);
    }

}