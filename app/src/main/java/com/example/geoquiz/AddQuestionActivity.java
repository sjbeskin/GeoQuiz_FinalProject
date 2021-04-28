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
    public static final String KEY_EXTRA = "com.example.geoquiz.ADD_QUESTION_KEY";

    private EditText mAddQuestionTitleField;
    private String mQuestionTitle;
    private Button mCompleteButton;
    private boolean mQuestionTrue;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, AddQuestionActivity.class);
        return intent;
    };

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

        final Spinner trueFalseSpinner = (Spinner) findViewById(R.id.add_question_true_false_spinner);
        trueFalseSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.add_question_true_false_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trueFalseSpinner.setAdapter(adapter);

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

        mCompleteButton = (Button) findViewById(R.id.add_question_complete_button);
        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuestionTitle != null && !mQuestionTitle.isEmpty()) {
                    mQuestionTrue = trueFalseSpinner.getSelectedItem().toString().equals("True") ? true : false;
                    setQuestionVariables(mQuestionTitle, mQuestionTrue);
                    finish();

                } else {
                    Toast errorMessage = Toast.makeText(AddQuestionActivity.this, R.string.add_question_title_missing_toast, Toast.LENGTH_SHORT);
                    errorMessage.setGravity(Gravity.CENTER, 0, 0);
                    errorMessage.show();
                }
            }
        });
    }

    private void setQuestionVariables(String title, boolean isTrue) {
        Intent data = new Intent();
        data.putExtra(ADD_QUESTION_TITLE, title);
        data.putExtra(ADD_QUESTION_TRUE_FALSE, isTrue);
        setResult(RESULT_OK, data);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}
