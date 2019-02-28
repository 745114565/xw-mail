package com.xzbd.mail.controller;

import com.xzbd.common.quartz.utils.QuartzManager;
import com.xzbd.common.utils.*;
import com.xzbd.mail.domain.*;
import com.xzbd.mail.service.*;
import com.xzbd.mail.utils.mailContentUtil.BiEmailContentTypeEnum;
import com.xzbd.system.domain.UserDO;
import com.xzbd.system.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 邮件作业任务配置
 *
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */

@Controller
@RequestMapping("/mail/biEmailJobConfig")
public class BiEmailJobConfigController {
    @Autowired
    private BiEmailJobConfigService biEmailJobConfigService;
    @Autowired
    private BiEmailSenderConfigService biEmailSenderConfigService;
    @Autowired
    private QuartzManager quartzManager;
    @Autowired
    private BiEmailTextContentConfigService biEmailTextContentConfigService;
    @Autowired
    private BiEmailExcelConfigService biEmailExcelConfigService;
    @Autowired
    private BiEmailContentConfigService biEmailContentConfigService;
    @Autowired
    private BiEmailReceiverGroupConfigService biEmailReceiverGroupConfigService;
    @Autowired
    private UserService userService;

    @GetMapping()
    @RequiresPermissions("mail:biEmailJobConfig:biEmailJobConfig")
    String BiEmailJobConfig() {
        return "mail/biEmailJobConfig/biEmailJobConfig";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("mail:biEmailJobConfig:biEmailJobConfig")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        query.put("del",0);
        List<BiEmailJobConfigDO> biEmailJobConfigList = biEmailJobConfigService.list(query);
        if (CollectionUtils.isNotEmpty(biEmailJobConfigList))
            for (BiEmailJobConfigDO obj : biEmailJobConfigList) {
                Long createManID = obj.getCreateMan();
                Long updateManID = obj.getLastOptionMan();
                UserDO creatMan = userService.get(createManID);
                UserDO updateMan = userService.get(updateManID);
                if(Objects.nonNull(creatMan))
                    obj.setCreateManStr(creatMan.getName());
                if(Objects.nonNull(updateMan))
                    obj.setLastOptionManStr(updateMan.getName());
            }
        int total = biEmailJobConfigService.count(query);
        PageUtils pageUtils = new PageUtils(biEmailJobConfigList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("mail:biEmailJobConfig:add")
    String add(Model model) {
        List<BiEmailSenderConfigDO> seders = biEmailSenderConfigService.getActiveSenderAddrs();
        model.addAttribute("seders", seders);
        return "mail/biEmailJobConfig/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("mail:biEmailJobConfig:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        BiEmailJobConfigDO biEmailJobConfig = biEmailJobConfigService.get(id);
        model.addAttribute("biEmailJobConfig", biEmailJobConfig);
        List<BiEmailSenderConfigDO> seders = biEmailSenderConfigService.getActiveSenderAddrs();
        model.addAttribute("seders", seders);
        return "mail/biEmailJobConfig/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("mail:biEmailJobConfig:add")
    public R save(BiEmailJobConfigDO biEmailJobConfig) {
        //校验是否重名
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("jobName", biEmailJobConfig.getJobName());
        queryMap.put("del", 0);
        List<BiEmailJobConfigDO> jobs = biEmailJobConfigService.list(queryMap);
        if (CollectionUtils.isNotEmpty(jobs))
            return R.error("该名称已经存在，请重新命名");
        int save = biEmailJobConfigService.save(biEmailJobConfig);
        if (save < 1) return R.error("新增失败");
        return R.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("mail:biEmailJobConfig:edit")
    public R update(BiEmailJobConfigDO biEmailJobConfig) {

        Long jobId = biEmailJobConfig.getId();
        if (jobId == null)
            return R.ok("该记录不存在");
        BiEmailJobConfigDO old = biEmailJobConfigService.get(jobId);
        if (old == null)
            return R.ok("该记录不存在");

        //校验是否重名
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("id", biEmailJobConfig.getId());
        queryMap.put("jobName", biEmailJobConfig.getJobName());
        queryMap.put("del", 0);
        List<BiEmailJobConfigDO> jobs = biEmailJobConfigService.list(queryMap);
        if (CollectionUtils.isNotEmpty(jobs))
            jobs = jobs.stream().filter(item -> !item.getId().equals(biEmailJobConfig.getId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(jobs))
            return R.error("该名称已经存在，请重新命名");

        old.setSendTo(biEmailJobConfig.getSendTo());
        old.setCc(biEmailJobConfig.getCc());
        old.setBcc(biEmailJobConfig.getBcc());
        old.setSubject(biEmailJobConfig.getSubject());
        old.setJobDesc(biEmailJobConfig.getJobDesc());
        old.setEmailContentId(biEmailJobConfig.getEmailContentId());
        old.setCronExpression(biEmailJobConfig.getCronExpression());
        old.setEmailSenderId(biEmailJobConfig.getEmailSenderId());
        old.setJobName(biEmailJobConfig.getJobName());
        old.setMemo1(biEmailJobConfig.getMemo1());

        old.setTextEmailContentIds(biEmailJobConfig.getTextEmailContentIds());
        old.setExcelEmailContentIds(biEmailJobConfig.getExcelEmailContentIds());
        int res = biEmailJobConfigService.update(old);
        if (res > 0)
            return R.ok();
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("mail:biEmailJobConfig:remove")
    public R remove(Long id) {
        if (null == id)
            return R.error("该记录不存在");
        BiEmailJobConfigDO biEmailJobConfig = biEmailJobConfigService.get(id);
        if (Objects.isNull(biEmailJobConfig))
            return R.error("该记录不存在");
        biEmailJobConfig.setDel(1);
        int update = biEmailJobConfigService.update(biEmailJobConfig);
        if (update > 0)
            return R.ok();
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("mail:biEmailJobConfig:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        biEmailJobConfigService.batchRemove(ids);
        return R.ok();
    }


    @PostMapping("/jobStatus")
    @ResponseBody
    public R jobStatus(Long id, int jobStatus) {
        try {
            if (null == id)
                return R.error("该记录不存在");
            BiEmailJobConfigDO old = biEmailJobConfigService.get(id);
            if (old == null)
                return R.error("该记录不存在");

            String label = "";
            if (jobStatus == 0) {
                old.setJobStatus(1);
                label = "启动";
                quartzManager.addJob(ScheduleJobUtils.entityToData(old));
            } else if (jobStatus == 1) {
                old.setJobStatus(0);
                label = "停止";
                quartzManager.deleteJob(ScheduleJobUtils.entityToData(old));
            }


            Date curD = new Date();
            Long userId = ShiroUtils.getUserId();
            old.setLastOptionMan(userId);
            old.setLastOptionTime(curD);

            int res = biEmailJobConfigService.update(old);
            if (res > 0)
                return R.ok("任务" + label + "成功");
            return R.error("任务" + label + "失败");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return R.error("操作失败！");
        }
    }

    /**
     * 跳转到手动发送邮件页面
     *
     * @return
     */
    @GetMapping("/toSendMail/{id}")
    public String toSendMail(@PathVariable Long id, Model model) {
        if (id == null)
            throw new RuntimeException("id不能为空");
        BiEmailJobConfigDO old = biEmailJobConfigService.get(id);
        if (Objects.nonNull(old)) {
            String to = old.getSendTo();
            String cc = old.getCc();
            String bcc = old.getBcc();
            if (StringUtils.isNotBlank(to)) {
                List<BiEmailReceiverGroupConfigDO> receiverGroups = biEmailReceiverGroupConfigService.listFindInIds(to);
                if (CollectionUtils.isNotEmpty(receiverGroups)) {
                    List<String> list = receiverGroups.stream()
                            .map(item -> item.getName())
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(list)) {
                        String str = StringUtils.join(list, ",");
                        old.setSendTo(str);
                    }
                }
            }
            if (StringUtils.isNotBlank(cc)) {
                List<BiEmailReceiverGroupConfigDO> receiverGroups = biEmailReceiverGroupConfigService.listFindInIds(cc);
                if (CollectionUtils.isNotEmpty(receiverGroups)) {
                    List<String> list = receiverGroups.stream()
                            .map(item -> item.getName())
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(list)) {
                        String str = StringUtils.join(list, ",");
                        old.setCc(str);
                    }
                }

            }
            if (StringUtils.isNotBlank(bcc)) {
                List<BiEmailReceiverGroupConfigDO> receiverGroups = biEmailReceiverGroupConfigService.listFindInIds(bcc);
                if (CollectionUtils.isNotEmpty(receiverGroups)) {
                    List<String> list = receiverGroups.stream()
                            .map(item -> item.getName())
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(list)) {
                        String str = StringUtils.join(list, ",");
                        old.setBcc(str);
                    }
                }

            }
        }
        model.addAttribute("old", old);
        List<BiEmailSenderConfigDO> seders = biEmailSenderConfigService.getActiveSenderAddrs();
        model.addAttribute("seders", seders);
        return "mail/biEmailJobConfig/sendMail";
    }

    /**
     * 手动发送邮件
     *
     * @return
     */
    @GetMapping("/sendByManual")
    @ResponseBody
    public R sendByManual(Long id, String date) {

        try {
            if (id == null)
                throw new RuntimeException("id不能为空");
            if (StringUtils.isBlank(date))
                return R.error("时间不能为空！");
            String pattern = "\\d{8}";
            if (!Pattern.matches(pattern, date))
                return R.error("时间 格式不匹配：时间格式为：20180101，您输入的为：" + date);
            date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
            biEmailJobConfigService.sendEmail(id, date);
            return R.ok("邮件发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("邮件发送失败:  " + e.getMessage());
        }

    }

    /**
     * @return
     */
    @GetMapping("/content")
    @ResponseBody
    public R content(Long jobId) {

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> textQuery = new HashMap<>();
        textQuery.put("del", 0);
        Map<String, Object> excelQuery = new HashMap<>();
        excelQuery.put("del", 0);
        List<BiEmailTextContentConfigDO> textList = biEmailTextContentConfigService.list(textQuery);
        List<BiEmailExcelConfigDO> exceLList = biEmailExcelConfigService.list(excelQuery);
        map.put("textList", textList);
        map.put("exceLList", exceLList);

        Map<String, Object> query = new HashMap<>();
        query.put("del", 0);
        query.put("emailJobId", jobId);
        List<BiEmailContentConfigDO> contents = biEmailContentConfigService.list(query);
        map.put("contents", contents);
        return R.ok(map);
    }
}
