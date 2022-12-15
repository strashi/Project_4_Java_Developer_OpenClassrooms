package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;

public class DataBaseConfigTest {
	
	public static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	public static Connection con;
	
	@AfterAll
	public static void tearDown() {
		try {
			dataBaseTestConfig.closeConnection(con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Test
	public void getConnectionTest() throws ClassNotFoundException, SQLException {
		con = dataBaseTestConfig.getConnection();
		assertNotNull(con);
	}
}
