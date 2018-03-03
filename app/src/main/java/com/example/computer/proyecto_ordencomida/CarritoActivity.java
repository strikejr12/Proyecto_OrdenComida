package com.example.computer.proyecto_ordencomida;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.computer.proyecto_ordencomida.DataBase.SqlHelper;
import com.example.computer.proyecto_ordencomida.Modelo.Orden;
import com.example.computer.proyecto_ordencomida.Modelo.Pedido;
import com.example.computer.proyecto_ordencomida.Vista.CarritoAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarritoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase firebaseDB;
    DatabaseReference request;

    TextView txtPrecioTotal;
    Button btnColocarOrden;

    List<Orden> carrito = new ArrayList<>();

    CarritoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        //Firebase
        firebaseDB = FirebaseDatabase.getInstance();
        request = firebaseDB.getReference("Requests");

        //inicializar

        recyclerView = (RecyclerView)findViewById(R.id.listarCarrito);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtPrecioTotal = (TextView)findViewById(R.id.txtTotalPrecioEnCarrito);
        btnColocarOrden = (Button)findViewById(R.id.btnColocarOrden);

        btnColocarOrden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrarAlertaDeDialogo();




            }
        });

        cargarListaDeOrdenes();



    }

    private void mostrarAlertaDeDialogo(){

        AlertDialog.Builder dialogoDeAlerta = new AlertDialog.Builder(CarritoActivity.this);
        dialogoDeAlerta.setTitle("A un paso más");
        dialogoDeAlerta.setMessage("Ingrese su su dirección: ");

        final EditText edtDireccion = new EditText(CarritoActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        edtDireccion.setLayoutParams(layoutParams);
        dialogoDeAlerta.setView(edtDireccion);
        dialogoDeAlerta.setIcon(R.drawable.ic_shopping_cart_black_24dp);


    }



    private void cargarListaDeOrdenes() {

        carrito = new SqlHelper(this).obtenerOrden();
        adapter = new CarritoAdapter(carrito,this);
        recyclerView.setAdapter(adapter);

        //calcular precio total)

            int total = 0;

            for(Orden orden:carrito){

                total+=(Integer.parseInt(orden.getPrecio()))*(Integer.parseInt(orden.getCantidad()));
        }

        Locale local = new Locale("ES","PE");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(local);

        txtPrecioTotal.setText(fmt.format(total));





    }




}
