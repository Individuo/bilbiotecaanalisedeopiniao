package twitter;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBObject;

import domain.Tweet;
import miner.Miner;
import persistence.mongodb.EntityDao;
import twitter.crawler.FilterQuery;
import twitter.crawler.TweetListener;
import twitter.util.Converter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class ManagerTwitter {

	private TwitterStream twitterStream;

	public ManagerTwitter() {
		twitterStream = new TwitterStreamFactory().getInstance();
	}

	public void execute(List<String> filters, String collection) {
		FilterQuery filter = new FilterQuery();

		/*
		 * filter.addFilter("cblol"); filter.addFilter("pain gaming");
		 * filter.addFilter("intz"); filter.addFilter("keyd"); filter.addFilter(
		 * "kabum black"); filter.addFilter("kabum");
		 * filter.addFilter("gokabum"); filter.addFilter("gokeyd");
		 * filter.addFilter("goINTZ"); filter.addFilter("KBB");
		 * filter.addFilter("vamoCNB"); filter.addFilter("g3nerationX");
		 * filter.addFilter("g3x");
		 */
		for(String f : filters) {
			filter.addFilter(f);
		}

		filter.addFilterQuery();
		TweetListener listener = new TweetListener();
		listener.setCollection(collection);

		twitterStream.addListener(listener);
		twitterStream.filter(filter.getFilterQuery());
	}

	public void clearStopWords(String collection) {

		EntityDao<Tweet> dao = new EntityDao<Tweet>(collection);
		List<DBObject> tweets = dao.findAll();
		List<Tweet> tweetsList = Converter.convertToList(tweets);
		Tweet tweet;

		System.out.println("Total Tweets: " + tweetsList.size());

		List<Tweet> tweetsLimpos = new ArrayList<Tweet>();

		for (Tweet statusString : tweetsList) {

			tweet = new Tweet();
			tweet.setIdTweet(statusString.getIdTweet());
			tweet.setUserTweet(statusString.getUserTweet());
			tweet.setDateTime(statusString.getDateTime());
			tweet.setStatus(Miner.process(statusString.getStatus()));

			tweetsLimpos.add(tweet);
		}

		System.out.println("Clean " + tweetsLimpos.size());

		for (int i = 0; i < tweetsLimpos.size(); i++) {
			dao.save(Converter.convertToMap(tweetsLimpos.get(i)), collection + "Limpo");
		}

		System.out.println("Tweets Clean...");
	}

}
