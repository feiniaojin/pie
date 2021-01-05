import com.feiniaojin.pie.BootStrap;
import com.feiniaojin.pie.ChannelHandler;
import com.feiniaojin.pie.ChannelHandlerContext;

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
                .inboundParameter(dto)
                .outFactory(new OutFactoryImpl())
                .channel(new UserCheckChannel())
                .addChannelHandlerAtLast("first", new ChannelHandler() {
                    @Override
                    public void channelProcess(ChannelHandlerContext ctx,
                                               Object in,
                                               Object out) throws Exception {
                        System.out.println("first:11111");
                        ((UserDto) in).setName("first:change");
//                        throw new RuntimeException("");
                        Result re = (Result) out;
                        re.setCode(100);
                        re.setMsg("success");
//                        ctx.fireChannelProcess(inWrapper, outWrapper);
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx,
                                                Throwable cause,
                                                Object in,
                                                Object out) throws Exception {
                        System.out.println("+++++");
                        Result re = (Result) out;
                        re.setCode(404);
                        re.setMsg("异常");
                        ctx.fireExceptionCaught(cause, in, out);
                    }
                }).addChannelHandlerAtLast("second", new ChannelHandler() {

                    @Override
                    public void channelProcess(ChannelHandlerContext ctx,
                                               Object in,
                                               Object out) throws Exception {
                        System.out.println(((UserDto) in).getName());
                        System.out.println("second:22222");
                        Result re = (Result) out;
                        re.setCode(200);
                        re.setMsg("success");
                        ctx.fireChannelProcess(in, out);
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx,
                                                Throwable cause,
                                                Object in,
                                                Object out) throws Exception {
                        Result re = (Result) out;
                        System.out.println(re.getCode());
                        System.out.println(re.getMsg());
                        re.setCode(500);
                        re.setMsg("500异常");
                    }
                }).process();
        System.out.println(result.getCode());
        System.out.println(result.getMsg());
    }
}
