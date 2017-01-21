package com.example.dariuszn.lab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText nameEdit;
    private EditText surnameEdit;
    private EditText numberOfRatesEdit;

    private TextView averageLabel;
    private TextView averageValue;

    private Button ratesButton;

    private boolean isCorrectNameEdit;
    private boolean isCorrectSurnameEdit;
    private boolean isCorrectNumberOfRates;

    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toast =  Toast.makeText(this, "", Toast.LENGTH_SHORT);

        initAllComponents(savedInstanceState);
        addTextWatcherToEditViews();
        //handleLoosedFocusOfEditTexts();
        addListenerToRatesButton();
        putCorrectValuesIntoFields();
        setInitialVisibilityOfRateButton();
        initFinishButton();
    }



    private void initAllComponents(Bundle savedInstanceState) {
        nameEdit = (EditText) findViewById(R.id.nameView);
        surnameEdit = (EditText) findViewById(R.id.surnameView);
        numberOfRatesEdit = (EditText) findViewById(R.id.numberOfRatesView);
        ratesButton = (Button) findViewById(R.id.rateButton);
        averageLabel = (TextView) findViewById(R.id.averageLabel);
        averageValue = (TextView) findViewById(R.id.averageValue);

        if (savedInstanceState != null) {
            Student.firstName = savedInstanceState.getString("Name");
            Student.lastName = savedInstanceState.getString("Surname");
            Student.hasTwoRate = savedInstanceState.getBoolean("isHasTwo");

            try {
                Student.numberOfRates = Integer.parseInt(savedInstanceState.getString("NumberOfRates"));
            }
            catch (NumberFormatException e) {
                Log.v("Komunikat: ", "Brak liczby ocen w savedInstanceState");
            }


            try {
                Student.averageRates = savedInstanceState.getFloat("averageRates");
            }
            catch (NullPointerException e) {
                Log.v("Komunikat: ", "Brak średniej w savedInstanceState");
            }

            setVisibilityOfRateButton(savedInstanceState.getInt("visibilityOfButton"));

            isCorrectNameEdit = savedInstanceState.getBoolean("isCorrectName");
            isCorrectSurnameEdit = savedInstanceState.getBoolean("isCorrectSurname");
            isCorrectNumberOfRates = savedInstanceState.getBoolean("isCorrectNumberOfRates");
        }
        else {
            isCorrectNameEdit = false;
            isCorrectSurnameEdit = false;
            isCorrectNumberOfRates = false;
        }


    }

    private void addListenerToRatesButton() {
        ratesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCorrectOfAllFields()) {
                    setStudentData();
                    goToRatesActivity();
                    finish();
                }
            }
        });
    }

    private void setStudentData() {
        String firstName = nameEdit.getText().toString();
        String lastName = surnameEdit.getText().toString();
        int numberOfR = Integer.parseInt(numberOfRatesEdit.getText().toString());

        Student.setNamesAndNumberOfRates(firstName, lastName, numberOfR);
    }

    private void goToRatesActivity() {
        Intent ratesIntent = new Intent(this, RatesActivity.class);
        ratesIntent.putExtra("numberOfRates", numberOfRatesEdit.getText().toString());
        startActivity(ratesIntent);
    }

    private void setVisibilityOfRateButton(int visibility) {
        switch (visibility) {
            case 0:
                ratesButton.setVisibility(View.VISIBLE);
                break;
            case 4:
                ratesButton.setVisibility(View.INVISIBLE);
                break;
            case 8:
                ratesButton.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }


    private void addTextWatcherToEditViews() {
        addTextWatcherToNameEdit();
        addTextWatcherToSurnameEdit();
        addTextWatcherToNumberOfRatesEdit();
    }

    private void addTextWatcherToNameEdit() {
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = nameEdit.getText().toString();
                isCorrectNameEdit = ValidationLib.isCorrectText(text);
                showWarning(isCorrectNameEdit);
                changeVisibilityOfRateButton();
            }
        });
    }

    private void addTextWatcherToSurnameEdit() {
        surnameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = surnameEdit.getText().toString();
                isCorrectSurnameEdit = ValidationLib.isCorrectText(text);
                showWarning(isCorrectSurnameEdit);
                changeVisibilityOfRateButton();
            }
        });
    }


    private void addTextWatcherToNumberOfRatesEdit() {
        numberOfRatesEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = numberOfRatesEdit.getText().toString();
                isCorrectNumberOfRates = ValidationLib.isCorrectNumberOfRate(text);
                showWarning(isCorrectNumberOfRates);
                changeVisibilityOfRateButton();
            }
        });
    }




    private void setInitialVisibilityOfRateButton() {
        isCorrectNameEdit = ValidationLib.isCorrectText(nameEdit.getText().toString());
        isCorrectSurnameEdit = ValidationLib.isCorrectText(surnameEdit.getText().toString());
        isCorrectNumberOfRates = ValidationLib.isCorrectNumberOfRate(numberOfRatesEdit.getText().toString());
        changeVisibilityOfRateButton();
    }

    private void changeVisibilityOfRateButton() {
        if (checkCorrectOfAllFields()) {
            ratesButton.setVisibility(View.VISIBLE);
        }
        else {
            ratesButton.setVisibility(View.INVISIBLE);
        }
    }

    private boolean checkCorrectOfAllFields()
    {
        boolean isCorrect = false;

        if (isCorrectNameEdit == true && isCorrectSurnameEdit == true && isCorrectNumberOfRates == true) {
            isCorrect = true;
        }
        return isCorrect;
    }

    private void showWarning(boolean isCorrect) {
        toast.cancel();
        if (!isCorrect) {
            toast = Toast.makeText(this, "Pole posiada niepoprawną wartość", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void putCorrectValuesIntoFields() {
        if (Student.firstName != "") {
            nameEdit.setText(Student.firstName);
        }

        if (Student.lastName != "") {
            surnameEdit.setText(Student.lastName);
        }

        if (Student.numberOfRates != 0) {
            numberOfRatesEdit.setText(Integer.toString(Student.numberOfRates));
        }

        if (Student.averageRates != 0) {
            averageLabel.setVisibility(View.VISIBLE);
            averageValue.setVisibility(View.VISIBLE);
            averageValue.setText(Float.toString(Student.averageRates));
        }
        else {
            averageLabel.setVisibility(View.INVISIBLE);
            averageValue.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("Name", nameEdit.getText().toString());
        outState.putString("Surname", surnameEdit.getText().toString());
        outState.putString("NumberOfRates", numberOfRatesEdit.getText().toString());
        outState.putInt("visibilityOfButton", ratesButton.getVisibility());
        outState.putBoolean("isHasTwo", Student.hasTwoRate);

        if (averageValue.getVisibility() == View.VISIBLE) {
            outState.putFloat("averageRates", Student.averageRates);
        }



        outState.putBoolean("isCorrectName", isCorrectNameEdit);
        outState.putBoolean("isCorrectSurname", isCorrectSurnameEdit);
        outState.putBoolean("isCorrectNumberOfRates", isCorrectNumberOfRates);
    }

    private void initFinishButton() {
        if (Student.averageRates != 0) {
            Button finishButton = (Button) findViewById(R.id.finishButton);
            changeOfVisibilityOfFinishButton(finishButton);
            finishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFinishMessage();
                    Student.killAllValues();
                    closeApp();
                }
            });
        }
    }



    private void changeOfVisibilityOfFinishButton(Button button) {
        button.setVisibility(View.VISIBLE);
        if (Student.averageRates >= 3.0 && !Student.hasTwoRate) {
            button.setText("Super :)");
        }
        else {
            button.setText("Tym razem mi nie poszło.");
        }
    }

    private void showFinishMessage() {
        String message = "";
        if (Student.averageRates >= 3.0 && !Student.hasTwoRate) {
            message = "Gratulacje! Otrzymujesz zaliczenie.";
        }
        else {
            message = "Wysyłam poadnie o zaliczenie warunkowe.";
        }
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void closeApp() {
        this.finish();
        //System.exit(0);
    }
}
