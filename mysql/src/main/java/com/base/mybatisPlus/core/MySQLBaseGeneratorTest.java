package com.base.mybatisPlus.core;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.io.File;
import java.io.IOException;

/**
 * MySQL 代码生成模板
 * 官网：<a href="https://baomidou.com/pages/981406/#%E6%95%B0%E6%8D%AE%E5%BA%93%E9%85%8D%E7%BD%AE-datasourceconfig">...</a>
 *
 * @author zc
 * @since 3.5.3
 */
public class MySQLBaseGeneratorTest {

	/**
	 * 数据源配置
	 * 配置数据库连接、数据库连接参数、数据库操作方法、数据库类型转换、数据库关键字处理
	 */
	private static DataSourceConfig dataSourceConfig() {
		return new DataSourceConfig
				//数据库连接
				.Builder("jdbc:mysql://hwy.pub:23306/kc_ocm_common", "root", "KC!6688")
				//数据库连接参数
				.addConnectionProperty("allowMultiQueries", "true")
				.addConnectionProperty("useSSL", "false")
				.addConnectionProperty("useUnicode", "true")
				.addConnectionProperty("characterEncoding", "utf8")
				.addConnectionProperty("autoReconnect", "true")
				.addConnectionProperty("rewriteBatchedStatements", "true")
				//数据库操作方法
				.dbQuery(new MySqlQuery())
				//数据库类型转换
				.typeConvert(new MySqlTypeConvert())
				//数据库关键字处理
				.keyWordsHandler(new MySqlKeyWordsHandler())
				.build();
	}

	/**
	 * 全局配置
	 * 配置生成文件的输出路径、作者、日期注释等
	 */
	protected static GlobalConfig globalConfig() throws IOException {
		//获取项目路径
		var file = new File("");
		var basePath = file.getCanonicalPath().replace("\\base", "");

		return new GlobalConfig.Builder()
				//作者
				.author("zc")
				//输出路径
				.outputDir(basePath + "\\repository\\src\\main\\java\\")
				//生成日期注释
				.commentDate("yyyy-MM-dd")
				//.disableOpenDir()
				.build();
	}

	/**
	 * 策略配置
	 * 和模板配置配合使用，模板配置功能的启用和禁用
	 * 策略配置功能的具体配置
	 */
	protected static StrategyConfig strategyConfig() {
		return new StrategyConfig.Builder()
				//设置过滤表信息 过滤规则表 没tm的用
				//.notLikeTable(new LikeTable("rule"))

				//配置实体
				.entityBuilder()
				//覆盖已生成文件
				.enableFileOverride()
				//开启链式模型 会导致BeanCopier失效
				//.enableChainModel()
				//开启 lombok 模型
				.enableLombok()
				//是否生成实体时，生成字段注解
				.enableTableFieldAnnotation()
				//ActiveRecord 模式，开启领域模型，继承module类
				//.enableActiveRecord()
				//禁用生成 serialVersionUID
				.disableSerialVersionUID()

				//开始service设置
				.serviceBuilder()
				//设置服务接口基础类
				//.superServiceClass("com.ymhx.domain.domain.base.IMyBaseService")
				//设置服务实现基础类
				.superServiceImplClass("com.kc.service.common.MyBaseServiceImpl")
				//设置服务实现接口名称
				.convertServiceImplFileName((entityName) -> entityName + "Service")

				//开始mapper设置
				.mapperBuilder()
				//继承 MyBaseMapper
				.superClass("com.kc.mapper.common.MyBaseMapper")
				//开启 覆盖已生成文件
				//.enableFileOverride()
				.build();
	}

	/**
	 * 包配置
	 * 配置生成文件的包名和路径
	 */
	protected static PackageConfig packageConfig() {
		return new PackageConfig.Builder()
				//父包名
				.parent("com.kc")
				//实体包名
				.entity("po")
				//服务实现类包名
				.serviceImpl("service")
				.build();
	}

	/**
	 * 测试生成代码
	 * 运行生成代码
	 */
	public void testSimple() throws IOException {
		//启动代码生成器
		new AutoGenerator(dataSourceConfig())
				//全局配置
				.global(globalConfig())
				//包配置
				.packageInfo(packageConfig())
				//策略配置
				.strategy(strategyConfig())
				//执行
				.execute();
	}
}