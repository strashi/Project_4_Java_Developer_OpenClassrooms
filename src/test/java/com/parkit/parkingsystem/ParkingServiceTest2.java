package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
public class ParkingServiceTest2 {

	private static ParkingService parkingService;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;
	
	private Ticket ticket;

	@BeforeEach
	private void setUpPerTest() throws Exception {
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
		ticket = new Ticket();
		

	}
	

	@Test
	public void processExitingCarVehicleTest() {
		try {
			

			ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
			
			ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
			ticket.setParkingSpot(parkingSpot);
			ticket.setVehicleRegNumber("ABCDEF");
			when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
			when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
		parkingService.processExitingVehicle();
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}


	@Test
	public void processExitingBikeVehicleTest() {
		try {
			
			ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
			ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
			ticket.setParkingSpot(parkingSpot);
			ticket.setVehicleRegNumber("ABCDEF");
			when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
			when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);

			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
		parkingService.processExitingVehicle();
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}
	
	@Test
	public void processIncomingVehicleTest() {
		try {
			// add for select type of vehicule
			when(inputReaderUtil.readSelection()).thenReturn(1);
			// add to get parking slot
			when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
			
			when(ticketDAO.checkVehicleRegNumber("ABCDEF")).thenReturn(false);
			// add for test processIncomingVehicle
			when(ticketDAO.saveTicket(any(Ticket.class))).thenReturn(true);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
		ticket = parkingService.processIncomingVehicle();
		assertNotNull(ticket);
		verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));

	}
	

	@Test
	public void processIncomingVehicleTest_withRecurringUser() {
		try {
			// add for select type of vehicule
			when(inputReaderUtil.readSelection()).thenReturn(1);
			// add to get parking slot
			when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
			
			when(ticketDAO.checkVehicleRegNumber("ABCDEF")).thenReturn(true);
			// add for test processIncomingVehicle
			when(ticketDAO.saveTicket(any(Ticket.class))).thenReturn(true);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
		ticket = parkingService.processIncomingVehicle();
		assertNotNull(ticket);
		verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
	}
	
	@Test
	public void processIncomingVehicleTest_withoutRecurringUser() {
		try {
			// add for select type of vehicule
			when(inputReaderUtil.readSelection()).thenReturn(1);
			// add to get parking slot
			when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
			when(ticketDAO.checkVehicleRegNumber("ABCDEF")).thenReturn(false);
			// add for test processIncomingVehicle
			when(ticketDAO.saveTicket(any(Ticket.class))).thenReturn(true);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
		
		ticket = parkingService.processIncomingVehicle();
		assertNotNull(ticket);
		verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
	}
	@Disabled
	@Test
	public void getNextParkingNumberIfAvailableTest() {
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		//ParkingType parkingType = ParkingType.CAR;
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(-1);
		parkingService.getNextParkingNumberIfAvailable();
		
		verify(parkingSpotDAO).getNextAvailableSlot(ParkingType.CAR);
		//assertThrows(Exception.class, () -> parkingService.getNextParkingNumberIfAvailable());
	}

}