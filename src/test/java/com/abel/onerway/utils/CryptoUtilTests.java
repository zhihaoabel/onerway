package com.abel.onerway.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.TreeMap;

@SpringBootTest
public class CryptoUtilTests {


    // TODO: 请将privateKey替换为自己测试账号的密钥
    String privateKey = "59c5b49a58c74340b28ecc68004e815a";

    @Test
    void testHash() {
        String message = "Hello, world!";
        String encryptedMessage = CryptoUtil.hash(message, null);
        System.out.println(encryptedMessage);
    }

    @Test
    void testVerifyIntegrity() {
        String message = "Hello, world!";
        boolean res = CryptoUtil.verifyIntegrity(null, message, "56597b70ca38c7584cdba55e6fd48a0afb0157ee8a7ad1c20075b45655a02cfc");
        assert res;
    }

    @Test
    void testJson2Map() {
        String json = "{\n" +
                "  \"billingInformation\":\"{\\\"country\\\":\\\"US\\\",\\\"email\\\":\\\"test@qq.com\\\"}\",\n" +
                "  \"merchantNo\":\"800209\",\n" +
                "  \"merchantTxnId\":\"1654675447651\",\n" +
                "  \"merchantTxnTime\":\"2022-03-08 16:04:07\",\n" +
                "  \"merchantTxnTimeZone\":\"+08:00\",\n" +
                "  \"orderAmount\":\"35\",\n" +
                "  \"orderCurrency\":\"USD\",\n" +
                "  \"productType\":\"CARD\",\n" +
                "  \"cardInfo\":\"{\\\"holderName\\\":\\\"CL BRW2\\\"}\",\n" +
                "  \"shippingInformation\":\"{\\\"country\\\":\\\"US\\\",\\\"email\\\":\\\"test@qq.com\\\"}\",\n" +
                "  \"subProductType\":\"DIRECT\",\n" +
                "  \"txnOrderMsg\":\"{\\\"appId\\\":\\\"1700077023031386112\\\",\\\"returnUrl\\\":\\\"http://v1-demo.test.com/\\\",\\\"products\\\":\\\"[{\\\\\\\"price\\\\\\\":\\\\\\\"110.00\\\\\\\",\\\\\\\"num\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"name\\\\\\\":\\\\\\\"iphone11\\\\\\\",\\\\\\\"currency\\\\\\\":\\\\\\\"USD\\\\\\\"}]\\\"}\",\n" +
                "  \"txnType\":\"SALE\",\n" +
                "}";
        TreeMap<String, Object> map = (TreeMap<String, Object>) JsonUtil.json2Map(json);
        String hash = CryptoUtil.hash(StringUtil.concatValue(map), privateKey);
        System.out.println("hash = " + hash);
    }

    /**
     * 收银台支付
     * <p>
     * Note:
     * map的value中出现的\需要匹配，否则生成的hash值不对，会造成签名失败；也有可能会出现反序列化报错。
     * </p>
     */
    @Test
    void doPayment() {
        TreeMap<String, Object> data = new TreeMap<>();
        data.put("billingInformation", "{\"country\":\"US\",\"email\":\"test@qq.com\"}");
        data.put("merchantNo", "800209");
        data.put("merchantTxnId", "1654675447654");
        data.put("merchantTxnTime", "2022-03-08 16:04:07");
        data.put("merchantTxnTimeZone", "+08:00");
        data.put("orderAmount", "35");
        data.put("orderCurrency", "USD");
        data.put("productType", "CARD");
        data.put("cardInfo", "{\"holderName\":\"CL BRW2\"}");
        data.put("shippingInformation", "{\"country\":\"US\",\"email\":\"test@qq.com\"}");
        data.put("subProductType", "DIRECT");
        data.put("txnOrderMsg", "{\"appId\":\"1700077023031386112\",\"returnUrl\":\"http://v1-demo.test.com/\",\"products\":\"[{\\\"price\\\":\\\"110.00\\\",\\\"num\\\":\\\"1\\\",\\\"name\\\":\\\"iphone11\\\",\\\"currency\\\":\\\"USD\\\"}]\"}");
        data.put("txnType", "SALE");

        String hash = CryptoUtil.hash(StringUtil.concatValue(data), privateKey);
        System.out.println("hash = " + hash);
    }

