package com.ufc.user.controller;

import java.util.List;

/**
 * @author DengJie
 * @version 创建时间：2017年7月28日 类说明:
 */
public class Pager<E> {
	// 结果集
	private List<E> List;
	// 结果记录数
	private int Count;
	// 每页多少条数据
	private int PageSize;
	// 第几页
	private int PageNo;

	// 结果总页数
	public int getTotalPages() {
		return (Count + PageSize - 1) / PageSize;
	}
	public List<E> getList() {
		return List;
	}
	public void setList(List<E> list) {
		List = list;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public int getPageSize() {
		return PageSize;
	}
	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}
	public int getPageNo() {
		return PageNo;
	}
	public void setPageNo(int pageNo) {
		PageNo = pageNo;
	}
}