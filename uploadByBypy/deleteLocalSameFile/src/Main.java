import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XUAN
 * @date 2021/3/19 - 12:34
 * @references
 * @purpose
 * @errors
 */
public class Main {
    public static void main(String[] args) {
        try {

            String c = SaveAndRead.read("compare.txt");
            c=c.replaceAll("(.+Same files.+\n)|(\n.+Different files([\\s\\S]*)+)","");
            String[] sameFiles = c.split("\n");
            for (int i = 0; i < sameFiles.length; i++) {
                File file = new File(sameFiles[i].replaceAll("^\\w - ",""));
                file.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
