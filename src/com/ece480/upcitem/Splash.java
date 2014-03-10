package com.ece480.upcitem;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends Activity {// public means accessible by other//class
										// holds methods//extend another class
	// class Activity that is extend is super class
	// MainActivity is subclass
	MediaPlayer music;

	@Override
	// means using someone else's method
	protected void onCreate(Bundle savedInstanceState) {// passing information
		super.onCreate(savedInstanceState);// referring to the super class
											// passing bundle
		setContentView(R.layout.splash);// sets layout or view

		Thread logoTimer = new Thread() {// to multitask
			public void run() {
				try {
					sleep(1000);// 1000=one sec
					// transition from splash to main men
					overridePendingTransition(R.anim.activityfadein,
							R.anim.activityfadeout);
					Intent menuIntent = new Intent("com.ece480.upcitem.Meanu");
					startActivity(menuIntent);
				}// end try

				catch (InterruptedException e) {
					e.printStackTrace();
				}// end catch

				finally {// closes any other activities
					finish();// finish current activity
				}// end finally
			}// end try
		};// end thread logotimer
		logoTimer.start();
	}// end oncreate

	@Override
	protected void onPause() {
		super.onPause();

	}

}
