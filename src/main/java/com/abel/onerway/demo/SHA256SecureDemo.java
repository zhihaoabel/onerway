package com.abel.onerway.demo;



import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.util.TreeMap;

public class SHA256SecureDemo {

    public static void main(String[] args) throws Exception{
        TreeMap data = new TreeMap();
        data.put("merchantNo","800209");
        data.put("merchantTxnId","1640229747901");
        data.put("merchantTxnTime","2021-12-22 15:30:30");
        data.put("merchantTxnTimeZone","+08:00");
        data.put("productType","CARD");
        data.put("subProductType","DIRECT");
        data.put("txnType","SALE");
        data.put("orderAmount","20");
        data.put("orderCurrency","USD");
        data.put("txnOrderMsg","{\"returnUrl\":\"https://www.ronhan.com/\",\"products\":\"[{\\\"price\\\":\\\"110.00\\\",\\\"num\\\":\\\"1\\\",\\\"name\\\":\\\"iphone11\\\",\\\"currency\\\":\\\"USD\\\"}]\",\"transactionIp\":\"127.0.0.1\",\"appId\":1700077023031386112,\"javaEnabled\":false,\"colorDepth\":\"24\",\"screenHeight\":\"1080\",\"screenWidth\":\"1920\",\"timeZoneOffset\":\"-480\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\",\"userAgent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36\",\"contentLength\":\"340\",\"language\":\"zh-CN\"}");
        data.put("cardInfo","{\"cardNumber\":\"4000027891380961\",\"cvv\":\"789\",\"month\":\"12\",\"year\":\"2023\",\"holderName\":\"test sandbox\"}");
        data.put("shippingInformation","{\"firstName\":\"ShippingFirstName\",\"lastName\":\"ShippingLastName\",\"phone\":\"188888888888\",\"email\":\"shipping@test.com\",\"postalCode\":\"888888\",\"address\":\"ShippingAddress\",\"country\":\"CN\",\"province\":\"SH\",\"city\":\"SH\",\"street\":\"lujiazui\",\"number\":\"1\",\"identityNumber\":\"110000\"}");
        data.put("billingInformation","{\"firstName\":\"billingFirstName\",\"lastName\":\"billingLastName\",\"phone\":\"18600000000\",\"email\":\"billing@test.com\",\"postalCode\":\"430000\",\"address\":\"BillingAddress\",\"country\":\"CN\",\"province\":\"HK\",\"city\":\"HK\",\"street\":\"jianshazui\",\"number\":\"2\",\"identityNumber\":\"220000\"}");

//        data.put("bibllingInformation", "{\"country\":\"US\",\"email\":\"test@qq.com\"}");
//        data.put("merchantNo","800209");
//        data.put("merchantTxnId","1654675447648");
//        data.put("merchantTxnTime","2022-03-08 16:04:07");
//        data.put("merchantTxnTimeZone","+08:00");
//        data.put("orderAmount","35");
//        data.put("orderCurrency","USD");
//        data.put("productType","CARD");
//        data.put("cardInfo","{\"holderName\":\"CL BRW2\"}");
//        data.put("shippingInformation","{\"country\":\"US\",\"email\":\"test@qq.com\"}");
//        data.put("subProductType","DIRECT");
//        data.put("txnOrderMsg","{\"appId\":\"1700077023031386112\",\"returnUrl\":\"http://v1-demo.test.com/\",\"products\":\"[{\\\"price\\\":\\\"110.00\\\",\\\"num\\\":\\\"1\\\",\\\"name\\\":\\\"iphone11\\\",\\\"currency\\\":\\\"USD\\\"}]\"}");
//        data.put("txnType","SALE");

        String toBeSignedData =strcatValueSign(data);
        System.out.println("toBeSignedData = " + toBeSignedData);
        String key= "59c5b49a58c74340b28ecc68004e815a";
        String sign= signSha256(key,toBeSignedData);
        System.out.println(sign);
    }

    private static String strcatValueSign(TreeMap treeMap) {
        StringBuffer buffer = new StringBuffer();
        treeMap.forEach((k, v) -> {
            if (StringUtils.isNotBlank((CharSequence) v)) {
                buffer.append(v);
            }
        });
        return buffer.toString();
    }


    public static String signSha256(String key, String toBeSignedData){

        String str=toBeSignedData + key;
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    /**
     * 将字节转换为十六进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
