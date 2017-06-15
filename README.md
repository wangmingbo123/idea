# 仿mybatis写的框架
功能代码演示
        
        SqlSessionFactory sqlSessionFactory= new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        User user=new User();
        user.setUserId(3);
        user.setUserAge(1);
        user.setUserName("love");
        user.setUserPass("hhhhhh");
        User u=sqlSession.select("getUserById",user);
        System.out.println(new Gson().toJson(u));
配置文件
    <br/><mapper namespace="org.wang.mapper.UserMapper">
    <br/><select id="getUserById"  parameterType="org.wang.entity.User" resultType="org.wang.entity.User">
        <br/>select * from user where userId = #{userId}
    <br/></select>
    <br/><insert id="insertUser" parameterType="org.wang.entity.User">
        <br/>INSERT INTO user(userName,userPass,userAge) VALUES(#{userName},#{userPass},#{userAge})
    <br/></insert>

   <br/><select id="getUsers" parameterType="org.wang.entity.User" resultType="org.wang.entity.User">
           <br/>SELECT * FROM USER  WHERE  userId>#{userId}
   <br/></select>
   <br/></mapper>
        
