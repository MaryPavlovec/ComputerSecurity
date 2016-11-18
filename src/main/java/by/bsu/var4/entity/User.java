package by.bsu.var4.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;

public class User {
    private int userId;

    @NotNull
    @Size(min=4, max=30, message = "{login.size}")
    private String login;
    private String email;

    @NotNull
    @Size(min=4, max = 30, message = "{password.size}")
    private String password;
    private int role;
    private String pinCode;
    private int attemptCount;
    private Timestamp lastAttempt;
    private int pinAttemptCount;
    private Timestamp pinLastAttempt;

    public User() {
        super();
    }

    public User(String login, String email, String password, int role) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(int userId, String login, String email, String password, int role) {
        this.userId = userId;
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(int userId, String login, String email, String password, int role, String pinCode) {
        this.userId = userId;
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
        this.pinCode = pinCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (role != user.role) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return pinCode != null ? pinCode.equals(user.pinCode) : user.pinCode == null;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + role;
        result = 31 * result + (pinCode != null ? pinCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", pinCode='" + pinCode + '\'' +
                '}';
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    public Timestamp getLastAttempt() {
        return lastAttempt;
    }

    public void setLastAttempt(Timestamp lastAttempt) {
        this.lastAttempt = lastAttempt;
    }

    public int getPinAttemptCount() {
        return pinAttemptCount;
    }

    public void setPinAttemptCount(int pinAttemptCount) {
        this.pinAttemptCount = pinAttemptCount;
    }

    public Timestamp getPinLastAttempt() {
        return pinLastAttempt;
    }

    public void setPinLastAttempt(Timestamp pinLastAttempt) {
        this.pinLastAttempt = pinLastAttempt;
    }
}
