package com.xuan.utils.getFiles;
import java.io.File;

/**
 * @author XUAN
 * @date 2021/3/4 - 20:17
 * @references
 * @purpose
 * @errors
 */
public class SelectModeImpl {

    /**
     * 根据后缀选择
     * @param postfixes
     * @return
     */
    public static SelectMode selectByPostfixes(String... postfixes) {
        return (File file) ->{
            for (String postfix : postfixes) {
                //文件名还没有输入的后缀长，直接返回 false
                //文件名比输入的后缀长，截取文件名最右边与后缀长度相同的子串，进行比较
                if (file.getName().length() > postfix.length()) {
                    String filePostfix=file.getName().substring(file.getName().length() - postfix.length());
                    //有些文件后缀为大写，这里统一转为小写再匹配,避免输入 `.mp4` 却匹配不到 `.MP4`
                    if (filePostfix.toLowerCase().equals(postfix.toLowerCase())){
                        return true;
                    }
                }
            }
            return false;
        };
    }

    public static SelectMode selectVideoByPostfixes(){
        return selectByPostfixes("avi","wmv","wmp","wm","asf","mpg","mpeg","mpe",
                "m1v","m2v","mpv2","mp2v","ts","tp","tpr","trp","vob","ifo","ogm","ogv","mp4",
                "m4v","m4p","m4b","3gp","3gpp","3g2","3gp2","mkv","rm","ram","rmvb","rpm","flv",
                "swf","mov","qt","nsv","dpg","m2ts","m2t","mts","dvr-ms","k3g","skm","evo","nsr",
                "amv","divx","webm","wtv","f4v","mxf");
    }

    /**
     * 选择文件名为 name 的文件
     * @param name
     * @return
     */
    public static SelectMode selectNameEquals(String name) {
        return (File f) -> f.getName().equals(name);
    }

    /**
     * 选择包含 str 的文件
     * @param str
     * @return
     */
    public static SelectMode selectNameContains(String str) {
        return (File f) -> f.getName().contains(str);
    }
}
