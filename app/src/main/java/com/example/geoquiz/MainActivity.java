package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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
    private static final String DID_CHEAT = "cheat";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mBackButton;
    private Button mCheatButton;
    private TextView mTextNextButton;
    private TextView mQuestionTextView;
    private int mNumCorrect = 0;


//    private Question[] mQuestionBank = new Question[] {
//            new Question(R.string.question_australia, true),
//            new Question(R.string.question_oceans, true),
//            new Question(R.string.question_mideast, false),
//            new Question(R.string.question_africa, false),
//            new Question(R.string.question_americas, true),
//            new Question(R.string.question_asia, true),
//    };

    private ArrayList<Question> mQuestionBank = new ArrayList<Question>();

    private ArrayList<Integer> mQuestionsAnswered = new ArrayList<Integer>();

    private ArrayList<Integer> mQuestionsCheated = new ArrayList<Integer>();

    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(bundle) called");
        setContentView(R.layout.activity_main);

        mQuestionBank.add(new Question(R.string.question_australia, true));
        mQuestionBank.add(new Question(R.string.question_oceans, true));
        mQuestionBank.add(new Question(R.string.question_mideast, false));
        mQuestionBank.add(new Question(R.string.question_africa, false));
        mQuestionBank.add(new Question(R.string.question_americas, true));
        mQuestionBank.add(new Question(R.string.question_asia, true));

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(DID_CHEAT, false);
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
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size();
                mIsCheater = mQuestionsCheated.contains(mCurrentIndex);
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
                   mCurrentIndex = mQuestionBank.size() - 1;
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
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size();
                updateQuestion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CheatActivity
                boolean answerIsTrue = mQuestionBank.get(mCurrentIndex).isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            if (CheatActivity.wasAnswerShown(data)){
                mIsCheater = true;
                mQuestionsCheated.add(mCurrentIndex);
            }
        }
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
        savedInstanceState.putBoolean(DID_CHEAT, mIsCheater);
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
        if(mQuestionBank.get(mCurrentIndex).getNewQuestion() == null) {
            int question = mQuestionBank.get(mCurrentIndex).getTextResId();
            mQuestionTextView.setText(question);
        } else {
            String question = mQuestionBank.get(mCurrentIndex).getNewQuestion();
            mQuestionTextView.setText(question);
        }

    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank.get(mCurrentIndex).isAnswerTrue();

        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgement_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mNumCorrect++;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast myToast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        myToast.setGravity(Gravity.TOP, 0, 0);
        myToast.show();

        if(mQuestionsAnswered.size() == mQuestionBank.size()){
            int score = (int) Math.round((((mNumCorrect + 0.0) / mQuestionBank.size()) * 100));
            String scoreText = "Score: " + score + "%";
            Toast scoreToast = Toast.makeText( this, scoreText, Toast.LENGTH_LONG);
            scoreToast.setGravity(Gravity.CENTER, 0, -250);
            scoreToast.show();
        }
    }
}
