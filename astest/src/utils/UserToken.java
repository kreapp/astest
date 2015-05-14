package utils;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UserToken {

	static CloseableHttpClient client;
	static CloseableHttpResponse response;
	static Base base = new Base();
	public static  String USER_TOKEN;
	int STATUS_CODE = 0;

	public static String getXAuthToken() {

		String sourceurl = base.API_URL + "/user/login";
		client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(sourceurl);
		request.setHeader(HttpHeaders.AUTHORIZATION,
				base.authorizationHeader(base.USER_NAME, base.PASS_WORD));

		try {
			response = client.execute(request);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// System.out.println("Response Code : " +
		// response.getStatusLine().getStatusCode());
		// HttpEntity entity = response.getEntity();
		String responseString = null;
		try {
			responseString = EntityUtils.toString(response.getEntity());
		} catch (org.apache.http.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int statuscode = response.getStatusLine().getStatusCode();
		// System.out.println(statuscode);

		if (statuscode == 200) {
			// System.out.println(responseString);

			try {
				JSONParser parser = new JSONParser();
				// JSONObject jsonObject = (JSONObject)
				// parser.parse(responseString);

				Object resultObject = parser.parse(responseString);

				if (resultObject instanceof JSONArray) {
					JSONArray array = (JSONArray) resultObject;
					for (Object object : array) {
						JSONObject obj = (JSONObject) object;
						// System.out.println(obj.get("apiVersion"));
						// System.out.println(obj.get("responseId"));
					}

				} else if (resultObject instanceof JSONObject) {
					JSONObject obj = (JSONObject) resultObject;
					// System.out.println(obj.get("apiVersion"));
					// System.out.println(obj.get("responseId"));

					Object dataobject = obj.get("data");
					if (dataobject instanceof JSONObject) {
						JSONObject obj1 = (JSONObject) dataobject;
						Object items = obj1.get("items");
						if (items instanceof JSONArray) {
							JSONArray itemsarray = (JSONArray) items;
							for (Object itemObject : itemsarray) {
								JSONObject obj3 = (JSONObject) itemObject;

								// System.out.println(obj3.get("id"));
								USER_TOKEN = obj3.get("token").toString();
								// System.out.println(USER_TOKEN);
							}
						}
					}
				}

			} catch (Exception e) {
			}
		}
		return USER_TOKEN;
	}

}
