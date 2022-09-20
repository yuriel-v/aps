package com.example.aps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void translate(View view)
    {
        TextView msg = findViewById(R.id.welcome_string);
        Button exitBtn = findViewById(R.id.exit_btn);
        switch (view.getId())
        {
            case (R.id.pt_btn):
                msg.setText("Olá mundo!");
                exitBtn.setText("Sair");
                break;
            case (R.id.en_btn):
                msg.setText("Hello world!");
                exitBtn.setText("Exit");
                break;
        }
    }

    public void exitApp(View view)
    {
        finish();
        System.exit(0);
    }
}