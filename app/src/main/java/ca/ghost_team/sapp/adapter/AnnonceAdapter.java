package ca.ghost_team.sapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.activity.DetailAnnonce;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.AnnonceFavoris;
import ca.ghost_team.sapp.model.AnnonceImage;
import ca.ghost_team.sapp.repository.AnnonceFavorisRepo;
import ca.ghost_team.sapp.service.API.AnnonceFavorisAPI;
import ca.ghost_team.sapp.service.SappAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ca.ghost_team.sapp.BaseApplication.ID_USER_CURRENT;

public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceAdapter.AnnonceVH> {

    private static final String TAG = AnnonceAdapter.class.getSimpleName();
    private final MainActivity activity;
    Context context;
    List<Annonce> listeAnnonces;
    List<Annonce> listeAnnonceFavorite;
    private MainActivity app;
    private SappDatabase db;

    // Constantes
    public static final String ANNONCE_IMAGEURL_REQUEST = "Annonce_Image";
    public static final String ANNONCE_TITRE_REQUEST = "Annonce_Titre";
    public static final String ANNONCE_PRICE_REQUEST = "Annonce_Prix";
    public static final String ANNONCE_DESCRIPTION_REQUEST = "Annonce_Description";
    public static final String ANNONCE_ID_REQUEST = "Annonce_ID_current_Detail";
    public static final String ANNONCE_ZIP_REQUEST = "Annonce_ZIP_Code";

    public AnnonceAdapter(Context context) {
        this.context = context;
        this.listeAnnonces = new ArrayList<>();
        this.listeAnnonceFavorite = new ArrayList<>();
        this.db = Room.databaseBuilder(context, SappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries().build();
        this.activity = (MainActivity) getContext();
    }

    public Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public AnnonceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_annonce_item, parent, false);
        return new AnnonceVH(view);
    }

    //Va au final renvoyer jour resant
    public String formatDate(Date d) {
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT);
        String x = "" + shortDateFormat.format(d);
        return x;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AnnonceVH holder, int position) {
        this.listeAnnonceFavorite = db.annonceFavorisDao().findListAnnonceFavoriteByUser(ID_USER_CURRENT);
        Annonce uneAnnonce = listeAnnonces.get(position);

        if(uneAnnonce.getAnnonceImage() != 0){
            // Récuperer l'Id de l'image pour le rechercher sur le net
            AnnonceImage annonceImage = db.annonceImageDao().findLocationAnnonceImageByAnnonce(uneAnnonce.getAnnonceImage());

            String location = annonceImage.getLocation();
            String url = BaseApplication.BASE_URL + location;

            // decoder l'image
            byte[] decodedString = Base64.decode(annonceImage.getImagecode(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            // convert Bitmap to Drawable
            Drawable image = new BitmapDrawable(activity.getResources(), decodedByte);

            Picasso.with(context)
                    .load(url)
                    .error(image)
                    .into(holder.imageAnnonce);
        }
        else {
            Picasso.with(context)
                    .load(R.drawable.image)
                    .into(holder.imageAnnonce);
        }

        holder.titre.setText(uneAnnonce.getAnnonceTitre());
        holder.prix.setText("$" + uneAnnonce.getAnnoncePrix());
        //apelle de la methode de formatage
        holder.date.setText(""+ uneAnnonce.getAnnonceDate());

        // Donner les états initials du Boutton
        if (!verifyContent(uneAnnonce))
            holder.likeBtn.setImageResource(R.drawable.ic_favoris);
        else {
            holder.likeBtn.setImageResource(R.drawable.ic_favoris_red);
        }

        holder.likeBtn.setOnClickListener(v -> {
            if(uneAnnonce.getUtilisateurId() == ID_USER_CURRENT){
                Toast.makeText(context, "Tu ne peux pas aimer ton annonce !", Toast.LENGTH_LONG).show();
                return;
            }

            if (!verifyContent(uneAnnonce)) {
                holder.likeBtn.setImageResource(R.drawable.ic_favoris_red);

                // Ajouter (ou insérer l'enregistrement dans la Table des Annonces Favories)
                db.annonceFavorisDao().insertLiked(ID_USER_CURRENT, uneAnnonce.getIdAnnonce());

            } else {
                holder.likeBtn.setImageResource(R.drawable.ic_favoris);

                // Supprimer l'enregitrement dans la Table des Annonces Favoris
                db.annonceFavorisDao().deleteAnnonceByID(ID_USER_CURRENT, uneAnnonce.getIdAnnonce());
            }
            notifyDataSetChanged();
        });

        // set OnClickListener
        holder.cardView_detail_Article.setOnClickListener(v -> {
            // Creation de l'intent (Envoyer Toutes les informations nécessaires vers l'Activité)
            Intent intent = new Intent(context, DetailAnnonce.class);
            intent.putExtra(ANNONCE_ID_REQUEST, uneAnnonce.getIdAnnonce());

            context.startActivity(intent);
        });
    }


    void test(){

        SappAPI.getApi().create(AnnonceFavorisAPI.class).getAllAnnonceFavorisViaAPI(ID_USER_CURRENT)
                .enqueue(new Callback<List<AnnonceFavoris>>() {
                    @Override
                    public void onResponse(Call<List<AnnonceFavoris>> call, Response<List<AnnonceFavoris>> response) {
                        // Si conncetion Failed
                        if (!response.isSuccessful()) {
                            Log.i(TAG, "Connection Failed \nFailedCode : " + response.code());
                            return;
                        }

                        Log.i(TAG, "response : " + response);
                        List<AnnonceFavoris> annonceFavorisList = response.body();

                        AnnonceFavoris[] tableAnnonceFavoris = new AnnonceFavoris[annonceFavorisList.size()];
                        annonceFavorisList.toArray(tableAnnonceFavoris);
                        new AnnonceFavorisRepo(activity.getApplication()).insertLiked(tableAnnonceFavoris);

                    }

                    @Override
                    public void onFailure(Call<List<AnnonceFavoris>> call, Throwable t) {
                        // Si erreur 404
                        Log.e(TAG, t.getMessage());
                    }
                });
    }


    @Override
    public int getItemCount() {
        return listeAnnonces.size();
    }

    /**
     * Methode qui permet de setter une nouvelle liste à la liste de toutes les Annonces récupérée
     * depuis a base de données
     *
     * @param listeAllAnnonces la nouvelle liste à passer vers l'Adapter, contenant toutes les Annonces
     * @return void
     */
    public void addAnnonce(List<Annonce> listeAllAnnonces) {
        listeAnnonces = listeAllAnnonces;
        notifyDataSetChanged();
    }

    /**
     * Methode qui permet de Vérifier si une Annonce se trouve dans la liste des Annonces Aimées
     * par l'utilisateur Courant del'Application
     *
     * @param uneAnnonce Entité Annonce à vérifier
     *                   Si @code{uneAnnonce} est trouvée, retourne true
     *                   Sinon false
     * @return boolean
     */
    public boolean verifyContent(Annonce uneAnnonce) {
        if (listeAnnonceFavorite.size() > 0) {
            for (Annonce annonce : listeAnnonceFavorite) {
                if (annonce.getIdAnnonce() == uneAnnonce.getIdAnnonce())
                    return true;
            }
        }
        return false;
    }

    static class AnnonceVH extends RecyclerView.ViewHolder {
        /* On définit les Champs du model */
        TextView titre;
        TextView prix;
        TextView date;
        ImageView likeBtn;
        ImageView imageAnnonce;
        CardView cardView_detail_Article;

        public AnnonceVH(@NonNull View itemView) {
            super(itemView);
            imageAnnonce = itemView.findViewById(R.id.annonceImage);
            titre = itemView.findViewById(R.id.annonceTitre);
            prix = itemView.findViewById(R.id.annoncePrix);
            date = itemView.findViewById(R.id.annonceDate);
            likeBtn = itemView.findViewById(R.id.annonceLiked);
            cardView_detail_Article = itemView.findViewById(R.id.item_annonce);
        }
    }
}
