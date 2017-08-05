package com.forget.sm;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.forget.utils.DateTest;

public class GetSimHash {

	public static JSONObject getSimHash(String article){
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();
		if(compare_date){
			article = article.replaceAll("&nbsp;", "");
			article = article.replaceAll("&quot;", "");
			article = article.replaceAll("\\p{P}", "");
			article = article.replaceAll("\n", "");
			
			if(article == null || article.trim().equals("")){
				json.put("state", "error");
				json.put("msg", "参数异常");
				json.put("fingerprint", "");
				return json;
			}
			
			try {
				SimHash simHash = new SimHash(article, 64);
				String strSimHash = simHash.strSimHash;
				json.put("state", "success");
				json.put("msg", "");
				json.put("fingerprint", strSimHash);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
			json.put("fingerprint", "");
		}
		
		return json;
	}
	public static void main(String[] args) {
		System.setProperty("appPath", "D:/give0822");
		String str1 = "《外观没得说，颜值太高，动力没得说，超车没压力，操控性强，》;【油耗】刚刚做完首保后，不知道是心里作用还是什么，感觉车子提速没有原来那么轻松，感觉机油好像有点黏黏的，现在跑了6500多公里了，那种感觉没有了，也许当初是心里作用吧！油耗还那样，基本上就是高速平均6.3，国道平均7.8，市区平均9.5左右，这么大的车，油耗满意，涡轮增压确实真省油啊！动力真的没的说，有一次超车油门踩的有的狠，提速太快了，车上五个人算着司机，都头晕了，真的太满意了，对了，我一直用的是普通模式，用过一次经济模式，提速有点肉，需要踩下去才有，普通模式只要踩就有，运动模式没试过，感觉，普通模式足够了！"+
				"【保养】5000公里做的首保，免费换了机油，机滤，空滤用气泵吹了吹，不算太脏，放出来的机油可真的是太黑了，这次是免费的，下次就要花钱了，因为是涡轮增压的，所以，用的是好机油，说实话，具体是不是好的我也不知道，应该是好的吧，至少要比不带涡轮增压的要好，听4s工作人员说，下次换机油一桶机油就得400来的，我个人认为比不带涡轮增压的贵100多，别的应该是一样的，"+
				"【故障】跑了6500多公里了，什么毛病也没出过，新车，不可能出问题的，还有基本上2万公里不出问题就肯定没什么问题了，非要说问题的话，就是我的中控大屏，出现过一次不显示时间和日期了，重新启动一次就好了！"+
				"【吐槽】全新途胜哪都好。就是没有导航真闹心，虽然说有百度car，但感觉特别不方便，一是手机耗电问题，随说插在了usb上，也一直在掉点，再说了，手机一会充，一会不充的对手机也不好，打电话还行，车载，方便，但现在几乎都是微信，用手机导航不方便微信，别说开车不能微信，就像说开车不能看视频一样，原来我的车是后装的一体机，孩子上车就想看动画片，或看小品，这下好了，只要不在P档上，就别想看，可郁闷了，更想吐槽的是，这个价位的车车玻璃不带防夹手功能，夹过我闺女，夹过我自己，夹过我外甥女，说实话，夹的我疼了好几天，也不带遥控一键降窗，一键升窗，那东西没多少钱，建议厂家还是按个吧！为了孩子们安全着想，夹一下，真疼啊！";

		JSONObject simHash = getSimHash(str1);
		System.out.println(simHash.toJSONString());
	}
}
