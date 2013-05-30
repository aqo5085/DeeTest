package com.example.remake;


import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Main extends Activity {
	protected String clientKey;

	  /**
	   * Parse app application ID
	   */
	  protected String applicationId;

	  /**
	   * Twitter app consumer key
	   */
	  protected String twitterConsumerKey;

	  /**
	   * Twitter app consumer secret
	   */
	  protected String twitterConsumerSecret;
	  {
		  
	  }
	  
	  public interface LoginListener {
		    /**
		     * Fired if new user created
		     *
		     * @param type One of "twitter", "facebook", or "native"
		     * @param user The user object for this particular user
		     */
		    public void onSignup(String type, ParseUser user);

		    /**
		     * Fired if existing user signed in
		     *
		     * @param type One of "twitter", "facebook", or "native"
		     * @param user The user object for this particular user
		     */
		    public void onSignin(String type, ParseUser user);

		    /**
		     * Fired if signin/signup process errored in any way
		     *
		     * @param type One of "twitter", "facebook", or "native"
		     * @param err  Error object
		     */
		    public void onError(String type, ParseException err);
		  }

		  private LoginListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		 //Reference to my keys
        Parse.initialize(this, "deXjcKtmEwP17jEhTJ8mjUIBYyEnX6MzFQehYCzo", "6M1Jsar1DLpyiG8nJK8iOMIBIhtCbbePInvZ0Y4d"); 
        ParseTwitterUtils.initialize("T28xerTkCMzn2CH94DnJg", "SZQuvRZ5S2jvZFnEUwod4Ij0cX5NW5h06eDwMWQlYQ");
        //ParseFacebookUtils.initialize("501257739941090");

        //reference to button 
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.login)).getBackground().setColorFilter(Color.parseColor("#50B52D"), Mode.SRC_ATOP);
        ((Button) findViewById(R.id.facebook)).getBackground().setColorFilter(Color.parseColor("#005EFF"), Mode.SRC_IN);
        ((Button) findViewById(R.id.twitter)).getBackground().setColorFilter(Color.parseColor("#0095FF"), Mode.SRC_ATOP);
	}

	 public void setListener(com.example.remake.Main.LoginListener listener) {
	        this.listener = listener;
	      } 
	  // got this from parse web site
	    public void twitterCallback(View v) {
	        ParseTwitterUtils.logIn(this, new LogInCallback() {
	          @Override
	          public void done(ParseUser user, ParseException err) {
	            if (user == null) {
	              listener.onError("twitter", err);
	            } else if (user.isNew()) {
	              listener.onSignup("twitter", user);
	            } else {
	              listener.onSignin("twitter", user);
	            }
	          }
	        });
	      }

	      public void facebookCallback(View v) {
	        ParseTwitterUtils.logIn(this, new LogInCallback() {
	          @Override
	          public void done(ParseUser user, ParseException err) {
	            if (user == null) {
	              listener.onError("facebook", err);
	            } else if (user.isNew()) {
	              listener.onSignup("facebook", user);
	            } else {
	              listener.onSignin("facebook", user);
	            }
	          }
	        });
	      }
	      private String getLoginUserName() {
	    	    return ((EditText) findViewById(R.id.username)).getText().toString();
	    	  }

	    	  private String getLoginPwd() {
	    	    return ((EditText) findViewById(R.id.pwd)).getText().toString();
	    	  }

	    	  public void loginCallback(View v) {
	    	    final ProgressDialog dialog = ProgressDialog.show(this, "",
	    	        "Logging in ...", true);
	    	    ParseUser.logInInBackground(getLoginUserName(), getLoginPwd(), new LogInCallback() {
	    	      public void done(ParseUser user, ParseException e) {
	    	        dialog.dismiss();
	    	        if (user != null) {
	    	          listener.onSignin("native", user);
	    	        } else {
	    	          listener.onError("native", e);
	    	        }
	    	      }
	    	    });
	    	  }
	    	
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
