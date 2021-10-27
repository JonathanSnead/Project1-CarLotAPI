package com.skillstorm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.beans.Car;

public class CarDAO {
	
	private static final String url = "jdbc:mysql://localhost:3306/cars-api-p1";
	private static final String username = "root";
	private static final String password = "mysqlroot";
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	public Car create(Car car) {
		
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			
			String sql = "insert into Vehicle values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, car.getVin());
			stmt.setString(2, car.getCondition());
			stmt.setString(3, car.getColor());
			stmt.setInt(4, car.getYear());
			stmt.setString(5, car.getBrand());
			stmt.setString(6, car.getModel());
			stmt.setString(7, car.getType());
			stmt.setInt(8, car.getHorsepower());
			stmt.setInt(9, car.getTorque());
			stmt.setInt(10, car.getPrice());
			
			stmt.executeUpdate(); 
			
			ResultSet keys = stmt.getGeneratedKeys();  

			keys.next();
			String carVIN = keys.getString(1); 
			car.setVin(carVIN);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return car;
	}
	
	
	public List<Car> findAll() {
		
		List<Car> allCars = new LinkedList<>();
		
		// 2: set up the connection AND 5: close the connection
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			
			// 3: create our statement (prepared)
			String sql = "select * from Vehicle";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// 4: execute the statement
			ResultSet rs = stmt.executeQuery(); // query bc getting from the table
			while (rs.next()) {
				// retrieve the attributes
				String carVIN = rs.getString(1);
				String carCondition = rs.getString(2);
				String carColor = rs.getString(3);
				int carYear = rs.getInt(4);
				String carBrand = rs.getString(5);
				String carModel = rs.getString(6);
				String carType = rs.getString(7);
				int carHorsepower = rs.getInt(8);
				int carTorque = rs.getInt(9);
				int carPrice = rs.getInt(10);
				
				// create new car object 
				Car car = new Car(carVIN, carCondition, carColor, carYear,
						carBrand, carModel, carType, carHorsepower, carTorque, carPrice);
				
				// add to the set of all cars 
				allCars.add(car);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// return back the list of cars to display
		return allCars;
	}

	
	// MIGHT NOT ADD IN
	public Car findByVIN(String vin) {
		
		Car car = new Car(); 
		
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			
			String sql = "select * from Vehicle where VIN like ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, vin);
			
			// FILL IN THE REST 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return car;
	}
	
	
	public Car updateCar(Car car) {
		
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			
			String sql = "update Vehicle set `condition` = ?, color = ?, "
					+ "`year` = ?, brand = ?, model = ?, `type` = ?, "
					+ "horsepower = ?, torque = ?, price = ? where VIN = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, car.getCondition());
			stmt.setString(2, car.getColor());
			stmt.setInt(3, car.getYear());
			stmt.setString(4, car.getBrand());
			stmt.setString(5, car.getModel());
			stmt.setString(6, car.getType());
			stmt.setInt(7, car.getHorsepower());
			stmt.setInt(8, car.getTorque());
			stmt.setInt(9, car.getPrice());
			stmt.setString(10, car.getVin());
			
			stmt.executeUpdate(); 
			
//			ResultSet keys = stmt.getGeneratedKeys();  
//
//			keys.next();
//			String carVIN = keys.getString(1); 
//			car.setVin(carVIN);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return car;
	}
	
	
	public String deleteCar(Car car) {
		
		try (Connection conn = DriverManager.getConnection(url, username, password)) {

			String sql = "delete from Vehicle where VIN=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, car.getVin());

			stmt.executeUpdate(); 

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "Car Deleted";
	}
	
}
