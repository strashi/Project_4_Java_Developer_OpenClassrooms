package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)

public class ParkingSystemDAOTest {
	
	@Mock
	private static Ticket ticket;
	
	@Mock
	private static ResultSet rs;
	
	@Mock
	private static PreparedStatement ps;
	
	@Mock
	private static ParkingSpot parkingSpot;
	
	public DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService;
   // private static ParkingSpot parkingSpot;
    private static ParkingSpotDAO parkingSpotDAO;
    private static Ticket ticketTest;
    private static InputReaderUtil inputReaderUtil;
    private TicketDAO ticketDAO;
    
    @BeforeAll
    private static void setUp() {
        //parkingSpotDAO = new ParkingSpotDAO();
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() {
    	dataBasePrepareService.clearDataBaseEntries();
    	ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
    }
	
	@Test
	public void parkingSystemDAO_TicketDAO_GetTicket() {
		//Ticket ticket = new Ticket();
		try {
			
			//when(ps.executeQuery()).thenReturn();
			when(rs.next()).thenReturn(true);
			when(rs.getInt(1)).thenReturn(1);
			when(rs.getInt(2)).thenReturn(1);
			when(rs.getString(7)).thenReturn("ParkingType.CAR");
			when(rs.getDouble(3)).thenReturn(3.0);
			when(rs.getTimestamp(4)).thenReturn((Timestamp) new Date(System.currentTimeMillis()-(2*60*1000)));
			when(rs.getTimestamp(5)).thenReturn((Timestamp) new Date(System.currentTimeMillis()));
			when(rs.getBoolean(6)).thenReturn(false);
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		//Ticket ticket = ;
		assertNull(ticketDAO.getTicket("ABCDEF"));
		
	}
	
	@Test
	public void parkingSystemDAO_TicketDAO_saveTicket() {
		when(ticket.getParkingSpot()).thenReturn(parkingSpot);
		when(ticket.getParkingSpot().getId()).thenReturn(1);
		when(ticket.getInTime()).thenReturn(new Date());
		//when(ps.setInt(1,ticket.getParkingSpot().getId())).thenReturn(1);
		when(ticket.getVehicleRegNumber()).thenReturn("ABCDEF");
		when(ticket.getPrice()).thenReturn(0.0);
		//when(ticket.getInTime().getTime()).thenReturn(new Date().getTime());
		when(ticket.getOutTime()).thenReturn(null);
		when(ticket.getParkingSpot().getId()).thenReturn(1);
		when(ticket.getRecurringUser()).thenReturn(false);
		//TicketDAO ticketDAO = new TicketDAO();
				
		assertFalse(ticketDAO.saveTicket(ticket));
	}
	

}
