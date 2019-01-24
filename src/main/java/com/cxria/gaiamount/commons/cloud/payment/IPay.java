package com.cxria.gaiamount.commons.cloud.payment;

public interface IPay {
    String createOrder(String outTradeNo, int amount, String body, boolean isMobile);
}
