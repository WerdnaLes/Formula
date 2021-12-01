package com.example.formula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private Float val1, val2, val3, val4, val5, val6;
    static TreeMap<String, Float> settings = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addValue(View view) {
        EditText text = findViewById(R.id.edit_view);
        TextView stackView = findViewById(R.id.stack);
        String temp;
        try {
            if (val1 == null) {
                temp = text.getText().toString();
                val1 = Float.parseFloat(temp);
                settings.put("R1", val1);
            } else if (val2 == null) {
                temp = text.getText().toString();
                val2 = Float.parseFloat(temp);
                settings.put("R2", val2);
            } else if (val3 == null) {
                temp = text.getText().toString();
                val3 = Float.parseFloat(temp);
                settings.put("R3", val3);
            } else if (val4 == null) {
                temp = text.getText().toString();
                val4 = Float.parseFloat(temp);
                settings.put("R4", val4);
            } else if (val5 == null) {
                temp = text.getText().toString();
                val5 = Float.parseFloat(temp);
                settings.put("R5", val5);
            } else if (val6 == null) {
                temp = text.getText().toString();
                val6 = Float.parseFloat(temp);
                settings.put("R6", val6);
            }
            stackView.setText(settings.toString());
            calcValue();
        } catch (NumberFormatException e) {
            CharSequence err = "Невірний формат";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, err, duration);
            toast.show();
        }
        text.setText("");
    }

    public void calcValue() {
        TextView resultView = findViewById(R.id.result);
        Float equation;
        String result;
        if (settings.size() == 1) {
            result = val1.toString();
            resultView.setText(result);
        } else if (settings.size() == 2) {
            equation = 1 / ((1 / val1) + (1 / val2));
            result = equation.toString();
            resultView.setText(result);
        } else if (settings.size() == 3) {
            equation = 1 / ((1 / val1) + (1 / val2) + (1 / val3));
            result = equation.toString();
            resultView.setText(result);
        } else if (settings.size() == 4) {
            equation = 1 / ((1 / val1) + (1 / val2) + (1 / val3) + (1 / val4));
            result = equation.toString();
            resultView.setText(result);
        } else if (settings.size() == 5) {
            equation = 1 / ((1 / val1) + (1 / val2) + (1 / val3) + (1 / val4) + (1 / val5));
            result = equation.toString();
            resultView.setText(result);
        } else if (settings.size() == 6) {
            equation = 1 / ((1 / val1) + (1 / val2) + (1 / val3) + (1 / val4) + (1 / val5) + (1 / val6));
            result = equation.toString();
            resultView.setText(result);
        }
    }

    public void onReset(View view) {
        TextView resultView = findViewById(R.id.result);
        TextView stack = findViewById(R.id.stack);
        resultView.setText("");
        stack.setText("");
        settings.clear();
        val1 = val2 = val3 = val4 = val5 = val6 = null;
    }
}