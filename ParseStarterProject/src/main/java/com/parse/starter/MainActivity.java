/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements
        View.OnKeyListener,
        View.OnClickListener{

    EditText usernameField;
    EditText passwordField;
    Button signUpButton;
    Button logInButton;
    ImageView imageView;
    ConstraintLayout constraintLayout;

    //Start UserListActivity.class which shows signed up users to the new user
    public void showUserList(){

        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);

    }



    //Define onClick for sign up button first.
    public void signup(View view){

        if (usernameField.getText().toString() == "" || passwordField.getText().toString() == ""){

            Toast.makeText(this, "A username and password is required.", Toast.LENGTH_SHORT).show();

        } else {

            ParseUser user = new ParseUser();

            user.setUsername(usernameField.getText().toString());
            user.setPassword(passwordField.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null){

                        Log.i("Signup", "Successful");
                        showUserList();

                    } else {

                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
    }



    //Login method
    public void login(View view){

        if (usernameField.getText().toString() == "" || passwordField.getText().toString() == ""){

            Toast.makeText(this, "A username and password is required.", Toast.LENGTH_SHORT).show();

        } else {

            ParseUser.logInInBackground(usernameField.getText().toString(), passwordField.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (user != null){

                        Log.i("Login", "Login Successful");
                        showUserList();

                    } else {

                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }
    }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());

      usernameField = (EditText) findViewById(R.id.username);
      passwordField = (EditText) findViewById(R.id.password);
      signUpButton = (Button) findViewById(R.id.signUpButton);
      logInButton = (Button) findViewById(R.id.loginButton);
      imageView = (ImageView) findViewById(R.id.imageView);
      constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

      //This below method is implemented in onClick(View view) method to hide keyboard when clicked on these.
      imageView.setOnClickListener(this);
      constraintLayout.setOnClickListener(this);



      //If user is already logged in, launch showUSers method
      if (ParseUser.getCurrentUser() != null){
          showUserList();
      }

  }









    //TODO: Method to use enter button to automatically launch a method, either login or sign up
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        //If enter key is pressed, launch the login method
        //TODO: Note: keyEvent launches method twice, once when key is pressed, and again when finger is lifted up, so we also need to check for KeyEvent.Action_DOWN
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

            login(v);

        }

        return false;
    }


    //TODO: Method to hide the keyboard when someone clicks outside usernameField and passwordField, those items include : imageView and constraintLayout
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imageView || view.getId() == R.id.constraintLayout){

            Log.i("ClickedItemsID", String.valueOf(view.getId()));//Just for testing
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);//Gets keyboard for us
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);//Hide soft input means the software form of keyboard,

        }

    }
}



























