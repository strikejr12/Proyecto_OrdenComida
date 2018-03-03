package com.example.computer.proyecto_ordencomida;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.computer.proyecto_ordencomida.DataBase.SqlHelper;
import com.example.computer.proyecto_ordencomida.Modelo.Comida;
import com.example.computer.proyecto_ordencomida.Modelo.Orden;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DetalleComidaActivity extends AppCompatActivity {

    TextView txt_detallecomida_nombre,txt_detallecomida_precio,
            txt_detallecomida_descripcion;

    ImageView img_detallecomida;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btn_detallecomida_carrito;
    ElegantNumberButton btn_detallecomida_incremento;
    String idComida="";

    FirebaseDatabase database;
    DatabaseReference comidas;

    Comida comidaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_comida);


        //firebase
        database = FirebaseDatabase.getInstance();
        comidas = database.getReference("foods");

        //inicializar view

        btn_detallecomida_incremento = (ElegantNumberButton)findViewById(R.id.btn_detalle_nroIncremento);
        btn_detallecomida_carrito = (FloatingActionButton)findViewById(R.id.btnCarrito_detalleComida);

        btn_detallecomida_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SqlHelper(getBaseContext()).agregarAlcarro(
                        new Orden(
                                idComida,
                                comidaActual.getName(),
                                btn_detallecomida_incremento.getNumber(),
                                comidaActual.getPrice(),
                                comidaActual.getDiscount()
                                
                        )
                );

                Toast.makeText(DetalleComidaActivity.this, "La comida escogida se agregó al carrito", Toast.LENGTH_SHORT).show();
            }
        });
        txt_detallecomida_descripcion = (TextView)findViewById(R.id.txt_detalle_comidaDescripcion);
        txt_detallecomida_nombre = (TextView)findViewById(R.id.txt_detalle_nombrecomida);
        txt_detallecomida_precio = (TextView)findViewById(R.id.txtnombre_detalle_preciocomida);
        img_detallecomida = (ImageView)findViewById(R.id.img_comida_detalle);


        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.ctl_detalle_colapso);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if (getIntent() !=null)
            idComida = getIntent().getStringExtra("FoodId");
        if (!idComida.isEmpty()){
            getDetalleComida(idComida);
        }
    }

    private void getDetalleComida(String IDcomida) {


        comidas.child(IDcomida).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                comidaActual = dataSnapshot.getValue(Comida.class);

                //establecer imagen
                Picasso.with(getBaseContext()).load(comidaActual.getImage())
                        .into(img_detallecomida);

                collapsingToolbarLayout.setTitle(comidaActual.getName());

                //formato al precio
                String numero = comidaActual.getPrice();
                double monto = Double.parseDouble(numero);
                DecimalFormat formato = new DecimalFormat("#,###.00");

                txt_detallecomida_precio.setText(formato.format(monto));
                //--------------------------------------------------------
                txt_detallecomida_nombre.setText(comidaActual.getName());
                txt_detallecomida_descripcion.setText(comidaActual.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
