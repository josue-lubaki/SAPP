package ca.ghost_team.sapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ca.ghost_team.sapp.model.AnnonceImage;
import ca.ghost_team.sapp.repository.AnnonceImageRepo;

public class AnnonceImageViewModel extends AndroidViewModel {
    private final AnnonceImageRepo annonceImageRepo;
    private final LiveData<List<AnnonceImage>> AllAnnonces;

    public AnnonceImageViewModel(@NonNull Application application) {
        super(application);
        annonceImageRepo = new AnnonceImageRepo(application);
        AllAnnonces = annonceImageRepo.getAllAnnoncesImages();
    }


    public void insertAnnonce(AnnonceImage annonceImage){
        annonceImageRepo.insertAnnonceImage(annonceImage);
    }

    public void insertAllAnnonce(AnnonceImage... annonceImage){
        annonceImageRepo.insertAllAnnonceImage(annonceImage);
    }

    public void updateAnnonce(AnnonceImage annonceImage){
        annonceImageRepo.updateAnnonceImage(annonceImage);
    }

    public void deleteAnnonce(AnnonceImage annonceImage){
        annonceImageRepo.deleteAnnonceImage(annonceImage);
    }

    public void deleteAllAnnonce(){
        annonceImageRepo.deleteAllAnnonceImage();
    }

    public LiveData<List<AnnonceImage>> getAllAnnoncesImages() {
        return AllAnnonces;
    }

}
