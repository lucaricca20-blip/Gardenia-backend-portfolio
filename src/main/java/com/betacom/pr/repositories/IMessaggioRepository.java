package com.betacom.pr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.pr.models.MessageID;
import com.betacom.pr.models.Messaggi;

public interface IMessaggioRepository extends JpaRepository<Messaggi, MessageID> {

}
