import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpURLConnectionATM
{	
	// Stores PHP response
	StringBuffer response = null;

	// the URL path for your php folder
	final String url = "nickatm";

	// HTTP POST request
	public int sendPost(String url, String params) throws Exception 
	{ 
		URL obj = new URL(url);
		java.net.HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(params);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + params);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return responseCode;
	}
}