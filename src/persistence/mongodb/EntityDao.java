package persistence.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class EntityDao<T> implements IDao {
	
	private DBCollection dbCollection;
	
	public EntityDao(){
	}
	
	public EntityDao(String collectionName){
		this.dbCollection = MongoConnection.getInstance().getDB().getCollection(collectionName);

	}
	
	public void setDbCollection(String collectionName) {
		this.dbCollection = MongoConnection.getInstance().getDB().getCollection(collectionName);
	}
	
	protected DBCollection getDbCollection(){
		return dbCollection;
	}
	
	@Override
	public void save(Map<String, Object> mapEntity, String collectionName) {
		this.dbCollection = MongoConnection.getInstance().getDB().getCollection(collectionName);
		BasicDBObject document = new BasicDBObject(mapEntity);
		dbCollection.save(document);
	}

	@Override
	public void save(Map<String, Object> mapEntity) {
		BasicDBObject document = new BasicDBObject(mapEntity);
		dbCollection.save(document);
	}

	@Override
	public DBObject findOne(Object objId) {
		
		DBCursor cursor = (DBCursor) dbCollection.findOne(objId);
		
		while(cursor.hasNext()){
			System.out.println("returnou algo...");
		}
		return cursor.next();
	}

	@Override
	public List<DBObject> findAll() {
		List<DBObject> list = new ArrayList<DBObject>();
		DBCursor cursor = dbCollection.find();
		
		while(cursor.hasNext()){
			list.add(cursor.next());
			//System.out.println(cursor.curr().get("_id"));
		}
		
		return list;
	}
	
	// Fazer conversor de DBCollection para List
	@Override
	public DBCollection getCollection(String collection) {
		return dbCollection.getCollection(collection);
	}
	
	@Override
	public DBCollection getCollection() {
		return dbCollection.getCollection(dbCollection.getName());
	}
	
}
