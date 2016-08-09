package persistence.mongodb;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class MongoConnection {
	// private static final String host="189.1.162.193";
	private static final String HOST = "localhost";
	private static final int PORT = 27017;
	private static final String DB_NAME = "bd";

	private static MongoConnection uniqInstance;

	private Mongo mongo;
	private DB db;

	private MongoConnection() {

	}

	// criando o padrão Singleton
	public static synchronized MongoConnection getInstance() {
		if (uniqInstance == null) {
			uniqInstance = new MongoConnection();
		}
		return uniqInstance;
	}

	public DB getDB() {
		if (mongo == null) {
			try {
				mongo = new Mongo(HOST, PORT);
				db = mongo.getDB(DB_NAME);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return db;
	}

}
