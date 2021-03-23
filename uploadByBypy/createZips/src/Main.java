import getFiles.SelectMode;

import java.io.File;
import java.util.*;

/**
 * @author XUAN
 * @date 2021/3/20 - 1:36
 * @references
 * @purpose
 * @errors
 */
public class Main {
    public static void main(String[] args) throws Exception {
        File file = getFileOnCurrentPathByALotOfCondition();

        if (null!=file){ // linux zip 压缩最小需要 64K,而我想最少压出 5 个文件

            //分卷压缩后 zip 文件的数目
            long zips=VolumeNumber(file);
            //在被分割文件的同级目录下创建无后缀的同名目录
            File dir = new File(file.getParent()+File.separator+
                    getFileNameWithoutExtension(file)+"["+zips+"]");

            List<String> commands=new ArrayList<>();



            //在同名目录下的临时 zip 文件
            File tempZipFile = new File(dir.getAbsolutePath() + File.separator + "zipTempName" + new Date().getTime());
            String dirLinuxPath = dir.getAbsolutePath().replaceAll("([\\[\\]])","\\\\$1");
            String zipCommand = " zip "+
                    ""+"-m"+
                    " "+tempZipFile.getAbsolutePath()+
                    " "+file.getAbsolutePath();
            String zipSplitCommand = " zip"+
                    " -s"+
                    " "+(zips==5 ? file.length()/zips + 1 : 1024*1024*15) +
                    " "+tempZipFile.getAbsolutePath()+
                    " --out"+
                    " " + dir.getAbsolutePath()+File.separator+dir.getName();
            String zipAndSplit = zipCommand+" && " +zipSplitCommand;

            //这里的中括号需要转义
            String rmTempZipFile="rm -rf" + " " +tempZipFile.getAbsolutePath()
                    .replaceAll("([\\[\\]])","\\\\$1")+".zip";

            File invertedOrderZip_jar = new File(dir.getAbsolutePath()+File.separator+"invertedOrderZip.jar");
            String invertedOrderZip_jarLinuxPath = invertedOrderZip_jar.getAbsolutePath().replaceAll("([\\[\\]])","\\\\$1");
            commands.add(" mkdir"+" "+dir.getAbsolutePath());
            commands.add(zipAndSplit);
            commands.add(rmTempZipFile);
            commands.add("cp "+" "+invertedOrderZip_jar.getName()+" "+invertedOrderZip_jarLinuxPath);
            commands.add("java -jar"+" "+invertedOrderZip_jarLinuxPath);
            commands.add("rm -rf"+" "+invertedOrderZip_jarLinuxPath);
            String c="";
            for (int i = 0; i < commands.size(); i++) {
                c+="output=`"+commands.get(i)+"`"+"\n";
            }
            SaveAndRead.save("createZips.sh",c);
        }
    }

    private static File getFileOnCurrentPathByALotOfCondition(){
        return recursiveTraversalFolderForFindAFile((f)->{
            List<Boolean> selectionCondition = new ArrayList<>();
            //选出大于 64K*5 的
            selectionCondition.add(f.length()>1024*64*5);
            //选出以下后缀的
            selectionCondition.add(
                    f.getName().toLowerCase().endsWith(".mp4")||
                    f.getName().toLowerCase().endsWith(".wmv")||
                    f.getName().toLowerCase().endsWith(".wmv")||
                    f.getName().toLowerCase().endsWith(".mkv")||
                    f.getName().toLowerCase().endsWith(".flv")||
                    f.getName().toLowerCase().endsWith(".mov")||
                    f.getName().toLowerCase().endsWith(".rmvb"));
            //有一个条件不符合，返回 false
            for (Boolean condition : selectionCondition) {
                if (!condition) {
                    return false;
                }
            }
            return true;
        }, new File(System.getProperty("user.dir")));
    }

    private static File recursiveTraversalFolderForFindAFile(SelectMode selectMode, File currentDir) {
        File result=null;
        // 获取当前文件夹下所有 文件/文件夹
        File[] fileArr = currentDir.listFiles();
        if (null == fileArr || fileArr.length == 0) {
//				System.out.println("["+temp.getAbsolutePath()+"]"+" 为空!");
        } else {
            // 在操作当前目录下所有 文件/文件夹
            for (File file : fileArr) {
                if (file.isDirectory()) {
                    result = recursiveTraversalFolderForFindAFile(selectMode, file);// 是文件夹，继续往下递归
                    if (result!=null){
                        break;
                    }
                } else {
                    if (selectMode.selected(file)){
                        result = file;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static String getFileNameWithoutExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(0,fileName.lastIndexOf("."));
        else return "";
    }

    private static long VolumeNumber(File file){
        long zipFileSize;
        long zips;//分卷压缩后 zip 文件的数目
        if (file.length() < 1024*1024*75){//小于 75M 的，统一分成 5 份
            zipFileSize=file.length()/5;
            zips=5;
        }else {//大于 75M 的，每份 15M
            zipFileSize=1024*1024*15;
            zips=file.length()/zipFileSize;
            if(zips*zipFileSize < file.length()){
                zips++;
            }
        }
        return zips;
    }


}
