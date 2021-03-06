package pl.spring.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import pl.spring.demo.entity.UserEntity;
import pl.spring.demo.to.UserTo;

//TODO(mmotowid) add java docs
public class UserMapper {

	public static UserTo map(UserEntity userEntity) {
		if (userEntity != null) {
			return new UserTo(userEntity.getId(), userEntity.getUserName(), userEntity.getPassword());
		}
		return null;
	}

	public static UserEntity map(UserTo userTo) {
		if (userTo != null) {
			return new UserEntity(userTo.getId(), userTo.getUserName(), userTo.getPassword());
		}
		return null;
	}

	public static List<UserTo> map2To(List<UserEntity> userEntities) {
		return userEntities.stream().map(UserMapper::map).collect(Collectors.toList());
	}

	public static List<UserEntity> map2Entity(List<UserTo> userEntities) {
		return userEntities.stream().map(UserMapper::map).collect(Collectors.toList());
	}
}
