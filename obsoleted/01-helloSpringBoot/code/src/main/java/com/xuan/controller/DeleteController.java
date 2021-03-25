package com.xuan.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
@CrossOrigin
@RestController
public class DeleteController {

    @DeleteMapping("/delete")
    public void deleteFileByRequestURI(@RequestBody String body){

        JSONObject jsonObject = (JSONObject) JSONObject.parse(body);
        File file = new File((String) jsonObject.get("path"));
        System.out.println(file);
        file.delete();
    }

}
