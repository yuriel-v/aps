package com.example.aps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static boolean isChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addDynamicText();
    }

    private void addDynamicText() {
        EditText fahrenheit = findViewById(R.id.fahrenheit);
        EditText celsius = findViewById(R.id.celsius);
        EditText kelvin = findViewById(R.id.kelvin);

        fahrenheit.addTextChangedListener(calcTemperature(
            TemperatureType.FAHRENHEIT,
            celsius,
            kelvin
        ));
        celsius.addTextChangedListener(calcTemperature(
            TemperatureType.CELSIUS,
            fahrenheit,
            kelvin
        ));
        kelvin.addTextChangedListener(calcTemperature(
            TemperatureType.KELVIN,
            celsius,
            fahrenheit
        ));
    }

    public void exitApp(View view) {
        finish();
        System.exit(0);
    }

    private TextWatcher calcTemperature(TemperatureType temperature,EditText... field) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0 && !isChanged)
                {
                    double value = Double.parseDouble(charSequence.toString());
                    isChanged = true;
                    field[0].setText(String.format("%.2f", Temperature.calcTemperature(temperature,value)[0]));
                    field[1].setText(String.format("%.2f", Temperature.calcTemperature(temperature,value)[1]));
                    isChanged = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        };
    }
}