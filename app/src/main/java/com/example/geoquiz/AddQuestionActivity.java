package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddQuestionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String ADD_QUESTION_TITLE = "com.example.geoquiz.add_question_title";
    public static final String ADD_QUESTION_TRUE_FALSE = "com.example.geoquiz.add_question_true_false";

    private EditText mAddQuestionTitleField;
    private String mQuestionTitle;
    private Button mCompleteButton;
    private boolean mQuestionTrue;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, AddQuestionActivity.class);
        return intent;
    };

    // Getters
    public static String getNewQuestionTitle(Intent result) {
        return result.getStringExtra(ADD_QUESTION_TITLE);
    }

    public static boolean getNewQuestionTrueFalse(Intent result) {
        return result.getBooleanExtra(ADD_QUESTION_TRUE_FALSE, true);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(ADD_QUESTION_TRUE_FALSE, mQuestionTrue);
        savedInstanceState.putString(ADD_QUESTION_TITLE, mQuestionTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        // Spinner that presents the user with the option of making their question true or false
        // ArrayAdapter is used to take in and interpret that information
        final Spinner trueFalseSpinner = (Spinner) findViewById(R.id.add_question_true_false_spinner);
        trueFalseSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.add_question_true_false_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trueFalseSpinner.setAdapter(adapter);

        // Takes the String of text the user inputs into the EditText and sets mQuestionTitle equal to it
        mAddQuestionTitleField = (EditText) findViewById(R.id.add_question_title);
        mAddQuestionTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mQuestionTitle = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Complete Button - when pressed, the question information is stored in an intent to be sent back to MainActivity and the activity is closed
        mCompleteButton = (Button) findViewById(R.id.add_question_complete_button);
        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuestionTitle != null && !mQuestionTitle.isEmpty()) {
                    mQuestionTrue = trueFalseSpinner.getSelectedItem().toString().equals("True") ? true : false;
                    setQuestionVariables(mQuestionTitle, mQuestionTrue);
                    finish();

                } else {
                    // Informs the user when they try to create a question without inputting a title
                    Toast errorMessage = Toast.makeText(AddQuestionActivity.this, R.string.add_question_title_missing_toast, Toast.LENGTH_SHORT);
                    errorMessage.setGravity(Gravity.CENTER, 0, 0);
                    errorMessage.show();
                }
            }
        });
    }

    // Creates the intent that will send the data back to MainActivity
    private void setQuestionVariables(String title, boolean isTrue) {
        Intent data = new Intent();
        data.putExtra(ADD_QUESTION_TITLE, title);
        data.putExtra(ADD_QUESTION_TRUE_FALSE, isTrue);
        setResult(RESULT_OK, data);
    }

    // These are both required so AdapterView can take the information from the spinner
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}
