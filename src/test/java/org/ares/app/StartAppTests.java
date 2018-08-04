package org.ares.app;

import javax.annotation.Resource;

import org.ares.app.c5.security.rbac.dao.RbacMapper;
import org.ares.app.sys.entity.CasUser;
import org.ares.app.sys.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.PathMatcher;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StartAppTests {

	
	public void t_mybatis() {
		CasUser u=um.getUserByUserName("ly");
		System.out.println(u.getEmail());
		
		rm.getResOfRole().stream().forEach(e->System.out.println(e));
		System.out.println(rm.getRolesByUsername("ly").size());
		System.out.println(rm.getUserByUsername("admin").getRoles()==null);
		rm.getUserByUsername("ly").getRoles().stream().forEach(e->System.out.println(e));
		
		rm.getAllRess().stream().forEach(e->System.out.println(e));
		
	}
	
	@Test
	public void t_stream() {
		String url="/index";
		rm.getAllRess().stream().filter(e->pathMatcher.match(e.getResurl(), url)).flatMap(e->e.getRoles().stream()).forEach(e->System.out.println(e));
		System.out.println(rm.getAllRess().stream().filter(e->pathMatcher.match(e.getResurl(), url)).count());
	}
	
	@Resource UserMapper um;
	@Resource RbacMapper rm;
	@Resource PathMatcher pathMatcher;
}
