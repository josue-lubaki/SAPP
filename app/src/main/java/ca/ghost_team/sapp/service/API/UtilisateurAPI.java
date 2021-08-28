package ca.ghost_team.sapp.service.API;


import java.util.List;

import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.Utilisateur;
import ca.ghost_team.sapp.service.dto.UtilisateurDTO;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UtilisateurAPI {

    @FormUrlEncoded
    @POST("user.php")
    Call<String> getUtilisateurViaAPI(@Field("checksum") String checksum);

//    @GET("user.php")
//    Call<String> getUtilisateurViaAPI(
//            @Query("checksum") String checksum
//    );

    @FormUrlEncoded
    @POST("signup.php")
    Call<String> createUtilisateurViaAPI(@Field("checksum") String checksum);

//    @GET("signup.php")
//    Call<String> createUtilisateurViaAPI(
//            @Query("checksum") String checksum
//    );

    @FormUrlEncoded
    @POST("userbyannonce.php")
    Call<Utilisateur> getUtilisateurViaAnnonceAPI(
            @Field("titre") String titre,
            @Field("prix") int prix
    );

    @GET("userbyannonce.php")
    Call<Utilisateur> getUtilisateurViaAnnonceGetAPI(
            @Query("titre") String titre,
            @Query("prix") int prix
    );

    @FormUrlEncoded
    @POST("userbyid.php")
    Call<String> getUtilisateurbyID(@Field("id") int id);

}
