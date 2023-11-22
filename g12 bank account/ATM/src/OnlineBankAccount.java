import org.json.JSONException;
import org.json.JSONObject;

public class OnlineBankAccount extends BankAccount{

	OnlineBankAccount (int acctNum, double bal, String fName, String lName, String pswd, String log){
		super (acctNum, bal, fName, lName, pswd, log);
	}

	@Override
	public boolean deposit (double amt) {
		try {
			HttpURLConnectionATM http = new HttpURLConnectionATM();

			super.deposit(amt);

			String param1 = http.url + "deposit.php?";
			String param2 = "acctNum=" + acctNum + "&balance="+ balance + "&log="+log;

			http.sendPost(param1, param2);

			String response = http.response.toString().trim();

			if (http.response != null) {
				if (response.equals("deposit-success")) {
					//System.out.println(response);
					return true;
				} else if (response.equals("deposit-fail")) {
					//System.out.println(response);
					return false;
				}
			}
		} catch (Exception e) {e.printStackTrace(); }

		return false;
	}


	@Override
	public boolean withdraw (double amt) {
		try {
			HttpURLConnectionATM http = new HttpURLConnectionATM();

			super.withdraw(amt);

			String param1 = http.url + "withdraw.php?";
			String param2 = "acctNum=" + acctNum + "&balance="+ balance + "&log="+log;

			http.sendPost(param1, param2);

			String response = http.response.toString().trim();

			if (http.response != null) {
				if (response.equals("withdraw-success")) {
					//System.out.println(response);
					return true;
				} else if (response.equals("withdraw-fail")) {
					//System.out.println(response);
					return false;
				}
			}
		} catch (Exception e) {e.printStackTrace(); }

		return false;
	}

	@Override
	public boolean transferTo (double amt, OnlineBankAccount target) {
		try {
			HttpURLConnectionATM http = new HttpURLConnectionATM();

			super.transferTo(amt, target);

			String param1 = http.url + "transferTo.php?";
			String param2 = "acctNum=" + acctNum + "&balance="+ balance + "&log="+log;

			http.sendPost(param1, param2);

			String response = http.response.toString().trim();

			if (http.response != null) {
				if (response.equals("transfer-success")) {
					//System.out.println(response);
					target.deposit(0);
					return true;
				} else if (response.equals("transfer-fail")) {
					//System.out.println(response);
					return false;
				}
			}
		} catch (Exception e) {e.printStackTrace(); }

		return false;
	}
	


}




