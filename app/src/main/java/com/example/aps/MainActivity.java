package com.example.aps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addDynamicText();
    }

    private void addDynamicText()
    {
        EditText field1 = findViewById(R.id.teste3);
        TextView one = findViewById(R.id.teste1);
        TextView two = findViewById(R.id.teste2);

        field1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0)
                {
                    Double value = Double.parseDouble(charSequence.toString());
                    one.setText(String.format("%.2f", value * 2));
                    two.setText(String.format("%.2f", value * value));
                }
                else
                {
                    one.setText("< *2 >");
                    two.setText("< ^2 >");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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