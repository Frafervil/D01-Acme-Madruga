package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Request;

import repositories.RequestRepository;

@Service
@Transactional
public class RequestService {

	// Managed Repository

	@Autowired
	private RequestRepository requestRepository;

	// Supporting services

	// Simple CRUD methods

	public Collection<Request> findAll() {
		Collection<Request> result;

		result = this.requestRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public void delete(final Request request) {

		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);

		this.requestRepository.delete(request);
	}
}
