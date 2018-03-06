package com.badgersoft.datawarehouse.rawdata.dao;

import com.badgersoft.datawarehouse.rawdata.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
}