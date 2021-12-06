import com.feiniaojin.pie.ChannelHandler;
import com.feiniaojin.pie.ChannelHandlerContext;

/**
 * TODO:Add the description of this class.
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */
public class FirstHandler implements ChannelHandler {

    @Override
    public void channelProcess(ChannelHandlerContext ctx,
                               Object in,
                               Object out) throws Exception {
        System.out.println("first:11111");
        ((UserDto) in).setName("first:change");
        Result re = (Result) out;
        re.setCode(100);
        re.setMsg("success");
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
}
