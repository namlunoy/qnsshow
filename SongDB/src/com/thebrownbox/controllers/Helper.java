package com.thebrownbox.controllers;

public class Helper {
	public static boolean tryParseInt(String s)
	{
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
