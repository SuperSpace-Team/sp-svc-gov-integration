package com.yh.infra.svc.gov.sdk.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPUtils  {
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8"; 

    private GZIPUtils() {}
    
    public static byte[] compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
        	GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.finish();
            gzip.close();
            return out.toByteArray();
        } catch ( Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static byte[] compress(String str) throws IOException {  
        return compress(str, GZIP_ENCODE_UTF_8);  
    }
    
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String uncompressToString(byte[] bytes, String encoding) {  
        if (bytes == null || bytes.length == 0) {  
            return null;  
        }  
          
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);  
            GZIPInputStream ungzip = new GZIPInputStream(in);  
            byte[] buffer = new byte[1024];  
            int n;  
            while ((n = ungzip.read(buffer)) >= 0) {  
                out.write(buffer, 0, n);  
            }  
            return out.toString(encoding);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String uncompressToString(byte[] bytes) {  
        return uncompressToString(bytes, GZIP_ENCODE_UTF_8);  
    } 
 }