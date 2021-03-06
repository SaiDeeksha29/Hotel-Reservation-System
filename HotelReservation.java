package com.blz.training.controller;

import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.format.*;

@FunctionalInterface
interface Validate {
	public boolean validateDetails(String Details);
}

public class HotelReservation {

	private static List<Hotel> hotelList = new ArrayList<Hotel>();

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMMyyyy");

	// Add requirement details of customers
	public boolean addHotelCustomers(String hotelName, int rateForWeekdaysRegularCustomer,
			int rateForWeekendsRegularCustomer, int rating, int rateForWeekdaysRewardsCustomer,
			int rateForWeekendsRewardsCustomer) {
		Hotel hotel = new Hotel(hotelName, rateForWeekdaysRegularCustomer, rateForWeekendsRegularCustomer, rating,
				rateForWeekdaysRewardsCustomer, rateForWeekendsRewardsCustomer);
		hotelList.add(hotel);
		return true;
	}

	public static void setTotalRateForHotels(long noOfWeekdays, long noOfWeekends, Customer customer) {
		try {
			if (customer.getCustomerType().equals("regular")) {
				for (Hotel hotel : hotelList) {
					long totalRate = noOfWeekdays * hotel.getRateForWeekdaysRegularCustomer()
							+ noOfWeekends * hotel.getRateForWeekendsRegularCustomer();
					hotel.setTotalRate(totalRate);
				}
			} else if (customer.getCustomerType().equals("reward")) {
				for (Hotel hotel : hotelList) {
					long totalRate = noOfWeekdays * hotel.getRateForWeekdaysRewardsCustomer()
							+ noOfWeekends * hotel.getRateForWeekendsRewardsCustomer();
					hotel.setTotalRate(totalRate);
				}
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Best hotel with cheap costs for Regular customers
	public Hotel cheapestBestRatedHotel(String start, String end, Customer customerType) {
		Date StartDate = null;
		Date EndDate = null;
		try {
			StartDate = new SimpleDateFormat("ddMMMyyyy").parse(start);
			EndDate = new SimpleDateFormat("ddMMMyyyy").parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long noOfDays = 1 + (EndDate.getTime() - StartDate.getTime()) / 1000 / 60 / 60 / 24;
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(StartDate);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(EndDate);
		long weekdays = 0;
		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			startCal.setTime(EndDate);
			endCal.setTime(StartDate);

		}
		do {
			if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				++weekdays;
			}
			startCal.add(Calendar.DAY_OF_MONTH, 1);
		} while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());

		long weekends = noOfDays - weekdays;
		setTotalRateForHotels(weekdays, weekends, customerType);
		List<Hotel> bestRatedHotelList = hotelList.stream().sorted(Comparator.comparing(Hotel::getTotalRate))
				.collect(Collectors.toList());

		Hotel cheapestHotel = bestRatedHotelList.get(0);
		long cheapestRate = bestRatedHotelList.get(0).getTotalRate();
		for (Hotel hotel : bestRatedHotelList) {
			if (hotel.getTotalRate() <= cheapestRate) {
				if (hotel.getRating() > cheapestHotel.getRating())
					cheapestHotel = hotel;
			} else
				break;
		}
		return cheapestHotel;
	}

	public static void main(String[] args) {
		java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HotelReservation.class.getName());
		
		HotelReservation hotelReservation = new HotelReservation();
		Customer customer = new Customer();
		System.out.println("Welcome to Hotel Reservation Program in HotelReservation class on Master Branch");
		Scanner sc = new Scanner(System.in);
		hotelReservation.addHotelCustomers("Lakewood", 110, 90, 3, 80, 80);
		hotelReservation.addHotelCustomers("Bridgewood", 150, 50, 4, 110, 50);
		hotelReservation.addHotelCustomers("Ridgewood", 220, 150, 5, 100, 40);

		logger.info("Enter the start date in ddMMMyyyy format");
		String start = sc.next();
		logger.info("Enter the end date in ddMMMyyyy format");
		String end = sc.next();
		logger.info("Enter 1 if you are a regular customer \nEnter 2 if you are reward customer");
		int choice = sc.nextInt();
		if (choice == 1) {
			customer.setCustomerType("regular");
		} else
			customer.setCustomerType("reward");
		Hotel cheapHotel = hotelReservation.cheapestBestRatedHotel(start, end, customer);
		logger.info(cheapHotel.getHotelName() + "'s has rating of " + cheapHotel.getRating()
				+ " and total rate is " + cheapHotel.getTotalRate());
	}

}
