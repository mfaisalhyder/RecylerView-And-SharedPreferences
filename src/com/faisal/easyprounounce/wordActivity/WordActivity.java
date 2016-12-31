package com.faisal.easyprounounce.wordActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.faisal.easyprounounce.R;

public class WordActivity extends AppCompatActivity implements OnClickListener{

	TextView txtView_Word,txtView_POS;
	SharedPreferences sp;
	Toolbar mToolBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word);
		
		sp = getSharedPreferences("spWords", 0);
		
		mToolBar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolBar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		txtView_Word = (TextView) findViewById(R.id.txtView_Individual_Word);
		txtView_POS = (TextView) findViewById(R.id.txtView_Individual_POS);
		
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		sp.getString("word", "Data N/A");
		sp.getString("pos","Data N/A" );
		
		txtView_Word.setText(sp.getString("word", "Data N/A").toString());
		txtView_POS.setText(sp.getString("pos", "Data N/A").toString());		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	   switch(item.getItemId()){
	   
	   case android.R.id.home: 
	        finish();
	        break;
	    }
	    return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		
	}
}
