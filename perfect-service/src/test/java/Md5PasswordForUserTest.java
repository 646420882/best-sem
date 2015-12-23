import com.perfect.commons.constants.PasswordSalts;
import com.perfect.utils.MD5;

/**
 * Created by subdong on 15-10-20.
 */
public class Md5PasswordForUserTest {

    private static String userSalt = PasswordSalts.USER_SALT;

    public static void main(String[] args) {
        MD5.Builder md5Builder = new MD5.Builder();
        MD5 md5 = md5Builder.source("test1234").salt(userSalt).build();
        System.out.println(md5);
    }
}
