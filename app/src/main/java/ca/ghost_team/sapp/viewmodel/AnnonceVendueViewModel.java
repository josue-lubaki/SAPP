package ca.ghost_team.sapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.repository.AnnonceRepo;

public class AnnonceVendueViewModel extends AndroidViewModel {

    private final AnnonceRepo annonceRepo;
    private final LiveData<List<Annonce>> allAnnonceVendue;

    public AnnonceVendueViewModel(@NonNull Application application) {
        super(application);
        this.annonceRepo = new AnnonceRepo(application);
        allAnnonceVendue = annonceRepo.findAnnonceByUser();
    }

    public LiveData<List<Annonce>> getAllAnnonceVendue() {
        return allAnnonceVendue;
    }
}
