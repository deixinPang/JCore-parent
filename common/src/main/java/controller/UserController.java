package controller;

import domain.dto.UserInputDTO;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

/**
 * @Author pangdexin
 * @Date 2023/7/17 11:25
 * @Description: TODO
 */
@RequestMapping("/v1/api/user")
@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping
  public User addUser(UserInputDTO userInputDTO){
    User user = new UserInputDTO().convertToUser();
    User saveUserResult  = userService.addUser(user);
    return saveUserResult;
  }


  /**
   * 一： User user = new User();
   *     user.setUsername(userInputDTO.getUsername());
   *     user.setAge(userInputDTO.getAge());
   *
   *     这样写也没问题，但是如果字段多呢？ 这肯定不是一个最优的做法；所以使用浅拷贝方法
   *     BeanUtils.copyProperties(UserInputDTO.class,user);
   *
   *二：  在写 Java 代码时，更多的需要考虑语义的操作
   *     虽然这段代码很好的简化和优化了代码，但是他的语义是有问题的，我们需要提现一个转化过程才好
   *   public User addUser(UserInputDTO userInputDTO){
   *     User user = convertFor(userInputDTO);
   *     return userService.addUser(user);
   *   }
   *
   *   private User convertFor(UserInputDTO userInputDTO){
   *     User user = new User();
   *     BeanUtils.copyProperties(UserInputDTO.class,user);
   *     return user;
   *   }
   *
   *三： 当实际工作中，完成了几个 API 的 DTO 转化时，我们会发现，这样的操作有很多很多，那么应该定义好一个接口，让所有这样的操作都有规则的进行。
   *   public User addUser(UserInputDTO userInputDTO){
   *     User user = new UserInputDTOConvert().convert(userInputDTO);
   *     return userService.addUser(user);
   *   }
   *  UserInputDTOConvert是接口实现类；
   *  new 这样一个 DTO 转化对象是没有必要的，而且每一个转化对象都是由在遇到 DTO 转化的时候才会出现，那我们应该考虑一下，是否可以将这个类和 DTO 进行聚合呢，
   *  @Getter
   * @Setter
   * public class UserInputDTO {
   *   private String username;
   *   private int age;
   *
   *   public User convertToUser(){
   *     UserInputDTOConvert userInputDTOConvert = new UserInputDTOConvert();
   *     User convert = userInputDTOConvert.convert(this);
   *     return convert;
   *   }
   *
   *   private static class UserInputDTOConvert implements DTOConvert<UserInputDTO,User> {
   *     @Override
   *     public User convert(UserInputDTO userInputDTO) {
   *       User user = new User();
   *       BeanUtils.copyProperties(userInputDTO,user);
   *       return user;
   *     }
   *   }
   * }
   *
   *四：还有问题，不应该直接直接返回 User 实体，这样就暴露了太多实体相关的信息，这样的返回值是不安全的，所以我们更应该返回一个 DTO 对象，我们可称它为 UserOutputDTO：
   *
   * */

}