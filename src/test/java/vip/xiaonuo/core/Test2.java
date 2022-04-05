
package vip.xiaonuo.core;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.junit.Test;
import vip.xiaonuo.core.util.PastTimeFormatUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 纯test
 *
 * @author xuyuxiang
 * @date 2020/5/2014:29
 */
public class Test2 {

    public static void main(String[] args) {
        String s = PastTimeFormatUtil.formatPastTime(DateUtil.parseDateTime("2020-08-05 19:24:33"));
        System.out.println(s);
    }

    /**
     * @Description: 钉钉群机器人发送消息
     * @author 邾茂星
     * @date 2022/3/4 14:41
     */
    @Test
    public void dingTalk(){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=25c56e817d4008df488fef23bbcf71042642577ce0a58f83388a914a6eff45f0");
        OapiRobotSendRequest request = new OapiRobotSendRequest();

        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent("通知：测试文本消息");
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(Arrays.asList("13173665883"));
        // isAtAll类型如果不为Boolean，请升级至最新SDK
//        at.setIsAtAll(true);
//        at.setAtUserIds(Arrays.asList("109929","32099"));
        request.setAt(at);
        try {
            OapiRobotSendResponse response = client.execute(request);
            System.out.println(JSON.toJSONString(response));
        } catch (ApiException e) {
            e.printStackTrace();
        }

        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle("通知");
        markdown.setText("#### 杭州天气 \n" +
                "> 9度，西北风1级，空气良89，相对温度73%\n\n" +
                "> ![screenshot](https://images.jcxxdd.com/1498828474256822273.jpg)\n"  +
                "> #### [立即处理](https://testdingding.jcxxdd.com/) \n");
        request.setMarkdown(markdown);

        try {
            OapiRobotSendResponse response = client.execute(request);
            System.out.println(JSON.toJSONString(response));
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSMS(){
        String url = "http://hd.mosapi.cn:9051/api/v1.0.0/message/mass/send";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("batchName", "短信测试");
        paramMap.put("msgType", "sms");
        paramMap.put("bizType", "100");
        List list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("to","13173665883");
        list.add(map);
        paramMap.put("items", list);
        paramMap.put("content", "测试发送短信");
        String jsonString = JSON.toJSONString(paramMap);
        String result = HttpRequest.post(url)
                .header("Content-Type", "application/json;charset=utf-8")
                .header("Accept", "application/json")
                .header("Authorization", "enNmc0Bzam06OGE5NjIzYWIxOWMxY2QzMTQ4NjhmZDljYTk4ZGNjMTg=")
                .body(jsonString)
                .execute().body();
        System.out.println(result);
    }

    @Test
    public void dingTalkAt(){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=25c56e817d4008df488fef23bbcf71042642577ce0a58f83388a914a6eff45f0");
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent("通知：消息通知测试");
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        String ids = "031903;185120293736952241";
        at.setAtUserIds(Arrays.asList(ids.split(";")));
        request.setAt(at);
        try {
            OapiRobotSendResponse response = client.execute(request);
            System.out.println(JSON.toJSONString(response));
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}
