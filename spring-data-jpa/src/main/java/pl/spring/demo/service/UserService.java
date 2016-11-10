package pl.spring.demo.service;

import java.util.List;

import pl.spring.demo.to.UserTo;

//TODO(mmotowid) add java docs
public interface UserService {

	List<UserTo> findUserByName(String name);
}
