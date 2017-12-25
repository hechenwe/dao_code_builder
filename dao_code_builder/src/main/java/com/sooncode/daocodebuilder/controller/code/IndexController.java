package com.sooncode.daocodebuilder.controller.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sooncode.daocodebuilder.entity.module.Module;
import com.sooncode.daocodebuilder.entity.module.ModuleBuilder;
import com.sooncode.daocodebuilder.entity.table.Table;
import com.sooncode.daocodebuilder.entity.table.TableBuilder;
import com.sooncode.daocodebuilder.entity.util.DBConnectionUtils;
import com.sooncode.daocodebuilder.entity.util.config.DBConstant;
import com.sooncode.daocodebuilder.entity.view.View;
import com.sooncode.daocodebuilder.entity.view.ViewBuilder;
import com.sooncode.daocodebuilder.service.build.DaoCodeGenerator;
import com.sooncode.daocodebuilder.service.build.Project;
import com.sooncode.daocodebuilder.service.build.view.ViewGenerator;
import com.sooncode.daocodebuilder.util.PathUtil;

/**
 * 
 * @author hechen
 *
 */
@Controller()
@RequestMapping("/indexController")
public class IndexController {

	private static Logger logger = Logger.getLogger("IndexController.class");
	
    @Autowired
	private DaoCodeGenerator daoCodeGenerator ;
    @Autowired
    private ViewGenerator viewGenerator ;
	
	/**
	 * 跳转到首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
		Map<String, Object> map = new HashMap<String, Object>();
		 
		return new ModelAndView("index", map);
	}

	/**
	 * 跳转到数据库表列表页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tableList")
	public ModelAndView tableList(HttpServletRequest request, HttpServletResponse response) throws Exception {
         
		String project =  request.getParameter("project");
		String domainName =  request.getParameter("domainName");
		Project.projectName = project;
		Project.packagePrefixion = domainName;
		
		Map<String, Object> map = new HashMap<String, Object>();
		long t1 = System.currentTimeMillis();
		Map<String, Table> tablesCache = TableBuilder.getTablesCache();
		long t2 = System.currentTimeMillis();
		logger.info("获取数据库的所用表 耗时:"+ (t2-t1));
		List<Table> tables = new ArrayList<>();
		for (Map.Entry<String, Table> entry : tablesCache.entrySet()) {
			tables.add(entry.getValue());

		}
		map.put("tables", tables);
		long t3 = System.currentTimeMillis();
		List<Module> modules = ModuleBuilder.moduleBuilder(tables);
		long t4 = System.currentTimeMillis();
		logger.info("获取数据库模块 耗时:"+(t4-t3));
		for (Module module : modules) {
			int n = 0;
			List<Table> listes = new ArrayList<>();
			for (Table table : tables) {
				if(table.getModuleName() .equals(module.getModuleName())){
					n++;
					listes.add(table);
				}
			}
			module.setTables(listes);
			module.setTableSize(n);
		}
		
		
		
		map.put("modules", modules);
		
		//----------------------获取视图 
		long t5 = System.currentTimeMillis();
		List<View> viewes = ViewBuilder.getViewes();
		long t6 = System.currentTimeMillis();
		logger.info("获取视图 耗时:"+(t6-t5));
		map.put("viewes",viewes);
		return new ModelAndView("tableList", map);
	}

	/**
	 * 测试连接数据库
	 * @return
	 */
	@RequestMapping(value = "/testConnect",method =RequestMethod.POST)
	// @RequestMapping(params = "testConnect")
	@ResponseBody

	public String testConnect(HttpServletRequest request) {

		String codeFilePath = PathUtil.getSrc()+"code";
		
		//logger.info(codeFilePath);
		String ip = request.getParameter("ip");
		String port = request.getParameter("port");
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String databaseName = request.getParameter("databaseName");
		boolean bool = DBConnectionUtils.testConnection(ip, port, user, password, databaseName);

		if (bool) {

			DBConstant.IP = ip;
			DBConstant.PORT = port;
			DBConstant.USER = user;
			DBConstant.PASSWORD = password;
			DBConstant.DATA_BASE = databaseName;

			//清空缓存
			if(TableBuilder.tablesCache != null){
				
				TableBuilder.tablesCache= null;
			}
			 
			
			return "YES";
		} else {
			return "NO";
		}
	}
	/**
	 * 生成表的相关代码
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/builderCode")
	// @RequestMapping(params = "testConnect")
	@ResponseBody
	
	public String builderCode(HttpServletRequest request) throws Exception {
		//logger.info("build");
		String tableName = request.getParameter("tableName");
		String type = request.getParameter("type");
		String types [] = type.split("-");
		
		Table mainTable = TableBuilder.getTablesCache().get(tableName.toUpperCase());
		boolean bool = true;
		try {
			 
			for (String string : types) {
				bool = bool && daoCodeGenerator.outputByTabaleAndEntityMap(mainTable,string);
				
			}
			
		} catch (Exception e) {
			bool =  false;
			e.printStackTrace();
		}
		 
		if(bool){
			return "YES";
		}else{
			return "NO";
		}
	}
	/**
	 * 生成视图的相关代码
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/builderViewCode")
	// @RequestMapping(params = "testConnect")
	@ResponseBody
	
	public String builderViewCode(HttpServletRequest request) throws Exception {
		 
		String viewCode = request.getParameter("viewCode");
		String type = request.getParameter("type");
		String types [] = type.split("-");
		boolean bool = true;
		
		for (String typ : types) {
			Map<String,View> viewMap = ViewBuilder.getViewesMap();
			bool = bool && viewGenerator.outputByView(viewMap.get(viewCode), typ);
		}
		
		if(bool){
			return "YES";
		}else{
			return "NO";
		}
		
	}

	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = new User();
		user.setUserName(username);
		user.setUserPwd(password);
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("user", user);
		map.put("request", "1");

		return map;
	}
	/**
	 * 跳转到首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/text")
	public ModelAndView text(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/* TableAndEntityBuilder teb = new TableAndEntityBuilder(); */
		Map<String, Object> map = new HashMap<String, Object>();
		/* map.put("modules", teb.getAllModule("dao")); */
		return new ModelAndView("text", map);
	}
}
