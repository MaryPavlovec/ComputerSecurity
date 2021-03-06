package by.bsu.var4.entity;

/**
 * Created by Asus on 06.10.2016.
 */
public class UserModel {
    private String login;
    private String email;
    private String password;
    private String repeatPassword;
    private String pinCode;
    private String repeatPinCode;


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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getRepeatPinCode() {
        return repeatPinCode;
    }

    public void setRepeatPinCode(String repeatPinCode) {
        this.repeatPinCode = repeatPinCode;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", repeatPinCode='" + repeatPinCode + '\'' +
                '}';
    }
}
