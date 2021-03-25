import getFiles.GetFile;
import getFiles.SelectModeImpl;
import invertedOrderZip.InvertedOrderZip;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

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
    public static void main(String[] args) throws Exception {
        GetFile getFile = new GetFile(new File(System.getProperty("user.dir")));
        try {
            Stack<File> fileStack = getFile.getFiles(SelectModeImpl.selectVideoByPostfixes());
            for (File file : fileStack) {
                System.out.println(file.getAbsolutePath());
                if (file.length()>1024*1024){
                    File root = ZipUtils.CreateSplitZipFile(file);
                    InvertedOrderZip.work(root);
                }
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}

