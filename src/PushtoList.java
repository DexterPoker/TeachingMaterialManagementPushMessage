
import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;

public class PushtoList {
	static String appId = "3nlkSm6cYh7seBhLIEqmg1";
	static String appkey = "bSD0HoVg3O7hl8rhqZ5E09";
	static String master = "9pv9K2kFaa6FgCzRyo2iR7";
	static String CID = "";
	static String Alias ="";
	
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";


	public static void push(List<String> cid,String title,String text) throws Exception {
		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");

		final IGtPush push = new IGtPush(host, appkey, master);
		push.connect();
		NotificationTemplate template = NotificationTemplateDemo(title,text);

		ListMessage message = new ListMessage();

		message.setData(template);

		message.setOffline(false);
		message.setOfflineExpireTime(1 * 1000 * 3600);
		message.setPushNetWorkType(0);

		List<Target> targets = new ArrayList<Target>();
		for(int i = 0 ;i < cid.size(); i ++){
			Target target = new Target();
			target.setAppId(appId);
			target.setClientId(cid.get(i));
			targets.add(target);
		}
		String taskId = push.getContentId(message, "toList_Alias_Push");
		IPushResult ret = push.pushMessageToList(taskId, targets);
		System.out.println(ret.getResponse().toString());
	}

	public static NotificationTemplate NotificationTemplateDemo(String title,String text)
			throws Exception {
		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTitle(title);
		template.setText(text);
		template.setLogo("icon.png");
		template.setLogoUrl("");
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		template.setTransmissionType(1);
		template.setTransmissionContent("text");
		template.setPushInfo("actionLocKey", 2, "message", "sound", "payload",
				"locKey", "locArgs", "launchImage");
		return template;
	}

	public static void main(String[] args){
		List<String> cid = new ArrayList<String>();
		cid.add("daf18b18c7ba6ff41d50e33b85d22b28");
		String title = "Android 有新的教师推荐资料";
		String text = "阿萨德飞科";
		try {
			PushtoList.push(cid, title, text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}