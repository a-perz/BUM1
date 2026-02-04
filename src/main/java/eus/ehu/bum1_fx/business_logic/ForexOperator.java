package eus.ehu.bum1_fx.business_logic;

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

		//build URL 
		String urlText = "https://currencyconvert.online/" //default
			+ sourceCurrency.toLowerCase() 				// https://currencyconvert.online/EUR
			+ "/" + endCurrency.toLowerCase() 			// https://currencyconvert.online/EUR/USD
			+ "/" 										// https://currencyconvert.online/EUR/USD/
			// remove the .0 when it's whole number
			+ (amount % 1 == 0 
				? String.format("%.0f", amount) 
				: String.valueOf(amount) 				// https://currencyconvert.online/EUR/USD/10
			);

		//create a HTTP client
		OkHttpClient client = new OkHttpClient.Builder() //connects to the internet
				.followRedirects(true) //if the websites redirects me -> follow automatically
				.build(); //done configuring, create object
				
		//create the HTTP request
		Request request = new Request.Builder()
				.url(urlText) //URL= https://currencyconvert.online/EUR/USD/10
				.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36") //for the Java program to deisguise (i'm a normal browser, don't block me)
				.build(); //done configuring, create object		
				
		//send the request & get response
		try (Response response = client.newCall(request).execute()) { 
			//opens a connection -> sends HTTP request -> waits for the server -> receives response -> automatically closes connection
			String responseBody = response.body().string(); //read the web page as plain text (responseBody = entire HTML)
			double sol = -1.0; //if page !downloaded / is wrong
			
			//search for the line with the number (conversion)  EXAMPLE: Amount in words? — approximately 123.45 US dollars
			for (String line : responseBody.split("\n")) { //split HTML into lines (cut big string into smaller strings every time there's a new line ("\n") )
				
				if (line.startsWith("Amount in words")) { //look for the line that starts with "Amount in words"
					
					int pos0 = line.indexOf("? — ") + 4; //find approximate staring point: searches for the first occurrence of "? - " & skips it (+4)
						
						while (line.charAt(pos0) < '0' || line.charAt(pos0) > '9') //skip non-numeric chars
							pos0++;
						//pos0 => first digit of the number 
					int pos1 = line.indexOf(' ', pos0); //find wehre the number ends (starts from pos0 & finds the first space (' ') )
					sol = Double.parseDouble(line.substring(pos0, pos1)); //extract and convert the number to double
					break;
				}
			}
			
			if (sol < 0)
				throw new Exception();    // The page has not been downloaded or is wrong
				
			return sol;
		}
	}
}
