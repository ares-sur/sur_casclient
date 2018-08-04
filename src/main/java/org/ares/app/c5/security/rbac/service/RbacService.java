package org.ares.app.c5.security.rbac.service;

import java.util.List;
import java.util.Map;

/**
 * 没有大规模使用，本意是形成一个中间层，用来决定如何获取rbac的数据【1.直接从db 2.从缓存 3.从ldap等等】
 * @author Administrator
 *
 */
public interface RbacService {

	/**
	 * Map contain keys {resurl,rolename}
	 * @return
	 */
	List<Map<String,String>> getResOfRole();
	
	/**
	 * Map contain keys {username,rolename}
	 * @return
	 */
	List<Map<String,String>> getRoleAuthByUsername(String username);
	
}
