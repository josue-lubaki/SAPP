package ca.ghost_team.sapp.navigation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import ca.ghost_team.sapp.BaseApplication;
import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.Utils.ImageResizer;
import ca.ghost_team.sapp.database.SappDatabase;
import ca.ghost_team.sapp.databinding.LayoutAddpostBinding;
import ca.ghost_team.sapp.model.Annonce;
import ca.ghost_team.sapp.repository.AnnonceRepo;
import ca.ghost_team.sapp.service.API.AnnonceAPI;
import ca.ghost_team.sapp.service.SappAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static java.lang.Thread.sleep;

import static ca.ghost_team.sapp.Utils.Utilitaire.toTimeStr;

public class AddPost extends Fragment implements AdapterView.OnItemSelectedListener {

    private final String[] requiredPermissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    LayoutAddpostBinding binder;
    Spinner spinner;
    private Uri temp;
    private MainActivity activity;
    private EditText titre;
    private EditText description;
    private EditText prix;
    private EditText codePostal;
    private int idCategorie;
    private SappDatabase db;
    private final String TAG = AddPost.class.getSimpleName();
    private Bitmap bitmap;
    private Bitmap reducedBitmap;
    private Uri path;
    private String encodeImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.layout_addpost, container, false);
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bind Fields on this Fragment
        titre = binder.addpostTitre;
        description = binder.addpostDescription;
        prix = binder.addpostPrix;
        codePostal = binder.addpostCodepostal;

        // Create List of categories
        List<String> categories = new ArrayList<>();
        categories.add(0, getContext().getResources().getString(R.string.CategorieSelection));
        categories.add(1, getContext().getResources().getString(R.string.pants));
        categories.add(2, getContext().getResources().getString(R.string.Tshirt));
        categories.add(3, getContext().getResources().getString(R.string.Hoodie));
        categories.add(4, getContext().getResources().getString(R.string.Short));
        categories.add(5, getContext().getResources().getString(R.string.Cap));
        categories.add(6, getContext().getResources().getString(R.string.Other));

        // Take the instance of Spinner
        spinner = binder.addpostCategorie;
        spinner.setBackgroundColor(getResources().getColor(R.color.BackgroundAnnonce));

        // Create the instance of ArrayAdapter
        // having the list of Categories
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                categories);

        // set simple layout resource file for each item of spinner
        // Set the ArrayAdapter (adapter) data on the Spinner which binds data to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // when you click button "Prendre une Photo"
        binder.btnAddImg.setOnClickListener(this::openCamera);

        // when you click button "Publier"
        binder.addpostBtnPost.setOnClickListener(this::publierAnnonce);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity) context).setTitle(R.string.addPost);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.activity = (MainActivity) getActivity();
    }

    // Methode Qui permet d'ouvrir la Camera
    private void openCamera(View view) {
        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(requiredPermissions, 250);
                return;
            }
        }

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent, 260);

        // Lorsque toutes les Permissions ont été données
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        ContentValues values = new ContentValues();
//
//        values.put(MediaStore.Images.Media.TITLE, "temp.png");
//        temp = getActivity().getContentResolver()
//                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, temp);
//
//        if (getContext().getPackageManager()
//                .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
//            startActivityForResult(intent, 260);
//        } else {
//            Toast.makeText(getContext(), getContext().getResources().getString(R.string.dontCamera), Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 250) {
            for (int res : grantResults) {
                if (res != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.dontPermission), Toast.LENGTH_LONG).show();
                    return;
                }
            }
            openCamera(null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 260 && data != null) {
//            Glide.with(getContext())
//                    .load(temp)
//                    .placeholder(R.drawable.collection)
//                    .into(binder.addPostCapture);

            path = data.getData();
            uploadFile(path);

            Log.i(TAG, "Voici le path: " + path);
        }

    }

    private void uploadFile(Uri fileUri){
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
            reducedBitmap = ImageResizer.reduceBitmapSize(bitmap, 2099200);
            binder.addPostCapture.setImageBitmap(bitmap);
            Log.i(TAG, "Je viens de setter l'image Bitmap reducedBitmap");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // initialize byte stream
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // compress Bitmap (quality: 100%)
        reducedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        // Initialize byte array
        byte[] imageInBytes = stream.toByteArray();

        // get base64 encoded string
        encodeImage = Base64.encodeToString(imageInBytes, Base64.DEFAULT).replace("\n", "").replace("\r","");

        Log.i(TAG, "Voici l'image encodée : \n" + encodeImage);

    }

    /**
     * Methode Qui permet de Publier L'Annonce en récuperant toutes les informations fournit
     * par l'Utilisateur
     */
    private void publierAnnonce(View view) {
        // Vérifier que tous les champs sont bien remplies
        if (TextUtils.isEmpty(titre.getText().toString().trim()) ||
                TextUtils.isEmpty(description.getText().toString().trim()) ||
                TextUtils.isEmpty(prix.getText().toString().trim()) ||
                TextUtils.isEmpty(codePostal.getText().toString().trim()) ||
                idCategorie == 0) {

            if (TextUtils.isEmpty(titre.getText().toString().trim())) {
                titre.setError("Titre required");
                titre.requestFocus();
            }

            if (idCategorie == 0)
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.CategorieSelection), Toast.LENGTH_LONG).show();

            if (TextUtils.isEmpty(prix.getText().toString().trim())) {
                prix.setError("Price required");
                prix.requestFocus();
            }
            if (TextUtils.isEmpty(codePostal.getText().toString().trim())) {
                codePostal.setError("ZIP Postal required");
                codePostal.requestFocus();
            }
            return;
        }

        Log.i(TAG,"valeur de String.valueOf(new Date() : " + new Date());
        Log.i(TAG,"valeur de toTimeStr(new Date() : " + toTimeStr(new Date()));
        Log.i(TAG,"valeur de new Date() : " + new Date());

        /**
         * Création de l'Annonce
         * Le Serveur va sauvergarder l'annonce et l'image de celle-ci
         * */
        SappAPI.getApi().create(AnnonceAPI.class).createAnnonceViaAPI(
                encodeImage,
                titre.getText().toString(),
                description.getText().toString(),
                Integer.parseInt(prix.getText().toString()),
                String.valueOf(new Date()),
                codePostal.getText().toString().trim(),
                BaseApplication.ID_USER_CURRENT,
                idCategorie
        ).enqueue(new Callback<Annonce>() {
            @Override
            public void onResponse(Call<Annonce> call, Response<Annonce> response) {
                // Si conncetion Failed
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Connection Failed \nFailedCode : " + response.code());
                    return;
                }

                Annonce newAnnonce =  response.body();

                Log.i(TAG, "response.body = " + response);
                // inserer l'annonce dans la base de données locale via le Repository
                new AnnonceRepo(activity.getApplication()).insertAnnonce(newAnnonce);
                Toast.makeText(getContext(), requireContext().getResources().getString(R.string.offerPost), Toast.LENGTH_LONG).show();
                Log.i(TAG, "Annonce inserted !");
            }
            @Override
            public void onFailure(Call<Annonce> call, Throwable t) {
                // Si erreur 404
                Log.e(TAG, t.getMessage());
            }
        });

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String nomCategorie = parent.getItemAtPosition(position).toString();
        db = Room.databaseBuilder(getContext(), SappDatabase.class, BaseApplication.NAME_DB)
                .allowMainThreadQueries().build();

        idCategorie = db.categorieAnnonceDao().findIndexCategorieByName(nomCategorie);
        Log.i(TAG, nomCategorie + " (id) : " + idCategorie);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


}
