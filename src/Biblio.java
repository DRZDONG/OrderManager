import java.math.BigDecimal;

public class Biblio {
	/**
	 * parse string number to integer number
	 * @param string
	 * @return integer
	 * @throws NumberFormatException
	 */
	public static int parseNum(String s) throws NumberFormatException {
		return Integer.parseInt(s);
	}
	
	/**
	 * determine whether SKU is valid
	 * @param SKU
	 * @return if SKU is valid
	 */
	public static boolean isSKU(String s) {
		if (s.length() != 12)	return false;
		int i = 0;
		while (i < s.length()) {
			char c = s.charAt(i);
			if ((i == 0 || i == 1) && !Character.isUpperCase(c)) return false;
			if ((i == 2 || i == 9) && c != '-')	return false;
			if ((i >= 3 && i <= 8) && !Character.isDigit(c))	return false;
			if ((i == 10 || i == 11) && (!Character.isUpperCase(c) && !Character.isDigit(c)))	return false;
			i ++;
		}
		return true;
	}
	
	/**
	 * parse String price to BigDecimal price
	 * @param price
	 * @return integer format of price
	 */
	public static BigDecimal parsePrice(String s) {
		return BigDecimal.valueOf(Double.valueOf(s));
	}
	
	/**
	 * check validation of the address
	 * @param country
	 * @param state
	 * @return if state is valid
	 */
	public static boolean isValidState(String country, String state) {		
		String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA",
				"ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI",
				"SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
		if (country.equals("USA")) {
			for (String s : states) {
				if (s.equals(state))	return true;
			}
			return false;
		}
		return true;
	}
}
