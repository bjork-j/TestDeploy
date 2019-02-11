package com.revature.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.revature.models.Employee;
import com.revature.models.Request;
import com.revature.util.ERSConnectionUtil;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class DataSource {
	
	final static Logger log = Logger.getLogger(DataSource.class);
	private static final DataSource instance = new DataSource();
	private final List<Employee> employees = new ArrayList<>();
	private final List<Request> requests = new ArrayList<>();
	
	private DataSource() {
		try (Connection conn = ERSConnectionUtil.getConnection()) {
			String sql = "call GET_ALL_EMP_INFO(?)";
			CallableStatement cs = conn.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.execute();
			ResultSet rs = ((OracleCallableStatement)cs).getCursor(1);
			while (rs.next()) {
				employees.add(new Employee(
						rs.getInt("EMP_ID"),
						rs.getString("EMP_FNAME"),
						rs.getString("EMP_LNAME"),
						rs.getString("EMP_ADDR"),
						rs.getString("EMP_EMAIL"),
						rs.getString("EMP_UNAME"),
						rs.getString("EMP_PASS"),
						rs.getInt("EMP_ISMGR")));
			}
			sql = "call GET_ALL_REQ(?)";
			cs = conn.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.execute();
			rs = ((OracleCallableStatement)cs).getCursor(1);
			while (rs.next()) {
				requests.add(new Request(
						rs.getInt("REQ_ID"),
						rs.getInt("EMP_SUBMIT"),
						rs.getString("REQ_VENDOR"),
						rs.getString("REQ_DESC"),
						rs.getDouble("REQ_AMOUNT"),
						rs.getInt("REQ_PENDING"),
						rs.getInt("MGR_APPROVE"),
						rs.getInt("REQ_APPROVED")));
			}
		} catch (SQLException e) {
			log.error("error occured in getallcustomers method");
		}
	}
	
	public static DataSource getInstance() {
		return instance;
	}
	
	public List<Employee> getEmployeeTable() {
		return employees;
	}
	
	public List<Request> getRequestTable() {
		return requests;
	}
}
