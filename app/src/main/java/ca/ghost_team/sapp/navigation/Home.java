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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.adapter.AnnonceAdapter;
import ca.ghost_team.sapp.databinding.LayoutHomeBinding;
import ca.ghost_team.sapp.viewmodel.AnnonceViewModel;

public class Home extends Fragment {
    private LayoutHomeBinding binding;
    private AnnonceAdapter adapter;
    private final String LOG_TAG = "Fragment_Home";

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

        // Définition du RecyclerView
        RecyclerView recyclerViewAnnonce = binding.recyclerViewHome;
        GridLayoutManager grid = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerViewAnnonce.setLayoutManager(grid);

        // Définition de l'Adapter et ViewModel
        adapter = new AnnonceAdapter(getActivity());
        recyclerViewAnnonce.setAdapter(adapter);
        AnnonceViewModel annonceViewModel = new ViewModelProvider(this).get(AnnonceViewModel.class);

        annonceViewModel.getAllAnnonces().observe(getViewLifecycleOwner(), annonces -> {
            adapter.addAnnonce(annonces);
            Log.i(LOG_TAG, "Finish put Annonces in RecyclerView");
        });
    }
}