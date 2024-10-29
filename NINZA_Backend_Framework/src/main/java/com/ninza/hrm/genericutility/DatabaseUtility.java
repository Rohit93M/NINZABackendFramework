package com.ninza.hrm.genericutility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.cj.jdbc.Driver;

public class DatabaseUtility extends PropertyFileUtility {

	Connection conn;
	PropertyFileUtility fileUtils = new PropertyFileUtility();

	public void getDatabaseConnection() {
		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(fileUtils.getDataFromPropertyFile("dBUrl"), fileUtils.getDataFromPropertyFile("dBUsername"),
					fileUtils.getDataFromPropertyFile("dBPassword"));
		} catch (Exception e) {

		}
	}

	public void closeDatabaseConnection() {
		try {
			conn.close();
		} catch (Exception e) {

		}
	}

	public ResultSet executeSelectQuery(String query) {

		ResultSet resultSet = null;
		try {
			resultSet = conn.createStatement().executeQuery(query);
		} catch (Exception e) {
		}
		return resultSet;
	}

	public int executeNonSelectQuery(String query) {

		int result = 0;
		try {
			result = conn.createStatement().executeUpdate(query);
		} catch (Exception e) {
		}
		return result;
	}

	public boolean executeQueryVerifyAndGetData(String query, int columnIndex, String expectedData)
			throws SQLException, IOException {
		boolean flag = false;
		Connection conn = DriverManager.getConnection(getDataFromPropertyFile("dBUrl"), getDataFromPropertyFile("dBUsername"),
				getDataFromPropertyFile("dBPassword"));
		ResultSet result = conn.createStatement().executeQuery(query);
		while (result.next()) {
			if (result.getString(columnIndex).equals(expectedData)) {
				flag = true;
				break;
			}
		}

		if (flag) {
			System.out.println(expectedData + "==>data verified in Database table");
			return true;
		} else {
			System.out.println(expectedData + "==>data not verified in Database table");
			return false;
		}
	}
}
