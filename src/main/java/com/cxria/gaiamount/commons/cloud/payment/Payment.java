package com.cxria.gaiamount.commons.cloud.payment;

public abstract class Payment implements IPay {
    //支付结果通知地址
    private String notifyUrl;
    //支付成功回调地址
    private String returnUrl;
    //订单过期时间
    private String timeout;
    //商品标题
    private String subject;

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Payment(String notifyUrl, String returnUrl, String timeout, String subject) {
        this.notifyUrl = notifyUrl;
        this.returnUrl = returnUrl;
        this.timeout = timeout;
        this.subject = subject;
    }
}
