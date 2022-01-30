/***
 * @author Ahmed Saleh
 * Created by ahmadssb on 2014-12-13
 * Website: http://www.ahmadssb.com
 * Email: ahmad@ahmadssb.com
 * Facebook: https://www.facebook.com/ahmadssbblog
 * Twitter: https://twitter.com/ahmadssb
 * YouTube: http://www.youtube.com/user/ahmadssbblog
 */
package com.ahmaadssb.androidwebservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class UsersActivity extends Activity {
	
	// Declare Variables
	JSONObject jsonobject;
	JSONArray jsonArrayUsers;
	
	ListView listview;
	ListViewAdapter adapterListView;
	
	ProgressDialog mProgressDialog;
	
	ArrayList<HashMap<String, String>> arraylistUsers;
	
	// JSON parser Class
	JSONParser jsonParser = new JSONParser();

	// �� ���� ���� ����� ���
	private static String USERS_URL = "http://192.168.1.103/GoogleDrive/android-webservice1/users.php";

	// �� ���� Android emulator
	// private static String USERS_URL = "http://10.0.2.2/GoogleDrive/android-webservice1/users.php";

	// �� ���� ���� ������ ��� ��������
	// private static String USERS_URL = "http://www.YOUR-DOMAIN.com/GoogleDrive/android-webservice1/users.php";

	
	// JSON IDS:
	static final String TAG_SUCCESS = "success";
	static final String TAG_USERS_LIST = "users";
	static final String TAG_USER_ID = "id";
	static final String TAG_USER_USERNAME = "username";
	static final String TAG_USER_DISPLAYNAME = "displayname";
	private static final String TAG_MESSAGE = "message";
    
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_layout);

		new DownloadJSON().execute();
	}
	
	


	/***
	 * 
	 * @author Ahmadssb 
	 * �� ��� ������ ����� �������� ����� ����� ������ ����  �����
	 * ��� �� ���� ��� JSON Response
	 * ������ ��� ���� success ���� �� ����� �����
	 * 
	 */
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {
		int status;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		@Override
		protected Void doInBackground(Void... params) {
			// ���� ������ ArrayList 
			arraylistUsers = new ArrayList<HashMap<String, String>>();
			
			try {
				
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				//��� ����� �� ���� �� LogCat
				Log.d("request!", "Starting");
				

				// ���� ������ ��� POST ��� ���� Users 
				// ������ ��� JSON Objects 
			jsonobject = JSONParser.makeHttpRequest(USERS_URL,"POST",param);

			//��� ����� �� ���� �� LogCat
			Log.d("Loding Restaurant", jsonobject.toString());
			

			// ��� ���� success ����� ����� ����� �� JSON
			// ����� ������� success
			status = jsonobject.getInt(TAG_SUCCESS);
			
			if(status != 0)
			{
				// ���� ������� ��� ������ users �� JSON 
				jsonArrayUsers = jsonobject.getJSONArray(TAG_USERS_LIST);


				for (int i = 0; i < jsonArrayUsers.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject jsonobject = jsonArrayUsers.getJSONObject(i);
					
					// ���� ������� ��� JSON Objects �� ���� �� Object 
					// ����� ������ �� ���� HashMap 
					map.put(TAG_USER_ID, jsonobject.getString(TAG_USER_ID));
					map.put(TAG_USER_USERNAME, jsonobject.getString(TAG_USER_USERNAME));
					map.put(TAG_USER_DISPLAYNAME, jsonobject.getString(TAG_USER_DISPLAYNAME));
					
					
					// ���� ���� �������� ������� �� map  ����� arraylistUsers
					arraylistUsers.add(map);
				}
				//��� ����� �� ���� �� LogCat
				Log.d("jsonArrayKioskRestaurant Size:", ""+arraylistUsers.size());
				
				
				
			}else{
				//��� ����� �� ���� �� LogCat
				Log.d("Loding Restaurant Failure!", jsonobject.getString(TAG_MESSAGE));
			}
					
			
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {

			if (status != 0) {
				
				// ���� ���� ������ listView
				listview = (ListView) findViewById(R.id.listView1);
				// ���� ������ ������ �������� arraylistUsers ����� ListViewAdapter
				// ��� �� ListViewAdapter ������ ������ �������� 
				adapterListView = new ListViewAdapter(UsersActivity.this, arraylistUsers);
				// ���� ������ adapterListView ����� listView
				listview.setAdapter(adapterListView);
				
				//  �� ��� ��� ���� �� ���� ������ ��� ���� ��� ����� ��� ��� ������ �� ListView
				// ���� ������� ������ setOnItemClickListener()
				listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						
						TextView tvUserID = (TextView) (arg1.findViewById(R.id.txtID));
						String sUserID = tvUserID.getText().toString();
						TextView tvUsername = (TextView) (arg1.findViewById(R.id.txtUsername));
						String sUsername = tvUsername.getText().toString();
						TextView tvDisplayName = (TextView) (arg1.findViewById(R.id.txtDisplayName));
						String sDisplayName = tvDisplayName.getText().toString();
						
						Toast.makeText(UsersActivity.this, sUserID + " - " + sUsername + " - " + sDisplayName, Toast.LENGTH_LONG).show();
						

					}
				});
				
			}
			
		}
	}	
}
