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
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements StackListFragment.ListChanged {

    private TextView resultView;
    private String savedResult;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Присвоєння змінних
        Button addButton = findViewById(R.id.add_btn);
        Button resetButton = findViewById(R.id.reset_btn);
        Button removeButton = findViewById(R.id.remove_btn);
        resultView = findViewById(R.id.result);
        // При зміні конфігурації
        if (savedInstanceState != null) {
            savedResult = savedInstanceState.getString("savedResult");
            resultView.setText(savedResult);
        }

        //Слухачі кнопок
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
        outState.putString("savedResult", savedResult);
    }

    @Override
    public void onListChanged() {
        onSizeChanged();
        calcValue(Database.stackValues.size());
    }

    // Reworked adding code
    private void addValue() {
        EditText text = findViewById(R.id.edit_view);
        String temp;
        try {
            int id = 1;
            float inputValue;
            temp = text.getText().toString();
            inputValue = Float.parseFloat(temp);
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
            result = Objects.requireNonNull(Database.stackValues.get(1)).toString();
        } else {
            float tempResult = 0;
            float equation;
            for (int i = 0; i < mapSize; i++) {
                int id = i + 1;
                float temp = 0;
                if (Database.stackValues.get(id) != null) {
                    temp = 1 / Database.stackValues.get(id);
                }
                tempResult = tempResult + temp;
            }
            equation = 1 / tempResult;
            result = Float.toString(equation);
        }
        savedResult = result;
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

    private void onSizeChanged() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        StackListFragment stackListFragment = new StackListFragment();
        ft.replace(R.id.frameLayout_fl, stackListFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}