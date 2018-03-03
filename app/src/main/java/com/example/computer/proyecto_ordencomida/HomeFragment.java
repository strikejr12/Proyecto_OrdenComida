package com.example.computer.proyecto_ordencomida;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.computer.proyecto_ordencomida.Interfaz.ItemClickListener;
import com.example.computer.proyecto_ordencomida.Modelo.Categoria;
import com.example.computer.proyecto_ordencomida.Vista.SoporteVistaMenu;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class HomeFragment extends Fragment {

    //probando verificacion
    FirebaseAuth mAuth;
    //

    FirebaseDatabase database;
    DatabaseReference categoria;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Categoria,SoporteVistaMenu> adaptador;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Menu");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        mAuth = FirebaseAuth.getInstance();




        //obtener token de logeo
        /*
        if (AccessToken.getCurrentAccessToken()==null){
            irLogin();
        }*/

        //Inicialzando firebase
        database = FirebaseDatabase.getInstance();

        categoria = database.getReference("category");

        //cargar menu

        recycler_menu= (RecyclerView)view.findViewById(R.id.recycler_fragment_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycler_menu.setLayoutManager(layoutManager);

        cargarMenu();

        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void cargarMenu(){

        adaptador = new FirebaseRecyclerAdapter<Categoria,
                SoporteVistaMenu>(Categoria.class,
                R.layout.menu_item,
                SoporteVistaMenu.class,
                categoria) {
            @Override
            protected void populateViewHolder(SoporteVistaMenu soporteVista, Categoria beanCategoria, int position) {
                soporteVista.txtNombreMenu.setText(beanCategoria.getName());
                Picasso.with(getActivity().getBaseContext()).load(beanCategoria.getImage())
                        .into(soporteVista.imageMenu);

                final Categoria categoriaEscogido = beanCategoria;
                soporteVista.setItemEscogido(new ItemClickListener() {
                    @Override
                    public void alHacerClick(View view, int posicion, boolean isLongClick) {
                        Toast.makeText(getActivity(), ""+categoriaEscogido.getName(), Toast.LENGTH_SHORT).show();

                        //Obtener idCategoria y enviarlo al nuevo Activity
                        Intent _listaComidaActivity = new Intent(getActivity(),ListaComidaActivity.class);
                        _listaComidaActivity.putExtra("CategoryId",adaptador.getRef(posicion).getKey());
                        startActivity(_listaComidaActivity);

                    }
                });
            }
        };
        recycler_menu.setAdapter(adaptador);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser==null){
            irLogin();
        }/*else if (AccessToken.getCurrentAccessToken()==null){
            irLogin();
        }*/
    }

    private void irLogin(){

        Intent _irLogin = new Intent(getActivity(),MainActivity.class);
        getActivity().getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(_irLogin);
        getActivity().finish();
    }
}
