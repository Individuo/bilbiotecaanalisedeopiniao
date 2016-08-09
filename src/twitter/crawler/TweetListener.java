package twitter.crawler;

import java.util.Map;

import domain.Tweet;
import persistence.mongodb.EntityDao;
import twitter.util.Converter;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class TweetListener implements StatusListener {

	private Map<String, Object> mapTweet;
	private String collection;

	public TweetListener() {
		super();
	}

	@Override
	public void onStatus(Status status) {
		// Mandar para salvar no Mongo
		Tweet tweet = Converter.convertToTweet(status);

		this.mapTweet = Converter.convertToMap(tweet);
		EntityDao<Tweet> dao = new EntityDao<>(null);
		dao.setDbCollection(getCollection());
		dao.save(this.mapTweet);

		System.out.println(status.getText());
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getCollection() {
		return collection;
	}

	// Não implementar os métodos abaixo
	@Override
	public void onException(Exception arg0) {
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
	}

	@Override
	public void onStallWarning(StallWarning arg0) {
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
	}

}
