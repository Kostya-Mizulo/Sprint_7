package model;

import io.qameta.allure.Step;


public class CourierLoginModel {
    private String login;
    private String password;
    public CourierLoginModel(){}


    public CourierLoginModel(String login, String password){
        this.login = login;
        this.password = password;
    }


    public CourierLoginModel(CourierRegistrationModel courier) {
        this.login = courier.getLogin();
        this.password = courier.getPassword();
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


    @Step("Create CourierLoginModel object")
    public static CourierLoginModel createCourierLoginModelObject(CourierRegistrationModel courier){
        return new CourierLoginModel(courier);
    }
}
