package ca.ghost_team.sapp.navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.adapter.FavorisAdapter;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.databinding.LayoutFavorisBinding;
import ca.ghost_team.sapp.viewmodel.AnnonceFavorisViewModel;

public class Favoris extends Fragment {
    private static final String TAG = Favoris.class.getSimpleName();
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

        //binding
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


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        super.onContextItemSelected(item);

        // Utilisation de switch / pour une future ajout du menu contextuel
        switch(item.getItemId()){
            case R.id.favorite_delete :
                adapter.removeFromFavorites(item.getGroupId());
                Toast.makeText(getContext()," Annonce supprimée des favoris", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(getContext(),"default", Toast.LENGTH_SHORT).show();

                return super.onContextItemSelected(item);

        }
    }


}

