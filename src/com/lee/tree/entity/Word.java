package com.lee.tree.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Word extends BmobObject implements Serializable{
private String content;//心情内容
private Boolean isShare;//是否公开
private MyUser auther;//作者

public MyUser getAuther() {
	return auther;
}
public void setAuther(MyUser auther) {
	this.auther = auther;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public Boolean getIsShare() {
	return isShare;
}
public void setIsShare(Boolean isShare) {
	this.isShare = isShare;
} 
}
