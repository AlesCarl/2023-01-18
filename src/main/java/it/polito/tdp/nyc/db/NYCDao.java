package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.nyc.model.Hotspot;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	
	public List<String> getAllProvider(){
		
		String sql = "select distinct l.`Provider` "
				+ "from nyc_wifi_hotspot_locations l "
				+ "order by l.`Provider` asc ";
		
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add( res.getString("Provider"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	} 
	
   public List<String> getAllLocation(String provider){
		
		String sql = " select distinct l.`Location`, l.`Latitude`, l.`Longitude` "
				+ "from nyc_wifi_hotspot_locations l "
				+ "where  l.`Provider`= ? ";
		
		
		
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add( res.getString("l.Location"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
   
	
	//per gli archi
	 public LatLng getLatLang(String l1, String provider){
			
			String sql = "select  avg(l.`Latitude`) as avgLat, avg(l.`Longitude`) as avgLong "
					+ "from nyc_wifi_hotspot_locations l "
					+ "where  l.`Provider`= ?  and l.`Location`= ? "; 
		
			
			LatLng p1 = null; 
			
			try {
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, provider);
				st.setString(2, l1);
				ResultSet res = st.executeQuery();

				/*
				if(res.isFirst()) {
					 p1 = new LatLng(res.getDouble("avgLat"),res.getDouble("avgLong") );
			 	 		//System.out.println("\n  opooo");

				}*/ 
				
				//NON PUOI USARE  l'if perchè ci sono due numeri !!!!!!!!!1
				
				res.first();
				p1= new LatLng(res.getDouble("avgLat"),res.getDouble("avgLong"));
				
				conn.close();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("SQL Error");
			}

			return p1;
		}
	 
	

}
