package com.lorin.jedis.service;

import java.io.Serializable;

public class NotifyDO implements Serializable {

	private static final long serialVersionUID = 798327243501197434L;

	private String notifyId;

	private int userId;

	private String title;

	private String content;
	/**
	 */
	private String notify_type;
	/**
	 */
	private String status;

	private String creTime;

	private String modTime;

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(String notifyType) {
		notify_type = notifyType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getModTime() {
		return modTime;
	}

	public void setModTime(String modTime) {
		this.modTime = modTime;
	}

	public String getNotify_level() {
		return notify_level;
	}

	public void setNotify_level(String notifyLevel) {
		notify_level = notifyLevel;
	}

	public String getNotify_way() {
		return notify_way;
	}

	public void setNotify_way(String notifyWay) {
		notify_way = notifyWay;
	}

	/**
	 */
	private String notify_level;

	/**
	 */
	private String notify_way;

	@Override
	public String toString() {
		return "NotifyDO [content=" + content + ", creTime=" + creTime
				+ ", modTime=" + modTime + ", notifyId=" + notifyId
				+ ", notify_level=" + notify_level + ", notify_type="
				+ notify_type + ", notify_way=" + notify_way + ", status="
				+ status + ", title=" + title + ", userId=" + userId + "]";
	}
	
}
