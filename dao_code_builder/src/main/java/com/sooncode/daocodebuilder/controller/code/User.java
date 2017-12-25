package com.sooncode.daocodebuilder.controller.code;


 

  


/**********************************************************************************************************************************************************************************
 * 平台用户表 
 * 
 * @author  
 *
 *********************************************************************************************************************************************************************************/
 
public class User implements java.io.Serializable{

    /** 序列化  */
    private static final long serialVersionUID = 1L; 
    
     
    /**用户id*/
    private Long userId; 
    
     
    /**角色id*/
    private Long roleId; 
    
     
    /**用户名*/
    private String userName; 
    
     
    /**密码*/
    private String userPwd; 
    
     
    /**用户状态*/
    private String status; 
    
     
    /**创建时间*/
    private java.sql.Timestamp createTime; 
    
    
    
	

 
    /*===============================================================get,set 方法===================================================================*/
    /**用户id*/ 
    public Long getUserId(){  
      return userId;  
    }  
     /**用户id*/
    public void setUserId(Long userId){  
      this.userId = userId;  
    } 
    /*------------------------------------ -----------------------------*/
    /**角色id*/ 
    public Long getRoleId(){  
      return roleId;  
    }  
     /**角色id*/
    public void setRoleId(Long roleId){  
      this.roleId = roleId;  
    } 
    /*------------------------------------ -----------------------------*/
    /**用户名*/ 
    public String getUserName(){  
      return userName;  
    }  
     /**用户名*/
    public void setUserName(String userName){  
      this.userName = userName;  
    } 
    /*------------------------------------ -----------------------------*/
    /**密码*/ 
    public String getUserPwd(){  
      return userPwd;  
    }  
     /**密码*/
    public void setUserPwd(String userPwd){  
      this.userPwd = userPwd;  
    } 
    /*------------------------------------ -----------------------------*/
    /**用户状态*/ 
    public String getStatus(){  
      return status;  
    }  
     /**用户状态*/
    public void setStatus(String status){  
      this.status = status;  
    } 
    /*------------------------------------ -----------------------------*/
    /**创建时间*/ 
    public java.sql.Timestamp getCreateTime(){  
      return createTime;  
    }  
     /**创建时间*/
    public void setCreateTime(java.sql.Timestamp createTime){  
      this.createTime = createTime;  
    } 
    /*------------------------------------ -----------------------------*/
  
    
	/*------------------------------------ -----------------------------*/
 
 
 
 /*============================================================ toString 方法 ====================================================================================*/
     @Override
	 public String toString() {
		return  "User : 平台用户表["+
		        " ;用户id:userId = " + userId +  
		        " ;角色id:roleId = " + roleId +  
		        " ;用户名:userName = " + userName +  
		        " ;密码:userPwd = " + userPwd +  
		        " ;用户状态:status = " + status +  
		        " ;创建时间:createTime = " + createTime + "]" ;
	}
 
}
