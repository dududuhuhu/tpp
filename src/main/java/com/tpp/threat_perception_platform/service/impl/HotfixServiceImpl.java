package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.HotfixMapper;
import com.tpp.threat_perception_platform.dao.WinCveDbMapper;
import com.tpp.threat_perception_platform.param.HotfixParam;
import com.tpp.threat_perception_platform.pojo.Hotfix;
import com.tpp.threat_perception_platform.pojo.WinCveDb;
import com.tpp.threat_perception_platform.response.DangerousHotfix;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HotfixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    public ResponseResult saveHotfix(Hotfix hotfix, Timestamp now) {
        // 先查询是否已存在（根据 mac + hotfixId 判断是否重复）
        Hotfix db_app = hotfixMapper.selectByMacAndHotfixId(hotfix.getMac(), hotfix.getHotfixId());
        if (db_app != null) {
            db_app.setUpdatedTime(now);
            hotfixMapper.updateByPrimaryKey(db_app);
            return new ResponseResult<>(1003, "该补丁记录已存在！");
        }
        // 添加
        hotfix.setUpdatedTime(now);
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

//    @Override
//    public ResponseResult<List<DangerousHotfix>> getDangerousPatches(String mac) {
//        // 默认返回前100条
//        return getDangerousPatch(1, 100);
//    }

    @Override
    public ResponseResult<List<DangerousHotfix>> getDangerousPatch(HotfixParam param) {
        int page = param.getPage() != null ? param.getPage() : 1;
        int limit = param.getLimit() != null ? param.getLimit() : 10;

        PageHelper.startPage(page, limit); // 启动分页
        System.out.println("hotfixparam:"+param);

        // === 查询补丁列表 ===
        List<Hotfix> allHotfixes;
        if (param.getMacAddress() != null && !param.getMacAddress().isEmpty()) {
            allHotfixes = hotfixMapper.findByMac(param.getMacAddress());
        } else {
            allHotfixes = hotfixMapper.findAll();
        }
        System.out.println("allHotfixes:"+allHotfixes);

        List<DangerousHotfix> result = new ArrayList<>();
        for (Hotfix hotfix : allHotfixes) {
            String hotfixId = hotfix.getHotfixId();
            List<WinCveDb> matchedCves = winCveDbMapper.findByHotfixId(hotfixId);
            for (WinCveDb cve : matchedCves) {
                DangerousHotfix vo = new DangerousHotfix();
                vo.setMacAddress(hotfix.getMac());
                vo.setHotfixId(hotfixId);
                vo.setCve(cve.getCve());
                vo.setScore(cve.getScore());
                result.add(vo);
            }
        }

        // PageInfo 包装分页信息（注意：分页的是 result）
        PageInfo<DangerousHotfix> pageInfo = new PageInfo<>(result);

        return new ResponseResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

}
