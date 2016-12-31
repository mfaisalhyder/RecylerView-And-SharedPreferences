package com.faisal.easyprounounce.model;

public class Word {

	private int id;
	private String word;
	private String partOfSpeech;

	public Word() {
		super();
	}

	public Word( String word, String pos) {
		super();
		//this.id = id;
		this.word = word;
		this.setPartOfSpeech(pos);
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getWord() {
		return this.word;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Word [ ID="+ id + ", Word=" + word + ", part of Speech="
				+ partOfSpeech + " ]";
	}*/
	
	@Override
	public String toString() {
		return "Word [ Word=" + word + ", part of Speech="	+ partOfSpeech + " ]";
	}
}