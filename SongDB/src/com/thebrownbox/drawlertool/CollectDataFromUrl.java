package com.thebrownbox.drawlertool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.thebrownbox.database.MySQLite;
import com.thebrownbox.models.Show;
import com.thebrownbox.models.Song;

/*
 * Nhiệm vụ: chuyển content html sang object
 */

public class CollectDataFromUrl {
	public static int count = 0;
	private String url;
	private String content;

	public CollectDataFromUrl(String url) {
		this.url = url;
	}



	public void getTheShow() {
		content = HttpWork.GetStringFromURL(url);
		Document doc = Jsoup.parse(content);

		Elements titles = doc.getElementsByAttributeValue("class",
				"post-title entry-title");
		Elements times = doc.getElementsByAttributeValue("class", "entry-date");
		Elements contentUrls = doc.getElementsByAttributeValue("class",
				"element-wrap");
		Elements imageDiv = doc.getElementsByAttributeValue("class",
				"post-image");

		List<Show> list = new ArrayList<Show>();
		for (int i = 0; i < titles.size(); i++) {
			Show s = new Show(times.get(i).text());
			s.setTitle(titles.get(i).text());
			s.setContentUrl(contentUrls.get(i).attr("href"));
			s.setImageURL(imageDiv.get(i).getAllElements().get(2).attr("src"));
			list.add(s);
		}

		MySQLite.getInstance().AddShow(list);
		count++;
		System.out.println("Xong: " + count);
	}

	public void getTheSongsFromTheShow(Show s) {
		System.out.println(s.getContentUrl() + " is processing...");
		content = HttpWork.GetStringFromURL(s.getContentUrl());
		Document doc = Jsoup.parse(content);
		String pattern;
		Pattern p;
		Matcher m;
		int index = 0;
		List<Song> list = new ArrayList<Song>();

		// Lay id:
		pattern = "id:[^{}]+,l";
		p = Pattern.compile(pattern);
		m = p.matcher(content);
		while (m.find())
			list.add(new Song(Integer.parseInt(m.group().substring(5,
					m.group().length() - 3))));

		// Lay link bai hat
		pattern = "file:[^{}]+";
		p = Pattern.compile(pattern);
		m = p.matcher(content);
		index = 0;
		while (m.find())
			list.get(index++).setMp3URL(
					m.group().substring(6, m.group().length() - 1));

		// Lay ten bai hat va ca si
		pattern = "title:[^{}]+id";
		p = Pattern.compile(pattern);
		m = p.matcher(content);
		index = 0;
		while (m.find()) {
			String[] k = m.group().subSequence(8, m.group().length() - 4)
					.toString().split("-");
			if (k.length == 1) {
				System.out.println("x");
				list.get(index++).setTitle(k[0]);
			} else if (k.length == 2 || k.length == 3) {
				list.get(index).setTitle(k[0]);
				list.get(index++).setSinger(k[1]);
			}
		}

		// Laays image url
		pattern = "image:\"[^{}]+.jpg";
		p = Pattern.compile(pattern);
		m = p.matcher(content);
		index = 0;
		while (m.find())
			list.get(index++).setImageURL(
					m.group().substring(7, m.group().length()));
		
		for(Song song : list)
			song.setShowId(s.getId());
		
		MySQLite.getInstance().AddSong(list);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	///Chuyển jsuop của trang web về dạng Show
	public static Show UpdateTheShowFromQnsDotVN(Document doc)
	{
		
		return null;
	}
}