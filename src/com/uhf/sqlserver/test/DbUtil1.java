package com.uhf.sqlserver.test;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库连接工具类
 * 
 * @author Taylor
 *
 */
public class DbUtil1 {

	/**
	 * sqlserver
	 */
	private String dbUrl = "jdbc:sqlserver://localhost:1433;databasename=StudentName";
	private String dbUserName = "caojian";
	private String dbPassword = "123456";
	private String jdbcName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getCon() throws Exception {
		Class.forName(jdbcName);
		Connection con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		return con;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param con
	 * @throws Exception
	 */
	public void closeCon(Connection con) throws Exception {
		if (con != null) {
			con.close();
		}
	}

	/**
	 * 测试数据库连接
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DbUtil1 dbUtil = new DbUtil1();
		Connection con = null;
		try {
			con = dbUtil.getCon();
			System.out.println("数据库连接成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
