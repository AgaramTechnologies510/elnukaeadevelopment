package com.agaram.eln.primary.repository.fileManipulation;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.agaram.eln.primary.model.fileManipulation.OrderAttachment;
public interface OrderAttachmentRepository extends MongoRepository<OrderAttachment, String> {
	public OrderAttachment findTop1ById(String Id);
	public void deleteById(String Id);
}
