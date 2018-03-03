package com.example.computer.proyecto_ordencomida;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AutenticarActivity extends AppCompatActivity {

    EditText editmail;
    EditText editpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticar);

        editmail = (EditText)findViewById(R.id.editUser);
        editpass = (EditText)findViewById(R.id.editPass);

    }

    void ingresar(View view) {

        if (editmail. getText().toString().isEmpty()|| editpass.getText().toString().isEmpty()){

            Toast.makeText(AutenticarActivity.this
                    ,"Complete los datos de usuario o password para continuar.",
                    Toast.LENGTH_LONG).show();
        }else {
            //crear cuadro de dialogo
            final ProgressDialog mDialog = new ProgressDialog(AutenticarActivity.this);
            mDialog.setMessage("Validando usuario...");
            mDialog.show();

            FirebaseAuth servicioAutenticacion = FirebaseAuth.getInstance();

            servicioAutenticacion.signInWithEmailAndPassword(
                    editmail.getText().toString(),
                    editpass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //desaparece el cuadro de dialogo
                            mDialog.dismiss();
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                //Si no escribio bien sus credenciales

                                Toast.makeText(AutenticarActivity.this
                                        , "Error con su usuario o clave",
                                        Toast.LENGTH_LONG).show();
                            } else {

                                //si es que esta ok

                                Toast.makeText(AutenticarActivity.this
                                        , "Ingresó",
                                        Toast.LENGTH_LONG).show();

                                Intent _homeActivity = new Intent(AutenticarActivity.this,HomeActivity.class);
                                _homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(_homeActivity);


                            }


                            // ...
                        }
                    });

        }

    }

}
