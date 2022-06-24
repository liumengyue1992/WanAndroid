package com.lmy.androidutilcode.ui.districtphonenum

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lmy.androidutilcode.base.BaseViewModel
import com.lmy.androidutilcode.bean.DistrictPhoneBean
import com.lmy.androidutilcode.bean.DistrictPhoneIndexBean
import com.lmy.androidutilcode.bean.DistrictPhoneItemBean
import com.lmy.androidutilcode.network.BaseResult
import com.lmy.androidutilcode.network.RetrofitManager
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.util.*

/**
 * @Description: TODO
 * @Author: liheng.cai
 * @Date: 2022/2/16 10:27
 * @Email: liheng.cai@upuphone.com
 */
class DistrictPhoneViewModel(app: Application) : BaseViewModel(app) {
    
    val districtPhoneList = MutableLiveData<MutableList<DistrictPhoneBean>>()
    
    val letterKeyList = MutableLiveData<List<String>>()
    
    val selectLetterFirstIndexKey = MutableLiveData<DistrictPhoneIndexBean>()
    
    /**
     * 初始化地区信息
     */
    fun onGetDistrictPhoneInfo() {
        viewModelScope.launch {
            // val result = request({ RetrofitManager.apiService.getDistrictPhoneCode() })
            
            // 写死假数据
            var jsonString = "{\"code\":0,\"msg\":\"success\",\"data\":{\"phoneAreaCodes\":[{\"initial\":\"A\",\"en\":\"Angola\",\"cn\":\"安哥拉\",\"code\":\"244\"},{\"initial\":\"A\",\"en\":\"Afghanistan\",\"cn\":\"阿富汗\",\"code\":\"93\"},{\"initial\":\"A\",\"en\":\"Alaska(U.S.A)\",\"cn\":\"阿拉斯加\",\"code\":\"1907\"},{\"initial\":\"A\",\"en\":\"Albania\",\"cn\":\"阿尔巴尼亚\",\"code\":\"335\"},{\"initial\":\"A\",\"en\":\"Algeria\",\"cn\":\"阿尔及利亚\",\"code\":\"213\"},{\"initial\":\"A\",\"en\":\"Andorra\",\"cn\":\"安道尔共和国\",\"code\":\"376\"},{\"initial\":\"A\",\"en\":\"Anguilla\",\"cn\":\"安圭拉岛\",\"code\":\"1254\"},{\"initial\":\"A\",\"en\":\"Antigua and Barbuda\",\"cn\":\"安提瓜和巴布达\",\"code\":\"1268\"},{\"initial\":\"A\",\"en\":\"Argentina\",\"cn\":\"阿根廷\",\"code\":\"54\"},{\"initial\":\"A\",\"en\":\"Aruba\",\"cn\":\"阿鲁巴岛\",\"code\":\"297\"},{\"initial\":\"A\",\"en\":\"Ascension\",\"cn\":\"阿森松\",\"code\":\"247\"},{\"initial\":\"A\",\"en\":\"Australia\",\"cn\":\"澳大利亚\",\"code\":\"61\"},{\"initial\":\"A\",\"en\":\"Austria\",\"cn\":\"奥地利\",\"code\":\"43\"},{\"initial\":\"A\",\"en\":\"Azerbaijan\",\"cn\":\"阿塞拜疆\",\"code\":\"994\"},{\"initial\":\"A\",\"en\":\"Egypt\",\"cn\":\"埃及\",\"code\":\"20\"},{\"initial\":\"A\",\"en\":\"Estonia\",\"cn\":\"爱沙尼亚\",\"code\":\"372\"},{\"initial\":\"A\",\"en\":\"Ethiopia\",\"cn\":\"埃塞俄比亚\",\"code\":\"251\"},{\"initial\":\"A\",\"en\":\"Ireland\",\"cn\":\"爱尔兰\",\"code\":\"353\"},{\"initial\":\"A\",\"en\":\"Macao\",\"cn\":\"澳门\",\"code\":\"853\"},{\"initial\":\"A\",\"en\":\"Oman\",\"cn\":\"阿曼\",\"code\":\"968\"},{\"initial\":\"A\",\"en\":\"United Arab Emirates\",\"cn\":\"阿拉伯联合酋长国\",\"code\":\"971\"},{\"initial\":\"B\",\"en\":\"Bahamas\",\"cn\":\"巴哈马\",\"code\":\"1242\"},{\"initial\":\"B\",\"en\":\"Bahrain\",\"cn\":\"巴林\",\"code\":\"973\"},{\"initial\":\"B\",\"en\":\"Barbados\",\"cn\":\"巴巴多斯\",\"code\":\"1246\"},{\"initial\":\"B\",\"en\":\"Belarus\",\"cn\":\"白俄罗斯\",\"code\":\"375\"},{\"initial\":\"B\",\"en\":\"Belgium\",\"cn\":\"比利时\",\"code\":\"32\"},{\"initial\":\"B\",\"en\":\"Belize\",\"cn\":\"伯利兹\",\"code\":\"501\"},{\"initial\":\"B\",\"en\":\"Benin\",\"cn\":\"贝宁\",\"code\":\"229\"},{\"initial\":\"B\",\"en\":\"Bermuda Is\",\"cn\":\"百慕大群岛\",\"code\":\"1441\"},{\"initial\":\"B\",\"en\":\"Bhutan\",\"cn\":\"不丹\",\"code\":\"975\"},{\"initial\":\"B\",\"en\":\"Bolivia\",\"cn\":\"玻利维亚\",\"code\":\"591\"},{\"initial\":\"B\",\"en\":\"Bosnia And Herzegovina\",\"cn\":\"波斯尼亚和黑塞哥维那\",\"code\":\"387\"},{\"initial\":\"B\",\"en\":\"Botswana\",\"cn\":\"博茨瓦纳\",\"code\":\"267\"},{\"initial\":\"B\",\"en\":\"Brazil\",\"cn\":\"巴西\",\"code\":\"55\"},{\"initial\":\"B\",\"en\":\"Bulgaria\",\"cn\":\"保加利亚\",\"code\":\"359\"},{\"initial\":\"B\",\"en\":\"Burkina Faso\",\"cn\":\"布基纳法索\",\"code\":\"226\"},{\"initial\":\"B\",\"en\":\"Burundi\",\"cn\":\"布隆迪\",\"code\":\"257\"},{\"initial\":\"B\",\"en\":\"Iceland\",\"cn\":\"冰岛\",\"code\":\"354\"},{\"initial\":\"B\",\"en\":\"Pakistan\",\"cn\":\"巴基斯坦\",\"code\":\"92\"},{\"initial\":\"B\",\"en\":\"Panama\",\"cn\":\"巴拿马\",\"code\":\"507\"},{\"initial\":\"B\",\"en\":\"Papua New Cuinea\",\"cn\":\"巴布亚新几内亚\",\"code\":\"675\"},{\"initial\":\"B\",\"en\":\"Paraguay\",\"cn\":\"巴拉圭\",\"code\":\"595\"},{\"initial\":\"B\",\"en\":\"Poland\",\"cn\":\"波兰\",\"code\":\"48\"},{\"initial\":\"B\",\"en\":\"Puerto Rico\",\"cn\":\"波多黎各\",\"code\":\"1787\"},{\"initial\":\"C\",\"en\":\"Congo\",\"cn\":\"刚果\",\"code\":\"242\"},{\"initial\":\"C\",\"en\":\"North Korea\",\"cn\":\"朝鲜\",\"code\":\"850\"},{\"initial\":\"D\",\"en\":\"Denmark\",\"cn\":\"丹麦\",\"code\":\"45\"},{\"initial\":\"D\",\"en\":\"Dominica Rep\",\"cn\":\"多米尼加共和国\",\"code\":\"1890\"},{\"initial\":\"D\",\"en\":\"Samoa Eastern\",\"cn\":\"东萨摩亚(美)\",\"code\":\"684\"},{\"initial\":\"D\",\"en\":\"Togo\",\"cn\":\"多哥\",\"code\":\"228\"},{\"initial\":\"E\",\"en\":\"Ecuador\",\"cn\":\"厄瓜多尔\",\"code\":\"593\"},{\"initial\":\"E\",\"en\":\"Russia\",\"cn\":\"俄罗斯\",\"code\":\"7\"},{\"initial\":\"F\",\"en\":\"Cape Verde\",\"cn\":\"佛得角\",\"code\":\"238\"},{\"initial\":\"F\",\"en\":\"Fiji\",\"cn\":\"斐济\",\"code\":\"679\"},{\"initial\":\"F\",\"en\":\"Finland\",\"cn\":\"芬兰\",\"code\":\"358\"},{\"initial\":\"F\",\"en\":\"France\",\"cn\":\"法国\",\"code\":\"33\"},{\"initial\":\"F\",\"en\":\"French Guiana\",\"cn\":\"法属圭亚那\",\"code\":\"594\"},{\"initial\":\"F\",\"en\":\"French Polynesia\",\"cn\":\"法属玻利尼西亚\",\"code\":\"689\"},{\"initial\":\"F\",\"en\":\"Philippines\",\"cn\":\"菲律宾\",\"code\":\"63\"},{\"initial\":\"G\",\"en\":\"Colombia\",\"cn\":\"哥伦比亚\",\"code\":\"57\"},{\"initial\":\"G\",\"en\":\"Costa Rica\",\"cn\":\"哥斯达黎加\",\"code\":\"506\"},{\"initial\":\"G\",\"en\":\"Cuba\",\"cn\":\"古巴\",\"code\":\"53\"},{\"initial\":\"G\",\"en\":\"Gambia\",\"cn\":\"冈比亚\",\"code\":\"220\"},{\"initial\":\"G\",\"en\":\"Georgia\",\"cn\":\"格鲁吉亚\",\"code\":\"995\"},{\"initial\":\"G\",\"en\":\"Germany\",\"cn\":\"德国\",\"code\":\"49\"},{\"initial\":\"G\",\"en\":\"Grenada\",\"cn\":\"格林纳达\",\"code\":\"1809\"},{\"initial\":\"G\",\"en\":\"Guam\",\"cn\":\"关岛\",\"code\":\"1671\"},{\"initial\":\"G\",\"en\":\"Guyana\",\"cn\":\"圭亚那\",\"code\":\"592\"},{\"initial\":\"H\",\"en\":\"Haiti\",\"cn\":\"海地\",\"code\":\"509\"},{\"initial\":\"H\",\"en\":\"Honduras\",\"cn\":\"洪都拉斯\",\"code\":\"504\"},{\"initial\":\"H\",\"en\":\"Kazakstan\",\"cn\":\"哈萨克斯坦\",\"code\":\"327\"},{\"initial\":\"H\",\"en\":\"Korea\",\"cn\":\"韩国\",\"code\":\"82\"},{\"initial\":\"H\",\"en\":\"Netheriands Antilles\",\"cn\":\"荷属安的列斯\",\"code\":\"599\"},{\"initial\":\"H\",\"en\":\"Netherlands\",\"cn\":\"荷兰\",\"code\":\"31\"},{\"initial\":\"J\",\"en\":\"Canada\",\"cn\":\"加拿大\",\"code\":\"1\"},{\"initial\":\"J\",\"en\":\"Czech Republic\",\"cn\":\"捷克\",\"code\":\"420\"},{\"initial\":\"J\",\"en\":\"Djibouti\",\"cn\":\"吉布提\",\"code\":\"253\"},{\"initial\":\"J\",\"en\":\"Gabon\",\"cn\":\"加蓬\",\"code\":\"241\"},{\"initial\":\"J\",\"en\":\"Ghana\",\"cn\":\"加纳\",\"code\":\"233\"},{\"initial\":\"J\",\"en\":\"Guinea\",\"cn\":\"几内亚\",\"code\":\"224\"},{\"initial\":\"J\",\"en\":\"Kampuchea (Cambodia )\",\"cn\":\"柬埔寨\",\"code\":\"855\"},{\"initial\":\"J\",\"en\":\"Kyrgyzstan\",\"cn\":\"吉尔吉斯坦\",\"code\":\"331\"},{\"initial\":\"J\",\"en\":\"Zimbabwe\",\"cn\":\"津巴布韦\",\"code\":\"263\"},{\"initial\":\"K\",\"en\":\"Cameroon\",\"cn\":\"喀麦隆\",\"code\":\"237\"},{\"initial\":\"K\",\"en\":\"Cayman Is\",\"cn\":\"开曼群岛\",\"code\":\"1345\"},{\"initial\":\"K\",\"en\":\"Cook Is\",\"cn\":\"库克群岛\",\"code\":\"682\"},{\"initial\":\"K\",\"en\":\"Republic of Croatia\",\"cn\":\"克罗地亚共和国\",\"code\":\"385\"},{\"initial\":\"K\",\"en\":\"Ivory Coast\",\"cn\":\"科特迪瓦\",\"code\":\"225\"},{\"initial\":\"K\",\"en\":\"Kenya\",\"cn\":\"肯尼亚\",\"code\":\"254\"},{\"initial\":\"K\",\"en\":\"Kuwait\",\"cn\":\"科威特\",\"code\":\"965\"},{\"initial\":\"K\",\"en\":\"Qatar\",\"cn\":\"卡塔尔\",\"code\":\"974\"},{\"initial\":\"L\",\"en\":\"Laos\",\"cn\":\"老挝\",\"code\":\"856\"},{\"initial\":\"L\",\"en\":\"Latvia\",\"cn\":\"拉脱维亚\",\"code\":\"371\"},{\"initial\":\"L\",\"en\":\"Lebanon\",\"cn\":\"黎巴嫩\",\"code\":\"961\"},{\"initial\":\"L\",\"en\":\"Lesotho\",\"cn\":\"莱索托\",\"code\":\"266\"},{\"initial\":\"L\",\"en\":\"Liberia\",\"cn\":\"利比里亚\",\"code\":\"231\"},{\"initial\":\"L\",\"en\":\"Libya\",\"cn\":\"利比亚\",\"code\":\"218\"},{\"initial\":\"L\",\"en\":\"Liechtenstein\",\"cn\":\"列支敦士登\",\"code\":\"423\"},{\"initial\":\"L\",\"en\":\"Lithuania\",\"cn\":\"立陶宛\",\"code\":\"370\"},{\"initial\":\"L\",\"en\":\"Luxembourg\",\"cn\":\"卢森堡\",\"code\":\"352\"},{\"initial\":\"L\",\"en\":\"Reunion\",\"cn\":\"留尼旺\",\"code\":\"262\"},{\"initial\":\"L\",\"en\":\"Romania\",\"cn\":\"罗马尼亚\",\"code\":\"40\"},{\"initial\":\"M\",\"en\":\"Bangladesh\",\"cn\":\"孟加拉国\",\"code\":\"880\"},{\"initial\":\"M\",\"en\":\"Burma\",\"cn\":\"缅甸\",\"code\":\"95\"},{\"initial\":\"M\",\"en\":\"Madagascar\",\"cn\":\"马达加斯加\",\"code\":\"261\"},{\"initial\":\"M\",\"en\":\"Malawi\",\"cn\":\"马拉维\",\"code\":\"265\"},{\"initial\":\"M\",\"en\":\"Malaysia\",\"cn\":\"马来西亚\",\"code\":\"60\"},{\"initial\":\"M\",\"en\":\"Maldives\",\"cn\":\"马尔代夫\",\"code\":\"960\"},{\"initial\":\"M\",\"en\":\"Mali\",\"cn\":\"马里\",\"code\":\"223\"},{\"initial\":\"M\",\"en\":\"Malta\",\"cn\":\"马耳他\",\"code\":\"356\"},{\"initial\":\"M\",\"en\":\"Mariana Is\",\"cn\":\"马里亚那群岛\",\"code\":\"1670\"},{\"initial\":\"M\",\"en\":\"Martinique\",\"cn\":\"马提尼克\",\"code\":\"596\"},{\"initial\":\"M\",\"en\":\"Mauritius\",\"cn\":\"毛里求斯\",\"code\":\"230\"},{\"initial\":\"M\",\"en\":\"Mexico\",\"cn\":\"墨西哥\",\"code\":\"52\"},{\"initial\":\"M\",\"en\":\"Moldova\",\"cn\":\"摩尔多瓦\",\"code\":\"373\"},{\"initial\":\"M\",\"en\":\"Monaco\",\"cn\":\"摩纳哥\",\"code\":\"377\"},{\"initial\":\"M\",\"en\":\"Mongolia\",\"cn\":\"蒙古\",\"code\":\"976\"},{\"initial\":\"M\",\"en\":\"Montserrat Is\",\"cn\":\"蒙特塞拉特岛\",\"code\":\"1664\"},{\"initial\":\"M\",\"en\":\"Morocco\",\"cn\":\"摩洛哥\",\"code\":\"212\"},{\"initial\":\"M\",\"en\":\"Mozambique\",\"cn\":\"莫桑比克\",\"code\":\"258\"},{\"initial\":\"M\",\"en\":\"Peru\",\"cn\":\"秘鲁\",\"code\":\"51\"},{\"initial\":\"M\",\"en\":\"United States of America\",\"cn\":\"美国\",\"code\":\"1\"},{\"initial\":\"N\",\"en\":\"Namibia\",\"cn\":\"纳米比亚\",\"code\":\"264\"},{\"initial\":\"N\",\"en\":\"Nauru\",\"cn\":\"瑙鲁\",\"code\":\"674\"},{\"initial\":\"N\",\"en\":\"Nepal\",\"cn\":\"尼泊尔\",\"code\":\"977\"},{\"initial\":\"N\",\"en\":\"Nicaragua\",\"cn\":\"尼加拉瓜\",\"code\":\"505\"},{\"initial\":\"N\",\"en\":\"Niger\",\"cn\":\"尼日尔\",\"code\":\"227\"},{\"initial\":\"N\",\"en\":\"Nigeria\",\"cn\":\"尼日利亚\",\"code\":\"234\"},{\"initial\":\"N\",\"en\":\"Norway\",\"cn\":\"挪威\",\"code\":\"47\"},{\"initial\":\"N\",\"en\":\"South Africa\",\"cn\":\"南非\",\"code\":\"27\"},{\"initial\":\"N\",\"en\":\"Yugoslavia\",\"cn\":\"南斯拉夫\",\"code\":\"381\"},{\"initial\":\"P\",\"en\":\"Portugal\",\"cn\":\"葡萄牙\",\"code\":\"351\"},{\"initial\":\"R\",\"en\":\"Japan\",\"cn\":\"日本\",\"code\":\"81\"},{\"initial\":\"R\",\"en\":\"Sweden\",\"cn\":\"瑞典\",\"code\":\"46\"},{\"initial\":\"R\",\"en\":\"Switzerland\",\"cn\":\"瑞士\",\"code\":\"41\"},{\"initial\":\"S\",\"en\":\"Cyprus\",\"cn\":\"塞浦路斯\",\"code\":\"357\"},{\"initial\":\"S\",\"en\":\"EI Salvador\",\"cn\":\"萨尔瓦多\",\"code\":\"503\"},{\"initial\":\"S\",\"en\":\"Saint Lueia\",\"cn\":\"圣卢西亚\",\"code\":\"1758\"},{\"initial\":\"S\",\"en\":\"Saint Vincent\",\"cn\":\"圣文森特岛\",\"code\":\"1784\"},{\"initial\":\"S\",\"en\":\"San Marino\",\"cn\":\"圣马力诺\",\"code\":\"378\"},{\"initial\":\"S\",\"en\":\"Sao Tome and Principe\",\"cn\":\"圣多美和普林西比\",\"code\":\"239\"},{\"initial\":\"S\",\"en\":\"Saudi Arabia\",\"cn\":\"沙特阿拉伯\",\"code\":\"966\"},{\"initial\":\"S\",\"en\":\"Senegal\",\"cn\":\"塞内加尔\",\"code\":\"221\"},{\"initial\":\"S\",\"en\":\"Seychelles\",\"cn\":\"塞舌尔\",\"code\":\"248\"},{\"initial\":\"S\",\"en\":\"Sierra Leone\",\"cn\":\"塞拉利昂\",\"code\":\"232\"},{\"initial\":\"S\",\"en\":\"Slovakia\",\"cn\":\"斯洛伐克\",\"code\":\"421\"},{\"initial\":\"S\",\"en\":\"Slovenia\",\"cn\":\"斯洛文尼亚\",\"code\":\"386\"},{\"initial\":\"S\",\"en\":\"Solomon Is\",\"cn\":\"所罗门群岛\",\"code\":\"677\"},{\"initial\":\"S\",\"en\":\"Somali\",\"cn\":\"索马里\",\"code\":\"252\"},{\"initial\":\"S\",\"en\":\"SriLanka\",\"cn\":\"斯里兰卡\",\"code\":\"94\"},{\"initial\":\"S\",\"en\":\"St.Lucia\",\"cn\":\"圣卢西亚\",\"code\":\"1758\"},{\"initial\":\"S\",\"en\":\"St.Vincent\",\"cn\":\"圣文森特\",\"code\":\"1784\"},{\"initial\":\"S\",\"en\":\"Sudan\",\"cn\":\"苏丹\",\"code\":\"249\"},{\"initial\":\"S\",\"en\":\"Suriname\",\"cn\":\"苏里南\",\"code\":\"597\"},{\"initial\":\"S\",\"en\":\"Swaziland\",\"cn\":\"斯威士兰\",\"code\":\"268\"},{\"initial\":\"T\",\"en\":\"Taiwan\",\"cn\":\"台湾省\",\"code\":\"886\"},{\"initial\":\"T\",\"en\":\"Tajikstan\",\"cn\":\"塔吉克斯坦\",\"code\":\"992\"},{\"initial\":\"T\",\"en\":\"Tanzania\",\"cn\":\"坦桑尼亚\",\"code\":\"255\"},{\"initial\":\"T\",\"en\":\"Thailand\",\"cn\":\"泰国\",\"code\":\"66\"},{\"initial\":\"T\",\"en\":\"Tonga\",\"cn\":\"汤加\",\"code\":\"676\"},{\"initial\":\"T\",\"en\":\"Trinidad and Tobago\",\"cn\":\"特立尼达和多巴哥\",\"code\":\"1809\"},{\"initial\":\"T\",\"en\":\"Tunisia\",\"cn\":\"突尼斯\",\"code\":\"216\"},{\"initial\":\"T\",\"en\":\"Turkey\",\"cn\":\"土耳其\",\"code\":\"90\"},{\"initial\":\"T\",\"en\":\"Turkmenistan\",\"cn\":\"土库曼斯坦\",\"code\":\"993\"},{\"initial\":\"W\",\"en\":\"Brunei\",\"cn\":\"文莱\",\"code\":\"673\"},{\"initial\":\"W\",\"en\":\"Guatemala\",\"cn\":\"危地马拉\",\"code\":\"502\"},{\"initial\":\"W\",\"en\":\"Uganda\",\"cn\":\"乌干达\",\"code\":\"256\"},{\"initial\":\"W\",\"en\":\"Ukraine\",\"cn\":\"乌克兰\",\"code\":\"380\"},{\"initial\":\"W\",\"en\":\"Uruguay\",\"cn\":\"乌拉圭\",\"code\":\"598\"},{\"initial\":\"W\",\"en\":\"Uzbekistan\",\"cn\":\"乌兹别克斯坦\",\"code\":\"233\"},{\"initial\":\"W\",\"en\":\"Venezuela\",\"cn\":\"委内瑞拉\",\"code\":\"58\"},{\"initial\":\"X\",\"en\":\"Greece\",\"cn\":\"希腊\",\"code\":\"30\"},{\"initial\":\"X\",\"en\":\"Hongkong\",\"cn\":\"香港\",\"code\":\"852\"},{\"initial\":\"X\",\"en\":\"Hungary\",\"cn\":\"匈牙利\",\"code\":\"36\"},{\"initial\":\"X\",\"en\":\"New Zealand\",\"cn\":\"新西兰\",\"code\":\"64\"},{\"initial\":\"X\",\"en\":\"Samoa Western\",\"cn\":\"西萨摩亚\",\"code\":\"685\"},{\"initial\":\"X\",\"en\":\"Singapore\",\"cn\":\"新加坡\",\"code\":\"65\"},{\"initial\":\"X\",\"en\":\"Spain\",\"cn\":\"西班牙\",\"code\":\"34\"},{\"initial\":\"X\",\"en\":\"Syria\",\"cn\":\"叙利亚\",\"code\":\"963\"},{\"initial\":\"Y\",\"en\":\"Armenia\",\"cn\":\"亚美尼亚\",\"code\":\"374\"},{\"initial\":\"Y\",\"en\":\"India\",\"cn\":\"印度\",\"code\":\"91\"},{\"initial\":\"Y\",\"en\":\"Indonesia\",\"cn\":\"印度尼西亚\",\"code\":\"62\"},{\"initial\":\"Y\",\"en\":\"Iran\",\"cn\":\"伊朗\",\"code\":\"98\"},{\"initial\":\"Y\",\"en\":\"Iraq\",\"cn\":\"伊拉克\",\"code\":\"964\"},{\"initial\":\"Y\",\"en\":\"Israel\",\"cn\":\"以色列\",\"code\":\"972\"},{\"initial\":\"Y\",\"en\":\"Italy\",\"cn\":\"意大利\",\"code\":\"39\"},{\"initial\":\"Y\",\"en\":\"Jamaica\",\"cn\":\"牙买加\",\"code\":\"1876\"},{\"initial\":\"Y\",\"en\":\"Jordan\",\"cn\":\"约旦\",\"code\":\"962\"},{\"initial\":\"Y\",\"en\":\"United Kiongdom\",\"cn\":\"英国\",\"code\":\"44\"},{\"initial\":\"Y\",\"en\":\"Vietnam\",\"cn\":\"越南\",\"code\":\"84\"},{\"initial\":\"Y\",\"en\":\"Yemen\",\"cn\":\"也门\",\"code\":\"967\"},{\"initial\":\"Z\",\"en\":\"Central African Republic\",\"cn\":\"中非共和国\",\"code\":\"236\"},{\"initial\":\"Z\",\"en\":\"Chad\",\"cn\":\"乍得\",\"code\":\"235\"},{\"initial\":\"Z\",\"en\":\"Chile\",\"cn\":\"智利\",\"code\":\"56\"},{\"initial\":\"Z\",\"en\":\"China\",\"cn\":\"中国\",\"code\":\"86\"},{\"initial\":\"Z\",\"en\":\"Gibraltar\",\"cn\":\"直布罗陀\",\"code\":\"350\"},{\"initial\":\"Z\",\"en\":\"Zaire\",\"cn\":\"扎伊尔\",\"code\":\"243\"},{\"initial\":\"Z\",\"en\":\"Zambia\",\"cn\":\"赞比亚\",\"code\":\"260\"}]}}"
            val listType: Type = object : TypeToken<BaseResult<DistrictPhoneBean>>() {}.type
            val data: BaseResult<DistrictPhoneBean> = Gson().fromJson(jsonString, listType)
            handlerDistrictPhone(data.data.phoneAreaCodes)
        }
    }
    
