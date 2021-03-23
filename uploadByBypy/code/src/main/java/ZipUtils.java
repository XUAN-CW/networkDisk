import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;

/**
 * @author XUAN
 * @date 2021/3/18 - 19:05
 * @references
 * @purpose
 * @errors
 */
public class ZipUtils {

    /**
     * 传入文件,分卷压缩到同名目录下,每卷10M,成功则删除源文件
     * @param file
     */
    public static File CreateSplitZipFile(File file) throws Exception {

        if (file.length()<65536*10){
            throw new Exception("文件太小,至少160KiB");
        }

        //创建去掉后缀的同名目录
        File dir = new File(file.getParent() + File.separator +
                file.getName().replaceAll("\\..+$",""));

        // Initiate ZipFile object with the path/name of the zip file.
        ZipFile zipFile = zipFile = new ZipFile(dir.getAbsolutePath() + File.separator +
                file.getName() + ".zip");

        System.out.println(dir.mkdir()+":"+file.getAbsolutePath());
        // Build the list of files to be added in the array list
        // Objects of type File have to be added to the ArrayList
        ArrayList filesToAdd = new ArrayList();
        filesToAdd.add(new File(file.getAbsolutePath()));

        // Initiate Zip Parameters which define various properties such
        // as compression method, etc.
        ZipParameters parameters = new ZipParameters();

        // set compression method to store compression
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

        // Set the compression level. This value has to be in between 0 to 9
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        // Create a split file by setting splitArchive parameter to true
        // and specifying the splitLength. SplitLenth has to be greater than
        // 65536 bytes
        // Please note: If the zip file already exists, then this method throws an
        // exception
        long splitLength;
        if (file.length()<1024*1024*100){
            splitLength=file.length()/10;
        }else {
            splitLength = 1024*1024*10;
        }
        zipFile.createZipFile(filesToAdd, parameters, true, splitLength);
        file.delete();
        return dir;
    }
}

