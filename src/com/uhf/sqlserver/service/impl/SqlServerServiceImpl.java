package com.uhf.sqlserver.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uhf.sqlserver.dao.SqlServerMapper;
import com.uhf.sqlserver.entity.SqlServer;
import com.uhf.sqlserver.service.SqlServerService;

@Service("sqlServerService")
public class SqlServerServiceImpl implements SqlServerService{

	@Resource(name="sqlServerMapper")
	private SqlServerMapper sqlServerMapper;
	
	@Override
	public List<SqlServer> list() {
		return sqlServerMapper.list();
	}

	@Override
	public void insertDatas(SqlServer sqlServer) {
		
	}

	
}
