package com.enerbos.cloud.eam.contants;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年8月10日
 * @Description 
 */

public class QRCodeManagerCommon {

	//二维码编码分隔数字，查询二维码根据编码截取第一个0分隔成两个字符串
	public static final String QRCODENUMDIVISION = "0";

	//二维码设备设施台账应用程序value
	public static final String ASSETAPPVALUE = "asset";

	//二维码仪表台账应用程序value
	public static final String METERAPPVALUE = "meter";

	//有二维码功能的主表存储二维码编码后缀的返回字段名
	public static final String PRODUCT = "EAM";

	//有二维码功能的主表存储二维码编码后缀的返回字段名
	public static final String COMPANY = "翼虎能源<%s>";

	//有二维码功能的主表存储二维码编码后缀的返回字段名
	public static final String APPLICATION_QRCODENUM = "qrCodeNum";
	//二维码管理使用字符编码以及图片格式名
	public static final String FILE_NAME = "二维码.pdf";
	//二维码管理使用字符编码以及图片格式名
	public static final String CHARSET = "utf-8";
	public static final String FORMAT_NAME = "JPG";
	// 二维码尺寸
	public static final int QRCODE_SIZE = 160;
	// LOGO宽度
	public static final int WIDTH = 40;
	// LOGO高度
	public static final int HEIGHT = 40;
	//总的宽度
	public static final int IMAGE_WIDTH = 552;
	//总的高度
	public static final int IMAGE_HEIGHT = 300;
	//字体大小
	public static final int FONT_SIZE = 22;
	//字体类型
	public static final String FONT_FAMLIY = "宋体";
	//二维码右侧基本信息字体大小
	public static final int FONT_BASIC_SIZE = 16;
}
