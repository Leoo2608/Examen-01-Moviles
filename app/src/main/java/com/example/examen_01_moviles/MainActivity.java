package com.example.examen_01_moviles;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText txtMail, txtPass;
    Button submit;
    FirebaseAuth firebaseAuth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMail = findViewById(R.id.email);
        txtPass = findViewById(R.id.pass);
        submit = findViewById(R.id.btn_login);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        submit.setOnClickListener(view -> {
            String mail = txtMail.getText().toString().trim();
            String pass = txtPass.getText().toString().trim();

            if(mail == null || mail.equals("")){
                txtMail.setError("Campo Requerido");
            }else if(pass == null || pass.equals("")){
                txtPass.setError("Campo Requerido");
            }else{
                if(mail.matches(emailPattern) || mail.matches(emailPattern2)){
                    firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(task ->{
                        if(task.isSuccessful()){
                            Toast.makeText(
                                    this,
                                    "Sesión Iniciada con éxito!",
                                    Toast.LENGTH_SHORT
                            ).show();
                            startActivity(new Intent(this, HomeActivity.class));
                            finish();
                        }else{
                            Toast.makeText(
                                    this,
                                    "Credenciales incorrectas!",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    });
                }else{
                    Toast.makeText(
                            this,
                            "Correo inválido",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

        });

    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Deseas salir?")
                .setPositiveButton("Si", (dialog, i) -> {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}