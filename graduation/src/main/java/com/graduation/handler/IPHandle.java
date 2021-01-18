package com.graduation.handler;

import com.graduation.controller.UserAddressController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class IPHandle {
    static Logger log = LoggerFactory.getLogger(UserAddressController.class);
    public static String getIp() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            log.info("ip:"+ip.getHostAddress());
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("获取失败");
        }
        return "";
    }
}
