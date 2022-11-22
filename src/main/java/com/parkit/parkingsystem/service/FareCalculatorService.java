package com.parkit.parkingsystem.service;



import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {
	
	private double factor = 1;
	//TicketDAO ticketDAO= new TicketDAO();
	
	
	
    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

       //Get in Millis
        
        long inHour = ticket.getInTime().getTime();
        long outHour = ticket.getOutTime().getTime();
        float duration;
              
        if (outHour <= (inHour + (30 * 60 *1000))) {
			duration = 0;
		}else {
			duration = (outHour - inHour);
		}
        
        
        //Converse in Hours
        
        duration = duration / (1000*60*60);
        
        //Condition 5% discount
        //Research of the vehicleRegNumber in DB
        
        boolean recurringUser = ticket.getRecurringUser();
        
        if (recurringUser) {
        	factor = 0.95;
		}
               
        
        
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR * factor);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR * factor);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
        factor = 1;
    }
}