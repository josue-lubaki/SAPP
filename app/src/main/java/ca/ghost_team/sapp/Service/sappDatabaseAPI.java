package ca.ghost_team.sapp.Service;

import java.util.List;

import ca.ghost_team.sapp.model.Utilisateur;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface sappDatabaseAPI {

    @GET("user?utilisateur_username")
    Call<List<Utilisateur>> getUtilisateurViaAPI(
            @Query("Utilisateur_username") String username
    );

}
