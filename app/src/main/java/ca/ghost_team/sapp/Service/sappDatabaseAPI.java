package ca.ghost_team.sapp.Service;


import ca.ghost_team.sapp.model.Utilisateur;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface sappDatabaseAPI {

    @GET("user.php")
    Call<Utilisateur> getUtilisateurViaAPI(
            @Query("username") String nom,
            @Query("password") String motpasse
    );

    @FormUrlEncoded
    @POST("user.php")
    Call<Utilisateur> getUtilisateurViaPost(
            @Field("username") String nom,
            @Field("password") String motpasse
    );

}
