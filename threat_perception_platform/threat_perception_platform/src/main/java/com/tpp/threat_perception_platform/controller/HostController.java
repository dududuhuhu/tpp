package com.tpp.threat_perception_platform.controller;


import com.tpp.threat_perception_platform.param.AssetParam;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HostService;
import com.tpp.threat_perception_platform.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HostController {
    @Autowired
    private HostService hostService;
    @Autowired
    private ServiceService serviceService;

    @PostMapping("/host/list")
    public ResponseResult hostList(MyParam param) {
        return hostService.listHost(param);
    }

    @PostMapping("/host/delete")
    public ResponseResult hostDelete(@RequestParam("ids[]") Long[] ids) {
        return hostService.deleteHost(ids);
    }

    @PostMapping("/host/assets")
    public ResponseResult hostAssetsDiscovery(@RequestBody AssetParam param) {
        return hostService.assetsDiscovery(param);
    }

    @PostMapping("/host/assets/service/list")
    public ResponseResult retriveAssetsService(@RequestBody MyParam param) {
        System.out.println(param);
        return serviceService.retrieveAssetsService(param);
    }
}
