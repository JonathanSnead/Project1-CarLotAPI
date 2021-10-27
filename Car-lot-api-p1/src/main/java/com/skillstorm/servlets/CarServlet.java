package com.skillstorm.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.beans.Car;
import com.skillstorm.data.CarDAO;

@WebServlet(name = "car-servlet", urlPatterns = "/api/car-lot")
public class CarServlet extends HttpServlet {
	
	CarDAO dao = new CarDAO();

	// HAVE TO SWITCH TO "GET" REQUEST IN POSTMAN FOR THIS TO WORK
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// use the DAO to retrieve all cars
		List<Car> allCars = dao.findAll();
				
		// convert our list of cars to a json string
		String json = new ObjectMapper().writeValueAsString(allCars);
		
		// write the json string to our http response
		resp.getWriter().print(json);
		
		// setting content type to JSON
		resp.setContentType("application/json");
	}
	
	// HAVE TO SWITCH TO "POST" REQUEST IN POSTMAN FOR THIS TO WORK
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// parse the body of the http request
		InputStream requestBody = req.getInputStream();
		
		// convert the request body into java object
		Car car = new ObjectMapper().readValue(requestBody, Car.class);
	
		// updating the car object to contain the generated id
		Car updatedCar = dao.create(car);
		
		//returning back the updated car
		resp.getWriter().print(new ObjectMapper().writeValueAsString(updatedCar));
		
		// set status code to 201: CREATED
		resp.setStatus(201);
		
		// setting content type to JSON
		resp.setContentType("application/json");
	}
	
	// HAVE TO SWITCH TO "PUT" REQUEST IN POSTMAN FOR THIS TO WORK
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// parse the body of the http request
		InputStream requestBody = req.getInputStream();

		// convert the request body into java object
		Car car = new ObjectMapper().readValue(requestBody, Car.class);

		// updating the car object to contain the generated id
		Car updatedCar = dao.updateCar(car);

		//returning back the updated car
		resp.getWriter().print(new ObjectMapper().writeValueAsString(updatedCar));

		// set status code to 202: ACCEPTED
			// should be 200 for OK but using this to see if it works
		resp.setStatus(202);
	}
	
	// HAVE TO SWITCH TO "DELETE" REQUEST IN POSTMAN FOR THIS TO WORK
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// parse the body of the http request
		InputStream requestBody = req.getInputStream();
				
		// convert the request body into java object
		Car car = new ObjectMapper().readValue(requestBody, Car.class);
	
		// updating the car object to contain the generated id
		String delMsg = dao.deleteCar(car);
		
		//returning back the updated car
		resp.getWriter().print(delMsg);
		
		// set status code to 202: ACCEPTED
			// should be 200 but using 202 to see if it actually deletes
		resp.setStatus(202);
	}
}
