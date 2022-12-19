package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)

public class TicketDAOTest {
	
	@Mock
	private static Ticket ticket;
	
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	
	@Mock
	private static ParkingSpot parkingSpot;
		
	public DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static DataBasePrepareService dataBasePrepareService;
	private TicketDAO ticketDAO;

	@BeforeAll
	private static void setUp() {
		dataBasePrepareService = new DataBasePrepareService();
	}

	@BeforeEach
	private void setUpPerTest() {
		dataBasePrepareService.clearDataBaseEntries();
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
	}

	@Test
	public void parkingSystemDAO_TicketDAO_saveTicketTest() throws Exception {
		
		//ARRANGE
		when(ticket.getParkingSpot()).thenReturn(parkingSpot);
		when(ticket.getParkingSpot().getId()).thenReturn(1);
		when(ticket.getInTime()).thenReturn(new Date());
		when(ticket.getVehicleRegNumber()).thenReturn("ABCDEF");
		when(ticket.getPrice()).thenReturn(0.0);
		when(ticket.getOutTime()).thenReturn(null);
		when(ticket.getParkingSpot().getId()).thenReturn(1);
		when(ticket.getRecurringUser()).thenReturn(false);
		
		//ACT + ASSERT
		assertFalse(ticketDAO.saveTicket(ticket));
	}
	
	@Test
	public void parkingSystemDAO_TicketDAO_getTicketTest() {
		try {
			
			//ARRANGE
			ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
			when(inputReaderUtil.readSelection()).thenReturn(1);
			when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
			when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
			 
			//ACT
			parkingService.processIncomingVehicle();
			
			//ASSERT
			assertNull(ticketDAO.getTicket("ABCE"));
			assertNotNull(ticketDAO.getTicket("ABCDEF"));
			
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
	@Test
	public void parkingSystemDAO_TicketDAO_upDateTicketTest() {
		
	try {
		
		//ARRANGE
		when(ticket.getParkingSpot()).thenReturn(parkingSpot);
		when(ticket.getParkingSpot().getId()).thenReturn(1);
		when(ticket.getInTime()).thenReturn(new Date(System.currentTimeMillis()- (2*60*60*1000)));
		when(ticket.getVehicleRegNumber()).thenReturn("ABCDEF");
		when(ticket.getPrice()).thenReturn(0.0);
		when(ticket.getOutTime()).thenReturn(null);
		when(ticket.getParkingSpot().getId()).thenReturn(1);
		when(ticket.getRecurringUser()).thenReturn(false);
		ticketDAO.saveTicket(ticket);
		when(ticket.getPrice()).thenReturn(3.0);
		when(ticket.getOutTime()).thenReturn(new Date());
		when(ticket.getId()).thenReturn(1);
		
		//ACT + ASSERT
		assertTrue(ticketDAO.updateTicket(ticket));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}


