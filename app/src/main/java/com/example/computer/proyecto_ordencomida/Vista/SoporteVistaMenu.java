package com.example.computer.proyecto_ordencomida.Vista;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.computer.proyecto_ordencomida.Interfaz.ItemClickListener;
import com.example.computer.proyecto_ordencomida.R;

/**
 * Created by Computer on 17/11/2017.
 */

public class SoporteVistaMenu extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtNombreMenu;
    public ImageView imageMenu;

    private ItemClickListener itemEscogido;




    public SoporteVistaMenu (View itemView){
        super(itemView);

        txtNombreMenu = (TextView)itemView.findViewById(R.id.menu_nombre);
        imageMenu = (ImageView)itemView.findViewById(R.id.menu_imagen);

        itemView.setOnClickListener(this);
    }

    public void setItemEscogido(ItemClickListener itemEscogido) {
        this.itemEscogido = itemEscogido;
    }

    @Override
    public void onClick(View view) {
        itemEscogido.alHacerClick(view,getAdapterPosition(),false);

    }
}
