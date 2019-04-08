package com.qfjy.project.weixin.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

public class Character {
	/**
	 * 
	 * 全角转半角 符号，数字，字母 高位为-1，低位全角比半角少32H。 将字母a-z,A-Z，0-9之外的符号转换成为半 *角。
	 * 
	 * 
	 * 
	 * @param SBCString
	 * 
	 * @return 半角 String
	 * 
	 * @author Gyb
	 * 
	 */

	public static String SBC2DBC(String SBCString) {

		if (SBCString == null) {

			return null;

		}

		StringBuffer DBCString = new StringBuffer("");

		try {

			byte[] bytes = null;

			for (int i = 0; i < SBCString.length(); i++) {

				String temp = SBCString.substring(i, i + 1);

				boolean isContainLetters = Pattern.matches("[0-9a-zA-Z]", temp);

				if (!isContainLetters) {

					bytes = temp.getBytes("unicode");

					if (bytes[3] == -1) {

						bytes[2] = (byte) (bytes[2] + 32);

						bytes[3] = 0;

					}

					DBCString.append(new String(bytes, "unicode"));

				} else {

					DBCString.append(temp);

				}

			}

		} catch (UnsupportedEncodingException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return DBCString.toString();

	}

	/**
	 * 
	 * 半角转全角 半角的 符号，数字，字母 高位为0，低位半角比全角多32H。 将字母a-z,A-Z，0-9之外的符号转换成 *为全角，数据库存贮。
	 * 
	 * @param DBCString
	 * 
	 * @return 全角 String
	 * 
	 */

	public static String DBC2SBC(String DBCString) {
		if (DBCString == null) {
			return null;
		}
		StringBuffer SBCString = new StringBuffer("");
		try{
			byte[] bytes = null;
			for (int i = 0; i < DBCString.length(); i++) {
				String temp = DBCString.substring(i, i + 1);
				boolean isContainLetters = Pattern.matches("[0-9a-zA-Z]", temp);
				if (!isContainLetters) {
					bytes = temp.getBytes("unicode");
					if (bytes[3] == 0) {
						bytes[2] = (byte) (bytes[2] - 32);
						bytes[3] = -1;
					}
					SBCString.append(new String(bytes, "unicode"));
				} else {
					SBCString.append(temp);
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SBCString.toString();
	}
	public static void main(String[] args) {
		System.out.println(Character.SBC2DBC("工"));
		//半角转全角
		System.out.println(Character.DBC2SBC("1"));
	}
}
