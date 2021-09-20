package ca.ghost_team.sapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.MapsActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.Utils.Utilitaire;
import ca.ghost_team.sapp.adapter.AnnonceAdapter;
import ca.ghost_team.sapp.adapter.AnnonceVendueAdapter;
import ca.ghost_team.sapp.adapter.FavorisAdapter;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.databinding.ActivityDetailAnnonceBinding;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.AnnonceImage;
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
        // initialiser les variables
        ImageView detail_image_annonce = binding.vendeurImageArticle;
        TextView detail_tv_titre = binding.vendeurTitre;
        TextView detail_tv_prix = binding.vendeurPrix;
        TextView detail_tv_description = binding.vendeurDescription;
        TextView detail_tv_vendeur = binding.vendeurName;
        Button detail_btn_contacter = binding.vendeurContacter;
        ImageButton btn_maps = binding.optionMaps;

        // Create Bundle
        Bundle bundle = getIntent().getExtras();
        final int id_annonce;

        if(bundle.getInt(AnnonceAdapter.ANNONCE_ID_REQUEST) != 0)
            id_annonce = bundle.getInt(AnnonceAdapter.ANNONCE_ID_REQUEST);
        else if(bundle.getInt(FavorisAdapter.ANNONCE_ID_REQUEST_FAVORIS) != 0)
            id_annonce = bundle.getInt(FavorisAdapter.ANNONCE_ID_REQUEST_FAVORIS);
        else
            id_annonce = bundle.getInt(AnnonceVendueAdapter.ANNONCE_ID_REQUEST_ANNONCE_VENDUE);


        SappDatabase db = Room.databaseBuilder(getApplication(), SappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries()
                .build();

        Annonce uneAnnonce = db.annonceDao().getInfoAnnonce(id_annonce);
        AnnonceImage annonceImage = db.annonceImageDao().findLocationAnnonceImageByAnnonce(uneAnnonce.getAnnonceImage());

        // information du vendeur
        int vendeurTrouve = db.annonceDao().infoAnnonceur(uneAnnonce.getAnnonceTitre(), uneAnnonce.getAnnoncePrix()).size();

        if (vendeurTrouve == 0){
            getUtilisateurbyAnnonce(uneAnnonce.getAnnonceTitre(),uneAnnonce.getAnnoncePrix());
        }
        // envoyer une requête pour aller chercher le Nom du vendeur
        else
            vendeur = db.annonceDao().infoAnnonceur(uneAnnonce.getAnnonceTitre(), uneAnnonce.getAnnoncePrix()).get(0);

        if (vendeur != null) {
            // Set Information to Fields
            detail_tv_vendeur.setText(vendeur.getUtilisateurNom());

            // créer l'URL de l'image
            String url = BaseApplication.BASE_URL + annonceImage.getLocation();

            // Préparation image cache
            Drawable imageCode = Utilitaire.prepareImageCache(this, annonceImage);

            if (!url.equals("null")){
                Glide.with(this)
                        .load(url)
                        .error(imageCode)
                        .into(detail_image_annonce);
            }else{
                Glide.with(this)
                        .load(imageCode)
                        .error(R.drawable.image)
                        .into(detail_image_annonce);
            }

            detail_tv_titre.setText(uneAnnonce.getAnnonceTitre());
            detail_tv_prix.setText("$" + uneAnnonce.getAnnoncePrix());
            detail_tv_description.setText(uneAnnonce.getAnnonceDescription());
        }

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
            intent.putExtra(MAP_TITRE_REQUEST, uneAnnonce.getAnnonceTitre());
            intent.putExtra(MAP_ZIP_REQUEST, uneAnnonce.getAnnonceZip());
            intent.putExtra(MAP_DESCRIPTION_REQUEST, uneAnnonce.getAnnonceDescription());
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
