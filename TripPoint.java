import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TripPoint {
	private double lat;
	private double lon;
	private int time; // minutes
	private static ArrayList<TripPoint> trip;
	
	public TripPoint(int time, double lat, double lon) {
		this.time = time;
		this.lat = lat;
		this.lon = lon;
	}
	
	public int getTime() {
		return time;
	}
	
	public double getLat() {
		return lat;
	}
	
	public double getLon() {
		return lon;
	}
	
	public static ArrayList<TripPoint> getTrip() {
		return trip;
	}
	
	private static double haversine(double angle) {
		return Math.pow(Math.sin(angle / 2), 2);
	}
	
	public static double haversineDistance(TripPoint a, TripPoint b) {
		double radius = 6371;
		double lat1 = a.lat * Math.PI / 180;
		double lat2 = b.lat * Math.PI / 180;
		double lon1 = a.lon * Math.PI / 180;
		double lon2 = b.lon * Math.PI / 180;
		
		double dLat = lat2 - lat1;
		double dLon = lon2 - lon1;
		
		double haversineFunc = haversine(dLat) + Math.cos(lat1) * Math.cos(lat2) * haversine(dLon);
		double dist = 2 * radius * Math.asin(Math.sqrt(haversineFunc));
		
		return dist;
	}
	
	public static double avgSpeed(TripPoint a, TripPoint b) { // km/hr
		double dt = (double) (b.time - a.time);
		double dist = haversineDistance(a,b);
		
		return Math.abs(dist / dt * 60);
	}
	
	public static double totalTime() { // return time in hours
		return ((double) trip.get(trip.size()-1).time) / 60;
	}
	
	public static double totalDistance() {
		double dist = 0;
		for(int i = 0; i < trip.size() - 1; i++) {
			dist += haversineDistance(trip.get(i), trip.get(i+1));
		}
		return dist;
	}
	
	public static void readFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			trip = new ArrayList<TripPoint>();
			
			while((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				int time = Integer.parseInt(data[0]);
				double lat = Double.parseDouble(data[1]);
				double lon = Double.parseDouble(data[2]);
				
				trip.add(new TripPoint(time, lat, lon));
				System.out.println(line);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
