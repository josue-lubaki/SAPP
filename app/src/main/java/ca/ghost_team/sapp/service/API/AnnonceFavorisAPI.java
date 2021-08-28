package ca.ghost_team.sapp.service.API;

import java.util.List;

import ca.ghost_team.sapp.model.AnnonceFavoris;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AnnonceFavorisAPI {
    @FormUrlEncoded
    @POST("addannoncefavoris.php")
    Call<AnnonceFavoris> likeAnnonceViaAPI(
            @Field("idAnnonce") int idAnnonce,
            @Field("idUtilisateur") int idUtilisateur
    );

    @GET("addannoncefavoris.php")
    Call<AnnonceFavoris> likeAnnonceViaGetAPI(
            @Query("idAnnonce") int idAnnonce,
            @Query("idUtilisateur") int idUtilisateur
    );

    @FormUrlEncoded
    @POST("deleteannoncefavoris.php")
    Call<AnnonceFavoris> disLikeAnnonceViaAPI(
            @Field("idAnnonce") int idAnnonce,
            @Field("idUtilisateur") int idUtilisateur
    );

    @GET("deleteannoncefavoris.php")
    Call<AnnonceFavoris> disLikeAnnonceViaGetAPI(
            @Query("idAnnonce") int idAnnonce,
            @Query("idUtilisateur") int idUtilisateur
    );

    @FormUrlEncoded
    @POST("annoncefavoris.php")
    Call<List<AnnonceFavoris>> getAllAnnonceFavorisViaAPI(
            @Field("idUtilisateur") int idUtilisateur
    );

    @GET("annoncefavoris.php")
    Call<List<AnnonceFavoris>> getAllAnnonceFavorisViaGetAPI(
            @Query("idUtilisateur") int idUtilisateur
    );
}
