package cn.yang.core;

import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoreApplicationTests {
    private RestTemplate template = new RestTemplate();
    @Test
    public void contextLoads() {
        String url ="http://localhost:6000/api/in_amount";
        Object o = template.getForObject(url, Object.class);
        Assert.assertNotNull("测试结果", o);
        System.err.println(o);
    }

}
