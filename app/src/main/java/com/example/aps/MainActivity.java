package com.example.aps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static boolean isChanged = false;
    public PeriodicTable table;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //addDynamicText();
        addExitClickHandler();
        instancePeriodicTable();
        addTextListener();
    }

    private void instancePeriodicTable()
    {
        try
        {
            InputStream ins = getResources().openRawResource(
                    getResources().getIdentifier("elements", "raw", getPackageName())
            );
            this.table = new PeriodicTable(ins);
            ins.close();
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    private void addExitClickHandler()
    {
        Button exitBtn = findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
                System.exit(0);
            }
        });
    }

    private void addTextListener()
    {
        Spinner spinner = findViewById(R.id.dropdown);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TextView elementName = findViewById(R.id.chosen_name);
                TextView elementNumber = findViewById(R.id.chosen_number);
                TextView elementSymbol = findViewById(R.id.chosen_symbol);
                TextView elementMass = findViewById(R.id.chosen_mass);
                TextView elementBoilingKelvin = findViewById(R.id.chosen_boiling_kelvin);
                TextView elementBoilingCelsius = findViewById(R.id.chosen_boiling_celsius);
                TextView elementBoilingFahrenheit = findViewById(R.id.chosen_boiling_fahrenheit);
                TextView elementMeltingKelvin = findViewById(R.id.chosen_melting_kelvin);
                TextView elementMeltingCelsius = findViewById(R.id.chosen_melting_celsius);
                TextView elementMeltingFahrenheit = findViewById(R.id.chosen_melting_fahrenheit);

                PeriodicElement chosenElement = table.getElementByIndex((short) position);
                elementName.setText(chosenElement.name);
                elementNumber.setText(Integer.toString(chosenElement.number));
                elementSymbol.setText(chosenElement.symbol);
                elementMass.setText(chosenElement.mass);
                if (Double.isNaN(chosenElement.boilingPoint))
                {
                    elementBoilingKelvin.setText("Desconhecido");
                    elementBoilingCelsius.setText("Desconhecido");
                    elementBoilingFahrenheit.setText("Desconhecido");
                }
                else
                {
                    double[] temperatures = Temperature.calcTemperature(TemperatureType.KELVIN, chosenElement.boilingPoint);
                    elementBoilingKelvin.setText(String.format("%.1f K", chosenElement.boilingPoint));
                    elementBoilingCelsius.setText(String.format("%.1f ??C", temperatures[0]));
                    elementBoilingFahrenheit.setText(String.format("%.1f ??F", temperatures[1]));
                }
                if (Double.isNaN(chosenElement.meltingPoint))
                {
                    elementMeltingKelvin.setText("Desconhecido");
                    elementMeltingCelsius.setText("Desconhecido");
                    elementMeltingFahrenheit.setText("Desconhecido");
                }
                else
                {
                    double[] temperatures = Temperature.calcTemperature(TemperatureType.KELVIN, chosenElement.meltingPoint);
                    elementMeltingKelvin.setText(String.format("%.1f K", chosenElement.meltingPoint));
                    elementMeltingCelsius.setText(String.format("%.1f ??C", temperatures[0]));
                    elementMeltingFahrenheit.setText(String.format("%.1f ??F", temperatures[1]));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Here comes when you didn't choose anything from your spinner logic
            }

        });
    }

    /* private void addDynamicText()
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
    }*/

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