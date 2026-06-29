package models.request;

public class CourierLoginRequest {
    private String login;
    private String password;

    public CourierLoginRequest(){}

    public CourierLoginRequest(CreateCourierRequest courier){
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
}
