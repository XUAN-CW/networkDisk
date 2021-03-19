package com.xuan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class DeleteController {

//     http://localhost:9812/hello
    @DeleteMapping("/delete/**")
    public void deleteFileByRequestURI(HttpServletRequest request){
        System.out.println(request.getRequestURI());
        File file=new File(request.getRequestURI().substring("delete".length()+2));
        System.out.println(file);
        file.delete();
    }

}
