package com.parkit.parkingsystem;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.service.InteractiveShell;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class InteractiveShellTest {
	
	@Mock
	private static InputReaderUtil inputReaderUtil;
	
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	
	@Mock
	private static TicketDAO ticketDAO;
	
	//ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
   // TicketDAO ticketDAO = new TicketDAO();
	
	
	@Test
	public void loadInterfaceTest() {
		/*
		//InteractiveShell interactiveShell = new InteractiveShell();
		
		//ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
	
		when(inputReaderUtil.readSelection()).thenReturn(3);
		InteractiveShell.loadInterface();
		//assertNotNull(parkingService.processIncomingVehicle());
		//assertThat(option = 1);
		verify(inputReaderUtil, Mockito.times(1)).readSelection();
		*/
	}
}
