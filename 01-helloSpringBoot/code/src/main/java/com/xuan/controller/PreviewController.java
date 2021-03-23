package com.xuan.controller;

import com.alibaba.fastjson.JSONObject;
import com.xuan.utils.get100Image.GetImageFromVideo;
import org.springframework.web.bind.annotation.*;

import java.io.File;

/**
 * @author XUAN
 * @date 2021/3/19 - 17:29
 * @references
 * @purpose
 * @errors
 */
@CrossOrigin
@RestController
public class PreviewController {
    @PostMapping("/getPreviewImages")
    public void deleteFileByRequestURI(@RequestBody String body){
        JSONObject jsonObject = (JSONObject) JSONObject.parse(body);
        File file = new File((String) jsonObject.get("movie"));
        System.out.println(file.getName());
        new GetImageFromVideo(file).getAvgFrames(100);
    }
}
