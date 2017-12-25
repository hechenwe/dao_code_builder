<#include "/com/variable.ftl"><#--引入全局变量-->
package ${mainPackage};
 
import java.util.List;
 
<#if mainTable.allClassName??> 
  <#list mainTable.allClassName?keys as key> 
import ${mainTable.allClassName[key]};
  </#list>      
</#if>
/**
 *  ${mainTable.tableRemarks} 
 * 
 *  @author 代码生成器 
 *
 */
 
public class ${mainTable.entityName} implements java.io.Serializable{

    /** 序列化  */
    private static final long serialVersionUID = 1L; 
    
   
 <#list mainTable.columns as column> 
    /**${column.columnRemarks}*/
    private ${column.javaDataType} ${column.propertyName}; 
    
    
 </#list>
 <#------------------------------------------------------- one2one 联系---------------------------------------------------------------- -->
 <#if one2one.tables ?? >  
	 <#list one2one.tables as table>
    /**${table.tableRemarks}*/
	private ${table.entityName} ${table.defaultObjectName} ;
	
	
	 </#list>
 </#if>
 <#---------------------------------------------------- one2oneSelf 联系 --------------------------------------------------->
  
 <#if one2oneSelf.foreignKeys ?? >  
	 <#list one2oneSelf.foreignKeys as fk>
    /**${fk.foreignKeyRemarks}*/
	private ${fk.referencedRelationTable.entityName} ${fk.foreignPropertyName} ;
	
	
	 </#list>
 </#if>
 
 <#--------------------------------------------------- one2oneMiddle 联系--------------------------------------------------->
 
 <#if one2oneMiddle.middleTables ?? >  
	 <#list one2oneMiddle.middleTables as middleTable>
	 	<#list middleTable.foreignKeys as fk >
		 	<#if fk.fkColumnName != mainTable.primaryKey.primaryKeyName>
    /** ${fk.foreignKeyRemarks} */
	private ${mainTable.entityName} ${fk.foreignPropertyName} ;
	
	
		 	</#if>
	 	</#list>
	 </#list>
 </#if>
 <#--------------------------------------------------- one2many 联系 --------------------------------------------------->
 
 <#if one2many.tables ?? >  
	 <#list one2many.tables as table>
    /** ${table.tableRemarks} */
	private  List<${table.entityName}> ${table.defaultObjectNames} ;
	
		 
	 </#list>
 </#if>
 
 <#--------------------------------------------------- one2manySelf 联系 --------------------------------------------------->
 <#if one2manySelf.exportedKeys ?? >  
	 <#list one2manySelf.exportedKeys as ek>
    /** ${ek.exportedTable.tableRemarks} */
	private  List<${ek.exportedTable.entityName}> ${ek.exportedTable.defaultObjectNames} ;
	
	 	 
	 </#list>
 </#if>
 
 <#-- many2many 联系 -->
 <#if many2many.middleAndFkTables   ?? >  
	 <#list many2many.middleAndFkTables as middleAndFkTable>
    /** ${middleAndFkTable.fkTable.tableRemarks} */
	private  List<${middleAndFkTable.fkTable.entityName}> ${middleAndFkTable.fkTable.defaultObjectNames} ;
	 
	 	 
	 </#list>
 </#if>
 <#-- many2manySelf 联系 -->
  
