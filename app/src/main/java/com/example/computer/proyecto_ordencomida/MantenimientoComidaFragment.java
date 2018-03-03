package com.example.computer.proyecto_ordencomida;


import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.computer.proyecto_ordencomida.Modelo.Categoria;
import com.example.computer.proyecto_ordencomida.Modelo.Comida;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MantenimientoComidaFragment extends Fragment {

    EditText editNombrecomida,
            editDescripcion,
            editUrlComida,
            editPrecio,
            editDescuento,
            editCategoriaId;

    Spinner spnCategoria;

    Button btnGrabarComida;

    DatabaseReference database;

    String idcategoria="";




    public MantenimientoComidaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        getActivity().setTitle("Mantenimiento comida");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_mantenimiento_comida, container, false);



        //database = FirebaseDatabase.getInstance().getReference("foods");

        database = FirebaseDatabase.getInstance().getReference();
        database.child("category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> categorias = new ArrayList<>();
                final List<String> ids = new ArrayList<>();


                for (DataSnapshot categoriaSnapshot : dataSnapshot.getChildren() ){


                    String nombreCategoria = categoriaSnapshot.child("name").getValue(String.class);
                    String idcategorias = categoriaSnapshot.getKey();

                    categorias.add(nombreCategoria);
                    ids.add(idcategorias);


                }

                spnCategoria = view.findViewById(R.id.spn_mantecomida_Categoria);

                ArrayAdapter<String>categoriaAdapter = new ArrayAdapter<>
                        (getActivity(),android.R.layout.simple_spinner_item,categorias);


                categoriaAdapter.setDropDownViewResource(android.R.layout.
                        simple_spinner_dropdown_item);



                spnCategoria.setAdapter(categoriaAdapter);

                categoriaAdapter.notifyDataSetChanged();

                spnCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {

                        Toast.makeText(getActivity(), "ID: "+ ids.get(pos), Toast.LENGTH_SHORT).show();

                        idcategoria = ids.get(pos);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                        Log.i("Message", "Nothing is selected");

                    }


                });




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        editNombrecomida = (EditText)view.findViewById(R.id.edit_mantecomida_NombreComida);
        editDescripcion = (EditText)view.findViewById(R.id.edit_mantecomida_Descripcion);
        editUrlComida = (EditText)view.findViewById(R.id.edit_mantecomida_URLImagenComida);
        editPrecio = (EditText)view.findViewById(R.id.edit_mantecomida_Precio);
        editDescuento = (EditText)view.findViewById(R.id.edit_mantecomida_Descuento);
       // editCategoriaId = (EditText)view.findViewById(R.id.edit_mantecomida_CategoriaID);


        btnGrabarComida = (Button)view.findViewById(R.id.btn_mantecomida_grabar);

        btnGrabarComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grabarComida();
            }
        });





        return view;

    }

    private void grabarComida(){

        DatabaseReference tb_comida = FirebaseDatabase.getInstance().getReference("foods");

        String name = editNombrecomida.getText().toString().trim();
        String url = editUrlComida.getText().toString().trim();
        String descripcion = editDescripcion.getText().toString().trim();
        String precio = editPrecio.getText().toString().trim();
        String descuento = editDescuento.getText().toString().trim();

        //String categoriaId = editCategoriaId.getText().toString().trim();




        if(validarCamposManteComida(name, url, descripcion, precio, descuento, idcategoria)){



            Comida comida = new Comida(name,url,descripcion,precio,descuento,idcategoria);
            tb_comida.push().setValue(comida);

            Toast.makeText(getActivity(), "Comida nueva ingresada", Toast.LENGTH_SHORT).show();


        }else{

            Toast.makeText(getActivity(), "Debe completar todos los campos solcitados", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean validarCamposManteComida(String name, String url, String descripcion, String precio, String descuento
    , String categoriaId){
        boolean flag=false;

        if (!TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(url) &&
                !TextUtils.isEmpty(descripcion) &&
                !TextUtils.isEmpty(precio) &&
                !TextUtils.isEmpty(descuento) &&
                !TextUtils.isEmpty(categoriaId) )
        {
            flag=true;
        }else
        {flag=false;}


        return flag;

    }







}
