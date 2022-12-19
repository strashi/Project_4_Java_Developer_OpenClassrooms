package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;

@ExtendWith(MockitoExtension.class)

public class ParkingSpotDAOTest {
	
	public DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	
	private ParkingSpotDAO parkingSpotDAO;
	
	
		
	@Mock
	private static ParkingSpot parkingSpot;
	
	private static DataBasePrepareService dataBasePrepareService;
	
	
	
	
	@BeforeAll
	private static void setUp() {
		
		dataBasePrepareService = new DataBasePrepareService();
	}
	
	@BeforeEach
	private void setUpPerTest() {
		dataBasePrepareService.clearDataBaseEntries();
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		
	}
	
	@Test
	public void parkingSpotDAO_getNextAvailableSlotTest() {
		ParkingType parkingType = ParkingType.CAR;
		//System.out.println(parkingType.toString());
		//when(parkingType.toString()).thenReturn("CAR");
		
		int result =  parkingSpotDAO.getNextAvailableSlot(parkingType);
		
		
		assertTrue(result > 0);
		assertTrue(result <= 5);
		//System.out.println(result);
		
		//verify(parkingSpotDAO).toString();
		
	}
	
	@Test
	public void parkingSpotDAO_updateParkingTest() {
		when(parkingSpot.isAvailable()).thenReturn(true);
		when(parkingSpot.getId()).thenReturn(1);
		assertTrue(parkingSpotDAO.updateParking(parkingSpot));
		
		
	}

}
