package com.demeng7215.demlib.api;

import com.demeng7215.demlib.DemLib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class BlacklistSystem {

	public static boolean checkBlacklist() throws IOException {

		URLConnection connection = new URL("https://demeng7215.com/antipiracy/blacklist.txt").openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		connection.connect();

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

		StringBuilder sb = new StringBuilder();
		String str1;

		while ((str1 = reader.readLine()) != null) sb.append(str1);

		return sb.toString().contains(String.valueOf(DemLib.getPluginId()));
	}
}
