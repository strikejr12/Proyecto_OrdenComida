package com.example.computer.proyecto_ordencomida;

import android.app.ProgressDialog;
import android.content.Intent;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FacebookLogin";


    Button btnRegistrar, btnAutenticar;
    TextView txtSlogan;

    private LoginButton btnLoginFb;
    private CallbackManager callbackManager;

    //probando verificacion
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //si alguien esta logueado al cargarse el activity principal (MainActivity)
        /*if (AccessToken.getCurrentAccessToken()!=null){
            irMenuPrincipal();
        }*/


        // para obtner los KeyHashes (fb)
        // System.out.print("KeyHashes " + KeyHashes());

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Toast.makeText(this, "ya hay un usuario logueado", Toast.LENGTH_SHORT).show();
            irMenuPrincipal();
        }


        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnAutenticar = (Button) findViewById(R.id.btnAutenticar);

        txtSlogan = (TextView) findViewById(R.id.txtSlogan);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/NABILA.TTF");
        txtSlogan.setTypeface(face);

        //para login con fb

        callbackManager = CallbackManager.Factory.create();
        btnLoginFb = (LoginButton) findViewById(R.id.btnLoginFacebook);
        btnLoginFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                //irMenuPrincipal();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();

            }
        });


    }
/*metodo para obtener los keyhashes y ponerlo en fb

    public String KeyHashes(){
        PackageInfo info;
        String KeyHashes = null;
        try {
            info=getPackageManager().getPackageInfo("com.example.computer.proyecto_ordencomida", PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest md;
                md=MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                KeyHashes = new String(Base64.encode(md.digest(),0));

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return KeyHashes;
    }*/

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        /*FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser!=null){
            Toast.makeText(this, "entraste porque quiero", Toast.LENGTH_SHORT).show();
            irMenuPrincipal();
        }*/
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setMessage("Validando credenciales en facebook...");
        mDialog.show();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        mDialog.dismiss();


                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            irMenuPrincipal();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    void registrarUsuario(View view) {

        Intent _registrarActivity = new Intent(MainActivity.this, RegistrarActivity.class);
        startActivity(_registrarActivity);

    }

    void autenticarUsuario(View view) {


        Intent _autenticarActivity = new Intent(MainActivity.this, AutenticarActivity.class);
        startActivity(_autenticarActivity);


    }

    void irMenuPrincipal() {

        Intent _interfazprincipal = new Intent(MainActivity.this, HomeActivity.class);
        _interfazprincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(_interfazprincipal);
        finish();
    }


}
