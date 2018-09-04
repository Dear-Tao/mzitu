package mzitu;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class meizitu {
	private static meizitu meizitu = new meizitu();
	
	public static String path = "F:/meizitu/"; //存放文件地址
	public static long restTime = 3000; //休眠时间 （豪秒）不能太快
	
	
	public static void main(String[] args) {
		//获取大分类下全部 如：http://www.mzitu.com/xinggan/
//		getClassification("http://www.mzitu.com/xinggan/");
		
		//获取指定页数 如：//http://www.mzitu.com/mm/page/2/
//		getEachpage("http://www.mzitu.com/mm/page/2/");
		
		//获取每组图片 如：http://www.mzitu.com/148600
		getEachGroup("http://www.mzitu.com/147217");
	}
	/**
	* @Title: getClassification 
	* @Description: 大分类  
	* @param url    参数 
	* void  返回类型 
	* @throws
	 */
	public static void getClassification(String url){
		
		Document doc = meizitu.getDocument(url);
		// 获取总页数html
		Elements PageCountHtml = doc.select("[class=page-numbers]");
		//当前大分类总页数
		int PageCount = Integer.parseInt(PageCountHtml.get(PageCountHtml.size()-1).text());
		
		for (int i = 1; i <= PageCount; i++) {
			getEachpage(url+"/page/"+i+"/");
		}
	}
	/**
	 * 
	* @Title: getEachpage 
	* @Description: 获取每页组总数，并获取每个图片地址
	* @param url    参数 
	* void  返回类型 
	* @throws
	 */
	public static void getEachpage(String url){
		
		Document doc = meizitu.getDocument(url);
		Elements postlist = doc.select("[class=postlist]");
		Elements liCount = postlist.select("li");
		for (int i = 0; i < liCount.size(); i++) {
			Elements a = liCount.get(i).select("span").select("a");
			String aurl = a.attr("href");
			getEachGroup(aurl);
		}
	}
	
	/**
	* @Title: getImgTotalNumber 
	* @Description: 获取每组图片总数，并获取每个图片地址
	* @param url    参数 
	* void  返回类型 
	* @throws
	 */
	public static void getEachGroup(String url){
		
		Document doc = meizitu.getDocument(url);
		Elements pageNumberHtml = doc.select("[class=pagenavi]");
		Elements a = pageNumberHtml.select("a");
		int pageNumber = Integer.parseInt(a.get(a.size()-2).select("span").text());
		for (int i = 1; i <= pageNumber; i++) {
			String groupingurl = url+"/"+i;
			Document imgdoc = meizitu.getDocument(groupingurl);
			Elements select = imgdoc.select("[class=main-image]");
			Elements img = select.select("img");
			String imgurl = img.attr("src"); //下载图片地址
			String Suffix = imgurl.substring(imgurl.lastIndexOf("."));
			String title = img.attr("alt");//标题
			
			String name = title+"_"+i+Suffix; //文件名
			String savePath = path+title; //保存文件地址
			
		try {
				DownloadImage.download(imgurl, name, savePath,restTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param url
	 *            访问路径
	 * @return
	 */
	public Document getDocument(String url) {
		try {
			// 5000是设置连接超时时间，单位ms
			return Jsoup.connect(url).timeout(5000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
