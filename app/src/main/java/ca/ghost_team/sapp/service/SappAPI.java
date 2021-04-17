package ca.ghost_team.sapp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SappAPI {
    private SappDatabaseAPI api = null;

    public SappDatabaseAPI getApi(){
        if(api == null){
            Gson gson = new GsonBuilder().create();

            this.api =  new Retrofit.Builder()
                    .baseUrl("http://192.168.2.183/sappserver/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(SappDatabaseAPI.class);
        }

        return api;
    }

}
