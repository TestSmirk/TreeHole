package com.lee.tree.util;


import java.io.PrintWriter;
import java.io.StringWriter;




/**
 * ������Ŀ�����е��쳣
 * 
 * @author Lee
 * 
 */
public class ExceptionUtil {

	public static void exceptionHandle(Exception e) {
		if (1==2) {
			// ���쳣��Ϣ����ַ���������������Ա
			String str = "";

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			str = stringWriter.toString();
			
			// ��������
		} else {
			e.printStackTrace();
		}
	}

	public static void handleException(Exception e) {
		// TODO Auto-generated method stub
		
	}
}
