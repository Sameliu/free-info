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
public class LoadStockToEASTMONEY {


    public static void main(String[] args) throws InterruptedException {
        Map<String,String> headerMap = new HashMap<String, String>();

        headerMap.put("Accept", "*/*");
        headerMap.put("Accept-Encoding", "gzip, deflate, sdch, br");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Cookie", "emstat_bc_emcount=20227688702010392143; st_pvi=67255246741345; emhq_picfq=1; st_si=83766546642245; ct=WJlQR2uTlEIz-DtZb8277aPqgy9ApuIhaxImLS8Sz90Y00tnDF9Y6WWcBEAn9348IvDQznw4KFAjaIafmgflndNjrBA0djpQD6vNUseGdIx60O6V7Jml2d_eNQMhpLr3ES3M8_talE0m5RXTXd2Tqvsevw4tUMba31811hsqk4E; ut=FobyicMgeV4J81c3MmH9Z3ms0fxi1jH8B3xcYglwLZFWD26MytJqCUpZUd9_FEl9OVea43fFD4viV9nNjZhDkPScOPEeL0sax8sODlIIUBfEo8eYla1tn2wGLpedV3-Zw6hd8579TqC5ErxB3ZPFz806JM7_82yzjGfzI8OqJxSw7J-WjuWVzEjL0OyU_0P2cHr7_g-CLy7sl45yu4S8B0b65n8kJVisQCE6FfUs98qP6cjJlotgAKI_QoL7Tb95GYD5EigrO9XCbkSR0XRqsA; uidal=7706094510221766%e5%bc%a0%e5%af%92123456789; pi=7706094510221766%3bm7706094510221766%3b%e5%bc%a0%e5%af%92123456789%3baFjprg%2bWlSmZPrgKD9v8lz1jjlAAikMzQJBVedd2qMWZB5hEyLx8LATN7thdkRc%2bEDNwhl%2blxvtvc1n5PJeG4ZV7vk5maqYNpCBuy%2fCK4lKiDqbrV8cUE1Hfjym15nzuGrXDlyQsnSRcxMsOZiPjweRhwmj8EVjsxZV42vg7emIrlgnghZGRELwvlCZfcpPVDJ4WwyXp%3bMcbm69C3ZpYXOmqTbkuoktoHaYzR1WvkDJVKEKZuBudbx33bDK7d5CwjdeUWrGAexaMZdqlnISzYGZhx0StMhTw6qL1RYnIgo7czIwuepXYyrkHhFztVkcJEzZO149ZBDnxGMAFbz%2b2BRiA2OMskWu0v58srkg%3d%3d; vtpst=|; HAList=f-0-000001-%u4E0A%u8BC1%u6307%u6570%2Ca-sz-002410-%u5E7F%u8054%u8FBE%2Ca-sh-600584-%u957F%u7535%u79D1%u6280%2Ca-sz-300149-%u91CF%u5B50%u9AD8%u79D1%2Ca-sz-300159-%u65B0%u7814%u80A1%u4EFD%2Ca-sh-600360-%u534E%u5FAE%u7535%u5B50%2Ca-sz-002127-%u5357%u6781%u7535%u5546%2Ca-sz-300166-%u4E1C%u65B9%u56FD%u4FE1%2Ca-sz-002605-%u59DA%u8BB0%u6251%u514B%2Ca-sz-000892-%u661F%u7F8E%u8054%u5408%2Ca-sh-601933-%u6C38%u8F89%u8D85%u5E02%2Ca-sz-002418-%u5EB7%u76DB%u80A1%u4EFD%2Ca-sh-600610-%u4E2D%u6BC5%u8FBE; em_hq_fls=old; emstat_ss_emcount=2_1468881745_2376792088");
        headerMap.put("Host", "myfavor.eastmoney.com");
        headerMap.put("Referer", "http://i.eastmoney.com/");
        headerMap.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2774.3 Safari/537.36");


        String url = "";
        List<DockInfo> list =  FileUtil.loadDockFromConf(null);
        for(DockInfo dockInfo : list){
            String code= dockInfo.getCode().replace("sh","").replace("sz","");
            if(dockInfo.getCode().startsWith("sh")){
                url = "https://myfavor.eastmoney.com/mystock?f=as&g=3081796&sc="+code+"%7C01%7C01&cb=addhandlerCallback&0.8628258002117573";
            }else {
                url = "https://myfavor.eastmoney.com/mystock?f=as&g=3081796&sc="+code+"%7C02%7C01&cb=addhandlerCallback&0.8628258002117573";
            }
            HttpClientUtil httpClientUtil = new HttpClientUtil();
            String httpOrgCreateTestRtn = httpClientUtil.doGet(url, "utf-8",headerMap);
            Thread.sleep(2000);
            System.out.println(code+": ["+dockInfo.getName() +"] -->添加成功");
            System.out.println("result:" + httpOrgCreateTestRtn);

        }
    }
}
