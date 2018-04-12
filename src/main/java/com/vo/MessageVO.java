package com.vo;


/**
 * 
 * @author abc
 * 用来做异常处理的消息类
 *
 * @param <T>
 */
public class MessageVO<T> {
	private Integer mID;// 消息ID
	private String mName;// 消息名
	private String mContent;// 消息内容
	private String mTitle;// 消息标题
	private Integer mType;// 消息类型
	private T dataModel;// 得到的数据

	public Integer getmID() {
		return mID;
	}

	public void setmID(Integer mID) {
		this.mID = mID;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmContent() {
		return mContent;
	}

	public void setmContent(String mContent) {
		this.mContent = mContent;
	}

	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public Integer getmType() {
		return mType;
	}

	public void setmType(Integer mType) {
		this.mType = mType;
	}

	public T getDataModel() {
		return dataModel;
	}

	public void setDataModel(T dataModel) {
		this.dataModel = dataModel;
	}

}
