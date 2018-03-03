package com.example.computer.proyecto_ordencomida.Vista;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.computer.proyecto_ordencomida.Interfaz.ItemClickListener;
import com.example.computer.proyecto_ordencomida.R;

/**
 * Created by Computer on 18/11/2017.
 */

public class SoporteVistaComida extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtnombre_comida;
    public ImageView imagen_comida;

    private ItemClickListener itemEscogido;


    public void setItemEscogido(ItemClickListener itemEscogido) {
        this.itemEscogido = itemEscogido;
    }

    public SoporteVistaComida(View itemView) {
        super(itemView);

        txtnombre_comida = (TextView)itemView.findViewById(R.id.comida_nombre);
        imagen_comida = (ImageView)itemView.findViewById(R.id.comida_imagen);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        itemEscogido.alHacerClick(view,getAdapterPosition(),false);
    }

}
