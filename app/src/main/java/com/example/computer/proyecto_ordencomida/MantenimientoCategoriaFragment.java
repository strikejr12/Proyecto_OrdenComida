package com.example.computer.proyecto_ordencomida;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.computer.proyecto_ordencomida.Modelo.Categoria;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MantenimientoCategoriaFragment extends Fragment {

    EditText editNombreCategoria;
    EditText editUrlImagenCategoria;
    Button btnGrabar;

    DatabaseReference database;


    public MantenimientoCategoriaFragment() {
        // Required empty public constructor
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Mantenimiento categoria");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mantenimiento_categoria, container, false);


        database = FirebaseDatabase.getInstance().getReference("category");
        editNombreCategoria = (EditText)view.findViewById(R.id.edit_mante_NomCategoria);
        editUrlImagenCategoria = (EditText)view.findViewById(R.id.edit_mante_URLimagenCategoria);
        btnGrabar = (Button)view.findViewById(R.id.btn_mante_categoria_grabar);


        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grabarCategoria();

            }
        });





        return view;


    }

    private void grabarCategoria(){

        String name = editNombreCategoria.getText().toString().trim();
        String url = editUrlImagenCategoria.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(url)){



            Categoria categoria = new Categoria(name,url);
            database.push().setValue(categoria);

            Toast.makeText(getActivity(), "Categoría nueva ingresada", Toast.LENGTH_SHORT).show();


        }else{

            Toast.makeText(getActivity(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();

        }


    }








}
