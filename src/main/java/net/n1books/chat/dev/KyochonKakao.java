package net.n1books.chat.dev;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.Context;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

@RestController
public class KyochonKakao {
	private static Logger logger = LoggerFactory.getLogger(KyochonKakao.class);

	@RequestMapping(value = "/keyboard", method = RequestMethod.GET)
	public String kakaoChat() {
		logger.info("keyboard");

		JSONObject jobjBtn = new JSONObject();
		jobjBtn.put("type", "text");

		return jobjBtn.toJSONString();
	}

	@RequestMapping(value = "/message", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public String kakaoChatMsg(@RequestBody JSONObject resObj, HttpSession session) {
		logger.info("message");

		Conversation service = new Conversation(Conversation.VERSION_DATE_2017_05_26);
		service.setUsernameAndPassword("a8b3a91a-0b88-4c75-915e-3b48c043a986", "TysqfG6j72xJ");

		MessageResponse response = null;
		Context context = (Context) session.getAttribute("context");
		MessageOptions options = null;
		StringBuffer watsonSay = null;

		String content;
		content = (String) resObj.get("content");

		options = new MessageOptions.Builder().workspaceId("8506d1ca-d51d-4ace-afea-c0c162a2acb9").input(new InputData.Builder(content).build())
				.context(context).build();
		response = service.message(options).execute();

		watsonSay = new StringBuffer();
		for (String text : response.getOutput().getText()) {
			watsonSay.append(text);
			watsonSay.append(" ");
		}
		
		logger.info("Watson : " + watsonSay);

		context = response.getContext();
		session.setAttribute("context", context);
	
        JSONObject jobjRes = new JSONObject();
        JSONObject jobjText = new JSONObject();
		jobjText.put("text", watsonSay.toString());
		
        jobjRes.put("message", jobjText);
        System.out.println(jobjRes.toJSONString());
        
        return  jobjRes.toJSONString();
		
	}
}
