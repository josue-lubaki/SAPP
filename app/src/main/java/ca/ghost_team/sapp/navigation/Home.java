package ca.ghost_team.sapp.navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.adapter.AnnonceAdapter;
import ca.ghost_team.sapp.databinding.LayoutHomeBinding;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.viewmodel.AnnonceViewModel;

public class Home extends Fragment {
    private LayoutHomeBinding binding;
    private RecyclerView recyclerViewAnnonce;
    private AnnonceAdapter adapter;
    private final String LOG_TAG = "Fragment_Home";
    private AnnonceViewModel annonceViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_home, container, false);
        //set binding variables here
        return binding.getRoot();
        //return inflater.inflate(R.layout.layout_home, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity)context).setTitle(R.string.home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Définition du RecyclerView
        recyclerViewAnnonce = binding.recyclerViewHome;
        GridLayoutManager grid = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerViewAnnonce.setLayoutManager(grid);

        // Définition de l'Adapter
        adapter = new AnnonceAdapter(getActivity());
        recyclerViewAnnonce.setAdapter(adapter);
        annonceViewModel = new ViewModelProvider(this).get(AnnonceViewModel.class);

        // Supprimer tous les elements de la table avant d'en ajouter
        annonceViewModel.deleteAllAnnonce();
        // Ajout des Annonces par la methode Insert
        for(int i=0;i<getAnnoncesAleatoires().size();i++){
            annonceViewModel.insertAnnonce(getAnnoncesAleatoires().get(i));
        }

        annonceViewModel.getAllAnnonces().observe(getViewLifecycleOwner(), annonces -> {
            adapter.addAnnonce(annonces); // Pour DAO
            //adapter.addAnnonce(getAnnoncesAleatoires()); // Pour la Liste
            adapter.notifyDataSetChanged();
            Log.i(LOG_TAG, "RecyclerView correct");
        });
    }

    private List<Annonce> getAnnoncesAleatoires() {
        List<Annonce> maListe = new ArrayList<>();
        maListe.add(new Annonce(
                R.drawable.collection,
                "Ma collection",
                "Je te vends mes plus beaux vetements",
                150,
                "2 days ago",
                false
        ));
        maListe.add(new Annonce(
                R.drawable.chemise,
                "Ma Chemise",
                "Ma chemise blue",
                50,
                "1 days ago",
                true
        ));
        maListe.add(new Annonce(
                R.drawable.culotte2,
                "Ma Robe rouge",
                "Je te vends ma plus belle robe soirée",
                95,
                "3 days ago",
                false
        ));
        maListe.add(new Annonce(
                R.drawable.culotte1,
                "Ceinture",
                "Tu aimes les ceintures de marque ?",
                120,
                "2 days ago",
                false
        ));
        maListe.add(new Annonce(
                R.drawable.collection,
                "Jogging gris",
                "Pret pour le sport ?",
                45,
                "6 days ago",
                true
        ));
        maListe.add(new Annonce(
                R.drawable.chemise,
                "T-shirt",
                "Je te jure que tu vas l'adorer",
                25,
                "7 days ago",
                false
        ));
        maListe.add(new Annonce(
                R.drawable.culotte2,
                "Culotte",
                "je l'aime bien pour le BasketBall",
                55,
                "1 days ago",
                false
        ));
        maListe.add(new Annonce(
                R.drawable.collection,
                "Veste",
                "Tu veux être présentable ?",
                350,
                "3 days ago",
                true
        ));

        return maListe;
    }
}
