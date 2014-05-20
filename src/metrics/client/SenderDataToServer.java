package metrics.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.swing.JScrollBar;

public class SenderDataToServer {

	public void send() {
		try {
			URL url = new URL("http://localhost:8080/metrics/rest/post");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			
			 JsonObject value = Json.createObjectBuilder()
			     .add("token", "tgoqGiWBsr")
			     .add("events", Json.createArrayBuilder()
			         .add(Json.createObjectBuilder()
			             .add("name", "event1")
			             .add("properties", Json.createArrayBuilder()
			            	.add(Json.createObjectBuilder()
			            		 .add("name", "pr1")
			            		 .add("value", "frferg"))))
			      .add(Json.createObjectBuilder()
			    	  .add("name", "event2")
			    	     .add("properties", Json.createArrayBuilder()
			    	        .add(Json.createObjectBuilder()
			    			      .add("name", "pr2")
			    			      .add("value", "frferg")))))
			     .build();
			
			System.out.println(value.toString()); 
			 
			OutputStream os = conn.getOutputStream();
			os.write(value.toString().getBytes());
			os.flush();

			if (conn.getResponseCode() == 404) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static void main(String[] args) {
		SenderDataToServer toServer = new SenderDataToServer();
		toServer.send();
	}
}
