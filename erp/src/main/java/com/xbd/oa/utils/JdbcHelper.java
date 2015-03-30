package com.xbd.oa.utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


/**
 *功能：数据库连接公共类
 *@author ZHUQUAN
 *@version 1.0
 */
public class JdbcHelper {
	
	public static Connection getConnection() throws Exception{
		Context initContext = new InitialContext();
		DataSource ds = (DataSource)initContext.lookup("java:comp/env/jdbc/mysql");
		Connection conn = ds.getConnection();
		return conn;
	}
	
	public static void close(ResultSet rs,PreparedStatement pstmt,Connection conn){
		try{
			if(null!=rs){
				rs.close();
				rs = null;
			}
			if(null!=pstmt){
				pstmt.close();
				pstmt = null;
			}
			if(null!=conn){
				conn.close();
				conn = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt,Connection conn){
		try{
			if(null!=stmt){
				stmt.close();
				stmt = null;
			}
			if(null!=conn){
				conn.close();
				conn = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
