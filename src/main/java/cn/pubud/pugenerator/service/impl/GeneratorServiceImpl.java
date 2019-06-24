package cn.pubud.pugenerator.service.impl;

import cn.pubud.pugenerator.entity.Generator;
import cn.pubud.pugenerator.entity.Table;
import cn.pubud.pugenerator.mapper.GeneratorMapper;
import cn.pubud.pugenerator.service.IGeneratorService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: charleyZZZZ
 * @Date: 2019/6/24 15:30
 * @Version 1.0
 */
@Service
public class GeneratorServiceImpl implements IGeneratorService {


    @Value("${authorName}")
    private String authorName;

    @Value("${spring.datasource.driver-class-name}")
    private String driveName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Autowired
    private GeneratorMapper mapper;

    @Override
    public void generate(Generator generator) throws Exception {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String codePath = generator.getCodePath();
        gc.setOutputDir(codePath + "/src/main/java");
        gc.setAuthor(authorName);
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(driveName);
        dsc.setUsername(userName);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        String[] codePackage = generator.getCodePackage().split("\\.");
        String moduleName = codePackage[codePackage.length - 1];
        pc.setModuleName(moduleName);
        String parent = generator.getCodePackage().replace("." + moduleName, "");
        pc.setParent(parent);
        mpg.setPackageInfo(pc);

        String codePackagePath = generator.getCodePackage().replace(".", "\\");

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("package_qo", pc.getParent() + ".qo");
                this.setMap(map);

            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return codePath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        focList.add(new FileOutConfig("/templates/dubboserviceImpl.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return codePath + "\\src\\main\\java\\" + codePackagePath +
                         "\\service\\impl\\" + tableInfo.getEntityName() + "ServiceImpl" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/entityQo.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return codePath + "\\src\\main\\java\\" + codePackagePath +
                         "\\qo\\" + tableInfo.getEntityName() + "Qo" + StringPool.DOT_JAVA;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        strategy.setSuperEntityClass("cn.pubud.pugenerator.commons.entity.BaseEntity");
        strategy.setSuperServiceImplClass("cn.pubud.pugenerator.commons.service.impl.ServiceImpl");
        strategy.setSuperServiceClass("cn.pubud.pugenerator.commons.service.IService");

        strategy.setEntityLombokModel(true);
        strategy.setInclude(generator.getTableName());
        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();


    }

    @Override
    public List<Table> getTables(String tableName) {
        return mapper.selectTableInfo(tableName);
    }


}
