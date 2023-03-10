package com.bytedance.douyinclouddemo.controller;

import com.bytedance.douyinclouddemo.model.JsonResponse;
import com.bytedance.douyinclouddemo.model.SetNameRequest;
import com.bytedance.douyinclouddemo.service.HelloService;
import com.bytedance.douyinclouddemo.service.impl.HelloServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@RestController
public class HelloController {

    @Autowired
    private HelloServiceFactory factory;

    @Value("${cloud.env}")
    private String envMark;

    @GetMapping("/api/hello")
    public JsonResponse hello(@RequestParam(value = "target", defaultValue = "mongodb") String target) {
        JsonResponse response = new JsonResponse();
        try {
            HelloService helloService = factory.getHelloService(target);
            response.success("env:" + envMark + " hello " +  helloService.hello(target));
        }catch (Exception e){
            response.failure("unknown error");
        }
        return response;
    }

    @PostMapping("/api/set_name")
    public JsonResponse setName(@RequestBody SetNameRequest setNameRequest) {
        JsonResponse response = new JsonResponse();
        try {
            HelloService helloService = factory.getHelloService(setNameRequest.getTarget());
            helloService.setName(setNameRequest.getTarget(),setNameRequest.getName());
            response.success("");
        }catch (Exception e){
            response.failure("unknown error");
        }
        return response;
    }

    //https验证
    @GetMapping("/api/https")
    public String https() throws IOException {
        URL url = new URL("https://developer.toutiao.com/api/apps/qrcode");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("Content-Type", "application/json");

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("{\n    \"appname\": \"douyin\"\n}");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        return response;
    }

    //http验证
    @GetMapping("/api/http")
    public String http() throws IOException {
        URL url = new URL("http://developer.toutiao.com/api/apps/qrcode");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("Content-Type", "application/json");

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("{\n    \"appname\": \"douyin\"\n}");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        return response;
    }
}


