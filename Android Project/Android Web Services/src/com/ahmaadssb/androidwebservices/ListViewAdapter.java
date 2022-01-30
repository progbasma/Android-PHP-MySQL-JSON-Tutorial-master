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

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	// ���� ������ ��������� ��������
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	HashMap<String, String> resultp = new HashMap<String, String>();

	// ����� ����������� 
	// ��� ����� ��� ��������� 
	//context = ��� ������ 
	// arraylist = ������ users ���� ����� ����� �� JSONArray
	public ListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		// ��� ������� �� �������� ���� ����� ����� �� ����������� ��� ����� ���������� ������ �� single_user_layout
		data = arraylist;
	}

	@Override
	public int getCount() {
		// ���� ��� ��� data 
		// ����� �� ��� ������ JSON
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	/***
	 * �� ��� ������ ����� ������ ��  single_user_layout
	 * � ���� ������ �� ���� TextView 
	 * � �� �� ���� ���� ����� �������� �� data
	 * ������ �� �� TextView
	 */
	public View getView(final int position, View convertView, ViewGroup parent) {

		// ����� ������� TextView ��������
		TextView tvUserID, tvUsername, tvDisplayName;

		// ���� ������ inflater 
		// LayoutInflater : ����� ����
		// ���� �� ������ �� ���� (��-��-����...���) ��� View Objects 
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// ���� ������ itemView Object 
		// inflater.inflate (int resource, ViewGroup root, boolean attachToRoot)
		// resource = single_user_layout ����� ������� ���� ��� ������ ���������
		// ViewGroup root = parent ��� ���������� ������� �� getViwe() � ����� ��� ������ ��� ��� ���� �� ��� �������
		View itemView = inflater.inflate(R.layout.single_user_layout, parent, false);

		// ���� ��� ���� ������
		// ����� ��� �� ��� ���� 4 �������� ����� ��� �������� �� JSON Array
		// �� ������ �� ������ ����� ��� id - username - displayname 
		// ��� ��� ���� ���� �������� ��������
		// ���� ���� 2 ����� ��� ������ �������� ��� 2
		resultp = data.get(position);

		// ���� ����� �� ����� TextView ��� �� ����� �� ��� single_user_layout
		tvUserID = (TextView) itemView.findViewById(R.id.txtID);
		tvUsername = (TextView) itemView.findViewById(R.id.txtUsername);
		tvDisplayName = (TextView) itemView.findViewById(R.id.txtDisplayName);

		// ���� ���� ������ TextView �� �������
		tvUserID.setText(resultp.get(UsersActivity.TAG_USER_ID));
		tvUsername.setText(resultp.get(UsersActivity.TAG_USER_USERNAME));
		tvDisplayName.setText(resultp.get(UsersActivity.TAG_USER_DISPLAYNAME));

		// ����� ���� ������ itemView 
		return itemView;
	}

}
