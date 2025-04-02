package com.example.batch_72_a_assignment_qvesa;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText etxt_fullName, etxt_phone, etxt_emailAddress, etxt_password;
    Button btn_register;
    TextView txtbtn_goLogin;
    boolean valid = false;

    FirebaseAuth fAuth;

    FirebaseFirestore fStore;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etxt_fullName = findViewById(R.id.ETXT_fullName);
        etxt_phone = findViewById(R.id.ETXT_phone);
        etxt_emailAddress = findViewById(R.id.ETXT_emailAddress);
        etxt_password = findViewById(R.id.ETXT_password);
        btn_register = findViewById(R.id.BTN_register);
        txtbtn_goLogin = findViewById(R.id.TXTBTN_goLogin);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        txtbtn_goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(goLogin);
            }

        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(etxt_fullName);
                checkField(etxt_phone);
                checkField(etxt_emailAddress);
                checkField(etxt_password);

                String name = etxt_fullName.getText().toString();
                String phone = etxt_phone.getText().toString();
                String email = etxt_emailAddress.getText().toString();
                String pass = etxt_password.getText().toString();


                if (valid){
                    fAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Account Created", Toast.LENGTH_SHORT).show();

                            DocumentReference df = fStore.collection("UserDetails72B").document(user.getUid());

                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("Fullname", name);
                            userInfo.put("PhoneNumber", phone);
                            userInfo.put("EmailAddress", email);
                            userInfo.put("Password", pass);

                            userInfo.put("isAdmin", "0");  //

                            df.set(userInfo);





                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
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
}