package com.abel.onerway.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.abel.onerway.utils.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.TreeMap;

@Controller
public class PaymentController {

    /**
     * 两方支付<br>
     * 但是接口调不通，没排查到问题
     * @param map
     * @return
     */
    @PostMapping("/v1/txn/doTransaction")
    public String doTransaction(@RequestBody TreeMap<String, Object> map) {
        final String url = "https://sandbox-v3-acquiring.pacypay.com/v1/txn/doTransaction";
        Map<String, Object> data = JsonUtil.json2Map(JsonUtil.map2Json(map));

        String transactionResponse = HttpRequest.post(url)
                .method(Method.POST)
                .header("Content-Type", "application/json")
                .header("Accept-Encoding", "gzip,deflate,br")
                .header("Connection", "keep-alive")
                .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                .header("accept", "*/*")
                .header("Host", "sandbox-v3-acquiring.pacypay.com")
                .header("Content-Length", "1895")
                .form(data)
                .execute().body();
        System.out.println("transactionResponse = " + transactionResponse);
        return transactionResponse;
    }
}
