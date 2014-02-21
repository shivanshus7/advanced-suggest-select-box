package eu.nextstreet.gwt.components.client.ui.widget.util;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StringUtil {

	/**
	 * removes the text occurences and leaves only the indexes when they start and the value of the text until it starts (eventually between the last occurence
	 * and the current one) The last piece of text is not present
	 * 
	 * @param toAnalyse
	 * @param toFind
	 * @return
	 */
	public static Map<Integer, String> extractTextBetweenOccurences(String toAnalyse, String toFind, boolean caseSensitive) {
		if (isEmptyOrNull(toFind))
			return null;
		Map<Integer, String> toReturn = new TreeMap<Integer, String>();

		int index = -1;
		int lastIndex = 0;
		String toScan = caseSensitive ? toAnalyse : toAnalyse.toLowerCase();
		String sample = caseSensitive ? toFind : toFind.toLowerCase();
		while ((index = toScan.indexOf(sample, lastIndex)) != -1) {
			toReturn.put(index, toAnalyse.substring(lastIndex, index));
			lastIndex = index + toFind.length();
		}

		// if (lastIndex < toAnalyse.length() - 1)
		// toReturn.pu(toAnalyse.substring(lastIndex, toAnalyse.length()));

		return toReturn;
	}

	public static boolean isEmptyOrNull(String toFind) {
		return toFind == null || toFind.length() == 0;
	}

	public static StringBuilder replaceBySurrounding(String toAnalyse, String toFind, String prefix, String suffix, boolean caseSensitive) {
		String toScan = AsciiUtils.convertNonAscii(toAnalyse);
		Map<Integer, String> occurences = extractTextBetweenOccurences(toScan, toFind, false);
		Set<Integer> keySet = occurences.keySet();
		StringBuilder builder = new StringBuilder();
		int lastIndex = 0;
		int findSequenceLength = toFind.length();
		for (Integer index : keySet) {
			builder.append(occurences.get(index));
			builder.append(prefix);
			lastIndex = index + findSequenceLength;
			builder.append(toAnalyse.substring(index, lastIndex));
			builder.append(suffix);
		}
		builder.append(toAnalyse.substring(lastIndex));
		return builder;
	}

	public static void main(String[] args) {
		String toAnalyse = AsciiUtils.convertNonAscii("aBcDéàxYzèâ1234êä56");
		System.out.println(StringUtil.extractTextBetweenOccurences(toAnalyse, "Ea", false));
		System.out.println(StringUtil.replaceBySurrounding("aBcDéàxôDYzèâ12oD34êä56", "Ea", "(", ")", false));
		System.out.println(StringUtil.replaceBySurrounding("aBcDéàxôïYzèâ12Oî34êä56", "oi", "(", ")", false));
		System.out.println(StringUtil.extractTextBetweenOccurences("aBcDxYz123456", "Ea", false));
		System.out.println(StringUtil.replaceBySurrounding("aBcDxYz123456", "Ea", "(", ")", false));
	}

}
