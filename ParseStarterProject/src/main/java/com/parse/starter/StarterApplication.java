/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("24830636e455b3b10b507148550652d682c95878")
            .clientKey("10b0ccaa27c4987c101dfdef17f9cc91e2ce5ae7")
            .server("http://ec2-18-220-201-230.us-east-2.compute.amazonaws.com:80/parse/")
            .build()
    );

/*
    //Just to test if Parse Installation is working correctly, so we don't need it anymore
    ParseObject object = new ParseObject("ExampleObject");
    object.put("myNumber", "123");
    object.put("myString", "rob");
    object.put("myNumber", "456");
    object.put("myString", "Ahsan");

    object.saveInBackground(new SaveCallback () {
      @Override
      public void done(ParseException ex) {
        if (ex == null) {
          Log.i("Parse Result", "Successful!");
        } else {
          Log.i("Parse Result", "Failed" + ex.toString());
        }
      }
    });
*/


     //TODO: All my test code is moved from MainActivity.java onCreate method to here

      /*
    //Save data in Parse Server
    ParseObject score = new ParseObject("Score");
    score.put("username", "Ahsan");
    score.put("score", 86);

    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {

        if (e == null){

          Log.i("SaveInBackground", "Successful");

        } else {

          Log.i("SaveInBackground", "Failed. Error: " + e.toString());

        }

      }
    });
*/

/*
    //Retrieve Data from parse
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

    query.getInBackground("XNwMTiapPX", new GetCallback<ParseObject>() {
        @Override
        public void done(ParseObject object, ParseException e) {

            if (e == null && object != null){

                //If we want to update the score data, we can simply use this object to update
                object.put("score", 200);
                object.saveInBackground();//This time no call back

                Log.i("username", object.getString("username"));
                Log.i("score", String.valueOf(object.getInt("score")));

            } else {

                Log.i("ObjectRetrieveError", "Failed. Error: " + e.toString());

            }

        }
    });
*/


/*
    //Advance Parse Queries-Where clause
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");//Score class

      //Add a search criteria
      query.whereEqualTo("username", "ralphie");
      query.setLimit(1);//Return only one result

      query.findInBackground(new FindCallback<ParseObject>() {
          @Override
          public void done(List<ParseObject> objects, ParseException e) {

              //If there is no exception error, means everything worked
              if (e == null){

                Log.i("findInBackground", "Retrieved " + objects.size() + " objects");

                if (objects.size() > 0){

                    //We created ParseObject called object from each item in objects
                    for (ParseObject object : objects){

                        Log.i("findInBackgroundResult", object.toString());
                        Log.i("findInBackgroundStrings", object.getString("username"));
                        Log.i("findInBackgroundStrings", String.valueOf(object.getInt("score")));

                    }

                }
              }
          }
      });
*/


/*
      //Search when score is greater than 200 and add extra 50 points to it
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");//Score class
      query.whereGreaterThan("score", 200);

      query.findInBackground(new FindCallback<ParseObject>() {
          @Override
          public void done(List<ParseObject> objects, ParseException e) {

              if (e == null && objects != null){

                  for (ParseObject object: objects){

                      object.put("score", object.getInt("score") + 50);//Get integer value in score and add 50
                      object.saveInBackground();

                  }

              }

          }
      });
*/

/*
      //Create a new user
      ParseUser user = new ParseUser();

      user.setUsername("ahsan");
      user.setPassword("123");

      user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {

              if (e == null){

                  Log.i("Sign Up", "Successful");

              } else {

                  Log.i("Sign Up", "Failed. Error: " + e.toString());

              }

          }
      });
*/

/*
      ParseUser.logInInBackground("ahsan", "123", new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {

              if (e == null){

                  Log.i("Log In", "Successful");

              } else {

                  Log.i("Log In", "Failed. Error: " + e.toString());

              }

          }
      });
*/


/*
      //Check if user is logged in
      if(ParseUser.getCurrentUser() != null){

          Log.i("currentUser", "User Logged in " + ParseUser.getCurrentUser().getUsername());

      } else {

          Log.i("currentUser", "User not logged in");

      }
*/


/*
      //Log out the current user
      ParseUser.logOut();
*/



    //If you want to create automatic user
    //ParseUser.enableAutomaticUser();

    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    defaultACL.setPublicWriteAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);

  }
}
