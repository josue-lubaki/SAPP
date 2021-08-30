package ca.ghost_team.sapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.activity.DetailAnnonce;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.AnnonceFavoris;
import ca.ghost_team.sapp.model.AnnonceImage;

import static ca.ghost_team.sapp.BaseApplication.ID_USER_CURRENT;

public class FavorisAdapter extends RecyclerView.Adapter<FavorisAdapter.FavorisViewHolder> {

    // Constantes
    public static String ANNONCE_IMAGE_REQUEST_FAVORIS = "Annonce_Image";
    public static String ANNONCE_TITRE_REQUEST_FAVORIS = "Annonce_Titre";
    public static String ANNONCE_PRICE_REQUEST_FAVORIS = "Annonce_Prix";
    public static String ANNONCE_DESCRIPTION_REQUEST_FAVORIS = "Annonce_Description";
    Context context;
    List<Annonce> listeAnnoncesFavoris;
    private SappDatabase db;


    public FavorisAdapter(Context context) {
        this.context = context;
        this.listeAnnoncesFavoris = new ArrayList<>();
        this.db = Room.databaseBuilder(context, SappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public FavorisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_favoris_item, parent, false);
        return new FavorisViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavorisViewHolder holder, int position) {
        Annonce annonce = listeAnnoncesFavoris.get(position);

        AnnonceImage annonceImage = db.annonceImageDao().findLocationAnnonceImageByAnnonce(annonce.getAnnonceImage());
        if(annonceImage == null){
            return;
        }
        String location = annonceImage.getLocation();
        String url = BaseApplication.BASE_URL + location;

        if(annonce.getAnnonceImage() != 0){
            Picasso.with(context)
                    .load(url)
                    .into(holder.imageAnnonce);
//            holder.imageAnnonce.setImageURI(Uri.parse(annonce.getAnnonceImage()));
        } else
            holder.imageAnnonce.setImageResource(R.drawable.collection);

        holder.titre.setText(annonce.getAnnonceTitre());
        holder.description.setText(annonce.getAnnonceDescription());
        holder.prix.setText("$ " + annonce.getAnnoncePrix());

        // set OnClickListener
        holder.cardViewFavoris.setOnClickListener(v -> {
            // Creation de l'intent (Envoyer Toutes les informations nécessaires vers l'Activité)
            Intent intent = new Intent(context, DetailAnnonce.class);
            intent.putExtra(ANNONCE_IMAGE_REQUEST_FAVORIS, annonce.getAnnonceImage());
            intent.putExtra(ANNONCE_TITRE_REQUEST_FAVORIS, annonce.getAnnonceTitre());
            intent.putExtra(ANNONCE_PRICE_REQUEST_FAVORIS, annonce.getAnnoncePrix());
            intent.putExtra(ANNONCE_DESCRIPTION_REQUEST_FAVORIS, annonce.getAnnonceDescription());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listeAnnoncesFavoris.size();
    }

    public void addAnnonceToFavoris(List<Annonce> listeAnnonceLiked) {
        listeAnnoncesFavoris = listeAnnonceLiked;
        notifyDataSetChanged();
    }

    static class FavorisViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        /* On définit les Champs du model */
        TextView titre;
        TextView prix;
        ImageView imageAnnonce;
        TextView description;
        CardView cardViewFavoris;


        public FavorisViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAnnonce = itemView.findViewById(R.id.favoris_image);
            titre = itemView.findViewById(R.id.favoris_titre);
            prix = itemView.findViewById(R.id.favoris_price);
            description = itemView.findViewById(R.id.favoris_description);
            cardViewFavoris = itemView.findViewById(R.id.cardViewFavoris);
            cardViewFavoris.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.setHeaderTitle("Selectionner une action");
            menu.add(this.getAdapterPosition(), R.id.favorite_delete, 0, "Supprimer l'annonce des favoris");//groupId, itemId, order, title
        }
    }

    public void removeFromFavorites(int position){
        Annonce uneAnnonce = listeAnnoncesFavoris.get(position);
        listeAnnoncesFavoris.remove(position);

        // Supprimer l'enregitrement dans la Table des Annonces Favoris
        db.annonceFavorisDao().deleteAnnonceByID(ID_USER_CURRENT,uneAnnonce.getIdAnnonce());
        notifyDataSetChanged();
    }

}
