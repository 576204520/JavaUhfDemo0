package com.uhf.sqlserver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.uhf.sqlserver.entity.SqlServer;

public interface SqlServerService {

	List<SqlServer> list ();
	
	void insertDatas(SqlServer sqlServer);
}
