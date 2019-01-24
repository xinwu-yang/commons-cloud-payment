package com.cxria.gaiamount.commons.cloud.payment.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.cxria.gaiamount.commons.cloud.payment.Payment;

public class AlipayApi extends Payment {
    private AlipayClient alipayClient;

    public AlipayApi(String notifyUrl, String returnUrl, String timeout, String subject, AlipayClient alipayClient) {
        super(notifyUrl, returnUrl, timeout, subject);
        this.alipayClient = alipayClient;
    }

    @Override
    public String createOrder(String outTradeNo, int amount, String body, boolean isMobile) {
        String order = null;
        try {
            if (isMobile) {
                AlipayTradeWapPayModel wapPayModel = new AlipayTradeWapPayModel();
                wapPayModel.setOutTradeNo(outTradeNo);
                wapPayModel.setTotalAmount(convertAmount(amount));
                wapPayModel.setSubject(getSubject());
                wapPayModel.setBody(body);
                wapPayModel.setTimeoutExpress(getTimeout());
                wapPayModel.setProductCode("QUICK_WAP_WAY");
                AlipayTradeWapPayRequest wapPayRequest = new AlipayTradeWapPayRequest();
                wapPayRequest.setNotifyUrl(getNotifyUrl());
                wapPayRequest.setReturnUrl(getReturnUrl());
                wapPayRequest.setBizModel(wapPayModel);
                AlipayTradeWapPayResponse wapPayResponse = alipayClient.pageExecute(wapPayRequest);
                order = wapPayResponse.getBody();
            } else {
                AlipayTradePagePayModel pagePayModel = new AlipayTradePagePayModel();
                pagePayModel.setOutTradeNo(outTradeNo);
                pagePayModel.setTotalAmount(convertAmount(amount));
                pagePayModel.setSubject(getSubject());
                pagePayModel.setBody(body);
                pagePayModel.setTimeoutExpress(getTimeout());
                pagePayModel.setProductCode("FAST_INSTANT_TRADE_PAY");
                AlipayTradePagePayRequest pagePayRequest = new AlipayTradePagePayRequest();
                pagePayRequest.setNotifyUrl(getNotifyUrl());
                pagePayRequest.setReturnUrl(getReturnUrl());
                pagePayRequest.setBizModel(pagePayModel);
                AlipayTradePagePayResponse pagePayResponse = alipayClient.pageExecute(pagePayRequest);
                order = pagePayResponse.getBody();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return order;
    }

    private String convertAmount(int amount) {
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
