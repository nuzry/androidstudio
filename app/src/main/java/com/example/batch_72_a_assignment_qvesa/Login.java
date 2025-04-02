package com.example.batch_72_a_assignment_qvesa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText etxt_emailAddress, etxt_password;
    Button btn_login;
    TextView txtbtn_goRegister;

    Boolean valid = false;

    FirebaseAuth fAuth;

    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etxt_emailAddress = findViewById(R.id.ETXT_emailAddress);
        etxt_password = findViewById(R.id.ETXT_password);
        btn_login = findViewById(R.id.BTN_login);
        txtbtn_goRegister = findViewById(R.id.TXTBTN_goRegister);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        txtbtn_goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goRegister = new Intent(getApplicationContext(), Register.class);
                startActivity(goRegister);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(etxt_emailAddress);
                checkField(etxt_password);

                String email = etxt_emailAddress.getText().toString();
                String pass = etxt_password.getText().toString();

                if (valid){
                    fAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this, "Successfull Login", Toast.LENGTH_SHORT).show();
                            checkUserAccess(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    public boolean checkField(EditText ex) {
        if (ex.getText().toString().isEmpty()) {
            ex.setError("Required");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    public void checkUserAccess(String uid)
    {
        DocumentReference df = fStore.collection("UserDetails72B").document(uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.getString("isAdmin") != null)
                {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else
                {
                    startActivity(new Intent(getApplicationContext(), userDashboard.class));
                    finish();
                }

            }
        });
    }
}