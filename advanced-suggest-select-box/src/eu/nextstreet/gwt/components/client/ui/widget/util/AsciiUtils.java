package eu.nextstreet.gwt.components.client.ui.widget.util;

public class AsciiUtils {

	// @formatter:off
	/**
	 * Contains a list of all the characters that map one to one for UNICODE.
	 */
	private static final String PLAIN_ASCII = "AaEeIiOoUu" // grave
			+ "AaEeIiOoUuYy" // acute
			+ "AaEeIiOoUuYy" // circumflex
			+ "AaEeIiOoUuYy" // tilde
			+ "AaEeIiOoUuYy" // umlaut
			+ "CcDdEeEeNnOoRrSsTtUuZz" // umlaut
			+ "Aa" // ring
			+ "Cc" // cedilla
			+ "Nn" // n tilde (spanish)
	;

	/**
	 * Actual accented values, corresponds one to one with ASCII
	 */
	private static final String UNICODE = "\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9"
			+ "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD" 
			+ "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177"
			+ "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177" 
			+ "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF"
			+ "\u010C\u010D\u010E\u010F\u0114\u0115\u011A\u011B\u0147\u0148\u014E\u014F\u0158\u0159\u0160\u0161\u0164\u0165\u016E\u016F\u017D\u017E" 
			+ "\u00C5\u00E5" 
			+ "\u00C7\u00E7" 
			+ "\u00D1\u00F1";
// @formatter:on
	// private constructor, can't be instanciated!
	private AsciiUtils() {
	}

	/**
	 * Removes accentued from a string and replace with ascii equivalent
	 * 
	 * @param toConvert
	 *          The string to englishify
	 * @return The string without the french and spanish stuff.
	 */
	public static String convertNonAscii(String toConvert) {

		StringBuilder builder = new StringBuilder();

		int size = toConvert.length();
		for (int i = 0; i < size; i++) {
			char currentChar = toConvert.charAt(i);
			int pos = UNICODE.indexOf(currentChar);
			if (pos > -1) {
				builder.append(PLAIN_ASCII.charAt(pos));
			} else {
				builder.append(currentChar);
			}
		}

		return builder.toString();
	}

	public static void main(String[] args) {
		String str = "Replace (czech) čČ Ďď Ĕĕ Ěě Ňň Ŏŏ Řř Šš Ťť Ůů Žž | other: éàçè";
		System.out.println(convertNonAscii(str));
	}

}