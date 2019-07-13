package service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.utils.SilkToWavUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * 将silk转wav
 */
@RestController
@RequestMapping("/handler")
public class AudioConverterController {

    //http://localhost:9100/handler?cmd=audioconvert&url=http://static.cdn.longmaosoft.com/103501/887284/AtIVEuM9Ap8X2W7r8rsrEVbXf4Ui6n6I.silk

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("")
    public void handler(HttpServletRequest request, HttpServletResponse response) {
        // 处理编码
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("设置字符集异常{}", e.getMessage());
        }
        // 获取必要参数
        String cmd = request.getParameter("cmd");
        String url = request.getParameter("url");
        System.out.println(cmd);
        System.out.println(url);

        try {
            response.setContentType("audio/x-wav");
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            String wavName = fileName.replace(".silk", ".wav");
            if (fileName.endsWith(".silk")) {
                wavName = fileName.replace(".silk", ".wav");
            } else if (fileName.endsWith(".aac")) {
                wavName = fileName.replace(".aac", ".wav");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + wavName);
            OutputStream stream = response.getOutputStream();
            SilkToWavUtil.converterAudio(new URL(url), stream);
            stream.flush();
            stream.close();

        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFoundException-{},{}", e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Exception- {},{}", e.getMessage(), e);
        }
    }

    @RequestMapping("/test")
    public String test() {
        return "TEST SUCCESS";
    }
}
