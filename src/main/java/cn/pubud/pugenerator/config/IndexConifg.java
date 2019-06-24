package cn.pubud.pugenerator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.lang.reflect.Method;

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
        String osName = System.getProperty("os.name");
        try
        {
            if (osName.startsWith("Mac OS"))
            {
                //doc
                Class fileMgr = Class.forName("com.apple.eio.FileManager");
                Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] {String.class});
                openURL.invoke(null, new Object[] {URL});
            }
            else if (osName.startsWith("Windows"))
            {
                //Windows
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + URL);
            }
            else
            {
                //assume Unix or Linux
                String[] browsers = {"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape"};
                String browser = null;
                for (int count = 0; count < browsers.length && browser == null; count++)
                {
                    if (Runtime.getRuntime().exec(new String[] {"which", browsers[count]}).waitFor() == 0)
                    {
                        browser = browsers[count];
                    }
                }
                if (browser != null)
                {
                    Runtime.getRuntime().exec(new String[] {browser, URL});
                }
            }
        }
        catch (Exception ex)
        {
            //ExpWork.doExp(ex);
        }
    }
}
