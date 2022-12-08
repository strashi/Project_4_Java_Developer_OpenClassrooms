package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.App;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

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
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    public static void tearDown(){

    }

    @Test
    public void testParkingACar(){
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
      

        Ticket ticketIn = parkingService.processIncomingVehicle();
        

        //TODO: check that a ticket is actualy saved in DB and Parking table is updated with availability
              
        assertNotNull(ticketIn);
        assertFalse(ticketIn.getParkingSpot().isAvailable());
        
    }
   
    @Test
    public void testParkingLotExit()throws Exception{
        //testParkingACar();
    	ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        
        Ticket ticketIn = parkingService.processIncomingVehicle();
       
        Ticket ticketOut = parkingService.processExitingVehicle();
        //TODO: check that the fare generated and out time are populated correctly in the database
        Object obj = (Object)(ticketOut.getPrice());
        assertNotNull(obj);
        
        Object outTime = (Object)(ticketOut.getOutTime());
        assertNotNull(outTime);
      
     }
    /*
    @Test
    public void testInteractiveShellVehicleEntry() {
    	 ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	App app = new App();
    	//when(inputReaderUtil.readSelection()).thenReturn(1);
    	 Ticket ticketIn = parkingService.processIncomingVehicle();
    	 assertNotNull(ticketIn);
    }
    @Test
    public void testInteractiveShellVehicleExit() {
    	
    }
    @Test
    public void testInteractiveShellAppExit() {
    	
    }*/
   
}
