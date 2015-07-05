package com.thebrownbox.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Show {
	private SimpleDateFormat dateFormatter;
	private String title;
	private Date date;
	private String imageURL;
	private List<Song> listSong;
	private Boolean isFavorited;
	private String contentUrl;
	private String mp3Url;
	

	public static void main(String[] args) {
		Show s = new Show("29/10/2006");
		s.setDate("29/10/2006");
		System.out.println(s.getId());
	}

	public Show(String date) {
		dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		setDate(date);
		listSong = new ArrayList<Song>();
	}

	public int getId() {
		return Integer.parseInt(String.format("%02d%02d%02d",
				date.getYear() + 1900, date.getMonth() + 1, date.getDate()));
	}

	public String getDateString()
	{
		return String.format("%02d/%02d/%d",
				date.getDate(), date.getMonth() + 1,date.getYear() + 1900 );
	}
	
	// -------------- Setter Getter -----------
	
	
	
	@Override
	public String toString() {
		System.out.println("title: "+title);
		return super.toString();
	}

	public Boolean getIsFavorited() {
		return isFavorited;
	}

	public void setIsFavorited(Boolean isFavorited) {
		this.isFavorited = isFavorited;
	}

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

	public void setDate(String date) {
		try {
			this.date = dateFormatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getMp3Url() {
		return mp3Url;
	}

	public void setMp3Url(String mp3Url) {
		this.mp3Url = mp3Url;
	}
}
