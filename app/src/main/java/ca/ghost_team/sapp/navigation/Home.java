package ca.ghost_team.sapp.navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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
    private AnnonceAdapter adapter;
    private final String LOG_TAG = "Fragment_Home";
    private int selectedFilter = 0;
    private AnnonceViewModel annonceViewModel;
    private RecyclerView recyclerViewAnnonce;

    private Button filterAll;
    private ImageButton filterPant;
    private ImageButton filterTshirt;
    private ImageButton filterHoodie;
    private ImageButton filterCap;
    private ImageButton filterShort;
    private ImageButton filterMore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity) context).setTitle(R.string.home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Binding Fields
        filterAll = binding.filterAll;
        filterCap = binding.filterCap;
        filterHoodie = binding.filterHoodie;
        filterPant = binding.filterPant;
        filterMore = binding.filterMore;
        filterShort = binding.filterShort;
        filterTshirt = binding.filterTshirt;

        // Evenements au Clic
        filterAll.setOnClickListener(this::filterAllAnnonce);
        filterCap.setOnClickListener(this::filterCapAnnonce);
        filterHoodie.setOnClickListener(this::filterHoodieAnnonce);
        filterPant.setOnClickListener(this::filterPantAnnonce);
        filterMore.setOnClickListener(this::filterMoreAnnonce);
        filterShort.setOnClickListener(this::filterShortAnnonce);
        filterTshirt.setOnClickListener(this::filterTshirtAnnonce);

        // Définition du RecyclerView
        recyclerViewAnnonce = binding.recyclerViewHome;
        GridLayoutManager grid = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerViewAnnonce.setLayoutManager(grid);

        // Définition de l'Adapter et ViewModel
        adapter = new AnnonceAdapter(getActivity());
        annonceViewModel = new ViewModelProvider(this).get(AnnonceViewModel.class);

        annonceViewModel.getAllAnnonces().observe(getViewLifecycleOwner(), annonces -> {
            adapter.addAnnonce(annonces);
            Log.i(LOG_TAG, "Finish put Annonces in RecyclerView");
        });
        recyclerViewAnnonce.setAdapter(adapter);
    }

    public void filterList(int categorie) {

        List<Annonce> annonceFilteredList = new ArrayList<>();

        annonceViewModel.getAllAnnonces().observe(getViewLifecycleOwner(), annonces -> {

            for (Annonce annonce : annonces) {
                if (annonce.getCategorieId() == categorie) {
                    annonceFilteredList.add(annonce);
                }
            }

            adapter.addAnnonce(annonceFilteredList);
            recyclerViewAnnonce.setAdapter(adapter);
            Log.i(LOG_TAG, "Method Filter Generic finished");
        });
    }

    private void filterPantAnnonce(View view) {
        filterList(1);
    }

    private void filterTshirtAnnonce(View view) {
        filterList(2);
    }

    private void filterHoodieAnnonce(View view) {
        filterList(3);
    }

    private void filterShortAnnonce(View view) {
        filterList(4);
    }

    private void filterCapAnnonce(View view) {
        filterList(5);
    }

    private void filterMoreAnnonce(View view) {
        filterList(6);
    }

    private void filterAllAnnonce(View view) {
        annonceViewModel.getAllAnnonces().observe(getViewLifecycleOwner(), annonces -> {
            adapter.addAnnonce(annonces);
            Log.i(LOG_TAG, "Finish put Annonces in RecyclerView");
        });
        recyclerViewAnnonce.setAdapter(adapter);
    }
}