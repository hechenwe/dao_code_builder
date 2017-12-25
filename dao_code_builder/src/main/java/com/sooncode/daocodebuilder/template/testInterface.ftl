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
<#assign DaoInterfacePackage = DAO_INTERFACE_PACKAGE><#--  *Interface 包 -->
<#assign DaoInterface = DaoInterface><#-- -->
<#assign interfaceConfig = INTERFACE_CONFIG><#-- 配置文件 -->
<#assign Test = "Test"><#--  *Interface 包 -->

package ${DaoInterfacePackage};

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.apache.log4j.Logger;

import ${mainPackage}.${ClassName};

<#if mainTable.allClassName??> 
<#list mainTable.allClassName?keys as key> 
import ${mainTable.allClassName[key]};
</#list>      
</#if>


/**
 * '${mainTable.tableRemarks}' SQL接口测试类  
 *                                                                   
 *@author hechen
 */

@RunWith(SpringJUnit4ClassRunner.class) // 使用Springtest测试框架
@ContextConfiguration("/applicationContext.xml") // 加载配置
public class ${ClassName}${DaoInterface}${Test} {

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
    //                   日志
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
    private static Logger logger = Logger.getLogger("${ClassName}${DaoInterface}${Test}.class");
    
    
    
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**                                                                   ${objectName}${DaoInterface} 被测试接口                                                                                                                                                                                                  */
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Autowired
	private ${ClassName}${DaoInterface} ${objectName}${DaoInterface};
	
	
	
                                              
    //------------------------------------------------------------------------------单表部分--------------------------------------------------------------------------------------------------- 
    
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**                                                                     测试添加                                                                                                                                                                                                                                                                           */
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Test
	public void ${interfaceConfig.insert}${ClassName}${Test}() {
	    ${ClassName}  ${objectName} = new ${ClassName}();
	    <#list mainTable.columns as column >
	    //${column.columnRemarks }
	    ${objectName}.set${column.propertyName ? cap_first} (null);
	    
	    </#list>
		int n = this.${objectName}${DaoInterface}.${interfaceConfig.insert}${ClassName}(${objectName});
		logger.info(" ---测试---${objectName}${DaoInterface}.${interfaceConfig.insert}${ClassName}() ：返回-->" + n);
	}


    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**                                                                     测试删除                                                                                                                                                                                                                                                                           */
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test
	public void ${interfaceConfig.delete}${ClassName}${Test}() {
		${mainTable.primaryKey.javaDataType} ${mainTable.primaryKey.primaryPropertyName} = null ;
		int n = this.${objectName}${DaoInterface}.${interfaceConfig.delete}${ClassName}(${mainTable.primaryKey.primaryPropertyName});
		logger.info("---测试---${objectName}${DaoInterface}.${interfaceConfig.delete}${ClassName} ：返回-->" + n);
	}



    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**                                                                     测试更新                                                                                                                                                                                                                                                                          */
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Test
	public void ${interfaceConfig.update}${ClassName}${Test}() {
		 ${ClassName}  ${objectName} = new ${ClassName}();
	    <#list mainTable.columns as column >
	    //${column.columnRemarks }
	    ${objectName}.set${column.propertyName ? cap_first} (null);
	    
	    </#list>
		int n = this.${objectName}${DaoInterface}.${interfaceConfig.update}${ClassName}(${objectName});
	    logger.info("---测试 ---${objectName}${DaoInterface}.${interfaceConfig.update}${ClassName} ：返回-->" + n);
	}



    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**                                                                     测试查询                                                                                                                                                                                                                                                                        */
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Test
	public void ${interfaceConfig.select}${ClassName}${Test}() {
		 ${mainTable.primaryKey.javaDataType} ${mainTable.primaryKey.primaryPropertyName} = null;
		 ${ClassName}  new${ClassName}  = this.${objectName}${DaoInterface}.${interfaceConfig.select}${ClassName}(${mainTable.primaryKey.primaryPropertyName});
		logger.info("---测试---${objectName}${DaoInterface}.${interfaceConfig.select}${ClassName} :返回-->" +new${ClassName});
	 
	}



    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**                                                                     测试分页查询                                                                                                                                                                                                                                                                  */
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Test
	public void ${interfaceConfig.select}${ClassName}${interfaceConfig.page}${Test}() {
		 ${ClassName}  ${objectName} = new ${ClassName}();
	    <#list mainTable.columns as column >
	    //${column.columnRemarks }
	    ${objectName}.set${column.propertyName ? cap_first} (null);
	    
	    </#list>
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("${objectName}", ${objectName});
		map.put("index", 0);
		map.put("size", 2);
		List<${ClassName}> ${objectNames} = this.${objectName}${DaoInterface}.${interfaceConfig.select}${ClassName}${interfaceConfig.page}(map);
		for (${ClassName} temp : ${objectNames}) {
			logger.info("---测试---${objectName}${DaoInterface}.${interfaceConfig.select}${ClassName}${interfaceConfig.page}() 返回值：" + temp);
		}
	}
    
    
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**                                                                     测试分页总数                                                                                                                                                                                                                                                                  */
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Test
	public void ${ClassName}Size${Test}() {
		 ${ClassName}  ${objectName} = new ${ClassName}();
	     <#list mainTable.columns as column >
	    //${column.columnRemarks }
	    ${objectName}.set${column.propertyName ? cap_first} (null);
	    
	    </#list>
	    Map<String, Object> map = new HashMap<String, Object>();
		map.put("${objectName}", ${objectName});
	    int size = this.${objectName}${DaoInterface}.${ClassName}Size (map);
		logger.info("---测试---${objectName}${DaoInterface}.${ClassName}Size():返回值：" + size);
	}
	
	
	 
 
 //-----------------------------------------------------------------------------one2one 部分-------------------------------------------------------------------------------------------------
 <#if one2one.tables ?? >  
	 
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**                                                                     测试1:1连接查询                                                                                                                                                                                                                                                          */
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     
	@Test
	public void connectSelect${ClassName}${Test}() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		 ${ClassName}  ${objectName} = new ${ClassName}();
	    <#list mainTable.columns as column >
	    //${column.columnRemarks }
	    ${objectName}.set${column.propertyName ? cap_first} (null);
	    
