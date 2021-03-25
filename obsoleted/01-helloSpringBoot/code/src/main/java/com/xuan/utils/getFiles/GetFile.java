package com.xuan.utils.getFiles;

import java.io.File;
import java.util.Stack;

/**
 * @author XUAN
 * @date 2021/3/4 - 19:56
 * @references
 * @purpose
 * @errors
 */
public class GetFile {
    private File root;// 传入的目录为根目录
    /**
     * 为什么要用栈存储？
     * 为了防止文件夹因重命名而找不到路径
     */
    private Stack<File> folder = new Stack<File>();// 根目录下的所有文件夹
    private Stack<File> files = new Stack<File>(); // 根目录下的所有文件

    public GetFile(File dir) throws Exception {
        setRoot(dir);// 在一开始就设置好根目录
    }

    public void setRoot(File root) throws Exception {
        if (null==root){
            throw new NullPointerException();
        }
        if (!root.exists()){
            throw new Exception("文件夹不存在");
        }
        this.root = root;
    }

    private void recursiveTraversalFolder(SelectMode selectMode, File currentDir) {
        // 获取当前文件夹下所有 文件/文件夹
        File[] fileArr = currentDir.listFiles();
        if (null == fileArr || fileArr.length == 0) {
//				System.out.println("["+temp.getAbsolutePath()+"]"+" 为空!");
            return;
        } else {
            // 在操作当前目录下所有 文件/文件夹
            for (File file : fileArr) {
                if (file.isDirectory()) {
                    if (selectMode.selected(file)) {
                        folder.push(file);// 符合条件的文件夹入栈
                        recursiveTraversalFolder(selectMode, file);// 是文件夹，继续往下递归
                    }
                } else {
                    if (selectMode.selected(file)) {
                        files.push(file);// 符合条件的文件入栈
                    }
                }
            }
        }
    }

    /**
     * 获取根目录下的所有文件夹
     * @return
     */
    public Stack<File> getFolders(SelectMode selectMode) {
        while (!folder.empty()) {
            folder.pop();
        }
        recursiveTraversalFolder(selectMode,root);// 递归遍历根目录下的所有文件，获取文件夹
        return folder;
    }

    /**
     * 获取根目录下的所有文件（不包含文件夹）
     * @return
     */
    public Stack<File> getFiles(SelectMode selectMode) {
        while (!files.empty()) {
            files.pop();
        }
        recursiveTraversalFolder(selectMode,root);// 递归遍历根目录下的所有文件，获取文件
        return files;
    }

    /**
     * 获取根目录下的所有文件夹,全选
     * @return
     */
    public Stack<File> getFolders() {
        while (!folder.empty()) {
            folder.pop();
        }
        recursiveTraversalFolder((file)->true,root);// 递归遍历根目录下的所有文件，获取文件夹
        return folder;
    }

    /**
     * 获取根目录下的所有文件（不包含文件夹）,全选
     * @return
     */
    public Stack<File> getFiles() {
        while (!files.empty()) {
            files.pop();
        }
        recursiveTraversalFolder((file)->true,root);// 递归遍历根目录下的所有文件，获取文件
        return files;
    }
}
