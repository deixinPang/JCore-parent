package service;

import entity.User;
import org.springframework.stereotype.Service;

/**
 * @Author pangdexin
 * @Date 2023/7/17 11:26
 * @Description: TODO
 */
@Service
public class UserService {
  public User addUser(User user){
    //保存对象到数据库
    return user;
  }
}
