package twitter.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;

import domain.Tweet;
import twitter4j.Status;

public class Converter {
	private static Converter converter;
	private static Map<String, Object> mapTweets;
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private Converter() {

	}

	public static Converter getConverter() {
		if (converter != null) {
			return converter = new Converter();
		}
		return converter;
	}

	public static String convertToString(Date date) {
		return sdf.format(date);
	}

	public static Tweet convertToTweet(Status status) {
		Tweet tweet = new Tweet();

		tweet.setStatus(status.getText());
		tweet.setUserTweet(status.getUser().getName());

		return tweet;
	}
	
	public Tweet convertToTweet(DBObject dbObject) {
		Tweet tweet = new Tweet();

		tweet.setIdTweet((String) dbObject.get("_id"));
		tweet.setUserTweet((String) dbObject.get("usuario"));
		tweet.setStatus((String) dbObject.get("tweet"));

		@SuppressWarnings("deprecation")
		Date d = new Date((String) dbObject.get("hora"));
		tweet.setDateTime(d);

		return tweet;
	}

	public static Map<String, Object> convertToMap(Tweet tweet) {
		mapTweets = new HashMap<String, Object>();

		if (tweet.getIdTweet() != null) {
			mapTweets.put("_id", tweet.getIdTweet());
		}

		mapTweets.put("hora", Converter.convertToString(tweet.getDateTime()));
		mapTweets.put("usuario", tweet.getUserTweet());
		mapTweets.put("tweet", tweet.getStatus());

		return mapTweets;

	}

	public static List<Tweet> convertToList(List<DBObject> dbObjects) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		Tweet tweet = null;

		for (DBObject dbObject : dbObjects) {

			tweet = new Tweet();

			tweet.setIdTweet((String) dbObject.get("_id").toString());

			String dateMongo = (String) dbObject.get("hora");
			String[] datee = dateMongo.split(" ");
			String date = datee[0];
			String hour = datee[1];

			String[] splitdate = date.split("/");
			String[] splitHour = hour.split(":");

			@SuppressWarnings("deprecation")
			Date d = new Date(Integer.parseInt(splitdate[2]) - 1900, Integer.parseInt(splitdate[1]) - 1,
					Integer.parseInt(splitdate[0]), Integer.parseInt(splitHour[0]), Integer.parseInt(splitHour[1]),
					Integer.parseInt(splitHour[2]));
			tweet.setDateTime(d);
			tweet.setStatus((String) dbObject.get("tweet"));

			tweets.add(tweet);
		}
		return tweets;
	}

}
