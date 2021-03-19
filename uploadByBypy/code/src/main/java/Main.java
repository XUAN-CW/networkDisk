import invertedOrderZip.InvertedOrderZip;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;

/**
 * @author XUAN
 * @date 2021/3/17 - 14:15
 * @references
 * @purpose
 * @errors
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        File file = new File("D:\\core\\java\\Tools\\uploadByBypy\\code\\oceans.mp4");


        try {
            File root = ZipUtils.CreateSplitZipFile_10M(file);
            InvertedOrderZip.work(root);
        } catch (ZipException e) {
            e.printStackTrace();
        }


    }
}

