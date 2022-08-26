package com.example.formula;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    static SortedMap<Integer, Float> settings;
    private TextView resultView, stackView;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Перевизначення toString() класу TreeMap<>
        settings = new TreeMap<Integer, Float>() {
            @NonNull
            @Override
            public String toString() {
                Iterator<Entry<Integer, Float>> i = entrySet().iterator();
                if (!i.hasNext())
                    return "{}";

                StringBuilder sb = new StringBuilder();
                while (i.hasNext()) {
                    Entry<Integer, Float> e = i.next();
                    Integer key = e.getKey();
                    Float value = e.getValue();
                    sb.append('R');
                    sb.append(key);
                    sb.append('=');
                    sb.append(value);
                    sb.append('\n');
                }
                return sb.toString();
            }
        };
        // Присвоєння змінних
        Button addButton = findViewById(R.id.add_btn);
        Button resetButton = findViewById(R.id.reset_btn);
        Button removeButton = findViewById(R.id.remove_btn);
        resultView = findViewById(R.id.result);
        stackView = findViewById(R.id.stack);
        // При зміні конфігурації введені раніше значення вводяться повторно
        if (savedInstanceState != null) {
            float[] savedInstanceStateFloatArray = savedInstanceState.getFloatArray("floatArray");

            for (float v : savedInstanceStateFloatArray) {
                addValue(v);
            }
        }

        //Слухачі кнопок
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addValue(0);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReset();
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemoveValue();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloatArray("floatArray", savedValues());
    }


    // Reworked adding code
    private void addValue(float value) {
        EditText text = findViewById(R.id.edit_view);
        String temp;
        try {
            int id = 1;
            float inputValue;
            if (value == 0) {
                temp = text.getText().toString();
                inputValue = Float.parseFloat(temp);
            } else {
                inputValue = value;
            }
            if (settings.size() != 0) {
                while (settings.containsKey(id)) {
                    id++;
                    if (!settings.containsKey(id)) {
                        break;
                    }
                }
            }
            settings.put(id, inputValue);

            stackView.setText(settings.toString());
            calcValue(settings.size());
        } catch (NumberFormatException e) {
            CharSequence err = "Невірний формат";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, err, duration);
            toast.show();
        }
        text.setText("");
    }

    // Reworked calc
    private void calcValue(int mapSize) {
        String result;
        if (mapSize == 1) {
            result = settings.get(1).toString();
        } else {
            float tempResult = 0;
            Float equation;
            for (int i = 0; i < mapSize; i++) {
                int id = i + 1;
                float temp = 1 / settings.get(id);
                tempResult = tempResult + temp;
            }
            equation = 1 / tempResult;
            result = equation.toString();
        }
        resultView.setText(result);
    }

    private void onRemoveValue() {
        try {
            int last = settings.lastKey();
            settings.remove(last);
            stackView.setText(settings.toString());
            calcValue(settings.size());
        } catch (NoSuchElementException e) {
            Toast.makeText(this, "Не введено жодного значення", Toast.LENGTH_SHORT).show();
        }
    }


    private void onReset() {
        resultView.setText("");
        stackView.setText("");
        settings.clear();
    }

    // Зберегти значення перед знищенням активності
    private float[] savedValues() {
        Collection<Float> values = settings.values();
        float[] valuesStorage = new float[settings.size()];
        int i = 0;
        for (float a : values) {
            valuesStorage[i] = a;
            i++;
        }
        return valuesStorage;
    }
}