# 仿造mybatis写的框架
功能代码实例
  
配置文件
<mapper namespace="org.wang.mapper.UserMapper">
    <select id="getUserById"  parameterType="org.wang.entity.User" resultType="org.wang.entity.User">
        select * from user where userId = #{userId}
    </select>
    <insert id="insertUser" parameterType="org.wang.entity.User">
        INSERT INTO user(userName,userPass,userAge) VALUES(#{userName},#{userPass},#{userAge})
    </insert>

   <select id="getUsers" parameterType="org.wang.entity.User" resultType="org.wang.entity.User">
           SELECT * FROM USER  WHERE  userId>#{userId}
   </select>
</mapper>

   