package com.uhf.sqlserver.dao;

import java.util.List;

import com.uhf.sqlserver.entity.SqlServer;

/**
 * 
 * @author zyl
 *
 */
public interface SqlServerMapper {

	
	List<SqlServer> list ();
	
	void insertDatas(SqlServer sqlServer);
}
