package com.cr.archive.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpUtil {
    public static String get(String tag){
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)//一、连接超时：connectionTimeout-->指的是连接一个url的连接等待时间
                .setSocketTimeout(5000)// 二、读取数据超时：SocketTimeout-->指的是连接上一个url，获取response的返回等待时间
                .setConnectionRequestTimeout(5000)
                .build();

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://statsroyale.com/profile/" + tag);
        get.setConfig(requestConfig);
        HttpResponse response = null;
        String message = null;
        try {
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = response.getEntity();
                message = EntityUtils.toString(resEntity, "utf-8");
            } else {
                System.out.println("请求失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }


    public static String getCards(String tag){
        refresh(tag);
        return get(tag + "/cards");
    }

    private static void refresh(String tag) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)//一、连接超时：connectionTimeout-->指的是连接一个url的连接等待时间
                .setSocketTimeout(5000)// 二、读取数据超时：SocketTimeout-->指的是连接上一个url，获取response的返回等待时间
                .setConnectionRequestTimeout(5000)
                .build();

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://statsroyale.com/profile/" + tag + "/refresh");
        get.setConfig(requestConfig);
        HttpResponse response = null;
        String message = null;
        try {
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = response.getEntity();
                message = EntityUtils.toString(resEntity, "utf-8");
            } else {
                System.out.println("请求失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("getCards========"+ getCards("82UYLC9J"));
    }
}
