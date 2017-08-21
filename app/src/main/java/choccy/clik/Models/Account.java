package choccy.clik.Models;

/**
 * Class to represent a user account.
 */

public class Account {

    String username;
    String email;
    String password;
    String id;

    public Account(){};

    public Account(String name, String mail, String pass){
        this.username = name;
        this.email = mail;
        this.password = pass;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getId(){
        return id;
    }

    // Verify the state of this object is valid
    public boolean valid(){
        if(username != null && username.length() != 0 && email != null
                && email.length() != 0 && password != null && password.length() != 0) return true;
        return false;
    }
}
