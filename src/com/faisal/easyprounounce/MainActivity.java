package com.faisal.easyprounounce;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.faisal.easyprounounce.adapter.RecyclerAdapter;
import com.faisal.easyprounounce.adapter.RecyclerAdapter.ClickListener;
import com.faisal.easyprounounce.favouriteList.FavouriteListActivity;
import com.faisal.easyprounounce.model.Word;
import com.faisal.easyprounounce.wordActivity.WordActivity;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity implements ClickListener,View.OnClickListener{

	private Toolbar mToolBar;
	private DrawerLayout mDrawerLayout;
	private NavigationView mNavigationView;
	private static ActionBarDrawerToggle mDrawerToggle;
	private FloatingActionButton mFAB;
	private RecyclerView mRecyclerView;
	RecyclerAdapter mRecyclerAdapter;
	private List<Word> wordsList ;
	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mEditor;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		//********************<-----SharedfPreferences----->******************//
		mSharedPreferences = getSharedPreferences("spWords", 0);
		
		//********************<-----SharedfPreferences----->******************//
				
		
		//*********************<------ToolBAR----->***************************//
		mToolBar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolBar);		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//*********************<------ToolBAR----->***************************//
		
				
		//*********************<------Hamburger ICON----->***************************//
		mDrawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.drawer_close, R.string.drawer_open) {
			//for overriding default color of background when drrawer is opened
			/*@Override
			public void onDrawerSlide(View drawerView, float slideOff) {
				super.onDrawerSlide(drawerView, slideOff);
				mToolBar.setAlpha(1 - 0.0f * slideOff);				
			}*/
		};				
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();
		//*********************<------Hamburger ICON----->***************************//
		
		
		//*********************<------Navigation Drawer----->***************************//				
		mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
		mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			
			@Override
			public boolean onNavigationItemSelected(MenuItem mItem) {
				mItem.setChecked(true);
				mDrawerLayout.closeDrawers();
				
				int id = mItem.getItemId();
				switch(id) {
				case R.id.navigation_item_RateIT:
					Toast.makeText(MainActivity.this, "Rate it Clicked", Toast.LENGTH_SHORT).show();
					break;
					
				case R.id.navigation_item_HelpDeveloper:
					Toast.makeText(MainActivity.this, "Helping Development Clicked", Toast.LENGTH_SHORT).show();
					break;
								
				case R.id.navigation_item_AboutUs:
					Toast.makeText(MainActivity.this, "About Us Clicked", Toast.LENGTH_SHORT).show();
					break;
				}				
				return true;
			}
		});		 
		//*********************<------Navigation Drawer----->***************************//
		
		
		//*********************<------Floating Action Button----->***************************//
		mFAB = (FloatingActionButton) findViewById(R.id.fab);	
		mFAB.setOnClickListener(this);		
		//*********************<------Floating Action Button----->***************************//
		
		//*********************<------Recycler View----->***************************//
		
		wordsList = new ArrayList<Word>();
		mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain);
		mRecyclerAdapter = new RecyclerAdapter(getApplicationContext(), wordsList);
		
		mRecyclerView.setHasFixedSize(true);
	    mRecyclerAdapter.setListener(this);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
		
		mRecyclerView.setAdapter(mRecyclerAdapter);	
		
		/*mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
           	@Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && mFAB.isShown()){
                	mFAB.hide();
                }else if (dy < 0 && mFAB.getVisibility() != View.VISIBLE) {
                	mFAB.show();
	            }
           	}					
        });*/
		//*********************<------Recycler View----->***************************//

		prepareWordsList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		switch (id) {
        case android.R.id.home:
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        case R.id.action_search:
            return true;
    }

    return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		
		case R.id.fab:
			Intent favList = new Intent(this, FavouriteListActivity.class);
			startActivity(favList);
			break;
		}		
	}
	
	@Override
	public void itemClicked(View view, int position) {
		Word mapperObject = wordsList.get(position);

		String strWord = mapperObject.getWord();
		String strPOS = mapperObject.getPartOfSpeech();
		
		mEditor = mSharedPreferences.edit();
		mEditor.putString("word", strWord);
		mEditor.putString("pos", strPOS);
		mEditor.commit();
		
		Intent intent = new Intent(getApplication(),WordActivity.class);
		
		startActivity(intent);		
	}
	
	private void prepareWordsList(){
		
		Word mapperClass = new Word("Abacus", "Noun");
		wordsList.add(mapperClass);
		
		mapperClass = new Word("Abash", "Verb");
		wordsList.add(mapperClass);
		
		mapperClass = new Word("Abate", "Noun");
		wordsList.add(mapperClass);
		
		mapperClass = new Word("Apple", "Noun");
		wordsList.add(mapperClass);
		
		mapperClass = new Word("Home", "Noun");
		wordsList.add(mapperClass);

		mapperClass = new Word("Beautifull", "Adjective");
		wordsList.add(mapperClass);
		
		mapperClass = new Word("Facade", "Noun");
		wordsList.add(mapperClass);
		
		mapperClass = new Word("Pinnacle", "Noun");
		wordsList.add(mapperClass);
		
		mapperClass = new Word("Ameliorate", "Verb");
		wordsList.add(mapperClass);
		
		mapperClass = new Word("Abyss", "Noun");
		wordsList.add(mapperClass);	
		
		/*		Word mapperClass = new Word(1,"Abacus", "Noun");
		wordsList.add(mapperClass);
		
		mapperClass = new Word(2,"Abash", "Verb");
		wordsList.add(mapperClass);
		
		mapperClass = new Word(3,"Abate", "Noun");
		wordsList.add(mapperClass);
		
		mapperClass = new Word(4,"Apple", "Noun");
		wordsList.add(mapperClass);
		
		mapperClass = new Word(5,"Home", "Noun");
		wordsList.add(mapperClass);

		mapperClass = new Word(6,"Beautifull", "Adjective");
		wordsList.add(mapperClass);
		
		mapperClass = new Word(7,"Facade", "Noun");
		wordsList.add(mapperClass);
		
		mapperClass = new Word(8,"Pinnacle", "Noun");
		wordsList.add(mapperClass);
		
		mapperClass = new Word(9,"Ameliorate", "Verb");
		wordsList.add(mapperClass);
		
		mapperClass = new Word(10,"Abyss", "Noun");
		wordsList.add(mapperClass);	*/
		//mRecyclerAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	    mRecyclerAdapter.setListener(this);		
		mRecyclerView.setAdapter(mRecyclerAdapter);			
	}	
}