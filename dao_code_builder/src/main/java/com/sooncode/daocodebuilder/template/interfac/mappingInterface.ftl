<#include "/com/variable.ftl"><#--引入全局变量-->
<#assign mainPackage = mainTable.entityPackageName><#--  主实体包 -->
<#assign DaoInterface = DaoInterface><#--  主实体包 -->
<#assign interfaceConfig = INTERFACE_CONFIG><#--  主实体包 -->

package ${DAO_INTERFACE_PACKAGE};

import java.util.List;
import java.util.Map;
import ${mainPackage}.${mainEntityName};

/**
 *  ${mainTable.tableRemarks} 
 * 
 *  @author HeChen 
 *
 */
public interface ${mainEntityName}${DaoInterface} {

	//------------------------------------------------------------  单表操作  ----------------------------------------------------------------------------------------
	/**
	 * 插入一条${mainTable.defaultObjectName} (${mainTable.tableRemarks})数据
	 * @param   ${mainEntityName} ${mainTable.tableRemarks}
	 * @return  int 受影响的行数
	 */
    public int ${interfaceConfig.insert}${mainEntityName}(${mainEntityName} ${mainTable.defaultObjectName});
    
    
    /**
     * 删除  ${mainTable.defaultObjectName}
     * @param  ${mainTable.primaryKey.primaryPropertyName}  主键  
     * @return int 受影响的行数
     */
    public int ${interfaceConfig.delete}${mainEntityName}(${mainTable.primaryKey.javaDataType}  ${mainTable.primaryKey.primaryPropertyName});
    
    
    /**
     * 修改 ${mainTable.defaultObjectName}
     * @param  ${mainEntityName} ${mainTable.tableRemarks} (封装了需要修改字段,但不包含主键).
     * @return int 受影响的行数
     */
	public int ${interfaceConfig.update}${mainEntityName}(${mainEntityName} ${mainTable.defaultObjectName});
    
	
	/**
	 * 根据主键查询${mainEntityName} 
	 * @param ${mainTable.primaryKey.primaryPropertyName} 主键 
	 * @return ${mainEntityName}对象
	 */
	public  ${mainEntityName}  ${interfaceConfig.select}${mainEntityName}(${mainTable.primaryKey.javaDataType} ${mainTable.primaryKey.primaryPropertyName});
    
    <#-- 根据唯一约束字段查询 -->
	
	<#list mainTable.columns as column >
	<#if column.isOnly == "YES" && column.columnName != mainTable.primaryKey.primaryKeyName>
	/**
	 * 根据 ${column.columnRemarks} 查询
	 * ${column.propertyName} ${column.columnRemarks}
	 * @return ${mainEntityName}对象
	 */
	public  ${mainEntityName}  select${mainEntityName}By${column.propertyName?cap_first}(${column.javaDataType} ${column.propertyName});
	</#if>
	</#list>
    
    /**
     * 分页查询 ${mainEntityName}
     * @param map 封装了查询条件的${mainEntityName}对象,和 分页索引index,分页大小 size 的 Map.
     * @return List<${mainEntityName}> ${mainTable.tableRemarks} 集合
     */
	public List<${mainEntityName}> ${interfaceConfig.select}${mainEntityName}${interfaceConfig.page}(Map<String,Object> map);
    
    
    /**
     * 按照条件查询 ${mainEntityName}的记录总数
     * @param  map  封装了查询条件${mainTable.defaultObjectName}的Map
     * @return int 记录的总数
     */
	public int ${mainEntityName}Size(Map<String,Object> map);
   
    /**
     * 多字段匹配查询 ${mainTable.tableRemarks} 
     * @param  
     * @return 返回一个${mainEntityName} 集合
     */
	public  List<${mainEntityName}> select${mainTable.defaultObjectNames?cap_first}(${mainEntityName} ${mainTable.defaultObjectName});
   
   
   
    //--------------------------------------------------- one2one -------------------------------------------------------------------------------------------
	<#if one2one.tables ?? >  
	  
     /**
     * 分页连接查询  ${mainEntityName}
     * @param map 封装了${mainTable.defaultObjectName},和 分页索引index,分页大小 size 的Map.
     * @return List<${mainEntityName}> ${mainTable.tableRemarks} 集合
     */
	public List<${mainEntityName}> connectSelect${mainEntityName}(Map<String,Object> map);
	
	
	public int connectSelect${mainEntityName}Size(Map<String,Object> map);
	 
    </#if>			   
	 
	
	//--------------------------------------------------- one2many -------------------------------------------------------------------------------------------
	<#if one2many.tables ?? >  
	   <#list one2many.tables as table>
	   
    /**
     * 根据 ${mainEntityName}的主属性(对应表的主键),连接分页查询${mainEntityName} 和  ${table.entityName}     
     * @param map ${mainEntityName}的主键, ${mainTable.defaultObjectName}
     * @return ${mainEntityName} ${mainTable.tableRemarks}
     */
	public ${mainEntityName} select${mainEntityName}And${table.defaultObjectNames ? cap_first}(Map<String,Object> map);
    
    
    public int select${mainEntityName}And${table.defaultObjectNames ? cap_first}Size (Map<String,Object> map);
	 	 
	   </#list>
    </#if>
    
    
    //--------------------------------------------------- many2many -------------------------------------------------------------------------------------------
	
	<#if many2many.middleAndFkTables   ?? >  
	   <#list many2many.middleAndFkTables as middleAndFkTable>
	   
    /**
     * 根据左右表,中间表的条件分页查询 (多对多查询)
     * @param map 封装了${mainTable.defaultObjectName},和 分页索引index,分页大小 size 的Map.
     * @return ${mainEntityName} ${mainTable.tableRemarks} 其中含有多个${middleAndFkTable.fkTable.tableName}  
     */
	public ${mainEntityName} select${mainEntityName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first} (Map<String,Object> map);
   
   
   
    public int select${mainEntityName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}Size (Map<String,Object> map);
	   </#list>
    </#if>
	
	
}    
    
    
    
    

