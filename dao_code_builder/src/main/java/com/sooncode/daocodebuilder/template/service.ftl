<#assign mainTable = MAIN_TABLE ><#-- 主表模型 -->
<#assign someClassName = mainTable.entityName ><#-- 类名 -->
<#assign someObjectName = mainTable.defaultObjectName ><#-- 对象名 -->
<#assign one2one = ONE_TO_ONE ><#-- 主表的1:1模型 -->
<#assign one2oneSelf = ONE_TO_ONE_SELF><#-- 主表的 1:1自连接 模型 -->
<#assign one2oneMiddle = ONE_TO_ONE_MIDDLE><#-- 主表的 1:1自连接 模型 -->
<#assign one2many = ONE_TO_MANY><#-- 主表的 1:n 模型 -->
<#assign one2manySelf = ONE_TO_MANY_SELF><#-- 主表的 1:n自连接 模型 -->
<#assign many2many = MANY_TO_MANY><#-- 主表的 m:n 模型 -->
<#assign many2manySelf = MANY_TO_MANY_SELF><#-- 主表的 m:n自连接 模型 -->
<#assign mainPackage = mainTable.entityPackageName><#--  主实体包 -->
<#assign DaoInterface = DaoInterface><#--  主实体包 -->
<#assign daoPackage = DAO_PACKAGE><#--  *Dao 包 -->
<#assign interfaceConfig = INTERFACE_CONFIG><#--  主实体包 -->
package ${SERVICE_PACKAGE};

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import ${PAGER_PACKAGE}.Pager;
import ${DAO_INTERFACE_PACKAGE}.${someClassName}${DaoInterface}; <#-- 引入*DaoInterface类  -->
import ${mainTable.entityPackageName}.${someClassName};   <#-- 引入实体类  -->
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ${daoPackage}.${someClassName}Dao;
 
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
public class ${someClassName}Service {

    <#assign someDaoInterface = mainTable.defaultObjectName + DaoInterface ><#--  主实体包 -->  
    /**"${mainTable.tableRemarks}" Dao*/
    @Autowired
	private ${someClassName}Dao ${someObjectName}Dao ;



	 
 
}
