package com.lorin.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class TestHbase {

	private static Configuration conf = null;
	
	static {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.property.clientPort","2181");
		conf.set("hbase.zookeeper.quorum", "192.168.136.135");
		conf.set("hbase.master", "192.168.136.135:60000");
	}
	
	public static void createTable() throws Exception{
		String tableName = "user";
		HBaseAdmin hBaseAdmin = new HBaseAdmin(conf);
		if(hBaseAdmin.tableExists(tableName)){
			hBaseAdmin.disableTable(tableName);
			hBaseAdmin.disableTable(tableName);
		}
		HTableDescriptor tableDescriptor = new 
				HTableDescriptor(TableName.valueOf(tableName));
		tableDescriptor.addFamily(new HColumnDescriptor("info"));
		tableDescriptor.addFamily(new HColumnDescriptor("class"));
		tableDescriptor.addFamily(new HColumnDescriptor("parent"));
		hBaseAdmin.createTable(tableDescriptor);
		hBaseAdmin.close();
	}
	
	public static void putRow() throws Exception{
		String tableName = "user";
		String[] familyNames = {"info","class","parent"};
		HTable table = new HTable(conf,tableName);
		for(int i = 0;i<20;i++){
			for(int j=0;j<familyNames.length;j++){
				Put put = new Put(Bytes.toBytes(i+""));
				put.add(Bytes.toBytes(familyNames[j]),
						Bytes.toBytes("col"),
						Bytes.toBytes("value_"+i+"_"+j));
				table.put(put);
			}
		}
		table.close();
	}
	
	public static void getRow() throws Exception{
		String tableName = "user";
		String rowKey = "1";
		HTable table = new HTable(conf,tableName);
		Get g = new Get(Bytes.toBytes(rowKey));
		Result r = table.get(g);
		outputResult(r);
		table.close();
	}
	
	public static void scanTable() throws Exception{
		String tableName = "user";
		HTable table = new HTable(conf,tableName);
		Scan s = new Scan();
		ResultScanner rs = table.getScanner(s);
		for(Result r : rs){
			outputResult(r);
		}
		
		//设置startrow和endrow 进行查询
		s = new Scan("2".getBytes(),"6".getBytes());
		rs = table.getScanner(s);
		for(Result r : rs){
			outputResult(r);
		}
		table.close();
	}
	
	public static void deleteRow() throws Exception{
		String tableName = "user";
		String rowKey = "1";
		HTable table = new HTable(conf,tableName);
		List<Delete> list = new ArrayList<Delete>();
		Delete d = new Delete(rowKey.getBytes());
		list.add(d);
		table.delete(list);
		table.close();
	}
	
	public static void outputResult(Result rs){
		List<Cell> list = rs.listCells();
		for(Cell cell : list){
			System.out.println("family : " + new String(cell.getFamily()) 
			+ ", col : " + new String(cell.getQualifier())
			+ ", value : "+new String(cell.getValue()));
		}
	}
	
	public static void main(String[] args) throws Exception{
		createTable();
		putRow();
		getRow(); 
		scanTable();
	}
}
