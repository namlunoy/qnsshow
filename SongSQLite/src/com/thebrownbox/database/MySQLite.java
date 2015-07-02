package com.thebrownbox.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.thebrownbox.models.Show;
import com.thebrownbox.models.Song;

public class MySQLite {
	private static MySQLite instance = null;

	public static MySQLite getInstance() {
		if (instance == null)
			instance = new MySQLite();
		return instance;
	}

	private Connection conn;
	private Statement cmd;

	private MySQLite() {
		try {
			Class.forName("org.sqlite.JDBC");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Show> getTheShow(int index, int number) {
		try {
			String sql = "select * from show limit " + index + "," + number;
			conn = DriverManager.getConnection("jdbc:sqlite:qns.db");
			cmd = conn.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			List<Show> list = new ArrayList<Show>();

			while(rs.next())
			{
				Show s = new Show(rs.getString("date"));
				s.setContentUrl(rs.getString("content_url"));
				s.setImageURL(rs.getString("image_url"));
				s.setIsFavorited(rs.getBoolean("is_favorited"));
				s.setTitle(rs.getString("title"));
				list.add(s);
			}
			
			rs.close();
			cmd.close();
			conn.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Show> getAllTheShow() {
		try {
			String sql = "select * from show";
			conn = DriverManager.getConnection("jdbc:sqlite:qns.db");
			cmd = conn.createStatement();
			ResultSet rs = cmd.executeQuery(sql);
			List<Show> list = new ArrayList<Show>();

			while(rs.next())
			{
				Show s = new Show(rs.getString("date"));
				s.setContentUrl(rs.getString("content_url"));
				s.setImageURL(rs.getString("image_url"));
				s.setIsFavorited(rs.getBoolean("is_favorited"));
				s.setTitle(rs.getString("title"));
				list.add(s);
			}
			
			rs.close();
			cmd.close();
			conn.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void AddShow(List<Show> list) {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:qns.db");
			System.out.println("Connected to database!");

			cmd = conn.createStatement();
			for (Show s : list) {
				// insert into show (id,title,date,image_url,content_url) values
				// (1,'123', '123','123','123');
				String sql = "insert into show (id,title,date,image_url,content_url) values ("
						+ s.getId()
						+ ",'"
						+ s.getTitle()
						+ "', '"
						+ s.getDateString()
						+ "','"
						+ s.getImageURL()
						+ "','"
						+ s.getContentUrl() + "');";
				cmd.execute(sql);
			}

			conn.close();
			System.out.println("Closed database!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void AddSong(List<Song> list) {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:qns.db");
			System.out.println("Connected to database!");

			cmd = conn.createStatement();
			for (Song s : list) {
				//insert into song (id,title,singer,image_url,mp3_url) values (1,'title','singer','image','mp3')
				String sql = "insert into song (id,title,singer,image_url,mp3_url,show_id) values ("+s.getId()+",'"+s.getTitle()+"','"+s.getSinger()+"','"+s.getImageURL()+"','"+s.getMp3URL()+"',"+s.getShowId()+")";
				cmd.execute(sql);
			}

			conn.close();
			System.out.println("Closed database!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
