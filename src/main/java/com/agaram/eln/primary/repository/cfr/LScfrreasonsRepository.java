package com.agaram.eln.primary.repository.cfr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.cfr.LScfrreasons;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;

public interface LScfrreasonsRepository  extends JpaRepository<LScfrreasons, Integer> {
	public LScfrreasons findByComments(String comments);

	public LScfrreasons findByCommentsIgnoreCase(String comments);

	public List<LScfrreasons> findByStatus(Integer i);

	public LScfrreasons findByCommentsAndStatus(String comments, Integer i);

	public List<LScfrreasons> findByLssitemasterOrderByReasoncodeDesc(LSSiteMaster lsSiteMaster);

	public LScfrreasons findByLssitemasterAndCommentsAndStatus(LSSiteMaster lssitemaster, String comments, int i);

	public List<LScfrreasons> findByLssitemasterAndStatus(LSSiteMaster lsSiteMaster, int i);
}
