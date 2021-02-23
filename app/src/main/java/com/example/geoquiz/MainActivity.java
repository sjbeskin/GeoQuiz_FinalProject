package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mBackButton;
    private TextView mTextNextButton;
    private TextView mQuestionTextView;
    private int mNumCorrect = 0;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private ArrayList<Integer> mQuestionsAnswered = new ArrayList<Integer>();

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(bundle) called");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionsAnswered.add(mCurrentIndex);
                checkAnswer(true);
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
               mQuestionsAnswered.add(mCurrentIndex);
               checkAnswer(false);
               mTrueButton.setEnabled(false);
               mFalseButton.setEnabled(false);
           }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                if(mQuestionsAnswered.contains(mCurrentIndex)) {
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                } else {
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                }
                updateQuestion();
            }
        });

        mBackButton = (ImageButton) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               mCurrentIndex = (mCurrentIndex - 1);
               if(mCurrentIndex < 0){
                   mCurrentIndex = mQuestionBank.length - 1;
               }
               if(mQuestionsAnswered.contains(mCurrentIndex)) {
                   mTrueButton.setEnabled(false);
                   mFalseButton.setEnabled(false);
               } else {
                   mTrueButton.setEnabled(true);
                   mFalseButton.setEnabled(true);
               }
               updateQuestion();
            }
        });

        mTextNextButton = (TextView) findViewById(R.id.question_text_view);
        mTextNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            mNumCorrect++;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast myToast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        myToast.setGravity(Gravity.TOP, 0, 0);
        myToast.show();

        if(mQuestionsAnswered.size() == mQuestionBank.length){
            int score = (int) Math.round((((mNumCorrect + 0.0) / mQuestionBank.length) * 100));
            String scoreText = "Score: " + score + "%";
            Toast scoreToast = Toast.makeText( this, scoreText, Toast.LENGTH_LONG);
            scoreToast.setGravity(Gravity.CENTER, 0, -250);
            scoreToast.show();
        }
    }
}
