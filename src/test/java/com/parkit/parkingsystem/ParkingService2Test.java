package com.parkit.parkingsystem;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)

public class ParkingService2Test {
	
	   private static ParkingService parkingService;

	    @Mock
	    private static InputReaderUtil inputReaderUtil;
	    @Mock
	    private static ParkingSpotDAO parkingSpotDAO;
	    @Mock
	    private static TicketDAO ticketDAO;
	    
	    @Test
	    public void processIncomingVehicleTest_withoutRecurringUser(){
	    	try {
	        //add for select type of vehicule
	        when(inputReaderUtil.readSelection()).thenReturn(1);
	      //add to get parking slot
	        when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
	        
	        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
	        
	        when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
	        
	        when(ticketDAO.checkVehicleRegNumber("ABCDEF")).thenReturn(false);

	        //add for test processIncomingVehicle
	        when(ticketDAO.saveTicket(any(Ticket.class))).thenReturn(true);
	      	      
	    	}catch (Exception e) {
	    		 e.printStackTrace();
	             throw  new RuntimeException("Failed to set up test mock objects");
			}
	    	  parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
	    	  parkingService.processIncomingVehicle();
	    	  verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
	    }

	    @Test
	    public void processIncomingVehicleTest_withtRecurringUser(){
	    	try {
	        //add for select type of vehicule
	        when(inputReaderUtil.readSelection()).thenReturn(1);
	      //add to get parking slot
	        when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
	        
	        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
	        
	        when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
	        
	        when(ticketDAO.checkVehicleRegNumber("ABCDEF")).thenReturn(true);

	        //add for test processIncomingVehicle
	        when(ticketDAO.saveTicket(any(Ticket.class))).thenReturn(true);
	      	      
	    	}catch (Exception e) {
	    		 e.printStackTrace();
	             throw  new RuntimeException("Failed to set up test mock objects");
			}
	    	  parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
	    	  parkingService.processIncomingVehicle();
	    	  verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
	    }

}
