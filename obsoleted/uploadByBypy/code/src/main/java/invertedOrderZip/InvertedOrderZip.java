package invertedOrderZip;

import java.io.File;

/**
 * @author XUAN
 * @date 2021/3/17 - 18:14
 * @references
 * @purpose 逆序交换 .zx (x为数字) 文件的名字
 *   比如 zip 分卷压缩后，有文件 t.zip、t.01、z.z02、z.z03、z.z04
 *   则重命名:t.01->z.z04
 *           t.02->z.z03
 *           t.03->z.z02
 *           t.04->z.z01
 * @errors
 */
public class InvertedOrderZip {
    public static void work(File root){
        // 获取当前文件夹下以 .zx (x为数字) 结尾的文件
        File[] files = root.listFiles((File f)->{
            String suffix = f.getName().replaceAll(".+\\.","");
            return suffix.replaceAll("z\\d+","").equals("");
        });
        files = FileSort.sortFileByName(files, FileSort.asc);
        for (int i = 0; i < files.length / 2; i++) {
            File head = files[i];
            File tail = files[files.length-i-1];
            File temp =new File(files[i].getParent()+File.separator+"temp");
            files[i].renameTo(temp);
            files[files.length-i-1].renameTo(head);
            temp.renameTo(tail);
            temp.delete();
        }
    }
}
