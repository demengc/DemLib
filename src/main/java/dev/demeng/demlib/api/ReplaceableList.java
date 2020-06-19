package dev.demeng.demlib.api;

import java.util.ArrayList;
import java.util.List;

public class ReplaceableList extends ArrayList<String> {

	public static ReplaceableList of(List<String> list) {

		ReplaceableList finalList = new ReplaceableList();
		finalList.addAll(list);

		return finalList;
	}

	public ReplaceableList replace(String toReplace, String placeholder) {

		ReplaceableList list = new ReplaceableList();

		for (String line : this) list.add(line.replace(toReplace, placeholder));

		return list;
	}
}
