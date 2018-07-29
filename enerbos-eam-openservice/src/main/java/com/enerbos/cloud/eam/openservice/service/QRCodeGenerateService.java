package com.enerbos.cloud.eam.openservice.service;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.vo.QRCodeVo;
import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年08月02日
 * @Description
 */
public interface QRCodeGenerateService {

    /**
     * findQrcodeImge: 获取二维码图片
     * @param qrCode
     * @param response
     * @throws EnerbosException
     * @throws IOException
     * @throws WriterException
     */
    void findQrcodeImge(QRCodeVo qrCode, HttpServletResponse response) throws EnerbosException, IOException, WriterException;

    /**
     * processQrcodeGeneratePdf: 生成下载pdf
     * @param qrCodes
     * @param response
     * @throws EnerbosException
     * @throws IOException
     * @throws WriterException
     * @throws DocumentException
     */
    void processQrcodeGeneratePdf(List<QRCodeVo> qrCodes, HttpServletResponse response) throws EnerbosException, IOException, WriterException, DocumentException;
}
