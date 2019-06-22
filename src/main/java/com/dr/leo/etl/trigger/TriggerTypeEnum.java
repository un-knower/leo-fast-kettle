package com.dr.leo.etl.trigger;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 21:20
 * @since 1.0
 */
public enum TriggerTypeEnum {
    /**
     * 任务执行的类型
     */
    MANUAL("jobconf_trigger_type_manual"),
    CRON("jobconf_trigger_type_cron");

    private TriggerTypeEnum(String title) {
        this.title = title;
    }

    private String title;

    public String getTitle() {
        return title;
    }
}
