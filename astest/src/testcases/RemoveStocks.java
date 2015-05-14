package testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import utils.Base;
import utils.UserToken;

public class RemoveStocks {
  @Test
  public void removeStocks() {
	
	
			System.out.println("*********************************************");
			String baseURL = Base.API_URL + "/mystocks/remove";

			HttpClient client = new DefaultHttpClient();
			HttpPost postReq = new HttpPost(baseURL);
			postReq.setHeader("X-Auth-Token", UserToken.getXAuthToken());

			try {
				List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
				urlParameters.add(new BasicNameValuePair("stocks[0][stock_id]", "12"));
				postReq.setEntity(new UrlEncodedFormEntity(urlParameters));
				HttpResponse response = client.execute(postReq);
				HttpEntity resEntity = response.getEntity();
				int STATUS = response.getStatusLine().getStatusCode();
				if (resEntity != null & STATUS == 200) {
					String responsebody = EntityUtils.toString(resEntity);
					System.out.println("HTTP RESPOSE:" + response.getStatusLine());
					System.out.println("RESPONSE LENGTH: " + resEntity.getContentLength());
					System.out.println("Chunked? " + resEntity.isChunked());
					System.out.println("-------------------------------------------");
					System.out.println(STATUS);
					System.out.println("JSON DATA:" + responsebody);
					System.out.println("-------------------------------------------");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("*********************************************");
		}

}
