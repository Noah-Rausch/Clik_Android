package choccy.clik.Endpoints;

import choccy.clik.Models.Account;
import choccy.clik.Models.AuthResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Interface to outline Retrofit rest calls
 */

public interface LoginEndpoints {

    @POST("register")
    Call<AuthResponse> registerAcc(@Body Account acc);

    @POST("login")
    Call<AuthResponse> loginAcc(@Body Account acc);
}
