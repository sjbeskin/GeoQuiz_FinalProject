package com.example.geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private String mNewQuestion;

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public String getNewQuestion() {
        return mNewQuestion;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public Question(String newQuestion, boolean answerTrue) {
        mNewQuestion = newQuestion;
        mAnswerTrue = answerTrue;
    }
}
