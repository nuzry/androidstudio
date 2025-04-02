package com.example.batch_72_a_assignment_qvesa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    EditText etxt_emailAddress, etxt_password;
    Button btn_login;
    TextView txtbtn_goRegister;

    boolean valid = false;

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