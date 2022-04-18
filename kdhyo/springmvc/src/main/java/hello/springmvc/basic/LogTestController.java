package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LogTestController {

	@RequestMapping("/log-test")
	public String logTest() {
		String name = "Spring";

		System.out.println("name = " + name);

		// "info log = " + name 으로 하면 String 더하는 연산이 일어난다.
		log.trace("trace log = {}", name);
		log.debug("debug log = {}", name);
		log.info("info log = {}", name);
		log.warn("warn log = {}", name);
		log.error("error log = {}", name);

		return "ok";
	}

}
