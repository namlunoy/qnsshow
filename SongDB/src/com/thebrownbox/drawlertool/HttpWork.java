package com.thebrownbox.drawlertool;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.thebrownbox.database.MySQLite;
import com.thebrownbox.models.Show;

/**
 * @author Làm mọi thao tác với mạng để get data về
 */

public class HttpWork {
	public static String encode(String viewstate) {
		return URLEncoder.encode(viewstate);
	}



	/**
	 * Thực hiện tạo header để lấy được content chuẩn về Sau đó bóc tách luôn
	 * trong hàm này
	 * 
	 * @param p
	 * @return
	 */
	public HeaderParam DoRequest(HeaderParam p) {
		try {
			// System.out.println("Header:\n"+p);
			String url = "http://qns.vn/All-QuickAndSnow.html";
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();

			// Cài đặt header
			conn.setRequestMethod("POST");
			// conn.setRequestProperty("", "");

			// Gửi request
			// Cái này dùng để ghi data (tham số) cho cả POST và GET
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream());
			String params;
			params = "__LASTFOCUS=&__EVENTTARGET=" + p.EVENTTARGET
					+ "&__EVENTARGUMENT=&__VIEWSTATE=" + p.VIEWSTATE
					+ "&Login1%24txtpassword=&__EVENTVALIDATION="
					+ p.EVENTVALIDATION + "";
			writer.write(params);
			writer.flush();
			writer.close();

			// Đọc response
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuilder content = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null)
				content.append(line + "\n");
			reader.close();

			// Content
			String html = content.toString();

			// System.out.println(html);

			// Bắt các tham số của trang hiện tại
			HeaderParam hp = new HeaderParam();
			Document doc = Jsoup.parse(html);
			Element e;
			e = doc.getElementById("__VIEWSTATE");
			hp.VIEWSTATE = encode(e.attr("value"));
			e = doc.getElementById("__EVENTVALIDATION");
			hp.EVENTVALIDATION = encode(e.attr("value"));
			hp.checkPage6(html);

			// Check trang hien tai
			String pattern = "<span>\\d+</span>";
			String page = "";
			Pattern r = Pattern.compile(pattern);
			Matcher matchs = r.matcher(html);
			while (matchs.find())
				page = matchs.group();
			hp.theLastPage = Integer.parseInt(page.substring(6,
					page.indexOf("</span>")));
			System.out.println("Get page " + hp.theLastPage + " done!");

			// ------------------------ Xử lý dữ liệu nhận được
			// -------------------------
			CollectData.getListShowFromQnSHtml(html);

			return hp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HeaderParam getNextHeaderParam(String html) {
		// Bắt các tham số của trang hiện tại
		HeaderParam hp = new HeaderParam();
		Document doc = Jsoup.parse(html);
		Element e;
		e = doc.getElementById("__VIEWSTATE");
		hp.VIEWSTATE = encode(e.attr("value"));
		e = doc.getElementById("__EVENTVALIDATION");
		hp.EVENTVALIDATION = encode(e.attr("value"));
		hp.checkPage6(html);

		// Check trang hien tai
		String pattern = "<span>\\d+</span>";
		String page = "";
		Pattern r = Pattern.compile(pattern);
		Matcher matchs = r.matcher(html);
		while (matchs.find())
			page = matchs.group();
		hp.theLastPage = Integer.parseInt(page.substring(6,
				page.indexOf("</span>")));
		System.out.println("Get page " + hp.theLastPage + " done!");

		return hp;
	}

	public static String getHtmlFromWithHeaderParam(HeaderParam p) {
		try {
			// System.out.println("Header:\n"+p);
			String url = "http://qns.vn/All-QuickAndSnow.html";
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();

			// Cài đặt header
			conn.setRequestMethod("POST");
			// conn.setRequestProperty("", "");

			// Gửi request
			// Cái này dùng để ghi data (tham số) cho cả POST và GET
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream());
			String params;
			params = "__LASTFOCUS=&__EVENTTARGET=" + p.EVENTTARGET
					+ "&__EVENTARGUMENT=&__VIEWSTATE=" + p.VIEWSTATE
					+ "&Login1%24txtpassword=&__EVENTVALIDATION="
					+ p.EVENTVALIDATION + "";
			writer.write(params);
			writer.flush();
			writer.close();

			// Đọc response
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuilder content = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null)
				content.append(line + "\n");
			reader.close();

			// Content
			String html = content.toString();
			return html;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String GetStringFromURL(String url) {
		try {
			URL conn = new URL(url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.openStream(), "UTF-8"));
			String content = "";
			String line;
			while ((line = reader.readLine()) != null)
				content += line + "\n";
			reader.close();

			return content;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
