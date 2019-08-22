package com.uhf.detailwith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.uhf.linkage.Linkage;
import com.uhf.linkage.MulLinkage;
import com.uhf.springmvc.entity.StringUtils;
import com.uhf.sqlserver.entity.SqlServer;
import com.uhf.sqlserver.entity.TblRfidData;
import com.uhf.sqlserver.utils.GreyStartServlet;
import com.uhf.structures.InventoryData;
import com.uhf.structures.OnInventoryListener;

public class InventoryDetailWith implements OnInventoryListener {

	/**
	 * sqlserver
	 */
	private String dbUrl = GreyStartServlet.configurationMap.get("DATABASE_URL");
	private String dbUserName = GreyStartServlet.configurationMap.get("DATABASE_ID");
	private String dbPassword = GreyStartServlet.configurationMap.get("DATABASE_PASSWORD");
	private String jdbcName = GreyStartServlet.configurationMap.get("DATABASE_JDBCNAME");

	private Statement stmt;
	private ResultSet rs;
	// sql语句提出来写
	String sql = "select * from " + GreyStartServlet.configurationMap.get("DATABASE_SURFACE") + "";
	String sqlFindBy = "select * from " + GreyStartServlet.configurationMap.get("DATABASE_SURFACE")
			+ " where RFIdCode=? and RFTXId=? and RFEId=?";
	String sqlAdd = "insert into " + GreyStartServlet.configurationMap.get("DATABASE_SURFACE")
			+ " (RFEId,RFTXId,RFIdCode,InputType,ISTime) values(?,?,?,?,?)";
	String sqlUpdate = "update " + GreyStartServlet.configurationMap.get("DATABASE_SURFACE")
			+ " set InputType=?,OSTime=? where RFEId=? and RFTXId=? and RFIdCode=?";
	String sqlUpdate2 = "update " + GreyStartServlet.configurationMap.get("DATABASE_SURFACE")
			+ " set InputType=?,ISTime=? where RFEId=? and RFTXId=? and RFIdCode=?";
	String sqlFindByType = "select * from " + GreyStartServlet.configurationMap.get("DATABASE_SURFACE")
			+ " where InputType=?";
	// 全局List
	List<String> epcs = GreyStartServlet.epcs;
	// 全局计数器
	int count = 0;
	// 全局Map
	Map<String, Integer> countMap = new HashMap();

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

	private static InventoryDetailWith inventoryDetailWith = new InventoryDetailWith();
	public static List<Map<String, Object>> list = new ArrayList<>();
	public static Long startTime;
	public static int tagCount;
	// public static List<InventoryData> InventoryList = new ArrayList<>();
	// public InventoryData inventoryData;

	// public InventoryData getInventoryData() {
	// return inventoryData;
	// }
	//
	// public void setInventoryData(InventoryData inventoryData) {
	// this.inventoryData = inventoryData;
	// }

	public static Map<String, String> inventoryMap = new HashMap<String, String>();

	// public static InventoryData inventory = new InventoryData();

	public static InventoryDetailWith getInstance() {
		return inventoryDetailWith;
	}

