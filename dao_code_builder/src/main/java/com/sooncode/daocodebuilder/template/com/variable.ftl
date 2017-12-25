<#-- 全局变量  -->
<#assign mainTable = MAIN_TABLE >                  
<#assign one2one = ONE_TO_ONE >                     <#-- 主表的1:1模型               -->
<#assign one2oneSelf = ONE_TO_ONE_SELF>             <#-- 主表的 1:1自连接 模型 -->
<#assign one2oneMiddle = ONE_TO_ONE_MIDDLE>         <#-- 主表的 1:1自连接 模型 -->
<#assign one2many = ONE_TO_MANY>                    <#-- 主表的 1:n 模型           -->
<#assign one2manySelf = ONE_TO_MANY_SELF>           <#-- 主表的 1:n自连接 模型 -->
<#assign many2many = MANY_TO_MANY>			        <#-- 主表的 m:n 模型           -->
<#assign many2manySelf = MANY_TO_MANY_SELF>         <#-- 主表的 m:n自连接 模型 -->
<#assign mainPackage = mainTable.entityPackageName> <#-- 主实体包                            -->
 
<#assign mainEntityName = mainTable.entityName ><#-- 主表模型 -->
<#assign mainColumns = mainTable.columns ><#-- 主表模型 -->
 
<#assign someClassName = mainTable.entityName >           <#-- 类名 -->
<#assign someObjectName = mainTable.defaultObjectName >   <#-- 对象名 -->
 