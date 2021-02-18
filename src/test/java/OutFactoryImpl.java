import com.feiniaojin.pie.OutboundFactory;

/**
 * TODO:Add the description of this class.
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */
public class OutFactoryImpl implements OutboundFactory {
    @Override
    public Object newInstance() {
        return new Result();
    }
}
