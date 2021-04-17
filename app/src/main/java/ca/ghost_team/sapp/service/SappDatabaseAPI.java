package ca.ghost_team.sapp.service;


import java.util.List;

import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.Utilisateur;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SappDatabaseAPI {

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

    @FormUrlEncoded
    @POST("createannonce.php")
    Call<Annonce> createAnnonceViaAPI(
            @Field("image") String image,
            @Field("titre") String titre,
            @Field("description") String description,
            @Field("prix") int prix,
            @Field("date") String date,
            @Field("zip") String zip,
            @Field("utilisateurId") int utilisateurId,
            @Field("categorieId") int categorieId
    );

    @GET("createannonce.php")
    Call<Annonce> createAnnonceViaGetAPI(
            @Query("image") String image,
            @Query("titre") String titre,
            @Query("description") String description,
            @Query("prix") int prix,
            @Query("date") String date,
            @Query("zip") String zip,
            @Query("utilisateurId") int utilisateurId,
            @Query("categorieId") int categorieId
    );


    @FormUrlEncoded
    @POST("uneannonce.php")
    Call<Annonce> getAnnonceViaAPI(
            @Field("titre") String titre,
            @Field("prix") int prix
    );

    @GET("uneannonce.php")
    Call<Annonce> getAnnonceViaGetAPI(
            @Query("titre") String titre,
            @Query("prix") int prix
    );

    @POST("annonces.php")
    Call<List<Annonce>> getAllAnnonceViaAPI();
}
