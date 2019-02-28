package com.xzbd.mail.service.impl;

import com.sun.tools.javac.util.Assert;
import com.xzbd.common.config.XwMailConfig;
import com.xzbd.common.domain.ScheduleJob;
import com.xzbd.common.quartz.utils.QuartzManager;
import com.xzbd.common.utils.ScheduleJobUtils;
import com.xzbd.common.utils.ShiroUtils;
import com.xzbd.mail.exceptions.EmailException;
import com.xzbd.mail.dao.*;
import com.xzbd.mail.domain.*;
import com.xzbd.mail.service.*;
import com.xzbd.mail.utils.BiEmailUtils;
import com.xzbd.mail.utils.SenderProperties;
import com.xzbd.mail.utils.mailContentUtil.AbstractEmailContentItem;
import com.xzbd.mail.utils.mailContentUtil.BiEmailContentTypeEnum;
import com.xzbd.mail.utils.mailContentUtil.ExcelEmailContentItem;
import com.xzbd.mail.utils.mailContentUtil.TextEmailContentItem;
import com.xzbd.mail.utils.excelUtils.ExcelProperty;
import com.xzbd.mail.utils.excelUtils.ExcelSheetProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class BiEmailJobConfigServiceImpl implements BiEmailJobConfigService {
    @Autowired
    private BiEmailJobConfigDao biEmailJobConfigDao;
    @Autowired
    private BiEmailJobRunDiaryDao biEmailJobRunDiaryDao;
    @Autowired
    private BiEmailSenderConfigDao biEmailSenderConfigDao;
    @Autowired
    private BiEmailContentConfigDao biEmailContentConfigDao;
    @Autowired
    private BiEmailTextContentConfigDao biEmailTextContentConfigDao;
    @Autowired
    private BiEmailExcelConfigDao biEmailExcelConfigDao;
    @Autowired
    private BiEmailSheetConfigDao biEmailSheetConfigDao;
    @Autowired
    private BiEmailExcelSheetConfigDao biEmailExcelSheetConfigDao;
    @Autowired
    private BiEmailExcelCommonDao biEmailExcelCommonDao;
    @Autowired
    private BiEmailReceiverGroupConfigService biEmailReceiverGroupConfigService;
    @Autowired
    private XwMailConfig xwMailConfig;
    @Autowired
    private QuartzManager quartzManager;

    @Override
    public BiEmailJobConfigDO get(Long id) {
        return biEmailJobConfigDao.get(id);
    }

    @Override
    public List<BiEmailJobConfigDO> list(Map<String, Object> map) {
        return biEmailJobConfigDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return biEmailJobConfigDao.count(map);
    }

    @Override
    public int save(BiEmailJobConfigDO biEmailJobConfig) {
        int res = 0;
        Long userId = ShiroUtils.getUserId();
        Date time = new Date();
        biEmailJobConfig.setCreateMan(userId);
        biEmailJobConfig.setCreateTime(time);
        biEmailJobConfig.setLastOptionMan(userId);
        biEmailJobConfig.setLastOptionTime(time);

        biEmailJobConfig.setDel(0);
        biEmailJobConfig.setJobGroup("email");
        biEmailJobConfig.setJobBeanClass("com.xzbd.common.task.EmailJob");
        // record.setMethodName("");
        biEmailJobConfig.setJobStatus(0);

        //保存job
        res = biEmailJobConfigDao.save(biEmailJobConfig);
        if (res < 1)
            return res;

        //保存任务内容
        updateJobContent(biEmailJobConfig, userId, time);

        return res;
    }

    private int updateJobContent(BiEmailJobConfigDO biEmailJobConfig, Long userId, Date time) {
        Long jobId = biEmailJobConfig.getId();
        int remove = biEmailContentConfigDao.removeByJobId(jobId);
        List<Long> textIds = biEmailJobConfig.getTextEmailContentIds();
        List<Long> excelIds = biEmailJobConfig.getExcelEmailContentIds();

        List<BiEmailContentConfigDO> contents = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(textIds))
            textIds.stream().forEach(item -> {
                BiEmailContentConfigDO o = new BiEmailContentConfigDO();
                o.setContentId(item);
                o.setEmailJobId(jobId);
                o.setType("1");
                o.setCreateMan(userId);
                o.setCreateTime(time);
                o.setDel(0);
                contents.add(o);
            });

        if (CollectionUtils.isNotEmpty(excelIds))
            excelIds.stream().forEach(item -> {
                BiEmailContentConfigDO o = new BiEmailContentConfigDO();
                o.setContentId(item);
                o.setEmailJobId(jobId);
                o.setType("4");
                o.setCreateMan(userId);
                o.setCreateTime(time);
                o.setDel(0);
                contents.add(o);
            });

        if (CollectionUtils.isNotEmpty(contents))
            return biEmailContentConfigDao.batchInsert(contents);
        else
            return 0;
    }

    @Override
    public int update(BiEmailJobConfigDO biEmailJobConfig) {
        Long userId = ShiroUtils.getUserId();
        Date time = new Date();
        biEmailJobConfig.setLastOptionMan(userId);
        biEmailJobConfig.setLastOptionTime(time);

        updateJobContent(biEmailJobConfig, userId, time);

        return biEmailJobConfigDao.update(biEmailJobConfig);
    }

    @Override
    public int remove(Long id) {
        return biEmailJobConfigDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return biEmailJobConfigDao.batchRemove(ids);
    }

    @Override
    public void initSchedule() throws Exception {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("del", 0);
        List<BiEmailJobConfigDO> list = list(queryMap);
        if (CollectionUtils.isEmpty(list))
            return;
        for (BiEmailJobConfigDO job : list) {
            if (1 == job.getJobStatus()) {
                ScheduleJob o = ScheduleJobUtils.entityToData(job);
                quartzManager.addJob(o);
            }
        }
    }

    @Override
    public void sendEmail(Long jobId, String date) throws Exception {

        if (Objects.isNull(jobId))
            throw new EmailException("任务不存在：jobId is null");
        BiEmailJobConfigDO biEmailJobConfigDO = get(jobId);
        if (Objects.isNull(biEmailJobConfigDO))
            throw new EmailException("任务不存在");
        // 记录日志
        BiEmailJobRunDiaryDO biEmailJobRunDiaryDO = sendMailLog(biEmailJobConfigDO);
        //发送
        if (Objects.isNull(biEmailJobRunDiaryDO))
            throw new EmailException("邮件任务日志记录失败");
        boolean successed = true;
        String msg = "";
        try {
            sendEmailAction(biEmailJobConfigDO, date);
        } catch (Exception e) {
            successed = false;
            //取异常中的200字记录在来
            msg = e.getMessage().substring(0, Math.min(e.getMessage().length(), 200));
            throw e;
        } finally {
            //更新日志
            updateMailLog(biEmailJobRunDiaryDO, successed, msg);
        }

    }


    private void updateMailLog(BiEmailJobRunDiaryDO biEmailJobRunDiaryDO, boolean succecced, String msg) throws Exception {
        biEmailJobRunDiaryDO.setEndTime(new Date());
        if (succecced) {
            biEmailJobRunDiaryDO.setRunState(BiEmailJobRunDiaryRunStateEnum.SUCCESS.getValue());
        } else {
            biEmailJobRunDiaryDO.setRunState(BiEmailJobRunDiaryRunStateEnum.FAILED.getValue());
            biEmailJobRunDiaryDO.setFailedType(1);
            biEmailJobRunDiaryDO.setFailedMsg(msg);
            biEmailJobRunDiaryDO.setRetried(0);
        }
        int update = biEmailJobRunDiaryDao.update(biEmailJobRunDiaryDO);
        if (update < 1)
            throw new EmailException("邮件发送日志更新失败！");

    }

    private void sendEmailAction(BiEmailJobConfigDO biEmailJobConfigDO, String date) throws Exception {
        if (Objects.isNull(biEmailJobConfigDO))
            throw new EmailException("任务不存在");
        if (StringUtils.isBlank(date))
            throw new EmailException("日期未确定");
        Long senderId = biEmailJobConfigDO.getEmailSenderId();
        if (null == senderId)
            throw new EmailException("该任务没有配置发件人邮箱");
        BiEmailSenderConfigDO biEmailSenderConfigDO = biEmailSenderConfigDao.get(senderId);
        if (Objects.isNull(biEmailSenderConfigDO))
            throw new EmailException("发件人配置不存在");
        String senderAddr = biEmailSenderConfigDO.getEmailAddr();
        String senderPwd = biEmailSenderConfigDO.getEmailPwd();
        String senderName = biEmailSenderConfigDO.getEmailName();
        String maintainerAddrs = biEmailSenderConfigDO.getEmailMaintainerAddrs();
        if (StringUtils.isBlank(senderAddr) || StringUtils.isBlank(senderPwd))
            throw new EmailException("发件人地址或密码不存在");
        String subject = biEmailJobConfigDO.getSubject();
        if (StringUtils.isBlank(subject))
            throw new EmailException("邮件主题未配置");
        String to = biEmailJobConfigDO.getSendTo();
        if (StringUtils.isBlank(to))
            throw new EmailException("收件人未配置");
        String cc = biEmailJobConfigDO.getCc();
        String bcc = biEmailJobConfigDO.getBcc();
        List<String> toList = null;
        List<String> ccList = null;
        List<String> bccList = null;
        if (StringUtils.isNotBlank(to)) {
            List<BiEmailReceiverGroupConfigDO> toListDo = biEmailReceiverGroupConfigService.listFindInIds(to);
            toList = doToList(toListDo);
        }
        if (StringUtils.isNotBlank(cc)) {
            List<BiEmailReceiverGroupConfigDO> ccListDo = biEmailReceiverGroupConfigService.listFindInIds(cc);
            ccList = doToList(ccListDo);
        }
        if (StringUtils.isNotBlank(bcc)) {
            List<BiEmailReceiverGroupConfigDO> bccListDo = biEmailReceiverGroupConfigService.listFindInIds(bcc);
            bccList = doToList(bccListDo);
        }
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("del", 0);
        queryMap.put("emailJobId", biEmailJobConfigDO.getId());
        List<BiEmailContentConfigDO> contents = biEmailContentConfigDao.list(queryMap);
        if (CollectionUtils.isEmpty(contents))
            throw new EmailException("邮件内容未配置");
        List<AbstractEmailContentItem> emailContents = buildMailContentList(contents, date);
        if (emailContents.size() < 1)
            throw new EmailException("邮件内容未设置");

        SenderProperties sender = new SenderProperties(senderAddr, senderPwd, senderName);
        sender.setMailSmtpHost(biEmailSenderConfigDO.getMailSmtpHost());
        sender.setMailTransportProtocol(biEmailSenderConfigDO.getMailTransportProtocol());
        sender.setMailSmtpTimeout(biEmailSenderConfigDO.getMailSmtpTimeout());
        sender.setMailSmtpConnectionTimeout(biEmailSenderConfigDO.getMailSmtpConnectionTimeout());

        //发送邮件
        BiEmailUtils.sendMail(sender, toList, ccList, bccList,
                maintainerAddrs, subject, emailContents,
                xwMailConfig.getUploadPath());
    }

    private List<String> doToList(List<BiEmailReceiverGroupConfigDO> toListDo) {
        if (CollectionUtils.isEmpty(toListDo))
            return null;
        Set<String> set = new HashSet<>();
        toListDo.stream()
                .forEach(item -> {
                    String elements = item.getGroupElement();
                    if (StringUtils.isNotBlank(elements)) {
                        String[] eArr = elements.split(",");
                        for (String addr : eArr) {
                            if (StringUtils.isNotBlank(addr))
                                set.add(addr);
                        }
                    }
                });
        return new ArrayList<>(set);
    }

    private List<AbstractEmailContentItem> buildMailContentList(List<BiEmailContentConfigDO> contents, String date) throws Exception {
        if (CollectionUtils.isEmpty(contents))
            throw new EmailException("邮件内容未配置");
        List<AbstractEmailContentItem> list = new ArrayList<>();
        for (BiEmailContentConfigDO item : contents) {
            String type = item.getType();
            Long itemContentId = item.getContentId();
            if (null == itemContentId)
                throw new EmailException("邮件内容id不存在：邮件任务id is " + item.getEmailJobId());
            if (StringUtils.isBlank(type))
                throw new EmailException("邮件内容类型不确定");
            AbstractEmailContentItem contentItem = null;

            if (BiEmailContentTypeEnum.TEXT_CONTENT.getValue().equals(type)) {
                contentItem = new TextEmailContentItem();
                BiEmailTextContentConfigDO text = biEmailTextContentConfigDao.get(itemContentId);
                if (Objects.isNull(text))
                    throw new EmailException("文本配置内容不存在：文本id is " + itemContentId);
                ((TextEmailContentItem) contentItem).setContent(text.getContent());
                contentItem.setContentType(BiEmailContentTypeEnum.TEXT_CONTENT);

            } else if (BiEmailContentTypeEnum.EXCEL_CONTEXT.getValue().equals(type)) {
                contentItem = new ExcelEmailContentItem();
                ExcelProperty excelProperty = new ExcelProperty();
                List<ExcelSheetProperty> sheetProperties = new ArrayList<>();

                BiEmailExcelConfigDO excelConfig = biEmailExcelConfigDao.get(itemContentId);
                if (Objects.isNull(excelConfig))
                    throw new EmailException("Excel配置内容不存在 ： excel id is " + itemContentId);
                excelProperty.setExcelName(excelConfig.getName());
                Map<String, Object> sheetListQuery = new HashMap<>();
                sheetListQuery.put("excelId", excelConfig.getId());
                List<BiEmailExcelSheetConfigDO> sheetConfigs = biEmailExcelSheetConfigDao.list(sheetListQuery);
                if (CollectionUtils.isEmpty(sheetConfigs))
                    throw new EmailException("Sheet 未配置");
                for (BiEmailExcelSheetConfigDO sheetConfig : sheetConfigs) {
                    Long sheetId = sheetConfig.getSheetId();
                    if (null == sheetId)
                        throw new EmailException("sheet配置项不存在：data error");
                    BiEmailSheetConfigDO sheet = biEmailSheetConfigDao.get(sheetId);
                    if (Objects.isNull(sheet))
                        throw new EmailException("sheet配置项不存在：data error");
                    String sheetName = sheet.getSheetName();
                    String titles = sheet.getTitles();
                    String coloums = sheet.getColoums();
                    String querySQL = sheet.getQSql();

                    if (StringUtils.isBlank(sheetName))
                        throw new EmailException("sheet 名称为空");
                    if (StringUtils.isBlank(titles))
                        throw new EmailException("sheet 没有配置 title");
                    if (StringUtils.isBlank(coloums))
                        throw new EmailException("sheet 没有配置 coloums");
                    if (StringUtils.isBlank(querySQL))
                        throw new EmailException("sheet 没有配置 sql");

                    String[] sheetTitles = titles.split(",");
                    String[] sheetColums = coloums.split(",");
                    if (sheetTitles.length != sheetColums.length)
                        throw new EmailException("列名 与 数据库 字段名个数不相等 " + sheetName);

                    ExcelSheetProperty sheetProperty = new ExcelSheetProperty();
                    sheetProperty.setSheetName(sheetName);
                    sheetProperty.setSheetTitles(sheetTitles);
                    sheetProperty.setColums(Arrays.asList(sheetColums));

                    querySQL = querySQL.replace("$date$", "'" + date + "'");
                    querySQL = querySQL.replace("$DATE$", "'" + date + "'");
                    List<Map<String, String>> dataMap = biEmailExcelCommonDao.execDefinedSql(querySQL);
                    sheetProperty.setSheetData(dataMap);
                    sheetProperty.setSheetDataStartRowNum(1);
                    sheetProperties.add(sheetProperty);
                }

                excelProperty.setSheets(sheetProperties);
                ((ExcelEmailContentItem) contentItem).setExcel(excelProperty);
                contentItem.setContentType(BiEmailContentTypeEnum.EXCEL_CONTEXT);

            }


            if (Objects.nonNull(contentItem))
                list.add(contentItem);
        }

        return list;
    }

    private BiEmailJobRunDiaryDO sendMailLog(BiEmailJobConfigDO biEmailJobConfigDO) {
        BiEmailJobRunDiaryDO biEmailJobRunDiaryDO = new BiEmailJobRunDiaryDO();
        biEmailJobRunDiaryDO.setJobName(biEmailJobConfigDO.getJobName());
        biEmailJobRunDiaryDO.setJobId(biEmailJobConfigDO.getId());
        biEmailJobRunDiaryDO.setStartTime(new Date());
        biEmailJobRunDiaryDO.setRunState(BiEmailJobRunDiaryRunStateEnum.RUNNING.getValue());
        biEmailJobRunDiaryDO.setOriginTo(biEmailJobConfigDO.getSendTo());
        biEmailJobRunDiaryDO.setOriginCc(biEmailJobConfigDO.getCc());
        biEmailJobRunDiaryDO.setOriginBc(biEmailJobConfigDO.getBcc());
        int in = biEmailJobRunDiaryDao.save(biEmailJobRunDiaryDO);
        if (in > 0)
            return biEmailJobRunDiaryDO;
        else
            return null;

    }

}
