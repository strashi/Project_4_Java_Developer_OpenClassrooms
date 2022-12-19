package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
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
public class ParkingServiceTest {

	private ParkingService parkingService;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;

	@BeforeEach
	private void setUpPerTest() {
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

	}

	@Test
	public void processExitingCarVehicleTest() {
		try {
			
			//ARRANGE
			when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
			ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
			Ticket ticket = new Ticket();
			ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
			ticket.setParkingSpot(parkingSpot);
			ticket.setVehicleRegNumber("ABCDEF");
			when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
			when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
			when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
		
		//ACT
		Ticket ticket = parkingService.processExitingVehicle();
		
		//ASSERT
		assertNotNull(ticket);
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}

	
	@Test
	public void processExitingBikeVehicleTest() {
		try {
			
			//ARRANGE
			when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
			ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
			Ticket ticket = new Ticket();
			ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
			ticket.setParkingSpot(parkingSpot);
			ticket.setVehicleRegNumber("ABCDEF");
			when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
			when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
			when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
		
		//ACT
		Ticket ticket = parkingService.processExitingVehicle();
		
		//ASSERT
		assertNotNull(ticket);
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}

	@Test
	public void processIncomingVehicleTest() {
		try {
			
			//ARRANGE
			when(inputReaderUtil.readSelection()).thenReturn(1);
			when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
			when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
			when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
			when(ticketDAO.checkVehicleRegNumber("ABCDEF")).thenReturn(false);
			when(ticketDAO.saveTicket(any(Ticket.class))).thenReturn(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
	
		//ACT
		Ticket ticket = parkingService.processIncomingVehicle();
		
		//ASSERT
		assertNotNull(ticket);
		verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));

	}

	@Test
	public void processIncomingVehicleTest_returnNull() {
		try {
			
			//ARRANGE
			when(inputReaderUtil.readSelection()).thenReturn(1);
			when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(-1);
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
	
		//ACT
		Ticket ticket = parkingService.processIncomingVehicle();
		
		//ASSERT
		assertNull(ticket);
	}

	@Test
	public void processIncomingVehicleTest_withRecurringUser() {
		try {
			
			//ARRANGE
			when(inputReaderUtil.readSelection()).thenReturn(1);
			when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
			when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
			when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
			when(ticketDAO.checkVehicleRegNumber("ABCDEF")).thenReturn(true);
			when(ticketDAO.saveTicket(any(Ticket.class))).thenReturn(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
		
		//ACT
		Ticket ticket = parkingService.processIncomingVehicle();
		
		//ASSERT
		assertNotNull(ticket);
		assertTrue(ticket.getRecurringUser());
		verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
	}

	
	@Test
	public void getNextParkingNumberIfAvailableTest_withFalseCondition() {
		
		//ARRANGE
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(-1);
		
		//ACT
		parkingService.getNextParkingNumberIfAvailable();

		//ASSERT
		verify(parkingSpotDAO).getNextAvailableSlot(ParkingType.CAR);
	}
	
	@Test
	public void getVehicleTypeBikeTest() {
		
		//ARRANGE
		when(inputReaderUtil.readSelection()).thenReturn(2);
		when(parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE)).thenReturn(2);
		
		//ACT
		parkingService.getNextParkingNumberIfAvailable();

		//ASSERT
		verify(parkingSpotDAO).getNextAvailableSlot(ParkingType.BIKE);
	}
	
	@Test
	public void getVehicleWithNoTypeTest() {
		
		//ARRANGE
		when(inputReaderUtil.readSelection()).thenReturn(3);
		
		//ACT
		parkingService.getNextParkingNumberIfAvailable();
		
		//ASSERT
		verify(inputReaderUtil).readSelection();
		
	}
}