	@Override
	public void getInventoryData(InventoryData inventoryData) {
		GreyStartServlet.countName++;
		// 打印返回设备数据
		// System.out.println(inventoryData.toString());
		count++;
		if (count > 1000) {
			count = 0;
		}
		if (null != inventoryData) {
			String epc = "";
			String rss = "";
			String port = "";
			String tidUser = "";
			String key = "";
			String data = "";
			if (inventoryData.epcLen > 0) {
				epc = StringUtils.byteToHexString(inventoryData.epc, inventoryData.epcLen);
				rss = String.valueOf(inventoryData.rssi);
			}
			if (inventoryData.externalDataLen > 0) {
				tidUser = StringUtils.byteToHexString(inventoryData.externalData, inventoryData.externalDataLen);
			}
			port = String.valueOf(inventoryData.antennaPort);
			if (tidUser != null && tidUser != "") {
				key = "externalData";
				data = tidUser;
				// System.out.println(data + "==========");
			} else {
				key = "epc";
				data = epc;
				// System.out.println(data);
			}
			if (inventoryMap.containsKey(data)) {
				// System.out.println("-----containsKey----" + epc
				// + "-----list----" + list.size());
				for (int i = 0; i < list.size(); i++) {
					if (data.equals(list.get(i).get(key))) {
						String t = (String) list.get(i).get("count");
						t = String.valueOf(Integer.valueOf(t) + 1);
						list.get(i).put("count", t);
						break;
					}
				}
				if (!list.isEmpty()) {
					tagCount++;
				}

			} else {
				tagCount++;
				inventoryMap.put(data, data);
				Map<String, Object> map = new HashMap<>();
				map.put("antennaPort", port);
				map.put("epc", epc);
				map.put("externalData", tidUser);
				map.put("count", "1");
				map.put("rssi", rss);
				list.add(map);
			}
//			GreyStartServlet.Names.addAll(list);

//			String 设备id = GreyStartServlet.configurationMap.get("EQUIPMENT_NAME");
//			// 增加数据库信息
//			if (list != null) {
//				for (Map<String, Object> map : list) {
////					String rfidcode = map.get("epc").toString(); // fid
////					int countName = Integer.parseInt(map.get("count").toString()); // 次数
////					Integer antennaPort = Integer.parseInt(map.get("antennaPort").toString()); // 天线端口
////					String information = rfidcode + antennaPort; // fid + 天线端口
////					if (
////							!epcs.contains(information) && !countMap.containsKey(information)
////							|| !epcs.contains(information) && countMap.get(information) < countName) 
////					{
////						epcs.add(information);
////						List<TblRfidData> tblRfidDataList = this.findByTblRfidData(rfidcode, antennaPort, Integer.parseInt(设备id));
////						if (tblRfidDataList == null || tblRfidDataList.size() == 0) {
////							this.addTblRfidData( Integer.parseInt(设备id), antennaPort, rfidcode, 1, this.getCurrentDateAString());
////						} else {
////							this.updateTblRfidData2(1, this.getCurrentDateAString(),
////									Integer.parseInt(设备id),
////									antennaPort, rfidcode);
////						}
////						count = 1;
////						countMap.put(information, 0);
////					}
//					// 修改数据库状态
////					if (count % 10 == 0) {
////						List 临时 = new ArrayList<>();
////						for (String string : epcs) {
////							if (countMap.containsKey(information) && countMap.get(information) >= countName) {
////								String rfidcodes = information.substring(0, information.length() - 1);
////								Integer antennaPorts = Integer.parseInt(
////										information.substring(information.length() - 1, information.length()));
////								this.updateTblRfidData(0, this.getCurrentDateAString(),
////										Integer.parseInt(设备id),
////										antennaPorts, rfidcodes);
////								临时.add(information);
////							}
////						}
////						for (Object object : 临时) {
////							epcs.remove(object);
////						}
////						countMap.put(information, countName);
////					}
//				}
//			}
			// System.out.println("我的epcs的长度为：" + epcs.size());
		}
	}

	public void setListener(Linkage linkage) {
		linkage.setOnInventoryListener(this);
	}

	public void setListener(MulLinkage mulLinkage) {
		mulLinkage.setOnInventoryListener(this);
	}

