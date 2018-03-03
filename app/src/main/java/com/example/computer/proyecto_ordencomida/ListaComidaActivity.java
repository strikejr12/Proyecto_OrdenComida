package com.example.computer.proyecto_ordencomida;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.computer.proyecto_ordencomida.Interfaz.ItemClickListener;
import com.example.computer.proyecto_ordencomida.Modelo.Comida;
import com.example.computer.proyecto_ordencomida.Vista.SoporteVistaComida;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ListaComidaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference lista_comida;

    String idCategoria="";

    FirebaseRecyclerAdapter<Comida,SoporteVistaComida> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comida);



        //firebase
        database = FirebaseDatabase.getInstance();
        lista_comida = database.getReference("foods");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_comida);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //obtener acá el Intent para listar las comidas segun su categoria

        if (getIntent()!=null){
            idCategoria = getIntent().getStringExtra("CategoryId");
        }
        if (!idCategoria.isEmpty() && idCategoria !=null){
            cargarListaComida(idCategoria);
        }

    }

    private void cargarListaComida(String idCategoria) {

        adaptador = new FirebaseRecyclerAdapter<Comida, SoporteVistaComida>(Comida.class,
                                R.layout.comida_item,
                                SoporteVistaComida.class,
                                lista_comida.orderByChild("menuId").equalTo(idCategoria)
                                //esto es como: select * from foods where menuid=idcategoria
                ) {
            @Override
            protected void populateViewHolder(SoporteVistaComida viewHolder, Comida model, int position) {

                viewHolder.txtnombre_comida.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imagen_comida);

                final Comida _comida = model;

                viewHolder.setItemEscogido(new ItemClickListener() {
                    @Override
                    public void alHacerClick(View view, int posicion, boolean isLongClick) {

                            //Empezar nuevo Activity detalle

                        Intent _detallecomidaActivity = new Intent(ListaComidaActivity.this,
                                                                DetalleComidaActivity.class);

                        _detallecomidaActivity.putExtra("FoodId",
                                            adaptador.getRef(posicion).getKey());//se envia el id de la comida
                                                                                 //al nuevo activity
                        startActivity(_detallecomidaActivity);

                    }
                });
            }
        };

        // establecer adaptador
        recyclerView.setAdapter(adaptador);
    }
}
