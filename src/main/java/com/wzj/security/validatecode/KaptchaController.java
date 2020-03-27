package com.wzj.security.validatecode;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;



/**
 *   验证码的生成
 */
@Controller
@Slf4j
public class KaptchaController {

    /**
     * 1、验证码工具
     */
    @Autowired
    private DefaultKaptcha kaptcha;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    /**
     * 生成验证码
     * @param rep
     * @throws Exception
     */
    @RequestMapping("/validCode")
    public void getKaptcha(HttpServletResponse rep, HttpServletRequest req)
            throws Exception {
        byte[] captchaChallengeAsJpeg;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到sessionStrategy中
            String rightCode = kaptcha.createText();

//            req.getSession().setAttribute("validCode",rightCode);
            ImageCode imageCode = new ImageCode(rightCode, Constant.EXPIRETIME);

            sessionStrategy.setAttribute(new ServletWebRequest(req), Constant.SESSION_KEY, imageCode);

            log.info("生成的验证码：rightCode:{}", rightCode);

            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = kaptcha.createImage(rightCode);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            rep.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        rep.setHeader("Cache-Control", "no-store");
        rep.setHeader("Pragma", "no-cache");
        rep.setDateHeader("Expires", 0);
        rep.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = rep.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);

        try {
            responseOutputStream.flush();
        }finally {
            responseOutputStream.close();
        }


    }
}
