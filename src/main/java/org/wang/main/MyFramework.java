package org.wang.main;

import com.google.gson.Gson;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wang.builder.SqlSourceBuilder;
import org.wang.entity.User;
import org.wang.mapping.*;
import org.wang.session.SqlSession;
import org.wang.session.SqlSessionFactory;
import org.wang.session.SqlSessionFactoryBuilder;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by wy on 2017/4/28.
 * 
 */
public class MyFramework {
    @Test
    public void testReflection(){
        User user=new User();
        user.setUserName("wang");
        user.setUserPass("123456");
        user.setUserAge(12);
        ObjectFactory objectFactory=new DefaultObjectFactory();
        User u=objectFactory.create(User.class);
        ObjectWrapperFactory objectWrapperFactory=new DefaultObjectWrapperFactory();
        MetaObject metaObject=MetaObject.forObject(user,objectFactory,objectWrapperFactory);
        Class  classType=metaObject.getGetterType("userAge");
        System.out.println(classType);
    }


    @Test
    public void plain() throws SQLException {
        String url="jdbc:mysql://localhost:3306/lixueyuan";
        Connection connection=DriverManager.getConnection(url,"root","123456");
     //   Statement stmt=connection.createStatement();
        String sql="select * from user where userId=1";
        String insertSql="INSERT INTO user(userName,userPass,userAge) VALUES(?,?,?)";
        PreparedStatement preparedStatement=null;
        preparedStatement=connection.prepareStatement(insertSql);
        preparedStatement.setString(1,"h");
        preparedStatement.setString(2,"h");
        preparedStatement.setInt(3,12);
        preparedStatement.execute();

    }


    @Test
    public void myownFramework(){
        try {
            // 解析主配置文件
            parseWang();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        try {
            testMapper();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        SqlSessionFactory sqlSessionFactory= new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession=sqlSessionFactory.openSession();

        User user=new User();
        user.setUserId(3);
        user.setUserAge(1);
        user.setUserName("love");
        user.setUserPass("hhhhhh");
//      sqlSession.insert("insertUser",user);
        User u=sqlSession.select("getUserById",user);
        System.out.println(new Gson().toJson(u));

    }


    Configuration configuration=new Configuration();
    // 总的xml配置
    public void parseWang() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
        String resource="wang.xml";
        InputStream inputStream= Resources.getResourceAsStream(resource);
        Document document=documentBuilder.parse(inputStream);
        Element root=document.getDocumentElement();
        System.out.println("root "+root.getNodeName());
        NodeList nodeList=root.getChildNodes();
       for(int i=0;i<nodeList.getLength();i++){
            if(Node.ELEMENT_NODE==nodeList.item(i).getNodeType()){
                Element element= (Element) nodeList.item(i);
                String type=element.getNodeName();
                //System.out.println("type "+element.getNodeName());
                if(type.equals("environment")){
                   environment(element);
                }else if(type.equals("mapper")){
                   mapper(element);
                }
            }
       }
    }
    public void mapper(Element element){

    }
    public void environment(Element element){
             String en=element.getAttribute("id");
             System.out.println("environment "+en);
             Environment environment=new Environment();
             environment.setId(en);
             //  设置环境
             configuration.setEnvironment(environment);
             NodeList nodeList=element.getChildNodes();
             for(int i=0;i<nodeList.getLength();i++){

                 if(Node.ELEMENT_NODE==nodeList.item(i).getNodeType()){
                   Element node= (Element) nodeList.item(i);
                   String type=node.getNodeName();
                   if(type.equals("transactionManager")){
                       transactionManager(node);
                   }else if(type.equals("dataSource")){
                       dataSource(node);
                   }

                 }
               }
    }
    public void transactionManager(Element element){
                TransactionManager transactionManager=new TransactionManager();
                String type=element.getAttribute("type");
                transactionManager.setType(type);
                System.out.println("transactionManager "+type);
                // 设置 transactionManager
                configuration.getEnvironment().setTransactionManager(transactionManager);
    }
    public void dataSource(Element element){
        String pooled=element.getAttribute("type");
        DataSource dataSource=new DataSource();
        Properties properties=new Properties();
        NodeList nodeList=element.getChildNodes();
        for(int i=0;i<nodeList.getLength();i++){
            if(Node.ELEMENT_NODE==nodeList.item(i).getNodeType()){
            Element node= (Element) nodeList.item(i);
            String name=node.getAttribute("name");
            String value=node.getAttribute("value");
            System.out.println("node name "+node.getAttribute("name"));
            System.out.println("node value "+node.getAttribute("value"));
            properties.setProperty(name,value);
            }
        }
        dataSource.setProperties(properties);
        configuration.getEnvironment().setDataSource(dataSource);
    }


    // 解析mapper文件
    public void testMapper() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        String resource = "user.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        Document document = documentBuilder.parse(inputStream);
        //System.out.println("size "+nodeList.getLength());

        Element root = document.getDocumentElement();
        System.out.println("tag name " + root.getTagName());
        System.out.println("node name " + root.getNodeName());
//           System.out.println("text content "+root.getTextContent());
        System.out.println(" " + root.getAttribute("namespace"));

        // 注释   在文档空白处有 text节点

        NodeList nodeList = root.getChildNodes();
        System.out.println("lenght " + nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println(" " + nodeList.item(i).getNodeName());
        }
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element action = (Element) nodeList.item(i);
                System.out.println("action name " + action.getNodeName());
                parseStatement(action);
            }

        }
        //  执行sql语句


//         执行相应的语句操作

    }
    public void  parseStatement(Element element){
        Mapper mapper=new Mapper();
        String id=null;
        String parameterType=null;
        String resultType=null;
        String content=null;
        id=element.getAttribute("id");
        parameterType=element.getAttribute("parameterType");
        resultType=element.getAttribute("resultType");
        System.out.println("id "+id);
        System.out.println("parameterType "+parameterType);
        System.out.println("resultType  "+resultType);
        content=element.getTextContent();
        System.out.println("content "+content);

        mapper.setId(id);
        // 参数class类型
        Class cls=null;
        try {
            cls=Class.forName(parameterType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        mapper.setParaType(cls);
        // 参数类型

        try {
            mapper.setQinPara(cls.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 返回值类型

        Class returnCls=null;
        try {
            returnCls=Class.forName(resultType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            mapper.setReturnPara(returnCls.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mapper.setReturnType(returnCls);

        // 预先放
        configuration.put(id,mapper);




        handleContent(content,mapper);

    }
    public void   handleContent(String  orginalSql,Mapper mapper){
        SqlSourceBuilder sqlSourceBuilder=new SqlSourceBuilder(configuration);
        String sql=sqlSourceBuilder.parse(orginalSql,mapper.getId());
        System.out.println("sql "+sql);
        // 1 拿到参数名
        mapper.setSql(sql);



    }








}
