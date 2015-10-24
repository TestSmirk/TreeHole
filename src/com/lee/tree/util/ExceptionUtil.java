package com.lee.tree.util;


import java.io.PrintWriter;
import java.io.StringWriter;




/**
 * 处理项目中所有的异常
 * 
 * @author Lee
 * 
 */
public class ExceptionUtil {

	public static void exceptionHandle(Exception e) {
		if (1==2) {
			// 把异常信息变成字符串，发给开发人员
			String str = "";

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			str = stringWriter.toString();
			
			// 联网发送
		} else {
			e.printStackTrace();
		}
	}

	public static void handleException(Exception e) {
		// TODO Auto-generated method stub
		
	}
}
