package com.tpp.threat_perception_platform.service;


import com.tpp.threat_perception_platform.param.AssetsParam;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.response.ResponseResult;

public interface HostService {


    /**
     * 主机列表数据
     * @param param
     * @return
     */
    public ResponseResult hostList(MyParam param);

    /**
     * 保存主机信息
     * @param host
     * @return
     */
    public int saveHost(Host host);

    /**
     * 删除主机信息
     * @param ids
     * @return
     */
    ResponseResult deleteHost(Integer[] ids);

    /**
     * 编辑主机信息
     * @param host
     * @return
     */
    ResponseResult editHost(Host host);

    /**
     * 更新主机状态
     * @param
     * @return
     */


    /**
     * 根据MAC地址更新主机信息
     * @param host
     * @return
     */
    public int updateHostByMacAddress(Host host);

    /**
     * 资产发现
     * @param param
     * @return
     */
    public ResponseResult assetsDiscovery(AssetsParam param);

    ResponseResult hotfixDiscovery(HotfixParam param);
}
