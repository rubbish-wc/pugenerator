package cn.pubud.pugenerator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.IOException;

/**
 * @Author: charleyZZZZ
 * @Date: 2019/6/24 16:59
 * @Version 1.0
 */
@Configuration
class IndexConfig {

    private final static String URL = "http://localhost:8080/generator/generate";

    private final static Logger LOGGER = LoggerFactory.getLogger(IndexConfig.class);

    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() throws IOException {
        LOGGER.info("应用已经准备就绪 ... 启动浏览器 ... 自动打开代码生成界面 ...");
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("rundll32 url.dll,FileProtocolHandler " + URL);
    }
}
