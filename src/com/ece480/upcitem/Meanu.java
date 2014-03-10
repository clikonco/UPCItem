package com.ece480.upcitem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Meanu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		// Button 1 for New Scanning
		Button tut1 = (Button) findViewById(R.id.newscan);
		tut1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(
						"com.ece480.upcitem.CameraScanActivity"));// got to
																	// Camera
																	// Scan
																	// Activity

			}
		});
		// Button 2 for Old Scans/History
		Button tut2 = (Button) findViewById(R.id.oldscan);
		tut2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent("com.ece480.upcitem.Result"));// go to
																		// Results
																		// activity

			}
		});

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

}
