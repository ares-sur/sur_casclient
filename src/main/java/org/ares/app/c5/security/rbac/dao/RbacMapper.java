package org.ares.app.c5.security.rbac.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.ares.app.c5.security.rbac.model.SRes;
import org.ares.app.c5.security.rbac.model.SRole;
import org.ares.app.c5.security.rbac.model.SUser;
import org.ares.app.c5.security.rbac.service.RbacService;

/**
 * select="getRolesByUsername" can't write package names
 * @author ly
 */
/*@Repository*/
@Mapper
public interface RbacMapper extends RbacService {

	@Select("select res.resurl,r.rolename from s_role r join s_role_res rr on r.roleid=rr.roleid join s_res res on res.resid=rr.resid order by res.resurl")
	List<Map<String,String>> getResOfRole();
	
	@Select("select u.userid username,r.rolename as authority from s_user u join s_user_role ur on u.userid=ur.userid join s_role r on r.roleid=ur.roleid where u.userid=#{username}")
	List<Map<String,String>> getRoleAuthByUsername(String username);
	
	
	@Select("select * from s_user where userid = #{username}")
	@Results({
		@Result(property="roles",column="userid",many=@Many(select="getRolesByUsername",fetchType=FetchType.LAZY))
	})
	public SUser getUserByUsername(String username);
	
	
	@Select("select * from s_role where roleid in (select roleid from s_user_role where userid = #{username})")
	@Results({
		@Result(property="ress",column="roleid",many=@Many(select="getRessByRoleid",fetchType=FetchType.LAZY))
	})
	public List<SRole> getRolesByUsername(String username);
	
	@Select("select * from s_res where resid in (select resid from s_role_res where roleid = #{roleid})")
	public List<SRes> getRessByRoleid(String roleid);
	
	
	//资源表的资源尽量针对role使用通配符，除非业务要求，否则不要划分的特别细致
	@Select("select * from s_res")
	@Results({
		@Result(property="roles",column="resid",many=@Many(select="getRolesByResid",fetchType=FetchType.LAZY))
	})
	public List<SRes> getAllRess();
	
	@Select("select * from s_role where roleid in (select roleid from s_role_res where resid = #{resid})")
	public List<SRole> getRolesByResid(String resid);
	
}
