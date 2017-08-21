package choccy.clik.Services;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;

import choccy.clik.Endpoints.LoginEndpoints;
import choccy.clik.Models.Account;
import choccy.clik.Models.AuthResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class to handle implementation details of the passing of data over a network.
 */

public class LoginNetworkLayer{

    Retrofit retrofit;
    LoginEndpoints endpoints;
    Context context;

    public LoginNetworkLayer(Context con) {
        this.context = con;
        final String BASE_URL = "BASE URL FOR WEB API GOES HERE";
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(LoginEndpoints.class);
    }

    // Register the passed account.  Return the result of the transactions in the form of a
    // reponse object
    public AuthResponse registerAccount(Account acc){
        Call<AuthResponse> call = endpoints.registerAcc(acc);
        AuthResponse serverResponse = null;
        try {
            serverResponse = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverResponse;
    }

    // Verify the passed account.  Return the result of the transaction in the form of a server
    // response object.
    public AuthResponse loginAccount(Account acc){
        Call<AuthResponse> call = endpoints.loginAcc(acc);
        AuthResponse serverResponse = null;
        try {
            serverResponse = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverResponse;
    }
}
