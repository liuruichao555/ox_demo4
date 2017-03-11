-- MySQL dump 10.13  Distrib 5.7.17, for osx10.12 (x86_64)
--
-- Host: localhost    Database: demo4
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cus_name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `cus_type` varchar(10) NOT NULL,
  `integral` int(11) NOT NULL DEFAULT '0',
  `real_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cus_name` (`cus_name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'xingshi','123','PERSONAL',0,'星矢'),(2,'zilong','123','PERSONAL',0,'紫龙'),(3,'binghe','123','PERSONAL',0,'冰河'),(4,'yihui','123','PERSONAL',0,'一辉'),(5,'shun','123','PERSONAL',0,'瞬'),(17,'bjdxgjyy','123','HOSPITAL',0,'北京大学国际医院'),(18,'fddxfshsyy','123','HOSPITAL',0,'复旦大学附属华山医院'),(19,'bjdxszyy','123','HOSPITAL',0,'深圳市人民医院'),(20,'yjjg','123','THIRDPARTY',0,'研究机构');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_record`
--

DROP TABLE IF EXISTS `medical_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medical_record` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `user_id` varchar(32) NOT NULL DEFAULT '',
  `outpatient_info` varchar(256) DEFAULT '' COMMENT '门诊信息',
  `diagnose_info` varchar(256) DEFAULT '' COMMENT 'diagnose',
  `medicine_info` varchar(256) DEFAULT '' COMMENT '处方信息',
  `hospitalization_info` varchar(256) DEFAULT '' COMMENT '住院信息',
  `pic_info` varchar(256) DEFAULT '' COMMENT '拍片、图片等信息，多张逗号分隔',
  `operation_info` varchar(256) DEFAULT '' COMMENT '治疗信息，如手术',
  `doctor_message` varchar(256) DEFAULT '' COMMENT '医生描述',
  `treat_duration` varchar(256) DEFAULT '' COMMENT '治疗时间',
  `remark` varchar(256) DEFAULT '' COMMENT '备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `hospital_name` varchar(200) DEFAULT NULL,
  `price` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='病历信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_record`
--

LOCK TABLES `medical_record` WRITE;
/*!40000 ALTER TABLE `medical_record` DISABLE KEYS */;
INSERT INTO `medical_record` VALUES (1,'1','发育正常，营养中等，神志清楚，自主体位，步入病房，查体合作。全身皮肤无色素沉着，弹性可，无肝掌，无蜘蛛痣，无出血点，无淤斑，无皮疹，无溃疡，毛发分布正常。全身浅表淋巴结未触及肿大。','急性正露丸中毒','藿香正气丸一个疗程','未住院','','','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2017.01.12-2017.01.14','','2017-03-04 08:13:04','复旦大学附属华山医院',NULL),(2,'1','病人脚步骨折，无法正常行走，活动时疼痛感剧烈','脚步腕关节骨折','安体舒通(螺内酯) ','入住北京是海淀医院住院部2号楼504病房-3号床位','xray/22-脚.jpg','手术顺利进行，病人正常恢复中','注意休息，切勿剧烈运动','2017.01.11-2017.01.22','病人踢足球导致的骨折','2017-03-04 08:18:55','复旦大学附属华山医院',NULL),(3,'1','1周前晨起时感右上肢无力，穿衣困难，1小时后恢复正常，未注意。3天前晨起床时出现右上肢抬举困难，下肢稍跛行，语言不清，无头痛、呕吐及意识障碍，到村卫生所就诊，测血压为170/100mmHg，诊为“脑血管意外”，给“复方降压片”等药物，约半天又恢复正常。6小时前（今晨4点）起床小便时又出现右侧肢体明显无力、行走困难、语言不清，立即上床休息','高血压','心得安(普萘洛尔)','未住院','','','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2017.01.12-2017.01.14','','2017-03-06 12:42:50','复旦大学附属华山医院',NULL),(4,'1','左 手食指与中指指间关节背侧均可见一横行伤口，长约4cm，深约1cm，','手部外伤','藿香正气丸一个疗程','入住北京市海淀医院住院部2号楼201病房-6号床位','xray/1-手.jpg','手术顺利进行，病人正常恢复中','清淡饮食，注意感冒','2017.01.12-2017.01.14','','2017-03-06 12:42:51','复旦大学附属华山医院',NULL),(5,'2','患儿2日前因受凉出现发热，最高体温39.3℃，稍感头昏，咳嗽，咳白色粘痰，不流清涕，无脓涕及血丝；无喷嚏、咯血、胸闷、胸痛、眼花、乏力、腹痛、腹泻等症状。曾在当地诊所治疗，无明显好转，遂来我院门诊求治，门诊拟“上呼吸道感染”收入院。自发病以来患儿体重略有下降，精神睡眠一般，食欲可，二便正常','高血压性心脏病','哌替啶(杜令丁) 三个疗程','入住北京市海淀医院住院部3号楼301病房-4号床位','xray/16-蜂窝肺.jpg','手术顺利进行，病人正常恢复中','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2017.01.22-2017.02.14','','2017-03-06 12:42:52','北京大学国际医院',NULL),(6,'2','无畸形，无压痛，无外伤及疤痕。头发略显灰花、有光泽，无秃发。皮瓣供血不足致苍白，肌伸功能尚可无特殊','消化性溃疡','黄连素(盐酸小襞碱) 一个疗程','入住北京市海淀医院住院部2号楼541病房-4号床位','','','清淡饮食，注意感冒','2016.02.22-2016.02.23','','2017-03-06 12:42:52','北京大学国际医院',NULL),(7,'2','患者因感冒受凉后出现咳嗽、鼻塞到个体诊所开中药一剂，服后效果不明显，现出现咽痛、恶寒、咳嗽加重，体温37.8℃而来我院治疗，经检查收入住院并观察治疗。','真性红细胞增多','CO-Alocii(辅酶凡)一个疗程','入住北京是海淀医院住院部1号楼215病房-2号床位','xray/2-手部.jpg','手术顺利进行，病人正常恢复中','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2016.03.12-2017.03.15','','2017-03-06 12:42:53','北京大学国际医院',NULL),(8,'2','体温37.1℃，脉搏67次/min，呼吸20次/min，血压138/75mm Hg，发育正常 ，营养中等，被动体位，神志清楚，对答切题，查体合作，慢性病容','慢性心力衰竭','克罗米芬(枸橼酸氯米芬)  ','入住北京是海淀医院住院部1号楼215病房-2号床位','xray/4-头部.jpg','手术顺利进行，病人正常恢复中','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2016.05.12-2016.05.13','','2017-03-06 12:43:49','北京大学国际医院',NULL),(9,'2','发育正常，营养中等，神志清楚，自主体位，步入病房，查体合作。全身皮肤无色素沉着，弹性可，无肝掌，无蜘蛛痣，无出血点，无淤斑，无皮疹，无溃疡，毛发分布正常。全身浅表淋巴结未触及肿大。','类风湿性关节炎','阿米卡星(丁胺卡那霉素)','入住北京市区海淀医院住院部2号楼501病房-4号床位','xray/14-女性盆骨.jpg','手术顺利进行，病人正常恢复中','食清淡饮食，注意感冒','2017.01.11-2017.01.22','','2017-03-06 12:43:49','北京大学国际医院',NULL),(10,'3','者跌伤后出现右足趾及足背疼痛、肿胀、发红、发热，并出现局部皮肤破溃，明显皮下出血，在诊所给予局部泡洗及输液治疗（具体不详），症状无明显好转，1天前患者感上述症状加重，局部皮肤发黑，为求进一步治疗，遂来我院。门诊以\"外伤感染，皮下血肿\"收住入院。患者自发病以来，神志清，无恶心、呕吐症状，无心慌﹑气短，无腹痛、腹胀，精神欠佳，饮食、夜休可，大小便正常。','帕金森','痢特灵(呋喃唑酮)一个疗程','未住院','','','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2017.01.12-2017.01.14','','2017-03-06 12:44:16','复旦大学附属华山医院',1),(11,'3','腹平坦，右下腹有长约4cm的线状疤痕一处，腹壁未见静脉曲张，腹柔软，无压痛，反跳痛及移动性浊音。','消化性溃疡','诺和灵(精蛋白生物合成人胰岛素注射液)','入住北京是海淀医院住院部2号楼501病房-4号床位','xray/11-腹部.jpg','手术顺利进行，病人正常恢复中','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2016.01.12-2016.01.14','','2017-03-06 12:44:16','复旦大学附属华山医院',NULL),(12,'3','全身浅表淋巴结肿大。','帕金森','灭滴灵(甲硝唑)一个疗程','未住院','','','清淡饮食，注意感冒','2013.01.12-2013.01.14','','2017-03-06 12:44:17','复旦大学附属华山医院',2),(13,'3','发育正常，营养中等，神志清楚，自主体位，步入病房，查体合作。全身皮肤无色素沉着，弹性可，无肝掌，无蜘蛛痣，无出血点，无淤斑，无皮疹，无溃疡，毛发分布正常。全身浅表淋巴结未触及肿大。','皮质醇增多症','消心痛(硝酸异山梨酯) ','入住北京市区海淀医院住院部2号楼501病房-4号床位','xray/3-小臂jpg.jpg','手术顺利进行，病人正常恢复中','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2017.01.12-2017.01.14','','2017-03-06 12:44:17','复旦大学附属华山医院',NULL),(14,'3','女，39岁，因心悸、乏力、头昏10余天','慢性心力衰竭','西咪替丁(甲氰咪呱)两个疗程','入住北京市区海淀医院住院部2号楼501病房-4号床位','xray/10-脊椎.jpg','手术顺利进行，病人正常恢复中','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2012.01.12-2012.05.14','','2017-03-06 12:44:18','复旦大学附属华山医院',NULL),(15,'4','步态不能合作，四肢无肌肉萎缩。右侧上下肢肌张力略高，肌力上肢00，下肢II0，左侧肢体肌张力、肌力正常。左侧指鼻、跟膝胫试验正常。无不自主运动','帕金森','黄连素(盐酸小襞碱) 一个疗程','未住院','','','清淡饮食，注意感冒','2017.01.12-2017.01.14','','2017-03-06 12:44:29','北京大学国际医院',NULL),(16,'4','发育正常，营养中等，神志清楚，自主体位，步入病房，查体合作。全身皮肤无色素沉着，弹性可，无肝掌，无蜘蛛痣，无出血点，无淤斑，无皮疹，无溃疡，毛发分布正常。全身浅表淋巴结未触及肿大。','原发性血小板增多症','雷米封(异烟肼) 一个疗程','入住北京是海淀医院住院部1号楼215病房-2号床位','xray/12-胸腔.jpg','手术顺利进行，病人正常恢复中','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2017.01.12-2017.01.14','','2017-03-06 12:44:29','北京大学国际医院',NULL),(17,'4','右足趾及足背发红、肿胀明显，末端皮肤破溃缺损，并有脓性液渗出，部分结痂发黑，远端1/3指甲缺损，未见骨质外露，右足趾及足背皮温升高','原发性骨髓纤维化症','灭滴灵(甲硝唑)一个疗程','未住院','','','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2016.01.12-2016.01.14','','2017-03-06 12:44:30','北京大学国际医院',NULL),(18,'4','结膜轻度黄染，双侧瞳孔等大，对光反应及调节反应正常。外耳道无溢液，双侧乳突无压痛。鼻腔通气良好，无溢液鼻甲及鼻中隔无异常，鼻窦无压痛。唇红润，牙列整齐，舌质红，苔黄腻','慢性迁延乙型病毒性肝炎','痢特灵(呋喃唑酮)','入住北京市区海淀医院住院部2号楼501病房-4号床位','xray/6-带钢钉和钢板的小腿.jpg','手术顺利进行，病人正常恢复中','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2017.01.12-2017.01.14','','2017-03-06 12:44:30','北京大学国际医院',NULL),(19,'4','发育正常，营养中等，神志清楚，自主体位，步入病房，查体合作。全身皮肤无色素沉着，弹性可，无肝掌，无蜘蛛痣，无出血点，无淤斑，无皮疹，无溃疡，毛发分布正常。全身浅表淋巴结未触及肿大。','重症肌无力','弥可葆(甲钴胺一个疗程','入住北京市区海淀医院住院部2号楼501病房-4号床位','xray/5-肩膀.jpg','手术顺利进行，病人正常恢复中','清淡饮食，注意感冒','2017.01.12-2017.01.14','','2017-03-06 12:44:30','北京大学国际医院',NULL),(20,'5','体温37℃，脉搏54/min，血压16/10.7kPa（120/80mmHg），发育正常，营养中等，神志清楚，精神软弱','风湿性心脏瓣膜病','黄连素(盐酸小襞碱) 一个疗程','入住北京市区海淀医院住院部2号楼501病房-4号床位','xray/20-颈部.jpg','手术顺利进行，病人正常恢复中','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2017.01.12-2017.01.14','','2017-03-06 12:45:06','复旦大学附属华山医院',NULL),(21,'5','因乏力、纳差、腹胀、肝区痛。','帕金森','蓟宾葡甲胺片','未住院','','','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2017.01.12-2017.01.14','','2017-03-06 12:45:06','复旦大学附属华山医院',NULL),(22,'5','病人感冒咳嗽不止，伴有青鼻涕。','风寒感冒','维C银翘片一个疗程','未住院','','','注意饮食、休息，多饮热水','2015.01.12-2015.01.14','','2017-03-06 12:45:06','复旦大学附属华山医院',NULL),(23,'5','病人膝盖骨折，伴有剧烈疼痛感，无法行走','膝盖骨折','止痛片，阿莫西林胶囊','入住北京市区海淀医院住院部1号楼211病房-4号床位','xray/19-膝盖.jpg','手术顺利进行，病人正常恢复中','1、注意饮食、休息；2、疏导情绪； 3、不适随诊。','2016.01.12-2016.01.12','','2017-03-06 12:45:07','复旦大学附属华山医院',NULL),(24,'5','病人咳嗽不止，伴有血。','肺部癌症','痢特灵(呋喃唑酮)','入住北京市区海淀医院住院部3号楼501病房-4号床位','xray/24-肺癌.jpg','手术顺利进行，病人正常恢复中','注意术后休息','2016.04.13-2016.05.10','','2017-03-06 12:45:07','复旦大学附属华山医院',NULL);
/*!40000 ALTER TABLE `medical_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_user_id` int(11) DEFAULT NULL,
  `to_user_id` int(11) DEFAULT NULL,
  `content` text,
  `status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (16,17,3,'北京大学国际医院 想要访问您《帕金森》的病历，是否同意？',1,'2017-03-09 15:24:30','10'),(17,19,3,'深圳市人民医院 想要访问您《帕金森》的病历，是否同意？',1,'2017-03-09 15:25:57','10'),(18,17,1,'北京大学国际医院 想要访问您《急性正露丸中毒》的病历，是否同意？',1,'2017-03-10 20:36:10','1'),(19,17,3,'北京大学国际医院 想要访问您《甲状腺功能减退症》的病历，是否同意？',1,'2017-03-10 20:47:29','12'),(20,17,3,'北京大学国际医院 想要访问您《消化性溃疡》的病历，是否同意？',1,'2017-03-10 20:47:35','11'),(21,17,3,'北京大学国际医院 想要访问您《消化性溃疡》的病历，是否同意？',0,'2017-03-11 14:18:53','11'),(22,17,1,'北京大学国际医院 想要访问您《脚步腕关节骨折》的病历，是否同意？',1,'2017-03-11 16:40:10','2'),(23,17,1,'北京大学国际医院 想要访问您《高血压》的病历，是否同意？',1,'2017-03-11 16:45:05','3'),(24,17,1,'北京大学国际医院 想要访问您《手部外伤》的病历，是否同意？',1,'2017-03-11 16:46:56','4'),(25,17,3,'北京大学国际医院 想要访问您《消化性溃疡》的病历，是否同意？',1,'2017-03-11 16:57:25','11');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名',
  `identity_id` varchar(18) NOT NULL DEFAULT '' COMMENT '身份证号',
  `gender` varchar(2) DEFAULT '' COMMENT '性别',
  `profile_photo` varchar(256) DEFAULT NULL,
  `birth_place` varchar(16) DEFAULT '' COMMENT '出生地',
  `birth_date` varchar(16) DEFAULT '' COMMENT '出生时间',
  `tel` varchar(32) DEFAULT '' COMMENT '电话',
  `blood_type` varchar(2) DEFAULT '' COMMENT '血型',
  `medical_history` varchar(256) DEFAULT '' COMMENT '病史信息',
  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除(0：未删除 1：删除)',
  `remark` varchar(256) DEFAULT '' COMMENT '备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'星矢','421083197001123211','男','profile/1.jpg','香港','','13121229980','A','父母健在。家族中无遗传性疾病、先天性疾病病史。',0,'','2017-03-04 08:08:36'),(2,'紫龙','421083197001123212','男','profile/2.jpg','北京','','13121229982','O','否认肝炎、结核等急慢性传染性疾病史，否认高血压，心脏等慢性病史，无外伤、手术及输血史。否认食物药物过敏史。预防接种史不详',0,'','2017-03-04 08:12:26'),(3,'冰河','421083197001123213','男','profile/3.jpg','上海','','13121229981','B','过去身体健康。3岁时曾患麻疹并发肺炎。5周治愈；4岁患双侧腮腺炎，2周治疗；10岁曾患急性典型菌痢，服黄连素1周治愈；否认其他传染病史。',0,'','2017-03-04 08:11:09'),(4,'一辉','421083197001123214','男','profile/4.jpg','山东青岛','','13121229983','B','否认家族遗传病史及先天性疾病史。',0,'','2017-03-06 12:40:33'),(5,'瞬','421083197001123215','男','profile/5.jpg','山东青岛','','13121229911','A','否认家族遗传病史及先天性疾病史。',0,'','2017-03-06 12:40:53');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-11 17:20:01
