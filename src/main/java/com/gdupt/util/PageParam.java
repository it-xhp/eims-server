package com.gdupt.util;

import cn.hutool.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xuhuaping
 * @date 2022/9/13
 * 表格查询参数
 */
public class PageParam {
	/**
	 * 起始条数
	 */
	public int posStart;
	/**
	 * 一页的条数（页容量）
	 */
	public int count = 30;

	/**
	 * 当前页码
	 */
	private int currentPage;
	/**
	 * 一页的条数（页容量）
	 */
	private int pageSize = 30;

	/**
	 * 排序声明
	 */
	private Map<String,Object> sort = new LinkedHashMap<>();

	/**
	 * 查询条件
	 */
	private JSONObject params = new JSONObject();

	public int getPosStart() {
		return posStart;
	}

	public void setPosStart(int posStart) {
		this.posStart = posStart;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, Object> getSort() {
		return sort;
	}

	public void setSort(Map<String, Object> sort) {
		this.sort = sort;
	}

	public JSONObject getParams() {
		return params;
	}

	public void setParams(JSONObject params) {
		this.params = params;
	}

	public PageParam() {
	}

	public PageParam(int posStart, int count, int currentPage, int pageSize, Map<String, Object> sort, JSONObject params) {
		this.posStart = posStart;
		this.count = count;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.sort = sort;
		this.params = params;
	}
}
