import org.jibble.pircbot.PircBot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * IRC bot class, using the PircBot library, licenced under the GNU General Public License.
 * This is the main class.
 */
public class MyBotMain extends PircBot {

    // IRC server to connect to
    static final String server = "irc.freenode.net";

    // IRC channel to join
    static final String channel = "#testchannel";

    // Name of bot
    static final String name = "PranayChatBot";

    // Default location
    static final String defaultLocation = "75081";
    //Default country
    static final String defaultCountry = "US";
    // Regular expression to find a 5-digit number (i.e. find zip code)
    static final Pattern regex = Pattern.compile("(\\d{5})");

    public void WeatherBot() {
        setName(name);
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        message = message.toLowerCase();

        // If someone mentions the word "weather" or the word "temperature"
        if (message.contains("weather") | message.contains("temperature")) {
            // If unable to determine location, will use 75080 (Richardson) as default
            String location = defaultLocation + ",us";

            // Split message into separate words
            String[] words = message.split(" ");
            Matcher zipcode = regex.matcher(message);
            // If message is 2 words long (looking for "weather {location}"
            if (words.length == 2) {
                // If they say "weather/temperature {location}" then location = second word.
                // If they say "{location} weather/temperature" then location = first word
                if (words[0].equals("weather") | words[0].equals("temperature")) {
                    location = words[1];
                    if(zipcode.find()) {
                    	location = words[1] + ",us";
                    }
                    
                } else {
                    location = words[0];
                    if(zipcode.find()) {
                    	location = words[0] + ",us";
                    }
                }
                
            // The message is longer than 2 words (e.g. a sentence)
            } else {
                Matcher matcher = regex.matcher(message);
                // Try to find a 5 digit number or city in the message
                if (matcher.find()) {
                    // Let the location be number (the zip code)
                    location = matcher.group(1) + ",us";
                    System.out.println(location);
                } 
                else {
                    // If no zip code is detected, tell the user that we are assuming richardson
                    sendMessage(channel, "Unable to determine location. Assuming Richardson.");
                }
            }

            WeatherData data = WeatherService.getWeather(location);
            // If the request failed
            if (data == null) {
                // Try getting weather for the default location instead
                sendMessage(channel, "Unable to fetch weather data for " + location + ". Trying " + defaultLocation + " instead.");
                data = WeatherService.getWeather(defaultLocation + ",us");
                // If the request fails a second time
                if (data == null) {
                    sendMessage(channel, "Sorry, there is an error with the weather API.");
                }
            }
            // Convert weather data to a string
            String weather = data.toString();
            // Output weather message
            sendMessage(channel, weather);
        }
        
        if(message.contains("coronavirus") | (message.contains("statistics"))) {
        	//If unable to determine country
        	String country = defaultCountry;
        	
        	// Split message into separate words
            String[] words = message.split(" ");

            // If message is 2 words long (looking for "weather {location}"
            if (words.length == 2) {
                // If they say "weather {location}" then location = second word.
                // If they say "{location} weather" then location = first word
                if (words[0].equalsIgnoreCase("coronavirus") | words[0].equalsIgnoreCase("statistics")) {
                    country = words[1];
                } else {
                    country = words[0];
                }
                System.out.println(country);
        	
        }
            
     
            CoronaData data1 = CoronaService.getCorona(country);
            
            //If request failed
            if (data1 == null) {
                //Try getting weather for the default location instead
                sendMessage(channel, "Unable to fetch coronavirus data for " + country + ". Trying " + defaultCountry + " instead.");
                data1 = CoronaService.getCorona(defaultCountry);
                // If the request fails a second time
                if (data1 == null) {
                    sendMessage(channel, "Sorry, there is an error with the Coronavirus API.");
                }
            }
            // Convert corona data to a string
            String corona = data1.toString();
            // Output weather message
            sendMessage(channel, corona);
        }
        
        if(message.contains("quitbot")) {
        	sendMessage(channel, "Bye!");
        	quitServer();
        }
        if(message.contains("help")) {
        	
        	sendMessage(channel, "Enter the keywords weather or temperature either followed or preceded by a city or zipcode to receive weather data.");
        	sendMessage(channel, "Enter the keywords coronavirus or statistics followed or preceded by a countries two letter identification to receive coronavirus data.");
        }
            
        if((!message.contains("weather") && (!message.contains("temperature") && (!message.contains("coronavirus") && (!message.contains("statistics") && (!message.contains("help") && (!message.contains("quitbot")))))))) {
        	
        	sendMessage(channel, "Sorry, I do not understand");
        	sendMessage(channel, "Please try entering the keywords weather or temperature followed or preceded by a zip code or city for their respective weathers");
        	sendMessage(channel, "You can also enter the keywords coronavirus or statistics followed or preceded by a two letter country identifier to receive coronavirus statistics");
        	
        	
        }
        
    }

    public static void main(String[] args) {

        // Instantiate weather bot
        MyBotMain bot = new MyBotMain();
        bot.setVerbose(true);
        bot.setName(name);
        
        try {
            // Attempt to connect to the server
            bot.connect(server);
        } catch (Exception e) {
            // Could not connect
            System.out.printf("Failed to connect to %s\n", server);
        } finally {
            // If we connected, the join the server
            bot.joinChannel(channel);
            bot.sendMessage(channel, "Hi, I am a chatbot created by Pranay Boppana");
        }
    }

}
