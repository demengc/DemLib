package dev.demeng.demlib.serializer;

import java.util.*;

public class ListSerializer {

  public static String serialize(Collection<String> list) {

    if (list.isEmpty()) {
      return "[]";
    }

    final StringBuilder sb = new StringBuilder("[");
    int fakeIndex = -1;

    for (String s : list) {

      fakeIndex++;

      if (fakeIndex == 0) {
        sb.append(s);
      } else {
        sb.append(", ").append(s);
      }
    }

    sb.append("]");
    return sb.toString();
  }

  public static ArrayList<String> deserialize(String string) {

    if (string.equals("[]")) {
      return new ArrayList<>();
    }

    return new ArrayList<>(Arrays.asList(string.substring(1, string.length() - 1).split(", ")));
  }

  public static HashSet<String> deserializeToSet(String string) {

    if (string.equals("[]")) {
      return new HashSet<>();
    }

    return new HashSet<>(Arrays.asList(string.substring(1, string.length() - 1).split(", ")));
  }

  public static LinkedHashSet<String> deserializeToLinkedSet(String string) {

    if (string.equals("[]")) {
      return new LinkedHashSet<>();
    }

    return new LinkedHashSet<>(Arrays.asList(string.substring(1, string.length() - 1).split(", ")));
  }
}
