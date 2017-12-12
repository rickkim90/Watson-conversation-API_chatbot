package net.n1books.chat.dev;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class KyochonController {
	
	@RequestMapping("/")
	public String chatbot() {
		return "chatbot";
	}
}
