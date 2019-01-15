package com.cxria.gaiamount.commons.cloud.payment.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.cxria.gaiamount.commons.cloud.payment.IPay;

public class AlipayApi implements IPay {
    //支付结果通知地址
    private String notifyUrl;
    //支付成功回调地址
    private String returnUrl;
    //商户订单号
    private String outTradeNo;
    //金额总额
    private String totalAmount;
    //订单过期时间
    private String timeout = "10m";
    //标题
    private String subject;
    //内容
    private String body;

    private AlipayClient alipayClient;
    //是否是移动端支付
    private boolean mobile;

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

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public AlipayClient getAlipayClient() {
        return alipayClient;
    }

    public void setAlipayClient(AlipayClient alipayClient) {
        this.alipayClient = alipayClient;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    @Override
    public String createOrder() {
        AlipayTradePagePayModel alipayTradePagePayModel = new AlipayTradePagePayModel();
        alipayTradePagePayModel.setOutTradeNo(outTradeNo);
        alipayTradePagePayModel.setTotalAmount(totalAmount);
        alipayTradePagePayModel.setSubject(subject);
        alipayTradePagePayModel.setBody(body);
        alipayTradePagePayModel.setTimeoutExpress(timeout);
        if (mobile) {
            alipayTradePagePayModel.setProductCode("QUICK_WAP_WAY");
        } else {
            alipayTradePagePayModel.setProductCode("FAST_INSTANT_TRADE_PAY");
        }
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        alipayTradePagePayRequest.setNotifyUrl(notifyUrl);
        alipayTradePagePayRequest.setReturnUrl(returnUrl);
        alipayTradePagePayRequest.setBizModel(alipayTradePagePayModel);
        AlipayTradePagePayResponse alipayTradePagePayResponse;
        try {
            alipayTradePagePayResponse = alipayClient.pageExecute(alipayTradePagePayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
        return alipayTradePagePayResponse.getBody();
    }

    public static String convertAmount(int amount) {
        String amountStr = amount / 100 + ".";
        int z = amount % 100;
        if (z < 10) {
            amountStr += "0" + z;
        } else {
            amountStr += z;
        }
        return amountStr;
    }
}
