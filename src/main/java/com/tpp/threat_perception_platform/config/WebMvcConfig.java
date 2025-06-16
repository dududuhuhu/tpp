package com.tpp.threat_perception_platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 主页
        registry.addViewController("/page/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        // 控制台
        registry.addViewController("/page/console").setViewName("console");
        // 登录页面
        registry.addViewController("/page/login").setViewName("login");
        // 用户相关页面
        registry.addViewController("/page/user/list").setViewName("user/list");
        registry.addViewController("/page/user/edit").setViewName("user/edit");
        registry.addViewController("/page/user/add").setViewName("user/add");

        // 角色相关页面
        registry.addViewController("/page/role/list").setViewName("role/list");
        registry.addViewController("/page/role/edit").setViewName("role/edit");
        registry.addViewController("/page/role/add").setViewName("role/add");

        // 主机相关页面
        registry.addViewController("/page/host/list").setViewName("host/list");
        registry.addViewController("/page/host/assets").setViewName("host/assets");
        registry.addViewController("/page/host/hotfix").setViewName("host/hotfix");
        registry.addViewController("/page/host/vulnerability").setViewName("host/vulnerability");
        registry.addViewController("/page/host/weakPassword").setViewName("host/weakPassword");
        registry.addViewController("/page/host/appRisk").setViewName("host/appRisk");
        registry.addViewController("/page/host/systemRisk").setViewName("host/systemRisk");



        registry.addViewController("/page/host/assetsInfo").setViewName("host/assetsInfo");
        registry.addViewController("/page/host/accountInfo").setViewName("host/accountInfo");
        registry.addViewController("/page/host/appInfo").setViewName("host/appInfo");
        registry.addViewController("/page/host/processInfo").setViewName("host/processInfo");
        registry.addViewController("/page/host/serviceInfo").setViewName("host/serviceInfo");

        registry.addViewController("/page/host/assetsDiscovery").setViewName("host/assetsDiscovery");
        registry.addViewController("/page/host/hotfixDiscovery").setViewName("host/hotfixDiscovery");
        registry.addViewController("/page/host/vulnerabilityDiscovery").setViewName("host/vulnerabilityDiscovery");
        registry.addViewController("/page/host/weakPasswordDiscovery").setViewName("host/weakPasswordDiscovery");
        registry.addViewController("/page/host/appRiskDiscovery").setViewName("host/appRiskDiscovery");
        registry.addViewController("/page/host/systemRiskDiscovery").setViewName("host/systemRiskDiscovery");

        // 风险发现相关页面
        registry.addViewController("/page/risk/hotfixList").setViewName("risk/hotfixList");
        registry.addViewController("/page/risk/applicationList").setViewName("risk/applicationList");
        registry.addViewController("/page/risk/systemList").setViewName("risk/systemList");
        registry.addViewController("/page/risk/vulnerabilityList").setViewName("risk/vulnerabilityList");
        registry.addViewController("/page/risk/weakpasswordList").setViewName("risk/weakpasswordList");

        registry.addViewController("/page/risk/applicationRiskRulesAdd").setViewName("risk/applicationRiskRulesAdd");
        registry.addViewController("/page/risk/systemRiskRulesAdd").setViewName("risk/systemRiskRulesAdd");
        registry.addViewController("/page/risk/hotfixAdd").setViewName("risk/hotfixAdd");
        registry.addViewController("/page/risk/weakpasswordsAdd").setViewName("risk/weakpasswordsAdd");
        registry.addViewController("/page/risk/vulnerabilityRulesAdd").setViewName("risk/vulnerabilityRulesAdd");

        registry.addViewController("/page/risk/hotfixInfo").setViewName("risk/hotfixInfo");
        registry.addViewController("/page/risk/applicationInfo").setViewName("risk/applicationInfo");
        registry.addViewController("/page/risk/systemInfo").setViewName("risk/systemInfo");
        registry.addViewController("/page/risk/weakpasswordsInfo").setViewName("risk/weakpasswordsInfo");
        registry.addViewController("/page/risk/vulnerabilityInfo").setViewName("risk/vulnerabilityInfo");






    }
}

