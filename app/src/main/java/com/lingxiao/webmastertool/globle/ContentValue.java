package com.lingxiao.webmastertool.globle;

/**
 * Created by lingxiao on 17-11-18.
 */

public class ContentValue {
    public static String DOMAIN = "";
    public static String BASE_URL = "http://m.tool.chinaz.com";
    public static String PC_BASE_URL = "http://tool.chinaz.com";
    //seo查询
    public static String SEOINFO = "http://seo.chinaz.com/";
    //百度排名
    public static String RANKING = BASE_URL +"/baidusort?host=";
    //whois
    public static String WHOISINFO = "http://whois.chinaz.com/";
    //网站备案
    public static String ICPINFO = BASE_URL +"/beian?s=";
    //IP查询
    public static String IPINFO = BASE_URL +"/ipsel?ip=";
    //PR查询
    public static String PRINFO = BASE_URL +"/ranks?PRAddress=";
    //友情链接
    public static String BLOGINFO = BASE_URL +"/links?wd=";
    //反链
    public static String LINKINFO = "http://m.outlink.chinaz.com/?host=";

    //死链检测
    public static String DEADLINKINFO = BASE_URL +"/deadlinks/?DAddress=";
    //关键词密度
    public static String DENSITYKEYWORLD = BASE_URL +"/density/#/density/?kw=";
    public static String DENSITYKURL = "&url=";
    //META信息
    public static String METACHECKINFO = BASE_URL +"/metacheck/#/metacheck/?url=";
    //PR输出值
    public static String EXPORTPRINFO = BASE_URL + "/exportpr#/exportpr/?q=";

    //域名删除
    public static String DOMAINDEL = BASE_URL + "/domaindel/#/DomainDel/?wd=";
    //同ip网站
    public static String SAME = BASE_URL + "/same/?s=";
    //子域名查询
    public static String SUBDOMAIN = BASE_URL + "/subdomain/?domain=";

    //超级ping
    public static String PING = "http://ping.chinaz.com/";
    //路由器追踪
    public static String TRACERT = PC_BASE_URL + "/Tracert";
    //HTTP状态
    public static String PAGESTATUS = PC_BASE_URL + "/pagestatus/?url=";
    //端口扫描
    public static String PORT = BASE_URL + "/port/?ports=" +
            "+80%2C8080%2C3128%2C8081%2C9080%2C1080%2C21" +
            "%2C23%2C443%2C69%2C22%2C25%2C110%2C7001%2" +
            "C9090%2C3389%2C1521%2C1158%2C2100%2C1433&host=";
    //网站GZIP压缩
    public static String GZIPS = PC_BASE_URL + "/Gzips/?q=";
    //网站历史记录
    public static String HISTORY = PC_BASE_URL + "/history/?ht=0&h=";
    //竞争网站分析
    public static String WEBSITEPK = PC_BASE_URL + "/websitepk.aspx?host=";
    //网站安全检测
    public static String WEBSAFE = PC_BASE_URL + "/webscan?host=";
}
