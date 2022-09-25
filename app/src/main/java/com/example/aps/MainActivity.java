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

    private void addDynamicText()
    {
        EditText fahrenheit = findViewById(R.id.fahrenheit);
        EditText celsius = findViewById(R.id.celsius);
        EditText kelvin = findViewById(R.id.kelvin);

        fahrenheit.addTextChangedListener(makeTextWatcher(
            TemperatureType.FAHRENHEIT,
            celsius,
            kelvin
        ));
        celsius.addTextChangedListener(makeTextWatcher(
            TemperatureType.CELSIUS,
            fahrenheit,
            kelvin
        ));
        kelvin.addTextChangedListener(makeTextWatcher(
            TemperatureType.KELVIN,
            celsius,
            fahrenheit
        ));
    }

    public void exitApp(View view) {
        finish();
        System.exit(0);
    }

    private TextWatcher makeTextWatcher(TemperatureType temperature, EditText field1, EditText field2)
    {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0 && !isChanged)
                {
                    try
                    {
                        double value = Double.parseDouble(charSequence.toString());
                        isChanged = true;  // else the app crashes with the keyboard permanently open!
                        field1.setText(String.format("%.2f", Temperature.calcTemperature(temperature, value)[0]));
                        field2.setText(String.format("%.2f", Temperature.calcTemperature(temperature, value)[1]));
                        isChanged = false;
                    }
                    // fix cases when only a dash remains in the type box and the app crashes
                    catch (NumberFormatException e) {}
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        };
    }
}