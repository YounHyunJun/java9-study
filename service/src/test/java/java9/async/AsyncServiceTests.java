package java9.async;

import org.awaitility.Duration;
import org.junit.Before;
import org.junit.Test;

import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.given;
import static org.awaitility.proxy.AwaitilityClassProxy.to;
import static org.hamcrest.Matchers.equalTo;

public class AsyncServiceTests {

    private AsyncService asyncService;

    @Before
    public void setUp() {
        asyncService = new AsyncService();
    }

    @Test
    public void asyncTest() {
        asyncService.initialize();
        await().until(asyncService::isInitialized);
        System.out.println("end");
    }

    @Test
    public void asyncTest2() {
        asyncService.initialize();
        await()
                .atLeast(Duration.ONE_HUNDRED_MILLISECONDS)
                .atMost(Duration.FIVE_SECONDS)
                .with()
                .pollInterval(Duration.ONE_HUNDRED_MILLISECONDS)
                .until(asyncService::isInitialized);
    }

    @Test
    public void asyncMatcherTest() {
        asyncService.initialize();
        await()
                .until(asyncService::isInitialized);
        long value = 5;
        asyncService.addValue(value);
        await()
                .until(asyncService::getValue, equalTo(value));
    }

    @Test
    public void ignoreTest() {
        asyncService.initialize();
        given()
                .ignoreException(IllegalStateException.class)
                .await().atMost(Duration.FIVE_SECONDS)
                .atLeast(Duration.FIVE_HUNDRED_MILLISECONDS)
                .until(asyncService::getValue, equalTo(0L));
    }

    @Test
    public void proxyTest() {
        asyncService.initialize();
        // not support java12
        await()
                .untilCall(to(asyncService).isInitialized(), equalTo(true));
    }

}

