package ca.ghost_team.sapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import ca.ghost_team.sapp.adapter.AnnonceAdapter;
import ca.ghost_team.sapp.databinding.ActivityDetailAnnonceBinding;

public class DetailAnnonce extends AppCompatActivity {
    private ActivityDetailAnnonceBinding binding;

    // initialiser les variables
    ImageView detail_image_annonce;
    TextView detail_tv_titre;
    TextView detail_tv_prix;
    TextView detail_tv_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_annonce);

        // Assigner les Variables
        detail_image_annonce = binding.vendeurImageArticle;
        detail_tv_titre = binding.vendeurTitre;
        detail_tv_prix = binding.vendeurPrix;
        detail_tv_description = binding.vendeurDescription;

        // Create Bundle
        Bundle bundle = getIntent().getExtras();
        int annonce_image = bundle.getInt(AnnonceAdapter.ANNONCE_IMAGE_REQUEST);
        String annonce_titre = bundle.getString(AnnonceAdapter.ANNONCE_TITRE_REQUEST);
        String annonce_prix = bundle.getString(AnnonceAdapter.ANNONCE_PRICE_REQUEST);
        String annonce_description = bundle.getString(AnnonceAdapter.ANNONCE_DESCRIPTION_REQUEST);

        // Set Information to Fields
        detail_image_annonce.setImageResource(annonce_image);
        detail_tv_titre.setText(annonce_titre);
        detail_tv_prix.setText(annonce_prix);
        detail_tv_description.setText(annonce_description);
    }
}