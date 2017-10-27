/**
 * @Title: FileUtil.java
 * @Prject: Ktv
 * @Package: com.sz.ead.app.ktv.utils
 * @Description: FileUtil
 * @author: zhaoqy
 * @date: 2015-8-24 下午5:50:27
 * @version: V1.0
 */

package com.sz.ead.app.ktv.utils;

import java.io.File;

public class FileUtil 
{
	 /**
     * 创建文件夹
     * @param dirName
     */
	public static void createDir(String dirName) 
    {
    	if(!fileIsExists(dirName))
    	{
    		File dir = new File(dirName);
    		/*boolean result = */dir.mkdir();
    		//Logcat.i(FlagConstant.TAG, " result: " + result);
    	}
	}
    
    /**
	 * 文件是否存在
	 * @param pathAndfileName
	 * @return
	 */
	public static boolean fileIsExists(String pathAndfileName)
    {
        try
        {
            File f = new File(pathAndfileName);
            if (!f.exists())
            {
                return false;
            }
        }
        catch (Exception e) 
        {
        	return false;
        }
        return true;
    }
}
