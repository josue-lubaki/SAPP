package ca.ghost_team.sapp.Service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CredentialsAPI {
    private final sappDatabaseAPI api;

    public CredentialsAPI() {
        this.api =  new Retrofit.Builder()
                .baseUrl("http://localhost/sappdb/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(sappDatabaseAPI.class);
    }

    public sappDatabaseAPI getApi(){
        return api;
    }

}
