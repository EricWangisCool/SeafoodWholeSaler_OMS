package tw.com.ispan.eeit48.common.util;

import java.util.Comparator;
import java.util.Map;

public class SortComparator implements Comparator<Map<String, Object>> {

	private String sortItem;
	private String sortType;
	private String sortDire;

	public SortComparator(String sortItem, String sortType, String sortDire) {
		this.sortItem = sortItem;
		this.sortType = sortType;
		this.sortDire = sortDire;
	}

	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		int value1 = (int) o1.get(sortItem);
		int value2 = (int) o2.get(sortItem);
		if ("int".equalsIgnoreCase(this.sortType)) { // int sort
			int int1 = value1;
			int int2 = value2;
			if ("asc".equalsIgnoreCase(this.sortDire)) {
				return int1 - int2;
			} else if ("desc".equalsIgnoreCase(this.sortDire)) {
				return int2 - int1;
			} else {
				return 0;
			}
		} else { // nothing sort
			return 0;
		}
	}
}
