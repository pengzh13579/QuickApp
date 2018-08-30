package cn.pzh.system.web.project.common.utils.fileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by lvpeng on 14-2-13.
 */
public class FileUtil {

    private static final NewNIO nio = new NewNIO();

    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            return false;
        }
        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            if (!file.getParentFile().mkdirs()) {
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {

                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            return true;
        } else {
            return false;
        }
    }


    public static String createTempFile(String prefix, String suffix, String dirName) {
        File tempFile = null;
        if (dirName == null) {
            try {
                //在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                //返回临时文件的路径
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            File dir = new File(dirName);
            //如果临时文件所在目录不存在，首先创建
            if (!dir.exists()) {
                if (!FileUtil.createDir(dirName)) {
                    return null;
                }
            }
            try {
                //在指定目录下创建临时文件
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static boolean delFile(String destFileName) {

        File file = new File(destFileName);
        if (!file.exists()) {
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            return false;
        }
        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            return false;
        }
        //创建目标文件
        try {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeFile(String path, String content) {
        try {
//            FileWriter resultFile = new FileWriter(path);
//            PrintWriter myFile = new PrintWriter(resultFile);
//            myFile.println(content);
//            resultFile.close();
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8");
            osw.write(content);
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // added by shenyouyi 2014-5-19
    // 讲内容写入文件，覆盖模式
    public static boolean writeFileNoAppend(String path, String content) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
            osw.write(content);
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void deleFile(String filePathAndName) {
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myDelFile = new File(filePath);
            myDelFile.delete();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path String 文件夹路径 如 c:/fqf
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
            }
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        nio.copeOrMoveFile(oldPath, newPath, OptionFile_TYPE.COPE);
        /*try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();

        }*/

    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    /**
     * 复制整个文件夹内容
     *
     * @param oldPath  String 原文件路径 如：c:/fqf
     * @param newPath  String 复制后路径 如：f:/fqf/ff
     * @param tierSize int 路径深度
     */
    public static void copyFolder(String oldPath, String newPath, int tierSize) {
        File oldDir = new File(oldPath);
        File newDir = new File(newPath);
        if (oldDir.exists() && newDir.exists())
            nio.copeOrMoveDirectory(oldPath, newPath, tierSize, OptionFile_TYPE.COPE);
    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath String 如：c:/fqf.txt
     * @param newPath String 如：d:/fqf.txt
     */
    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);

    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath String 如：c:/fqf.txt
     * @param newPath String 如：d:/fqf.txt
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);

    }

    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    public static List<String> readFileByLines(List<String> fileName) {
//        String[] files= fileName.split(";");
        List<String> filelist = new ArrayList<String>();
        for (int k = 0; k < fileName.size(); k++) {
            File file = new File(fileName.get(k));
            Reader reader = null;
            StringBuffer filestring = new StringBuffer();
            try {
//                System.out.println("以字符为单位读取文件内容，一次读多个字节：");
                // 一次读多个字符
                char[] tempchars = new char[30];
                int charread = 0;
                reader = new InputStreamReader(new FileInputStream(fileName.get(k)), "UTF-8");
                // 读入多个字符到字符数组中，charread为一次读取字符数
                while ((charread = reader.read(tempchars)) != -1) {
                    // 同样屏蔽掉\r不显示
                    if ((charread == tempchars.length)
                            && (tempchars[tempchars.length - 1] != '\r')) {
                        System.out.print(tempchars);
                    } else {
                        for (int i = 0; i < charread; i++) {
                            if (tempchars[i] == '\r') {
                                continue;
                            } else {
//                                System.out.print(tempchars[i]);
                            }
                        }
                    }
                    filestring.append(tempchars);
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
            filelist.add(file.getName());
            filelist.add(filestring.toString());
        }

        return filelist;

    }

    //读文件，返回字符串
    public static String ReadFile(InputStreamReader isr) {
//        File file = new File(path);
        BufferedReader reader = null;
        String laststr = "";
        try {
            reader = new BufferedReader(isr);
            String tempString = null;
            int line = 1;
            //一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                laststr = laststr + tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return laststr;
    }

    public static List<String> readFilebyLine(List<String> paths) {
        List<String> filelist = new ArrayList<String>();
        for (int k = 0; k < paths.size(); k++) {
            File file = new File(paths.get(k));
            if (file.exists()) {
                try {
                    FileInputStream fi = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fi, "UTF-8");
                    BufferedReader bfin = new BufferedReader(isr);
                    String rLine = "";
                    StringBuffer buffer = new StringBuffer();
                    while ((rLine = bfin.readLine()) != null) {
                        StringTokenizer st = new StringTokenizer(rLine, "");
                        for (int i = 0; st.hasMoreElements(); i++) {
                            String word = (String) st.nextElement();
                            buffer.append(word);
                        }
                        buffer.append("\n");
                    }
                    filelist.add(file.getName());
                    filelist.add(buffer.toString());

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        return filelist;


    }
}

