package com.pc.customlist;

import java.util.Vector;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import com.pc.customlist.R;

public class IndexTableActivity extends CustomListIndex 
{
	ListView booksLV;
	TextView tv ;
	private UserListAdapter userListAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample);
		booksLV = (ListView)findViewById(R.id.booksLV);
		tv = (TextView)findViewById(R.id.tv);
		userVector = UserService.getUserList();

		final Vector<Book> subsidiesList = getIndexedBooks(userVector);
		totalListSize = subsidiesList.size();

		userListAdapter = new UserListAdapter(this, subsidiesList);
		booksLV.setAdapter(userListAdapter);
		/*  booksLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				System.err.println("Clicked...."+arg1);

			}
		});
		 */        booksLV.setOnItemClickListener(new OnItemClickListener() {

			 @Override
			 public void onItemClick(AdapterView<?> arg0, View arg1,
					 int position, long arg3) {	
				 if(!global.arrayforTitle.contains(position)){
					 Toast.makeText(getApplicationContext(), subsidiesList.get(position).getTitle(), 1000).show();
				 }
				 System.err.println("Clicked...."+position);}
		 });
		 booksLV.setOnScrollListener(new OnScrollListener() {
			 public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
				 // TODO Auto-generated method stub

			 }

			 public void onScroll(AbsListView paramAbsListView, int paramInt1,int paramInt2, int paramInt3) {
				 // TODO Auto-generated method stub
				 if(global.arrayforTitle.contains(paramInt1)){
					 System.out.println("Matched..."+paramInt1);
					 tv.setText(subsidiesList.get(paramInt1).getTitle());

				 }
				 //	System.out.println("firstVisibleItem="+paramInt1 +" visibleItemCount..="+paramInt2+"--- totalItemCount---"+paramInt3);
			 }
		 });
		 //LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
		 LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
		 sideIndex.setOnClickListener(onClicked);
		 sideIndexHeight = sideIndex.getHeight();
		 mGestureDetector = new GestureDetector(this, new ListIndexGestureListener());
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		getDisplayListOnChange();
	}


	private Vector<Book> getIndexedBooks(Vector<Book> booksVector) {

		//Retrieve it from DB in shorting order
		Vector<Book> v = new Vector<Book>();
		//Add default item
		String idx1 = null;
		String idx2 = null;
		for (int i = 0; i < booksVector.size(); i++) {
			Book temp = booksVector.elementAt(i);
			//Insert the alphabets
			idx1 = (temp.getTitle().substring(0, 1)).toLowerCase();
			if(!idx1.equalsIgnoreCase(idx2))
			{
				v.add(new Book(idx1.toUpperCase(), "", "" , "" , ""));
				idx2 = idx1;
				dealList.add(i);
			}
			v.add(temp);
		}

		return v;
	}

	/**
	 * ListIndexGestureListener method gets the list on scroll.
	 */
	private class ListIndexGestureListener extends
	GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {

			/**
			 * we know already coordinates of first touch we know as well a
			 * scroll distance
			 */
			sideIndexX = sideIndexX - distanceX;
			sideIndexY = sideIndexY - distanceY;

			/**
			 * when the user scrolls within our side index, we can show for
			 * every position in it a proper item in the list
			 */
			if (sideIndexX >= 0 && sideIndexY >= 0) {
				displayListItem();
			}

			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	}
}