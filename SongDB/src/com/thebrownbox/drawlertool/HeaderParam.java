package com.thebrownbox.drawlertool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class HeaderParam
{
	public String VIEWSTATE;
	public String EVENTTARGET;
	public String EVENTVALIDATION;
	public boolean hasPage6;
	public int theLastPage;
	public void checkPage6(String html)
	{
		hasPage6 = html.contains("dgrPaging$ctl04$ctl06");
	}
	public static String getEVENTTARGET(int page)
	{
		return "dgrPaging%24ctl04%24ctl0"+page;
	}
	@Override
	public String toString() {
		return String.format("\nVIEWSTATE:%s\nEVENTTARGET:%s\nEVENTVALIDATION:%s\nHasPage6:%s\nTheLasPage:%d\n",VIEWSTATE,EVENTTARGET,EVENTVALIDATION ,hasPage6,theLastPage);
	}
}