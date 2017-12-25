package com.sooncode.daocodebuilder.service.build;

import com.sooncode.daocodebuilder.entity.table.Table;
import com.sooncode.daocodebuilder.entity.view.View;
import com.sooncode.daocodebuilder.service.output.OutPutFileConstant;

/**
 * 工程属性
 * 
 * @author 臣
 *
 */
public class Project {

	/** 工程名称 */
	public static String projectName;//  PROJECT_NAME  ; // 如: daocodebuilder

	
	/** 工作空间 */
	//public static   String  PROJECT_WORKSPACE ; //D:\workspace\springmvc_freemarker_mybaties\goodstudent
    public static final String projectWorkspace = OutPutFileConstant.OUTPUT_PATH;
	
	/** 包前缀(一般是公司域名的倒写, 如:com.sooncode) */
	//public static    String  PACKAGE_PREFIXION ; //
    public static String packagePrefixion;
    
	/** 
	 * 包前缀对应的路径(一般是公司域名的倒写, 如:com/sooncode) 
	 */
	//public  static  String   PACKAGE_PREFIXION_TO_PATH ="cn/yjkjedu";
	/**
	 * 公共部分DAO代码
	 */
    private  final String COMMON_PART_NAME = "common";
	private  final String DAO = "dao";
	private  final String ENTITY= "entity";
	private  final String SRC= "src";
	private  final String TEST= "test";
	private  final String MAPPING= "mapping";
	private  final String INTERFACES= "interfaces";
	private  final String SERVICE= "service";
	
	private  final String UTIL="util";
	
	
	private   String ppc ;//= getPackagePrefixion() + "." + PROJECT_NAME + "." + COMMON_PART_NAME;
	private   String ppsppc ;// = getPackagePrefixion() + "/" + PROJECT_NAME + "/"+SRC+"/"+getPackgePrefixionToPath()+"/"+PROJECT_NAME+"/"+COMMON_PART_NAME;
	private   String pptppc  ;//= getPackagePrefixion() + "/" + PROJECT_NAME + "/"+TEST+"/"+getPackgePrefixionToPath()+"/"+PROJECT_NAME+"/"+COMMON_PART_NAME;  
	// ----------------------------------------------------------------------------------------------------------------------------------
	private  String  getPackgePrefixionToPath() {

		String packagePrefixion2PathString = "";
		String[] str = packagePrefixion.split("\\.");
		for (int i = 0; i < str.length; i++) {
			if (i != str.length - 1) {
				packagePrefixion2PathString = packagePrefixion2PathString + str[i] + "/";
			} else {
				packagePrefixion2PathString = packagePrefixion2PathString + str[i];
			}
		}
		return packagePrefixion2PathString;
	}

	
	
	
	// -------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 获取实体的包名 
	 * @param mainTable
	 * @return
	 */
	public   String getEntityPackageName(Table mainTable) {
		return getPpc() + "."+ENTITY+"." + mainTable.getModuleName().toLowerCase();
	}
	
	
	/**
	 * 获取*DaoInterface包名
	 * @param mainTable
	 * @return
	 */
	public  String getDaoMappingPackageName (Table mainTable){
		return getPpc() +"."+DAO+"." + mainTable.getModuleName().toLowerCase() + "."+MAPPING+"." + mainTable.getEntityName().toLowerCase();
	}
	