	/**
	 * 查询数据库方法
	 * 
	 * @return
	 */
	public List<SqlServer> findSqlServer() {
		// List<SqlServer> list = new ArrayList<>();
		InventoryDetailWith inventoryDetailWith = new InventoryDetailWith();
		Connection conn = null;
		try {
			conn = inventoryDetailWith.getCon();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int menusid = rs.getInt("menusid");
				String rfidcode = rs.getString("rfidcode");
				String menusurl = rs.getString("menusurl");
				int fatherid = rs.getInt("fatherid");
				/*
				 * SqlServer sqlServer = new SqlServer(menusid,rfidcode,menusurl,fatherid);
				 * list.add(sqlServer);
				 */
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inventoryDetailWith.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 增加数据库方法
	 */
	public void addTblRfidData(Integer rfeId, Integer rftxId, String rfidCode, Integer inputType, String isTime) {
		InventoryDetailWith inventoryDetailWith = new InventoryDetailWith();
		Connection conn = null;
		try {
			conn = inventoryDetailWith.getCon();
			PreparedStatement ps = conn.prepareStatement(sqlAdd);
			ps.setInt(1, rfeId);
			ps.setInt(2, rftxId);
			ps.setString(3, rfidCode);
			ps.setInt(4, inputType);
			ps.setString(5, isTime);
			int row = ps.executeUpdate();
			if (row > 0) {
				System.out.println("增加数据成功");
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inventoryDetailWith.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 修改数据库状态方法
	 */
	public void updateTblRfidData(Integer inputType, String osTime, Integer rfrId, Integer rftxId, String rfidCode) {
		InventoryDetailWith inventoryDetailWith = new InventoryDetailWith();
		Connection conn = null;
		try {
			conn = inventoryDetailWith.getCon();
			PreparedStatement ps = conn.prepareStatement(sqlUpdate);
			ps.setInt(1, inputType);
			ps.setString(2, osTime);
			ps.setInt(3, rfrId);
			ps.setInt(4, rftxId);
			ps.setString(5, rfidCode);
			int row = ps.executeUpdate();
			if (row > 0) {
				System.out.println("修改数据成功");
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inventoryDetailWith.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 修改数据库状态方法
	 */
	public void updateTblRfidData2(Integer inputType, String isTime, Integer rfrId, Integer rftxId, String rfidCode) {
		InventoryDetailWith inventoryDetailWith = new InventoryDetailWith();
		Connection conn = null;
		try {
			conn = inventoryDetailWith.getCon();
			PreparedStatement ps = conn.prepareStatement(sqlUpdate2);
			ps.setInt(1, inputType);
			ps.setString(2, isTime);
			ps.setInt(3, rfrId);
			ps.setInt(4, rftxId);
			ps.setString(5, rfidCode);
			int row = ps.executeUpdate();
			if (row > 0) {
				System.out.println("修改数据成功");
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inventoryDetailWith.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据条件查询数据返回List<TblRfidData>类型
	 */
	public List<TblRfidData> findByTblRfidData(String rfidCode, Integer rftxId, Integer rfeId) {
		List<TblRfidData> tblRfidDataList = new ArrayList<>();
		InventoryDetailWith inventoryDetailWith = new InventoryDetailWith();
		Connection conn = null;
		try {
			conn = inventoryDetailWith.getCon();
			PreparedStatement ps = conn.prepareStatement(sqlFindBy);
			ps.setString(1, rfidCode);
			ps.setInt(2, rftxId);
			ps.setInt(3, rfeId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String Id = rs.getString("Id");
				String ParentId = rs.getString("ParentId");
				int RFEId = rs.getInt("RFEId");
				int RFTXId = rs.getInt("RFTXId");
				String RFIdCode = rs.getString("RFIdCode");
				int InputType = rs.getInt("InputType");
				String ISTime = rs.getString("ISTime");
				String OSTime = rs.getString("OSTime");
				TblRfidData tblRfidData = new TblRfidData(Id, ParentId, RFEId, RFTXId, RFIdCode, InputType, ISTime,
						OSTime);
				tblRfidDataList.add(tblRfidData);
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inventoryDetailWith.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tblRfidDataList;
	}

	/**
	 * 根据状态查询数据返回List<TblRfidData>类型
	 */
	public List<TblRfidData> findByInputType(Integer inputType) {
		List<TblRfidData> tblRfidDataList = new ArrayList<>();
		InventoryDetailWith inventoryDetailWith = new InventoryDetailWith();
		Connection conn = null;
		try {
			conn = inventoryDetailWith.getCon();
			PreparedStatement ps = conn.prepareStatement(sqlFindByType);
			ps.setInt(1, inputType);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String Id = rs.getString("Id");
				String ParentId = rs.getString("ParentId");
				int RFEId = rs.getInt("RFEId");
				int RFTXId = rs.getInt("RFTXId");
				String RFIdCode = rs.getString("RFIdCode");
				int InputType = rs.getInt("InputType");
				String ISTime = rs.getString("ISTime");
				String OSTime = rs.getString("OSTime");
				TblRfidData tblRfidData = new TblRfidData(Id, ParentId, RFEId, RFTXId, RFIdCode, InputType, ISTime,
						OSTime);
				tblRfidDataList.add(tblRfidData);
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inventoryDetailWith.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tblRfidDataList;
	}

	/**
	 * 取当前日期并转化为格式为:yyyy-MM-dd HH:mm:ss的字符串。毫秒部分设置为0
	 *
	 * @return 当前时间按照yyyy-MM-dd HH:mm:ss格式化的字符串。
	 */
	public String getCurrentDateAString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		Date now = new Date();
		String time = sdf.format(now);
		return time;
	}
}
