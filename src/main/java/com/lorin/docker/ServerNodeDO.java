/**
 * 服务器节点
 */
package com.lorin.docker;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author mahaiyuan@55tuan.com
 * @since 2015年6月9日上午10:26:50
 */
public class ServerNodeDO {

	private static final long serialVersionUID = 1L;
	private int id;
	private int idcId; // 机房id
	
	private String name; // 机器名
	private Integer cpuNum; // CPU数量
	private Integer memorySize; // 内存大小
	private String address; // 机器地址
	private String sslCertificate;
	private String sslKey;
	private String caCertificate;
	private String remarks; // 备注
	private Date addTime; // 添加时间
	private Date updateTime; // 最后更新时间

	private Integer imageNum; // 节点下镜像数量
	private Integer containerNum; // 容器数量
	private Integer runContainerNum; // 运行中容器数量
	
	private String idcName;// 页面显示机房名称
	
	private String rule;//角色 slave or master
	private String rack;//机架
	private int serviceNum;//机器几点下的服务数量
	private int status;//节点状态 1：有效 2：无效
	private String ip;
	//服务组id
	String[] sgroupId;
	
	/*
	 * 主机上的服务应用所加字段。
	 * 本着程序的可复用行所加； 因为这些字段可以新建一个表，
	 * 但改动过大，所以加到jeehe_server_node表中 并已type区分；
	 */
	public int type;//1:主机节点 2：主机节点上的服务应用
	public String apiUrl;//服务应用的api地址 可有可无
	public Date deployTime;//服务应用的部署时间；
	public int appStatus;//服务应用的状态；
	public int deployStatus;//服务应用的部署状态；
	public String env;//环境；只有haproxy和consul-teamplate才有环境
	public String username;//用户名
	public String password;//密码
	public int parentId;//所属主机节点id
	
	

	public ServerNodeDO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCpuNum() {
		return cpuNum;
	}

	public void setCpuNum(Integer cpuNum) {
		this.cpuNum = cpuNum;
	}

	public Integer getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(Integer memorySize) {
		this.memorySize = memorySize;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSslCertificate() {
		return sslCertificate;
	}

	public void setSslCertificate(String sslCertificate) {
		this.sslCertificate = sslCertificate;
	}

	public String getSslKey() {
		return sslKey;
	}

	public void setSslKey(String sslKey) {
		this.sslKey = sslKey;
	}

	public String getCaCertificate() {
		return caCertificate;
	}

	public void setCaCertificate(String caCertificate) {
		this.caCertificate = caCertificate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getImageNum() {
		return imageNum;
	}

	public void setImageNum(Integer imageNum) {
		this.imageNum = imageNum;
	}

	public Integer getContainerNum() {
		return containerNum;
	}

	public void setContainerNum(Integer containerNum) {
		this.containerNum = containerNum;
	}

	public Integer getRunContainerNum() {
		return runContainerNum;
	}

	public void setRunContainerNum(Integer runContainerNum) {
		this.runContainerNum = runContainerNum;
	}

	public int getIdcId() {
		return idcId;
	}

	public void setIdcId(int idcId) {
		this.idcId = idcId;
	}

	public String getIdcName() {
		return idcName;
	}

	public void setIdcName(String idcName) {
		this.idcName = idcName;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getIp() {
		if(StringUtils.isNotBlank(address)){
			ip = getRealIp(address);
		}
		return ip;
	}
	
	public String getRack() {
		return rack;
	}

	public void setRack(String rack) {
		this.rack = rack;
	}

	public int getServiceNum() {
		return serviceNum;
	}

	public void setServiceNum(int serviceNum) {
		this.serviceNum = serviceNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRealIp(String address){
		String regex = "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)){3}";
		Pattern p = Pattern.compile(regex);  
	    Matcher m = p.matcher(address);  
	    String ip = "";
	    while (m.find()) {  
	        ip = m.group();  
	        break;
	    } 
	    return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String[] getSgroupId() {
		return sgroupId;
	}

	public void setSgroupId(String[] sgroupId) {
		this.sgroupId = sgroupId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public Date getDeployTime() {
		return deployTime;
	}

	public void setDeployTime(Date deployTime) {
		this.deployTime = deployTime;
	}

	public int getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(int appStatus) {
		this.appStatus = appStatus;
	}

	public int getDeployStatus() {
		return deployStatus;
	}

	public void setDeployStatus(int deployStatus) {
		this.deployStatus = deployStatus;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
