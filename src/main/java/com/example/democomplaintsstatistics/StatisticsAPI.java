package com.example.democomplaintsstatistics;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.democomplaints.ComplaintFiledEvent;

@ProcessingGroup("statistics")
@RestController
public class StatisticsAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(StatisticsAPI.class);
	
	private final ConcurrentMap<String, AtomicLong> statistics = new ConcurrentHashMap<>();
	
	@EventHandler
	public void handle(ComplaintFiledEvent event){
		logger.info("handle( {} )", event);
		
		statistics.computeIfAbsent(event.getCompany(), k ->  new AtomicLong()).incrementAndGet();
	}
	
	@GetMapping 
	public ConcurrentMap<String, AtomicLong> getStatistics(){
		logger.info("getStatistics()");
		return statistics;
	}

}
