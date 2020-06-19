package dev.demeng.demlib.api.connections;

import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class WebUtils {

	private String url;

	public boolean contains(String... strings) throws IOException {

		URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		connection.connect();

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

		StringBuilder sb = new StringBuilder();
		String str1;

		while ((str1 = reader.readLine()) != null) sb.append(str1);

		boolean contains = false;

		for (String s : strings)
			if (sb.toString().contains(s)) {
				contains = true;
				break;
			}

		return contains;
	}
}
