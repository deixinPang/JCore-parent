/*******************************************************************************
 * 广州理德物联网科技有限公司
 * Copyright (c) 2019.
 ******************************************************************************/

package config;

import cn.hutool.core.util.StrUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * @author Administrator
 */
public class MyLocaleResolver extends AcceptHeaderLocaleResolver {

  List<Locale> LOCALES = Arrays.asList(
      Locale.ENGLISH,

      Locale.SIMPLIFIED_CHINESE);

  @Override
  public Locale resolveLocale(HttpServletRequest request) {
    String headerLang = request.getHeader("Accept-Language");
    //当请求未传入语言时，默认设置为简体中文
    return headerLang == null || StrUtil.isBlank(headerLang)
        ? Locale.SIMPLIFIED_CHINESE
        : request.getLocale();
  }

  /**
   * 获取Locale对象，不在范围内或是出错则返回null
   *
   * @param acceptLanguage Accept-Language
   * @return Locale对象
   */
  public static Locale getLocale(String acceptLanguage) {
    List<LanguageRange> languageRangeList;
    try {
      acceptLanguage    = Optional.ofNullable(acceptLanguage)
          .map(s -> s.replace("_", "-")).orElse("");
      languageRangeList = LanguageRange.parse(acceptLanguage);
    } catch (Exception e) {
      languageRangeList = new ArrayList<>();
    }
    return Optional.of(languageRangeList)
        .filter(list -> !list.isEmpty())
        .map(list -> list.get(0).getRange())
        .map(Locale::forLanguageTag).orElse(null);
  }

}
