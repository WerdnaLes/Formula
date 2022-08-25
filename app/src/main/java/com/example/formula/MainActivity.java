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
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    static SortedMap<Integer, Float> settings = new TreeMap<Integer, Float>() {
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
    private TextView resultView, stackView;
    String stackSaved, resultSaved;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addButton = findViewById(R.id.add_btn);
        Button resetButton = findViewById(R.id.reset_btn);
        Button removeButton = findViewById(R.id.remove_btn);
        resultView = findViewById(R.id.result);
        stackView = findViewById(R.id.stack);
        if (savedInstanceState != null) {
            stackSaved=savedInstanceState.getString("stackSaved");
            resultSaved = savedInstanceState.getString("resultSaved");

            stackView.setText(stackSaved);
            resultView.setText(resultSaved);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addValue();
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
        outState.putString("resultSaved", resultSaved);
        outState.putString("stackSaved",stackSaved);
    }

    // Reworked adding code
    private void addValue() {
        EditText text = findViewById(R.id.edit_view);
        String temp;
        try {
            int id = 1;
            temp = text.getText().toString();
            float inputValue = Float.parseFloat(temp);
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
            stackSaved = settings.toString();
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
                int text = i + 1;
                float temp = 1 / settings.get(text);
                tempResult = tempResult + temp;
            }
            equation = 1 / tempResult;
            result = equation.toString();
        }
        resultSaved = result;
        resultView.setText(result);
    }

    private void onRemoveValue() {
        try {
            int last = settings.lastKey();
            settings.remove(last);
            stackView.setText(settings.toString());
            stackSaved = settings.toString();
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
}