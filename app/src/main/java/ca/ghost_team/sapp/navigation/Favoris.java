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
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.activity.Login;
import ca.ghost_team.sapp.adapter.AnnonceAdapter;
import ca.ghost_team.sapp.adapter.FavorisAdapter;
import ca.ghost_team.sapp.database.sappDatabase;
import ca.ghost_team.sapp.databinding.LayoutFavorisBinding;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.viewmodel.AnnonceFavorisViewModel;
import ca.ghost_team.sapp.viewmodel.AnnonceViewModel;

public class Favoris extends Fragment {
    private static final String TAG = Favoris.class.getSimpleName();
    private LayoutFavorisBinding binding;
    private RecyclerView recyclerView;
    private FavorisAdapter adapter;
    private sappDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.layout_favoris, container, false);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.db = Room.databaseBuilder(context,sappDatabase.class,"sappDatabase")
                .allowMainThreadQueries().build();
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

        AnnonceFavorisViewModel annonceFavorisViewModel = new ViewModelProvider(this).get(AnnonceFavorisViewModel.class);

        annonceFavorisViewModel.getAllAnnonceFavoriteByUser().observe(getViewLifecycleOwner(), annonces -> {
            adapter.addAnnonceToFavoris(annonces);
            adapter.notifyDataSetChanged();

            Log.i(TAG, "RecyclerView correct");
        });

    }
}

