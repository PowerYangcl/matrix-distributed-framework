package com.matrix.helper;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @description: 转换|格式化等操作
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年10月18日 下午2:20:02 
 * @version 1.0.0
 */
public class FormatHelper {


	/**
	 * @param sBaseString 输入字符串
	 * @param sFromStrings  替换字符串组
	 * @return 返回替换后结果
	 */
	public static String formatString(String sBaseString, Object... sFromStrings) {
		for (int i = 0 ; i < sFromStrings.length; i++) {
			sBaseString = sBaseString.replace("{" + (i) + "}", sFromStrings[i].toString());
		}
		return sBaseString;
	}

	/**
	 * 强制进制转换函数 用数字表示一个字符串或者反向转换
	 * 
	 * @param sInput
	 * @param sParam
	 * @return
	 */
	public static String convertFormatStringNumber(String sInput, String sParam) {

		char[] cNumber = sParam.toCharArray();
		char[] cInfo = sInput.toCharArray();
		int iNumLength = cNumber.length;

		BigInteger bNumLength = BigInteger.valueOf(iNumLength);

		BigInteger lReturnNum = BigInteger.ZERO;

		for (int i = 0, j = cInfo.length; i < j; i++) {
			int iNow = 0;
			for (int n = 0; n < iNumLength; n++) {
				if (cNumber[n] == cInfo[i]) {
					iNow = n + 1;
				}
			}

			lReturnNum = lReturnNum.add(BigInteger.valueOf(iNow).multiply(bNumLength.pow(j - i - 1)));

			// lReturnNum += iNow * Math.pow(iNumLength, j - i - 1);
		}

		return lReturnNum.toString();
	}

	public static String convertFormatNumberBack(String dSource, String sParam) {

		char[] cNumber = sParam.toCharArray();

		int iLength = cNumber.length;

		int iStep = 0;

		BigInteger bSource = new BigInteger(dSource);

		ArrayList<Integer> aList = new ArrayList<Integer>();

		while (bSource.divide(BigInteger.valueOf(iLength).pow(iStep)).compareTo(BigInteger.ONE) != -1) {

			int iNow = bSource.remainder(BigInteger.valueOf(iLength).pow(iStep + 1))
					.divide(BigInteger.valueOf(iLength).pow(iStep)).intValue();

			if (iNow == 0) {
				iNow = iLength;
			}
			bSource = bSource.subtract(BigInteger.valueOf(iNow).multiply(BigInteger.valueOf(iLength).pow(iStep)));
			aList.add(iNow);
			iStep++;
		}

		StringBuffer sBuffer = new StringBuffer();
		for (int i = aList.size() - 1; i >= 0; i--) {
			if (aList.get(i) == 0) {
				aList.set(i, iLength);
			}
			sBuffer.append(cNumber[aList.get(i) - 1]);
		}
		return sBuffer.toString();
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String upDateTime() {
		return upDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 减小时
	 * 
	 * @return
	 */
	public static String minusDate(int hour) {
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateMinusOneHours = df.format(new Date(d.getTime() - hour * (60 * 60 * 1000)));
		// System.out.println("三天后的日期：" + df.format(new Date(d.getTime() + 3 *
		// 24 * 60 * 60 * 1000)));
		return dateMinusOneHours;
	}

	/**
	 * 将指定日期增加执行参数
	 * 
	 * @param date
	 * @param iType
	 * @param iAmount
	 * @return
	 */
	public static String upDateTimeAdd(Date date, int iType, int iAmount) {

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.DATE, iAmount);

		return upDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");

	}

	/**
	 * 返回当前时间增加指定的时间格式
	 * 
	 * @param sExp
	 *            d结尾表示天数
	 * @return
	 */
	public static String upDateTimeAdd(String sExp) {

		String sEndType = sExp.substring(sExp.length() - 1);

		int iExp = Integer.valueOf(sExp.substring(0, sExp.length() - 1));

		String sExpString = "";

		if (sEndType.equals("d")) {
			sExpString = upDateTimeAdd(new Date(), Calendar.DATE, iExp);
		} else {
			sExpString = upDateTime();
		}

		return sExpString;

	}

	/**
	 * 时间格式化
	 * 
	 * @param dDate
	 * @param sPattern
	 * @return
	 */
	public static String upDateTime(Date dDate, String sPattern) {
		SimpleDateFormat sFormat = new SimpleDateFormat(sPattern);
		return sFormat.format(dDate);
	}

	/**
	 * 时间格式化
	 * 
	 * @param sPattern
	 * @return
	 */
	public static String upDateTime(String sPattern) {
		SimpleDateFormat sFormat = new SimpleDateFormat(sPattern);
		return sFormat.format(new Date());
	}

	/**
	 * 获取16进制表示的当前时间
	 * 
	 * @return
	 */
	public static String upDateHex() {

		return Integer.toHexString(Integer.valueOf(upDateTime("yyMMdd")));
	}

	public static Date parseDate(String sDateTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return sdf.parse(sDateTime);
	}

	/**
	 * 获取url结构表示出来的字符串参数 a=b&c=d 表示为[a,b,c,d]
	 * 
	 * @param sUrl
	 * @return
	 */
	public static String[] upUrlStrings(String sUrl) {
		ArrayList<String> aList = new ArrayList<String>();
		if (sUrl.indexOf("?") > -1) {
			sUrl = sUrl.substring(sUrl.indexOf("?"));
		}
		for (String sIn : sUrl.split("&")) {
			aList.add(StringUtils.substringBefore(sIn, "="));

			aList.add(StringUtils.substringAfter(sIn, "="));
		}

		return aList.toArray(new String[] {});
	}

	public static String upReplaceSerialize(String sKey) {
		return sKey.replace("_", "").toLowerCase();
	}

	/**
	 * 获取sql格式化
	 * 
	 * @param sSql
	 * @return
	 */
	public static String upReplaceSql(String sSql) {
		return sSql.replaceAll(".*([';]+|(--)+).*", " ");
	}

	/**
	 * 用逗号合并输入数组
	 * 
	 * @param sKeys
	 * @return
	 */
	public static String join(String... sKeys) {
		return StringUtils.join(sKeys, ",");
	}

	public static String upStringFromObject(Object object) {

		String sReturnString = null;

		if (object != null)

		{
			sReturnString = object.toString();
		}
		return sReturnString;

	}

}
