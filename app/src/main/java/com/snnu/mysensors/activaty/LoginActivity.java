package com.snnu.mysensors.activaty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.snnu.mysensors.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private Button register;
    private Button login;
    private EditText name;
    private String userName;
    private String loginTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        name = findViewById(R.id.name);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        sp = getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE);
        editor = sp.edit();
        if(view.getId()==R.id.login){
            userName = name.getText().toString();
            if(!"".equals(userName)){
                editor.putString("userName",userName);
                Intent intent = new Intent(this,MainActivity.class);
                loginTime = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date());
                editor.putString("userName",userName);
                editor.putString("loginTime",loginTime.toString());
                editor.commit();
                startActivity(intent);

            }else{
                Toast.makeText(this,"请填写姓名",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
