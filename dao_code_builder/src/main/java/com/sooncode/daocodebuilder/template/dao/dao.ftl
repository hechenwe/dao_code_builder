<#include "/com/variable.ftl">                            <#--引入全局变量-->
<#assign DaoInterface = DaoInterface>                     <#--  主实体包 -->
<#assign daoPackage = DAO_PACKAGE>                        <#--  *Dao 包 -->
<#assign interfaceConfig = INTERFACE_CONFIG>              <#--  主实体包 -->
package ${daoPackage};

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import ${DAO_INTERFACE_PACKAGE}.${someClassName}${DaoInterface};    <#-- 引入*DaoInterface类  -->
import ${mainTable.entityPackageName}.${someClassName};             <#-- 引入实体类  -->
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ${Pager};
<#if mainTable.allClassName??> 
<#list mainTable.allClassName?keys as key> 
import ${mainTable.allClassName[key]};
</#list>      
</#if>
 

/**
 * "${mainTable.entityName}" Dao
 * @author sooncode
 *
 */

@Repository
public class ${someClassName}Dao {

    <#assign someDaoInterface = mainTable.defaultObjectName + DaoInterface ><#--  主实体包 -->  
    /**"${mainTable.tableRemarks}" SQL映射接口*/
    @Autowired
	private ${someClassName}${DaoInterface} ${someDaoInterface} ;



	/**
	 * 添加 一条"${mainTable.tableRemarks}"记录
	 * @param  ${someObjectName} ${mainTable.tableRemarks} 对象
	 * @return int : 1 添加成功 ;0 添加失败
	 */
    public int ${interfaceConfig.insert}${someClassName}(${someClassName} ${someObjectName}){
        
         return this.${someDaoInterface}.${interfaceConfig.insert}${someClassName}(${someObjectName});
             
         }
    
    
    /**
     * 删除一条数据
     * @param ${mainTable.primaryKey.primaryPropertyName} 主键
     * @return int 受影响的行数
     */
    public int ${interfaceConfig.delete}${someClassName} (${mainTable.primaryKey.javaDataType}  ${mainTable.primaryKey.primaryPropertyName}) {
         
         return this.${someDaoInterface}.${interfaceConfig.delete}${someClassName} (${mainTable.primaryKey.primaryPropertyName});
   		
   		 }
   	
   	
   		 
    /**
     * 修改一条数据 
     * @param  ${someObjectName} ${mainTable.tableRemarks} 对象   
     * @return int 受影响的行数
     */
	public int ${interfaceConfig.update}${someClassName} (${someClassName} ${someObjectName}){
	    
	    return this.${someDaoInterface}.${interfaceConfig.update}${someClassName}  ( ${someObjectName});
	    
	    }
    
	
	
	/**
	 * 根据主键查询数据
	 * @param ${mainTable.primaryKey.primaryPropertyName} 主键 
	 * @return ${someObjectName} ${mainTable.tableRemarks} 对象   
	 */
	public ${someClassName} ${interfaceConfig.select}${someClassName}(${mainTable.primaryKey.javaDataType} ${mainTable.primaryKey.primaryPropertyName}){
         return this.${someDaoInterface}.${interfaceConfig.select}${someClassName} (${mainTable.primaryKey.primaryPropertyName});
    }
    
    <#-- 根据唯一约束字段查询 -->
	
	<#list mainTable.columns as column >
	<#if column.isOnly == "YES" && column.columnName != mainTable.primaryKey.primaryKeyName>
	
	/**
	 * 根据 ${column.columnRemarks} 查询
	 * ${column.propertyName} ${column.columnRemarks}
	 * @return ${mainEntityName}对象
	 */
	public ${mainEntityName} select${mainEntityName}By${column.propertyName?cap_first}(${column.javaDataType} ${column.propertyName}){
	      return this.select${mainEntityName}By${column.propertyName?cap_first}(${column.propertyName});
	}
	
	</#if>
	</#list>
    
    /**
     * 分页查询 ${mainTable.tableRemarks} 
     * @param map 封装了查询条件的${someObjectName},和 分页索引index,分页大小 size 的 Map.
     * @return List<${someClassName}> ${mainTable.tableRemarks} List 集合
     */
	public Pager<${someClassName}> ${interfaceConfig.select}${someClassName}${interfaceConfig.page}(${someClassName} ${someObjectName} , int pageNumber,int pageSize) {
	    Map<String ,Object> map = new HashMap<String, Object>();
		map.put("${someObjectName}", ${someObjectName});
		map.put("index", (pageNumber - 1 )* pageSize);
		map.put("size", pageSize);
		List<${someClassName}> ${mainTable.defaultObjectNames} = this.${someDaoInterface}.${interfaceConfig.select}${someClassName}${interfaceConfig.page}(map);
		int total = this.${someDaoInterface}.${someClassName}Size(map);
		Pager<${someClassName}> pager = new Pager<${someClassName}>(pageNumber, pageSize, total, ${mainTable.defaultObjectNames});
		return pager;
	}
	
	
    /**
     * 多字段匹配查询  ${mainTable.tableRemarks} 
     * @param  ${someObjectName}  ${mainTable.tableRemarks} 
     * @return List<${someClassName}> ${mainTable.tableRemarks} List 集合
     */
	public List<${someClassName}> select${mainTable.defaultObjectNames?cap_first}(${someClassName} ${someObjectName}) {
    	return this.${someDaoInterface}.select${mainTable.defaultObjectNames?cap_first}(${someObjectName});
    }
    //---------------------------------------------------one2one部分 -------------------------------------------------------------------------------------------
	<#if one2one.tables ?? > 
		
