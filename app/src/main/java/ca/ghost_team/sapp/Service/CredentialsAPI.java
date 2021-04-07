package ca.ghost_team.sapp.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CredentialsAPI {
    private final sappDatabaseAPI api;

    public CredentialsAPI() {
        Gson gson = new GsonBuilder()
                .create();

        this.api =  new Retrofit.Builder()
                .baseUrl("http://192.168.2.183/sappserver/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(sappDatabaseAPI.class);
    }

    public sappDatabaseAPI getApi(){
        return api;
    }

}
