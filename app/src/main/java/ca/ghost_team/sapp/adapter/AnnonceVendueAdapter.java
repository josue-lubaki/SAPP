package ca.ghost_team.sapp.adapter;

import android.app.AlertDialog;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.activity.DetailAnnonce;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.model.AnnonceFavoris;
import ca.ghost_team.sapp.model.AnnonceImage;
import ca.ghost_team.sapp.repository.AnnonceRepo;
import ca.ghost_team.sapp.repository.MessageRepo;
import ca.ghost_team.sapp.service.API.AnnonceAPI;
import ca.ghost_team.sapp.service.SappAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ca.ghost_team.sapp.BaseApplication.ID_USER_CURRENT;

public class AnnonceVendueAdapter extends RecyclerView.Adapter<AnnonceVendueAdapter.AnnonceVendueVH> {

    private static final String TAG = AnnonceVendueAdapter.class.getSimpleName();
    public static String ANNONCE_ID_REQUEST_ANNONCE_VENDUE = "Annonce_Id_AnnonceVendue";
    Context context;
    private List<Annonce> listeAnnonceVendue;
    private SappDatabase db;

    public AnnonceVendueAdapter(Context context) {
        this.context = context;
        listeAnnonceVendue = new ArrayList<>();
        this.db = Room.databaseBuilder(context, SappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public AnnonceVendueVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_annonce_vendue_item, parent, false);
        return new AnnonceVendueAdapter.AnnonceVendueVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnonceVendueVH holder, int position) {
        Annonce annonce = listeAnnonceVendue.get(position);

        if(annonce.getAnnonceImage() != 0){
            AnnonceImage annonceImage = db.annonceImageDao().findLocationAnnonceImageByAnnonce(annonce.getAnnonceImage());

            String location = annonceImage.getLocation();
            String url = BaseApplication.BASE_URL + location;

            // decoder l'image
            byte[] decodedString = Base64.decode(annonceImage.getImagecode(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            // convert Bitmap to Drawable
            Drawable image = new BitmapDrawable(context.getResources(), decodedByte);

            Glide.with(context)
                    .load(url)
                    .error(image)
                    .fitCenter()
                    .into(holder.annonceImage);
        } else{
            Glide.with(context)
                    .load(R.drawable.image)
                    .fitCenter()
                    .into(holder.annonceImage);
        }

        holder.annonceTitre.setText(annonce.getAnnonceTitre());
        holder.annonceDescription.setText(annonce.getAnnonceDescription());
        holder.annoncePrice.setText("$" + annonce.getAnnoncePrix());

        holder.annonceImage.setOnClickListener(v -> {
            // Creation de l'intent (Envoyer Toutes les informations nécessaires vers l'Activité)
            Intent intent = new Intent(context, DetailAnnonce.class);
            intent.putExtra(ANNONCE_ID_REQUEST_ANNONCE_VENDUE, annonce.getIdAnnonce());
            context.startActivity(intent);
        });

        // Trash
        holder.annonceTrash.setOnClickListener((v)->{

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.confirmAnnonceDelete)
                    .setCancelable(false)
                    .setPositiveButton(R.string.confirmYes, (dialog, which)->{
                        // Supprimer sur la liste (ROOM)
                        Annonce uneAnnonce = listeAnnonceVendue.get(position);
                        listeAnnonceVendue.remove(uneAnnonce);
                        Log.i(TAG,"Annonce supprimé");
                        notifyDataSetChanged();

                        // Methode qui permet de supprimer une annonce via API
                        suppressionAnnonce(annonce);
                    })
                    .setNegativeButton(R.string.confirmNo, (dialog, which)->{
                        dialog.cancel();
                    })
                    .create()
                    .show();

            notifyDataSetChanged();
        });
    }

    /**
     * Methode qui permet de supprimer une annonce
     * @param annonce : annonce à supprimer
     * */
    private void suppressionAnnonce(Annonce annonce) {
        SappAPI.getApi().create(AnnonceAPI.class).deleteMyAnnonce(
                annonce.getIdAnnonce(),
                annonce.getUtilisateurId(),
                annonce.getAnnonceTitre(),
                annonce.getAnnoncePrix()).enqueue(new Callback<Annonce>() {
            @Override
            public void onResponse(Call<Annonce> call, Response<Annonce> response) {
                // Si conncetion Failed
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Connection Failed \nFailedCode : " + response.code());
                    return;
                }

                Log.i(TAG, "response : " + response);
                Annonce reponse = response.body();
                // Envoyer une Requête pour supprimer l'Annonce
                assert reponse != null;
                db.annonceDao().deleteAnnonce(reponse.getIdAnnonce());
                Toast.makeText(context, "\"" + reponse.getAnnonceTitre() + "\" supprimée", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Annonce> call, Throwable t) {
                // Si erreur 404
                listeAnnonceVendue.add(annonce);
                Log.e(TAG, t.getMessage());
            }
        });
    }


    @Override
    public int getItemCount() {
        return listeAnnonceVendue.size();
    }

    public void addAnnonce(List<Annonce> maListe) {
        listeAnnonceVendue = maListe;
        notifyDataSetChanged();
    }

    // class intern
    static class AnnonceVendueVH extends RecyclerView.ViewHolder{

        ImageView annonceImage;
        TextView annonceTitre;
        TextView annonceDescription;
        TextView annoncePrice;
        ImageButton annonceTrash;
        CardView cardViewAnnonceVendue;

        public AnnonceVendueVH(@NonNull View itemView) {
            super(itemView);

            annonceImage = itemView.findViewById(R.id.AnnonceVendueImage);
            annonceTitre = itemView.findViewById(R.id.AnnonceVendueTitre);
            annonceDescription = itemView.findViewById(R.id.AnnonceVendueDescription);
            annoncePrice = itemView.findViewById(R.id.AnnonceVenduePrix);
            annonceTrash = itemView.findViewById(R.id.AnnonceVendueTrash);
            cardViewAnnonceVendue= itemView.findViewById(R.id.cardViewAnnonceVendue);
        }
    }
}
