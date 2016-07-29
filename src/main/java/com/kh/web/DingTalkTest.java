package com.kh.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.kh.util.HttpClientUtil;

public class DingTalkTest {
	
	private String url = "https://oapi.dingtalk.com/";  
    private String charset = "UTF-8";  
    private HttpClientUtil httpClientUtil = null;  
      
    public DingTalkTest(){  
        httpClientUtil = new HttpClientUtil();  
    }
    
    public void getDingTalkToken(){
    	HttpURLConnection conn = null;
    	String ul = url+ "gettoken?corpid=id&corpsecret=secrect";
    	try {
			conn = (HttpURLConnection) (new URL(ul)).openConnection();
			conn.setDoInput(true);  
			conn.setDoOutput(true);  
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.getOutputStream().write(ul.getBytes("UTF-8"));  
			conn.getOutputStream().flush();  
			conn.getOutputStream().close();  
            BufferedReader in = new BufferedReader(new InputStreamReader(  
            		conn.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                System.out.println(line);  
            }  
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }
     //获取token
    public void token(){  
        String httpOrgCreateTest = url + "gettoken?corpid=dingaaac49d6ee55e7d6&corpsecret=aea0VqqTUz4kdzGSwu75V76d9xleReTBwgRzXGwG4SZW7Ip_Wg2bgvtcVAHQdGQN";  
        String httpOrgCreateTestRtn = httpClientUtil.doGet(httpOrgCreateTest,null,charset);  
        System.out.println("result:"+httpOrgCreateTestRtn);
    }
    //发公司消息
    public void send(){
    	 String httpOrgCreateTest = url + "message/send?access_token=4b82c6f3e0d53199b7280107d66469f7";  
         String param = "{'touser':'kh06972','agentid':'1857028','msgtype':'text','text':{'content':'您有一笔OA待办事宜.请及时处理!(测试消息)'}}";
         String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest,param,charset);
         System.out.println("result:"+httpOrgCreateTestRtn);
    }
    //获取部门列表
    public void dept(){
   	 String httpOrgCreateTest = url + "department/list?access_token=4b82c6f3e0d53199b7280107d66469f7";  
        String httpOrgCreateTestRtn = httpClientUtil.doGet(httpOrgCreateTest,null,charset);
        System.out.println("result:"+httpOrgCreateTestRtn);
   }
    //获取部门详情
    public void deptDetail(){
      	 String httpOrgCreateTest = url + "department/get?access_token=4b82c6f3e0d53199b7280107d66469f7&id=1";  
           String httpOrgCreateTestRtn = httpClientUtil.doGet(httpOrgCreateTest,null,charset);
           System.out.println("result:"+httpOrgCreateTestRtn);
      }
    
    //获取部门成员详情
    public void deptUsers(){
     	 String httpOrgCreateTest = url + "user/list?access_token=4b82c6f3e0d53199b7280107d66469f7&department_id=1945762&offset=0&size=100";  
          String httpOrgCreateTestRtn = httpClientUtil.doGet(httpOrgCreateTest,null,charset);
          System.out.println("result:"+httpOrgCreateTestRtn);
     }
   
    
    //获取用户详情
    public void userDetail(){
      	 String httpOrgCreateTest = url + "user/get?access_token=4b82c6f3e0d53199b7280107d66469f7&userid=kh06980";  
           String httpOrgCreateTestRtn = httpClientUtil.doGet(httpOrgCreateTest,null,charset);
           System.out.println("result:"+httpOrgCreateTestRtn);
      }
    
      
    public static void main(String[] args){
    	DingTalkTest main = new DingTalkTest();  
        main.token();  
    }  

}
