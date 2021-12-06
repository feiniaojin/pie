import com.feiniaojin.pie.BootStrap;

/**
 * TODO:Add the description of this class.
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */
public class PieTest {

    public static void main(String[] args) {

        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setName("username");

        BootStrap bootStrap = new BootStrap();
        Result result = (Result) bootStrap
                .inboundParameter(dto)//入参
                .outboundFactory(new OutFactoryImpl())//出参工厂
                .channel(new UserCheckChannel())//自定义channel
                .addChannelHandlerAtLast("first", new FirstHandler())//第一个handler
                .addChannelHandlerAtLast("second", new SecondHandler())//第二个handler
                .process();//执行
        //result为执行结果
        System.out.println(result.getCode());
        System.out.println(result.getMsg());
    }
}
