//package com.sp.infra.svc.gov.sdk.util;
//
//import com.sp.infra.svc.gov.sdk.sac.client.GenerateCodeUtil;
//import com.sp.infra.svc.gov.sdk.sac.command.SeqConstants;
//import org.junit.Test;
//
//import java.util.List;
//
//public class GenerateCodeUtilTest {
//
//    @Test
//    public void getCode() {
//
//        String code = GenerateCodeUtil.getCode();
//        System.out.println("唯一编码:" + code);
//    }
//
//    @Test
//    public void listCode() {
//        List<String> list = GenerateCodeUtil.listCode(10);
//        for (String s : list) {
//            System.out.println("唯一编码:" + s);
//        }
//    }
//
//    @Test
//    public void getCodeByRule() {
//        String codeByRule = GenerateCodeUtil.getCodeByRule("s", "e", SeqConstants.TIME_YEAR, "-");
//        System.out.println("唯一编码:" + codeByRule);
//    }
//
//    @Test
//    public void listCodeByRule() {
//        List<String> list = GenerateCodeUtil.listCodeByRule("s", "e", SeqConstants.TIME_YEAR, "-", 10);
//        for (String s : list) {
//            System.out.println("唯一编码:" + s);
//        }
//    }
//
//    @Test
//    public void countByOneMinute() {
//        long start = System.currentTimeMillis();
//        int count = 0;
//        for (int i = 0; System.currentTimeMillis()-start<1000; i++,count=i) {
//            GenerateCodeUtil.getCode();
//            count++;
//        }
//
//        long end = System.currentTimeMillis()-start;
//        System.out.println(end);
//        System.out.println(count);
//    }
//
//    /**
//     * 判断100秒内是否有重复的数据
//     * 本地测试：
//     * 时间差:101337
//     * 重复的编码数:0
//     * 生成唯一编码总数:26741471
//     */
//    /*@Test
//    public void validateRepeat() {
//        Set<String> codeSet = new HashSet<>(100000000);
//        long start = System.currentTimeMillis();
//        int count = 0;
//        int failCount = 0 ;
//        for (int i = 0; System.currentTimeMillis()-start<100000; i++,count=i) {
//            String code = GenerateCodeUtil.getCode();
//            if (!codeSet.add(code)) {
//                failCount ++;
//            }
//            count++;
//        }
//
//        long end = System.currentTimeMillis()-start;
//        System.out.println("时间差:" + end);
//        System.out.println("重复的编码数:" + failCount);
//        System.out.println("生成唯一编码总数:" + count);
//    }*/
//
//
//}