    /**
     * 两方接口支付
     * <p>
     * Note:
     * map的value中出现的\需要匹配，否则生成的hash值不对，会造成签名失败；也有可能会出现反序列化报错。
     * </p>
     */
    @Test
    void doTransaction() {
        String json = "{\n" +
                "  \"merchantNo\": \"800209\",\n" +
                "  \"merchantTxnId\": \"1640229747908\",\n" +
                "  \"merchantTxnTime\": \"2021-12-22 15:30:30\",\n" +
                "  \"merchantTxnTimeZone\": \"+08:00\",\n" +
                "  \"productType\": \"CARD\",\n" +
                "  \"subProductType\": \"DIRECT\",\n" +
                "  \"txnType\": \"SALE\",\n" +
                "  \"orderAmount\": \"20\",\n" +
                "  \"orderCurrency\": \"USD\",\n" +
                "  \"txnOrderMsg\": \"{\\\"returnUrl\\\":\\\"https://www.ronhan.com/\\\",\\\"products\\\":\\\"[{\\\\\\\"price\\\\\\\":\\\\\\\"110.00\\\\\\\",\\\\\\\"num\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"name\\\\\\\":\\\\\\\"iphone11\\\\\\\",\\\\\\\"currency\\\\\\\":\\\\\\\"USD\\\\\\\"}]\\\",\\\"transactionIp\\\":\\\"127.0.0.1\\\",\\\"appId\\\":1700077023031386112,\\\"javaEnabled\\\":false,\\\"colorDepth\\\":\\\"24\\\",\\\"screenHeight\\\":\\\"1080\\\",\\\"screenWidth\\\":\\\"1920\\\",\\\"timeZoneOffset\\\":\\\"-480\\\",\\\"accept\\\":\\\"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\\\",\\\"userAgent\\\":\\\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36\\\",\\\"contentLength\\\":\\\"340\\\",\\\"language\\\":\\\"zh-CN\\\"}\",\n" +
                "  \"cardInfo\": \"{\\\"cardNumber\\\":\\\"4000027891380961\\\",\\\"cvv\\\":\\\"789\\\",\\\"month\\\":\\\"12\\\",\\\"year\\\":\\\"2023\\\",\\\"holderName\\\":\\\"test sandbox\\\"}\",\n" +
                "  \"shippingInformation\": \"{\\\"firstName\\\":\\\"ShippingFirstName\\\",\\\"lastName\\\":\\\"ShippingLastName\\\",\\\"phone\\\":\\\"188888888888\\\",\\\"email\\\":\\\"shipping@test.com\\\",\\\"postalCode\\\":\\\"888888\\\",\\\"address\\\":\\\"ShippingAddress\\\",\\\"country\\\":\\\"CN\\\",\\\"province\\\":\\\"SH\\\",\\\"city\\\":\\\"SH\\\",\\\"street\\\":\\\"lujiazui\\\",\\\"number\\\":\\\"1\\\",\\\"identityNumber\\\":\\\"110000\\\"}\",\n" +
                "  \"billingInformation\": \"{\\\"firstName\\\":\\\"billingFirstName\\\",\\\"lastName\\\":\\\"billingLastName\\\",\\\"phone\\\":\\\"18600000000\\\",\\\"email\\\":\\\"billing@test.com\\\",\\\"postalCode\\\":\\\"430000\\\",\\\"address\\\":\\\"BillingAddress\\\",\\\"country\\\":\\\"CN\\\",\\\"province\\\":\\\"HK\\\",\\\"city\\\":\\\"HK\\\",\\\"street\\\":\\\"jianshazui\\\",\\\"number\\\":\\\"2\\\",\\\"identityNumber\\\":\\\"220000\\\"}\",\n" +
                "}";

        TreeMap<String, Object> data = (TreeMap<String, Object>) JsonUtil.json2Map(json);
        String hash = CryptoUtil.hash(StringUtil.concatValue(data), privateKey);
        System.out.println("hash = " + hash);
    }

