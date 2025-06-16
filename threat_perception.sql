/*
 Navicat Premium Data Transfer

 Source Server         : Mysql80
 Source Server Type    : MySQL
 Source Server Version : 80042 (8.0.42)
 Source Host           : localhost:3306
 Source Schema         : threat_perception

 Target Server Type    : MySQL
 Target Server Version : 80042 (8.0.42)
 File Encoding         : 65001

 Date: 16/06/2025 23:24:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account_info
-- ----------------------------
DROP TABLE IF EXISTS `account_info`;
CREATE TABLE `account_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mac` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `full_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `sid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `sid_type` int NULL DEFAULT NULL,
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `disabled` tinyint(1) NULL DEFAULT NULL,
  `lockout` tinyint(1) NULL DEFAULT NULL,
  `password_changeable` tinyint(1) NULL DEFAULT NULL,
  `password_expires` tinyint(1) NULL DEFAULT NULL,
  `password_required` tinyint(1) NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_harmful` tinyint(1) NULL DEFAULT NULL,
  `harmful_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account_info
-- ----------------------------
INSERT INTO `account_info` VALUES (1, '08:8E:90:C7:79:13', 'Administrator', '', 'S-1-5-21-3011837012-749493050-3588558054-500', 1, 'Degraded', 1, 0, 1, 0, 1, '2025-06-14 20:21:48', '2025-06-14 20:21:48', 0, NULL);
INSERT INTO `account_info` VALUES (2, '08:8E:90:C7:79:13', 'dawn', '', 'S-1-5-21-3011837012-749493050-3588558054-1001', 1, 'OK', 0, 0, 1, 0, 0, '2025-06-14 20:21:48', '2025-06-14 20:21:48', 0, NULL);
INSERT INTO `account_info` VALUES (3, '08:8E:90:C7:79:13', 'DefaultAccount', '', 'S-1-5-21-3011837012-749493050-3588558054-503', 1, 'Degraded', 1, 0, 1, 0, 0, '2025-06-14 20:21:48', '2025-06-14 20:21:48', 0, NULL);
INSERT INTO `account_info` VALUES (4, '08:8E:90:C7:79:13', 'Guest', '', 'S-1-5-21-3011837012-749493050-3588558054-501', 1, 'Degraded', 1, 0, 0, 0, 0, '2025-06-14 20:21:48', '2025-06-14 20:21:48', 1, 'name');
INSERT INTO `account_info` VALUES (5, '08:8E:90:C7:79:13', 'WDAGUtilityAccount', '', 'S-1-5-21-3011837012-749493050-3588558054-504', 1, 'Degraded', 1, 0, 1, 1, 1, '2025-06-14 20:21:48', '2025-06-14 20:21:48', 0, NULL);
INSERT INTO `account_info` VALUES (6, '08:8E:90:C7:79:13', 'Administrator', '', 'S-1-5-21-3011837012-749493050-3588558054-500', 1, 'Degraded', 1, 0, 1, 0, 1, '2025-06-14 20:37:55', '2025-06-14 20:37:55', 0, NULL);
INSERT INTO `account_info` VALUES (7, '08:8E:90:C7:79:13', 'dawn', '', 'S-1-5-21-3011837012-749493050-3588558054-1001', 1, 'OK', 0, 0, 1, 0, 0, '2025-06-14 20:37:55', '2025-06-14 20:37:55', 0, NULL);
INSERT INTO `account_info` VALUES (8, '08:8E:90:C7:79:13', 'DefaultAccount', '', 'S-1-5-21-3011837012-749493050-3588558054-503', 1, 'Degraded', 1, 0, 1, 0, 0, '2025-06-14 20:37:55', '2025-06-14 20:37:55', 0, NULL);
INSERT INTO `account_info` VALUES (9, '08:8E:90:C7:79:13', 'Guest', '', 'S-1-5-21-3011837012-749493050-3588558054-501', 1, 'Degraded', 1, 0, 0, 0, 0, '2025-06-14 20:37:55', '2025-06-14 20:37:55', 1, 'name');
INSERT INTO `account_info` VALUES (10, '08:8E:90:C7:79:13', 'WDAGUtilityAccount', '', 'S-1-5-21-3011837012-749493050-3588558054-504', 1, 'Degraded', 1, 0, 1, 1, 1, '2025-06-14 20:37:55', '2025-06-14 20:37:55', 0, NULL);

-- ----------------------------
-- Table structure for app_info
-- ----------------------------
DROP TABLE IF EXISTS `app_info`;
CREATE TABLE `app_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `mac` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `display_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `install_location` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `uninstall_string` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `collect_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_mac_display_name`(`mac` ASC, `display_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 110 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of app_info
-- ----------------------------
INSERT INTO `app_info` VALUES (1, '08:8E:90:C7:79:13', 'Git', 'C:\\Program Files\\Git\\', '\"C:\\Program Files\\Git\\unins000.exe\"', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (2, '08:8E:90:C7:79:13', 'GTK3-Runtime Win64', 'C:\\Program Files\\GTK3-Runtime Win64', 'C:\\Program Files\\GTK3-Runtime Win64\\gtk3_runtime_uninst.exe', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (3, '08:8E:90:C7:79:13', 'Microsoft Office 家庭和学生版 2021 - zh-cn', 'C:\\Program Files\\Microsoft Office', '\"C:\\Program Files\\Common Files\\Microsoft Shared\\ClickToRun\\OfficeClickToRun.exe\" scenario=install scenariosubtype=ARP sourcetype=None productstoremove=HomeStudent2021Retail.16_zh-cn_x-none culture=zh-cn version.16=16.0', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (4, '08:8E:90:C7:79:13', 'ModelSim - Intel FPGA Starter Edition 20.1.0.711', 'C:\\intelFPGA_lite\\20.1', '\"C:\\intelFPGA_lite\\20.1\\uninstall\\modelsim_ase-20.1.0.711-windows-uninstall.exe\"', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (5, '08:8E:90:C7:79:13', '微软OfficePLUS', 'C:\\Program Files\\Microsoft OfficePLUS', '\"C:\\Program Files\\Microsoft OfficePLUS\\3.9.0.20938\\uninst.exe\"', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (6, '08:8E:90:C7:79:13', 'PremiumSoft Navicat Premium 16.3', 'C:\\Program Files\\PremiumSoft\\Navicat Premium 16\\', '\"C:\\Program Files\\PremiumSoft\\Navicat Premium 16\\unins000.exe\"', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (7, '08:8E:90:C7:79:13', 'Microsoft OneNote - zh-cn', 'C:\\Program Files\\Microsoft Office', '\"C:\\Program Files\\Common Files\\Microsoft Shared\\ClickToRun\\OfficeClickToRun.exe\" scenario=install scenariosubtype=ARP sourcetype=None productstoremove=OneNoteFreeRetail.16_zh-cn_x-none culture=zh-cn version.16=16.0', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (8, '08:8E:90:C7:79:13', 'Quartus Prime Lite Edition (Free) 20.1.0.711', 'C:\\intelFPGA_lite\\20.1', '\"C:\\intelFPGA_lite\\20.1\\uninstall\\quartus_lite-20.1.0.711-windows-uninstall.exe\"', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (9, '08:8E:90:C7:79:13', '腾讯会议', '\"C:\\Program Files\\Tencent\\WeMeet\\3.34.11.408\"', '\"C:\\Program Files\\Tencent\\WeMeet\\3.34.11.408\\WeMeetUninstall.exe\"', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (10, '08:8E:90:C7:79:13', 'WinRAR 7.00 (64-位)', 'C:\\Program Files\\WinRAR', 'C:\\Program Files\\WinRAR\\uninstall.exe', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (11, '08:8E:90:C7:79:13', 'Microsoft Visual C++ 2022 X64 Additional Runtime - 14.36.32532', '', 'MsiExec.exe /I{0025DD72-A959-45B5-A0A3-7EFEB15A8050}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (12, '08:8E:90:C7:79:13', 'Microsoft Visual C++ 2013 x64 Additional Runtime - 12.0.40664', '', 'MsiExec.exe /X{010792BA-551A-3AC0-A7EF-0FAB4156C382}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (13, '08:8E:90:C7:79:13', 'Python 3.10.4 pip Bootstrap (64-bit)', '', 'MsiExec.exe /I{0707FD0B-C82B-4730-8967-D6C3003BCAE0}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (14, '08:8E:90:C7:79:13', 'SQL Server Management Studio', '', 'MsiExec.exe /I{07A90211-80BE-440C-A18E-C3E63915F3DF}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (15, '08:8E:90:C7:79:13', 'MySQL Connector C++ 8.0', 'C:\\Program Files\\MySQL\\Connector C++ 8.0\\', 'MsiExec.exe /I{08C1C9C9-0C03-41FF-8010-D0061C5BE7FE}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (16, '08:8E:90:C7:79:13', 'SQL Server 2019 Client Tools Extensions', '', 'MsiExec.exe /I{0A016847-2CCF-4D51-94B0-AE61CA27A675}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (17, '08:8E:90:C7:79:13', 'Windows SDK DirectX x64 Remote', '', 'MsiExec.exe /I{0B12F7E3-EDAA-AF92-20BB-88540FEF54BA}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (18, '08:8E:90:C7:79:13', 'vs_devenx64vmsi', '', 'MsiExec.exe /I{0DE775A3-1C63-4210-9CDC-D23F5330D715}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (19, '08:8E:90:C7:79:13', 'SQL Server 2019 Common Files', '', 'MsiExec.exe /I{0FB552DD-543E-48E7-A6F4-2F8D82723C6A}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (20, '08:8E:90:C7:79:13', 'Python 3.11.5 Development Libraries (64-bit)', '', 'MsiExec.exe /I{0FEE67DA-831A-442F-A7B1-D709EF005148}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (21, '08:8E:90:C7:79:13', 'Python 3.10.4 Core Interpreter (64-bit)', '', 'MsiExec.exe /I{12BDD20C-1666-463B-B473-3473B4BB97A7}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (22, '08:8E:90:C7:79:13', 'Microsoft SQL Server 2019 Setup (English)', '', 'MsiExec.exe /X{17DCED0E-5B27-453A-B2B4-E487B869B28A}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (23, '08:8E:90:C7:79:13', 'SQL Server 2019 Database Engine Services', '', 'MsiExec.exe /I{186F36FA-A3D6-403D-82DF-4E2BA7759CE7}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (24, '08:8E:90:C7:79:13', 'vs_minshellx64msi', '', 'MsiExec.exe /I{1AB2F81F-A360-4BE1-B68F-B50F0609A1AE}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (25, '08:8E:90:C7:79:13', 'SQL Server 2019 Database Engine Shared', '', 'MsiExec.exe /I{1B7BFC1D-9D10-437F-B446-DC87A836A2D2}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (26, '08:8E:90:C7:79:13', 'Python 3.11.5 Tcl/Tk Support (64-bit)', '', 'MsiExec.exe /I{1BA18593-41AB-434B-B31F-EEC8BBA9612A}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (27, '08:8E:90:C7:79:13', 'Microsoft Visual C++ 2010  x64 Redistributable - 10.0.40219', '', 'MsiExec.exe /X{1D8E6291-B0D5-35EC-8441-6616F567A0F7}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (28, '08:8E:90:C7:79:13', 'SQL Server 2019 XEvent', '', 'MsiExec.exe /I{2129312E-5204-4F3A-9039-B6D34DBB00FB}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (29, '08:8E:90:C7:79:13', 'SQL Server Management Studio for Analysis Services', '', 'MsiExec.exe /I{2635EEB8-0BDC-43F4-8477-3F3A17485D9A}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (30, '08:8E:90:C7:79:13', 'Java 8 Update 144 (64-bit)', 'C:\\Program Files\\Java\\jre1.8.0_144\\', 'MsiExec.exe /X{26A24AE4-039D-4CA4-87B4-2F64180144F0}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (31, '08:8E:90:C7:79:13', 'Java(TM) SE Development Kit 21.0.6 (64-bit)', 'C:\\Program Files\\Java\\jdk-21\\', 'MsiExec.exe /X{26F4C2FF-9E0F-5FD4-B66B-1B13F1068A00}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (32, '08:8E:90:C7:79:13', 'Microsoft Visual C++ 2022 X64 Debug Runtime - 14.34.31931', '', 'MsiExec.exe /I{28E7B326-6E09-4960-88C1-AF305A11665C}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (33, '08:8E:90:C7:79:13', 'SQL Server 2019 SQL Diagnostics', '', 'MsiExec.exe /I{28ED6838-D8E5-454C-A813-12C5EB447CAB}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (34, '08:8E:90:C7:79:13', 'Application Verifier x64 External Package', '', 'MsiExec.exe /I{2CBA883F-51A6-3D7D-DBB9-0527D39433CB}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (35, '08:8E:90:C7:79:13', 'Python 3.11.5 Documentation (64-bit)', '', 'MsiExec.exe /I{2EB6BD56-25CA-49CB-8CFD-B03D872B8239}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (36, '08:8E:90:C7:79:13', 'MySQL Server 5.7', 'C:\\Program Files\\MySQL\\MySQL Server 5.7\\', 'MsiExec.exe /I{2F3AB21E-EFF9-4CFD-AF0D-8B983FC9DC37}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (37, '08:8E:90:C7:79:13', 'Microsoft VSS Writer for SQL Server 2019', '', 'MsiExec.exe /I{353FC88B-8D34-42F4-A407-6C51ED5CD919}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (38, '08:8E:90:C7:79:13', 'Node.js', '', 'MsiExec.exe /I{3754FE15-6D3F-4C6B-ABF5-AE4AEC711CEC}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (39, '08:8E:90:C7:79:13', 'Typora 1.0', 'C:\\Program Files\\Typora\\', '\"C:\\Program Files\\Typora\\unins000.exe\"', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (40, '08:8E:90:C7:79:13', 'Microsoft Visual C++ 2012 x64 Additional Runtime - 11.0.61030', '', 'MsiExec.exe /X{37B8F9C7-03FB-3253-8781-2517C99D7C00}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (41, '08:8E:90:C7:79:13', 'Microsoft System CLR Types for SQL Server 2019', '', 'MsiExec.exe /I{3E5195F5-ED93-4406-B149-F9F66F35E851}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (42, '08:8E:90:C7:79:13', 'Bitvise SSH Client - FlowSshNet (x64)', '', 'MsiExec.exe /X{42B2DE86-F7D1-412F-9AF3-A4561BBD837F}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (43, '08:8E:90:C7:79:13', 'Blackmagic RAW Common Components', '', 'MsiExec.exe /I{47DFB167-EACF-4A3D-A16F-BDF9E0D68983}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (44, '08:8E:90:C7:79:13', 'SQL Server 2019 DMF', '', 'MsiExec.exe /I{4F3D424C-5552-45F2-9148-45189D04F7F1}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (45, '08:8E:90:C7:79:13', 'Microsoft Visual C++ 2013 x64 Minimum Runtime - 12.0.40664', '', 'MsiExec.exe /X{53CF6934-A98D-3D84-9146-FC4EDF3D5641}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (46, '08:8E:90:C7:79:13', 'vs_minshellinteropx64msi', '', 'MsiExec.exe /I{5691C5AB-0847-4862-8C49-43245E7DCE2C}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (47, '08:8E:90:C7:79:13', 'Python 3.11.5 pip Bootstrap (64-bit)', '', 'MsiExec.exe /I{57527742-12D9-4E19-ACFF-6A7B0A88D23A}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (48, '08:8E:90:C7:79:13', 'Microsoft SQL Server 2019 RsFx Driver', '', 'MsiExec.exe /I{5825CDC4-4E99-4CF9-91FE-DB60C0E2F5EA}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (49, '08:8E:90:C7:79:13', 'SQL Server 2019 Integration Services', '', 'MsiExec.exe /I{58A39382-FA21-4FDC-9396-48EE0A7CB740}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (50, '08:8E:90:C7:79:13', 'Python 3.10.4 Development Libraries (64-bit)', '', 'MsiExec.exe /I{5A092BC3-DC8C-4B40-871A-D50F71058449}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (51, '08:8E:90:C7:79:13', 'SQL Server 2019 Shared Management Objects', '', 'MsiExec.exe /I{5D24DB68-43A1-4C75-AEFC-902D2EC0E25D}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (52, '08:8E:90:C7:79:13', 'Microsoft Visual C++ 2008 Redistributable - x64 9.0.30729.6161', '', 'MsiExec.exe /X{5FCE6D76-F5DC-37AB-B2B8-22AB8CEDB1D4}', '2025-06-14 20:22:43');
INSERT INTO `app_info` VALUES (53, '08:8E:90:C7:79:13', 'Java SE Development Kit 8 Update 144 (64-bit)', 'C:\\Program Files\\Java\\jdk1.8.0_144\\', 'MsiExec.exe /X{64A3A4F4-B792-11D6-A78A-00B0D0180144}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (54, '08:8E:90:C7:79:13', 'Azure Data Studio', 'C:\\Program Files\\Azure Data Studio\\', '\"C:\\Program Files\\Azure Data Studio\\unins000.exe\"', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (55, '08:8E:90:C7:79:13', 'Java(TM) SE Development Kit 17.0.2 (64-bit)', 'C:\\Program Files\\Java\\jdk-17.0.2\\', 'MsiExec.exe /X{65BA81E7-0238-5B54-9069-A59610247B0B}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (56, '08:8E:90:C7:79:13', 'SQL Server 2019 Client Tools', '', 'MsiExec.exe /I{68B843D3-5C31-4F0C-B61C-662C97FDAD1C}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (57, '08:8E:90:C7:79:13', 'Windows App Certification Kit Native Components', '', 'MsiExec.exe /I{69331A50-908A-0745-CFCF-8413360C5B96}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (58, '08:8E:90:C7:79:13', 'Python 3.11.5 Test Suite (64-bit)', '', 'MsiExec.exe /I{6D4BE933-74FA-43A6-B654-CC1BCEF568D5}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (59, '08:8E:90:C7:79:13', 'Microsoft Visual Studio Installer', '\"C:\\Program Files (x86)\\Microsoft Visual Studio\\Installer\"', '\"C:\\Program Files (x86)\\Microsoft Visual Studio\\Installer\\setup.exe\" /uninstall', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (60, '08:8E:90:C7:79:13', 'Python 3.11.5 Executables (64-bit)', '', 'MsiExec.exe /I{798A2965-0FFA-4061-AE86-FCD98A4FBB4A}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (61, '08:8E:90:C7:79:13', 'VS JIT Debugger', '', 'MsiExec.exe /I{7B8542BA-01E4-43EB-A172-1DA975AFD00B}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (62, '08:8E:90:C7:79:13', 'Python 3.10.4 Utility Scripts (64-bit)', '', 'MsiExec.exe /I{7CBB42A3-C12B-413C-AA93-65DA4C31D421}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (63, '08:8E:90:C7:79:13', 'icecap_collection_x64', '', 'MsiExec.exe /I{7D6A7B92-A26B-4DC6-A51F-0D741C9BC70F}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (64, '08:8E:90:C7:79:13', 'Universal CRT Tools x64', '', 'MsiExec.exe /I{82F9F289-6088-8F39-1918-A45315FEF99A}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (65, '08:8E:90:C7:79:13', 'MySQL Connector/ODBC 8.3 (64-bit)', 'C:\\Program Files\\MySQL\\Connector ODBC 8.3\\', 'MsiExec.exe /I{8652E1C1-58AF-486D-A7C3-A492C6D7D444}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (66, '08:8E:90:C7:79:13', 'Python 3.11.5 Utility Scripts (64-bit)', '', 'MsiExec.exe /I{896CE1B5-5393-426C-A466-4465EEAE1363}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (67, '08:8E:90:C7:79:13', 'Microsoft OLE DB Driver for SQL Server', '', 'MsiExec.exe /I{898A728A-CCF2-48F5-96DF-ED18DEBE5A6C}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (68, '08:8E:90:C7:79:13', 'SQL Server 2019 Shared Management Objects Extensions', '', 'MsiExec.exe /I{8DDAEBCA-4267-4E16-9FE0-D87F21D36891}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (69, '08:8E:90:C7:79:13', 'Office 16 Click-to-Run Licensing Component', '', 'MsiExec.exe /I{90160000-007E-0000-1000-0000000FF1CE}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (70, '08:8E:90:C7:79:13', 'Office 16 Click-to-Run Extensibility Component', '', 'MsiExec.exe /X{90160000-008C-0000-1000-0000000FF1CE}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (71, '08:8E:90:C7:79:13', 'Office 16 Click-to-Run Localization Component', '', 'MsiExec.exe /X{90160000-008C-0804-1000-0000000FF1CE}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (72, '08:8E:90:C7:79:13', 'vs_Graphics_Singletonx64', '', 'MsiExec.exe /I{9138874C-2D20-46BC-84BC-A13B31DF8955}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (73, '08:8E:90:C7:79:13', 'Microsoft SQL Server 2012 Native Client ', '', 'MsiExec.exe /I{926B987C-C8DF-4FC8-85C0-622279D90C45}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (74, '08:8E:90:C7:79:13', 'Oracle VM VirtualBox 7.0.18', '', 'MsiExec.exe /I{969E2718-394A-4636-A0E3-ECB97F2FCCB2}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (75, '08:8E:90:C7:79:13', 'vs_communityx64msi', '', 'MsiExec.exe /I{9BF7BDD3-62E8-4E47-AF96-13EA1EB853AD}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (76, '08:8E:90:C7:79:13', 'Python 3.10.4 Test Suite (64-bit)', '', 'MsiExec.exe /I{9C759455-2832-4F78-B2C7-511820072E90}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (77, '08:8E:90:C7:79:13', 'Microsoft SQL Server 2019 T-SQL 语言服务 ', '', 'MsiExec.exe /I{9DC7C869-A4C6-48CA-A938-26DB1FC5FC3B}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (78, '08:8E:90:C7:79:13', 'SQL Server 2019 Connection Info', '', 'MsiExec.exe /I{9E2762BB-786A-49D1-BB82-75524B3DA1D8}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (79, '08:8E:90:C7:79:13', '亚信安全 终端安全管家 高级版', 'D:\\Program Files (x86)\\AsiaInfo\\', 'D:\\Program Files (x86)\\AsiaInfo\\Titanium\\Remove.exe', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (80, '08:8E:90:C7:79:13', 'MySQL Connector/C 6.1', 'C:\\Program Files\\MySQL\\MySQL Connector.C 6.1\\', 'MsiExec.exe /I{ABC3A516-54E3-414B-B501-762E7FB2F9D5}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (81, '08:8E:90:C7:79:13', 'Adobe Acrobat (64-bit)', 'C:\\Program Files\\Adobe\\Acrobat DC\\', 'MsiExec.exe /I{AC76BA86-2052-1033-7760-BC15014EA700}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (82, '08:8E:90:C7:79:13', 'Microsoft Visual C++ 2005 Redistributable (x64)', '', 'MsiExec.exe /X{ad8a2fa1-06e7-4b0d-927d-6e54b3d31028}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (83, '08:8E:90:C7:79:13', 'Microsoft Visual Studio Tools for Applications 2017 x64 Hosting Support', '', 'MsiExec.exe /X{AFFB9D8D-6E58-38A0-A7DD-F6F1F4247B36}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (84, '08:8E:90:C7:79:13', 'Python 3.11.5 Add to Path (64-bit)', '', 'MsiExec.exe /I{B1D86A26-1D57-45EE-AAAD-2E19018A6376}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (85, '08:8E:90:C7:79:13', 'NVIDIA 图形驱动程序 517.47', 'C:\\Program Files\\NVIDIA Corporation\\Installer2\\Display.Driver.{BCB5BB73-79F9-4F7F-B7B7-BF713457003B}', '\"C:\\WINDOWS\\SysWOW64\\RunDll32.EXE\" \"C:\\Program Files\\NVIDIA Corporation\\Installer2\\InstallerCore\\NVI2.DLL\",UninstallPackage Display.Driver', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (86, '08:8E:90:C7:79:13', 'NVIDIA GeForce Experience 3.23.0.74', 'C:\\Program Files\\NVIDIA Corporation\\Installer2\\Display.GFExperience.{BC54916B-4D78-4379-A06D-2B2A8339DB05}', '\"C:\\Windows\\SysWOW64\\RunDll32.EXE\" \"C:\\Program Files\\NVIDIA Corporation\\Installer2\\InstallerCore\\NVI2.DLL\",UninstallPackage Display.GFExperience', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (87, '08:8E:90:C7:79:13', 'NVIDIA PhysX 系统软件 9.21.0713', 'C:\\Program Files (x86)\\NVIDIA Corporation\\PhysX', '\"C:\\Windows\\SysWOW64\\RunDll32.EXE\" \"C:\\Program Files\\NVIDIA Corporation\\Installer2\\InstallerCore\\NVI2.DLL\",UninstallPackage Display.PhysX', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (88, '08:8E:90:C7:79:13', 'NVIDIA FrameView SDK 1.1.4923.29968894', 'C:\\Program Files\\NVIDIA Corporation\\FrameViewSDK', '\"C:\\Windows\\SysWOW64\\RunDll32.EXE\" \"C:\\Program Files\\NVIDIA Corporation\\Installer2\\InstallerCore\\NVI2.DLL\",UninstallPackage FrameViewSdk', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (89, '08:8E:90:C7:79:13', 'SSMS Post Install Tasks', '', 'MsiExec.exe /I{BCE4DAEE-4995-4E80-9222-86C950DDD940}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (90, '08:8E:90:C7:79:13', 'Microsoft ODBC Driver 17 for SQL Server', '', 'MsiExec.exe /I{BE12E5B1-C477-48F5-981D-5C37B4390E02}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (91, '08:8E:90:C7:79:13', 'Microsoft Analysis Services OLE DB 提供程序', '', 'MsiExec.exe /I{C4E2FDC7-9D2B-4AC7-A112-B68F0F7B4AAF}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (92, '08:8E:90:C7:79:13', 'Python 3.11.5 Core Interpreter (64-bit)', '', 'MsiExec.exe /I{C62CE14B-8E3D-4A41-8671-405CA705DDF2}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (93, '08:8E:90:C7:79:13', 'Microsoft Update Health Tools', '', 'MsiExec.exe /X{C6FD611E-7EFE-488C-A0E0-974C09EF6473}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (94, '08:8E:90:C7:79:13', 'VS Script Debugging Common', '', 'MsiExec.exe /I{C8EA234A-FC2F-4EEC-BF7F-DB14C28C84D2}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (95, '08:8E:90:C7:79:13', 'MySQL Workbench 8.0 CE', 'C:\\Program Files\\MySQL\\MySQL Workbench 8.0', 'MsiExec.exe /I{CB651F1E-4B3C-496C-88DA-54499A6F8C6B}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (96, '08:8E:90:C7:79:13', 'Python 3.11.5 Standard Library (64-bit)', '', 'MsiExec.exe /I{CDE4410B-99CE-46EB-B88B-9881AE7E7438}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (97, '08:8E:90:C7:79:13', 'Microsoft Visual C++ 2012 x64 Minimum Runtime - 11.0.61030', '', 'MsiExec.exe /X{CF2BEA3C-26EA-32F8-AA9B-331F7E34BA97}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (98, '08:8E:90:C7:79:13', 'Java(TM) SE Development Kit 20.0.2 (64-bit)', 'C:\\Program Files\\Java\\jdk-20\\', 'MsiExec.exe /X{D2D0311F-1C55-57CC-95CC-F973FA7660D4}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (99, '08:8E:90:C7:79:13', 'SQL Server 2019 Batch Parser', '', 'MsiExec.exe /I{D459615B-83B0-408F-8F39-6CC07C277BA6}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (100, '08:8E:90:C7:79:13', 'Microsoft Visual C++ 2022 X64 Minimum Runtime - 14.36.32532', '', 'MsiExec.exe /I{D5D19E2F-7189-42FE-8103-92CD1FA457C2}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (101, '08:8E:90:C7:79:13', 'SQL Server 2019 SQL Data Quality Common', '', 'MsiExec.exe /I{DE61B584-A1E5-4AB4-810B-EC2F8C106B00}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (102, '08:8E:90:C7:79:13', 'Pandoc 3.6.4', 'C:\\Users\\dawn\\AppData\\Local\\Pandoc\\', 'MsiExec.exe /X{E21E4135-CB2E-4A4D-B039-45C9134BED5E}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (103, '08:8E:90:C7:79:13', 'Python 3.10.4 Tcl/Tk Support (64-bit)', '', 'MsiExec.exe /I{E22FBFCD-7312-4CED-BE8C-B8CB8D4EADCA}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (104, '08:8E:90:C7:79:13', 'Python 3.10.4 Documentation (64-bit)', '', 'MsiExec.exe /I{E2B8DCDD-2047-44A2-ADC7-E526084777B4}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (105, '08:8E:90:C7:79:13', 'Microsoft Visual Studio Code', 'D:\\soft\\Microsoft VS Code\\', '\"D:\\soft\\Microsoft VS Code\\unins000.exe\"', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (106, '08:8E:90:C7:79:13', 'SQL Server Management Studio for Reporting Services', '', 'MsiExec.exe /I{EC766230-FA07-4462-95EC-589FE19A6B19}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (107, '08:8E:90:C7:79:13', 'Python 3.10.4 Executables (64-bit)', '', 'MsiExec.exe /I{FBCE87D2-C7FC-47AB-B870-A0613A081CFD}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (108, '08:8E:90:C7:79:13', 'DiagnosticsHub_CollectionService', '', 'MsiExec.exe /I{FECAFEB5-8D0E-4AE4-8FA0-745BAA835C35}', '2025-06-14 20:22:44');
INSERT INTO `app_info` VALUES (109, '08:8E:90:C7:79:13', 'Python 3.10.4 Standard Library (64-bit)', '', 'MsiExec.exe /I{FFF8FCBE-5551-4DB2-8828-D2FE463981E2}', '2025-06-14 20:22:44');

-- ----------------------------
-- Table structure for application_risk
-- ----------------------------
DROP TABLE IF EXISTS `application_risk`;
CREATE TABLE `application_risk`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rule_id` int NOT NULL COMMENT '规则ID',
  `mac` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设备MAC地址',
  `risk_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '风险名称',
  `risk_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '风险类型',
  `risk_level` int NULL DEFAULT NULL COMMENT '风险等级',
  `target_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标主机',
  `target_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标URL',
  `detection_time` datetime NOT NULL COMMENT '检测时间',
  `is_risky` tinyint(1) NULL DEFAULT 0 COMMENT '是否存在风险：0-否，1-是',
  `risk_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '风险详情',
  `response_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '响应内容（截取前1000字符）',
  `error_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '错误信息',
  `remediation_advice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '修复建议',
  `status` enum('pending','confirmed','fixed','false_positive') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'pending' COMMENT '处理状态',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_rule_id`(`rule_id` ASC) USING BTREE,
  INDEX `idx_target_host`(`target_host` ASC) USING BTREE,
  INDEX `idx_detection_time`(`detection_time` ASC) USING BTREE,
  INDEX `idx_is_risky`(`is_risky` ASC) USING BTREE,
  CONSTRAINT `application_risk_ibfk_1` FOREIGN KEY (`rule_id`) REFERENCES `application_risk_rules` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '应用风险检测结果表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of application_risk
-- ----------------------------
INSERT INTO `application_risk` VALUES (1, 3, '08:8E:90:C7:79:13', NULL, NULL, NULL, NULL, NULL, '2025-06-16 22:46:49', 0, NULL, NULL, NULL, NULL, 'pending', '2025-06-16 22:46:48');

-- ----------------------------
-- Table structure for application_risk_rules
-- ----------------------------
DROP TABLE IF EXISTS `application_risk_rules`;
CREATE TABLE `application_risk_rules`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `risk_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '风险名称',
  `risk_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '风险描述',
  `risk_level` int NOT NULL COMMENT '风险等级：1-低风险，2-中风险，3-高风险，4-严重风险',
  `risk_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '风险类型：权限配置、信息泄露、服务配置、认证绕过等',
  `target_service` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目标服务：tomcat、ftp、nginx、apache等',
  `detection_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '检测方法：HTTP、HTTPS、PORT、SERVICE',
  `detection_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '检测路径',
  `detection_payload` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '检测载荷',
  `risk_flag` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '风险标识：用于判断是否存在风险的关键字',
  `remediation_advice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '修复建议',
  `status` enum('active','inactive') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'active' COMMENT '规则状态',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_risk_type`(`risk_type` ASC) USING BTREE,
  INDEX `idx_target_service`(`target_service` ASC) USING BTREE,
  INDEX `idx_risk_level`(`risk_level` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '应用风险检测规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_risk_rules
-- ----------------------------
INSERT INTO `application_risk_rules` VALUES (1, 'Tomcat运行权限检查', 'Tomcat运行权限过高', 4, '权限配置', 'tomcat', 'HTTP', '/manager/html', '$tomcatProc = Get-CimInstance Win32_Process | Where-Object { $_.Name -like \'tomcat*.exe\' -or $_.CommandLine -match \'tomcat\' }; if (-not $tomcatProc) { exit 0 }; foreach ($proc in $tomcatProc) { try { $owner = $proc.GetOwner(); $user = $owner.User; if ($user -in @(\'SYSTEM\',\'Administrator\',\'Administrators\')) { exit 1 } } catch {} }; exit 0\r\n', 'Apache Tomcat', '建议使用低权限用户（非 SYSTEM/Administrator）运行 Tomcat', 'active', '2025-06-13 15:13:17', '2025-06-16 21:15:36');
INSERT INTO `application_risk_rules` VALUES (2, 'Tomcat404错误页面重定向配置检查', 'Tomcat默认404错误页面可能泄露服务器版本信息', 2, '配置风险', 'tomcat', 'HTTP', '/nonexistent-page-test-404', '$tomcatHome = $env:CATALINA_HOME; if (-not $tomcatHome) { $defaultPaths = @(\'${env:ProgramFiles}\\\\Apache Software Foundation\\\\Tomcat *\',\'${env:ProgramFiles(x86)}\\\\Apache Software Foundation\\\\Tomcat *\',\'${env:SystemDrive}\\\\apache-tomcat-*\'); foreach ($p in $defaultPaths) { $found = Resolve-Path $p -ErrorAction SilentlyContinue | Select-Object -ExpandProperty Path -First 1; if ($found) { $tomcatHome = $found; break } } }; if (-not $tomcatHome) { exit 0 }; $webXml = Join-Path $tomcatHome \'conf\\\\web.xml\'; if (-not (Test-Path $webXml)) { exit 0 }; $content = Get-Content $webXml -Raw; if ($content -notmatch \'<error-page>\\\\s*<error-code>\\\\s*404\\\\s*</error-code>\' -or $content -match \'<location>\\\\s*http(s)?://\') { exit 1 } else { exit 0 }', 'Apache Tomcat', '请在 Tomcat 安装目录的 conf/web.xml 中正确配置 <error-page><error-code>404</error-code><location>/custom-404.html</location></error-page>，以防止默认页面泄露服务器信息或重定向漏洞', 'active', '2025-06-13 15:13:17', '2025-06-16 21:16:34');
INSERT INTO `application_risk_rules` VALUES (3, 'FTP用户权限可跨目录', 'FTP 用户若具备访问主机上任意用户目录权限，可能导致信息泄露或被用作攻击跳板', 3, '权限配置', 'ftp', 'PORT', '', '$testPath = \"C:\\TestUser\"\r\nif (Test-Path $testPath) {\r\n    $access = icacls $testPath 2>$null\r\n    if ($access -match \"Everyone:\\(F\\)\" -or $access -match \"Users:\\(F\\)\") {\r\n        exit 1\r\n    }\r\n}\r\nexit 0\r\n', '', '请限制 FTP 用户目录权限，使用隔离主目录（Chroot）方式或虚拟目录映射，仅允许其访问授权目录，避免访问系统敏感路径', 'active', '2025-06-13 15:13:17', '2025-06-16 21:36:39');

-- ----------------------------
-- Table structure for host
-- ----------------------------
DROP TABLE IF EXISTS `host`;
CREATE TABLE `host`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `mac_address` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主机mac地址',
  `host_name` varchar(50) CHARACTER SET cp850 COLLATE cp850_general_ci NOT NULL COMMENT '主机的名字',
  `ip_address` varchar(40) CHARACTER SET cp850 COLLATE cp850_general_ci NOT NULL COMMENT '主机ip',
  `os_type` varchar(20) CHARACTER SET cp850 COLLATE cp850_general_ci NOT NULL COMMENT '主机系统类型',
  `os_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `os_version` varchar(50) CHARACTER SET cp850 COLLATE cp850_general_ci NOT NULL COMMENT '主机操作系统版本',
  `os_bit` varchar(10) CHARACTER SET cp850 COLLATE cp850_general_ci NOT NULL COMMENT '主机操作系统位数',
  `cpu_name` varchar(100) CHARACTER SET cp850 COLLATE cp850_general_ci NOT NULL COMMENT '主机cpu名字',
  `ram` varchar(3) CHARACTER SET cp850 COLLATE cp850_general_ci NOT NULL COMMENT '主机内存大小',
  `status` int NOT NULL COMMENT '主机状态：1表示在线；0表示下线',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `mac_unique`(`mac_address` ASC) USING BTREE COMMENT 'MAC地址的唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = cp850 COLLATE = cp850_general_ci COMMENT = '主机表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of host
-- ----------------------------
INSERT INTO `host` VALUES (1, '08:8E:90:C7:79:13', 'LAPTOP-Shu', '10.132.235.16', 'Windows', 'Microsoft Windows 11 Home China', '10.0.26100', '64bit', '11th Gen Intel(R) Core(TM) i5-11300H @ 3.10GHz', '16', 1, '2025-06-09 16:53:56', '2025-06-16 23:23:44');

-- ----------------------------
-- Table structure for hotfix
-- ----------------------------
DROP TABLE IF EXISTS `hotfix`;
CREATE TABLE `hotfix`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `mac` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备MAC地址',
  `hotfix_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '补丁编号',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_mac_hotfix`(`mac` ASC, `hotfix_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '补丁表：记录设备与补丁的对应关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hotfix
-- ----------------------------
INSERT INTO `hotfix` VALUES (1, '08:8E:90:C7:79:13', 'KB5056579', '2025-06-16 22:44:43');
INSERT INTO `hotfix` VALUES (2, '08:8E:90:C7:79:13', 'KB5063060', '2025-06-16 22:44:43');
INSERT INTO `hotfix` VALUES (3, '08:8E:90:C7:79:13', 'KB5059502', '2025-06-16 22:44:43');

-- ----------------------------
-- Table structure for process_info
-- ----------------------------
DROP TABLE IF EXISTS `process_info`;
CREATE TABLE `process_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mac` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `pid` int NOT NULL,
  `ppid` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `cmd` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `priority` int NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `collect_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of process_info
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `role_des` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', '超级管理员');
INSERT INTO `role` VALUES (2, '普通用户', '普通用户');
INSERT INTO `role` VALUES (6, 'VIP', 'VIP');

-- ----------------------------
-- Table structure for service_info
-- ----------------------------
DROP TABLE IF EXISTS `service_info`;
CREATE TABLE `service_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `host_id` int NOT NULL,
  `port` int NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `state` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `protocol` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `product` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `version` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `extrainfo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `host_id_port_unique`(`host_id` ASC, `port` ASC) USING BTREE,
  INDEX `host_id_foreign`(`host_id` ASC) USING BTREE,
  CONSTRAINT `host_id_foreign` FOREIGN KEY (`host_id`) REFERENCES `host` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of service_info
-- ----------------------------

-- ----------------------------
-- Table structure for system_risk
-- ----------------------------
DROP TABLE IF EXISTS `system_risk`;
CREATE TABLE `system_risk`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mac` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `rule_id` int NOT NULL,
  `risk_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `risk_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `risk_level` int NULL DEFAULT NULL,
  `is_risky` tinyint NULL DEFAULT 0,
  `detection_output` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `risk_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `remediation_advice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_risk`(`mac` ASC, `rule_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_risk
-- ----------------------------
INSERT INTO `system_risk` VALUES (1, '08:8E:90:C7:79:13', 1, '服务器时间偏差过大', '系统配置', 2, 1, 'NTP时间: 2025-06-16 22:59:24.972854, 本地时间: 2025-06-16 22:47:47.702886', 'NTP时间偏差：697 秒', '建议配置 Windows 时间同步服务或使用 NTP 工具', '2025-06-16 22:47:49', '2025-06-16 22:47:49');

-- ----------------------------
-- Table structure for system_risk_rules
-- ----------------------------
DROP TABLE IF EXISTS `system_risk_rules`;
CREATE TABLE `system_risk_rules`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `risk_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '风险名称',
  `risk_level` int NOT NULL COMMENT '风险等级（1=低，2=中，3=高）',
  `risk_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '风险类型（如系统配置、注册表配置、网络配置）',
  `detection_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '检测方法（如 NTP、REG、CMD）',
  `rule_path` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '检测路径（如注册表路径、命令）',
  `rule_payload` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '匹配值（如注册表中的值或命令的返回）',
  `remediation_advice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '修复建议',
  `is_active` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统风险检测规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_risk_rules
-- ----------------------------
INSERT INTO `system_risk_rules` VALUES (1, '服务器时间偏差过大', 2, '系统配置', 'NTP', NULL, '时间偏差超过300秒', '建议配置 Windows 时间同步服务或使用 NTP 工具', 1);
INSERT INTO `system_risk_rules` VALUES (2, '路由转发功能开启', 3, '注册表配置', 'REG', 'SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters|IPEnableRouter', '1', '在注册表中将 IPEnableRouter 设置为 0', 1);
INSERT INTO `system_risk_rules` VALUES (3, '网卡处于混杂模式', 3, '网络配置', 'CMD', 'powershell -Command \"(Get-NetAdapter | Where-Object { $_.PromiscuousMode -eq \'Enabled\' }).Name\"', '', '执行 PowerShell 命令关闭混杂模式', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `user_pwd` char(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `user_role` int UNSIGNED NOT NULL DEFAULT 2 COMMENT '用户角色',
  `user_status` int UNSIGNED NULL DEFAULT 1 COMMENT '用户状态：1 正常 2 锁定',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '用户创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '用户更新时间',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '用户最后登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_name`(`user_name`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$l4/yfZ1SuK/8tpafODPLnuryGoL6YpZC4aYOhApSQ0Sqq4LSGrkBq', 1, 1, '2025-06-16 21:50:10', NULL, '2025-06-16 21:50:11');
INSERT INTO `user` VALUES (2, 'tomcat', '$2a$10$XUgMIGI/yKmQJFrx0oPwHOLi.Xkc2GqnES9qDvtZ79/IS2sjPaNNS', 2, 1, '2024-05-24 14:21:00', NULL, NULL);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', 'admin123');

-- ----------------------------
-- Table structure for vulnerability_risk
-- ----------------------------
DROP TABLE IF EXISTS `vulnerability_risk`;
CREATE TABLE `vulnerability_risk`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `mac` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主机MAC地址',
  `rule_id` int NOT NULL COMMENT '漏洞规则ID',
  `vul_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '漏洞名称',
  `vul_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '漏洞类型',
  `vul_level` int NOT NULL COMMENT '漏洞等级',
  `target_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目标URL',
  `payload` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '漏洞载荷',
  `scan_time` datetime NOT NULL COMMENT '扫描时间',
  `response_time` float NULL DEFAULT NULL COMMENT '响应时长（单位：秒）',
  `is_exit` tinyint(1) NULL DEFAULT 0 COMMENT '是否存在漏洞',
  `error_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '错误信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '漏洞扫描结果记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vulnerability_risk
-- ----------------------------
INSERT INTO `vulnerability_risk` VALUES (1, '08:8E:90:C7:79:13', 1, 'SQL注入测试', 'SQL注入', 1, 'http://127.0.0.1:5000/user', '?id=1\' OR \'1\'=\'1', '2025-06-16 22:45:20', 0.04, NULL, '');

-- ----------------------------
-- Table structure for vulnerability_rules
-- ----------------------------
DROP TABLE IF EXISTS `vulnerability_rules`;
CREATE TABLE `vulnerability_rules`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `vul_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '漏洞名\r\n字',
  `vul_desc` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '漏洞描\r\n述',
  `vul_level` int UNSIGNED NULL DEFAULT NULL COMMENT '漏洞等级: 1 高危 2中危 3低危',
  `vul_request_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求类型:  GET/POST/PUT/DELETE',
  `vul_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '漏洞类\r\n型: SQL注入/反序列化',
  `vul_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '漏洞产\r\n生的路径',
  `vul_payload` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '漏洞攻击载荷',
  `vul_flag` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '漏洞的\r\n验证标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vulnerability_rules
-- ----------------------------
INSERT INTO `vulnerability_rules` VALUES (1, 'SQL注入测试', '通过id参数注入SQL语句', 1, 'GET', 'SQL注入', 'http://127.0.0.1:5000/user', '?id=1\' OR \'1\'=\'1', 'Welcome admin');

-- ----------------------------
-- Table structure for weak_passwords
-- ----------------------------
DROP TABLE IF EXISTS `weak_passwords`;
CREATE TABLE `weak_passwords`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_password`(`password` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of weak_passwords
-- ----------------------------
INSERT INTO `weak_passwords` VALUES (1, '000000');
INSERT INTO `weak_passwords` VALUES (2, '111111');
INSERT INTO `weak_passwords` VALUES (3, '123123');
INSERT INTO `weak_passwords` VALUES (4, '123321');
INSERT INTO `weak_passwords` VALUES (5, '1234');

-- ----------------------------
-- Table structure for weakpassword_risk
-- ----------------------------
DROP TABLE IF EXISTS `weakpassword_risk`;
CREATE TABLE `weakpassword_risk`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `weak` tinyint(1) NOT NULL COMMENT '是否弱密码，true表示弱密码',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码（可为空）',
  `mac` varchar(17) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'MAC地址',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '弱密码风险用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of weakpassword_risk
-- ----------------------------
INSERT INTO `weakpassword_risk` VALUES (1, 'test', 1, '123123', '08:8E:90:C7:79:13', '2025-06-16 22:47:04');

-- ----------------------------
-- Table structure for win_cve_db
-- ----------------------------
DROP TABLE IF EXISTS `win_cve_db`;
CREATE TABLE `win_cve_db`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cve` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'CVE id',
  `score` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '各产品的CVSS Score平均值',
  `product_id_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '漏洞影响范围，以productid集表示',
  `kb_list` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '漏洞对应补丁号合集',
  `cvrf_id` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'cvrf id',
  `dt` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据日期',
  PRIMARY KEY (`id`, `dt`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 924 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Windows漏洞与KB补丁关系库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of win_cve_db
-- ----------------------------
INSERT INTO `win_cve_db` VALUES (386, 'CVE-2025-24513', '0', '11870', 'KB5056579', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (387, 'CVE-2025-24035', '8.1', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (388, 'CVE-2024-9157', '0', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (389, 'CVE-2025-24044', '7.8', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053886,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (390, 'CVE-2025-24043', '7.5', '12498', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (391, 'CVE-2025-24057', '7.8', '11573,11574,11762,11763,11951,11952,11953,12420,12421,12440,10753,10754', 'KB5002693,KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (392, 'CVE-2025-24070', '7.0', '12256,12507,12459,12506,12271,12322', 'KBReleaseNotes,KB5054229,KB5054230', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (393, 'CVE-2025-24077', '7.8', '11762,11763,11951,12420,12421,12440', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (394, 'CVE-2025-24078', '7.0', '11573,11574,11762,11763,11951,11952,11953,12420,12421,12440,10746,10747', 'KB5002662,KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (395, 'CVE-2025-24079', '7.8', '11573,11574,11762,11763,11951,11952,11953,12420,12421,12440,10746,10747', 'KB5002662,KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (396, 'CVE-2025-24080', '7.8', '11573,11574,11762,11763,11952,11953,12420,12421,10753,10754', 'KB5002693', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (397, 'CVE-2025-24081', '7.8', '10836,11573,11574,11762,11763,11951,11952,11953,12420,12421,12440,10739,10740', 'KBReleaseNotes,KB5002696,KB5002694,KB5002690', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (398, 'CVE-2025-24082', '7.8', '10836,11573,11574,11762,11763,11951,11952,11953,12420,12421,12440,10739,10740', 'KBReleaseNotes,KB5002696,KB5002690', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (399, 'CVE-2025-24083', '7.8', '11573,11574,11762,11763,11951,11952,11953,12420,12421,12440,10753,10754', 'KB5002693,KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (400, 'CVE-2025-24986', '6.5', '12509,12510', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (401, 'CVE-2025-24987', '6.6', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (402, 'CVE-2025-24988', '6.6', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (403, 'CVE-2025-21180', '7.8', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (404, 'CVE-2025-24995', '7.8', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053618,KB5053606,KB5053596,KB5053602,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (405, 'CVE-2025-24996', '6.5', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053620,KB5053886,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (406, 'CVE-2025-24997', '4.4', '11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053606,KB5053602,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (407, 'CVE-2025-24998', '7.3', '12506,12459,11600,11935,12271,12322', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (408, 'CVE-2025-25003', '7.3', '11935,12271,12322,12459,12506', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (409, 'CVE-2025-25008', '7.1', '11571,11924,12436,10816,10855,11572,11923,12244,12437', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053596,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (410, 'CVE-2025-1919', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (411, 'CVE-2025-1916', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (412, 'CVE-2025-1918', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (413, 'CVE-2025-1917', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (414, 'CVE-2025-1921', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (415, 'CVE-2025-1915', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (416, 'CVE-2025-1923', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (417, 'CVE-2025-1922', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (418, 'CVE-2025-1914', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (419, 'CVE-2025-2135', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (420, 'CVE-2025-1920', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (421, 'CVE-2025-29807', '8.7', '12338', '', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (422, 'CVE-2025-29814', '9.3', '12429', '', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (423, 'CVE-2025-2476', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (424, 'CVE-2025-29806', '6.5', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (425, 'CVE-2025-26683', '8.1', '12517', '', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (426, 'CVE-2025-21384', '8.3', '12401', '', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (427, 'CVE-2025-21247', '4.3', '12436,12437,11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12242,12243,12244,12389,12390,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053593,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (428, 'CVE-2025-21199', '6.7', '12473,12511', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (429, 'CVE-2025-24045', '8.1', '11571,11572,11923,11924,12437,12244,12436,10816,10855,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053887,KB5053596,KB5053886,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (430, 'CVE-2025-24046', '7.8', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053618,KB5053606,KB5053596,KB5053602,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (431, 'CVE-2025-24048', '7.8', '11569,11571,11572,11923,11924,11931,12085,12086,12097,12437,12242,12243,12244,12389,12390,12436,10853,10816,10855', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053606,KB5053596,KB5053602,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (432, 'CVE-2025-24050', '7.8', '11569,11571,11572,11923,11924,11931,12085,12086,12097,12437,12242,12243,12244,12389,12390,12436,10853,10816,10855', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053606,KB5053596,KB5053602,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (433, 'CVE-2025-24051', '8.8', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (434, 'CVE-2025-24054', '6.5', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053620,KB5053886,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (435, 'CVE-2025-24055', '4.3', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (436, 'CVE-2025-24056', '8.8', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (437, 'CVE-2025-24059', '7.8', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (438, 'CVE-2025-24061', '7.8', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053618,KB5053606,KB5053596,KB5053602,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (439, 'CVE-2025-24064', '8.1', '11571,11572,11923,11924,12437,12244,12436,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053887,KB5053888,KB5053596,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (440, 'CVE-2025-24066', '7.8', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053618,KB5053606,KB5053596,KB5053602,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (441, 'CVE-2025-24067', '7.8', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053618,KB5053606,KB5053596,KB5053602,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (442, 'CVE-2025-24071', '6.5', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,10483,10543', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (443, 'CVE-2025-24072', '7.8', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (444, 'CVE-2025-24075', '7.8', '10836,11573,11574,11762,11763,11951,11952,11953,12420,12421,12440,10739,10740', 'KBReleaseNotes,KB5002696,KB5002690', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (445, 'CVE-2025-24076', '7.3', '12085,12086,12437,12242,12243,12244,12389,12390,12436', 'KB5053598,KB5053599,KB5053636,KB5053602', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (446, 'CVE-2025-24983', '7.0', '10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053627,KB5053618,KB5053887,KB5053888,KB5053886,KB5053594,KB5053995,KB5053620', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (447, 'CVE-2025-24984', '4.6', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053886,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (448, 'CVE-2025-24985', '7.8', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (449, 'CVE-2025-24991', '5.5', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (450, 'CVE-2025-24992', '5.5', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (451, 'CVE-2025-24993', '7.8', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (452, 'CVE-2025-24994', '7.3', '12085,12086,12242,12243,12389,12390', 'KB5053598,KB5053636,KB5053602', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (453, 'CVE-2025-24049', '8.4', '12103', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (454, 'CVE-2025-26627', '7.0', '12068', '\"KBWhatsnew\"', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (455, 'CVE-2025-26630', '7.8', '11573,11574,11762,11763,11952,11953,12420,12421,10751,10752', 'KB5002697', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (456, 'CVE-2025-26631', '7.3', '11622', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (457, 'CVE-2025-26633', '7.0', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KB5053995,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (458, 'CVE-2025-26643', '5.4', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (459, 'CVE-2025-26645', '8.8', '12457,11568,11569,11571,11572,11849,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855,9312,10287,9318,9344,10051,10049,10378,10379,10483,10543', 'KB5053598,KB5053599,KB5053627,KB5053603,KB5053636,KB5053995,KB5053618,KB5053887,KB5053606,KB5053596,KB5053602,KB5053888,KB5053620,KB5053886,KBReleaseNotes,KB5053594,KB5053638', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (460, 'CVE-2025-2137', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (461, 'CVE-2025-2136', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (462, 'CVE-2025-24201', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (463, 'CVE-2025-1097', '0', '11870', '', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (464, 'CVE-2025-1098', '0', '11870', '', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (465, 'CVE-2025-1974', '0', '11870', '', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (466, 'CVE-2025-24514', '0', '11870', '', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (467, 'CVE-2025-26634', '7.5', '11568,11569,11571,11572,11923,11924,11929,11930,11931,12085,12086,12097,12098,12099,12437,12242,12243,12244,12389,12390,12436,10729,10735,10852,10853,10816,10855', 'KB5051974,KB5051980,KB5052000,KB5051989,KB5052040,KB5052006,KB5051979,KB5051987', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (468, 'CVE-2025-24053', '7.2', '12338', '', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (469, 'CVE-2025-29795', '7.8', '12470', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (470, 'CVE-2025-2783', '0', '11655', 'KBReleaseNotes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (471, 'CVE-2021-3672', '5.6', '12357,12137,12138,12356', 'KBc-ares', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (472, 'CVE-2021-23336', '5.9', '12139,12140,12137,12138', 'KBpython2,KBpython3', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (473, 'CVE-2023-40548', '7.4', '12356,12357,12139,12140', 'KBshim-unsigned-aarch64,KBshim,KBshim-unsigned-x64', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (474, 'CVE-2023-40550', '5.5', '12356,12139,12140,12357', 'KBshim-unsigned-aarch64,KBshim,KBshim-unsigned-x64', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (475, 'CVE-2020-27840', '7.5', '12356,12357', 'KBsamba', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (476, 'CVE-2024-5288', '5.9', '12356,12357,12139,12140', 'KBmariadb', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (477, 'CVE-2024-6104', '5.5', '12139,12140,12356,12357', 'KBpacker,KBkeda,KBlibcontainers-common,KBcert-manager,KBinfluxdb', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (478, 'CVE-2023-45288', '7.5', '12139,12140,12356,12357', 'KBazcopy,KBcert-manager,KBblobfuse2,KBapplication-gateway-kubernetes-ingress,KBcoredns', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (479, 'CVE-2019-20907', '7.5', '12139,12140,12137,12138', 'KBpython2,KBpython3', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (480, 'CVE-2019-9674', '7.5', '12139,12140,12137,12138', 'KBpython2', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (481, 'CVE-2012-2677', '0', '12356,12357', 'KBmysql', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (482, 'CVE-2012-6708', '6.1', '12356,12357', 'KBslf4j', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (483, 'CVE-2024-52338', '9.8', '12356,12357', 'KBlibarrow', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (484, 'CVE-2024-34062', '4.8', '12139,12140,12356,12357', 'KBpython-tqdm', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (485, 'CVE-2024-32020', '3.9', '12139,12140,12356,12357', 'KBgit', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (486, 'CVE-2024-7383', '7.4', '12356,12357,12139,12140', 'KBlibnbd', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (487, 'CVE-2022-40898', '7.5', '12356,12357,12139,12140', 'KBpython-wheel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (488, 'CVE-2019-14584', '7.8', '12356,12357', 'KBshim-unsigned-aarch64', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (489, 'CVE-2022-32746', '5.4', '12356,12357', 'KBsamba', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (490, 'CVE-2021-20277', '7.5', '12356,12357', 'KBsamba', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (491, 'CVE-2019-3816', '7.5', '12139,12140,12356,12357', 'KBopenwsman', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (492, 'CVE-2025-26466', '5.9', '12356,12357', 'KBopenssh', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (493, 'CVE-2024-50608', '7.5', '12356,12139,12140,12357', 'KBfluent-bit', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (494, 'CVE-2025-26618', '0', '12356,12139,12140,12357', 'KBerlang', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (495, 'CVE-2024-1454', '3.4', '12356,12357,12139,12140', 'KBopensc', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (496, 'CVE-2024-9681', '6.5', '12356,12357,12139,12140', 'KBcurl,KBmysql,KBcmake,KBrust', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (497, 'CVE-2025-27144', '0', '12356,12139,12140,12357', 'KBgh,KBcontainerized-data-importer,KBcert-manager,KBinfluxdb,KBdcos-cli', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (498, 'CVE-2024-50609', '7.5', '12356,12357,12139,12140', 'KBfluent-bit', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (499, 'CVE-2023-52917', '0', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (500, 'CVE-2021-36373', '5.5', '12137,12138,12356,12357,12139,12140', 'KBjavapackages-bootstrap,KBant', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (501, 'CVE-2021-36374', '5.5', '12137,12138,12356,12357,12139,12140', 'KBjavapackages-bootstrap,KBant', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (502, 'CVE-2023-40660', '6.6', '12356,12357,12139,12140', 'KBopensc', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (503, 'CVE-2024-50181', '0', '12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (504, 'CVE-2024-56741', '0', '12357,12139,12356,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (505, 'CVE-2024-39908', '4.3', '12356,12357,12139,12140', 'KBruby,KBrubygem-rexml', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (506, 'CVE-2025-0840', '7.5', '12139,12140,12356,12357', 'KBbinutils', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (507, 'CVE-2024-26973', '5.5', '12139,12140,12356,12357', 'KBhyperv-daemons', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (508, 'CVE-2024-53150', '7.1', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (509, 'CVE-2024-53151', '5.5', '12139,12356,12357,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (510, 'CVE-2024-53215', '5.5', '12140,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (511, 'CVE-2024-53180', '5.5', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (512, 'CVE-2024-56568', '4.7', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (513, 'CVE-2024-56567', '5.5', '12357,12139,12356,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (514, 'CVE-2024-56598', '7.8', '12140,12139,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (515, 'CVE-2024-56634', '5.5', '12356,12139,12140,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (516, 'CVE-2024-56640', '7.8', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (517, 'CVE-2024-56720', '5.5', '12357,12139,12356,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (518, 'CVE-2024-56754', '5.5', '12357,12139,12140,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (519, 'CVE-2024-57802', '5.5', '12140,12356,12357,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (520, 'CVE-2024-57807', '5.5', '12140,12356,12357,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (521, 'CVE-2024-57900', '7.8', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (522, 'CVE-2024-57938', '5.5', '12140,12356,12139,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (523, 'CVE-2024-45828', '5.5', '12357,12139,12140,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (524, 'CVE-2024-47143', '5.5', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (525, 'CVE-2024-53161', '5.5', '12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (526, 'CVE-2024-53155', '7.1', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (527, 'CVE-2024-55916', '5.5', '12139,12357,12140,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (528, 'CVE-2024-56548', '7.8', '12140,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (529, 'CVE-2024-56600', '7.8', '12356,12139,12140,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (530, 'CVE-2024-56626', '7.8', '12140,12356,12139,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (531, 'CVE-2024-56627', '7.1', '12356,12139,12140,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (532, 'CVE-2024-56662', '7.1', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (533, 'CVE-2024-56659', '5.5', '12356,12139,12140,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (534, 'CVE-2024-56724', '5.5', '12357,12140,12356,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (535, 'CVE-2024-56728', '5.5', '12356,12357,12140,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (536, 'CVE-2024-56774', '5.5', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (537, 'CVE-2024-56777', '5.5', '12139,12356,12357,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (538, 'CVE-2024-57901', '5.5', '12139,12356,12140,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (539, 'CVE-2025-26596', '7.8', '12357,12139,12140,12356', 'KBxorg-x11-server-Xwayland,KBxorg-x11-server', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (540, 'CVE-2025-26594', '7.8', '12139,12140,12357,12356', 'KBxorg-x11-server-Xwayland,KBxorg-x11-server', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (541, 'CVE-2025-26595', '7.8', '12139,12140,12357,12356', 'KBxorg-x11-server-Xwayland,KBxorg-x11-server', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (542, 'CVE-2025-26598', '7.8', '12139,12357,12140,12356', 'KBxorg-x11-server-Xwayland,KBxorg-x11-server', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (543, 'CVE-2025-22134', '4.2', '12356,12139,12140,12357', 'KBvim', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (544, 'CVE-2022-41862', '3.7', '12139,12140', 'KBpostgresql', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (545, 'CVE-2024-53859', '6.5', '12356,12357', 'KBgh', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (546, 'CVE-2024-36620', '6.5', '12356,12357', 'KBmoby-engine', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (547, 'CVE-2024-10963', '7.4', '12356,12357', 'KBpam', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (548, 'CVE-2023-40551', '5.1', '12356,12139,12140,12357', 'KBshim', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (549, 'CVE-2024-56657', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (550, 'CVE-2024-56763', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (551, 'CVE-2024-57892', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (552, 'CVE-2024-57980', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (553, 'CVE-2025-21637', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (554, 'CVE-2025-21718', '7.0', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (555, 'CVE-2024-3651', '7.5', '12356,12357,12139,12140', 'KBpython-pip,KBtensorflow,KBpython3,KBpython-idna', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (556, 'CVE-2015-2158', '7.8', '12356,12357', 'KBfltk,KBteckit', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (557, 'CVE-2020-14152', '7.1', '12356,12357', 'KBfltk', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (558, 'CVE-2024-56658', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (559, 'CVE-2024-56710', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (560, 'CVE-2024-57912', '7.1', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (561, 'CVE-2024-57978', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (562, 'CVE-2025-21638', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (563, 'CVE-2025-21680', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (564, 'CVE-2025-21716', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (565, 'CVE-2024-29195', '6.0', '12139,12356,12357,12140', 'KBazure-iot-sdk-c', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (566, 'CVE-2022-3162', '6.5', '12139,12140,12356,12357', 'KBkube-vip-cloud-provider,KBprometheus-adapter,KBkeda,KBcert-manager', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (567, 'CVE-2025-27219', '7.5', '12140,12139', 'KBruby', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (568, 'CVE-2025-27221', '5.3', '12139,12140', 'KBruby', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (569, 'CVE-2025-27220', '7.5', '12139,12140', 'KBruby', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (570, 'CVE-2024-1013', '7.8', '12139,12140,12356,12357', 'KBunixODBC', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (571, 'CVE-2024-8176', '7.5', '12139,12140', 'KBexpat', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (572, 'CVE-2024-8096', '6.5', '12357,12140,12356,12139', 'KBcurl,KBmysql,KBcmake', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (573, 'CVE-2025-22870', '4.4', '12139,12140,12356,12357', 'KBpacker,KBtelegraf,KBinfluxdb,KBvitess,KBazcopy', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (574, 'CVE-2024-33600', '5.9', '12139,12140', 'KBglibc', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (575, 'CVE-2024-33599', '8.1', '12139,12140', 'KBglibc', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (576, 'CVE-2025-21490', '4.9', '12139,12140', 'KBmysql,KBmariadb', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (577, 'CVE-2024-34397', '5.2', '12357,12356', 'KBglib', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (578, 'CVE-2024-43806', '6.5', '12139,12357,12140,12356', 'KBvirtiofsd,KBkata-containers,KBkata-containers-cc,KBrust', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (579, 'CVE-2023-29942', '5.5', '12139,12140', 'KBclang16', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (580, 'CVE-2025-29786', '7.5', '12356,12357', 'KBig,KBcoredns,KBkeda', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (581, 'CVE-2023-29935', '5.5', '12139,12140', 'KBclang16', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (582, 'CVE-2024-2511', '5.9', '12139,12140,12356,12357', 'KBcloud-hypervisor-cvm,KBnodejs18,KBopenssl,KBnodejs,KBhvloader', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (583, 'CVE-2025-30348', '5.3', '12356,12357', 'KBqtbase', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (584, 'CVE-2022-45142', '7.5', '12356,12357,12139,12140,12137,12138', 'KBheimdal', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (585, 'CVE-2024-31580', '4.0', '12139,12140', 'KBpytorch', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (586, 'CVE-2024-52006', '0', '12356,12357,12139,12140', 'KBgit', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (587, 'CVE-2024-50349', '0', '12357,12139,12140,12356', 'KBgit', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (588, 'CVE-2022-28737', '7.8', '12356,12139,12140,12357', 'KBshim-unsigned-aarch64,KBshim', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (589, 'CVE-2024-3727', '8.3', '12356,12357,12139,12140', 'KBcontainerized-data-importer,KBlibcontainers-common,KBig,KBskopeo,KBcri-o', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (590, 'CVE-2022-32149', '7.5', '12139,12140,12356,12357', 'KBgh,KBcni,KBcontainerized-data-importer,KBkeda,KBkubevirt,KBapplication-gateway-kubernetes-ingress,KBmultus,KBcf-cli', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (591, 'CVE-2024-45338', '5.3', '12140,12357,12139,12356', 'KBcni,KBcert-manager,KBapplication-gateway-kubernetes-ingress,KBcni-plugins,KBcf-cli', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (592, 'CVE-2017-18207', '6.5', '12139,12140,12137,12138', 'KBpython2', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (593, 'CVE-2017-17522', '8.8', '12139,12140,12137,12138', 'KBpython2', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (594, 'CVE-2007-4559', '9.8', '12139,12140,12137,12138', 'KBpython2,KBpython3', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (595, 'CVE-2025-1176', '5.0', '12139,12140,12357,12356', 'KBbinutils,KBgdb', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (596, 'CVE-2025-1182', '5.0', '12139,12140,12356,12357', 'KBbinutils,KBgdb', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (597, 'CVE-2025-1181', '5.0', '12139,12356,12357,12140', 'KBbinutils', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (598, 'CVE-2025-1178', '5.6', '12139,12356,12357,12140', 'KBbinutils', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (599, 'CVE-2015-9251', '6.1', '12356,12357', 'KBslf4j', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (600, 'CVE-2020-22217', '5.9', '12139,12140,12356,12357', 'KBpython-gevent,KBgrpc', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (601, 'CVE-2021-24032', '4.7', '12357,12137,12138,12139,12140,12356', 'KBceph,KBzstd', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (602, 'CVE-2024-25629', '5.5', '12139,12140,12356,12357', 'KBgrpc,KBfluent-bit,KBnodejs18,KBc-ares,KBpython-gevent,KBnodejs', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (603, 'CVE-2024-32465', '7.3', '12139,12140,12356,12357', 'KBgit', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (604, 'CVE-2021-20286', '2.7', '12139,12140,12356,12357', 'KBlibnbd', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (605, 'CVE-2024-32021', '3.9', '12139,12140,12356,12357', 'KBgit', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (606, 'CVE-2023-40549', '5.5', '12357,12139,12140,12356', 'KBshim-unsigned-aarch64,KBshim,KBshim-unsigned-x64', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (607, 'CVE-2023-40546', '5.5', '12357,12139,12140,12356', 'KBshim-unsigned-aarch64,KBshim,KBshim-unsigned-x64', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (608, 'CVE-2024-37535', '4.4', '12139,12140,12356,12357', 'KBvte291', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (609, 'CVE-2023-1393', '7.8', '12139,12140,12356,12357', 'KBxorg-x11-server', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (610, 'CVE-2019-3833', '7.5', '12139,12140,12356,12357', 'KBopenwsman', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (611, 'CVE-2024-12133', '5.3', '12356,12357,12139,12140', 'KBgnutls,KBlibtasn1', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (612, 'CVE-2024-26943', '5.5', '12356,12357', 'KBhyperv-daemons', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (613, 'CVE-2024-27026', '5.5', '12356,12357', 'KBhyperv-daemons', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (614, 'CVE-2024-35790', '5.5', '12356,12357', 'KBhyperv-daemons', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (615, 'CVE-2024-35792', '7.8', '12356,12357', 'KBhyperv-daemons', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (616, 'CVE-2024-27061', '7.8', '12356,12357', 'KBhyperv-daemons', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (617, 'CVE-2024-40982', '0', '12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (618, 'CVE-2024-43098', '5.5', '12140,12139,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (619, 'CVE-2024-53146', '5.5', '12139,12356,12357,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (620, 'CVE-2025-22868', '7.5', '12356,12357,12139,12140', 'KBcontainerized-data-importer,KBazcopy,KBcert-manager,KBblobfuse2,KBcoredns', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (621, 'CVE-2024-53157', '5.5', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (622, 'CVE-2024-53156', '7.8', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (623, 'CVE-2024-53165', '7.8', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (624, 'CVE-2024-53171', '7.8', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (625, 'CVE-2024-56369', '5.5', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (626, 'CVE-2024-53227', '7.8', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (627, 'CVE-2024-53237', '7.8', '12140,12356,12357,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (628, 'CVE-2024-53217', '5.5', '12140,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (629, 'CVE-2024-56569', '5.5', '12140,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (630, 'CVE-2024-56572', '5.5', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (631, 'CVE-2024-56578', '5.5', '12139,12356,12357,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (632, 'CVE-2024-56574', '5.5', '12356,12357,12140,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (633, 'CVE-2024-56593', '5.5', '12140,12139,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (634, 'CVE-2024-56622', '5.5', '12140,12139,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (635, 'CVE-2024-56606', '7.8', '12357,12139,12140,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (636, 'CVE-2024-56601', '7.8', '12139,12357,12140,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (637, 'CVE-2024-56602', '7.8', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (638, 'CVE-2024-56698', '5.5', '12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (639, 'CVE-2024-56704', '7.8', '12357,12139,12140,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (640, 'CVE-2024-56715', '5.5', '12139,12356,12357,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (641, 'CVE-2024-56642', '7.8', '12356,12139,12140,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (642, 'CVE-2024-56716', '5.5', '12140,12356,12357,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (643, 'CVE-2024-56726', '5.5', '12140,12356,12357,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (644, 'CVE-2024-56746', '5.5', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (645, 'CVE-2024-56723', '5.5', '12357,12139,12356,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (646, 'CVE-2024-56739', '5.5', '12357,12140,12356,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (647, 'CVE-2024-56748', '5.5', '12357,12356,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (648, 'CVE-2024-56776', '5.5', '12357,12139,12356,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (649, 'CVE-2024-56779', '5.5', '12356,12139,12140,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (650, 'CVE-2024-56778', '5.5', '12357,12139,12356,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (651, 'CVE-2024-56780', '5.5', '12356,12139,12357,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (652, 'CVE-2024-56785', '5.5', '12139,12356,12140,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (653, 'CVE-2024-57874', '6.1', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (654, 'CVE-2024-57896', '7.8', '12140,12356,12139,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (655, 'CVE-2024-57841', '5.5', '12140,12356,12357,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (656, 'CVE-2024-57890', '5.5', '12140,12356,12139,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (657, 'CVE-2024-36476', '5.5', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (658, 'CVE-2024-57946', '5.5', '12139,12356,12140,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (659, 'CVE-2024-9287', '7.8', '12139,12140,12356,12357', 'KBpython3', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (660, 'CVE-2025-22869', '7.5', '12139,12356,12140,12357', 'KBdocker-compose,KBcert-manager,KBdocker-buildx,KBkubevirt,KBkubernetes,KBmoby-compose,KBcf-cli', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (661, 'CVE-2024-50051', '7.8', '12139,12140,12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (662, 'CVE-2024-48881', '5.5', '12140,12357,12139,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (663, 'CVE-2024-53145', '5.5', '12140,12356,12357,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (664, 'CVE-2024-53096', '7.8', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (665, 'CVE-2024-53206', '7.8', '12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (666, 'CVE-2024-53239', '7.8', '12140,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (667, 'CVE-2024-53173', '7.8', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (668, 'CVE-2024-53226', '5.5', '12357,12139,12356,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (669, 'CVE-2024-56575', '5.5', '12140,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (670, 'CVE-2024-56581', '7.8', '12140,12356,12357,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (671, 'CVE-2024-56595', '7.8', '12139,12357,12140,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (672, 'CVE-2024-56587', '5.5', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (673, 'CVE-2024-56596', '7.8', '12139,12356,12357,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (674, 'CVE-2024-56614', '7.8', '12140,12139,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (675, 'CVE-2024-56605', '7.8', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (676, 'CVE-2024-56615', '7.8', '12139,12356,12140,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (677, 'CVE-2024-56603', '7.8', '12140,12356,12357,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (678, 'CVE-2024-56623', '5.5', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (679, 'CVE-2024-56643', '5.5', '12139,12356,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (680, 'CVE-2024-56650', '7.1', '12139,12357,12140,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (681, 'CVE-2024-56648', '5.5', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (682, 'CVE-2024-56629', '5.5', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (683, 'CVE-2024-56670', '5.5', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (684, 'CVE-2024-56694', '5.5', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (685, 'CVE-2024-56688', '5.5', '12140,12356,12357,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (686, 'CVE-2024-56708', '7.8', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (687, 'CVE-2024-56747', '5.5', '12356,12357,12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (688, 'CVE-2024-56745', '5.5', '12357,12139,12140,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (689, 'CVE-2024-56756', '5.5', '12140,12356,12357,12139', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (690, 'CVE-2024-56770', '5.5', '12139,12357,12140,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (691, 'CVE-2024-56781', '5.5', '12139,12140,12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (692, 'CVE-2024-56787', '5.5', '12139,12357,12140,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (693, 'CVE-2024-57850', '7.8', '12139,12140,12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (694, 'CVE-2024-57902', '5.5', '12139,12140,12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (695, 'CVE-2024-50302', '5.5', '12139,12140,12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (696, 'CVE-2025-27516', '0', '12140,12356,12139,12357', 'KBpython-jinja2,KBnodejs18', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (697, 'CVE-2025-26600', '7.8', '12356,12139,12140,12357', 'KBxorg-x11-server-Xwayland,KBxorg-x11-server', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (698, 'CVE-2025-26597', '7.8', '12139,12140,12356,12357', 'KBxorg-x11-server-Xwayland,KBxorg-x11-server', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (699, 'CVE-2025-1744', '0', '12139,12357,12140,12356', 'KBcloud-hypervisor,KBceph,KBbinutils', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (700, 'CVE-2025-27423', '7.1', '12139,12140,12357,12356', 'KBvim', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (701, 'CVE-2025-26599', '7.8', '12140,12356,12357,12139', 'KBxorg-x11-server-Xwayland,KBxorg-x11-server', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (702, 'CVE-2025-26601', '7.8', '12356,12139,12140,12357', 'KBxorg-x11-server-Xwayland,KBxorg-x11-server', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (703, 'CVE-2024-43802', '4.5', '12356,12357,12139,12140', 'KBvim', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (704, 'CVE-2024-45337', '9.1', '12357,12139,12140,12356', 'KBpacker,KBdocker-compose,KBcert-manager,KBdocker-buildx,KBmoby-engine,KBmoby-compose,KBcf-cli', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (705, 'CVE-2024-36621', '6.5', '12139,12140,12356,12357', 'KBmoby-engine', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (706, 'CVE-2024-36623', '8.1', '12356,12357,12139,12140', 'KBdocker-cli,KBmoby-cli,KBmoby-engine,KBmoby-compose', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (707, 'CVE-2023-6121', '4.3', '12139,12140', 'KBhyperv-daemons', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (708, 'CVE-2023-6817', '7.8', '12139,12140', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (709, 'CVE-2023-2455', '5.4', '12139,12140', 'KBpostgresql', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (710, 'CVE-2025-0665', '9.8', '12356,12357', 'KBcurl', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (711, 'CVE-2025-0167', '3.4', '12356,12357', 'KBcurl', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (712, 'CVE-2024-11053', '3.4', '12139,12140,12356,12357', 'KBcurl,KBmysql,KBcmake', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (713, 'CVE-2025-0633', '0', '12357,12356', 'KBiniparser', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (714, 'CVE-2023-48795', '5.9', '12139,12140,12356,12357', 'KBlibssh,KBcert-manager,KBdocker-buildx,KBerlang,KBkubernetes,KBcf-cli', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (715, 'CVE-2024-6874', '4.3', '12357,12356', 'KBcurl,KBcmake', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (716, 'CVE-2024-47141', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (717, 'CVE-2024-48875', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (718, 'CVE-2024-54680', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (719, 'CVE-2024-56582', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (720, 'CVE-2024-56613', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (721, 'CVE-2024-56611', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (722, 'CVE-2024-56617', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (723, 'CVE-2024-56649', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (724, 'CVE-2024-56653', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (725, 'CVE-2024-56654', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (726, 'CVE-2024-56655', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (727, 'CVE-2024-56663', '7.1', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (728, 'CVE-2024-56667', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (729, 'CVE-2024-56703', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (730, 'CVE-2024-56718', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (731, 'CVE-2024-56719', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (732, 'CVE-2024-56767', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (733, 'CVE-2024-56769', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (734, 'CVE-2024-57801', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (735, 'CVE-2024-57882', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (736, 'CVE-2024-57887', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (737, 'CVE-2024-57908', '7.1', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (738, 'CVE-2024-57922', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (739, 'CVE-2024-57910', '7.1', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (740, 'CVE-2024-57916', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (741, 'CVE-2024-57925', '7.1', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (742, 'CVE-2024-57933', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (743, 'CVE-2024-57926', '7.8', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (744, 'CVE-2024-57939', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (745, 'CVE-2024-57973', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (746, 'CVE-2024-57981', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (747, 'CVE-2024-57996', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (748, 'CVE-2024-58007', '7.1', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (749, 'CVE-2024-58017', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (750, 'CVE-2024-58011', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (751, 'CVE-2025-21640', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (752, 'CVE-2025-21642', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (753, 'CVE-2025-21652', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (754, 'CVE-2025-21667', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (755, 'CVE-2025-21658', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (756, 'CVE-2025-21669', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (757, 'CVE-2025-21670', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (758, 'CVE-2025-21674', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (759, 'CVE-2025-21675', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (760, 'CVE-2025-21681', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (761, 'CVE-2025-21683', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (762, 'CVE-2025-21692', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (763, 'CVE-2025-21697', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (764, 'CVE-2025-21694', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (765, 'CVE-2025-21707', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (766, 'CVE-2025-21711', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (767, 'CVE-2025-21743', '7.1', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (768, 'CVE-2025-21748', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (769, 'CVE-2025-21749', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (770, 'CVE-2025-21820', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (771, 'CVE-2025-0426', '6.2', '12357,12356', 'KBkubernetes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (772, 'CVE-2020-13435', '5.5', '12356,12357', 'KBceph,KBlibdb', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (773, 'CVE-2024-56171', '7.8', '12139,12356,12140,12357', 'KBlibxml2', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (774, 'CVE-2025-24928', '7.8', '12139,12140,12356,12357', 'KBlibxml2', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (775, 'CVE-2025-27113', '7.5', '12139,12140,12356,12357', 'KBlibxml2', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (776, 'CVE-2024-13176', '4.1', '12356,12357', 'KBopenssl', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (777, 'CVE-2024-28757', '7.5', '12356,12357,12139,12140', 'KBpython3,KBexpat', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (778, 'CVE-2023-45803', '4.2', '12139,12140,12356,12357', 'KBpython-pip,KBpython-urllib3,KBpython3', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (779, 'CVE-2024-28180', '4.3', '12139,12140,12356,12357', 'KBcontainerized-data-importer,KBcert-manager,KBcontainerd,KBcri-o,KBdcos-cli', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (780, 'CVE-2024-3219', '0', '12356,12357', 'KBpython3', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (781, 'CVE-2024-4032', '7.5', '12356,12139,12140,12357', 'KBpython3', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (782, 'CVE-2024-45490', '7.5', '12356,12357,12139,12140', 'KBpython3,KBexpat', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (783, 'CVE-2024-50602', '5.9', '12139,12140,12356,12357', 'KBpython3,KBexpat', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (784, 'CVE-2024-45492', '9.8', '12356,12357,12139,12140', 'KBpython3,KBexpat', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (785, 'CVE-2025-0938', '0', '12357,12139,12140,12356', 'KBpython3', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (786, 'CVE-2025-1215', '2.8', '12139,12357,12140,12356', 'KBvim', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (787, 'CVE-2025-26603', '4.2', '12356,12357,12139,12140', 'KBvim', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (788, 'CVE-2019-25219', '7.5', '12356,12357', 'KBasio', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (789, 'CVE-2023-52160', '6.5', '12139,12140,12356,12357', 'KBwpa_supplicant', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (790, 'CVE-2024-24826', '5.0', '12356,12357', 'KBexiv2', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (791, 'CVE-2024-25112', '5.0', '12356,12357', 'KBexiv2', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (792, 'CVE-2016-10087', '7.5', '12356,12357', 'KBfltk', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (793, 'CVE-2016-9840', '8.8', '12356,12357', 'KBfltk', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (794, 'CVE-2016-9841', '9.8', '12357,12356', 'KBfltk', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (795, 'CVE-2016-9842', '8.8', '12356,12357', 'KBfltk', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (796, 'CVE-2016-9843', '9.8', '12356,12357', 'KBfltk', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (797, 'CVE-2023-6992', '5.5', '12356,12357', 'KBfltk,KBteckit', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (798, 'CVE-2023-25564', '8.2', '12356,12357', 'KBgssntlmssp', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (799, 'CVE-2023-25567', '7.5', '12357,12356', 'KBgssntlmssp', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (800, 'CVE-2023-25566', '7.5', '12356,12357', 'KBgssntlmssp', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (801, 'CVE-2004-2779', '7.5', '12357,12356', 'KBlibid3tag', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (802, 'CVE-2024-36039', '6.3', '12357,12356', 'KBpython-PyMySQL', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (803, 'CVE-2024-49769', '7.5', '12356,12357', 'KBpython-waitress', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (804, 'CVE-2024-34403', '5.9', '12356,12357', 'KBuriparser', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (805, 'CVE-2024-51744', '3.1', '12356,12357,12139,12140', 'KBcoredns,KBpacker,KBcert-manager,KBinfluxdb,KBapplication-gateway-kubernetes-ingress,KBetcd,KBazcopy', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (806, 'CVE-2024-7264', '6.5', '12139,12140,12356,12357', 'KBcurl,KBmysql,KBcmake', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (807, 'CVE-2023-3978', '6.1', '12139,12140,12356,12357', 'KBcontainerized-data-importer,KBcert-manager,KBkubevirt,KBapplication-gateway-kubernetes-ingress,KBmultus,KBcni-plugins', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (808, 'CVE-2025-0725', '7.3', '12139,12140,12356,12357', 'KBcurl,KBmysql', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (809, 'CVE-2024-10846', '5.9', '12356,12357', 'KBdocker-compose', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (810, 'CVE-2024-47809', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (811, 'CVE-2025-25204', '6.3', '12356,12357', 'KBgh', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (812, 'CVE-2024-48873', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (813, 'CVE-2024-53179', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (814, 'CVE-2024-54683', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (815, 'CVE-2024-56631', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (816, 'CVE-2024-56635', '7.0', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (817, 'CVE-2024-56604', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (818, 'CVE-2024-56651', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (819, 'CVE-2024-56660', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (820, 'CVE-2024-56665', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (821, 'CVE-2024-56664', '7.0', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (822, 'CVE-2024-56672', '7.0', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (823, 'CVE-2024-56675', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (824, 'CVE-2024-56717', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (825, 'CVE-2024-56760', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (826, 'CVE-2024-56783', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (827, 'CVE-2024-56765', '7.8', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (828, 'CVE-2024-56766', '7.8', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (829, 'CVE-2024-56786', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (830, 'CVE-2024-57798', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (831, 'CVE-2024-57895', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (832, 'CVE-2024-57906', '7.1', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (833, 'CVE-2024-57907', '7.1', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (834, 'CVE-2024-57911', '7.1', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (835, 'CVE-2024-57913', '4.7', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (836, 'CVE-2024-57940', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (837, 'CVE-2024-57949', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (838, 'CVE-2024-57951', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (839, 'CVE-2024-57997', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (840, 'CVE-2024-58005', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (841, 'CVE-2024-58010', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (842, 'CVE-2025-21636', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (843, 'CVE-2025-21631', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (844, 'CVE-2025-21639', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (845, 'CVE-2025-21666', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (846, 'CVE-2025-21665', '5.5', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (847, 'CVE-2025-21673', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (848, 'CVE-2025-21676', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (849, 'CVE-2025-21684', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (850, 'CVE-2025-21689', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (851, 'CVE-2025-21687', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (852, 'CVE-2025-21690', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (853, 'CVE-2025-21699', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (854, 'CVE-2025-21735', '7.8', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (855, 'CVE-2025-21736', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (856, 'CVE-2025-21741', '7.1', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (857, 'CVE-2025-21742', '7.1', '12357,12356', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (858, 'CVE-2025-21744', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (859, 'CVE-2025-21745', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (860, 'CVE-2025-21814', '5.5', '12356,12357', 'KBkernel', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (861, 'CVE-2025-1390', '6.1', '12139,12140,12356,12357', 'KBlibcap', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (862, 'CVE-2024-25062', '7.5', '12356,12357,12139,12140', 'KBlibxml2', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (863, 'CVE-2025-23359', '8.3', '12140,12356,12357,12139', 'KBnvidia-container-toolkit', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (864, 'CVE-2024-12797', '6.3', '12356,12357,12139,12140', 'KBopenssl,KBcloud-hypervisor-cvm', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (865, 'CVE-2024-9143', '4.3', '12140,12357,12139,12356', 'KBopenssl,KBhvloader', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (866, 'CVE-2024-37891', '4.4', '12139,12140,12356,12357', 'KBpython-pip,KBpython-urllib3,KBpython3', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (867, 'CVE-2024-4030', '7.1', '12357,12356', 'KBpython3', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (868, 'CVE-2024-45491', '9.8', '12356,12357,12139,12140', 'KBpython3,KBexpat', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (869, 'CVE-2023-44398', '8.8', '12356,12357', 'KBexiv2', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (870, 'CVE-2024-39695', '6.5', '12356,12357', 'KBexiv2', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (871, 'CVE-2015-8126', '0', '12356,12357', 'KBfltk', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (872, 'CVE-2015-8472', '7.3', '12356,12357', 'KBfltk', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (873, 'CVE-2017-12652', '9.8', '12357,12356', 'KBfltk', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (874, 'CVE-2023-25563', '7.5', '12357,12356', 'KBgssntlmssp', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (875, 'CVE-2019-7317', '5.3', '12356,12357', 'KBfltk', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (876, 'CVE-2023-25565', '7.5', '12356,12357', 'KBgssntlmssp', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (877, 'CVE-2023-40305', '5.5', '12356,12357', 'KBindent', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (878, 'CVE-2017-11550', '5.5', '12356,12357', 'KBlibid3tag', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (879, 'CVE-2017-11551', '5.5', '12356,12357', 'KBlibid3tag', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (880, 'CVE-2018-7263', '9.8', '12356,12357', 'KBlibmad', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (881, 'CVE-2023-39976', '9.8', '12356,12357', 'KBlibqb', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (882, 'CVE-2023-35789', '5.5', '12357,12356', 'KBlibrabbitmq', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (883, 'CVE-2022-43357', '7.5', '12356,12357', 'KBlibsass', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (884, 'CVE-2022-26592', '8.8', '12356,12139,12140,12357', 'KBlibsass,KBreaper', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (885, 'CVE-2022-43358', '7.5', '12356,12357', 'KBlibsass', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (886, 'CVE-2023-28370', '6.1', '12356,12357', 'KBpython-tornado', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (887, 'CVE-2022-24761', '7.5', '12357,12356', 'KBpython-waitress', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (888, 'CVE-2022-31015', '5.9', '12356,12357', 'KBpython-waitress', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (889, 'CVE-2018-25032', '7.5', '12137,12138,12139,12140,12356,12357', 'KBcloud-hypervisor-cvm,KBgrpc,KBqt5-qtbase,KBnmap,KBteckit,KBmariadb,KBerlang,KBcmake,KBboost', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (890, 'CVE-2022-37434', '9.8', '12137,12138,12139,12140,12356,12357', 'KBzlib,KBcloud-hypervisor-cvm,KBcrash,KBteckit', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (891, 'CVE-2024-34402', '8.6', '12356,12357', 'KBuriparser', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (892, 'CVE-2024-30161', '6.5', '12356,12357', 'KBqtbase', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (893, 'CVE-2023-35945', '7.5', '12139,12140,12356,12357', 'KBnodejs18,KBnodejs,KBnghttp2,KBcmake', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (894, 'CVE-2024-53899', '7.8', '12356,12357,12139,12140', 'KBpython-virtualenv', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (895, 'CVE-2025-27363', '8.1', '12139,12140', 'KBfreetype', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (896, 'CVE-2024-26651', '5.5', '12356,12357', 'KBhyperv-daemons', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (897, 'CVE-2025-1550', '0', '12356,12357', 'KBkeras', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (898, 'CVE-2024-33601', '7.3', '12139,12140', 'KBglibc', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (899, 'CVE-2024-53427', '8.1', '12356,12357', 'KBjq', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (900, 'CVE-2023-29933', '5.5', '12139,12140', 'KBclang16,KBllvm16', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (901, 'CVE-2025-25724', '4.0', '12139,12140', 'KBlibarchive', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (902, 'CVE-2024-25580', '6.2', '12356,12357,12139,12140', 'KBqt5-qtbase,KBqtbase', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (903, 'CVE-2023-29941', '5.5', '12139,12356,12357,12140', 'KBtensorflow,KBclang16,KBllvm16', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (904, 'CVE-2024-46901', '3.1', '12139,12140', 'KBsubversion', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (905, 'CVE-2024-53257', '4.9', '12140,12139', 'KBvitess', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (906, 'CVE-2023-29932', '5.5', '12139,12140', 'KBllvm,KBrust', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (907, 'CVE-2023-34410', '5.3', '12140,12139', 'KBqt5-qtbase', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (908, 'CVE-2023-31147', '6.5', '12139,12140,12137,12138,12356,12357', 'KBfluent-bit,KBnodejs,KBgrpc,KBc-ares', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (909, 'CVE-2024-53920', '7.8', '12356,12357,12139,12140', 'KBemacs', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (910, 'CVE-2024-55549', '7.8', '12139,12140', 'KBlibxslt', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (911, 'CVE-2024-12243', '5.3', '12139,12140', 'KBgnutls', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (912, 'CVE-2025-24855', '7.8', '12139,12140', 'KBlibxslt', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (913, 'CVE-2024-28863', '6.5', '12356,12357,12139,12140', 'KBreaper,KBnodejs,KBnodejs18', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (914, 'CVE-2025-29768', '4.4', '12356,12357,12139,12140', 'KBvim', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (915, 'CVE-2023-6683', '6.5', '12139,12140', 'KBqemu', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (916, 'CVE-2023-6693', '5.3', '12139,12140', 'KBqemu', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (917, 'CVE-2023-5088', '7.0', '12139,12140', 'KBqemu', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (918, 'CVE-2020-8565', '5.5', '12357,12137,12138,12356', 'KBlocal-path-provisioner,KBkubernetes', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (919, 'CVE-2023-6004', '4.8', '12357,12356', 'KBlibssh', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (920, 'CVE-2023-6918', '5.3', '12139,12140,12356,12357', 'KBlibssh', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (921, 'CVE-2023-46137', '5.3', '12356,12139,12140,12357', 'KBpython-twisted', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (922, 'CVE-2025-26629', '7.8', '11762,11763,12420,12421', '', '2025-Mar', '2025-06-12');
INSERT INTO `win_cve_db` VALUES (923, 'CVE-2025-24084', '8.4', '11923,11924,12085,12086,12437,12242,12243,12244,12389,12390,12436', 'KB5053598,KB5053599,KB5053603,KB5053636,KB5053602,KB5053638', '2025-Mar', '2025-06-12');

-- ----------------------------
-- Table structure for win_product_name
-- ----------------------------
DROP TABLE IF EXISTS `win_product_name`;
CREATE TABLE `win_product_name`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_id` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'product_id，不需要比较大小，所以set为字符串\r\n的格式',
  `product_name` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'product_id对应产品名',
  `dt` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据日期',
  PRIMARY KEY (`id`, `dt`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 292 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'product_id对应产品名' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of win_product_name
-- ----------------------------
INSERT INTO `win_product_name` VALUES (205, '10049', 'Windows Server 2008 R2 for x64-based Systems Service Pack 1 (Server Core installation)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (206, '10051', 'Windows Server 2008 R2 for x64-based Systems Service Pack 1', '2025-06-12');
INSERT INTO `win_product_name` VALUES (207, '10287', 'Windows Server 2008 for 32-bit Systems Service Pack 2 (Server Core installation)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (208, '10378', 'Windows Server 2012', '2025-06-12');
INSERT INTO `win_product_name` VALUES (209, '10379', 'Windows Server 2012 (Server Core installation)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (210, '10483', 'Windows Server 2012 R2', '2025-06-12');
INSERT INTO `win_product_name` VALUES (211, '10543', 'Windows Server 2012 R2 (Server Core installation)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (212, '10729', 'Windows 10 for 32-bit Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (213, '10735', 'Windows 10 for x64-based Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (214, '10739', 'Microsoft Excel 2016 (32-bit edition)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (215, '10740', 'Microsoft Excel 2016 (64-bit edition)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (216, '10746', 'Microsoft Word 2016 (32-bit edition)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (217, '10747', 'Microsoft Word 2016 (64-bit edition)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (218, '10751', 'Microsoft Access 2016 (32-bit edition)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (219, '10752', 'Microsoft Access 2016 (64-bit edition)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (220, '10753', 'Microsoft Office 2016 (32-bit edition)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (221, '10754', 'Microsoft Office 2016 (64-bit edition)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (222, '10816', 'Windows Server 2016', '2025-06-12');
INSERT INTO `win_product_name` VALUES (223, '10836', 'Office Online Server', '2025-06-12');
INSERT INTO `win_product_name` VALUES (224, '10852', 'Windows 10 Version 1607 for 32-bit Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (225, '10853', 'Windows 10 Version 1607 for x64-based Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (226, '10855', 'Windows Server 2016 (Server Core installation)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (227, '11568', 'Windows 10 Version 1809 for 32-bit Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (228, '11569', 'Windows 10 Version 1809 for x64-based Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (229, '11571', 'Windows Server 2019', '2025-06-12');
INSERT INTO `win_product_name` VALUES (230, '11572', 'Windows Server 2019 (Server Core installation)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (231, '11573', 'Microsoft Office 2019 for 32-bit editions', '2025-06-12');
INSERT INTO `win_product_name` VALUES (232, '11574', 'Microsoft Office 2019 for 64-bit editions', '2025-06-12');
INSERT INTO `win_product_name` VALUES (233, '11600', 'Microsoft Visual Studio 2017 version 15.9 (includes 15.0 - 15.8)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (234, '11622', 'Visual Studio Code', '2025-06-12');
INSERT INTO `win_product_name` VALUES (235, '11655', 'Microsoft Edge (Chromium-based)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (236, '11762', 'Microsoft 365 Apps for Enterprise for 32-bit Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (237, '11763', 'Microsoft 365 Apps for Enterprise for 64-bit Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (238, '11849', 'Remote Desktop client for Windows Desktop', '2025-06-12');
INSERT INTO `win_product_name` VALUES (239, '11870', 'Azure Kubernetes Service', '2025-06-12');
INSERT INTO `win_product_name` VALUES (240, '11923', 'Windows Server 2022', '2025-06-12');
INSERT INTO `win_product_name` VALUES (241, '11924', 'Windows Server 2022 (Server Core installation)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (242, '11929', 'Windows 10 Version 21H2 for 32-bit Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (243, '11930', 'Windows 10 Version 21H2 for ARM64-based Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (244, '11931', 'Windows 10 Version 21H2 for x64-based Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (245, '11935', 'Microsoft Visual Studio 2019 version 16.11 (includes 16.0 - 16.10)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (246, '11951', 'Microsoft Office LTSC for Mac 2021', '2025-06-12');
INSERT INTO `win_product_name` VALUES (247, '11952', 'Microsoft Office LTSC 2021 for 64-bit editions', '2025-06-12');
INSERT INTO `win_product_name` VALUES (248, '11953', 'Microsoft Office LTSC 2021 for 32-bit editions', '2025-06-12');
INSERT INTO `win_product_name` VALUES (249, '12068', 'Azure ARC', '2025-06-12');
INSERT INTO `win_product_name` VALUES (250, '12085', 'Windows 11 Version 22H2 for ARM64-based Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (251, '12086', 'Windows 11 Version 22H2 for x64-based Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (252, '12097', 'Windows 10 Version 22H2 for x64-based Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (253, '12098', 'Windows 10 Version 22H2 for ARM64-based Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (254, '12099', 'Windows 10 Version 22H2 for 32-bit Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (255, '12103', 'Azure CLI', '2025-06-12');
INSERT INTO `win_product_name` VALUES (256, '12137', 'CBL Mariner 1.0 x64', '2025-06-12');
INSERT INTO `win_product_name` VALUES (257, '12138', 'CBL Mariner 1.0 ARM', '2025-06-12');
INSERT INTO `win_product_name` VALUES (258, '12139', 'CBL Mariner 2.0 x64', '2025-06-12');
INSERT INTO `win_product_name` VALUES (259, '12140', 'CBL Mariner 2.0 ARM', '2025-06-12');
INSERT INTO `win_product_name` VALUES (260, '12242', 'Windows 11 Version 23H2 for ARM64-based Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (261, '12243', 'Windows 11 Version 23H2 for x64-based Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (262, '12244', 'Windows Server 2022, 23H2 Edition (Server Core installation)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (263, '12256', 'ASP.NET Core 8.0', '2025-06-12');
INSERT INTO `win_product_name` VALUES (264, '12271', 'Microsoft Visual Studio 2022 version 17.8', '2025-06-12');
INSERT INTO `win_product_name` VALUES (265, '12322', 'Microsoft Visual Studio 2022 version 17.10', '2025-06-12');
INSERT INTO `win_product_name` VALUES (266, '12338', 'Microsoft Dataverse', '2025-06-12');
INSERT INTO `win_product_name` VALUES (267, '12356', 'Azure Linux 3.0 x64', '2025-06-12');
INSERT INTO `win_product_name` VALUES (268, '12357', 'Azure Linux 3.0 ARM', '2025-06-12');
INSERT INTO `win_product_name` VALUES (269, '12389', 'Windows 11 Version 24H2 for ARM64-based Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (270, '12390', 'Windows 11 Version 24H2 for x64-based Systems', '2025-06-12');
INSERT INTO `win_product_name` VALUES (271, '12401', 'Azure Health Bot', '2025-06-12');
INSERT INTO `win_product_name` VALUES (272, '12420', 'Microsoft Office LTSC 2024 for 32-bit editions', '2025-06-12');
INSERT INTO `win_product_name` VALUES (273, '12421', 'Microsoft Office LTSC 2024 for 64-bit editions', '2025-06-12');
INSERT INTO `win_product_name` VALUES (274, '12429', 'Microsoft Partner Center', '2025-06-12');
INSERT INTO `win_product_name` VALUES (275, '12436', 'Windows Server 2025', '2025-06-12');
INSERT INTO `win_product_name` VALUES (276, '12437', 'Windows Server 2025 (Server Core installation)', '2025-06-12');
INSERT INTO `win_product_name` VALUES (277, '12440', 'Microsoft Office LTSC for Mac 2024', '2025-06-12');
INSERT INTO `win_product_name` VALUES (278, '12457', 'Windows App Client for Windows Desktop', '2025-06-12');
INSERT INTO `win_product_name` VALUES (279, '12459', 'Microsoft Visual Studio 2022 version 17.12', '2025-06-12');
INSERT INTO `win_product_name` VALUES (280, '12470', 'Microsoft Edge Update Setup', '2025-06-12');
INSERT INTO `win_product_name` VALUES (281, '12473', 'Azure Agent for Site Recovery', '2025-06-12');
INSERT INTO `win_product_name` VALUES (282, '12498', 'WinDbg', '2025-06-12');
INSERT INTO `win_product_name` VALUES (283, '12506', 'Microsoft Visual Studio 2022 version 17.13', '2025-06-12');
INSERT INTO `win_product_name` VALUES (284, '12507', 'ASP.NET Core 9.0', '2025-06-12');
INSERT INTO `win_product_name` VALUES (285, '12509', 'Azure promptflow-core', '2025-06-12');
INSERT INTO `win_product_name` VALUES (286, '12510', 'Azure promptflow-tools', '2025-06-12');
INSERT INTO `win_product_name` VALUES (287, '12511', 'Azure Agent for Backup', '2025-06-12');
INSERT INTO `win_product_name` VALUES (288, '12517', 'Azure Playwright', '2025-06-12');
INSERT INTO `win_product_name` VALUES (289, '9312', 'Windows Server 2008 for 32-bit Systems Service Pack 2', '2025-06-12');
INSERT INTO `win_product_name` VALUES (290, '9318', 'Windows Server 2008 for x64-based Systems Service Pack 2', '2025-06-12');
INSERT INTO `win_product_name` VALUES (291, '9344', 'Windows Server 2008 for x64-based Systems Service Pack 2 (Server Core installation)', '2025-06-12');

SET FOREIGN_KEY_CHECKS = 1;
