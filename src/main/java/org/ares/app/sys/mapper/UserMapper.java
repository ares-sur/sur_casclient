package org.ares.app.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ares.app.sys.entity.CasUser;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

	@Select("SELECT * FROM casuser WHERE userid = #{username}") //动态传入表名，可以使用 ...FROM ${tableName}... ，但需要解决sql注入风险
	CasUser getUserByUserName(@Param("username") String username);
	
}

/**
 	@Result 修饰返回的结果集，关联实体类属性和数据库字段一一对应，如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰。
	@Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值
	@Update 负责修改，也可以直接传入对象
	@delete 负责删除
	@Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
        @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
        @Result(property = "nickName", column = "nick_name")
    })
 */
