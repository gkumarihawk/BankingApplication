package com.synergisticit.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.repository.BranchRepository;

@Service
public class BranchServiceImpl implements BranchService {
	
	@Autowired BranchRepository branchRepository;


	public Branch saveBranch(Branch branch) {
		
		return branchRepository.save(branch);
	}

	@Override
	public Branch findById(long branchId) {
		Optional<Branch> optBranch = branchRepository.findById(branchId);
		if(optBranch.isPresent()) {
			return optBranch.get();
		}
		return null;
	}

	@Override
	public List<Branch> findAll() {
	
		return branchRepository.findAll();
	}

	@Override
	public void deleteById(long branchId) {
		
		branchRepository.deleteById(branchId);
		
	}

	/*@Override
	public boolean existsById(long branchId) {
		
		return branchRepository.existsById(branchId);
	}*/
	
	@Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public boolean existsById(long branchId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT count(*) FROM Branch WHERE id = :branchId";
        Long count = (Long) session.createQuery(hql)
                                   .setParameter("branchId", branchId)
                                   .uniqueResult();
        return count != null && count > 0;
    }

	public Long getNextBranchId() {
        return branchRepository.getNextBranchId();
    }
	
	@Override
	public List<Branch> findAll(String sortBy) {
		// TODO Auto-generated method stub
		return branchRepository.findAll(Sort.by(sortBy));
	}

}
