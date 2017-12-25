<#assign mainTable = MAIN_TABLE ><#-- 主表模型-->
<#assign ClassName = mainTable.entityName ><#-- 实体类名 -->
<#assign objectName = mainTable.defaultObjectName ><#-- 实体变量名 -->
<#assign objectNames = mainTable.defaultObjectNames ><#-- 实体变量名复数 -->
<#assign one2one = ONE_TO_ONE ><#-- 主表的1:1模型 -->
<#assign one2oneSelf = ONE_TO_ONE_SELF><#-- 主表的 1:1自连接 模型 -->
<#assign one2oneMiddle = ONE_TO_ONE_MIDDLE><#-- 主表的 1:1自连接 模型 -->
<#assign one2many = ONE_TO_MANY><#-- 主表的 1:n 模型 -->
<#assign one2manySelf = ONE_TO_MANY_SELF><#-- 主表的 1:n自连接 模型 -->
<#assign many2many = MANY_TO_MANY><#-- 主表的 m:n 模型 -->
<#assign many2manySelf = MANY_TO_MANY_SELF><#-- 主表的 m:n自连接 模型 -->
<#assign mainPackage = mainTable.entityPackageName><#--  主实体包 -->
<#assign DaoPackage = DAO_PACKAGE><#--  *Interface 包 -->
<#assign DaoInterface = DaoInterface><#-- -->
<#assign interfaceConfig = INTERFACE_CONFIG><#-- 配置文件 -->
<#assign Test = "Test"><#--  *Interface 包 -->

package ${DaoPackage};


import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.apache.log4j.Logger;

import ${mainPackage}.${ClassName};
import ${Pager};
 
<#if mainTable.allClassName??> 
<#list mainTable.allClassName?keys as key> 
import ${mainTable.allClassName[key]};
</#list>      
</#if>
 
/**
 *'${mainTable.tableRemarks}' Dao接口测试类
 *                                                                     
 *  @author hechen
 */

@RunWith(SpringJUnit4ClassRunner.class) // 使用Springtest测试框架
@ContextConfiguration("/applicationContext.xml") // 加载配置
public class ${ClassName}Dao${Test} {

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
    //                                                      日志
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
    private static Logger logger = Logger.getLogger("${ClassName}${DaoInterface}${Test}.class");
    
    
    
    
	@Autowired
	private ${ClassName}Dao ${objectName}Dao;
	
    //------------------------------------------------------------------------------ 单表部分 -------------------------------------------------------------------------------------------------
	
	
    
	@Test
	public void ${interfaceConfig.insert}${ClassName}${Test}() {
	    ${ClassName}  ${objectName} = new ${ClassName}();
	    <#list mainTable.columns as column >
	    //${column.columnRemarks }
	    ${objectName}.set${column.propertyName ? cap_first} (null);
	    
	    </#list>
		int n = this.${objectName}Dao.${interfaceConfig.insert}${ClassName}(${objectName});
		logger.info(" ---测试---${objectName}Dao.${interfaceConfig.insert}${ClassName}() ：返回-->" + n);
	}

	@Test
	public void ${interfaceConfig.delete}${ClassName}${Test}() {
		 
		${mainTable.primaryKey.javaDataType} ${mainTable.primaryKey.primaryPropertyName} = null ;
		int n = this.${objectName}Dao.${interfaceConfig.delete}${ClassName}(${mainTable.primaryKey.primaryPropertyName});
		logger.info("---测试---${objectName}Dao.${interfaceConfig.delete}${ClassName} ：返回-->" + n);
	}

	@Test
	public void ${interfaceConfig.update}${ClassName}${Test}() {
		 ${ClassName}  ${objectName} = new ${ClassName}();
	    <#list mainTable.columns as column >
	    //${column.columnRemarks }
	    ${objectName}.set${column.propertyName ? cap_first} (null);
	    
	    </#list>
		int n = this.${objectName}Dao.${interfaceConfig.update}${ClassName}(${objectName});
	    logger.info("---测试 ---${objectName}Dao.${interfaceConfig.update}${ClassName} ：返回-->" + n);
	}

