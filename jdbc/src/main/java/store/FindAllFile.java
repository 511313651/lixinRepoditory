package src.main.java.store;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FindAllFile {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<File> allFiles = getAllFiles("C:\\Users\\李信\\Desktop", (file) -> file.getName().endsWith(".pdf"));
        allFiles.stream().forEach(file -> System.out.println(file.getAbsolutePath()));
    }

    //通过递归和过滤器查找文件夹下的所有文件
    public static ArrayList<File> getAllFiles(String dir, FileFilter filter) throws FileNotFoundException {
        File file = new File(dir);
        return getAllFiles(file,filter);
    }

    //
    public static ArrayList<File> getAllFiles(File file, FileFilter filter) throws FileNotFoundException {
        //包装用户传进来的过滤器,使其不过滤文件夹
        FileFilter wrapFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(pathname.isDirectory())return true;
                return filter.accept(pathname);
            }
        };
        if((!file.exists())||file.isFile())
            throw new FileNotFoundException("找不到指定目录");
        ArrayList<File> allFiles = new ArrayList<>();
        File[] fileList = file.listFiles(wrapFilter);
        for (File file1 : fileList) {
            if(file1.isDirectory()){
                allFiles.addAll(getAllFiles(file1.getAbsolutePath(),wrapFilter));
                continue;//如果是一个目录,就不执行后面添加到集合中的代码
            }
            allFiles.add(file1);
        }
        return allFiles;
    }
}