 <#if many2manySelf.middleTables ?? >  
	 <#list many2manySelf.middleTables as middleTable>
	 	<#list middleTable.foreignKeys as fk >
		 	<#if fk.fkColumnName != mainTable.primaryKey.primaryKeyName>
    /** ${fk.foreignKeyRemarks} */
	private List<${mainTable.entityName}> ${fk.foreignPropertyNames} ;
	
	
	        <#break>
		 	</#if>
	 	</#list>
	 </#list>
 </#if>
                                                               
   
 <#--------------------------------------------------- column 属性 get set 方法 ------------------------------------------------------------开始 -->
    //------------------------get,set 方法----------------------------
 
 
 <#list mainTable.columns as column>  
    /**${column.columnRemarks}*/ 
    public ${column.javaDataType} get${column.propertyName?cap_first}(){  
      return ${column.propertyName};  
    }  
     /**${column.columnRemarks}*/
    public void set${column.propertyName?cap_first}(${column.javaDataType} ${column.propertyName}){  
      this.${column.propertyName} = ${column.propertyName};  
    } 
    
    
   //----------------------------------------------------------------
   
   
  </#list> 
<#------------------------------------------------------------------ column 属性 get set 方法 ------------------------------------------------------------结束 --> 
 
 
<#------------------------------------------------------------------ one2one  属性 get set 方法 ------------------------------------------------------------开始 -->
  
<#if one2one.tables ?? >  
	 <#list one2one.tables as table> 
    /**${table.tableRemarks}*/ 
    public ${table.entityName} get${table.defaultObjectName ? cap_first}(){  
      return this.${table.defaultObjectName};  
    }  
    /**${table.tableRemarks}*/
    public void set${table.defaultObjectName ? cap_first}(${table.entityName} ${table.defaultObjectName}){  
      this.${table.defaultObjectName} = ${table.defaultObjectName};  
    } 
    
    
    //----------------------------------------------------------------
  
  
	  </#list> 
 </#if>
 <#--------------------------------------------------- one2one 属性 get set 方法 ------------------------------------------------------------结束 --> 
 <#--------------------------------------------------- one2oneSelf  属性 get set 方法 ------------------------------------------------------------开始 -->
 
 
   
 <#if one2oneSelf.foreignKeys ?? >  
	 <#list one2oneSelf.foreignKeys as fk>
	 /**${fk.foreignKeyRemarks}*/ 
    public ${fk.referencedRelationTable.entityName} get${fk.foreignPropertyName ? cap_first}(){  
      return this.${fk.foreignPropertyName};  
    }  
    /**${fk.foreignKeyRemarks}*/
    public void set${fk.foreignPropertyName ? cap_first}(${fk.referencedRelationTable.entityName} ${fk.foreignPropertyName}){  
      this.${fk.foreignPropertyName} = ${fk.foreignPropertyName};  
    } 
    
    
    //----------------------------------------------------------------
    
    
    
	 </#list>
 </#if>
<#---------------------------------------------------------------- one2oneSelf  属性 get set 方法 ------------------------------------------------------------结束 -->  
 
<#-- ---------------------------------------------------------------one2oneMiddle 联系 --------------------------------------------------------------------------->
<#-------------------------------------------------------------- one2oneMiddle  属性 get set 方法 ------------------------------------------------------------开始 --> 
 
 <#if one2oneMiddle.middleTables ?? >  
	 <#list one2oneMiddle.middleTables as middleTable>
	 	<#list middleTable.foreignKeys as fk >
		 	<#if fk.fkColumnName != mainTable.primaryKey.primaryKeyName>
		 	
		 	
	/**${fk.foreignKeyRemarks}*/ 
    public ${mainTable.entityName} get${fk.foreignPropertyName ? cap_first}(){  
      return this.${fk.foreignPropertyName};  
    }  
    /**${fk.foreignKeyRemarks}*/
    public void set${fk.foreignPropertyName ? cap_first}(${mainTable.entityName} ${fk.foreignPropertyName}){  
      this.${fk.foreignPropertyName} = ${fk.foreignPropertyName};  
    } 	
    
     
    //----------------------------------------------------------------
    
    	
		 	</#if>
	 	</#list>
	 </#list>
 </#if>
<#----------------------------------------------------------------------- one2oneMiddle  属性 get set 方法 ------------------------------------------------------------结束 -->
  
<#----------------------------------------------------------------------- one2many  属性 get set 方法 -----------------------------------------------------------------开始 -->  
 
 <#if one2many.tables ?? >  
	 <#list one2many.tables as table>
	 
	
	/**${table.tableRemarks}*/ 
    public List<${table.entityName}> get${table.defaultObjectNames ? cap_first}(){  
      return this.${table.defaultObjectNames};  
    }  
    /**${table.tableRemarks}*/
    public void set${table.defaultObjectNames? cap_first}(List<${table.entityName}> ${table.defaultObjectNames}){  
      this.${table.defaultObjectNames} = ${table.defaultObjectNames};  
    } 
    
    
    //----------------------------------------------------------------
    
    	 	 
	 </#list>
 </#if>
                               <#-------------------- one2many  属性 get set 方法 -------------------------结束 -->
  
