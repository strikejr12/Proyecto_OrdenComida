package com.example.computer.proyecto_ordencomida;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.computer.proyecto_ordencomida.Interfaz.ItemClickListener;
import com.example.computer.proyecto_ordencomida.Modelo.Categoria;
import com.example.computer.proyecto_ordencomida.Vista.SoporteVistaMenu;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int INDEX_OF_TAB_WITH_FAB = 0;
    private FloatingActionButton mFab;

    FirebaseAuth mAuth;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        mFab = (FloatingActionButton) findViewById(R.id.fab);





        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case INDEX_OF_TAB_WITH_FAB:
                        mFab.show();
                        break;

                    default:
                        mFab.hide();
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Carrito en proceso", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */

                Intent carritoIntent= new Intent(HomeActivity.this,CarritoActivity.class);
                startActivity(carritoIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //para la flecha de regresar en la parte superior
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //email de usuario logeado

       //iniciar fragment principal

        mostrarFragmentPrincipal();

    }

    private void mostrarFragmentPrincipal() {
        fragment = new HomeFragment();
        if(fragment!=null){

            FragmentManager fm = getSupportFragmentManager();
            // ft.replace(R.id.contenedorPrincipal,fragment,fragment.getTag());
            fm.beginTransaction().replace(R.id.contenedorPrincipal, fragment).commit();
        }
    }


    @Override
    public void onBackPressed() {
            /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                super.onBackPressed();

            }*/
                /*int count = getFragmentManager().getBackStackEntryCount();
                if (count == 0) {
                    super.onBackPressed();
                    //additional code
                } else {
                    getFragmentManager().popBackStack();
                }*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(fragment instanceof HomeFragment){

                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Cerrar aplicación")
                        .setMessage("Presionando el mismo botón otra vez saldrá de la aplicación;" +
                                " sin embargo, su cuenta permanecerá abierta. " +
                                "¿Está seguro?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                HomeActivity.super.onBackPressed();
                            }
                        }).setNegativeButton("No", null).show();

            }else{
                mostrarFragmentPrincipal();
            }



        }


        //fin de otro if (arriba)




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_menu) {

            /*Intent menu = new Intent(HomeActivity.this,HomeActivity.class);
            startActivity(menu);
            finish();*/
            fragment = new HomeFragment();


        } else if (id == R.id.nav_carrito) {
            fragment = new MantenimientoCategoriaFragment();

        } else if (id == R.id.nav_ordenes) {
            fragment = new MantenimientoComidaFragment();

        } else if (id == R.id.nav_ubicanos){
            fragment = new UbicanosFragment();

        } else if (id == R.id.nav_salir) {
            //LoginManager.getInstance().logOut();
            Toast.makeText(getApplicationContext(),"presiono salir",Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            irLogin();

        }

        if(fragment != null){
            FragmentManager fragmentmanager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentmanager.beginTransaction();

            ft.replace(R.id.contenedorPrincipal,fragment);
           // ft.replace(R.id.contenedorPrincipal,fragment,fragment.getTag());
           // ft.addToBackStack(null);
            ft.commit();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void irLogin(){

        Intent _irLogin = new Intent(HomeActivity.this,MainActivity.class);
        getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(_irLogin);
        finish();
    }

}
