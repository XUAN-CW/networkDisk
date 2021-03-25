package com.xuan.controller;

import com.alibaba.fastjson.JSONArray;
import com.xuan.domain.YellowResources;
import com.xuan.utils.getFiles.GetFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author XUAN
 * @date 2021/3/14 - 18:15
 * @references
 * @purpose
 * @errors
 */
@CrossOrigin
@RestController
public class HomeController {
    // http://localhost:9812/getdir
    @GetMapping("/getdir")
    @ResponseBody
    public String deleteFileByRequestURI() throws Exception {
        GetFile getFile = new GetFile(new File("."));
        Stack<File> files = getFile.getFiles((file)->{
            List<String> unwanted = new ArrayList<>();//不想要的文件的正则表达式
            unwanted.add(".+!qB$");
            unwanted.add("^\\..+");
            unwanted.add("PreviewImages");
            for (String regex:unwanted) {
                if (file.getName().startsWith(".")){
                }
                if (file.getName().replaceAll(regex,"").equals("")){
                    return false;
                }
            }
            return true;
        });

        List<YellowResources> h = new ArrayList<>();
        files.forEach((file -> {
            YellowResources temp = new YellowResources();
            temp.setName(file.getName());
            temp.setUrl(file.getPath().replaceAll("\\\\","/"));
            temp.setSize(toBKMG(file.length()));
            h.add(temp);
        }));

        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(h);
        String listJson = jsonArray.toString();
        System.out.println(listJson);
        return listJson;
    }

    String toBKMG(long length){
        String[] units= new String[]{"B","K", "M", "G"};
        int unit=0;
        for (;unit<units.length;unit++){
            if (length<1024){
                break;
            }else {
                length/=1024;
            }
        }
        return length+units[unit];
    }

}
