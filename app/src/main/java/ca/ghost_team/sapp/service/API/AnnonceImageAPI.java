package ca.ghost_team.sapp.service.API;

import java.util.List;

import ca.ghost_team.sapp.model.AnnonceImage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AnnonceImageAPI {
    @GET("annoncesImages.php")
    Call<List<AnnonceImage>> getAllAnnoncesImagesViaAPI();
}