	    </#list>
		map.put("index", 0);
		map.put("size", 2);
		map.put("${objectName}", ${objectName});
		List<${ClassName}> ${objectNames} = this.${objectName}${DaoInterface}.connectSelect${ClassName}(map);
		for (${ClassName} temp : ${objectNames}) {
			logger.info("---测试---this.${objectName}${DaoInterface}.connectSelect${ClassName}()返回值：" + temp);
		}
	}
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**                                                                     测试1:1连接查询的数据总数                                                                                                                                                                                                                                        */
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
	public void connectSelect${ClassName}Size${Test}() {
		 ${ClassName}  ${objectName} = new ${ClassName}();
	     <#list mainTable.columns as column >
	    //${column.columnRemarks }
	    ${objectName}.set${column.propertyName ? cap_first} (null);
	    
	    </#list>
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("${objectName}", ${objectName});
	    int size = this.${objectName}${DaoInterface}.connectSelect${ClassName}Size (map);
		logger.info("---测试---${objectName}${DaoInterface}.connectSelect${ClassName}Size:返回值：" + size);
	}
	 
 </#if>
  
 //------------------------------------------------------------------------------------one2many 部分-------------------------------------------------------------------------------------------- 
 <#if one2many.tables ?? >  
	 <#list one2many.tables as table>
     	 
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**                                                                     测试1:N 连接查询                                                                                                                                                                                                                                                      */
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Test
	public void select${ClassName}And${table.defaultObjectNames ? cap_first}${Test}() {
		
		${mainTable.primaryKey.javaDataType} ${mainTable.primaryKey.primaryPropertyName} = null ;
	 
		${table.entityName} ${table.defaultObjectName} = new ${table.entityName}();
	    <#list table.columns as column>
		//${column.columnRemarks }
		${table.defaultObjectName}.set${column.propertyName ? cap_first} (null);
	    </#list>
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		  
		map.put("${mainTable.primaryKey.primaryPropertyName}", ${mainTable.primaryKey.primaryPropertyName});
		map.put("${table.defaultObjectName}", ${table.defaultObjectName});
	 
		${ClassName} ${objectName}Result = this.${objectName}${DaoInterface}.select${ClassName}And${table.defaultObjectNames ? cap_first}(map);
	    logger.info("---测试---this.${objectName}${DaoInterface}.select${ClassName}And${table.defaultObjectNames ? cap_first}()返回值：" +${objectName}Result);
		
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**                                                                     测试1:N连接查询的数据总数                                                                                                                                                                                                                                        */
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Test
	public void select${ClassName}And${table.defaultObjectNames ? cap_first}Size${Test}() {
		 ${ClassName}  ${objectName} = new ${ClassName}();
	     <#list mainTable.columns as column >
	    //${column.columnRemarks }
	    ${objectName}.set${column.propertyName ? cap_first} (null);
	    
	    </#list>
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("${objectName}", ${objectName});
	    int size = this.${objectName}${DaoInterface}.select${ClassName}And${table.defaultObjectNames ? cap_first}Size (map);
		logger.info("---测试---${objectName}${DaoInterface}.select${ClassName}And${table.defaultObjectNames ? cap_first}Size:返回值：" + size);
	}
		 
	 </#list>
 </#if>
 
//-----------------------------------------------------------------------------many2many 部分-------------------------------------------------------------------------------------------------
 
 
<#if many2many.middleAndFkTables   ?? >  
	 <#list many2many.middleAndFkTables as middleAndFkTable>
      //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	  /**                                                                     测试1:N 连接查询                                                                                                                                                                                                                                                      */
	  //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	  @Test
	  public void select${ClassName}And${middleAndFkTable.fkTable.defaultObjectNames}${Test}() {
		
			Map<String, Object> map = new HashMap<String, Object>();
			${ClassName}  ${objectName} = new ${ClassName}();
		    <#list mainTable.columns as column >
		    //${column.columnRemarks }
		    ${objectName}.set${column.propertyName ? cap_first} (null);
		    </#list>
			map.put("index", 0);
			map.put("size", 2);
			map.put("${objectName}", ${objectName});
			${ClassName} ${objectName}Result = this.${objectName}${DaoInterface}.select${ClassName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}(map);
		    logger.info("---测试---this.${objectName}${DaoInterface}.select${ClassName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}()返回值：" +${objectName}Result);
		
	  }
	
	  //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	  /**                                                                     测试1:N连接查询的数据总数                                                                                                                                                                                                                                        */
	  //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	  @Test
	  public void select${ClassName}And${middleAndFkTable.fkTable.defaultObjectNames}Size${Test}() {
			 ${ClassName}  ${objectName} = new ${ClassName}();
		     <#list mainTable.columns as column >
		     //${column.columnRemarks }
		     ${objectName}.set${column.propertyName ? cap_first} (null);
		     </#list>
		     Map<String, Object> map = new HashMap<String, Object>();
		     map.put("${objectName}", ${objectName});
		     int size = this.${objectName}${DaoInterface}.select${ClassName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}Size (map);
			 logger.info("---测试---${objectName}${DaoInterface}.select${ClassName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}Size:返回值：" + size);
	  } 
       
	 </#list>
</#if> 
 
}
 