	/**
	 * 获取common下 util包名 
	 * @param className
	 * @return
	 */
	public  String getCommonUtilPackageName (String className){
		return getPpc()+"." + UTIL +"."+className;
	}
	/**
	 * 获取*Dao包名
	 * @param mainTable
	 * @return
	 */
	public  String getDaoPackageName (Table mainTable){
		return getPpc()+"."+DAO+"." + mainTable.getModuleName().toLowerCase() + "."+INTERFACES+"." + mainTable.getEntityName().toLowerCase();
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 获取*Service包名
	 * @param mainTable
	 * @return
	 */
	public  String getServicePackageName (Table mainTable){
		return getPpc()+"."+SERVICE+"." + mainTable.getModuleName().toLowerCase() + "."+ mainTable.getEntityName().toLowerCase();
	}
	
	/**
	 *  interface.xml 和   interface.java 输出路径
	 * @param mainTable
	 * @return
	 */
	public  String getDaoMappingOutPutPath(Table mainTable){
		return getPpsppc() +"/"+DAO+"/" + mainTable.getModuleName().toLowerCase() + "/"+MAPPING+"/" + mainTable.getEntityName().toLowerCase();
	}
	/**
	 * 实体类输出路径
	 * @param mainTable
	 * @return
	 */
	public  String getEntityOutPutPath(Table mainTable){
		return getPpsppc()+"/"+ENTITY+"/" + mainTable.getModuleName().toLowerCase();
	}
	
	/**
	 *    some-DaoInterface测试类输出路径
	 * @param mainTable
	 * @return
	 */
	public  String getTestDaoInterfaceOutPutPath(Table mainTable){
		return getPptppc()+"/"+DAO+"/" + mainTable.getModuleName().toLowerCase()+ "/"+MAPPING+"/" + mainTable.getEntityName().toLowerCase();
	}
	
	/**
	 * Dao接口类输出路径
	 * @param mainTable
	 * @return
	 */
	public  String getDaoInterfacesOutPutPath(Table mainTable){
		return getPpsppc()+"/"+DAO+"/" + mainTable.getModuleName().toLowerCase()+ "/"+INTERFACES+"/" + mainTable.getEntityName().toLowerCase();
	}
	/**
	 * Dao测试类输出路径
	 * @param mainTable
	 * @return
	 */
	public  String getTestDaoOutPutPath(Table mainTable){
		return getPptppc()+"/"+DAO+"/" + mainTable.getModuleName().toLowerCase()+ "/"+INTERFACES+"/" + mainTable.getEntityName().toLowerCase();
	}
	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Dao接口类输出路径
	 * @param mainTable
	 * @return
	 */
	public  String getServiceOutPutPath(Table mainTable){
		return getPpsppc()+"/"+SERVICE+"/" + mainTable.getModuleName().toLowerCase() + "/" + mainTable.getEntityName().toLowerCase();
	}

	
	
	public String getPagerPackage(){
		return getPpc()+"."+ UTIL;
	}
	
	
	
	
	public String getProjectName() {
		return projectName;
	}






	public String getPpc() {
		
		ppc = packagePrefixion + "." + getProjectName() + "." + COMMON_PART_NAME;
		
		return ppc;
	}




	public String getPpsppc() {
		ppsppc = projectWorkspace + "/" + getProjectName() + "/"+SRC+"/"+getPackgePrefixionToPath()+"/"+getProjectName()+"/"+COMMON_PART_NAME;
		return ppsppc;
	}




	public String getPptppc() {
		pptppc = projectWorkspace + "/" +  getProjectName() + "/"+TEST+"/"+getPackgePrefixionToPath()+"/"+ getProjectName()+"/"+COMMON_PART_NAME;
		return pptppc;
	}
	
	//-------------------------视图------------------------------------
	
	/**
	 * 获取视图实体的包名 
	 * @param mainTable
	 * @return
	 */
	public   String getViewEntityPackageName(View view) {
		return getPpc() + "."+ENTITY+"." + "view." + view.getViewCode().toLowerCase();
	}
	/**
	 * 视图实体类输出路径
	 * @param mainTable
	 * @return
	 */
	public  String getViewEntityOutPutPath(View view){
		return getPpsppc()+"/"+ENTITY+"/" + "view" +"/" + view.getViewCode().toLowerCase() ;
	}
	/**
	 * 视图SQL映射文件输出路径
	 * @param mainTable
	 * @return
	 */
	public  String getViewSqlOutPutPath(View view){
		return getPpsppc() +"/"+DAO+"/" + "view" + "/"+MAPPING+"/" + view.getViewCode().toLowerCase();//getPpsppc()+"/"+ENTITY+"/" + "view" +"/" + view.getViewCode().toLowerCase() ;
	}
	
	
}
