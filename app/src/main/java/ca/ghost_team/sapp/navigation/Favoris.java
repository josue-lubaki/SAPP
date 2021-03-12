package ca.ghost_team.sapp.navigation;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.adapter.AnnonceAdapter;
import ca.ghost_team.sapp.adapter.FavorisAdapter;
import ca.ghost_team.sapp.databinding.LayoutFavorisBinding;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.viewmodel.AnnonceViewModel;

public class Favoris extends Fragment {
    private LayoutFavorisBinding binding;
    private RecyclerView recyclerView;
    private FavorisAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.layout_favoris, container, false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity)context).setTitle(R.string.favoris);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.recyclerViewFavoris;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Définition de l'Adapter
        adapter = new FavorisAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        AnnonceViewModel annonceViewModel = new ViewModelProvider(this).get(AnnonceViewModel.class);

        annonceViewModel.getAllAnnonces().observe(getViewLifecycleOwner(), annonces -> {
            List<Annonce> maListeFavorite = new ArrayList<>();
            for (Annonce annonce: annonces) {
                if(annonce.isAnnonce_liked())
                    maListeFavorite.add(annonce);
            }
            adapter.addAnnonceToFavoris(maListeFavorite);
            adapter.notifyDataSetChanged();

            Log.i("Favoris.class", "RecyclerView correct");
        });

    }
}
