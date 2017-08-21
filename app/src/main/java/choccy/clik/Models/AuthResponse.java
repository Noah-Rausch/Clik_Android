package choccy.clik.Models;

/**
 * A class to represent the server response regarding registering/logging in a user
 */

public class AuthResponse {

    private Account account;
    String message;

    public AuthResponse(){}

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
