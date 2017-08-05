/*package com.forget.test;

import javax.xml.ws.spi.http.HttpContext;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class Down implements Runnable{
    private String url="";
    private String path="";
    private final HttpClient httpClient;
    private final HttpContext httpContext;
    private final HttpGet httpGet;
   
    public Down(HttpClient httpClient,HttpGet httpGet,String url,String path){
        this.httpClient=httpClient;
        this.httpGet=httpGet;
//        this.httpContext=new BasicHttpContext();
        this.path=path;
        this.url=url;
       
    }
   
    public void run() {
        System.out.println("[INFO] Download From : "+this.url);
        File file=new File(this.path);
        if(file.exists())file.delete();
        try {
            //使用file来写入本地数据
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(this.path);
           
            //执行请求，获得响应
            HttpResponse httpResponse = this.httpClient.execute(this.httpGet,this.httpContext);
           
            System.out.println("[STATUS] Download : "+httpResponse.getStatusLine()+" [FROM] "+this.path);
           
            HttpEntity httpEntity=httpResponse.getEntity();
            InputStream inStream=httpEntity.getContent();
            while(true){//这个循环读取网络数据，写入本地文件
                byte[] bytes=new byte[1024*1000];
                int k=inStream.read(bytes);
                if(k>=0){
                    outStream.write(bytes,0,k);
                    outStream.flush();
                }
                else break;
            }
           
*/