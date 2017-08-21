package choccy.clik.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import choccy.clik.Models.Account;
import choccy.clik.Models.AuthResponse;
import choccy.clik.R;
import choccy.clik.Services.LoginNetworkLayer;

public class LoginAct extends AppCompatActivity {

    // UI elements
    private Button registerBt;
    private Button loginBt;
    private EditText usernameEt;
    private EditText emailEt;
    private EditText passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Reference UI elements in code
        registerBt = (Button) findViewById(R.id.registerBtXml);
        loginBt = (Button) findViewById(R.id.loginBtXml);
        usernameEt = (EditText) findViewById(R.id.usernameEtXml);
        emailEt = (EditText) findViewById(R.id.emailEtXml);
        passwordEt = (EditText) findViewById(R.id.passwordEtXml);

        // When the user clicks 'Register', first verify that their input is valid
        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUsername = usernameEt.getText().toString();
                String enteredEmail = emailEt.getText().toString();
                String enteredPassword = passwordEt.getText().toString();
                if(verifyRegisterInfo(enteredUsername, enteredEmail, enteredPassword)){
                    Account acc = new Account(enteredUsername, enteredEmail, enteredPassword);
                    if(acc.valid()) {
                        // Have the LoginWorkerThread deal with connecting to the network
                        // and verifying the inputted credentials.
                        RegisterWorkerThread registerWorkerThread = new RegisterWorkerThread();
                        registerWorkerThread.execute(acc);
                    }
                }
                else{
                    // Invalid input
                }
            }
        });

        // When the user clicks 'Login', first verify that the input is valid
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUsername = usernameEt.getText().toString();
                String enteredEmail = emailEt.getText().toString();
                String enteredPassword = passwordEt.getText().toString();
                if(verifyLoginInfo(enteredUsername, enteredEmail, enteredPassword)){
                    Account acc = new Account(enteredUsername, enteredEmail, enteredPassword);
                    if(acc.valid()) {
                        // Have the LoginWorkerThread deal with connecting to the network
                        LoginWorkerThread loginWorkerThread = new LoginWorkerThread();
                        loginWorkerThread.execute(acc);
                    }
                }
                else{
                    // Have user try again, invalid input
                }
            }
        });
    }

    // Let the user know that the info they inputted is valid or not
    public boolean verifyRegisterInfo(String name, String mail, String pass){
        String response = "";
        if(!verifyUsername(name)){
            response += "invalid username ";
        }
        if(!verifyEmail(mail)){
            response += "invalid email ";
        }
        if(!verifyPassword(pass)){
            response += "invalid password";
        }
        // If all fields are correct, let the user know, otherwise, have them try again
        if(response.equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Entered info is valid", Toast.LENGTH_LONG);
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG);
            return false;
        }
    }

    // Verify that the user's login info is correct
    public boolean verifyLoginInfo(String name, String mail, String pass){
        return false;
    }

    // Check that the entered username is the correct format
    public boolean verifyUsername(String username){
        if(username.length() <= 12 && username.length() >= 4) return true;
        return false;
    }

    // Check that the email is the correct format
    public boolean verifyEmail(String email){
        if(email.contains("@")) return true;
        return false;
    }

    // Check that the password is the correct format
    public boolean verifyPassword(String password){
        if(password.length() <= 16 && password.length() >= 8) return true;
        return false;
    }

    // AsyncTask to handle the connection to the network, regarding logging the user in
    private class LoginWorkerThread extends AsyncTask<Account, Void, AuthResponse> {

        // Given an Account object, have the network layer attempt to authenticate it.
        @Override
        protected AuthResponse doInBackground(Account... params) {
            LoginNetworkLayer networkLayer = new LoginNetworkLayer(getApplicationContext());
            AuthResponse response = networkLayer.loginAccount(params[0]);
            return response;
        }

        @Override
        protected void onPostExecute(AuthResponse authResponse) {
            // Display the result of the authentication to the user and log them in.
            // *** CHECK FOR NULL!  
            Toast.makeText(getApplicationContext(), authResponse.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    // AsyncTask to handle the connection to the network, regarding registry of a new user account
    private class RegisterWorkerThread extends AsyncTask<Account, String, AuthResponse> {

        // Given an Account object, have the network layer attempt to authenticate it.
        @Override
        protected AuthResponse doInBackground(Account... params) {
            LoginNetworkLayer networkLayer = new LoginNetworkLayer(getApplicationContext());
            AuthResponse response = networkLayer.registerAccount(params[0]);
            return response;
        }

        // After getting a response from the server, display it to the user
        @Override
        protected void onPostExecute(AuthResponse authResponse) {
            Toast.makeText(getApplicationContext(), authResponse.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }
}
