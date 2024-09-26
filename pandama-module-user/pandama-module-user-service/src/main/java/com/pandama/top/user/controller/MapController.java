package com.pandama.top.user.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(tags = "用户信息相关接口")
@Validated
@Slf4j
public class MapController {

    /**
     *
     * @param response
     * @param LAYER
     * @param TILECOL
     * @param TILEROW
     * @param TILEMATRIX
     * @throws IOException
     */
    @GetMapping("getTiles/{LAYER}")
    public void getTiles(HttpServletResponse response, @PathVariable("LAYER") String LAYER,
                         @RequestParam("TILECOL") String TILECOL,
                         @RequestParam("TILEROW") String TILEROW,
                         @RequestParam("TILEMATRIX")String TILEMATRIX) throws IOException {

        String tilesPath = "lianyungangtianditu";
        ServletOutputStream out = null;
        try {
            BufferedImage image = ImageIO.read(Files.newInputStream(Paths.get(tilesPath + "/" + LAYER + "/" + TILEMATRIX + "/" + TILEROW + "/" + TILECOL + ".png")));
            response.setContentType("image/png");
            response.setHeader("Cache-Control", "public");
            out = response.getOutputStream();
            ImageIO.write(image, "png", out);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }


}
