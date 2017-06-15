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
       在src\main\resources\user.xml文件里
