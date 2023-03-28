package app;

import java.util.ArrayList;
import java.util.List;

import dao.HotelDao;
import entity.Address;
import entity.Hotel;
import entity.Room;

public class main {
	public static void main(String[] args) {
//		List<Room> rooms = new ArrayList<Room>();
//		List<String> tags = new ArrayList<String>();
//		tags.add("test tag");
//		rooms.add(new Room("test des", "type 1", "test bed", 10, false, tags));
//		Hotel hotel = new Hotel("an4", "an test", "an", 6, new Address("ádsad", "sdasds", "adsda", "ádsd", "ádsds"), rooms);
		HotelDao hotelDao = new HotelDao();
//		System.out.println(hotelDao.addHotel(hotel));
		
//		Room room = new Room("an test 1", "tádasdasd", "đâsd", 3, false, tags);
//		boolean temp = hotelDao.addRoom("an test hotelid", room);
//		System.out.println(temp);
//		hotelDao.getHotels(2);
		
//		List<Hotel> hotels = hotelDao.getHotels(2);
//		hotels.forEach(h -> System.out.println(h));
		System.out.println(hotelDao.deleteHotelById("an4"));
	}
}
