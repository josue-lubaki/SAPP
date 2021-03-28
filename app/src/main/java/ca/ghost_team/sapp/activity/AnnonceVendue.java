package ca.ghost_team.sapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.adapter.AnnonceVendueAdapter;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.databinding.AnnonceVendueBinding;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.viewmodel.AnnonceVendueViewModel;

import static ca.ghost_team.sapp.BaseApplication.ID_USER_CURRENT;

public class AnnonceVendue extends AppCompatActivity {

    private static final String TAG = AnnonceVendue.class.getSimpleName();
    private AnnonceVendueBinding binding;
    private RecyclerView recyclerView;
    private AnnonceVendueAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.annonce_vendue);

        // binding
        recyclerView = binding.annonceVenduRecyclerView;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Définition de l'Adapter
        adapter = new AnnonceVendueAdapter(this);

        AnnonceVendueViewModel annonceVendueViewModel = new ViewModelProvider(this).get(AnnonceVendueViewModel.class);

        annonceVendueViewModel.getAllAnnonceVendue().observe(this, annonces -> {
            adapter.addAnnonce(annonces);
            adapter.notifyDataSetChanged();

            Log.i(TAG, "RecyclerView Annonce Vendue correct");
        });

        recyclerView.setAdapter(adapter);
    }

}
