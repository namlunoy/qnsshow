package com.thebrownbox.models;

import java.util.Date;
import java.util.List;

public class Show {
	private String title;
	private Date date;
	private String imageURL;
	private List<Song> listSong;
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public List<Song> getListSong() {
		return listSong;
	}
	public void setListSong(List<Song> listSong) {
		this.listSong = listSong;
	}
}
