package com.agaram.eln.primary.repository.sample;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.sample.SampleCategory;

public interface SampleCategoryRepository extends JpaRepository<SampleCategory, Integer> {

	List<SampleCategory> findBySsamplecatnameIgnoreCaseAndNsitecode(String ssamplecatname, Integer nsitecode);

	public SampleCategory findByNsamplecatcode(Integer nsamplecatcode);

	public SampleCategory findByNsitecodeAndSsamplecatnameIgnoreCase(Integer nsitecode, String ssamplecatname);
	
	List<SampleCategory> findByNsitecodeOrderByNsamplecatcodeDesc(Integer nsitecode);

	List<SampleCategory> findByNsitecodeNotAndNdefaultstatusOrderByNsamplecatcodeDesc(int i, int j);

	List<SampleCategory> findByNsitecodeAndNdefaultstatusOrderByNsamplecatcodeDesc(Integer nsitecode, int i);


}
