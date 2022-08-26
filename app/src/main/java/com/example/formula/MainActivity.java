package com.example.formula;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.Collection;
import java.util.NoSuchElementException;

public class MainActivity extends AppCompatActivity
implements StackListFragment.ListChanged{

    private TextView resultView;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Присвоєння змінних
        Button addButton = findViewById(R.id.add_btn);
        Button resetButton = findViewById(R.id.reset_btn);
        Button removeButton = findViewById(R.id.remove_btn);
        resultView = findViewById(R.id.result);
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

    @Override
    public void onListChanged() {
        onSizeChanged();
        calcValue(Database.stackValues.size());
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
            if (Database.stackValues.size() != 0) {
                while (Database.stackValues.containsKey(id)) {
                    id++;
                    if (!Database.stackValues.containsKey(id)) {
                        break;
                    }
                }
            }
            Database.stackValues.put(id, inputValue);
            onSizeChanged();
            calcValue(Database.stackValues.size());
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
            result = Database.stackValues.get(1).toString();
        } else {
            float tempResult = 0;
            Float equation;
            for (int i = 0; i < mapSize; i++) {
                int id = i + 1;
                float temp = 0;
                if (Database.stackValues.get(id) != null) {
                    temp = 1 / Database.stackValues.get(id);
                }
                tempResult = tempResult + temp;
            }
            equation = 1 / tempResult;
            result = equation.toString();
        }
        resultView.setText(result);
    }

    private void onRemoveValue() {
        try {
            int last = Database.stackValues.lastKey();
            Database.stackValues.remove(last);
            onSizeChanged();
            calcValue(Database.stackValues.size());
        } catch (NoSuchElementException e) {
            Toast.makeText(this, "Не введено жодного значення", Toast.LENGTH_SHORT).show();
        }
    }


    private void onReset() {
        resultView.setText("");
        Database.stackValues.clear();
        Database.backupValues.clear();
        onSizeChanged();
    }
    // Зберегти значення перед знищенням активності

    private float[] savedValues() {
        Collection<Float> values = Database.stackValues.values();
        float[] valuesStorage = new float[Database.stackValues.size()];
        int i = 0;
        for (float a : values) {
            valuesStorage[i] = a;
            i++;
        }
        return valuesStorage;
    }

    private void onSizeChanged() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        StackListFragment stackListFragment = new StackListFragment();
        ft.replace(R.id.frameLayout_fl, stackListFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}