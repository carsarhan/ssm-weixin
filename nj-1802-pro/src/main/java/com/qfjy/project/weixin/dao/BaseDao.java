/**
 *	@filename:BaseDao.java
 *  @basepath:com.zxt.khzy.dao.impl 
 *  @description:TODO
 *  @author:fangxiaohua
 *  @created:2013-9-11下午8:16:13
 *  @version:V1.0
 */

package com.qfjy.project.weixin.dao;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author guoweixin
 * @Description 与数据库交互的基础类
 * @created 2013-9-11 下午8:16:13
 * @History 
 * @version v1.0
 */

public class BaseDao {
	private static Connection connection;
	/**
	 * 获取jdbc配置 jdbc.properties
	 * 
	 * @return
	 */
	public static Properties jdbcProperties() {
		Properties properties = new Properties();
		InputStream inStream;
		try {
//非WEB 程序			
			/* inStream = new BufferedInputStream(new
					 FileInputStream("D:\\workplace\\goods\\khzy\\src\\jdbc.properties"));
				 properties.load(inStream);*/

			inStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("/jdbc.properties");
			properties.load(inStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	
	
	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public static Connection getConn() {
		try {
			String url=null;
			Properties properties = BaseDao.jdbcProperties();
			url=properties.getProperty("jdbc.url")+"?user="+properties.getProperty("jdbc.username")+"&password="+properties.getProperty("jdbc.password")+"&useUnicode=true&characterEncoding="+properties.getProperty("jdbc.characterEncoding");
			Class.forName(properties.getProperty("driverClassName")).newInstance();
			connection = DriverManager.getConnection(url);
			 if(connection!=null){
				 System.out.print("数据库连接成功\n");
			 }
		return BaseDao.connection;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询方法
	 * 根据传入的SQL语句返回取出的结果
	 */
	public ArrayList select(String sql) throws Exception {
		Connection conn = null;
		ArrayList result = new ArrayList();
		Statement stmt = null;
		ResultSet rst = null;
		try {
			 conn =BaseDao.getConn();
			 stmt = conn.createStatement();
			 if(stmt!=null) System.out.print("statement对象创建成功\n");
			 rst = stmt.executeQuery(sql);
			 ResultSetMetaData rsmd = rst.getMetaData();
			 int cols = rsmd.getColumnCount();
			 int i = 0;
			 while (rst.next() != false) {
				ArrayList row = new ArrayList();
				for (i = 1; i <= cols; ++i) {
					if (rst.getString(i) == null) {
						row.add("");
					} else {
						row.add(rst.getString(i));
					}
				}
				result.add(row);
			}
		} catch (SQLException e) {
			throw new Exception("select data exception:" + e.getMessage());
		} finally {
			this.close(conn, stmt, rst);
		}
		return result;
	}

	
	
	
	/**
	 * 增删改
	 * 
	 * @param1:sql语句
	 * @param2:Object数组
	 * @return int
	 */
	public int executeSQL(String pre, Object[] obj) throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;
		int num = 0;
		try {
			con = BaseDao.getConn();
			pst = con.prepareStatement(pre);
			 if(pst!=null) System.out.print("prepareStatement对象创建成功\n");
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					pst.setObject(i + 1, obj[i]);
				}
			}
			num = pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException();
		} finally {
			BaseDao.close(con, pst, null);
		}
		return num;

	}
	
	/**
	 * 关闭连接
	 * 
	 * @param con
	 * @param pst
	 * @param rst
	 */
	public static void close(Connection con, Statement pst,
			ResultSet rst) {
		try {
			if (rst != null)
				rst.close();
			System.out.print("ResultSet关闭成功\n");
			if (pst != null)
				pst.close();
			System.out.print("Statement关闭成功\n");
			if (con != null)
				con.close();
			System.out.print("Connection关闭成功\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
