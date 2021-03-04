package ca.ghost_team.sapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.model.Annonce;

public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceAdapter.AnnonceVH>{
    Context context;
    List<Annonce> listeAnnonces;

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
        holder.imageAnnonce.setImageResource(uneAnnonce.getImageAnnonce());
        holder.titre.setText(uneAnnonce.getTitre());
        holder.prix.setText("$" + uneAnnonce.getPrix());
        holder.date.setText(uneAnnonce.getDate());
        holder.likeBtn.setImageResource(R.drawable.ic_favoris);
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

        public AnnonceVH(@NonNull View itemView) {
            super(itemView);
            imageAnnonce = itemView.findViewById(R.id.annonceImage);
            titre = itemView.findViewById(R.id.annonceTitre);
            prix = itemView.findViewById(R.id.annoncePrix);
            date = itemView.findViewById(R.id.annonceDate);
            likeBtn = itemView.findViewById(R.id.annonceLiked);
        }
    }
}
