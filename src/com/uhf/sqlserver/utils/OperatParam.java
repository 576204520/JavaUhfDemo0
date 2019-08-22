package com.uhf.sqlserver.utils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 自定义properties文件读取工具类
 * 
 * @author zyl
 *
 */
@Component
public class OperatParam {

	private Map<String, String> configMap;

	@Resource
	private Properties configProperties;// 通过在spring配置bean，读取config.properties文件

	@PostConstruct
	private void init() {
		this.configMap = new HashMap<String, String>();
		Set<?> keys = this.configProperties.keySet();
		for (Iterator<?> localIterator = keys.iterator(); localIterator.hasNext();) {
			Object key = localIterator.next();
			this.configMap.put(key.toString(), this.configProperties.getProperty(key.toString()));
		}
	}

	public String get(String key) {
		return (String) this.configMap.get(key);
	}

}
