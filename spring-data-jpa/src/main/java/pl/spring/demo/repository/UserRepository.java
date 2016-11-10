package pl.spring.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.spring.demo.entity.UserEntity;

//TODO(mmotowid) add java docs
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query("select user from UserEntity user where upper(user.userName) like concat(upper(:name), '%')")
	public List<UserEntity> findUsersByName(@Param("name") String name);

}
