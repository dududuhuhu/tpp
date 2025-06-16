package com.tpp.threat_perception_platform.service.impl;

import com.tpp.threat_perception_platform.dao.HotfixMapper;
import com.tpp.threat_perception_platform.dao.WinCveDbMapper;
import com.tpp.threat_perception_platform.pojo.Hotfix;
import com.tpp.threat_perception_platform.pojo.WinCveDb;
import com.tpp.threat_perception_platform.response.DangerousHotfix;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HotfixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class HotfixServiceImpl implements HotfixService {

    @Autowired
    private HotfixMapper hotfixMapper;

    @Autowired
    private WinCveDbMapper winCveDbMapper;

    /**
     * 查询
     */
    /**
     * @Override
    public ResponseResult hotfixList(HotfixParam param) {
        String mac= param.getMacAddress();
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<Hotfix> hotfixList = hotfixMapper.findAll(mac);
        // 构架pageInfo
        PageInfo<Hotfix> pageInfo = new PageInfo<>(hotfixList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
    */

    /**
     * 保存
     */
    @Override
    public ResponseResult saveHotfix(Hotfix hotfix) {
        // 先查询是否已存在（根据 mac + hotfixId 判断是否重复）
        Hotfix db_app = hotfixMapper.selectByMacAndHotfixId(hotfix.getMac(), hotfix.getHotfixId());
        if (db_app != null) {
            return new ResponseResult<>(1003, "该补丁记录已存在！");
        }

        // 添加

        hotfixMapper.insertSelective(hotfix);
        return new ResponseResult<>(0, "添加成功！");
    }

    /**
     * 更新
     */
//    @Override
//    public ResponseResult editApp(AppInfo appInfo) {
//        appInfoMapper.updateByPrimaryKeySelective(appInfo);
//        return new ResponseResult<>(0, "更新成功！");
//    }

    @Override
    public ResponseResult<List<DangerousHotfix>> getDangerousPatches(String mac) {
        // 默认返回前100条
        return getDangerousPatch(mac, 1, 100);
    }

    @Override
    public ResponseResult<List<DangerousHotfix>> getDangerousPatch(String mac, Integer page, Integer limit) {
        List<Hotfix> allHotfixes = hotfixMapper.findAll(mac);
        List<DangerousHotfix> result = new ArrayList<>();

        System.out.println("开始探测危险补丁：" + mac);
        for (Hotfix hotfix : allHotfixes) {
            String hotfixId = hotfix.getHotfixId();
            System.out.println(hotfixId);
            List<WinCveDb> matchedCves = winCveDbMapper.findByHotfixId(hotfixId);

            for (WinCveDb cve : matchedCves) {
                System.out.println(cve);
                DangerousHotfix vo = new DangerousHotfix();
                vo.setMacAddress(hotfix.getMac());
                vo.setHotfixId(hotfixId);
                vo.setCve(cve.getCve());
                vo.setScore(cve.getScore());
                result.add(vo);
            }
        }

        // 手动分页
        int fromIndex = (page - 1) * limit;
        int toIndex = Math.min(fromIndex + limit, result.size());
        List<DangerousHotfix> pageList = (fromIndex >= result.size()) ? new ArrayList<>() : result.subList(fromIndex, toIndex);

        System.out.println("=== 危险补丁分页列表 ===");
        pageList.forEach(System.out::println);

        return new ResponseResult<>((long) result.size(), pageList);
    }

}
