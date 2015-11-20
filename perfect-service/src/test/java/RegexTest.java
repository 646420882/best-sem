import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaowei
 * @title RegexTest
 * @package PACKAGE_NAME
 * @description
 * @update 2015年10月20日. 下午5:10
 */
public class RegexTest {
    public static void main(String[] args) {
        String str="a霜撒旦法asdf";
//        Pattern pattern = Pattern.compile("^(?!.*(a霜)).*$", Pattern.CASE_INSENSITIVE);    //不包含
//        Pattern pattern = Pattern.compile("^.*a.*$", Pattern.CASE_INSENSITIVE); // 包含
//        Pattern pattern = Pattern.compile("^a霜.*$", Pattern.CASE_INSENSITIVE); // 以。。开始
        Pattern pattern = Pattern.compile(".*asdf$", Pattern.CASE_INSENSITIVE); // 以。。结尾
        Matcher matcher= pattern.matcher(str);
        System.out.println(matcher.find());
    }
}