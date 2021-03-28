package ca.ghost_team.sapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.repository.AnnonceRepo;

public class AnnonceViewModel extends AndroidViewModel {
    private final AnnonceRepo annonceRepo;
    private final LiveData<List<Annonce>> AllAnnonces;

    public AnnonceViewModel(@NonNull Application application) {
        super(application);
        annonceRepo = new AnnonceRepo(application);
        AllAnnonces = annonceRepo.getAllAnnonces();
    }


    public void insertAnnonce(Annonce annonce){
        annonceRepo.insertAnnonce(annonce);
    }

    public void insertAllAnnonce(Annonce... annonce){
        annonceRepo.insertAllAnnonce(annonce);
    }

    public void updateAnnonce(Annonce annonce){
        annonceRepo.updateAnnonce(annonce);
    }

    public void deleteAnnonce(Annonce annonce){
        annonceRepo.deleteAnnonce(annonce);
    }

    public void deleteAllAnnonce(){
        annonceRepo.deleteAllAnnonce();
    }

    public LiveData<List<Annonce>> findAnnonceByUser(){
        return annonceRepo.findAnnonceByUser();
    }

    public LiveData<List<Annonce>> getAllAnnonces() {
        return AllAnnonces;
    }
}
