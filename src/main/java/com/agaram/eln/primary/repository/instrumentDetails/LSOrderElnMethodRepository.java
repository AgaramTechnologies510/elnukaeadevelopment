package com.agaram.eln.primary.repository.instrumentDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.instrumentDetails.LSOrderElnMethod;
import com.agaram.eln.primary.model.methodsetup.Method;

public interface LSOrderElnMethodRepository extends JpaRepository<LSOrderElnMethod, Integer> {
	List<LSOrderElnMethod> findByMethodOrderByOrderelnmethodcodeDesc(Method methodobj);
}
