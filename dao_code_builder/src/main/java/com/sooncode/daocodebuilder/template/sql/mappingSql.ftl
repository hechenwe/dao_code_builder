<#include "/com/variable.ftl"><#--引入全局变量-->
<#assign daoMappingPackage = DAO_MAPPING_PACKAGE><#--  -->
<#assign DaoInterface = DaoInterface><#--   -->
<#assign interfaceConfig = INTERFACE_CONFIG><#--   -->
<#assign m_p = mainTable.propertyMaxLength ><#-- -->
<#assign m_c = mainTable.columnMaxLength ><#--   -->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper	 PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"	 "http://mybatis.org/dtd/mybatis-3-mapper.dtd">


											<!--  ${mainTable.tableRemarks}  --> 
											

<mapper namespace="${daoMappingPackage}.${mainEntityName}${DaoInterface}">
    <cache/>
      
    
	<#------单表------>
	<!--添加-->
    <insert id="${interfaceConfig.insert}${mainEntityName}" useGeneratedKeys="true"  keyProperty="${mainTable.primaryKey.primaryPropertyName}"  parameterType="${mainPackage}.${mainEntityName}"> <!-- 插入 ${mainEntityName} -->
   		 INSERT INTO 
   		 ${mainTable.tableName} (<#list mainTable.columns as column ><#if column.isAutoinCrement=="NO"> ${column.columnName}<#if !column_has_next> <#else>,</#if></#if></#list>) 
   		 VALUES (<#list mainTable.columns as column ><#if column.isAutoinCrement=="NO">${"#"}{${column.propertyName}} <#if !column_has_next> <#else>,</#if></#if></#list>) 
    </insert>
    
    <!--删除-->
    
      
	     <#assign primaryKey = mainTable.primaryKey><#--主键-->
	     <#assign primaryPropertyName = mainTable.primaryKey.primaryPropertyName><#--主键名称-->
	     <#assign databaseDataType = mainTable.primaryKey.databaseDataType><#--主键的数据库数据类型-->
    <delete id="${interfaceConfig.delete}${mainEntityName}"  parameterType="${mainTable.primaryKey.javaDataType}" ><!--  删除  ${mainEntityName}  --> 
	     DELETE FROM ${mainTable.tableName} 
	     WHERE ${primaryKey.primaryKeyName} = <#if databaseDataType=="VARCHAR" || databaseDataType=="CHAR" || databaseDataType=="DATE">'${"#"}{${primaryPropertyName}}'<#else>${"#"}{${primaryPropertyName}}</#if>
    </delete>
	
	<!--更新-->
    <update id="${interfaceConfig.update}${mainEntityName}" parameterType="${mainPackage}.${mainEntityName}" ><!--  更新  ${mainEntityName}  -->
        
	    UPDATE ${mainTable.tableName}
	    SET  
	    <#list  mainTable.columns as column>
	    <#assign p = column.propertyLength ><#--   --> 
	    <#assign c = column.columnLength ><#--   -->
			    <#if column.columnName != mainTable.primaryKey.primaryKeyName> 
         		<if test="${ column.propertyName} != null"><#list 0..(m_p - p ) as n>${" "}</#list>${column.columnName} = ${'#'}{${column.propertyName}}, <#list 0..(m_p + m_c - p -c) as n>${" "}</#list></if> <!--  ${column.columnRemarks} -->      
		        </#if>
        </#list> 
        
	    <#list  mainTable.columns as column>
	    <#assign p = column.propertyLength ><#--   --> 
	    <#assign c = column.columnLength ><#--   -->
			    <#if column.columnName == mainTable.primaryKey.primaryKeyName> 
         		<if test="${ column.propertyName} != null"><#list 0..(m_p - p ) as n>${" "}</#list>${column.columnName} = ${'#'}{${column.propertyName}}  <#list 0..(m_p + m_c - p -c) as n>${" "}</#list></if> <!--  ${column.columnRemarks} -->      
		        </#if>
        </#list> 
		       
	    WHERE  
	    ${primaryKey.primaryKeyName} = <#if databaseDataType=="VARCHAR" || databaseDataType=="CHAR" || databaseDataType=="DATE">'${"#"}{${primaryPropertyName}}'<#else>${"#"}{${primaryPropertyName}}</#if> 
    </update>  
    
    <!-- 查询  -->
    <sql id="${mainEntityName}Columns"> <#list mainTable.columns as column><#if column_index != 0> , </#if>${column.columnName}</#list> </sql>   <!--  ${mainEntityName} 的字段 --> 
    
    <#-- 返回的结果集-->
    <resultMap id="${mainEntityName}ResultMap" type="${mainPackage}.${mainEntityName}" >  
     <#list mainTable.columns as column><#t>
	    	 <result column="${column.columnName}" property="${column.propertyName}"/><#list 0..(mainTable.propertyMaxLength + mainTable.columnMaxLength  - column.propertyLength -column.columnLength  ) as n>${" "}</#list><!--  ${column.columnRemarks} -->  
     </#list><#t>
	</resultMap> 
	
    <!-- 根据主键查询 -->
    <select id="${interfaceConfig.select}${mainEntityName}" parameterType="${mainTable.primaryKey.javaDataType}" resultMap="${mainEntityName}ResultMap"> 
    
			SELECT <include refid="${mainEntityName}Columns"/>
			FROM ${mainTable.tableName} 	 
			WHERE ${primaryKey.primaryKeyName} = ${"#"}{${primaryPropertyName}} 
	
	</select>  
	<#-- 根据唯一约束字段查询 -->
	
	<#list mainTable.columns as column >
	<#if column.isOnly == "YES" && column.columnName != primaryKey.primaryKeyName>
	<!-- 根据 ${column.columnRemarks} 查询 -->
	<select id="select${mainEntityName}By${column.propertyName?cap_first}" parameterType="${column.javaDataType}" resultMap="${mainEntityName}ResultMap"> 
    
			SELECT <include refid="${mainEntityName}Columns"/>
			FROM ${mainTable.tableName} 	 
			WHERE ${column.columnName} = ${"#"}{${column.propertyName}} 
	
	</select>  
	</#if>
	</#list>
	
	
	 <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄分页查询数据┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
	 <sql id ="pagingSql"> <!-- 分页时用到的查询条件-->
	  
	  <#list  mainTable.columns as column ><#t>
	              <#assign p = column.propertyLength ><#--   --> 
	              <#assign c = column.columnLength ><#--   --> 
				  <#if column.databaseDataType=="VARCHAR" || column.databaseDataType=="CHAR"   > 
				  <if test="${mainTable.defaultObjectName}.${column.propertyName} != null"><#list 0..(m_p - p) as n>${" "}</#list>AND  ${column.columnName}<#list 0..(m_c-c) as n>${" "}</#list> LIKE '%${"$"}{${mainTable.defaultObjectName}.${column.propertyName}}%'</if><#list 0..(m_p - p) as n>${" "}</#list><!--  ${column.columnRemarks} -->  
				  <#else> 
				  <if test="${mainTable.defaultObjectName}.${column.propertyName} != null"><#list 0..(m_p - p) as n>${" "}</#list>AND  ${column.columnName}<#list 0..(m_c-c) as n>${" "}</#list> =      ${"#"}{${mainTable.defaultObjectName}.${column.propertyName}}  </if><#list 0..(m_p - p) as n>${" "}</#list><!--  ${column.columnRemarks} -->   
				  </#if> 
		    </#list> 
	 </sql>
	 
	 <select id="${interfaceConfig.select}${mainEntityName}${interfaceConfig.page}" parameterType="map" resultMap="${mainEntityName}ResultMap">
    
			SELECT <include refid="${mainEntityName}Columns"/>
			FROM ${mainTable.tableName} 
			WHERE 1 = 1
		    <include refid="pagingSql"/> 
				  
			LIMIT ${"#"}{index},${"#"}{size}	 
			 		                     
	 </select>
	
	 <!-- 多字段匹配查询--><#-- (理论上只有少量数据) -->
	 <select id="select${mainTable.defaultObjectNames?cap_first}" parameterType="${mainPackage}.${mainEntityName}" resultMap="${mainEntityName}ResultMap">
    
			SELECT <include refid="${mainEntityName}Columns"/>
			FROM ${mainTable.tableName} 
			WHERE 1 = 1
		    <#list  mainTable.columns as column ><#t>
            <#assign p = column.propertyLength ><#--   --> 
            <#assign c = column.columnLength ><#--   --> 
		    <#if column.databaseDataType=="VARCHAR" || column.databaseDataType=="CHAR"   > 
		    <if test="${column.propertyName} != null"><#list 0..(m_p - p) as n>${" "}</#list>AND  ${column.columnName}<#list 0..(m_c-c) as n>${" "}</#list> LIKE '%${"$"}{${column.propertyName}}%'</if><#list 0..(m_p - p) as n>${" "}</#list><!--  ${column.columnRemarks} -->  
		    <#else> 
		    <if test="${column.propertyName} != null"><#list 0..(m_p - p) as n>${" "}</#list>AND  ${column.columnName}<#list 0..(m_c-c) as n>${" "}</#list> =      ${"#"}{${column.propertyName}}  </if><#list 0..(m_p - p) as n>${" "}</#list><!--  ${column.columnRemarks} -->   
		    </#if> 
		    </#list> 
			 		                     
	</select>
	<!--分页查询时总数-->
	<select id="${mainEntityName}Size" parameterType="map" resultType="int">
	
	      SELECT  
	            COUNT(1)  
	      FROM 
	            ${mainTable.tableName}
	      WHERE 
	            1 = 1
		   <include refid="pagingSql"/> 
	</select>  
	 
<!--one2one 部分-->  

<#if one2one.tables ?? >  
   <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄one2one 查询条件┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄--> 
     <sql id="${mainEntityName}<#list one2one.tables as table >And${table.entityName}</#list>">  
             
        <#list mainColumns as column  >
            <#assign p = column.propertyLength ><#--   --> 
	        <#assign c = column.columnLength ><#--   -->	 
            ${mainTable.abbreviation}.${column.columnName}  <#list 0..(m_c - c) as k>${" "}</#list>AS    ${mainTable.abbreviation}_${column.columnName} ,<#list 0..(m_c - c) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
        </#list> 
                                             
	    <#list one2one.tables as table>
	            <#assign m_p1 = table.propertyMaxLength >  
	            <#assign m_c1 = table.columnMaxLength >
				    <#list table.columns as column >
				        <#assign p1 = column.propertyLength >  
			            <#assign c1 = column.columnLength > 	
				        <#if !table_has_next  &&  !column_has_next>
		            ${table.abbreviation}.${column.columnName}  <#list 0..(m_c1 - c1) as k>${" "}</#list>AS    ${table.abbreviation}_${column.columnName}  <#list 0..(m_c1 - c1) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
				        <#else>
		            ${table.abbreviation}.${column.columnName}  <#list 0..(m_c1 - c1) as k>${" "}</#list>AS    ${table.abbreviation}_${column.columnName} ,<#list 0..(m_c1 - c1) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
				        </#if>
				    </#list>
		 </#list>
	  </sql>
	  <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄one2one 返回结果集┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄--> 
	  <resultMap id="${mainEntityName}<#list one2one.tables as table >And${table.entityName}</#list>Result" type="${mainPackage}.${mainEntityName}">
	      
	      <#list  mainColumns as column  >
	      <#assign p = column.propertyLength ><#--   --> 
	      <#assign c = column.columnLength ><#--   -->
		  <result property="${column.propertyName}" <#list 0..(m_p - p) as k>${" "}</#list> column="${mainTable.abbreviation}_${column.columnName }" ></result><#list 0..(m_c - c) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
	      </#list>
		  <#list one2one.tables as table >
		  <#assign m_p1 = table.propertyMaxLength ><#assign m_c1 = table.columnMaxLength >
		  <association property="${table.defaultObjectName}" javaType="${table.entityPackageName}.${table.entityName}"> <!--${table.tableRemarks}-->
		  
			  <#list table.columns as column  >
			  <#assign p1 = column.propertyLength ><#--   --> 
	          <#assign c1 = column.columnLength ><#--   -->
			  <result property="${column.propertyName}"<#list 0..(m_p1 - p1) as k>${" "}</#list>column="${table.abbreviation}_${column.columnName }" ></result><#list 0..(m_c1 - c1) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
		      </#list>
		      
		  </association>
				    
		  </#list>
	   
	 </resultMap> 
	<!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄连接查询的查询条件┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ -->
	 <sql id ="${mainEntityName}AndOtherSizeSql" >  
 
	      
			 <#------ ---------主表的查询条件--------------- -->
			<#list mainColumns  as column ><#t>
			 <#assign p = column.propertyLength ><#--   --> 
	         <#assign c = column.columnLength ><#--   -->
				    <#if column.databaseDataType=="VARCHAR" || column.databaseDataType=="CHAR" ><#t>
				  <if test="${mainTable.defaultObjectName}.${column.propertyName} != null"><#list 0..(m_p - p) as k>${" "}</#list>AND  ${mainTable.abbreviation}.${column.columnName}<#list 0..(m_c - c) as k>${" "}</#list>LIKE '%${"$"}{${mainEntityName}.${column.propertyName}}%'</if><#list 0..(m_p - p) as k>${" "}</#list><!--${column.columnRemarks}-->   
				 
				    <#else><#t>
				  <if test="${mainTable.defaultObjectName}.${column.propertyName} != null"><#list 0..(m_p - p) as k>${" "}</#list>AND  ${mainTable.abbreviation}.${column.columnName}<#list 0..(m_c - c) as k>${" "}</#list>=      ${"#"}{${mainEntityName}.${column.propertyName}}  </if><#list 0..(m_p - p) as k>${" "}</#list><!--${column.columnRemarks}-->  
				  
				    </#if><#t> 
		    </#list><#lt> 
		    
	           <#list one2one.tables as table>
	           <#assign m_p1 = table.propertyMaxLength >  
	           <#assign m_c1 = table.columnMaxLength >
		 <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄one2one参照表的查询条件 ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
		 <if test="${table.defaultObjectName} != null"><!--${table.tableRemarks}查询条件  -->
			 <#list table.columns  as column ><#t>
			           <#assign p1 = column.propertyLength ><#--   --> 
	                   <#assign c1 = column.columnLength ><#--   -->
				    <#if column.databaseDataType=="VARCHAR" || column.databaseDataType=="CHAR" ><#t>
				    
				  <if test="${table.defaultObjectName}.${column.propertyName} != null"><#list 0..(m_p1 - p1) as k>${" "}</#list>AND  ${table.abbreviation}.${column.columnName}<#list 0..(m_c1 - c1) as k>${" "}</#list>LIKE  '%${"$"}{${table.defaultObjectName}.${column.propertyName}}%'</if><#list 0..(m_p1 - p1) as k>${" "}</#list><!--${column.columnRemarks}-->     
				  
				    <#else><#t>
				  <if test="${table.defaultObjectName}.${column.propertyName} != null"><#list 0..(m_p1 - p1) as k>${" "}</#list>AND  ${table.abbreviation}.${column.columnName}<#list 0..(m_c1 - c1) as k>${" "}</#list>=       ${"#"}{${table.defaultObjectName}.${column.propertyName}}  </if><#list 0..(m_p1 - p1) as k>${" "}</#list><!--${column.columnRemarks}-->   
				    </#if><#t> 
		    </#list><#lt> 
	     </if>	
	</#list>
	
	 </sql>
	 <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄one2one连接查询 ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
	 <select id="connectSelect${mainEntityName}" parameterType="map" resultMap="${mainEntityName}<#list one2one.tables as table >And${table.entityName}</#list>Result"><!--连接查询-->
    
			SELECT 
			      <include refid="${mainEntityName}<#list one2one.tables as table >And${table.entityName}</#list>"/> 
			FROM 
			 <#------ ---------连接条件--------------- -->
			${mainTable.tableName} ${mainTable.abbreviation}                                          
			<#list one2one.mainTable.foreignKeys as fk >
			LEFT JOIN ${fk.referencedRelationTable.tableName} ${fk.referencedRelationTable.abbreviation} ON ${mainTable.abbreviation}.${fk.fkColumnName } = ${fk.referencedRelationTable.abbreviation}.${fk.referencedRelationTable.primaryKey.primaryKeyName}
			</#list>
			WHERE 1 = 1
			
			      <include refid="${mainEntityName}AndOtherSizeSql"/> 
			
			      LIMIT ${"#"}{index},${"#"}{size}	
	</select>
	 <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄one2one连接查询总数 ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
	 <select id="connectSelect${mainEntityName}Size" parameterType="map" resultType="int"> <!--连接查询总数-->
	
	      SELECT  
	            COUNT(1)  
	      FROM
	                                                               <#------ ---------连接条件--------------- -->
		        ${mainTable.tableName} ${mainTable.abbreviation}                                          
			    <#list one2one.mainTable.foreignKeys as fk >
			    LEFT JOIN ${fk.referencedRelationTable.tableName} ${fk.referencedRelationTable.abbreviation} ON ${mainTable.abbreviation}.${fk.fkColumnName } = ${fk.referencedRelationTable.abbreviation}.${fk.referencedRelationTable.primaryKey.primaryKeyName}
			    </#list> 
	      WHERE 1 = 1
		       <include refid="${mainEntityName}AndOtherSizeSql"/>     
		   
	</select>   
	
</#if>

	<!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄one2oneSelf 部分 ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
	
	<!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄one2oneMiddle 部分 ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
	
	<!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄one2many 部分 ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->

<#if one2many.tables ?? >   
	  <#list one2many.tables as table>
	  <#assign m_p1 = table.propertyMaxLength >  
	  <#assign m_c1 = table.columnMaxLength >
	  
<!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄one2many查询部分┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
	  
	  
	  <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄${mainTable.tableRemarks} , ${table.tableRemarks} 字段重命名  SQL 片段┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ -->
	  
	  <sql id="${mainEntityName}And${table.entityName}Columns">  
	      
	        
            <#list one2many.mainTable.columns as column >
            <#assign p = column.propertyLength ><#--   --> 
	        <#assign c = column.columnLength ><#--   -->
            ${mainTable.abbreviation}.${column.columnName}<#list 0..(m_c-c) as k>${" "}</#list>AS    ${mainTable.abbreviation}_${column.columnName} ,<#list 0..(m_c-c) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
            </#list> 
             
		    <#list table.columns as column>
		    <#assign p1 = column.propertyLength ><#--   --> 
	        <#assign c1 = column.columnLength ><#--   -->
            ${table.abbreviation}.${column.columnName}<#list 0..(m_c1-c1) as k>${" "}</#list>AS    ${table.abbreviation}_${column.columnName}  <#if column_has_next>,</#if><#list 0..(m_c1-c1) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
	        </#list>                               
	</sql>
	
	
	<!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄one2many返回结果集┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
	<resultMap  id="${mainEntityName}And${table.entityName}ResultMap" type="${mainPackage}.${mainEntityName}"> <!-- 返回结果集 -->
	 
         <#list one2many.mainTable.columns as column >
         <#assign p = column.propertyLength ><#--   --> 
	     <#assign c = column.columnLength ><#--   -->
		 <result property="${column.propertyName}"<#list 0..(m_p-p) as k>${" "}</#list>column="${mainTable.abbreviation}_${column.columnName }" ></result><#list 0..(m_c-c) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
	     </#list> 
	      
        <collection property="${table.defaultObjectNames}" ofType="${table.entityPackageName}.${table.entityName}" column="${table.primaryKey.primaryKeyName}">   
        
              <#list table.columns as column >
              <#assign p1 = column.propertyLength ><#--   --> 
	          <#assign c1 = column.columnLength ><#--   -->
			  <result property="${column.propertyName}"<#list 0..(m_p1-p1) as k>${" "}</#list>column="${table.abbreviation}_${column.columnName }" ></result><#list 0..(m_p1-p1) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
		      </#list>
	    	 
        </collection>  
        
    </resultMap>  
	 
	 
	 <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄one2many查询条件┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
	 <sql id = "get${table.entityName}Sql" > 
	 <#assign mainTablePk = mainTable.primaryKey ><#--   -->
	         
			 ${mainTable.abbreviation}.${mainTablePk.primaryKeyName} = <#if mainTablePk.databaseDataType=="VARCHAR" || mainTablePk.databaseDataType=="CHAR" >  '${"#"}{${mainTable.primaryKey.primaryPropertyName}}'<#else>${"#"}{${mainTable.primaryKey.primaryPropertyName}}</#if> 
		     <#list table.foreignKeys as fk>
		     <#if fk.referencedRelationTable.tableName == mainTable.tableName>AND  ${mainTable.abbreviation}.${mainTablePk.primaryKeyName} = ${table.abbreviation}.${fk.fkColumnName}</#if>
		     </#list>
		     
			 <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ "${table.tableRemarks }" 查询条件┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
		     <if test="${table.defaultObjectName} != null">
			 <#list table.columns  as column><#t>
			 <#assign p1 = column.propertyLength ><#--   --> 
	         <#assign c1 = column.columnLength ><#--   -->
				    <#if column.databaseDataType =="VARCHAR" || column.databaseDataType=="CHAR" ><#t>
		     
				  <if test="${table.defaultObjectName}.${column.propertyName} != null"><#list 0..(m_p1-p1) as k>${" "}</#list>AND  ${table.abbreviation}.${column.columnName}<#list 0..(m_c1-c1) as k>${" "}</#list> LIKE '%${"$"}{${table.defaultObjectName}.${column.propertyName}}%'</if><#list 0..(m_p1-p1) as k>${" "}</#list><!--${column.columnRemarks}-->
				  
				    <#else><#t>
				  <if test="${table.defaultObjectName}.${column.propertyName} != null"><#list 0..(m_p1-p1) as k>${" "}</#list>AND  ${table.abbreviation}.${column.columnName}<#list 0..(m_c1-c1) as k>${" "}</#list> =      ${"#"}{${table.defaultObjectName}.${column.propertyName}}  </if><#list 0..(m_p1-p1) as k>${" "}</#list><!--${column.columnRemarks}--> 
			
				    </#if><#t> 
		    </#list><#lt> 
			</if>	 
	 
	 </sql>
	
	
	<!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄one2many查询语句┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
	<select id="select${mainEntityName}And${table.defaultObjectNames ? cap_first}" parameterType="map" resultMap="${mainEntityName}And${table.entityName}ResultMap">  <!-- 查询语句 -->
        SELECT <include refid="${mainEntityName}And${table.entityName}Columns"/>  
        FROM    ${mainTable.tableName} ${mainTable.abbreviation}  ,   ${table.tableName}  ${table.abbreviation} 
        WHERE  
	         <include refid="get${table.entityName}Sql"/>   
           
    </select>   
    
   
    <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ one2many分页查询总数的查询语句┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
    <select id="select${mainEntityName}And${table.defaultObjectNames ? cap_first}Size" parameterType="map" resultType="int">
	
	      SELECT  
	            COUNT(1)  
	      FROM  
	            ${mainTable.tableName} ${mainTable.abbreviation}  ,   ${table.tableName}  ${table.abbreviation} 
	      WHERE 
		       <include refid="get${table.entityName}Sql"/>     
		   
	</select>
    
    </#list>
	 
 </#if>
	  
	  
	 
	 <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄one2manySelf 部分  ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
	
	 <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄many2many 部分  ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
	<#if many2many.middleAndFkTables   ?? >  
	 <#list many2many.middleAndFkTables as middleAndFkTable> 
	 <#assign m_p1 = middleAndFkTable.fkTable.propertyMaxLength >  
	 <#assign m_c1 = middleAndFkTable.fkTable.columnMaxLength >
     <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄many2many 部分┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
	
	 <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄${mainTable.tableRemarks} ${middleAndFkTable.fkTable.tableRemarks} 字段别名 SQL 片段 ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
    
     <sql id="${mainEntityName}And${middleAndFkTable.fkTable.entityName}ManyColumn">  
	      
             
            <#list mainTable.columns as column >
            <#assign p = column.propertyLength >  
	        <#assign c = column.columnLength > 
            ${mainTable.abbreviation}.${column.columnName}<#list 0..(m_c-c) as k>${" "}</#list>AS    ${mainTable.abbreviation}_${column.columnName} ,<#list 0..(m_c-c) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
            </#list> 
            
			 
					<#list middleAndFkTable.fkTable.columns as column>
				    <#assign p1 = column.propertyLength >  
	                <#assign c1 = column.columnLength > 
				        <#if  !column_has_next>
		     ${middleAndFkTable.fkTable.abbreviation}.${column.columnName}<#list 0..(m_c1-c1) as k>${" "}</#list>AS    ${middleAndFkTable.fkTable.abbreviation}_${column.columnName}  <#list 0..(m_c1-c1) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
				        <#else>
		     ${middleAndFkTable.fkTable.abbreviation}.${column.columnName}<#list 0..(m_c1-c1) as k>${" "}</#list>AS    ${middleAndFkTable.fkTable.abbreviation}_${column.columnName} ,<#list 0..(m_c1-c1) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
				        </#if>
			        </#list>
			 
			
	 </sql>
	
     <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄返回结果集┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
  	 <resultMap id="${mainEntityName}And${middleAndFkTable.fkTable.entityName}ManyResultMap" type="${mainPackage}.${mainEntityName}" > <!-- ${mainTable.tableRemarks} ${middleAndFkTable.fkTable.tableRemarks}返回结果集 -->
	 
		<#list mainTable.columns as column >
		<#assign p = column.propertyLength >  
	    <#assign c = column.columnLength > 
		<result property="${column.propertyName}"<#list 0..(m_p-p) as k>${" "}</#list>column="${mainTable.abbreviation}_${column.columnName }" ></result><#list 0..(m_c-c) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
	    </#list> 
	      
        <collection property="${middleAndFkTable.fkTable.defaultObjectNames}" ofType="${middleAndFkTable.fkTable.entityPackageName}.${middleAndFkTable.fkTable.entityName}" >
          
        
			  <#list middleAndFkTable.fkTable.columns as column>
			  <#assign p1 = column.propertyLength >  
	          <#assign c1 = column.columnLength >
		      <result property="${column.propertyName}"<#list 0..(m_p1-p1) as k>${" "}</#list>column="${middleAndFkTable.fkTable.abbreviation}_${column.columnName }" ></result><#list 0..(m_c1-c1) as k>${" "}</#list><!--  ${column.columnRemarks} -->  
		      
		      </#list>
	    	 
        </collection>  
        
     </resultMap> 
     
     <!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄查询条件┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->	 
	 <sql id ="get${mainEntityName}And${middleAndFkTable.fkTable.entityName}Sql"> <!--查询条件 -->
	                                               <!-- "${mainTable.tableRemarks}" 查询条件 -->
	 <#list middleAndFkTable.middleTable.foreignKeys as middleTableFk>
	           <#if middleTableFk.referencedRelationTable.tableName == mainTable.tableName>
               ${middleAndFkTable.middleTable.abbreviation}.${middleTableFk.fkColumnName} = ${mainTable.abbreviation}.${mainTable.primaryKey.primaryKeyName}  
               AND
               <#else>
               ${middleAndFkTable.middleTable.abbreviation}.${middleTableFk.fkColumnName} = ${middleAndFkTable.fkTable.abbreviation}.${middleAndFkTable.fkTable.primaryKey.primaryKeyName }  
               AND
	           </#if>
     </#list>
			  ${mainTable.abbreviation}.${mainTable.primaryKey.primaryKeyName} = <#if mainTable.primaryKey.databaseDataType =="VARCHAR" || mainTable.primaryKey.databaseDataType=="CHAR" || mainTable.primaryKey.databaseDataType =="DATE"> '${"#"}{${mainEntityName}.${mainTable.primaryKey.primaryPropertyName}}'<#else>${"#"}{${mainEntityName}.${mainTable.primaryKey.primaryPropertyName}}</#if> 
		      
	 
			<if test = "${middleAndFkTable.fkTable.defaultObjectName} != null"><!-- "${middleAndFkTable.middleTable.tableRemarks}" 查询条件 -->  
			 <#list middleAndFkTable.fkTable.columns  as column><#t>
			        <#assign p1 = column.propertyLength >  
	                <#assign c1 = column.columnLength >
				    <#if column.databaseDataType == "VARCHAR" || column.databaseDataType == "CHAR" ><#t>
				  <if test="${middleAndFkTable.middleTable.defaultObjectName}.${column.propertyName} != null"><#list 0..(m_p1-p1) as k>${" "}</#list>AND  ${middleAndFkTable.middleTable.abbreviation}.${column.columnName}<#list 0..(m_c1-c1) as k>${" "}</#list>LIKE '%${"$"}{${middleAndFkTable.middleTable.defaultObjectName}.${column.propertyName}}%'</if><#list 0..(m_p1-p1) as k>${" "}</#list><!--${column.columnRemarks}-->
				  
				    <#else><#t>
				  <if test="${middleAndFkTable.middleTable.defaultObjectName}.${column.propertyName} != null"><#list 0..(m_p1-p1) as k>${" "}</#list>AND  ${middleAndFkTable.middleTable.abbreviation}.${column.columnName}<#list 0..(m_c1-c1) as k>${" "}</#list>=      ${"#"}{${middleAndFkTable.middleTable.defaultObjectName}.${column.propertyName}}  </if><#list 0..(m_p1-p1) as k>${" "}</#list><!--${column.columnRemarks}--> 
				    </#if><#t> 
		    </#list><#lt>
			</if>	 
	 </sql>
	 
<!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄查询语句┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->	
	<select id="select${mainEntityName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}" parameterType="map" resultMap="${mainEntityName}And${middleAndFkTable.fkTable.entityName}ManyResultMap">  <!-- 查询语句 -->
        SELECT 
        	  <include refid="${mainEntityName}And${middleAndFkTable.fkTable.entityName}ManyColumn"/>  
        FROM
              ${mainTable.tableName}  ${mainTable.abbreviation} ,
              ${middleAndFkTable.middleTable.tableName} ${middleAndFkTable.middleTable.abbreviation},
	          ${middleAndFkTable.fkTable.tableName} ${middleAndFkTable.fkTable.abbreviation}
             
        WHERE 
              
		       <include refid="get${mainEntityName}And${middleAndFkTable.fkTable.entityName}Sql"/>  
		       LIMIT ${"#"}{index},${"#"}{size}	
		      
		      
    </select>
<!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄查询总数的查询语句 ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
    <select id="select${mainEntityName}And${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}Size" parameterType="map" resultType="int">
	
	      SELECT  
	            COUNT(1)  
	      FROM 
	          ${mainTable.tableName}  ${mainTable.abbreviation} ,
              ${middleAndFkTable.middleTable.tableName} ${middleAndFkTable.middleTable.abbreviation},
	          ${middleAndFkTable.fkTable.tableName} ${middleAndFkTable.fkTable.abbreviation}
	      WHERE 
		       <include refid="get${mainEntityName}And${middleAndFkTable.fkTable.entityName}Sql"/>     
		   
	</select>
    
    
	 </#list>
 </#if>
      
      
	<!--┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄many2manySelf 部分┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄-->
	
	
</mapper>
 
