package ca.ghost_team.sapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.MapsActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.adapter.AnnonceAdapter;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.databinding.ActivityDetailAnnonceBinding;
import ca.ghost_team.sapp.model.Utilisateur;
import ca.ghost_team.sapp.repository.UtilisateurRepo;
import ca.ghost_team.sapp.service.API.UtilisateurAPI;
import ca.ghost_team.sapp.service.SappAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ImageButton btn_maps;
    private SappDatabase db;
    private Utilisateur vendeur;
    private final String TAG = DetailAnnonce.class.getSimpleName();
    public static final String MAP_TITRE_REQUEST = "ca.ghost_team.sapp.activity.Map_titre";
    public static final String MAP_ZIP_REQUEST = "ca.ghost_team.sapp.activity.Map_zip";
    public static final String MAP_DESCRIPTION_REQUEST = "ca.ghost_team.sapp.activity.Map_description";

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
        btn_maps = binding.optionMaps;

        // Create Bundle
        Bundle bundle = getIntent().getExtras();
        int id_annonce = bundle.getInt(AnnonceAdapter.ANNONCE_ID_REQUEST);
        String annonce_image = bundle.getString(AnnonceAdapter.ANNONCE_IMAGE_REQUEST);
        String annonce_titre = bundle.getString(AnnonceAdapter.ANNONCE_TITRE_REQUEST);
        int annonce_prix = bundle.getInt(AnnonceAdapter.ANNONCE_PRICE_REQUEST);
        String annonce_description = bundle.getString(AnnonceAdapter.ANNONCE_DESCRIPTION_REQUEST);
        String annonce_zip = bundle.getString(AnnonceAdapter.ANNONCE_ZIP_REQUEST);

        db = Room.databaseBuilder(getApplication(), SappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries()
                .build();
        int vendeurTrouve = db.annonceDao().infoAnnonceur(annonce_titre, annonce_prix).size();

        if (vendeurTrouve == 0){
            getUtilisateurbyAnnonce(annonce_titre,annonce_prix);
        }
        // envoyer une requête pour aller chercher le Nom du vendeur
        else
            vendeur = db.annonceDao().infoAnnonceur(annonce_titre, annonce_prix).get(0);

        if (vendeur != null) {
            System.out.println("Info vendeur : " + vendeur.toString());
            // Set Information to Fields
            detail_tv_vendeur.setText(vendeur.getUtilisateurNom());
            if (!annonce_image.equals("null"))
                detail_image_annonce.setImageURI(Uri.parse(annonce_image));
            else
                detail_image_annonce.setImageResource(R.drawable.collection);
            detail_tv_titre.setText(annonce_titre);
            detail_tv_prix.setText("$" + annonce_prix);
            detail_tv_description.setText(annonce_description);
        }

        Log.i(TAG, "je te montre la valeur de ID_Annonce : " + id_annonce);
        detail_btn_contacter.setOnClickListener(v -> {
            if (vendeur == null) {
                Toast.makeText(this, "Un problème s'est produit", Toast.LENGTH_LONG).show();
                return;
            }

            // Un vendeur ne peut pas appuyer sur le button "Contacter" pour sa propre annonce
            if (BaseApplication.ID_USER_CURRENT == vendeur.getIdUtilisateur()) {
                Toast.makeText(this, "Vous êtes déjà l'auteur de cette Annonce !", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(DetailAnnonce.this, MessageActivity.class);
                intent.putExtra(ID_ANNONCE_CURRENT, id_annonce);
                intent.putExtra(ID_RECEIVER_CURRENT, vendeur.getIdUtilisateur());
                startActivity(intent);
            }
        });

        btn_maps.setOnClickListener(v -> {
            Intent intent = new Intent(DetailAnnonce.this, MapsActivity.class);
            intent.putExtra(MAP_TITRE_REQUEST, annonce_titre);
            intent.putExtra(MAP_ZIP_REQUEST, annonce_zip);
            intent.putExtra(MAP_DESCRIPTION_REQUEST, annonce_description);
            startActivity(intent);
        });
    }

    /**
     * Methode permettant d'aller chercher les informations de l'annonceur
     *
     * @param annonce_titre : le titre de l'annnonce dont l'Utilisateur est l'auteur
     * @param annonce_prix  : le prix de l'annonce concernée
     * @return void
     */
    private void getUtilisateurbyAnnonce(String annonce_titre, int annonce_prix) {
        SappAPI.getApi().create(UtilisateurAPI.class).getUtilisateurViaAnnonceAPI(
                annonce_titre,
                annonce_prix
        ).enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                // Si conncetion Failed
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Connection Failed \nFailedCode : " + response.code());
                    return;
                }

                Log.i(TAG, "response : " + response);
                Utilisateur user = response.body();
                assert user != null;
                user.setUtilisateurPassword("");
                String content = "";
                content += "idUtilisateur : " + user.getIdUtilisateur() + "\n";
                content += "utilisateurNom : " + user.getUtilisateurNom() + "\n";
                content += "utilisateurUsername : " + user.getUtilisateurUsername() + "\n";
                content += "Email : " + user.getUtilisateurEmail() + "\n\n";
                Log.i(TAG, content);
                new UtilisateurRepo(getApplication()).insertUtilisateur(user);
                vendeur = user;
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                // Si erreur 404
                Log.e(TAG, t.getMessage());
            }
        });
    }


}
