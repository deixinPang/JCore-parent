package domain.dto;

import domain.DTOConvert;
import entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @Author pangdexin
 * @Date 2023/7/17 11:30
 * @Description: TODO
 */
@Getter
@Setter
public class UserInputDTO {

  private String username;
  private int    age;

  public User convertToUser(){
    UserInputDTOConvert userInputDTOConvert = new UserInputDTOConvert();
    User convert = userInputDTOConvert.convert(this);
    return convert;
  }

  private static class UserInputDTOConvert implements DTOConvert<UserInputDTO,User> {
    @Override
    public User convert(UserInputDTO userInputDTO) {
      User user = new User();
      BeanUtils.copyProperties(userInputDTO,user);
      return user;
    }
  }
}