	@Test
	public void ${interfaceConfig.select}${ClassName}${Test}() {
		 ${mainTable.primaryKey.javaDataType} ${mainTable.primaryKey.primaryPropertyName} = null;
		 ${ClassName}  new${ClassName}  = this.${objectName}Dao.${interfaceConfig.select}${ClassName}(${mainTable.primaryKey.primaryPropertyName});
		logger.info("---测试---${objectName}Dao.${interfaceConfig.select}${ClassName} :返回-->" +new${ClassName});
	 
	}
	@Test
	public void ${interfaceConfig.select}${ClassName}${interfaceConfig.page}${Test}() {
		 ${ClassName}  ${objectName} = new ${ClassName}();
	    <#list mainTable.columns as column >
	    //${column.columnRemarks }
	    ${objectName}.set${column.propertyName ? cap_first} (null);
	    
	    </#list>
		int pageNumber = 1 ;
		int pageSize = 5 ;
		Pager<${ClassName}> pager = this.${objectName}Dao.${interfaceConfig.select}${ClassName}${interfaceConfig.page}(${objectName},pageNumber,pageSize);
		for (${ClassName} temp : pager.getLists()) {
			logger.info("---测试---${objectName}Dao.${interfaceConfig.select}${ClassName}${interfaceConfig.page}() 返回值：" + temp);
		}
	}

	 
	
	
 
 //----------------------------------------------------------------------------------------------------one2one 联系-------------------------------------------------------------------------------------------------------------
 <#if one2one.tables ?? >  
	 
    
    
	@Test
	public void connectSelect${ClassName}${Test}() {
		 
		${ClassName}  ${objectName} = new ${ClassName}();
	<#list mainTable.columns as column >
	    //${column.columnRemarks }
	    ${objectName}.set${column.propertyName ? cap_first} (null);
	</#list>
		
	<#list one2one.tables as table>
		${table.entityName} ${table.defaultObjectName} = new ${table.entityName}();
			<#list table.columns as column>
		//${column.columnRemarks }
		${table.defaultObjectName}.set${column.propertyName ? cap_first} (null);
			</#list>
	</#list> 
		 
		int pageNumber = 1;
		int pageSize = 5; 
		Pager<${ClassName}> pager = this.${objectName}Dao.connectSelect${ClassName}(${objectName},<#list one2one.tables as table> ${table.defaultObjectName} ,</#list>pageNumber,pageSize);
		for (${ClassName} temp : pager.getLists()) {
			logger.info("---测试---this.${objectName}Dao.connectSelect${ClassName}()返回值：" + temp);
		}
	}
	
    
	 
 </#if>
 
 
//-------------------------------------------------------------------- one2many 部分------------------------------------------------------------------------------------------
	<#if one2many.tables ?? >  
		 <#list one2many.tables as table>
	 
    @Test
	public void select${ClassName}And${table.defaultObjectNames ? cap_first}${Test}() {
		 
		${mainTable.primaryKey.javaDataType} ${mainTable.primaryKey.primaryPropertyName} = null ;
	 
		${table.entityName} ${table.defaultObjectName} = new ${table.entityName}();
	<#list table.columns as column>
		//${column.columnRemarks }
		${table.defaultObjectName}.set${column.propertyName ? cap_first} (null);
	</#list>
	 
		${ClassName}  ${objectName} = this.${objectName}Dao.select${ClassName}And${table.defaultObjectNames ? cap_first}(${mainTable.primaryKey.primaryPropertyName},${table.defaultObjectName});
	 
		for (${table.entityName} temp : ${objectName}.get${table.defaultObjectNames ? cap_first}()) {
			logger.info("---测试---this.${objectName}Dao.connectSelect${ClassName}()返回值：" + temp);
		}
	}
		  	 
		 </#list>
    </#if>
	
	//--------------------------------------------------- many2many 部分 -------------------------------------------------------------------------------------------
	
	<#if many2many.middleAndFkTables   ?? >  
		 <#list many2many.middleAndFkTables as middleAndFkTable>
		@SuppressWarnings("unchecked") 
		@Test
		public void select${ClassName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}${Test}() {
		 
		${ClassName}  ${objectName} = new ${ClassName}();
	<#list mainTable.columns as column >
	    //${column.columnRemarks }
	    ${objectName}.set${column.propertyName ? cap_first} (null);
	</#list>
		
	 
		${middleAndFkTable.fkTable.entityName} ${middleAndFkTable.fkTable.defaultObjectName} = new ${middleAndFkTable.fkTable.entityName}();
	<#list middleAndFkTable.fkTable.columns as column>
		//${column.columnRemarks }
		${middleAndFkTable.fkTable.defaultObjectName}.set${column.propertyName ? cap_first} (null);
	</#list>
	 
		 
		int pageNumber = 1;
		int pageSize = 5; 
		Map<String,Object> map = this.${objectName}Dao.select${ClassName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}(${objectName},${middleAndFkTable.fkTable.defaultObjectName},pageNumber,pageSize);
		Pager<${ClassName}> pager = (Pager<${ClassName}>) map.get("${middleAndFkTable.fkTable.defaultObjectName}Pager");
		for (${ClassName} temp : pager.getLists()) {
			logger.info("---测试---this.${objectName}Dao.select${ClassName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}()返回值：" + temp);
		}
	}
		 
		
		 </#list>
    </#if> 
 
}
 