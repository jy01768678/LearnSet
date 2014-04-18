package com.lorin.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;


public class WhiteUserUtils {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(WhiteUserUtils.class);

    private static final Set<Long> hashSetWhiteUserIds = new HashSet<Long>();
    
    private static boolean inited = false;
    
    static{
        System.out.println("is inited in static block :"+inited);
        init();
        System.out.println("static init");
//        Timer t = Timer.getInstance();
//        t.getScheduler().scheduleAtFixedRate(new ReinitTask(), 3000L, 3000L,
//                TimeUnit.MILLISECONDS);
    }
    
    public static void init(){
        if (inited) {
            System.out.println("already user init...");
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("super user init...");
        }
        try {
            InputStreamReader reader = new InputStreamReader(
                    WhiteUserUtils.class.getResourceAsStream("white_user.txt"), "UTF-8");
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                hashSetWhiteUserIds.add(Long.valueOf(line));
                if (logger.isDebugEnabled()) {
                    logger.debug("super user:" + line);
                }
            }
            reader.close();
            inited = true;
            return;
        } catch (Exception e) {
            logger.error("Read super user id error, error:" + e);
        }
    }
    
    public static boolean isSuperUser(long userId) {
        if (hashSetWhiteUserIds.contains(userId)) {
            return true;
        }
        return false;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("before:"+inited);
        System.out.println(hashSetWhiteUserIds.isEmpty());
        System.out.println("after:"+inited);
//        System.out.println(isSuperUser(383495187));
    }

}
class ReinitTask implements Runnable {

    @Override
    public void run() {
        WhiteUserUtils.init();
    }
}
