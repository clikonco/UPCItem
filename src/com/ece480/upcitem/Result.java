package com.ece480.upcitem;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Result extends ListActivity {

	// Progress Dialog
	private ProgressDialog pDialog;
	private String PRODUCT_URL;
	// php read comments script
	private TextView tv;
	public String UPC;
	private String dynamic = "192.168.2.29";// IP ADDRESS OF DB
	// JSON IDS:
	private static final String TAG_POSTS = "posts";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ProductName = "ProductName";
	private static final String TAG_UPC = "UPC";
	private static final String TAG_id = "id";
	private static final String TAG_Amazon = "Amazon";
	private static final String TAG_Frys = "Frys";
	private static final String TAG_Ebay = "Ebay";
	private static final String TAG_Other = "Other";
	// An array of all of our comments
	private JSONArray mComments = null;
	// manages all of our comments in a list.
	private ArrayList<HashMap<String, String>> mCommentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// note that use read_comments.xml instead of our single_post.xml
		setContentView(R.layout.forum);
		tv = (TextView) findViewById(R.id.forumtitle);
		PRODUCT_URL = "http://" + dynamic + "/webservice/itemsearch.php?UPC=";

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			UPC = extras.getString("UPC");
			Log.i("Getting Info from Prev Activity:", UPC);
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		// loading the comments via AsyncTask
		new LoadComments().execute();
	}

	/**
	 * Retrieves recent post data from the server.
	 */
	public void updateJSONdata() {

		// Instantiate the arraylist to contain all the JSON data.

		mCommentList = new ArrayList<HashMap<String, String>>();
		Log.i("STATUS: ", "Before JSON PARSER");
		JSONParser jParser = new JSONParser();
		Log.i("STATUS: ", "After JSON PARSER");

		if (UPC == null) {
			UPC = "1";
		}
		Log.e("FORUM_COMMENTS_URL", PRODUCT_URL + UPC);
		JSONObject json = jParser.getJSONFromUrl(PRODUCT_URL + UPC);

		try {
			mComments = json.getJSONArray(TAG_POSTS);
			// looping through all posts according to the json object returned
			for (int i = 0; i < mComments.length(); i++) {
				JSONObject c = mComments.getJSONObject(i);

				String ProductName = c.getString(TAG_ProductName);
				String UPC = c.getString(TAG_UPC);
				String id = c.getString(TAG_id);
				String Amazon = c.getString(TAG_Amazon);
				String Frys = c.getString(TAG_Frys);
				String Ebay = c.getString(TAG_Ebay);
				String Other = c.getString(TAG_Other);

				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();

				map.put(TAG_ProductName, ProductName);
				map.put(TAG_UPC, UPC);
				map.put(TAG_id, id);
				map.put(TAG_Amazon, Amazon);
				map.put(TAG_Frys, Frys);
				map.put(TAG_Ebay, Ebay);
				map.put(TAG_Other, Other);

				// adding HashList to ArrayList
				mCommentList.add(map);

			}

		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("STATUS", "NO PRODUCTS FOUND");
		}
	}

	/**
	 * Inserts the parsed data into the listview.
	 */
	private void updateList() {
		ListAdapter adapter = new SimpleAdapter(this, mCommentList,
				R.layout.forum_single, new String[] { TAG_ProductName, TAG_UPC,
						TAG_Amazon, TAG_Frys, TAG_Ebay, TAG_Other }, new int[] {
						R.id.ProductName, R.id.UPC, R.id.Amazon, R.id.Frys,
						R.id.Ebay, R.id.Other });
		setListAdapter(adapter);

		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
	}

	public class LoadComments extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Result.this);
			pDialog.setMessage("Loading Items...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			updateJSONdata();
			return null;

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			updateList();
		}
	}
}
