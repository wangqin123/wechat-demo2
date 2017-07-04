package com.wq.wechat.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bee(Mifeng.He)
 * @desc Page封装 
 * @param <T>
 */
public class Page<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String code;
	private boolean success = true;
	private String msg;
	private T t;
	private String xml;
	private String url;
	private List<T> list = new ArrayList<T>();
	private Map<String, Object> map = new HashMap<String, Object>();
	
	private Map<String, Object> params = new HashMap<String, Object>();
	private Integer pageNo = 1;
	private Integer pageSize = 5;
	private Long pages = 0L;
	private Long totalSize = 0L;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	public String getXml() {
		return xml;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Long getPages() {
		return pages;
	}
	public void setPages(Long pages) {
		this.pages = pages;
	}
	public Long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}
	
}
