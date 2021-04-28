GEOQUIZ is a simple app that presents the user with true/false questions and allows them to answer. 
The function of my addition to the app is that the user is now able to add their own questions. 
By clicking the 'Add Question' button, users will be presented with a screen that allows them to create and add their own questions to the list.
Additionally, the user will be able to delete questions from the list, giving them the opportunity to make a fully customizable set of true/false questions. <br />


Core Concepts Used <br />
1. **Intents** <br />MainActivity uses an Intent to call a separate activity, AddQuestionActivity, that takes in and returns user data.
2. **Spinners** <br />AddQuestionActivity uses a spinner to allow the user to select between "True" and "False" when creating their question.
3. **Toasts** <br />MainActivity and AddQuestionActivity use toasts to give the user necessary information when trying to do certain things.
4. **AdapterView** <br />Very helpful in taking data from the spinner and making use of it.
5. **EditText** <br />Allows the user to type out the question they want to create.
