package com.example.computer.proyecto_ordencomida;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegistrarActivity extends AppCompatActivity {


    EditText editmail, editpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        editmail = (EditText)findViewById(R.id.edit_reg_user);
        editpass = (EditText)findViewById(R.id.edit_reg_pass);


    }

    void registrar(View view){



        if (editmail. getText().toString().isEmpty()|| editpass.getText().toString().isEmpty()){

            Toast.makeText(RegistrarActivity.this
                    ,"Complete los datos de usuario o password para registrar.",
                    Toast.LENGTH_LONG).show();
        }else {
            //crear cuadro de dialogo
            final ProgressDialog mDialog = new ProgressDialog(RegistrarActivity.this);
            mDialog.setMessage("Por favor, espere mientras se validan los datos...");
            mDialog.show();


            final FirebaseAuth servicioAutenticacion = FirebaseAuth.getInstance();

            servicioAutenticacion.createUserWithEmailAndPassword(editmail.getText().toString(), editpass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //desaparece el cuadro de dialogo despues que se escucha el servicio de firebase
                            mDialog.dismiss();


                            if (!task.isSuccessful()) {

                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(RegistrarActivity.this,
                                            "Ya existe un usuario con este email.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(RegistrarActivity.this, "Verifique formato de email o password mayor a 6 caracteres",
                                            Toast.LENGTH_SHORT).show();

                                }

                            } else {

                                Toast.makeText(RegistrarActivity.this, "Nuevo usuario Registrado!",
                                        Toast.LENGTH_SHORT).show();

                                irMenuPrincipal();



                            }

                            // ...
                        }
                    });

        }
    }//termina el metodo void

    private void irMenuPrincipal(){

        Intent _menuprincipal = new Intent(RegistrarActivity.this,HomeActivity.class);
        _menuprincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(this, "se ingreso al menu despues de registrar", Toast.LENGTH_SHORT).show();
        startActivity(_menuprincipal);
        finish();

    }



}
