package org.pcloud.common.filemaker;

/**
 * 最终报文模板名称接口
 * 
 * @author 徐海洋
 * 
 */
public interface TemplateName {

    /**
     * 返回名称字符串
     * 
     * @param newFile
     *            最终生成的报文IO流对象
     * @return 报文名称
     * @throws Exception
     *             异常对象
     */
    String getName() throws Exception;
}
