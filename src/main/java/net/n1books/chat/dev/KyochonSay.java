package net.n1books.chat.dev;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.Context;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

@RestController
public class KyochonSay {
	private static Logger logger = LoggerFactory.getLogger(KyochonSay.class);

	@RequestMapping(value = "watsonsay")
	public MessageResponse watsonsay(String isay, HttpSession session) {
		logger.info("user input : " + isay);

		Conversation service = new Conversation(Conversation.VERSION_DATE_2017_05_26);
		service.setUsernameAndPassword("a8b3a91a-0b88-4c75-915e-3b48c043a986", "TysqfG6j72xJ");

		MessageResponse response = null;
		Context context = (Context) session.getAttribute("context");
		MessageOptions options = null;
		String msg = isay;
		StringBuffer watsonSay = null;

		options = new MessageOptions.Builder().workspaceId("8506d1ca-d51d-4ace-afea-c0c162a2acb9")
				.input(new InputData.Builder(msg).build()).context(context).build();
		response = service.message(options).execute();

		watsonSay = new StringBuffer();
/*		for (String text : response.getOutput().getText()) {
			watsonSay.append(text);
			watsonSay.append(" ");
		}*/

		logger.info("Watson : " + watsonSay);
		logger.info("response : " + response);

		context = response.getContext();
		session.setAttribute("context", context);
		return response;
	}
}
