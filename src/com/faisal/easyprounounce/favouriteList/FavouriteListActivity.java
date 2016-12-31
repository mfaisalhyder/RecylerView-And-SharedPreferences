package com.faisal.easyprounounce.favouriteList;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.faisal.easyprounounce.MainActivity;
import com.faisal.easyprounounce.R;
import com.faisal.easyprounounce.adapter.RecyclerAdapter;
import com.faisal.easyprounounce.adapter.RecyclerAdapter.ClickListener;
import com.faisal.easyprounounce.model.Word;
import com.faisal.easyprounounce.sqlite.DBSQLiteHandler;
import com.faisal.easyprounounce.wordActivity.WordActivity;

public class FavouriteListActivity extends AppCompatActivity implements ClickListener{

	Toolbar mToolBar;
	public List<Word> wordsListFavourite;
	RecyclerAdapter mRecyclerAdapter;
	RecyclerView mRecyclerView;
	MainActivity mObject;
	//SharedPreference mSharedPreference;
	SharedPreferences mSharedPreferences;
	SharedPreferences.Editor mEditor;
	//added extra
	DBSQLiteHandler dbHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favword_list);
		
		mSharedPreferences = getSharedPreferences("spWords", 0);
		
		mToolBar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolBar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setTitle("Favourite Words");
		
		//added Extra
		//Get starred items from SQLite
		dbHandler = new DBSQLiteHandler(getApplicationContext());
		wordsListFavourite = dbHandler.getWords();
		
		
		//Get favorite items from SharedPreferences.
		//mSharedPreference = new SharedPreference();
		//wordsListFavourite = mSharedPreference.getFavorites(getApplicationContext());	
		
		
		if (wordsListFavourite == null) {
			Snackbar.make(findViewById(R.id.favList_Layout), "No favourite - Null", Snackbar.LENGTH_SHORT).show();
		} else {
			
			if (wordsListFavourite.size() == 0) {
				Snackbar.make(findViewById(R.id.favList_Layout), "No favourite - size 0", Snackbar.LENGTH_SHORT).show();
			}
			
			mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
			if (wordsListFavourite != null) {
				
				mRecyclerAdapter = new RecyclerAdapter(getApplicationContext(), wordsListFavourite);
				
				mRecyclerView.setHasFixedSize(true);
				mRecyclerView.setItemAnimator(new DefaultItemAnimator());
				mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
				mRecyclerView.setAdapter(mRecyclerAdapter);
				//mRecyclerAdapter.notifyDataSetChanged ();
				mRecyclerAdapter.setListener(this);			
			}
		}		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void itemClicked(View view, final int position) {
		
		//String img = view.getResources().getResourceName(R.id.imgButton_Favourite);
		//Toast.makeText(getApplicationContext(), img, Toast.LENGTH_SHORT).show();
		
		//RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForItemId(mRecyclerAdapter.getItemId(position));
		
		Word mapperObject = wordsListFavourite.get(position);

		String strWord = mapperObject.getWord();
		String strPOS = mapperObject.getPartOfSpeech();

		mEditor = mSharedPreferences.edit();
		mEditor.putString("word", strWord);
		mEditor.putString("pos", strPOS);
		mEditor.commit();

		Intent intent = new Intent(getApplication(), WordActivity.class);
		startActivity(intent);
	}	
}