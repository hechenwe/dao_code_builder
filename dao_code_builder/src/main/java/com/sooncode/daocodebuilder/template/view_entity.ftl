package ${packageName};

/**
 *  ${view.viewCode} 视图 类
 * 
 *  @author HeChen 
 *
 */
 
public class ${view.viewCode} implements java.io.Serializable{

    /** 序列化  */
    private static final long serialVersionUID = 1L; 
    
   
 <#list view.columns as column> 
    /**${column.columnRemarks}*/
    private ${column.javaDataType} ${column.propertyName}; 
    
    
 </#list>
  
 
 <#list view.columns as column>  
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
  

}
