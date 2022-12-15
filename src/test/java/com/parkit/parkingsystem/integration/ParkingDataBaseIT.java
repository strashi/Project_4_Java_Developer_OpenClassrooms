package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;
    private static Ticket ticket;
  
    @Mock
    private static InputReaderUtil inputReaderUtil;
    
    @BeforeAll
    public static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    public void setUpPerTest() throws Exception {
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
    }
    
    @AfterAll
    public static void tearDown(){
    	
    }
    
    @Test
    public void testParkingACar(){
    	when(inputReaderUtil.readSelection()).thenReturn(1);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        ticket = parkingService.processIncomingVehicle();
        
        //TODO: check that a ticket is actualy saved in DB and Parking table is updated with availability
        assertNotNull(ticket);
        assertFalse(ticket.getParkingSpot().isAvailable());
    }
    
    @Test
    public void testParkingLotExit()throws Exception{
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	ticket = new Ticket();
    
        ticket.setId(0);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setOutTime(null);
        ticket.setParkingSpot(parkingSpot);
        ticket.setInTime(new Date(System.currentTimeMillis()-((2*60*60*1000)+2)));
        ticket.setPrice(0);
     
        ticketDAO.saveTicket(ticket);
        
        ticket = parkingService.processExitingVehicle();
       
        //TODO: check that the fare generated and out time are populated correctly in the database
        double price = (ticket.getPrice());
        assertTrue(price >= 2.98);
        
        Date outTime = ticket.getOutTime();
        assertTrue(outTime.after(ticket.getInTime()));
      
     }
    
}
