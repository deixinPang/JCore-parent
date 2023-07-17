package domain;

/**
 * @Author pangdexin
 * @Date 2023/7/17 11:41
 * @Description: TODO
 */
public interface DTOConvert<S,T> {
  T convert(S s);
}
