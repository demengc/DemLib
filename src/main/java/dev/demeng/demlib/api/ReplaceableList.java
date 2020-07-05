package dev.demeng.demlib.api;

import java.util.ArrayList;
import java.util.List;

/** An ArrayList that allows you to easily replace stuff. */
public class ReplaceableList extends ArrayList<String> {

  /**
   * Get a ReplaceableList from a normal list.
   *
   * @param list
   * @return
   */
  public static ReplaceableList from(List<String> list) {
    final ReplaceableList finalList = new ReplaceableList();
    finalList.addAll(list);
    return finalList;
  }

  /**
   * Replace a string with another, throughout the entire list.
   *
   * @param toReplace The string to be replaced
   * @param placeholder The string that toReplace will be replaced with
   * @return The replaced list
   */
  public ReplaceableList replace(String toReplace, String placeholder) {
    final ReplaceableList list = new ReplaceableList();
    for (String line : this) list.add(line.replace(toReplace, placeholder));
    return list;
  }
}
