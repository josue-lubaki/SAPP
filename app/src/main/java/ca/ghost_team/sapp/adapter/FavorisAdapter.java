package ca.ghost_team.sapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.activity.DetailAnnonce;
import ca.ghost_team.sapp.model.Annonce;

public class FavorisAdapter extends RecyclerView.Adapter<FavorisAdapter.FavorisViewHolder> {

    // Constantes
    public static String ANNONCE_IMAGE_REQUEST_FAVORIS = "Annonce_Image";
    public static String ANNONCE_TITRE_REQUEST_FAVORIS = "Annonce_Titre";
    public static String ANNONCE_PRICE_REQUEST_FAVORIS = "Annonce_Prix";
    public static String ANNONCE_DESCRIPTION_REQUEST_FAVORIS = "Annonce_Description";
    Context context;
    List<Annonce> listeAnnoncesFavoris;


    public FavorisAdapter(Context context) {
        this.context = context;
        this.listeAnnoncesFavoris = new ArrayList<>();
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
        // TODO : pour le besoin de necessité, toutes les annonces viendront dans le Favoris, mais le transfret sera normalemnt fait par rapport un Utilisateur

        holder.imageAnnonce.setImageURI(Uri.parse(annonce.getAnnonceImage()));
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

    static class FavorisViewHolder extends RecyclerView.ViewHolder {
        /* On définit les Champs du model */
        TextView titre;
        TextView prix;
        ImageView likeBtn; // TODO : Ajouter le Like puisqu'il sera relier au client, Si le client DisLike, on supprime l'Annonce du favoris
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
        }
    }
}
