package com.vectortrading.vectortrading;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.vectortrading.vectortrading.entity.MarketDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class})
@Import(cn.hutool.extra.spring.SpringUtil.class)
@EnableScheduling
@Slf4j
public class VectorTradingApplication {
    public static void main(String[] args) {
        SpringApplication.run(VectorTradingApplication.class, args);
    }

    @Value("${ths.get_access_token.url}")
    private String getTokenUrl;
    @Value("${ths.real_time_quotation.url}")
    private String realTimeQuotationUrl;

    private static final String REFRESH_TOKEN = "eyJzaWduX3RpbWUiOiIyMDI1LTA2LTI4IDIxOjQyOjA0In0=.eyJ1aWQiOiI3OTA1ODQxMTAiLCJ1c2VyIjp7ImFjY291bnQiOiJ3b2luZzAzMCIsImF1dGhVc2VySW5mbyI6e30sImNvZGVDU0kiOltdLCJjb2RlWnpBdXRoIjpbXSwiaGFzQUlQcmVkaWN0IjpmYWxzZSwiaGFzQUlUYWxrIjpmYWxzZSwiaGFzQ0lDQyI6ZmFsc2UsImhhc0NTSSI6ZmFsc2UsImhhc0V2ZW50RHJpdmUiOmZhbHNlLCJoYXNGVFNFIjpmYWxzZSwiaGFzRmFzdCI6ZmFsc2UsImhhc0Z1bmRWYWx1YXRpb24iOmZhbHNlLCJoYXNISyI6dHJ1ZSwiaGFzTE1FIjpmYWxzZSwiaGFzTGV2ZWwyIjpmYWxzZSwiaGFzUmVhbENNRSI6ZmFsc2UsImhhc1RyYW5zZmVyIjpmYWxzZSwiaGFzVVMiOmZhbHNlLCJoYXNVU0FJbmRleCI6ZmFsc2UsImhhc1VTREVCVCI6ZmFsc2UsIm1hcmtldEF1dGgiOnsiRENFIjpmYWxzZX0sIm1heE9uTGluZSI6MSwibm9EaXNrIjpmYWxzZSwicHJvZHVjdFR5cGUiOiJTVVBFUkNPTU1BTkRQUk9EVUNUIiwicmVmcmVzaFRva2VuRXhwaXJlZFRpbWUiOiIyMDI1LTA3LTI0IDE1OjMzOjUzIiwic2Vzc3Npb24iOiJlMmExZTAzMTFiMTRlMDA3Y2RiZWJiMWY5MGI5MDVkZiIsInNpZEluZm8iOnt9LCJ0cmFuc0F1dGgiOmZhbHNlLCJ1aWQiOiI3OTA1ODQxMTAiLCJ1c2VyVHlwZSI6IkZSRUVJQUwiLCJ3aWZpbmRMaW1pdE1hcCI6e319fQ==.BE93C1B6293590397EC4A91A1713836962610E4FEA90BA34964DD44A1923E976";


    @Scheduled(fixedRate = 10 * 1000)
    public void vt() {
        // a. 获取access_token
        String token = getToken();
        JSONObject requestBody = new JSONObject();
        requestBody.set("codes", "300033.SZ,600000.SH");
        requestBody.set("indicators", "open,high,volume,turnoverRatio");
        try (HttpResponse response = HttpRequest.post(realTimeQuotationUrl)
                .header("Content-Type", "application/json")
                .header("access_token", token)
                .body(requestBody.toString())
                .execute()){

            // 检查响应状态
            if (response.isOk()) {
                // 解析响应
                String responseBody = response.body();
                MarketDataResponse dto = JSONUtil.toBean(responseBody, MarketDataResponse.class);
                log.info("Market data: {}", JSONUtil.toJsonStr(dto));

                //TODO 通过指标进行交易决策
            }else {
                throw new RuntimeException(response.body());
            }
        } catch (Exception e) {
            log.error("请求失败: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getToken() {
        // 构建请求体
        JSONObject requestBody = new JSONObject();
        requestBody.set("refresh_token", REFRESH_TOKEN);
        try (HttpResponse response = HttpRequest.post(getTokenUrl)
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .execute()){

            // 检查响应状态
            if (response.isOk()) {
                // 解析响应
                String responseBody = response.body();
                JSONObject jsonResponse = JSONUtil.parseObj(responseBody);

                // 假设返回的JSON中包含access_token字段
                JSONObject data = jsonResponse.getJSONObject("data");
                return data.getStr("access_token");
            }else {
                throw new RuntimeException(response.body());
            }
        } catch (Exception e) {
            log.error("Error while refreshing token: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