	   /**
	   * 分页连接查询 '${mainTable.tableRemarks}' 和 <#list one2one.tables as table>${table.tableRemarks} </#list>
	   * @param ${someObjectName} ‘${mainTable.tableRemarks}’对象 ,和 分页索引index,分页大小 size 的Map.
	   * @return List<${someClassName}> ${mainTable.tableRemarks} 集合
	   */
	   public Pager<${someClassName}> connectSelect${someClassName}(${someClassName} ${someObjectName} , <#list one2one.tables as table>${table.entityName} ${table.defaultObjectName} ,</#list> int pageNumber ,int pageSize ){
		    Map<String ,Object> map = new HashMap<String, Object>();
			map.put("${someObjectName}", ${someObjectName});
			<#list one2one.tables as table>
			map.put("${table.defaultObjectName}",${table.defaultObjectName});
			</#list>
			map.put("index", ( pageNumber - 1 ) * pageSize );
			map.put("size", pageSize);
			List<${someClassName}> ${mainTable.defaultObjectNames} = this.${someDaoInterface}.connectSelect${someClassName}(map);
			int total = this.${someDaoInterface}.connectSelect${someClassName}Size(map);
			Pager<${someClassName}> pager = new Pager<${someClassName}>(pageNumber, pageSize, total, ${mainTable.defaultObjectNames});
			return pager;
	    }
		
		
    </#if>
	
	
	//-------------------------------------------------------------------- one2many 部分------------------------------------------------------------------------------------------
	<#if one2many.tables ?? >  
		 <#list one2many.tables as table>
		 /**
	     * 连接查询'${mainTable.tableRemarks}' 和 ${table.tableRemarks} (一对多)
	     * @param map 封装了 ${mainTable.tableRemarks}主键 和 ${table.tableRemarks}的查询条件的Map.
	     * @return 包含多个${table.tableRemarks} 的 ${someClassName} .
	     */
		 public  ${someClassName} select${someClassName}And${table.defaultObjectNames ? cap_first} (${mainTable.primaryKey.javaDataType} ${mainTable.primaryKey.primaryPropertyName} , ${table.entityName} ${table.defaultObjectName}){
		    Map<String ,Object> map = new HashMap<String, Object>();
			map.put("${mainTable.primaryKey.primaryPropertyName}", ${mainTable.primaryKey.primaryPropertyName});
			map.put("${table.defaultObjectName}", ${table.defaultObjectName});
			 
		    ${someClassName} new${someClassName}= this.${someDaoInterface}.select${someClassName}And${table.defaultObjectNames ? cap_first}(map);
			 
		    return new${someClassName};
		    
		   
		}	 
	
	     	 
	     	 
		 </#list>
    </#if>
	
	//--------------------------------------------------- many2many 部分 -------------------------------------------------------------------------------------------
	
	<#if many2many.middleAndFkTables   ?? >  
		 <#list many2many.middleAndFkTables as middleAndFkTable>
		 /**
	     * 连接查询 '${mainTable.tableRemarks}' 和' ${middleAndFkTable.fkTable.tableRemarks}' 
	     * @param map 封装了 ${mainTable.tableRemarks} 和 ${middleAndFkTable.fkTable.tableRemarks}的查询条件 ,和 分页索引index,分页大小 size 的Map.
	     * @return Map 其中含有一个 '${mainTable.tableRemarks}' 和多个' ${middleAndFkTable.fkTable.tableRemarks}'
	     */
		 public Map<String,Object>  select${someClassName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first} (${someClassName} ${someObjectName}, ${middleAndFkTable.fkTable.entityName} ${middleAndFkTable.fkTable.defaultObjectName} , int pageNumber , int pageSize ){
		    Map<String ,Object> map = new HashMap<String, Object>();
			map.put("${someObjectName}", ${someObjectName});
			map.put("${middleAndFkTable.fkTable.defaultObjectName}",${middleAndFkTable.fkTable.defaultObjectName});
			map.put("index", (pageNumber - 1 )* pageSize);
			map.put("size", pageSize);
		    ${someClassName}  new${someClassName} = this.${someDaoInterface}.select${someClassName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}(map);
			int total = this.${someDaoInterface}.select${someClassName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}Size(map);
			Pager<${middleAndFkTable.fkTable.entityName}> ${middleAndFkTable.fkTable.entityName}Pager = new Pager<${middleAndFkTable.fkTable.entityName}>(pageNumber, pageSize, total, new${someClassName}.get${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}());
			new${someClassName}.set${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}(null);
			map.clear();
			map.put("${someObjectName}", new${someClassName});
			map.put("${middleAndFkTable.fkTable.entityName}Pager", ${middleAndFkTable.fkTable.entityName}Pager);
		   
		   return map;
		    
		 }
		 </#list>
    </#if> 
 
}
