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
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	private EditText user, pass;
	private Button login, register;

	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();

	// �� ���� ���� ����� ���
	private static String LOGIN_URL = "http://192.168.1.103/GoogleDrive/android-webservice1/login.php";

	// �� ���� Android emulator
	// private static String LOGIN_URL = "http://10.0.2.2/GoogleDrive/android-webservice1/login.php";

	// �� ���� ���� ������ ��� ��������
	// private static String LOGIN_URL = "http://www.YOUR-DOMAIN.com/GoogleDrive/android-webservice1/login.php";

	/***
	 * ��� ����� �� ����� PHP ���� ���� ������ (�������) � JSON ���� �� ����
	 * �������� �� static final ����� ��� ����� �������� �����
	 */
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_USERNAME = "username";
	private static final String TAG_PASSWORD = "password";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		user = (EditText) findViewById(R.id.edtUsername);
		pass = (EditText) findViewById(R.id.edtPassword);

		login = (Button) findViewById(R.id.btnLogin);

		register = (Button) findViewById(R.id.btnRegisterUser);

		login.setOnClickListener(this);

		register.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLogin:
			// ��� ����� ��� Login
			// ���� ������ ���� AttemptLogin()
			new AttemptLogin().execute();
			break;
		case R.id.btnRegisterUser:
			Intent i = new Intent(MainActivity.this, RegisterActivity.class);
			startActivity(i);
			break;

		default:
			break;
		}
	}

	/***
	 * 
	 * @author Ahmadssb �� ��� ������ ����� �������� ����� ����� ������ �����
	 *         ������ ����������� �������� username , Password ��� �� ������ ���
	 *         JSON Response ������ ��� ���� success ���� �� ����� �����
	 * 
	 */

	class AttemptLogin extends AsyncTask<String, String, String> {

		boolean failure = false;

		// ���� ���� ProgressDialog
		// ��� ����� ���� ������� �������� �� doInBackground()
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("������ ����� ������ ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub

			int success;
			// ���� ������ �������� �������� �� ���� username � password ���
			String username = user.getText().toString();
			String password = pass.getText().toString();
			try {
				// ���� ����� ����������� �������� ������ ����� ������
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair(TAG_USERNAME, username));
				params.add(new BasicNameValuePair(TAG_PASSWORD, password));

				// ��� ����� �� ���� �� LogCat
				Log.d("request!", "starting");

				// ���� ������ ��� POST ��� ���� ����� ������ �� ����� ������������
				// ������ ��� JSON Objects 
				JSONObject json = JSONParser.makeHttpRequest(LOGIN_URL, "POST",	params);

				// ��� ����� �� ���� �� LogCat
				Log.d("Login attempt", json.toString());

				// ��� ���� success ����� ����� ����� �� JSON
				// ����� ������� success
				success = json.getInt(TAG_SUCCESS);

				/*
				 * �� ��� ���� ���� success = 1 ���� ��� ��� �� ������� �����
				 * ���� ����� ���� ���� ������ ���� ��� �������� ��� ��������
				 * ��� �� �� �� �����
				 * 
				 * ******************************** ��� �� ���� ���� success = 0
				 * ���� ��� ���� ����� ���� �� �������� �� �� ������� ��������
				 * ����� ���� ����� ���� �����
				 */
				if (success == 1) {
					Log.d("Login Successful!", json.toString());

					Intent i = new Intent(MainActivity.this,
							UsersActivity.class);

					startActivity(i);
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(String file_url) {
			// ���� ������ �������� ��� ��������
			pDialog.dismiss();

			// ���� ���� ������� ������� �� ���� doInBackground()
			if (file_url != null) {
				Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG)
						.show();

			}

		}

	}

}