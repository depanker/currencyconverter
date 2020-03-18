package com.depanker.currencyconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class CurrencyconverterApplication {

	public static void main(String[] args) {
		BlockHound.builder()
				.allowBlockingCallsInside("sun.security.ssl.SSLHandshake", "consume")
				.allowBlockingCallsInside("io.netty.util.concurrent.GlobalEventExecutor", "addTask")
				.install();
		Hooks.onOperatorDebug();
		SpringApplication.run(CurrencyconverterApplication.class, args);
	}

}
