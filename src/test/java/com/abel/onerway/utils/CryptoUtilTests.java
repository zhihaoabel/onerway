package com.abel.onerway.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@SpringBootTest
public class CryptoUtilTests {

    @Test
    void testHash() {
        String message = "Hello, world!";
        String encryptedMessage = CryptoUtil.hash(message, null);
        System.out.println(encryptedMessage);
    }

    @Test
    void testVerifyIntegrity() {
        String message = "Hello, world!";
        boolean res = CryptoUtil.verifyIntegrity(null, message, "9F8CAA51A7E3C8E5CA89EF16C0CC48B01C73527E5F9B54335A0BA17D9E4747BF");
        assert res;
    }

    @Test
    void testEncryptWithRSA() {
        String message = "Hello, world!";
        String key = "59c5b49a58c74340b28ecc68004e815a";
        String encryptedMessage = CryptoUtil.encryptWithRSA(key, message);
        System.out.println(encryptedMessage);
    }

    /**
     * 查询支付方式
     */
    @Test
    void consultPaymentMethods() {
        TreeMap map = new TreeMap<>();
        map.put("merchantNo", "800209");
        map.put("merchantTxnId", "1640229747000");
        map.put("merchantTxnTime", "2021-12-22 15:30:30");
        map.put("merchantTxnTimeZone", "+08:00");
        map.put("productType", "CARD");
        map.put("subProductType", "DIRECT");
        map.put("txnType", "SALE");
        map.put("orderAmount", "20");
        map.put("orderCurrency", "USD");
        map.put("txnOrderMsg", "{\"returnUrl\":\"https://www.ronhan.com/\",\"products\":\"[{\\\"price\\\":\\\"110.00\\\",\\\"num\\\":\\\"1\\\",\\\"name\\\":\\\"iphone11\\\",\\\"currency\\\":\\\"USD\\\"}]\",\"transactionIp\":\"127.0.0.1\",\"appId\":1458672763818790912,\"javaEnabled\":false,\"colorDepth\":\"24\",\"screenHeight\":\"1080\",\"screenWidth\":\"1920\",\"timeZoneOffset\":\"-480\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\",\"userAgent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36\",\"contentLength\":\"340\",\"language\":\"zh-CN\"}");
        map.put("cardInfo", "{\"cardNumber\":\"4000027891380961\",\"cvv\":\"789\",\"month\":\"12\",\"year\":\"2022\",\"holderName\":\"test sandbox\"}");
        map.put("shippingInformation", "{\"firstName\":\"ShippingFirstName\",\"lastName\":\"ShippingLastName\",\"phone\":\"188888888888\",\"email\":\"shipping@test.com\",\"postalCode\":\"888888\",\"address\":\"ShippingAddress\",\"country\":\"CN\",\"province\":\"SH\",\"city\":\"SH\",\"street\":\"lujiazui\",\"number\":\"1\",\"identityNumber\":\"110000\"}");

        map.put("billingInformation", "{\"firstName\":\"billingFirstName\",\"lastName\":\"billingLastName\",\"phone\":\"18600000000\",\"email\":\"billing@test.com\",\"postalCode\":\"430000\",\"address\":\"BillingAddress\",\"country\":\"CN\",\"province\":\"HK\",\"city\":\"HK\",\"street\":\"jianshazui\",\"number\":\"2\",\"identityNumber\":\"220000\"}");
//        map.put("sign", "CAD44FD54F94FBA53ED64A0E6C1D0009EC0C3EA898CBC812787493522CE85F96");
        String hash = CryptoUtil.hash(StringUtil.concatValue(map), "59c5b49a58c74340b28ecc68004e815a");
        System.out.println("hash = " + hash);
    }

    @Test
    void doTransaction() {

    }
}
