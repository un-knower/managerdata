package com.ufc.user.utils.file;

import java.util.List;
import java.util.Map;

public class WordMain {
/** 
* 1.对于word,使用XWPFDocument操作07以上的doc或者docx都没有问题,并且必须是07或者以上的电脑上生成的word 
* 如果是WordExtractor或者HWPFDocument只能操作03以下的word,并且只能是03以下电脑生成的word 
*  
* @param args 
*/

    public static void main(String[] args) {
        String path = "d:\\tmp\\poi\\";
//        String fileName = "poi.docx";
        String fileName = "20170720北语研讨会.doc";
        String filePath = path + fileName;
        /*
        //创建word  
        WordUtils1.createWord(path,fileName);
        //写入数据  
        String data = "本文是以poi3.9读写2010word、2010excel、2010ppt,记录学习的脚步相应的功能在代码都有注释,就不解释了 详情可以参看poi3.9的文档主测试函数 TestMain.java";
        WordUtils1.writeDataDocx(filePath,data,true,12);
        //WordUtils.writeDataDoc(filePath,data); 
        */
        
        //读取数据  
        //String contentWord=WordUtils.readDataDoc(filePath); 
        try {
        List<Map<String,Object>> contentWord=WordUtils.readData(filePath);
        System.out.println("word的内容为:\n"+contentWord);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        //System.out.println();
    }

}