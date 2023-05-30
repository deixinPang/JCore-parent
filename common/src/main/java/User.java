
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author pangdexin
 * @Date 2023/5/29 16:01
 * @PackageName:PACKAGE_NAME
 * @Description: TODO
 */

public class User {

  @NotNull(message = "user.id.required")
  private Long id;

  @NotBlank(message = "user.name.required")
  private String name;

  @Email(message = "user.email.invalid")
  private String email;

  @NotNull(message = "user.age.required")
  @Min(value = 18, message = "user.age.invalid")
  @Max(value = 99, message = "user.age.invalid")
  private Integer age;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }
}
