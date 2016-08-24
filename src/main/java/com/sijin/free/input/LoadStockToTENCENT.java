package com.sijin.free.input;

import com.sijin.free.http.HttpClientUtil;
import com.sijin.free.po.DockInfo;
import com.sijin.free.util.FileUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sijinzhang on 16/7/18.
 */
public class LoadStockToTENCENT {


    public static void main(String[] args) throws InterruptedException {
        Map<String,String> headerMap = new HashMap<String, String>();

        headerMap.put("Accept", "*/*");
        headerMap.put("Accept-Encoding", "gzip, deflate, sdch");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Cookie", "pac_uid=1_272885400; ts_refer=news.baidu.com/ns; ts_uid=3942265994; ptisp=ctc; RK=BRc6XL3iRu; pgv_pvi=7404557312; pgv_si=s4741209088; ptui_loginuin=272885400; pt2gguin=o0272885400; uin=o0272885400; skey=@5D2p2fxq9; ptcz=41e5dd35b95bcfe99c001462a8888172f8f17452ca78bdfdcbea9403e4986f19; ASRECENT_CODE=PRU.N_200%7C%7C; uid=324683652; RECENT_CODE=600360_1%7C300527_51%7C601231_1%7C601169_1%7C600707_1%7C000970_51%7C300264_51%7C002514_51%7C000970_0%7C00970_100%7C000823_51%7C600406_1%7C000725_51%7C002300_51%7C600973_1%7C300003_51%7C600558_1%7C600305_1%7C000023_51%7C600606_1%7C000001_1%7C000801_51%7C002290_51%7C; pgv_info=ssid=s9974338630; pgv_pvid=9116037710; o_cookie=272885400");
        headerMap.put("Host", "webstock.finance.qq.com");
        headerMap.put("Referer", "http://gu.qq.com/i/");
        headerMap.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2774.3 Safari/537.36");


        String url = "";
        List<DockInfo> list =  FileUtil.loadDockFromConf(null);
        for(DockInfo dockInfo : list){
            url = "http://webstock.finance.qq.com/stockapp/Updstockweb/operseq?&app=web&uin=272885400&seq=%5B%7B%22grpid%22:%221%22,%22act%22:%22sa%22,%22code%22:%22"+dockInfo.getCode()+"%22,%22timestamp%22:1468855960149%7D%5D&callback=GET_12&_=1468855807597";
            HttpClientUtil httpClientUtil = new HttpClientUtil();
            String httpOrgCreateTestRtn = httpClientUtil.doGet(url, null,headerMap);
            Thread.sleep(2000);
            System.out.println(dockInfo.getCode()+": ["+dockInfo.getName() +"] -->添加成功");
            System.out.println("result:" + httpOrgCreateTestRtn);

        }
    }
}
