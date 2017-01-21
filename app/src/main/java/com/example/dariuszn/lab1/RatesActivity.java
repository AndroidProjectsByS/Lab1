package com.example.dariuszn.lab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class RatesActivity extends AppCompatActivity implements RatesAdapter.OnCheckedChangeListener {
    private static int[] oceny;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);

        if (savedInstanceState != null) {
            oceny = savedInstanceState.getIntArray("rates");
        }
        else {
            String numberOfRatesStr = getIntent().getStringExtra("numberOfRates");
            int numberOfRates = Integer.parseInt(numberOfRatesStr);
            oceny = new int[numberOfRates];
            inicujWstepneWartosciOcen();
        }

        RatesAdapter ratesAdapter = new RatesAdapter(this, oceny);

        ListView listView = (ListView) findViewById(R.id.listOfRates);
        Button readyRatesButton = (Button) findViewById(R.id.readyRatesButton);

        readyRatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTwoGrade();
                goToMainActivity();
                finish();
            }
        });

        listView.setAdapter(ratesAdapter);
    }

    public void goToMainActivity() {
        Intent intent = new Intent(RatesActivity.this, MainActivity.class);
        float avg = countAVG();

        Student.averageRates = avg;

        startActivity(intent);
    }

    private void isTwoGrade() {
        for (int ocena : oceny) {
            if (ocena == 2) {
                Student.hasTwoRate = true;
            }
        }
    }

    private float countAVG() {
        float sum = 0;
        for (int ocena : oceny) {
            sum += ocena;
        }
        float numberOfRates = oceny.length;
        float avg = sum / numberOfRates;

        return avg;
    }

    private void inicujWstepneWartosciOcen() {
        for (int i = 0; i < oceny.length; i++) {
            oceny[i] = 5;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("rates", oceny);
    }

    @Override
    public void onCheckedChange(int position, int value) {
        oceny[position] = value;
    }
}
