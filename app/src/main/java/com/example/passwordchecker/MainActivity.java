package com.example.passwordchecker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import android.text.method.PasswordTransformationMethod;


public class MainActivity extends AppCompatActivity {

    Button generate , check, visibility;
    EditText password;
    ProgressBar statusBar;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generate = findViewById(R.id.generate);
        check = findViewById(R.id.check);
        password = findViewById(R.id.password);
        statusBar = findViewById(R.id.StatusBar);
        result = findViewById(R.id.result);
        visibility = findViewById(R.id.view);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password.getText().toString();
                if (pass.isEmpty()) {
                    result.setText("Please enter a password ");
                } else {
                    StrengthLevel(pass);
                }
            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setText(GeneratePassword());
            }
        });

        visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPassword();
            }
        });

    }

    private void StrengthLevel(String password){

        int strength = 0;
        StringBuilder tips = new StringBuilder();

        if( password.length() >= 8 ) strength++;
        else tips.append("Password must be at least 8 characters long.\n");

        if( password.matches(".*[A-Z].*") ) strength++;
        else tips.append("Password must contain at least one uppercase letter.\n");

        if( password.matches(".*[a-z].*") ) strength++;
        else tips.append("Password must contain at least one lowercase letter.\n");

        if( password.matches(".*[0-9].*") ) strength++;
        else tips.append("Password must contain at least one number.\n");

        if( password.matches(".*[!@#$%^&*()].*") ) strength++;
        else tips.append("Password must contain at least one special character.\n");

        String status;
        if (strength <= 2){
            status = " Weak ";
            statusBar.setProgressTintList(getColorStateList(android.R.color.holo_red_dark));
        } else if (strength == 3) {
            status = " Medium ";
            statusBar.setProgressTintList(getColorStateList(android.R.color.holo_orange_light));
        } else {
            status = " Strong ";
            statusBar.setProgressTintList(getColorStateList(android.R.color.holo_green_light));
        }

        statusBar.setProgress(strength);
        result.setText("Password strength: " + status + "\n" + tips.toString());
    }

    private String GeneratePassword(){
        String chars =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789" +
                "!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();
    }

    private void ViewPassword(){
        if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
            password.setTransformationMethod(null);
        } else {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}