    /**
     * JS下单生成sign
     * <p>
     * Note:
     * map的value中出现的\需要匹配，否则生成的hash值不对，会造成签名失败；也有可能会出现反序列化报错。
     * </p>
     */
    @Test
    void doTransactionInJs() {
        String json = "{\n" +
                "    \"merchantNo\": \"800209\",\n" +
                "    \"merchantTxnId\": \"1654675447668\",\n" +
                "    \"merchantTxnTime\": null,\n" +
                "    \"merchantTxnTimeZone\": null,\n" +
                "    \"productType\": \"CARD\",\n" +
                "    \"subProductType\": \"DIRECT\",\n" +
                "    \"txnType\": \"SALE\",\n" +
                "    \"orderAmount\": \"22.1\",\n" +
                "    \"orderCurrency\": \"USD\",\n" +
                "    \"originTransactionId\": null,\n" +
                "    \"risk3dsStrategy\": null,\n" +
                "    \"txnOrderMsg\": \"{\\\"returnUrl\\\":\\\"https://www.ronhan.com/\\\",\\\"products\\\":\\\"[{\\\\\\\"name\\\\\\\":\\\\\\\"iphone 11\\\\\\\",\\\\\\\"price\\\\\\\":\\\\\\\"5300.00\\\\\\\",\\\\\\\"num\\\\\\\":\\\\\\\"2\\\\\\\",\\\\\\\"currency\\\\\\\":\\\\\\\"CNY\\\\\\\"},{\\\\\\\"name\\\\\\\":\\\\\\\"macBook\\\\\\\",\\\\\\\"price\\\\\\\":\\\\\\\"1234.00\\\\\\\",\\\\\\\"num\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"currency\\\\\\\":\\\\\\\"USD\\\\\\\"}]\\\",\\\"transactionIp\\\":\\\"127.0.0.1\\\",\\\"appId\\\":1700077023031386112,\\\"javaEnabled\\\":false,\\\"colorDepth\\\":\\\"24\\\",\\\"screenHeight\\\":\\\"1080\\\",\\\"screenWidth\\\":\\\"1920\\\",\\\"timeZoneOffset\\\":\\\"-480\\\",\\\"accept\\\":\\\"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\\\",\\\"userAgent\\\":\\\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36\\\",\\\"contentLength\\\":\\\"340\\\",\\\"language\\\":null}\",\n" +
                "    \"shippingInformation\": \"{\\\"firstName\\\":\\\"Shipping\\\",\\\"lastName\\\":\\\"Name\\\",\\\"phone\\\":\\\"188888888888\\\",\\\"email\\\":\\\"taoyun15@gmail.com\\\",\\\"postalCode\\\":\\\"888888\\\",\\\"address\\\":\\\"Shipping Address Test\\\",\\\"country\\\":\\\"CN\\\",\\\"province\\\":\\\"HB\\\",\\\"city\\\":\\\"WH\\\",\\\"street\\\":\\\"833 Cheung Sha Wan Road\\\",\\\"number\\\":\\\"1\\\",\\\"identityNumber\\\":\\\"82962612865\\\"}\",\n" +
                "    \"billingInformation\": \"{\\\"firstName\\\":\\\"test\\\",\\\"lastName\\\":\\\"test\\\",\\\"phone\\\":\\\"18600000000\\\",\\\"email\\\":\\\"taoyun15@gmail.com\\\",\\\"postalCode\\\":\\\"430000\\\",\\\"address\\\":\\\"Unit 1113, 11/F, Tower 2, Cheung Sha Wan Plaza, 833 Cheung Sha Wan Road, Lai Chi Kok\\\",\\\"country\\\":\\\"CN\\\",\\\"province\\\":\\\"HB\\\",\\\"city\\\":\\\"HK\\\"}\"\n" +
                "}";

        TreeMap<String, Object> data = (TreeMap<String, Object>) JsonUtil.json2Map(json);
        String hash = CryptoUtil.hash(StringUtil.concatValue(data), privateKey);
        System.out.println("hash = " + hash);
    }

