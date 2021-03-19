import com.alibaba.fastjson.JSONArray;
import com.xuan.utils.get100Image.GetImageFromVideo;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XUAN
 * @date 2021/3/19 - 17:59
 * @references
 * @purpose
 * @errors
 */
public class TTT {
    @Test
    public void T(){
        List<String> list = new ArrayList();
        for (int i = 1; i <= 100; i++) {
//            list.add("PreviewImages/"+i+".jpg");
            System.out.println("<img src=\"PreviewImages/"+i+".jpg\"/>");
        }
//        System.out.println(JSONArray.toJSONString(list));
    }

    @Test
    public void T2(){

        File file = new File("D:\\core\\Desktop\\新建文件夹\\oceans.mp4");
        new GetImageFromVideo(file).getAvgFrames(100);
    }
}
