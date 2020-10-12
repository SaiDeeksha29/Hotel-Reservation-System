package com.blz.training.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.format.*;
public class HotelReservation {

	private List<Hotel> hotelList = new ArrayList<Hotel>();

	public boolean addHotelForRegularCustomers(String hotelName, int rateForWeekdaysRegularCustomer,int rateForWeekendsRegularCustomer,int rating) {
		Hotel hotel = new Hotel(hotelName, rateForWeekdaysRegularCustomer,rateForWeekendsRegularCustomer,rating);
		hotelList.add(hotel);
		return true;
	}
	
	public boolean addHotelForRewardsCustomers(String hotelName, int rateForWeekdaysRewardsCustomer,int rateForWeekendsRewardsCustomer,int rating) {
		Hotel hotel = new Hotel(hotelName, rateForWeekdaysRewardsCustomer,rateForWeekendsRewardsCustomer,rating);
		hotelList.add(hotel);
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HotelReservation hotelReservation =new HotelReservation();
		System.out.println("Welcome to Hotel Reservation Program in HotelReservation class on Master Branch");
        Scanner sc=new Scanner(System.in);
        hotelReservation.addHotelForRewardsCustomers("Lakewood",80,80,3);
        hotelReservation.addHotelForRewardsCustomers("Bridgewood",110,50,4);
        hotelReservation.addHotelForRewardsCustomers("Ridgewood",100,40,5);
	}

}
