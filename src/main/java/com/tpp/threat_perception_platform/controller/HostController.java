package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.param.ApplicationRiskParam;
import com.tpp.threat_perception_platform.param.AssetsParam;
import com.tpp.threat_perception_platform.param.HotfixParam;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.param.SystemRiskParam;
import com.tpp.threat_perception_platform.param.WeakpasswordParam;
import com.tpp.threat_perception_platform.param.*;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.pojo.WeakpasswordRisk;
import com.tpp.threat_perception_platform.pojo.WinCveDb;
import com.tpp.threat_perception_platform.response.DangerousHotfix;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @Autowired
    private HotfixService hotfixService;
    @Autowired
    private WeakpasswordRiskService weakpasswordRiskService;
    @Autowired
    private VulnerabilityService vulnerabilityService;
    @Autowired
    private ApplicationRiskService applicationRiskService;

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

    @PostMapping("/host/appRiskDiscovery")
    public ResponseResult appRiskDiscovery(@RequestBody ApplicationRiskParam param){
        System.out.println(param);
        return hostService.appRiskDiscovery(param);
    }

    @PostMapping("/host/systemRiskDiscovery")
    public ResponseResult systemRiskDiscovery(@RequestBody SystemRiskParam param){
        System.out.println(param);
        return hostService.systemRiskDiscovery(param);
    }

    @PostMapping("/host/applicationRisk")
    public ResponseResult getApplicationRiskInfo(@RequestBody ApplicationRiskParam param){
        return applicationRiskService.appRiskList(param);
    }

    /**
     * 补丁发现
     */
    @PostMapping("/host/hotfixDiscovery")
    public ResponseResult hotfixDiscovery(@RequestBody HotfixParam param) {
        // 下发指令
        return hostService.hotfixDiscovery(param);
    }

    /**
     * 危险补丁展示
     */
    @PostMapping("/host/dangerousHotfixRisk")
    public ResponseResult<List<DangerousHotfix>> getDangerousPatches(@RequestBody HotfixParam param) {
        System.out.println("param:"+param);
        System.out.println("param.mac:"+param.getMacAddress());
        return hotfixService.getDangerousPatch(param.getMacAddress(), param.getPage(), param.getLimit());
    }

    /**
     * 弱密码发现
     */
    @PostMapping("/host/weakPasswordDiscovery")
    public ResponseResult weakpasswordDiscovery(@RequestBody WeakpasswordParam param) {
        // 下发指令
        return hostService.weakpasswordDiscovery(param);
    }

    /**
     * 弱密码展示
     */
    @PostMapping("/host/weakpasswordRisk")
    public ResponseResult getWeakpassword(@RequestBody WeakpasswordParam param) {
        return weakpasswordRiskService.weakpasswordList(param);
    }

    /**
     * 漏洞发现
     */
    @PostMapping("/host/vulnerabilityDiscovery")
    public ResponseResult vulnerabilityDiscovery(@RequestBody VulnerabilityParam param) {
        // 下发指令
        return hostService.vulnerabilityDiscovery(param);
    }

    /**
     * 漏洞展示
     */
    @PostMapping("/host/vulnerabilityRisk")
    public ResponseResult getVulnerability(@RequestBody VulnerabilityParam param) {
        return vulnerabilityService.vulnerabilityRiskList(param);
    }

}
