package com.sooncode.daocodebuilder.service.output;

import com.sooncode.daocodebuilder.util.PathUtil;

/**
 * 输出文件描述
 * @author hechen
 *
 */
public class OutPutFileConstant {
public static final String DAO_INTERFACE ="DaoInterface"; 
public static final String DAO_INTERFACE_JAVA = DAO_INTERFACE+".java";
public static final String DAO_INTERFACE_XML = DAO_INTERFACE+".xml";
public static final String ENTITY_JAVA = ".java";
public static final String DAO_INTERFACE_TEST_JAVA = DAO_INTERFACE+"Test.java";

public static final String ENTITY_DAO_JAVA = "Dao.java";
public static final String TEST_DAO_JAVA = "DaoTest.java";

public static final String SERVICE_JAVA = "Service.java";

public static final String OUTPUT_PATH = PathUtil.getSrc()+"code";

}
