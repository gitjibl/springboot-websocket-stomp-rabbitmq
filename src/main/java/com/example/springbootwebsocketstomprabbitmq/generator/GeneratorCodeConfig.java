package com.example.springbootwebsocketstomprabbitmq.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

/**
 * @ProjectName: sysxy
 * @Package: com.sysam.sysxymain.config
 * @ClassName: GeneratorCodeConfig
 * @Author: jibl
 * @Description:
 * @Date: 2021/1/19 9:39
 * @Version: 1.0
 */
public class GeneratorCodeConfig {

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String DATA_URL = "jdbc:mysql://localhost:3306/messages?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
        String USERNAME = "root";
        String PASSWORD = "123456";
        String AUTHOR = "jibl";
        //MOUDLENAME 模块名称  sysam_task
        String MOUDLENAME = "";
        //自定义模块名称
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(DRIVER);
        dsc.setUrl(DATA_URL);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);

        AutoGenerator mpg = new AutoGenerator();

        GlobalConfig gc = new GlobalConfig();
        //得到当前项目的路径
        String projectPath = System.getProperty("user.dir");
        //生成文件输出根目录
        String ROOT_DIR = projectPath + "/" + MOUDLENAME + "/src/main/java";


        gc.setOpen(false);//生成完成后不弹出文件框
        gc.setOutputDir(ROOT_DIR);//生成文件输出根目录
        gc.setFileOverride(true);//每次生成覆盖之间的
        gc.setActiveRecord(true);//模型类对应关系型数据库中的一个表
//        gc.setSwagger2(true); //实体属性 Swagger2 注解
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setDateType(DateType.ONLY_DATE);//指定时间类型Date
        gc.setAuthor(AUTHOR);//生成人
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //strategy.setTablePrefix(new String[] { "SYS_" });// 此处可以修改为您的表前缀
        strategy.setRestControllerStyle(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        /** 如果需要生成getset 注释掉下面配置 **/
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setTablePrefix("");    //绑定表名前缀
        strategy.setTablePrefix("sys" + "_");
        strategy.setEntityLombokModel(true);
        strategy.setControllerMappingHyphenStyle(true);// 驼峰转连字符
        strategy.setEntityTableFieldAnnotationEnable(true);// 是否生成实体时，生成字段注解
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));


        PackageConfig pc = new PackageConfig();
        pc.setParent("com.example.springbootwebsocketstomprabbitmq" + MOUDLENAME);
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setEntity("entity");
        pc.setMapper("dao");


        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-rb");
                this.setMap(map);
            }
        };
        //xml生成路径
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/" + MOUDLENAME + "/src/main/resources/" + "mapper/mybatis/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        mpg.setPackageInfo(pc);
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);

        mpg.setDataSource(dsc);         //数据源配置
        mpg.setGlobalConfig(gc);        //全局配置
        mpg.setStrategy(strategy);      //生成策略配置
        mpg.setPackageInfo(pc);         //包配置
        mpg.setCfg(cfg);                //xml配置
        mpg.setTemplate(tc);
        // 执行生成
        mpg.execute();
    }
}
