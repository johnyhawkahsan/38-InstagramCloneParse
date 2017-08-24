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
import android.view.View;
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


public class MainActivity extends AppCompatActivity {

    EditText usernameField;
    EditText passwordField;
    Button signUpButton;
    Button logInButton;
    ImageView imageView;
    ConstraintLayout constraintLayout;



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


  }

}



























