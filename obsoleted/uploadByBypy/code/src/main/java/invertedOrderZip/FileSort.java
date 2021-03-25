package invertedOrderZip;

import java.io.File;
import java.util.*;

/**
 * @author wzx
 * @time 2018/8/4
 */
public class FileSort {

    static String asc = "asc";
    static String desc = "desc";

    /**
     *
     * @param files
     * @param orderStr 排序:asc,des,不区分大小写
     * @return
     */
    public static List<File> sortFileByName(List<File> files, final String orderStr) {
        if (!orderStr.equalsIgnoreCase(asc) && orderStr.equalsIgnoreCase(desc)) {
            return files;
        }
        File[] files1 = files.toArray(new File[0]);
        Arrays.sort(files1, new Comparator<File>() {
            public int compare(File o1, File o2) {
                int n1 = extractNumber(o1.getName());
                int n2 = extractNumber(o2.getName());
                if(orderStr == null || orderStr.length() < 1 || orderStr.equalsIgnoreCase(asc)) {
                    return n1 - n2;
                } else {
                    //降序
                    return n2 - n1;
                }
            }
        });
        return new ArrayList<File>(Arrays.asList(files1));
    }

    private static int extractNumber(String name) {
        int i;
        try {
            String number = name.replaceAll("[^\\d]", "");
            i = Integer.parseInt(number);
        } catch (Exception e) {
            i = 0;
        }
        return i;
    }

    public static File[] sortFileByName(File[] files, final String orderStr) {
        List<File> fileList=new ArrayList<>();
        Collections.addAll(fileList, files);
        fileList = sortFileByName(fileList,orderStr);
        return fileList.toArray(new File[files.length]);
    }
}