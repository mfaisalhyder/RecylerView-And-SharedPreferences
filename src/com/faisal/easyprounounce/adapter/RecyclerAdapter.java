package com.faisal.easyprounounce.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.faisal.easyprounounce.R;
import com.faisal.easyprounounce.model.Word;
import com.faisal.easyprounounce.sqlite.DBSQLiteHandler;
import com.faisal.easyprounounce.utility.SharedPreference;

@SuppressLint("NewApi")
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemsViewHolder>{
	
	private List<Word> wordsList;
	public List<Word> favouriteWords = new ArrayList<Word>();
	int mPreviousPosition = -1;
	Context mContext;
	static ClickListener clickListener;	
	SharedPreference mSharedPreference;
	//added extra
	DBSQLiteHandler dbHandler;
	public ArrayList<Word> wordsListDB = new ArrayList<Word>();
	
	
	public RecyclerAdapter(Context con, List<Word> wordsList){
		this.wordsList=wordsList;
		this.mContext=con;
		//mSharedPreference = new SharedPreference();
		//added extra
		this.dbHandler = new DBSQLiteHandler(mContext);
		this.wordsListDB = (ArrayList<Word>) wordsList;
	}
	
	@Override
	public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){		
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
		return new ItemsViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(ItemsViewHolder holder, int position) {
		
		Word wordMapper = wordsList.get(position);
		
		holder.txtViewIcon.setText(""+ wordMapper.getWord().charAt(0));
		holder.txtViewIcon.setGravity(Gravity.CENTER);
		holder.txtViewWord.setText(wordMapper.getWord());
		holder.txtViewPOS.setText(wordMapper.getPartOfSpeech());		
			
		/*If a product exists in SQLite then set filled star drawable and set a tag*/
		if(checkFavouriteItem(wordMapper)){
			
			Drawable starFilled = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_favourite_filled, null);
			starFilled.setBounds(0,0,24,24);
			holder.imgButtonFavourite.setBackground(starFilled);
			holder.imgButtonFavourite.setTag("filled");
			
		}else{
			
			Drawable starEmpty = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_favourite,null);
			starEmpty.setBounds(0,0,24,24);
			holder.imgButtonFavourite.setBackground(starEmpty);
			holder.imgButtonFavourite.setTag("empty");
			
		}
		//Animation on Up and Down scroll
		
		/*if(position>mPreviousPosition){
			AnimationUtils.animate(holder, true);			
		}else{
			AnimationUtils.animate(holder, true);
		}	
	    mPreviousPosition = position;*/
	}
	
	@Override
	public int getItemCount() {
		return wordsList.size();
		//return (null != wordsList ? wordsList.size() : 0);
	}
	
	public class ItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
		
		CardView cardView;
		TextView txtViewIcon,txtViewWord,txtViewPOS;
		ImageButton imgButtonFavourite;
		boolean starred = false;		
		
		public ItemsViewHolder(View itemView){
			super(itemView);
			
			cardView = (CardView) itemView.findViewById(R.id.cardViewID);
			txtViewIcon = (TextView) itemView.findViewById(R.id.txtView_iconEntry);
			txtViewWord = (TextView) itemView.findViewById(R.id.txtView_Word);
			txtViewPOS = (TextView) itemView.findViewById(R.id.txtView_PartOfSpeech);
			imgButtonFavourite = (ImageButton) itemView.findViewById(R.id.imgButton_Favourite);			
			
			itemView.setOnClickListener(this);	
			imgButtonFavourite.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					
					String tag = imgButtonFavourite.getTag().toString();
					if (tag.equalsIgnoreCase("empty") && !starred) {
						
						//SharedPreference way
						//mSharedPreference.addFavorite(mContext,	wordsList.get(getAdapterPosition()));
						
						//SQLiteDB way
						dbHandler.addWord(wordsList.get(getAdapterPosition()));
						
						imgButtonFavourite.setTag("filled");
						Drawable starFilled = ResourcesCompat.getDrawable(view.getResources(), R.drawable.ic_favourite_filled, null);
						starFilled.setBounds(0, 0, 24, 24);
						imgButtonFavourite.setBackground(starFilled);
						
						Snackbar.make(view, "Added to Favorites", Snackbar.LENGTH_LONG).setAction("Remove",new View.OnClickListener() {
							
							@Override
							public void onClick(View view) {
								//SharedPreference way
								//mSharedPreference.removeFavorite(mContext, wordsList.get(getAdapterPosition()));
								
								//SQLiteDB way
								dbHandler.removeWord(wordsList.get(getAdapterPosition()));
								
								Drawable star = ResourcesCompat.getDrawable(view.getResources(), R.drawable.ic_favourite, null);
								star.setBounds(0,0,24,24);
								imgButtonFavourite.setBackground(star);
							}
						}).show();
					} else {

						//SharedPreference way
						//mSharedPreference.removeFavorite(mContext,	wordsList.get(getAdapterPosition()));
						
						//SQLiteDB way
						dbHandler.removeWord(wordsList.get(getAdapterPosition()));
						
						//*****//
						wordsList.remove(getAdapterPosition());
						notifyItemRemoved(getAdapterPosition());
						notifyItemRangeChanged(getAdapterPosition(), wordsList.size());
						//*******//	
						
						imgButtonFavourite.setTag("empty");
						Drawable starEmpty = ResourcesCompat.getDrawable(view.getResources(), R.drawable.ic_favourite, null);
						starEmpty.setBounds(0, 0, 24, 24);
						imgButtonFavourite.setBackground(starEmpty);					
					}					
					starred =! starred;				
				}	
			});
		}
		
		@Override
		public void onClick(View view) {
			if(clickListener!=null){
				clickListener.itemClicked(view, getAdapterPosition());
			}
		}
	}
	
	
/*	//added extra
	public void removeItem(int position){
		wordsList.remove(position);
		notifyItemRemoved(position);
	}
		
	//added extra
	public void addItem(MapperClass mapperObject){
		wordsList.add(mapperObject);
		notifyDataSetChanged();
	}
*/
	
	//Checks whether a particular product exists in SQLiteDB
	public boolean checkFavouriteItem(Word checkStarredItem){
		boolean check = false;
		//shared preference way
		/*List<Word> favouriteItemsInSharedPreference = mSharedPreference.getFavorites(mContext); 
		
		if (favouriteItemsInSharedPreference != null) {
            for (Word word : favouriteItemsInSharedPreference) {
                if (word.equals(checkStarredItem)) {
                    check = true;
                    break;
                }
            }
        }*/
        
		
		//SQLiteDB way		
		ArrayList<Word> itemsInDB = dbHandler.getWords();
		
		if(itemsInDB!=null){
			for(Word word : itemsInDB){
				if((word.getWord()).equals(checkStarredItem.getWord())) {
					check = true;
					break;
				}
			}
		}
        return check;
	}	

	public void setListener(ClickListener clicked){
		RecyclerAdapter.clickListener = clicked;
	}	
	
	public interface ClickListener{
		public void itemClicked(View view, int position);
	}
}
