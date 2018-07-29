package com.enerbos.cloud.eam.openservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.contants.QRCodeManagerCommon;
import com.enerbos.cloud.eam.openservice.service.QRCodeGenerateService;
import com.enerbos.cloud.eam.vo.QRCodeVo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import sun.awt.SunHints;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
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
@Service
public class QRCodeGenerateServiceImpl implements QRCodeGenerateService {

    @Value("${qrcodePath}")
    private String qrcodePath;

    @Override
    public void findQrcodeImge(QRCodeVo qrCode, HttpServletResponse response) throws EnerbosException, IOException, WriterException {
        //生成二维码基本图片
        BufferedImage image = createImage(qrCode.getContent());
        // 插入图片
        insertImage(image, qrcodePath, true);
        updateImage(image,160,160);
        response.setContentType("image/jpeg");
        ImageIO.write(image, QRCodeManagerCommon.FORMAT_NAME, response.getOutputStream());
        image.flush();
    }

    @Override
    public void processQrcodeGeneratePdf(List<QRCodeVo> qrCodes, HttpServletResponse response) throws EnerbosException, IOException, WriterException, DocumentException {
        //创建文件
        Document document = new Document(PageSize.A4);
        //建立一个书写器
        PdfWriter writer = PdfWriter.getInstance(document,response.getOutputStream());
        String filename = new String(QRCodeManagerCommon.FILE_NAME.getBytes(QRCodeManagerCommon.CHARSET), "ISO8859-1");
        response.reset();
        response.setContentType("application/pdf");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+java.net.URLEncoder.encode(filename, "ISO8859-1"));
        response.addHeader("Cache-Control", "no-cache");
        document.open();
        //PDF版本(默认1.4)
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
        float width=document.getPageSize().getWidth()-75;//取页面宽度并减去页边距
        float height=document.getPageSize().getHeight()-50;//取页面高度并减去页边距
        float imgwidth = 276f;
        float imghight = 150f;
        float imgx = 20f;
        float imgy = height-imghight;
        document.newPage();
        for (int i=0;i<qrCodes.size();i++){
            QRCodeVo qrCode=qrCodes.get(i);
            //生成二维码基本图片
            BufferedImage image = createImage(qrCode.getContent());
            // 插入图片
            insertImage(image, qrcodePath, true);
            //插入文字描述
            BufferedImage image1 = insertString(image,qrCode);

            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(image1, QRCodeManagerCommon.FORMAT_NAME, imOut);
            //document.
            com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(bs.toByteArray());//选择图片
            img.scaleAbsolute(imgwidth,imghight);
            //img.setRotationDegrees(90);
            img.setAbsolutePosition(imgx,imgy);
            //scaleAbsolute
            document.add(img);
            if (i!=qrCodes.size()-1){
                if((width-(imgx+imgwidth+5))>0){
                    imgx = imgx + imgwidth + 5;
                }else {
                    imgx = 20f;
                    if((imgy-imghight-5)>0){
                        imgy = imgy - imghight - 5;
                    }else {
                        document.newPage();
                        imgy = height-imghight;
                    }
                }
            }
        }
        document.close();
    }

    private static BufferedImage createImage(String content) throws WriterException, IOException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, QRCodeManagerCommon.CHARSET);
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, 90, 90, hints);

        //去掉白边
        bitMatrix = updateBit(bitMatrix, 0);  //生成新的bitMatrix

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000: 0xFFFFFFFF);
            }
        }
        return updateImage(image,QRCodeManagerCommon.QRCODE_SIZE,QRCodeManagerCommon.QRCODE_SIZE);
    }

    /**
     * 插入LOGO
     * @param source 二维码图片
     * @param imgPath LOGO图片地址
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws IOException {
        InputStream resouceStream = this.getClass().getClassLoader().getResourceAsStream(imgPath);
        if (resouceStream == null) {
            return;
        }
        Image src = ImageIO.read(resouceStream);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > QRCodeManagerCommon.WIDTH) {
                width = QRCodeManagerCommon.WIDTH;
            }
            if (height > QRCodeManagerCommon.HEIGHT) {
                height = QRCodeManagerCommon.HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCodeManagerCommon.QRCODE_SIZE - width) / 2;
        int y = (QRCodeManagerCommon.QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 放大或缩小图片
     * @param originalImage 源图片
     * @param width 图片的宽
     * @param height 图片的高
     * @return 新的图片流
     */
    public static BufferedImage updateImage(BufferedImage  originalImage, int width, int height){
        BufferedImage newImage = new BufferedImage(width,height,originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0,0,width,height,null);
        g.dispose();
        return newImage;
    }

    /**
     * 设置二维码白边的大小
     * @param matrix
     * @param margin
     * @return
     */
    private static BitMatrix updateBit(BitMatrix matrix, int margin){
        int tempM = margin*2;
        int[] rec = matrix.getEnclosingRectangle();   //获取二维码图案的属性
        int resWidth = rec[2] + tempM;
        int resHeight = rec[3] + tempM;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
        resMatrix.clear();
        for(int i= margin; i < resWidth- margin; i++){   //循环，将二维码图案绘制到新的bitMatrix中
            for(int j=margin; j < resHeight-margin; j++){
                if(matrix.get(i-margin + rec[0], j-margin + rec[1])){
                    resMatrix.set(i,j);
                }
            }
        }
        return resMatrix;
    }

    /**
     * 插入文字描述
     * @param source
     * @param qrCode
     * @throws IOException
     */
    private static BufferedImage insertString(BufferedImage source, QRCodeVo qrCode) throws IOException {
        // 创建BufferedImage对象
        BufferedImage image2 = new BufferedImage(QRCodeManagerCommon.IMAGE_WIDTH, QRCodeManagerCommon.IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB);
        // 获取Graphics2D
        Graphics2D g2d = image2.createGraphics();
        g2d.setRenderingHint(SunHints.KEY_ANTIALIASING, SunHints.VALUE_ANTIALIAS_ON);
        // 画图
        g2d.setBackground(Color.white);
        g2d.setColor(Color.black);
        g2d.clearRect(0, 0, QRCodeManagerCommon.IMAGE_WIDTH, QRCodeManagerCommon.IMAGE_HEIGHT);
        //设置边框
        BasicStroke wideStroke = new BasicStroke(1f);
        g2d.setStroke(wideStroke);
        g2d.drawRect(16,16,QRCodeManagerCommon.IMAGE_WIDTH-33,QRCodeManagerCommon.IMAGE_HEIGHT-33);
        g2d.drawLine(16,68,QRCodeManagerCommon.IMAGE_WIDTH-18,68);
        //g2d.drawRect(10,40,IMAGE_WIDTH-21,1);
        //添加标题
        Font font3=new Font(QRCodeManagerCommon.FONT_FAMLIY,Font.PLAIN,22);
        g2d.setFont(font3);
        String title = qrCode.getTitle();
        int strWidth1 = g2d.getFontMetrics().stringWidth(title);
        int strwidth = (QRCodeManagerCommon.IMAGE_WIDTH-strWidth1)/2;
        int titlty = 16+QRCodeManagerCommon.FONT_SIZE+(52-QRCodeManagerCommon.FONT_SIZE)/2;
        g2d.drawString(title,strwidth,titlty);
        //画二维码到新的面板
        g2d.drawImage(source, 36, 84, source.getWidth(), source.getHeight(), null);
        Font font=new Font(QRCodeManagerCommon.FONT_FAMLIY,Font.PLAIN,QRCodeManagerCommon.FONT_BASIC_SIZE);
        g2d.setFont(font);
        int h = 42;
        if(qrCode.getInfolist()!=null&&qrCode.getInfolist().size()>0){
            h = 84+QRCodeManagerCommon.FONT_BASIC_SIZE+QRCodeManagerCommon.FONT_BASIC_SIZE+2;
            for(String info : qrCode.getInfolist()){
                int str = g2d.getFontMetrics().stringWidth(info);
                char[] ss=new char[50];
                info.getChars(0,info.length(),ss,0);
                if(str<=272){
                    g2d.drawString(info,218,h);
                } else {
//                    String info1 = info.substring(0, 272/QRCodeManagerCommon.FONT_BASIC_SIZE);
//                    String info2 = info.substring(272/QRCodeManagerCommon.FONT_BASIC_SIZE, info.length());
//                    int str2 = g2d.getFontMetrics().stringWidth(info2);
//                    if(str2>272){
//                        info2 = info2.substring(0, info.length()-2);
//                        info2 = info2+"..";
//                    }
//                    g2d.drawString(info1,218,h);
//                    g2d.drawString(info2,218+4*QRCodeManagerCommon.FONT_BASIC_SIZE+QRCodeManagerCommon.FONT_BASIC_SIZE/2,h+QRCodeManagerCommon.FONT_BASIC_SIZE);
                    info = splitString(info);
                    info = info+"..";
                    g2d.drawString(info,218,h);
                }
                h = h+QRCodeManagerCommon.FONT_BASIC_SIZE+QRCodeManagerCommon.FONT_BASIC_SIZE+4;
            }
        }
        Font font1=new Font(QRCodeManagerCommon.FONT_FAMLIY,Font.PLAIN,16);
        g2d.setFont(font1);
        int numwidthstr = g2d.getFontMetrics().stringWidth(qrCode.getQRCodeNum());
        int numwidth = (QRCodeManagerCommon.QRCODE_SIZE-numwidthstr)/2 + 20+16;
        g2d.drawString(qrCode.getQRCodeNum(),numwidth,270);
        Font font2=new Font(QRCodeManagerCommon.FONT_FAMLIY,Font.PLAIN,12);
        g2d.setFont(font2);
        String gsstr =String.format(QRCodeManagerCommon.COMPANY,qrCode.getModelName());
        int gsstrwidth = g2d.getFontMetrics().stringWidth(gsstr);
        g2d.drawString(gsstr,image2.getWidth()-gsstrwidth-20,270);
        //释放对象
        g2d.dispose();
        source.flush();
        return image2;
    }

    /**
     * 根据输入字符串截取小于等于51个字节，不出现截半
     * @param src
     * @return
     */
    private static String splitString(String src) {
        byte bt[] = src.getBytes(); // 将String转换成byte字节数组

        String subStrx = new String(bt, 0, 52);
        subStrx = subStrx.substring(0,subStrx.length()-1);
        return subStrx;
    }
}
