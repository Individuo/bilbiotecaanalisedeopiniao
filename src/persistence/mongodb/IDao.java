package persistence.mongodb;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import java.util.List;
import java.util.Map;

public interface IDao {

	public void save(Map<String,Object> mapEntity, String collectionName);
	
	public DBObject findOne(Object objId);
	
	public List<DBObject> findAll();
	
	public DBCollection getCollection();

	public void save(Map<String, Object> mapEntity);

	public DBCollection getCollection(String collection);
}
