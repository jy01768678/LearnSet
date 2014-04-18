package com.lorin.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class GZipDataCompress implements DataCompress {

    @Override
    public byte[] compress(byte[] data) throws Exception {
        GZIPOutputStream gis = null;
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream(data.length / 2);
            gis = new GZIPOutputStream(baos);
            gis.write(data);
            gis.finish();
            gis.flush();
            return baos.toByteArray();
        }finally{
            if(gis != null){
                gis.close();
                gis = null;
            }
        }
    }

    @Override
    public byte[] decompress(byte[] data) throws Exception {
        GZIPInputStream gis = null;
        ByteArrayOutputStream baos = null;
        try{
            gis = new GZIPInputStream(new ByteArrayInputStream(data));
            byte[] buf = new byte[data.length * 2];
            baos = new ByteArrayOutputStream(data.length * 2);
            int i = 0;
            while((i = gis.read(buf)) != -1){
                baos.write(buf, 0, 1);
            }
            return baos.toByteArray();
        }finally{
            if(baos != null){
                baos.close();
                baos = null;
            }
            if(gis != null){
                gis.close();
                gis = null;
            }
        }
    }

}
