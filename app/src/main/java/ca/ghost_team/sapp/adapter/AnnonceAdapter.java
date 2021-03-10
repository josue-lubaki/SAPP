package ca.ghost_team.sapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.ghost_team.sapp.DetailAnnonce;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.model.Annonce;

public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceAdapter.AnnonceVH>{
    Context context;
    List<Annonce> listeAnnonces;

    // Constantes
    public static String ANNONCE_IMAGE_REQUEST = "Annonce_Image";
    public static String ANNONCE_TITRE_REQUEST = "Annonce_Titre";
    public static String ANNONCE_PRICE_REQUEST = "Annonce_Prix";
    public static String ANNONCE_DESCRIPTION_REQUEST = "Annonce_Description";


    public AnnonceAdapter(Context context) {
        this.context = context;
        this.listeAnnonces = new ArrayList<>();
    }

    @NonNull
    @Override
    public AnnonceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_annonce_item, parent, false);
        return new AnnonceVH(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AnnonceVH holder, int position) {
        Annonce uneAnnonce = listeAnnonces.get(position);
        holder.imageAnnonce.setImageResource(uneAnnonce.getAnnonce_image());
        holder.titre.setText(uneAnnonce.getAnnonce_titre());
        holder.prix.setText("$" + uneAnnonce.getAnnonce_prix());
        holder.date.setText(uneAnnonce.getAnnonce_date());

        // Donner les états initials du Boutton
        if (!uneAnnonce.isAnnonce_liked())
            holder.likeBtn.setImageResource(R.drawable.ic_favoris);
        else
            holder.likeBtn.setImageResource(R.drawable.ic_favoris_red);

        // set OnClickListener pour liker l'Annonce par le button
        holder.likeBtn.setOnClickListener(v -> {
            if(!uneAnnonce.isAnnonce_liked()){
                holder.likeBtn.setImageResource(R.drawable.ic_favoris_red);
                uneAnnonce.setAnnonce_liked(true); // setter le changement
            }
            else{
                holder.likeBtn.setImageResource(R.drawable.ic_favoris);
                uneAnnonce.setAnnonce_liked(false); // setter le changement
            }
            notifyDataSetChanged();
        });

        // set OnClickListener
        holder.cardView_detail_Article.setOnClickListener(v -> {
            // Creation de l'intent (Envoyer Toutes les informations nécessaires vers l'Activité)
            Intent intent = new Intent(context, DetailAnnonce.class);
            intent.putExtra(ANNONCE_IMAGE_REQUEST, uneAnnonce.getAnnonce_image());
            intent.putExtra(ANNONCE_TITRE_REQUEST, uneAnnonce.getAnnonce_titre());
            intent.putExtra(ANNONCE_PRICE_REQUEST, uneAnnonce.getAnnonce_prix());
            intent.putExtra(ANNONCE_DESCRIPTION_REQUEST, uneAnnonce.getAnnonce_description());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listeAnnonces.size();
    }


    public void addAnnonce(List<Annonce> maListe){
        listeAnnonces = maListe;
        notifyDataSetChanged();
    }

    static class AnnonceVH extends RecyclerView.ViewHolder{
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
