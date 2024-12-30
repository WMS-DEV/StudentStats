package pl.wmsdev.universities.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wmsdev.universities.dto.UniversityDisplayable;
import pl.wmsdev.universities.service.UniversitiesService;

import java.util.List;

@RestController
@RequestMapping("/universities")
@RequiredArgsConstructor
public class UniversitiesController {

	private final UniversitiesService universitiesService;

	@GetMapping
	public List<UniversityDisplayable> getUniversities() {
		return universitiesService.getUniversitiesDisplayable();
	}
}
