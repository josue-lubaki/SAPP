package ca.ghost_team.sapp.navigation;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.databinding.LayoutAddpostBinding;

public class AddPost extends Fragment implements AdapterView.OnItemSelectedListener{

    private String[] requiredPermissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    LayoutAddpostBinding binder;
    Spinner spinner;
    private Uri temp;
    private MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder= DataBindingUtil.inflate(inflater, R.layout.layout_addpost, container, false);
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> categories = new ArrayList<>();
        categories.add(0,"Choisir une catégorie: ");
        categories.add("pantalon");
        categories.add("t-shirt");
        categories.add("short");
        categories.add("chaussettes");
        categories.add("casquette");
        categories.add("chandail");

        /* Connection Spinner Categories */
        spinner = binder.addpostCategorie;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1
                , categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // when you click button "Prendre une Photo"
        binder.btnAddImg.setOnClickListener(this::openCamera);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.activity = (MainActivity)getActivity();
    }

    // Methode Qui permet d'ouvrir la Camera
    private void openCamera(View view) {
        for(String permission: requiredPermissions){
            if(ContextCompat.checkSelfPermission(getContext(), permission)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(requiredPermissions, 250);
                return;
            }

        }

        // Lorsque toutes les Permissions ont été données
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.TITLE, "temp.png");
        temp = getActivity().getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, temp);

        if(getContext().getPackageManager()
                .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null){
            startActivityForResult(intent, 260);
        }else{
            Toast.makeText(getContext(), "Désolé, vous n'avez pas de Camera", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 250){
            for(int res: grantResults){
                if(res != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(), "Je n'ai pas la Permission", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            openCamera(null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == 260){
            Glide.with(getContext())
                    .load(temp)
                    .placeholder(R.drawable.collection)
                    .into(binder.addPostCapture);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity)context).setTitle(R.string.addPost);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
