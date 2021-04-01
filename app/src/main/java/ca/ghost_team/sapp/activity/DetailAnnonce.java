package ca.ghost_team.sapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.adapter.AnnonceAdapter;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.databinding.ActivityDetailAnnonceBinding;
import ca.ghost_team.sapp.model.Utilisateur;

public class DetailAnnonce extends AppCompatActivity {
    private ActivityDetailAnnonceBinding binding;
    public static String ID_ANNONCE_CURRENT = "IdAnnonceCurrent";
    public static String ID_RECEIVER_CURRENT = "IdUtilisateurCurrent";

    // initialiser les variables
    private ImageView detail_image_annonce;
    private TextView detail_tv_titre;
    private TextView detail_tv_prix;
    private TextView detail_tv_description;
    private TextView detail_tv_vendeur;
    private Button detail_btn_contacter;
    private SappDatabase db;
    private String TAG = DetailAnnonce.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_annonce);

        // Desactiver le ToolBar
        getSupportActionBar().hide();

        // Assigner les Variables
        detail_image_annonce = binding.vendeurImageArticle;
        detail_tv_titre = binding.vendeurTitre;
        detail_tv_prix = binding.vendeurPrix;
        detail_tv_description = binding.vendeurDescription;
        detail_tv_vendeur = binding.vendeurName;
        detail_btn_contacter = binding.vendeurContacter;

        // Create Bundle
        Bundle bundle = getIntent().getExtras();
        int id_annonce = bundle.getInt(AnnonceAdapter.ANNONCE_ID_REQUEST);
        String annonce_image = bundle.getString(AnnonceAdapter.ANNONCE_IMAGE_REQUEST);
        String annonce_titre = bundle.getString(AnnonceAdapter.ANNONCE_TITRE_REQUEST);
        int annonce_prix = bundle.getInt(AnnonceAdapter.ANNONCE_PRICE_REQUEST);
        String annonce_description = bundle.getString(AnnonceAdapter.ANNONCE_DESCRIPTION_REQUEST);

        db = Room.databaseBuilder(getApplication(), SappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries()
                .build();

        // envoyer une requête pour aller chercher le Nom du vendeur
        Utilisateur vendeur = db.annonceDao().infoAnnonceur(annonce_titre, annonce_prix, annonce_description).get(0);

        if(vendeur != null){
            System.out.println("Info vendeur : " + vendeur.toString());
            // Set Information to Fields
            detail_tv_vendeur.setText(vendeur.getUtilisateurNom());
            detail_image_annonce.setImageURI(Uri.parse(annonce_image));
            detail_tv_titre.setText(annonce_titre);
            detail_tv_prix.setText("$" + annonce_prix);
            detail_tv_description.setText(annonce_description);
        }

        Log.i(TAG, "je te montre la valeur de ID_Annonce : " + id_annonce);
        detail_btn_contacter.setOnClickListener(v -> {
            if(vendeur == null){
                Toast.makeText(this, "Un problème s'est produit", Toast.LENGTH_LONG).show();
                return;
            }

            // Un vendeur ne peut pas appuyer sur le button "Contacter" pour sa propre annonce
            if(BaseApplication.ID_USER_CURRENT == vendeur.getIdUtilisateur()){
                Toast.makeText(this, "Vous êtes déjà l'auteur de cette Annonce !", Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(DetailAnnonce.this, MessageActivity.class);
                intent.putExtra(ID_ANNONCE_CURRENT, id_annonce);
                intent.putExtra(ID_RECEIVER_CURRENT, vendeur.getIdUtilisateur());
                startActivity(intent);
            }
        });
    }
}