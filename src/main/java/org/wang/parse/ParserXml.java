package org.wang.parse;

import org.apache.ibatis.io.Resources;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wang.builder.SqlSourceBuilder;
import org.wang.mapping.Configuration;
import org.wang.mapping.Mapper;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wy on 2017/4/27.
 */
public class ParserXml {
       Configuration configuration=new Configuration();
       @Test
       public void testMapper() throws ParserConfigurationException, IOException, SAXException {
           DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
           DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
           String resource="user.xml";
           InputStream inputStream= Resources.getResourceAsStream(resource);
           Document document=documentBuilder.parse(inputStream);
           //System.out.println("size "+nodeList.getLength());

           Element root=document.getDocumentElement();
           System.out.println("tag name "+root.getTagName());
           System.out.println("node name "+root.getNodeName());
//           System.out.println("text content "+root.getTextContent());
           System.out.println(" "+root.getAttribute("namespace"));

           // 注释   在文档空白处有 text节点

           NodeList nodeList=root.getChildNodes();
           System.out.println("lenght "+nodeList.getLength());
           for(int i=0;i<nodeList.getLength();i++){
               System.out.println(" "+nodeList.item(i).getNodeName());
           }
          for(int i=0;i<nodeList.getLength();i++){
               if(nodeList.item(i).getNodeType()==Node.ELEMENT_NODE){
                  Element action= (Element) nodeList.item(i);
                  System.out.println("action name "+action.getNodeName());
                  parseStatement(action);
               }

          }
          //  执行sql语句



          // 执行相应的语句操作







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
                    mapper.setParaType(parameterType.getClass());
                    // 参数类型
                    mapper.setQinPara(parameterType);
                    mapper.setReturnPara(resultType.getClass());
                    mapper.setReturnPara(resultType);

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
