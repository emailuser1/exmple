package com.exmple.utils;

public class SimpleResult {
	private Integer errno = 0;
	private String txt = "";
	
	public SimpleResult() {
		
	}
	
	public SimpleResult(Integer errno, String txt) {
		this.errno = errno;
		this.txt = txt;
	}
	
	public int getErrno() {
		return errno;
	}

	public void setErrno(int errno) {
		this.errno = errno;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}
}
