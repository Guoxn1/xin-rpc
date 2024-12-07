import com.xin.xinrpc.serialize.Serializer;

import java.util.Arrays;

/**
 * ClassName: test
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/28 下午8:10
 * @Version 1.0
 */
public class test {
    public static void main(String[] args) {

        try {
            System.out.println(Class.forName(String.class.getName()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
