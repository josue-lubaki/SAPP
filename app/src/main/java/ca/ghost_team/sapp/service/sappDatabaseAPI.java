package ca.ghost_team.sapp.service;


import ca.ghost_team.sapp.model.Utilisateur;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface sappDatabaseAPI {

    @FormUrlEncoded
    @POST("user.php")
    Call<Utilisateur> getUtilisateurViaAPI(
            @Field("username") String nom,
            @Field("password") String motpasse
    );

//    @GET("user.php")
//    Call<Utilisateur> getUtilisateurViaAPI(
//            @Query("username") String nom,
//            @Query("password") String motpasse
//    );

    @FormUrlEncoded
    @POST("signup.php")
    Call<Utilisateur> createUtilisateurViaAPI(
            @Field("fullname") String fullname,
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email
    );

//    @GET("signup.php")
//    Call<Utilisateur> createUtilisateurViaAPI(
//            @Query("fullname") String fullname,
//            @Query("username") String username,
//            @Query("password") String password,
//            @Query("email") String email
//    );

}
