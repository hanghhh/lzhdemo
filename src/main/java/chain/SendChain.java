package chain;

/**
 * @author liuzihang
 * @description: TODO
 * @date 2021/4/21 4:44 下午
 */
public interface SendChain {
    /**
     * 返回false，表示过滤掉
     * @param pushMessage
     * @return
     */
    boolean filter(String pushMessage);
}
