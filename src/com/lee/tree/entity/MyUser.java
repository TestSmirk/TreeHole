package com.lee.tree.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
/**
 * ”√ªß
 * @author lee
 *
 */
public class MyUser extends BmobUser {
private String gender ;
private BmobFile pic;
public String getGender() {
	return gender;
}

public BmobFile getPic() {
	return pic;
}

public void setPic(BmobFile pic) {
	this.pic = pic;
}

public void setGender(String gender) {
	this.gender = gender;
}
}
