package com.siganid.web.util;

public class DownFileOperatorInfo {

	String fileName;
	long downLength;
	long fileLength;
	boolean result;
	long speed;
	int errorTimes;
	String viewKey;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public int getErrorTimes() {
		return errorTimes;
	}

	public void setErrorTimes(int errorTimes) {
		this.errorTimes = errorTimes;
	}

	public String getViewKey() {
		return viewKey;
	}

	public void setViewKey(String viewKey) {
		this.viewKey = viewKey;
	}

	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getDownLength() {
		return downLength;
	}

	public void setDownLength(long downLength) {
		this.downLength = downLength;
	}

	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	public String getProcessString() {
		try {
			return getDownLength() * 100 / getFileLength() + "%";
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "";
	}
}
