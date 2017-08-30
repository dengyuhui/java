package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.ISevenZipInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;

public class INTO {

	public static void main(String[] args) {
		Date dateStart = new Date();
		final String sourceZipFile = "C:/Users/xiaodeng/Desktop/图片111.rar";
		final String destinationDir = "C:/Users/xiaodeng/Desktop/tu";
		final String password = "111111";
		unzipDirWithPassword(sourceZipFile,destinationDir,password);
		Date dateEnd = new Date();
		System.out.println("开始时间："+(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 SSS")).format(dateStart));
		System.out.println("结束时间："+(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 SSS")).format(dateEnd));
		System.out.println("消耗时间："+(dateEnd.getTime() - dateStart.getTime()));
	}
	
	public static void unzipDirWithPassword(final String sourceZipFile, final String destinationDir, final String password){  
           RandomAccessFile randomAccessFile = null;  
           ISevenZipInArchive inArchive = null;  
           try{  
              randomAccessFile = new RandomAccessFile(sourceZipFile, "r");  
              inArchive = SevenZip.openInArchive(null, // autodetect archive type  
              new RandomAccessFileInStream(randomAccessFile));  
               
              // Getting simple interface of the archive inArchive  
              ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();  
              /*System.out.println(simpleInArchive.getArchiveItems().length);
              return;*/
              for (final ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()){  
                  final int[] hash = new int[] { 0 };  
                  if (!item.isFolder()){  
                      ExtractOperationResult result;  
                      result = item.extractSlow(new ISequentialOutStream(){  
                            public int write(final byte[] data) throws SevenZipException{  
                                  try{  
                                        if(item.getPath().indexOf(File.separator)>0){  
                                            String path = destinationDir+File.separator+item.getPath(). substring(0,item.getPath().lastIndexOf(File.separator));  
                                            File folderExisting = new File(path);  
                                            if (!folderExisting.exists())  
                                                 new File(path).mkdirs();  
                                        }  
                                        if(!new File(destinationDir + File.separator+item.getPath()).exists()){  
                                            new File(destinationDir).createNewFile();  
                                        }  
                                        OutputStream out = new FileOutputStream(destinationDir+ File.separator+item.getPath());  
                                        out.write(data);  
                                        out.close();  
                                   }catch( Exception e ){  
                                        e.printStackTrace();  
                                   }  
                                   hash[0] |= Arrays.hashCode(data);
                                   return data.length; // Return amount of proceed data  
                           }  
                       },password); /// password.  
                       if (result == ExtractOperationResult.OK){  
                           System.out.println(String.format("%9X | %s",hash[0], item.getPath()));  
                       }else{  
                           System.err.println("Error extracting item: " + result);  
                       }  
                  }  
              }  
           } catch (Exception e){  
                e.printStackTrace();  
           } finally {  
                if (inArchive != null){  
                    try {  
                       inArchive.close();  
                    } catch (SevenZipException e){  
                       System.err.println("Error closing archive: " + e);  
                       e.printStackTrace();  
                    }  
                }  
                if (randomAccessFile != null) {  
                     try{  
                         randomAccessFile.close();  
                     } catch (IOException e){  
                         System.err.println("Error closing file: " + e);  
                         e.printStackTrace();  
                     }  
                }  
           }  
    }  
	
}
