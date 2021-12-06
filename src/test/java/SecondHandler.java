import com.feiniaojin.pie.ChannelHandler;
import com.feiniaojin.pie.ChannelHandlerContext;

/**
 * TODO:Add the description of this class.
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */
public class SecondHandler implements ChannelHandler {
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
}
