package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PlaceRepository;
import domain.Place;

@Service
@Transactional
public class PlaceService {

	// Managed Repository

	@Autowired
	PlaceRepository placeRepository;

	public void delete(final Place place) {
		this.placeRepository.delete(place);
	}

	public Place create() {
		final Place result = null;

		return result;
	}
}
