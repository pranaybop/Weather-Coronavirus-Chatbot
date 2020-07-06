import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CoronaService {

	private static final String endpoint = "http://coronavirus-tracker-api.herokuapp.com/v2/locations?country_code=%s";

	public static CoronaData getCorona(String location) {
		try {
			URL url = new URL(String.format(endpoint, location));
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("GET");
			System.out.println("Successful");

			BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
			StringBuilder result = new StringBuilder();

			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
				System.out.println(line);
			}
			reader.close();

			return parseJSON(result.toString());

		} catch (Exception e) {
			System.out.println("Failed to fetch Coronavirus Data");
		}
		return null;
	}

	private static CoronaData parseJSON(String json) {
		// Parse entire JSON string and convert to object
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();

		// Get the object under the "latest" and "locations" key
		JsonObject latest = object.getAsJsonObject("latest");
		JsonArray locations = object.getAsJsonArray("locations");

		// Get the temperatures from "main" object
		int confirmed = latest.get("confirmed").getAsInt();
		int deaths = latest.get("deaths").getAsInt();

		JsonObject arrayObject = (JsonObject) locations.get(0);
		int countrypop = arrayObject.get("country_population").getAsInt();
		

		// Get the location name from the root object
		String location = arrayObject.get("country").getAsString();
		System.out.println(location);

		// Return fetched data as a CoronaData object
		return new CoronaData(confirmed, deaths, countrypop, location);
	}

}
