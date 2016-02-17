package com.ms.training.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mark.zhu on 2016/1/5.
 */
public class RegExpressionTest {
    private static final String REG_MOBILE = "^0?(13|15|18|14|17)[0-9]{9}$";

    @Test
    public void testFind() {
        String s = "<scf><header><txId>384596</txId><txTime>20160105160000</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>沛县宇诺商贸有限公司</custname><custno>8800112852</custno><orderamt>186541.06</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160000</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>雄县北大电器门市部</custname><custno>8800118487</custno><orderamt>86045.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160001</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>六枝特区鼎盛电器行</custname><custno>8800119996</custno><orderamt>17190.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160001</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>华蓥市双河和信电器经营部</custname><custno>8800121388</custno><orderamt>111874.17</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160002</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>山阳福茂商贸有限公司</custname><custno>8800121850</custno><orderamt>17053.20</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160002</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>南通和泽贸易有限公司</custname><custno>8800123358</custno><orderamt>-2899.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160003</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>宁波市海可商贸有限公司</custname><custno>8800123770</custno><orderamt>29985.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160003</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>河南先声电器有限公司</custname><custno>8800131920</custno><orderamt>2999.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160004</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>仙桃市诚信电器批发部</custname><custno>8800135499</custno><orderamt>69900.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160004</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>扬州海佑轩商贸有限公司</custname><custno>8800139725</custno><orderamt>5399.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160005</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>连云港国正纸箱包装有限公司</custname><custno>8800140039</custno><orderamt>384976.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160005</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>龙岩市亿博贸易有限公司</custname><custno>8800140598</custno><orderamt>42061.40</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160006</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>淮南海德商贸有限公司</custname><custno>8800141641</custno><orderamt>65271.50</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160006</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>成都好爱嘉商贸有限公司</custname><custno>8800159978</custno><orderamt>64627.01</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160007</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>寿县伟业商贸有限公司</custname><custno>8700002090</custno><orderamt>285095.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160007</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>利辛县源丰商贸有限责任公司</custname><custno>8700002478</custno><orderamt>10954.34</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160008</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>潜山县华盛家电有限责任公司</custname><custno>8700006578</custno><orderamt>89900.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160008</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>诸城市仁和五交化有限责任公司家电城</custname><custno>8700008318</custno><orderamt>346521.06</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160009</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>福建省永春弘盛家电有限公司</custname><custno>8700008691</custno><orderamt>-799.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160009</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>邢台汇海家电商行</custname><custno>8700009200</custno><orderamt>-10128.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160010</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>重庆市潼南东方实业有限公司</custname><custno>8700010560</custno><orderamt>13854.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160010</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>雅安诺亚电器有限责任公司</custname><custno>8700012282</custno><orderamt>18794.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160011</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>定州市创联电器有限公司</custname><custno>8700017679</custno><orderamt>448011.50</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160011</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>嵩明杨林玉妮家电维修部</custname><custno>8700041283</custno><orderamt>143188.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160011</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>威海诚佳电气安装工程有限公司</custname><custno>8700051156</custno><orderamt>-7070.45</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160012</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>洛阳中飞特商贸有限公司</custname><custno>8700056022</custno><orderamt>131528.69</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160013</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>青岛东之旭经贸有限公司</custname><custno>8700090831</custno><orderamt>-1024.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160013</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>韶关市恒煦电器机电有限公司</custname><custno>8700095107</custno><orderamt>224350.40</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160014</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>西安雷廷商贸有限公司</custname><custno>8700107068</custno><orderamt>12145.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160014</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>上海博菱电器设备有限公司</custname><custno>8800010592</custno><orderamt>31054.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160015</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>济宁市天安家电有限公司</custname><custno>8800010913</custno><orderamt>139856.08</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160015</txTime><rtCode>0</rtCode></header><body><cancleamt>89990.00</cancleamt><custname>海南丽岛安装工程有限公司</custname><custno>8800011155</custno><orderamt>311570.59</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160016</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>仙游县榜头添发电器店</custname><custno>8800011678</custno><orderamt>36289.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160016</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>麻城市明刚电器销售有限公司</custname><custno>8800012751</custno><orderamt>32580.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160017</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>原平市原平燕青家电销售中心</custname><custno>8800013214</custno><orderamt>34758.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160017</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>甘肃毓祥商贸有限公司</custname><custno>8800014038</custno><orderamt>518602.13</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160018</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>河西百佳电器销售部</custname><custno>8800014533</custno><orderamt>-2998.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160018</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>无锡水之源商贸有限公司</custname><custno>8800014749</custno><orderamt>64603.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160019</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>永年县天顺经贸有限公司</custname><custno>8800015396</custno><orderamt>115721.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160019</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>徐州颂福商贸有限公司</custname><custno>8800015417</custno><orderamt>21991.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160020</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>郑州海一贸易有限公司</custname><custno>8800015854</custno><orderamt>34725.40</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160020</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>永善县恒信电器销售有限公司</custname><custno>8800017860</custno><orderamt>20646.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160021</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>济南三星伟业经贸有限公司</custname><custno>8800018034</custno><orderamt>479843.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160021</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>武汉市京鸿泰电器有限公司</custname><custno>8800019999</custno><orderamt>441287.84</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160022</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>剑阁县博来电器销售有限公司</custname><custno>8800020242</custno><orderamt>47727.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160022</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>银川乾梁商贸有限公司</custname><custno>8800023004</custno><orderamt>33506.81</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160023</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>贺州市荣盛家电有限责任公司</custname><custno>8800100469</custno><orderamt>200121.00</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160023</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>淄博福桂轩商贸有限公司</custname><custno>8800100771</custno><orderamt>23668.20</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160023</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>阜南县鑫海电器有限公司</custname><custno>8800101853</custno><orderamt>206153.58</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160024</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>石家庄美尚商贸有限公司</custname><custno>8800102254</custno><orderamt>39123.40</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160024</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>崇州市崇阳新乐家电器经营部</custname><custno>8800102306</custno><orderamt>40536.04</orderamt><orderdate>20160104</orderdate></body></scf> \n" +
                "<scf><header><txId>384596</txId><txTime>20160105160025</txTime><rtCode>0</rtCode></header><body><cancleamt>0.00</cancleamt><custname>深圳市隆阳电器有限公司</custname><custno>8800103746</custno><orderamt>11684.20</orderamt><orderdate>20160104</orderdate></body></scf> \n";
        Pattern p = Pattern.compile("<custno>(.*)</custno>");

//		2015-12-10 00:11:03,378 INFO   [bankIn10983595] [SocketServer        ] 请求[null]处理结束,用时(ms):16
//      ^2015-12-10.*请求\[null\]处理结束,用时\(ms\):\d{1,3}$

//		^\r\n$ 匹配空行

        Matcher m = p.matcher(s);
        while (m.find()) {
            System.out.println(m.group() + " -- " + m.group(1));
        }
    }

}
