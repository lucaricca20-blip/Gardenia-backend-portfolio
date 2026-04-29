package com.betacom.pr.services.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.betacom.pr.models.MessageID;
import com.betacom.pr.models.Messaggi;
import com.betacom.pr.repositories.IMessaggioRepository;
import com.betacom.pr.services.interfaces.IMessaggioServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessaggioImpl implements IMessaggioServices {
	
	private final IMessaggioRepository msgR;

	@Value("${lang}")
	private String lang;
	
	
	@Override
	public String get(String code) {
		log.debug("get  {} ",code);
		String r = null;
		Optional<Messaggi> m = msgR.findById(new MessageID(lang, code));
		if (m.isEmpty())
			r = code;
		else
			r = m.get().getMessaggio();		
		
		return r;
	}

}
