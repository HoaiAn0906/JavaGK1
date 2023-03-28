package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.reactivestreams.Publisher;

import com.google.gson.Gson;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.AggregatePublisher;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

import db.Connection;
import entity.Hotel;
import entity.Room;

public class HotelDao {
	private MongoCollection<Document> collection;
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	
	public HotelDao() {
		mongoClient = Connection.getInstance().getMongoClient();
		mongoDatabase = mongoClient.getDatabase("An20012781");
		
		collection = mongoDatabase.getCollection("hotel");
	}
	
	public boolean addHotel(Hotel hotel) {
		Gson gson = new Gson();
		String jsonHotel = gson.toJson(hotel);
		Document doc = Document.parse(jsonHotel);
		
		Publisher<InsertOneResult> publisher = collection.insertOne(doc);
		HotelSubcriber<InsertOneResult> subcriber = new HotelSubcriber<InsertOneResult>();
		publisher.subscribe(subcriber);
		
		return subcriber.getSingleResult().getInsertedId() != null;
	}
	
	//add room by id hotel (không trùng)
	public boolean addRoom(String hotelId, Room newRoom) {
		Gson gson = new Gson();
		String json = gson.toJson(newRoom);
		Publisher<UpdateResult> publisher = collection.updateOne(Filters.eq("_id", hotelId), Document.parse("{$pull: {rooms: "+ json +"}}"));
		HotelSubcriber<UpdateResult> hotelSubcriber = new HotelSubcriber<UpdateResult>();
		publisher.subscribe(hotelSubcriber);
		
		return hotelSubcriber.getSingleResult().getModifiedCount() > 0;
	}
	
	//db.hotel.aggregate([{$group: {_id: null, avgRating: { $avg: "$rating"}}}])
	//db.hotel.aggregate([{$match: {"rating": {$gt: 3.4}}},{$sample: { size : 1}}])
	public List<Hotel> getHotels(int n) {
		Gson gson = new Gson();
		AggregatePublisher<Document> publisher = collection.aggregate(Arrays.asList(
				Document.parse("{$group: {_id: null, avgRating: { $avg: \"$rating\"}}}")
				));
		HotelSubcriber<Document> subcriber = new HotelSubcriber<Document>();
		publisher.subscribe(subcriber);
		
		double avgRating = subcriber.getSingleResult().getDouble("avgRating");
		
		AggregatePublisher<Document> publisher1 = collection.aggregate(Arrays.asList(
				Document.parse("{$match: {\"rating\": {$gt: "+avgRating+"}}}"),
				Document.parse("{$sample: { size : "+n+"}}")
				));
		HotelSubcriber<Document> subcriber1 = new HotelSubcriber<Document>();
		publisher1.subscribe(subcriber1);
		
		List<Hotel> listHotel = new ArrayList<Hotel>();
		
		for(Document doc : subcriber1.getResults()) {
			Hotel hotel = gson.fromJson(doc.toJson(), Hotel.class);
			listHotel.add(hotel);
		}
	
		return listHotel;
	}
	
	//delete hotel by id
	public boolean deleteHotelById(String id) {
		Publisher<DeleteResult> publisher = collection.deleteOne(Document.parse("{_id : '"+id+"'}"));
		HotelSubcriber<DeleteResult> subcriber = new HotelSubcriber<>();
		publisher.subscribe(subcriber);
		
		return subcriber.getSingleResult().getDeletedCount() > 0;
	}
	
	//update rating lên 3 của nhưng khách sạn có seelpsCount: 3
	
}
