import com.feiniaojin.pie.OutFactory;

/**
 * TODO:Add the description of this class.
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */
public class OutFactoryImpl implements OutFactory {
    @Override
    public Result newInstance() {
        return new Result();
    }
}
