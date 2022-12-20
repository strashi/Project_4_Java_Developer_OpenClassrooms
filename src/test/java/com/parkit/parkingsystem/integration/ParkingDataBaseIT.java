package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
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
        //TODO: check that a ticket is actualy saved in DB and Parking table is updated with availability

    	//ARRANGE
    	when(inputReaderUtil.readSelection()).thenReturn(1);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        
        //ACT
        ticket = ticketDAO.getTicket("ABCDEF");
              
        //ASSERT
        assertNotNull(ticket);
        assertFalse(ticket.getParkingSpot().isAvailable());
    }
    
    @Test
    public void testParkingLotExit(){
        //TODO: check that the fare generated and out time are populated correctly in the database
    	
    	//ARRANGE
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	ticket = new Ticket();
        ticket.setId(0);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setOutTime(null);
        ticket.setParkingSpot(parkingSpot);
        ticket.setInTime(new Date(System.currentTimeMillis()-((2*60*60*1000)+(5*1000))));
        ticket.setPrice(0);
        ticketDAO.saveTicket(ticket);
        
        //ACT
        ticket = parkingService.processExitingVehicle();
             
        //ASSERT
        assertTrue(ticket.getPrice() >= 3 && ticket.getPrice() < 3.004);
        assertTrue(((ticket.getOutTime().getTime())- ticket.getInTime().getTime()) >= (2*60*60*1000) 
        		&& ((ticket.getOutTime().getTime())- ticket.getInTime().getTime()) < (2*60*60*1000 + 30*1000));
      
    }
    
    // Test for free 30 minutes parking time
    
    @Test
    public void testParkingLotExitUnder30MinutesParkingTime(){
    	
    	//ARRANGE
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	ticket = new Ticket();
        ticket.setId(0);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setOutTime(null);
        ticket.setParkingSpot(parkingSpot);
        ticket.setInTime(new Date(System.currentTimeMillis()-((29*60*1000))));
        ticket.setPrice(0);
        ticketDAO.saveTicket(ticket);
        
        //ACT
        ticket = parkingService.processExitingVehicle();
             
        //ASSERT
        assertTrue(ticket.getPrice() == 0 );
       
     }
    
    // Test for recurring users
    
    @Test
    public void testParkingLotExitForRecurringUser(){
    	
    	//ARRANGE
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	ticket = new Ticket();
        ticket.setId(0);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setOutTime(null);
        ticket.setParkingSpot(parkingSpot);
        ticket.setInTime(new Date(System.currentTimeMillis()-((2*60*60*1000)+(5*1000))));
        ticket.setPrice(0);
        ticket.setRecurringUser(true);
        ticketDAO.saveTicket(ticket);
        
        //ACT
        ticket = parkingService.processExitingVehicle();
             
        //ASSERT
        assertTrue(ticket.getPrice() >= 3 * 0.95 && ticket.getPrice() < 3.004 * 0.95);
        assertTrue(((ticket.getOutTime().getTime())- ticket.getInTime().getTime()) >= (2*60*60*1000) 
        		&& ((ticket.getOutTime().getTime())- ticket.getInTime().getTime()) < (2*60*60*1000 + 30*1000));
      
     }
    
}
