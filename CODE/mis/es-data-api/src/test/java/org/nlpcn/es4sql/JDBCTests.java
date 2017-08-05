package org.nlpcn.es4sql;


import com.alibaba.druid.pool.DruidDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by allwefantasy on 8/26/16.
 */
public class JDBCTests {
	
	public static void main(String[] args) {
		try {
			JDBCConnection.getInstance();
			Connection connection = JDBCConnection.connection;
			DruidDataSource dds = JDBCConnection.dds;
			connection = dds.getConnection();
			PreparedStatement ps = connection.prepareStatement("SELECT  gender,lastname,age from  " + TestsConstants.TEST_INDEX + " where lastname='Heath'");
	        ResultSet resultSet = ps.executeQuery();
	        List<String> result = new ArrayList<String>();
	        while (resultSet.next()) {
	        	System.out.println(resultSet.next());
	            result.add(resultSet.getString("lastname") + "," + resultSet.getInt("age") + "," + resultSet.getString("gender"));
	        }
	        System.out.println(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


