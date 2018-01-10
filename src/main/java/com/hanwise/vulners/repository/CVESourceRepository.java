package com.hanwise.vulners.repository;

import com.hanwise.vulners.entity.CVESource;
import org.springframework.data.repository.CrudRepository;

public interface CVESourceRepository extends CrudRepository<CVESource,String>{
    CVESource findById(String id);
}
