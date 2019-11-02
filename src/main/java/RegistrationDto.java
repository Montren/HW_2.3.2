public class RegistrationDto {
    public String login;
    public String password;
    public String status;

    public RegistrationDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public RegistrationDto(String login, String password, String status) {
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }
}
