package com.xzbd.mail.utils;

import com.sun.tools.javac.util.Assert;
import com.xzbd.mail.utils.excelUtils.BiExcelUtils2007;
import com.xzbd.mail.utils.excelUtils.ExcelProperty;
import com.xzbd.mail.utils.excelUtils.ExcelSheetProperty;
import com.xzbd.mail.utils.mailContentUtil.AbstractEmailContentItem;
import com.xzbd.mail.utils.mailContentUtil.ExcelEmailContentItem;
import com.xzbd.mail.utils.mailContentUtil.TextEmailContentItem;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class BiEmailUtils {

    public static Integer MAX_ROW_NUMBER_2003 = 65536;
    public static Integer MAX_ROW_NUMBER_2007 = 1000000;

    private static Logger logger = LoggerFactory.getLogger(BiEmailUtils.class);



    /**
     * 初始化session
     *
     * @return
     */
    public static Session initSession( Properties props) {

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log
        return session;
    }

    /**
     * 设置发件人
     *
     * @param message
     * @param fromAccountAdr
     * @param nikeName
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public static void setFromAccoutn(MimeMessage message, String fromAccountAdr, String nikeName)
            throws UnsupportedEncodingException, MessagingException {
        Assert.checkNonNull(message, "message 不能为空");
        Assert.checkNonNull(fromAccountAdr, "发件人不能为空");
        if (StringUtils.isBlank(nikeName)) {
            nikeName = "xw-mail";
        }
        message.setFrom(new InternetAddress(fromAccountAdr, nikeName, "UTF-8"));
    }

    /**
     * 设置收件人 支持给多人发送，若给多人发送，将多个地址用·,·拼接即可。如：aa@bb.com,cc@dd.com
     *
     * @param message
     * @param toAccountAdr
     * @throws MessagingException
     */
    public static void setToAccoutn(MimeMessage message, List<String> toAccountAdr)
            throws MessagingException {
        Assert.checkNonNull(message, "message 不能为空");
        Assert.checkNonNull(toAccountAdr, "收件人不能为空");
        Assert.check(toAccountAdr.size() > 0, "收件人不能为空");
        List<InternetAddress> list = toInternetAddressList(toAccountAdr);
        InternetAddress[] address = list.toArray(new InternetAddress[list.size()]);
        Assert.checkNonNull(list.size() > 0, "收件人不能为空");
        message.addRecipients(MimeMessage.RecipientType.TO, address);
    }

    private static List<InternetAddress> toInternetAddressList(List<String> toAccountAdr) throws AddressException {
        List<InternetAddress> list = new ArrayList<>();// 不能使用string类型的类型，这样只能发送一个收件人
        for (String str : toAccountAdr) {
            if (StringUtils.isBlank(str))
                continue;
            list.add(new InternetAddress(str));
        }
        return list;
    }


    /**
     * 设置抄送人
     *
     * @param message
     * @return
     * @throws AddressException
     * @throws MessagingException
     */
    public static void setCc(MimeMessage message, List<String> ccList)
            throws AddressException, MessagingException {
        Assert.checkNonNull(message, "message 不能为空");
        if (CollectionUtils.isEmpty(ccList))
            return;
        List<InternetAddress> list = toInternetAddressList(ccList);
        InternetAddress[] address = list.toArray(new InternetAddress[list.size()]);
        message.setRecipients(MimeMessage.RecipientType.CC, address);
    }

    /**
     * 设置密送
     *
     * @param message
     * @throws AddressException
     * @throws MessagingException
     */
    public static void setBCCto(MimeMessage message, List<String> bccList)
            throws AddressException, MessagingException {
        Assert.checkNonNull(message, "message 不能为空");
        if (CollectionUtils.isEmpty(bccList))
            return;
        List<InternetAddress> list = toInternetAddressList(bccList);
        InternetAddress[] address = list.toArray(new InternetAddress[list.size()]);
        message.setRecipients(MimeMessage.RecipientType.BCC, address);
    }


    /**
     *  通知系统维护人员
     * @param sender
     * @param toAccount
     * @param copyto
     * @param bccto
     * @param subject
     * @param content
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    private static void sendMailNoticfySystem(SenderProperties sender, List<String> toAccount, List<String> copyto,
                                              List<String> bccto, String subject, String content)
            throws UnsupportedEncodingException, MessagingException {
        System.setProperty("mail.mime.splitlongparameters", "false");
        Session session = BiEmailUtils.initSession(sender.getProperties());
        // 1.创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 2.设置发件人/收件人
        BiEmailUtils.setFromAccoutn(message, sender.getSenderAddr(), sender.getSenderName());
        BiEmailUtils.setToAccoutn(message, toAccount);
        // 3.设置抄送人、密送
        BiEmailUtils.setCc(message, copyto);
        BiEmailUtils.setBCCto(message, bccto);
        // 4.设置文本内容
        message.setText(content);
        // 6.设置主题
        message.setSubject(subject, "UTF-8");
        // 7. 设置发件时间
        message.setSentDate(new Date());
        // 8. 保存上面的所有设置
        message.saveChanges();
        // 9. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        // 10. 使用 邮箱账号 和 密码 连接邮件服务器
        // 这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
        transport.connect(sender.getSenderAddr(), sender.getSenderPwd());
        // 11. 发送邮件, 发到所有的收件地址, message.getAllRecipients()
        // 获取到的是在创建邮件对象时添加的所有收件人,抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        // 12. 关闭连接
        transport.close();
    }

    /**
     *  发送邮件 支持多个附件，多个文件内容拼接
     * @param sender
     * @param toList
     * @param ccList
     * @param bccList
     * @param maintainerAddrs
     * @param subject
     * @param emailContents
     * @param configFilePath
     * @throws Exception
     */

    public static void sendMail(SenderProperties sender, List<String> toList,
                                List<String> ccList, List<String> bccList,
                                String maintainerAddrs, String subject,
                                List<AbstractEmailContentItem> emailContents,
                                String configFilePath) throws Exception {
        System.setProperty("mail.mime.splitlongparameters", "false");
        Session session = BiEmailUtils.initSession(sender.getProperties());
        // 1.创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 2.设置发件人/收件人、设置抄送人、密送
        setFromAccoutn(message, sender.getSenderAddr(), sender.getSenderName());
        setToAccoutn(message, toList);
        setCc(message, ccList);
        setBCCto(message, bccList);

        // 5 邮件内容
        MimeMultipart multipart = new MimeMultipart();
        multipart.setSubType("mixed");

        //分类组装邮件内容
        for (AbstractEmailContentItem item : emailContents) {
            BodyPart bodyPart = new MimeBodyPart();

            //文本内容
            if (item instanceof TextEmailContentItem) {
                bodyPart.setText(((TextEmailContentItem) item).getContent());

                //生成的Excel附件
            } else if (item instanceof ExcelEmailContentItem) {

                //生成Excel文件
                String filePath = buildExcelDefault(configFilePath, ((ExcelEmailContentItem) item).getExcel());
                File originFile = new File(filePath);
                DataHandler dh2 = new DataHandler(new FileDataSource(originFile)); // 读取本地文件
                bodyPart.setDataHandler(dh2); // 将附件数据添加到“节点”
                bodyPart.setFileName(MimeUtility.encodeText(originFile.getName()));
            } else {
                //TODO 自定义其他邮件类型……
            }
            multipart.addBodyPart(bodyPart);
        }
        // 将multipart对象放到message中
        message.setContent(multipart);

        // 6.设置主题
        message.setSubject(subject, "UTF-8");
        // 7. 设置发件时间
        message.setSentDate(new Date());
        // 8. 保存上面的所有设置
        message.saveChanges();
        // 9. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        // 10. 使用 邮箱账号 和 密码 连接邮件服务器
        // 这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
        transport.connect(sender.getSenderAddr(), sender.getSenderPwd());

        try {
            // 11. 发送邮件, 发到所有的收件地址, message.getAllRecipients()
            // 获取到的是在创建邮件对象时添加的所有收件人,抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
        } catch (SendFailedException e) {
            e.printStackTrace();
            Address[] _invalid = e.getInvalidAddresses();
            Address[] _validUnsent = e.getValidUnsentAddresses();
            // Address[] _validSent = e.getValidSentAddresses();
            //TODO 重新发送  maintainerAddrs
            // 通知邮件维护员

        }
        // 12. 关闭连接
        transport.close();
    }


    /**
     * 创建sheet并写入数据
     *
     * @param workbook
     * @param sheet
     */
    private static void createSheetAndWriteData(XSSFWorkbook workbook, ExcelSheetProperty sheet) {

        //重要参数
        List<Map<String, String>> sheetData = sheet.getSheetData();
        String[] titles = sheet.getSheetTitles();
        List<String> colums = sheet.getColums();
        String sheetName = sheet.getSheetName();
        int sheetTitlesRowNum = sheet.getSheetTitlesRowNum() == null ? 0 : sheet.getSheetTitlesRowNum();
        int sheetDataStartRowNum = sheet.getSheetDataStartRowNum() == null ? (sheetTitlesRowNum + 1) : sheet.getSheetDataStartRowNum();


        //创建 sheet 带有名称
        if (Objects.isNull(sheetName)) sheetName = "Sheet";
        String sheet_title = sheetName;
        XSSFSheet sheet_1 = BiExcelUtils2007.createSheet(workbook, sheet_title);
        XSSFSheet sheet_cur = sheet_1;

        //在sheet指定行创建表头
        BiExcelUtils2007.setColumTitle(sheet_1, titles, sheetTitlesRowNum);
        //填数据
        if (CollectionUtils.isNotEmpty(sheetData)) {
            int sheet_num = 0;
            int cellNumInRow = titles.length;
            int curRowNum = sheetDataStartRowNum;

            for (Map<String, String> a : sheetData) {
                if (curRowNum == MAX_ROW_NUMBER_2007) {
                    sheet_num++;
                    sheet_title = sheetName + sheet_num;
                    sheet_cur = BiExcelUtils2007.createSheet(workbook, sheet_title);
                    //在sheet指定行创建表头
                    BiExcelUtils2007.setColumTitle(sheet_cur, titles, sheetTitlesRowNum);
                    curRowNum = 1;
                }
                XSSFRow curRow = BiExcelUtils2007.createRow(sheet_cur, curRowNum, cellNumInRow);
                int columsLength = colums.size();
                for (int i = 0; i < columsLength; i++) {
                    XSSFCell cell = curRow.getCell(i);
                    if (Objects.isNull(cell)) cell = curRow.createCell(i);
                    String colum = colums.get(i);
                    colum = StringUtils.isBlank(colum) ? "" : colum.trim();
                    cell.setCellValue(BiExcelUtils2007.setCellValue(a.get(colum)));
                }
                curRowNum++;
            }
        }

    }


    public static String buildExcelDefault(String baseUploadPath, ExcelProperty excel) throws IOException {
        //基础参数
        String excelName = excel.getExcelName();
        if (StringUtils.isBlank(excelName)) throw new RuntimeException("excelName 配置不能为空");

        //创建Excel
        XSSFWorkbook workbook = BiExcelUtils2007.createXSSFWorkbook();

        //创建sheet 并为该sheet写入数据
        List<ExcelSheetProperty> sheets = excel.getSheets();
        if (!CollectionUtils.isEmpty(sheets)) {
            for (ExcelSheetProperty sheet : sheets) {
                createSheetAndWriteData(workbook, sheet);
            }
        }
        //将文件保存在临时目录
        String fileName = baseUploadPath + File.separator + excelName + ".xlsx";
        BiExcelUtils2007.writeFileInTempDir(workbook, fileName);
        return fileName;
    }


}
