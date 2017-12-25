<#assign viewName = view.viewCode ?capitalize ><#-- 视图名称 -->
<#assign viewClassName = view.viewCode ?cap_first ><#-- 视图对应的类名 -->
<#assign viewObjectName = view.viewCode ><#-- 视图对应的对象名 -->
<#assign packageName = packageName ><#-- 视图对应的对象名 -->
 
 
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper	 PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"	 "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

											<!--  ${viewObjectName}视图 --> 

<mapper namespace="${packageName}.${viewClassName}Interface">
    <cache/>   
    <!--所用字段-->
    <sql id="${viewObjectName}Columns"> <#list view.columns as column><#if column_index != 0> , </#if>${column.columnName}</#list> </sql>   <!--  ${view.viewCode} 的字段 --> 
    
    <!--${viewObjectName} 返回的结果集-->
    <resultMap id="${viewObjectName}ResultMap" type="${packageName}.${viewClassName}" >  
     <#list view.columns as column><#t>
	    	 <result column="${column.columnName}" property="${column.propertyName}"/><!--  ${column.columnRemarks} -->  
     </#list><#t>
	</resultMap> 
   
	
	 <!--分页查询数据-->
	 <sql id ="pagingSql"> <!-- 分页时用到的查询条件-->
	  
	  <#list  view.columns as column ><#t>
	              <#assign p = column.propertyLength ><#--   --> 
	              <#assign c = column.columnLength ><#--   --> 
				  <#if column.databaseDataType=="VARCHAR" || column.databaseDataType=="CHAR"   > 
				  <if test="${viewObjectName}.${column.propertyName} != null"> AND  ${column.columnName}  LIKE '%${"$"}{${viewObjectName}.${column.propertyName}}%'</if> <!--  ${column.columnRemarks} -->  
				  <#else> 
				  <if test="${viewObjectName}.${column.propertyName} != null"> AND  ${column.columnName}  =      ${"#"}{${viewObjectName}.${column.propertyName}}  </if> <!--  ${column.columnRemarks} -->   
				  </#if> 
		    </#list> 
	 </sql>
	 
	 <select id="select${viewClassName}Pager" parameterType="map" resultMap="${viewObjectName}ResultMap">
    
			SELECT <include refid="${viewObjectName}Columns"/>
			FROM ${viewName} 
			WHERE 1 = 1
		    <include refid="pagingSql"/> 
				  
			LIMIT ${"#"}{index},${"#"}{size}	 
			 		                     
	</select>
	<!--分页查询时总数-->
	<select id="${viewObjectName}Size" parameterType="map" resultType="int">
	
	      SELECT  
	            COUNT(1)  
	      FROM 
	            ${viewName}
	      WHERE 
	            1 = 1
		   <include refid="pagingSql"/> 
	</select>  
 
</mapper>
 
