package com.tpp.threat_perception_platform.utils;
/**
 * ping 检查主机是否在线
 * 1 在线
 */
public class PingUtil {
    public static String ping(String ip) {
        String pingCommand = "ping -n 1 " + ip;
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec(pingCommand);
            process.waitFor();
            process.destroy();
            result = process.exitValue() == 0 ? "success" : "fail";
            System.out.println("Ping " + ip + ": " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
