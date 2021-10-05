package com.example.darshanam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    TextView show;
    Button login;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    Thread timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.email);
        password=findViewById(R.id.pass);

        show=findViewById(R.id.show);
        login=findViewById(R.id.login);
        sharedPreferences=this.getSharedPreferences("Login",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if (sharedPreferences.getString("isLogin","false").equals("yes"))
        {
            openDash();
        }

        timer = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(5000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    show.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(password.getInputType()==144) //Hide mode
                            {
                                password.setInputType(129);//Show mode
                                show.setText("Show");
                            }
                            else
                            {
                                password.setInputType(144);
                                show.setText("Hide");
                            }
                            password.setSelection(password.getText().length());
                        }
                    });

                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            validateDate();
                        }
                    });
                }

                }

        };
        timer.start();





}

    private void validateDate() {
        String e=email.getText().toString();
        String p=password.getText().toString();

        if (e.isEmpty())
        {
            email.setError("Required");
            email.requestFocus();
        }
        else if (p.isEmpty())
        {
            password.setError("Required");
            password.requestFocus();
        }
        else if (e.equals("deep@gmail.com") && p.equals("1234"))
        {
            editor.putString("isLogin","yes");
            editor.commit();
            openDash();
        }
        else
        {
//           Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            Toasty.error(MainActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT, true).show();

        }
    }

    private void openDash() {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }
    }

