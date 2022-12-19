package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
		
		//ARRANGE
		ParkingType parkingType = ParkingType.CAR;
			
		//ACT
		int result =  parkingSpotDAO.getNextAvailableSlot(parkingType);
				
		//ASSERT
		assertTrue(result > 0);
		assertTrue(result <= 5);
	}
	
	@Test
	public void parkingSpotDAO_updateParkingTest() {
		
		//ARRANGE
		when(parkingSpot.isAvailable()).thenReturn(true);
		when(parkingSpot.getId()).thenReturn(1);
		
		//ACT + ASSERT
		assertTrue(parkingSpotDAO.updateParking(parkingSpot));
			
	}

}