    /**
     * 数据处理
     */
    private fun handlerDistrictPhone(list: MutableList<DistrictPhoneItemBean>?) {
        val map = TreeMap<String, DistrictPhoneBean>()
        list?.forEach continuing@{
            if (TextUtils.isEmpty(it.initial)) return@continuing
            if (TextUtils.isEmpty(it.cn)) return@continuing
            if (TextUtils.isEmpty(it.code)) return@continuing
            val firstChar = it.cn?.trim()?.firstOrNull() ?: return@continuing
            val item = map[it.initial] ?: DistrictPhoneBean()
            with(item) {
                initial = it.initial
                if (phoneAreaCodes == null) {
                    phoneAreaCodes = mutableListOf()
                }
                phoneAreaCodes!!.add(it)
                if (firstNameList == null) {
                    firstNameList = mutableListOf()
                }
                firstNameList!!.add(firstChar.toString())
            }
            map[it.initial!!] = item
        }
        letterKeyList.value = map.keys.toList()
        val sortList = map.values.toMutableList()
        sortList?.forEach { at ->
            at.phoneAreaCodes?.sortBy { it.cn }
            at.firstNameList = at.firstNameList?.distinct()?.toMutableList()
            at.firstNameList?.sorted()
        }
        districtPhoneList.value = sortList
    }
    
    /**
     * 计算需要滚动的位置
     */
    fun onDistrictPhonePosition(letter: String, firstCnName: String) {
        if (TextUtils.isEmpty(letter)) return
        if (TextUtils.isEmpty(firstCnName)) return
        var result: DistrictPhoneIndexBean? = null
        val item = districtPhoneList.value?.find { it.initial == letter }
        item?.let {
            val position = districtPhoneList.value?.indexOf(it) ?: -1
            if (position < 0) return
            it.phoneAreaCodes?.forEachIndexed { childPosition, districtPhoneItemBean ->
                if (districtPhoneItemBean.cn?.firstOrNull()?.toString() == firstCnName) {
                    result = DistrictPhoneIndexBean(position, childPosition)
                    return@forEachIndexed
                }
            }
        } ?: return
        result?.let {
            selectLetterFirstIndexKey.value = it
        }
    }
}
