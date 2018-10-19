package com.cxria.gaiamount.commons.cloud.payment.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.cxria.gaiamount.commons.cloud.payment.IPay;

public class AlipayApi implements IPay {

    private String notifyUrl;

    private String returnUrl;

    private String outTradeNo;

    private String totalAmount;

    private String body;

    private AlipayClient alipayClient;

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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
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

    @Override
    public String createOrder() {
        AlipayTradePagePayModel alipayTradePagePayModel = new AlipayTradePagePayModel();
        alipayTradePagePayModel.setOutTradeNo(outTradeNo);
        alipayTradePagePayModel.setTotalAmount(totalAmount);
        alipayTradePagePayModel.setSubject("Gaiamount Goods");
        alipayTradePagePayModel.setBody(body);
        alipayTradePagePayModel.setTimeoutExpress("10m");
        alipayTradePagePayModel.setProductCode("FAST_INSTANT_TRADE_PAY");
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
