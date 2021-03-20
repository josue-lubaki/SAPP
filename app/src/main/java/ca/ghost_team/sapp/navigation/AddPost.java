package ca.ghost_team.sapp.navigation;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

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
        spinner = binder.addpostCategorie;
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1
                ,categories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);





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
