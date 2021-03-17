package ca.ghost_team.sapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.repository.AnnonceFavorisRepo;

public class AnnonceFavorisViewModel extends AndroidViewModel {
    private final AnnonceFavorisRepo annonceFavorisRepo;
    private final LiveData<List<Annonce>> AllAnnonceFavoriteByUser;

    public AnnonceFavorisViewModel(@NonNull Application application) {
        super(application);
        annonceFavorisRepo = new AnnonceFavorisRepo(application);
        AllAnnonceFavoriteByUser = annonceFavorisRepo.getAllAnnonceFavoriteByUser();
    }

    public LiveData<List<Annonce>> getAllAnnonceFavoriteByUser() {
        return AllAnnonceFavoriteByUser;
    }
}
