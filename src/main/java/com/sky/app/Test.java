package com.sky.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test {
	
	public static void main(String[] args) {
		
		new Test().deploy();
	}
	
	public String deploy() throws RuntimeException { 
		StringBuilder sb = new StringBuilder();
		try {
			
			Runtime rt = Runtime.getRuntime();
		    Process p = null;
			String line = null;
			BufferedReader br = null;
		        
			p = rt.exec("cmd /c netstat -aon|findstr \"8080\"");  //8082
		    br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    line=br.readLine();
		    String pid=null;
		    if(line!=null && !line.trim().equals("")) {
		    pid = line.trim().substring(line.trim().length()-6);
		    sb.append("当前软件工厂应用正在运行：");	
		    System.out.println("当前软件工厂应用正在运行：");
		    sb.append(line);		
		    sb.append("关闭软件工厂应用...");
		    System.out.println("关闭软件工厂应用...");
		    rt.exec("cmd /c taskkill /pid "+pid+" /F");
		    sb.append("        成功关闭软件工厂应用，进程号："+pid);
		    System.out.println("        成功关闭软件工厂应用，进程号："+pid);
		  }
		  
		  sb.append("重新编译、部署软件工厂应用...");		  
		  System.out.println("重新编译、部署软件工厂应用...");
	      p = rt.exec("cmd /c D:/workspaces/skys/gradlew.bat bootRun");	  //D:/workspaces/skys
		  br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		  while((line=br.readLine())!=null && line!=""){
	          sb.append("   ").append(new String(line.getBytes(), "GBK"));
	          System.out.println(new String(line.getBytes(), "GBK"));
			 /* if(line.indexOf("JVM running for")!=-1) {
				  sb.append("重新部署应用成功！");
				  System.out.println("重新部署应用成功！");
				  break;
			  }else if(line.indexOf("BUILD FAILED")!=-1){
				  sb.append("重新部署应用失败，请检查输出信息！");
				  System.out.println("重新部署应用失败，请检查输出信息！");
				  break;
			  }*/
			//Thread.sleep(60*60*1000);
		   }
	       br.close();
	       
	       return sb.toString();
		}catch(Exception e) {
		   throw new RuntimeException(e);
		}
	}
}
