import invertedOrderZip.InvertedOrderZip;
import net.lingala.zip4j.exception.ZipException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author XUAN
 * @date 2021/3/19 - 10:35
 * @references
 * @purpose
 * @errors
 */

public class TestDemo {
    @Test
    public void t() throws ZipException, IOException {
//        File file = new File("D:\\core\\Desktop\\新建文件夹\\oceans.mp4");
//        File zips = ZipUtils.CreateSplitZipFile_10M(file);
//        InvertedOrderZip.work(zips);
        String userDir = System.getProperties().getProperty("user.dir");
        System.out.println(userDir);
        String shpath = userDir+File.separator+"bypy.sh";   //程序绝对路径
        String command1 = "chmod 777 " + shpath;
        Runtime.getRuntime().exec(command1);
        Runtime.getRuntime().exec(shpath);
    }
}
