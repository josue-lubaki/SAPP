package ca.ghost_team.sapp.service.API;

import java.util.List;

import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.service.dto.ImageDTO;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface AnnonceAPI {
    @FormUrlEncoded
    @POST("createannonce.php")
    Call<Annonce> createAnnonceViaAPI(
            @Field("photo") String image,
            @Field("titre") String titre,
            @Field("description") String description,
            @Field("prix") int prix,
            @Field("date") String date,
            @Field("zip") String zip,
            @Field("utilisateurId") int utilisateurId,
            @Field("categorieId") int categorieId
    );

//    @Multipart
//    @POST("upload.php")
//    Call<ImageDTO> uploadImage(@Part("photo") MultipartBody.Part part, @Part("date") RequestBody requestBody);

    @FormUrlEncoded
    @POST("upload.php")
    Call<ImageDTO> uploadImage(@Field("photo") String photoEncode);

    @GET("createannonce.php")
    Call<Annonce> createAnnonceViaGetAPI(
            @Query("photo") String imageId,
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

    @FormUrlEncoded
    @POST("deletemyannonce.php")
    Call<Annonce> deleteMyAnnonce(
            @Field("idAnnonce") int idAnnonce,
            @Field("utilisateurId") int utilisateurId,
            @Field("titre") String titre,
            @Field("prix") int prix
    );
}
