package com.agaram.eln.primary.repository.cloudFileManip;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.agaram.eln.primary.model.cloudFileManip.CloudOrderCreation;

public interface CloudOrderCreationRepository  extends JpaRepository<CloudOrderCreation, Long> {
	public CloudOrderCreation findTop1ById(Long Id);
//	@Query(value = "SELECT * FROM lsOrderCreationfiles WHERE text(contentvalues) LIKE :value", nativeQuery = true)
//	public List<CloudOrderCreation> findByContentvaluesequal(@Param("value") String value);
	@Query(value = "SELECT * FROM lsOrderCreationfiles WHERE contentvalues::text LIKE :value AND id IN (:lstsamplefilecode)", nativeQuery = true)
	List<CloudOrderCreation> findByContentvaluesequal(
	    @Param("value") String value,
	    @Param("lstsamplefilecode") List<Integer> lstsamplefilecode
	);



	@Query(value = "SELECT * FROM lsOrderCreationfiles WHERE text(contentparameter) LIKE :value And id IN (:lstsamplefilecode)", nativeQuery = true)
	public List<CloudOrderCreation> findByContentparameterequal(@Param("value") String value,   @Param("lstsamplefilecode") List<Integer> lstsamplefilecode);
	@Transactional
	@Modifying
	@Query(value = "select fileuid from "
			+ "lsordercreationfiles where id in (?1) ORDER BY id DESC", nativeQuery=true)
	public List<CloudOrderCreationRepository> getFileuid(List<Integer> sampleFileCodeList1);

}
