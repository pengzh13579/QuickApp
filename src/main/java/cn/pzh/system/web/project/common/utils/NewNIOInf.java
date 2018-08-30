package cn.pzh.system.web.project.common.utils;

/**
 * @since 14-2-14
 */
import java.io.File;
import java.io.IOException;
import java.util.List;


public interface NewNIOInf {

    /**
     * 拷贝或者移动文件
     *
     * @param src
     * @param tar
     * @return
     */
    public boolean copeOrMoveFile(String src, String tar, OptionFile_TYPE type);

    /**
     * 拷贝或者移动文件夹
     *
     * @param src
     * @param tar
     * @param tierSize 层次，拷贝完成后的路径0只是当前文件夹，+1就是加一级父类文件夹（但不拷贝父类内容）
     * @param type
     */
    public void copeOrMoveDirectory(String src, String tar, int tierSize, OptionFile_TYPE type);

    /**
     * 根据文件类型查找相关文件集合，多种类型时用逗号隔开
     *
     * @param dir         目录
     * @param keys        文件类型
     * @param isMatchCase 是否区分大小写
     * @return
     * @throws IOException
     */
    public List<String> findFilesByType(String dir, String[] keys, boolean isMatchCase) throws IOException;


    /**
     * 查找所有该名称的文件
     *
     * @param dir
     * @param keys
     * @param isMatchCase
     * @return
     * @throws IOException
     */
    public List<String> findFilesByName(String dir, String[] keys, boolean isMatchCase) throws IOException;

    /**
     * 查找文件名包含指定字符串的相关文件集合，多种名称时用逗号隔开
     *
     * @param dir         目录
     * @param keys        文件名称
     * @param isMatchCase 是否区分大小写
     * @return
     * @throws IOException
     */
    public List<String> findFilesByLikeName(String dir, String[] keys, boolean isMatchCase) throws IOException;

    /**
     * 判断两个文件是否相同
     *
     * @param f1
     * @param f2
     * @return
     */
    public boolean isSameFile(File f1, File f2);

    /**
     * 更改文件名
     *
     * @param file
     * @param newName
     * @return
     */
    public boolean reNameFile(File file, String newName);

}
