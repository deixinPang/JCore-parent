package config;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;


/**
 * @Author pangdexin
 * @Date 2023/5/29 16:16
 * @PackageName:config
 * @Description: TODO
 */
@Component
public class I18n{

  private static ResourceBundleMessageSource messageSource;

  @Autowired
  I18n(ResourceBundleMessageSource messageSource) {
    I18n.messageSource = messageSource;
  }

  public static String getMessage(String msgCode) {
    Locale locale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(msgCode, null, locale);
  }

  public static String getMessage(String msgCode, Object... params) {
    Locale locale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(msgCode, params, locale);
  }

}
