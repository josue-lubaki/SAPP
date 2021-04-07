package ca.ghost_team.sapp.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UtilisateurAPI {
    private sappDatabaseAPI api = null;

    public sappDatabaseAPI getApi(){
        if(api == null){
            Gson gson = new GsonBuilder().create();

            this.api =  new Retrofit.Builder()
                    .baseUrl("http://192.168.2.183/sappserver/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(sappDatabaseAPI.class);
        }

        return api;
    }

}
