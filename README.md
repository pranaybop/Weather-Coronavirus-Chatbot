# Weather-Coronavirus-Chatbot

Libraries used: 
PircBot library for IRC communications.
Google GSON library for JSON parsing
Open Weather Map API
Coronavirus Tracker REST API v2.0

The bot will connect to #testchannel using the nickname PranayChatbot

It can handle commands in the form of weather {location/zipcode}, {location/zipcode} weather or temperature {location/zipcode}, {location/zipcode} temperature. 
 
City name can only be a single word. If you want to use "New York" then instead put "New,York"

It can also has basic sentence recognition. If a message contains the word "weather" and also contains a zip code, it will return the weather for that zip code.

If a location is unable to be determined, it will default to checking the weather in Plano.

It can handle commands for coronavirus with coronavirus {two letter country code(US)}, {two letter country code(US)} coronavirus or statistics {two letter country code(US)}, {two letter country code(US)} statistics.

If a country is unable to be determined, it will default to checking the statistics for the US. 
