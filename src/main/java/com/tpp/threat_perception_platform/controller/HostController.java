package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.param.AssetsParam;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class HostController {

    @Autowired
    private HostService hostService;
    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private ProcessInfoService processInfoService;
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private ServiceInfoService serviceInfoService;

    @PostMapping("/host/list")
    public ResponseResult hostList(MyParam param){
        return hostService.hostList(param);
    }

    @PostMapping("/host/delete")
    public ResponseResult hostDelete(@RequestParam("ids[]") Integer[] ids){
        return hostService.deleteHost(ids);
    }

    @PostMapping("/host/save")
    public int hostSave(@RequestBody Host host){
        return hostService.saveHost(host);
    }
    @PostMapping("/host/edit")
    public ResponseResult hostEdit(@RequestBody Host host){
        return hostService.editHost(host);
    }

    // 获取账号信息
    @PostMapping("/host/accountInfo")
    public ResponseResult accountInfo(@RequestBody MyParam param) {
        //        输出检查
        ResponseResult result = accountInfoService.accountList(param);
//        输出result
//        System.out.println(result);
        return result;
    }

    // 获取服务信息
    @PostMapping("/host/serviceInfo")
    public ResponseResult serviceInfo(@RequestBody MyParam param) {
        System.out.println(param);
        return serviceInfoService.retrieveAssetsService(param);
    }

    // 获取进程信息
    @PostMapping("/host/processInfo")
    public ResponseResult processInfo(@RequestBody MyParam param) {
        // 打印接收到的参数
        System.out.println(param);
        System.out.println("macAddress: " + param.getMacAddress());
        System.out.println("page: " + param.getPage());
        System.out.println("limit: " + param.getLimit());
        return processInfoService.processInfoList(param);
    }

    // 获取应用信息
    @PostMapping("/host/appInfo")
    public ResponseResult appInfo(@RequestBody MyParam param ) {
        System.out.println(param);
        // 打印接收到的参数
        System.out.println("macAddress: " + param.getMacAddress());
        System.out.println("page: " + param.getPage());
        System.out.println("limit: " + param.getLimit());

        return appInfoService.appList(param);
    }

    /**
     * 资产探测
     * @param param
     * @return
     */
    @PostMapping("/host/assetsDiscovery")
    public ResponseResult assetsDiscovery(@RequestBody AssetsParam param){
        return hostService.assetsDiscovery(param);
    }



}
