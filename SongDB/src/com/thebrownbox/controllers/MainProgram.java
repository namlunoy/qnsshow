package com.thebrownbox.controllers;

import java.io.IOException;
import java.util.List;

import com.thebrownbox.database.MySQLite;
import com.thebrownbox.drawlertool.CollectDataFromUrl;
import com.thebrownbox.models.Show;

public class MainProgram {
	public static void main(String[] args) {
		
		List<Show> list = MySQLite.getInstance().getAllTheShow();
		int count = 0;
		int total = list.size();
		for(Show s : list)
		{
			count++;
			new CollectDataFromUrl(s.getContentUrl()).getTheSongsFromTheShow(s);
			System.out.println("Process: "+count+"/"+total);
		}
		

	}
}