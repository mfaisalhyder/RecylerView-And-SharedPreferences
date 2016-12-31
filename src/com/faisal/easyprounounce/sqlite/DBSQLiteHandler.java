package com.faisal.easyprounounce.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.faisal.easyprounounce.model.Word;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBSQLiteHandler extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "easyPronounce";
	private static final String TABLE_WORD = "words";
	private static final String KEY_ID = "id";
	private static final String KEY_WORD = "word";
	private static final String KEY_POS = "partofspeech";
	Context context;

	public DBSQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	//create table
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_WORD + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WORD + " TEXT,"
                + KEY_POS + " TEXT" + ")";
		db.execSQL(CREATE_TABLE);
	}

	//update table
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_WORD);
		onCreate(db);
	}
	
	//add word
	public void addWord(Word word){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_WORD, word.getWord());
		contentValues.put(KEY_POS, word.getPartOfSpeech());
		
		db.insert(TABLE_WORD,null,contentValues);
		db.close();		
	}
	
	//remove word
	public void removeWord(Word word){
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete(TABLE_WORD, KEY_WORD + " = ?", new String[]{String.valueOf(word.getWord())});
		db.close();
	}
	
	//getWord
	public Word getWord(Word word){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_WORD, new String[]{KEY_ID,KEY_WORD,KEY_POS},KEY_ID + " = ?",
				new String[]{String.valueOf(word.getId())}, null,null,null,null);
		if(cursor!=null) 
			cursor.moveToFirst();
		
		Word wordFound = new Word(cursor.getString(1), cursor.getString(2));
		
		return wordFound;
	}
	
	//getAllWords
	public ArrayList<Word> getWords(){
		List<Word> wordsList = new ArrayList<Word>();
		String query = "SELECT * FROM " + TABLE_WORD;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst()){
			do{
				Word word = new Word(cursor.getString(1), cursor.getString(2));
				wordsList.add(word);				
			}while(cursor.moveToNext());
		}
		
		return (ArrayList<Word>) wordsList;
	}

}
