package eus.ehu.bum1_fx;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This class knows the SHORT NAMES of two currencies and it is able to calculate
 * the exchange value of a certain amount of the first one in terms of the other one
 *
 */
public class ForexOperator {

	private String sourceCurrency;
	private double amount;
	private String endCurrency;

	public ForexOperator (String source, double x, String end) {
		sourceCurrency = source;
		amount = x;
		endCurrency = end;
	}

	/**
	 * @return				The exchange value as obtained in an online service
	 * (currencyconvert.online). Yes, it is a sort of piracy, but only intended
	 * to be used as a teaching example.
	 *
	 * @throws Exception	Several exceptions can be raised here, including
	 * URLMalformedException (unlikely), IOException and NumberFormatException
	 * (when the change value cannot be  obtained, e.g. because the currency
	 * is not convertible). Additionally, if anything fails during the connection
	 * and no numerical outcome is obtained, it also raises a generic exception.
	 *
	 */
	public double getChangeValue() throws Exception {

		String urlText = "https://currencyconvert.online/" + sourceCurrency.toLowerCase()
		+ "/" + endCurrency.toLowerCase() + "/" + 
		// remove the .0 when the number is a whole number
		(amount % 1 == 0 ? String.format("%.0f", amount) : String.valueOf(amount));

		OkHttpClient client = new OkHttpClient.Builder()
				.followRedirects(true)
				.build();
				
		Request request = new Request.Builder()
				.url(urlText)
				.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36")
				.build();
				
		try (Response response = client.newCall(request).execute()) {
			String responseBody = response.body().string();
			double sol = -1.0;
			
			for (String line : responseBody.split("\n")) {
				if (line.startsWith("Amount in words")) {
					int pos0 = line.indexOf("? — ") + 4;
					while (line.charAt(pos0) < '0' || line.charAt(pos0) > '9')
						pos0++;
					int pos1 = line.indexOf(' ', pos0);
					sol = Double.parseDouble(line.substring(pos0, pos1));
					break;
				}
			}
			
			if (sol < 0)
				throw new Exception();    // The page has not been downloaded or is wrong
				
			return sol;
		}
	}
}
