<#assign viewName = view.viewCode ?capitalize ><#-- 视图名称 -->
<#assign viewClassName = view.viewCode ?cap_first ><#-- 视图对应的类名 -->
<#assign viewObjectName = view.viewCode ><#-- 视图对应的对象名 -->
<#assign packageName = packageName ><#-- 视图对应的对象名 -->
package ${packageName};

import java.util.List;
import java.util.Map;
 
import ${packageName}.${viewClassName};

/**
 *  ${viewObjectName} 视图 
 * 
 *  @author HeChen 
 *
 */
public interface ${viewClassName}DaoInterface {
    
    /**
     * 分页查询 ${viewClassName}
     * @param map 封装了查询条件的${viewClassName}对象,和 分页索引index,分页大小 size 的 Map.
     * @return List<${viewClassName}>  集合
     */
	public List<${viewClassName}> select${viewClassName}Pager(Map<String,Object> map);
    
    /**
     * 按照条件查询 ${viewClassName}的记录总数
     * @param  map  封装了查询条件${viewObjectName}的Map
     * @return int 记录的总数
     */
	public int ${viewObjectName}Size(Map<String,Object> map);
    
}

