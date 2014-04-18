package com.lorin.compress;

/**
 * 数据压缩接口
 * @author Administrator
 */
public interface DataCompress {

    /**
     * 压缩
     * @param data
     * @return
     * @throws Exception
     */
    byte[] compress(byte[] data) throws Exception;
    
    /**
     * 压缩
     * @param data
     * @return
     * @throws Exception
     */
    byte[] decompress(byte[] data) throws Exception;
}