                               <#-------------------- one2manySelf  属性 get set 方法 ---------------------开始 --> 
 
<#if one2manySelf.exportedKeys ?? >  
	 <#list one2manySelf.exportedKeys as ek>
	/**${ek.exportedTable.tableRemarks}*/ 
    public List<${ek.exportedTable.entityName}> get${ek.exportedTable.defaultObjectNames ? cap_first}(){  
      return this.${ek.exportedTable.defaultObjectNames};  
    }  
    /**${ek.exportedTable.tableRemarks}*/
    public void set${ek.exportedTable.defaultObjectNames ? cap_first}(List<${ek.exportedTable.entityName}> ${ek.exportedTable.defaultObjectNames}){  
      this.${ek.exportedTable.defaultObjectNames} = ${ek.exportedTable.defaultObjectNames};  
    } 
    
    
    //----------------------------------------------------------------
    
    	 
	 </#list>
 </#if>
<#-- one2manySelf  属性 get set 方法 -----------结束 -->  

<#-- many2many  属性 get set 方法 -----------开始 -->

<#if many2many.middleAndFkTables   ?? >  
	 <#list many2many.middleAndFkTables as middleAndFkTable>
	/**${middleAndFkTable.fkTable.tableRemarks}*/ 
    public List<${middleAndFkTable.fkTable.entityName}> get${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}(){  
      return this.${middleAndFkTable.fkTable.defaultObjectNames};  
    }  
    /**${middleAndFkTable.fkTable.tableRemarks}*/
    public void set${middleAndFkTable.fkTable.defaultObjectNames ? cap_first}(List<${middleAndFkTable.fkTable.entityName}> ${middleAndFkTable.fkTable.defaultObjectNames}){  
      this.${middleAndFkTable.fkTable.defaultObjectNames} = ${middleAndFkTable.fkTable.defaultObjectNames};  
    } 
    
    
    //----------------------------------------------------------------
    
     	 
	 </#list>
 </#if>
<#--------------------------------------------------- many2many  属性 get set 方法 ------------------------------------------------------------结束 -->  

<#--------------------------------------------------- many2manySelf  属性 get set 方法 ------------------------------------------------------------开始 --> 

<#if many2manySelf.middleTables ?? >  
	 <#list many2manySelf.middleTables as middleTable>
	 	<#list middleTable.foreignKeys as fk >
		 	<#if fk.fkColumnName != mainTable.primaryKey.primaryKeyName>
	/**${fk.foreignKeyRemarks}*/ 
    public List<${mainTable.entityName}> get${fk.foreignPropertyNames ? cap_first}(){  
      return this.${fk.foreignPropertyNames};  
    }  
    /**${fk.foreignKeyRemarks}*/
    public void set${fk.foreignPropertyNames ? cap_first}(List<${mainTable.entityName}> ${fk.foreignPropertyNames}){  
      this.${fk.foreignPropertyNames} = ${fk.foreignPropertyNames};  
    }
    
      
    //----------------------------------------------------------------
    	
    	
	        <#break>
		 	</#if>
	 	</#list>
	 </#list>
 </#if>
<#----------------------------------------------------------------- many2manySelf  属性 get set 方法 ----------------------------------结束 -->  

<#-------------------------------------------------------------------------- toString()方法 -------------------------------------------------> 
     //----------------------------------------------------------------
     @Override
	 public String toString() {
		return  "${mainTable.entityName} : ${mainTable.tableRemarks}["+
			    <#list mainTable.columns as column > 
		        <#if ! column_has_next>
		        " ;${column.columnRemarks}:${column.propertyName} = " + ${column.propertyName} + "]" ;
		        <#else>
		        " ;${column.columnRemarks}:${column.propertyName} = " + ${column.propertyName} +  
		        </#if>
		        </#list>
	}
 

}
