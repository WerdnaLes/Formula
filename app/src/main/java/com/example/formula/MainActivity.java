package com.example.formula;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private final Float[] vals = new Float[6];
    static TreeMap<String, Float> settings = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Reworked adding code
    public void addValue(View view) {
        EditText text = findViewById(R.id.edit_view);
        TextView stackView = findViewById(R.id.stack);
        String temp;
        try {
            for (int i = 0; i < vals.length; i++) {
                if (vals[i] == null) {
                    temp = text.getText().toString();
                    String temp2 = String.format("R%d", i + 1);
                    vals[i] = Float.parseFloat(temp);
                    settings.put(temp2, vals[i]);
                    break;
                }
            }
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
    public void calcValue(int mapSize) {
        TextView resultView = findViewById(R.id.result);
        String result;
        if (mapSize == 1) {
            result = vals[0].toString();
        } else {
            float tempResult = 0;
            Float equation;
            for (int i = 0; i < mapSize; i++) {
                String text = String.format("R%d", i + 1);
                float temp = 1 / settings.get(text);
                tempResult = tempResult + temp;
            }
            equation = 1 / tempResult;
            result = equation.toString();
        }
        resultView.setText(result);
    }


    public void onReset(View view) {
        TextView resultView = findViewById(R.id.result);
        TextView stack = findViewById(R.id.stack);
        resultView.setText("");
        stack.setText("");
        settings.clear();
        Arrays.fill(vals, null);
    }
}