    /**
     * 两方接口支付-本地支付-Alipay+
     */
    @Test
    void doAlipay() {
        String json = "{\n" +
                "  \"merchantNo\": \"800209\",\n" +
                "  \"merchantTxnId\": \"16460431556919\",\n" +
                "  \"merchantTxnTime\":\"2023-08-28 15:30:30\",\n" +
                "  \"merchantTxnTimeZone\":\"+08:00\",\n" +
                "  \"productType\":\"LPMS\",\n" +
                "  \"subProductType\":\"DIRECT\",\n" +
                "  \"txnType\": \"SALE\",\n" +
                "  \"orderAmount\": \"20\",\n" +
                "  \"paymentMode\": \"WAP\",\n" +
                "  \"osType\": \"ANDROID\",\n" +
                "  \"orderCurrency\":  \"USD\",\n" +
                "  \"cardInfo\": \"{\\\"cardNumber\\\":\\\"4918190000000002\\\",\\\"cvv\\\":\\\"123\\\",\\\"month\\\":\\\"05\\\",\\\"year\\\":\\\"24\\\",\\\"holderName\\\":\\\"abel xx\\\"}\",\n" +
                "  \"txnOrderMsg\": \"{\\\"returnUrl\\\":\\\"https://www.ronhan.com/\\\",\\\"products\\\":\\\"[{\\\\\\\"name\\\\\\\":\\\\\\\"iphone 11\\\\\\\",\\\\\\\"price\\\\\\\":\\\\\\\"5300.00\\\\\\\",\\\\\\\"num\\\\\\\":\\\\\\\"2\\\\\\\",\\\\\\\"currency\\\\\\\":\\\\\\\"CNY\\\\\\\"}]\\\",\\\"transactionIp\\\":\\\"2600:1700:f0f1:1e30:d08f:c6da:976c:45cd\\\",\\\"appId\\\":1700077023031386112}\",\n" +
                "  \"lpmsInfo\":\"{\\\"lpmsType\\\":\\\"Alipay+\\\",\\\"bankName\\\":\\\"\\\",\\\"iban\\\":\\\"\\\"}\",\n" +
                "  \"shippingInformation\":\"{\\\"firstName\\\":\\\"da\\\",\\\"lastName\\\":\\\"xiong\\\",\\\"phone\\\":\\\"8522847000\\\",\\\"email\\\":\\\"shipping@example.com\\\",\\\"postalCode\\\":\\\"123456\\\",\\\"address\\\":\\\"HHHEEII\\\",\\\"country\\\":\\\"MY\\\",\\\"province\\\":\\\"BABA\\\",\\\"city\\\":\\\"BALALA\\\",\\\"street\\\":\\\"1010\\\",\\\"number\\\":\\\"20-1202\\\",\\\"identityNumber\\\":\\\"11112223333\\\",\\\"birthDate\\\":\\\"2020/12/28\\\"}\",\n" +
                "  \"billingInformation\":\"{\\\"firstName\\\":\\\"José\\\",\\\"lastName\\\":\\\"Silva\\\",\\\"phone\\\":\\\"8522847035\\\",\\\"email\\\":\\\"jose@example.com\\\",\\\"postalCode\\\":\\\"61919-230\\\",\\\"address\\\":\\\"Rua E\\\",\\\"country\\\":\\\"BR\\\",\\\"province\\\":\\\"CE\\\",\\\"city\\\":\\\"Maracanaú\\\",\\\"street\\\":\\\"1040\\\",\\\"identityNumber\\\":\\\"853.513.468-93\\\",\\\"birthDate\\\":\\\"2000/12/20\\\"}\"\n" +
                "}";

        TreeMap<String, Object> data = (TreeMap<String, Object>) JsonUtil.json2Map(json);
//        privateKey = "b2195d4b09b14b2691083c50b5120e7e";
        String hash = CryptoUtil.hash(StringUtil.concatValue(data), privateKey);
        System.out.println("hash = " + hash);
    }

}
