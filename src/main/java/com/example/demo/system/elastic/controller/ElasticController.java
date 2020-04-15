package com.example.demo.system.elastic.controller;

import com.example.demo.system.elastic.dao.CompanyRepository;
import com.example.demo.system.elastic.entity.CompanyEntity;
import com.example.demo.system.elastic.service.ElasticService;
import com.example.demo.system.entity.Result;
import com.example.demo.system.security.WebFluxSecurityConfig;
import com.example.demo.system.elastic.utils.IKAnalyzerUtil;
import lombok.extern.java.Log;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("elastic")
@Log
@SuppressWarnings("unchecked")
public class ElasticController {

    final static HashMap hashMap = new HashMap<Integer, String>();

    @Resource
    private CompanyRepository companyRepository;
    @Resource
    private ElasticService elasticService;
    @Resource
    private RestHighLevelClient restHighLevelClient;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @PostMapping("add")
    public Result add(@RequestBody CompanyEntity companyEntity) {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            elasticService.save(companyEntity);
        }
        return Result.success("companyRepository.save(companyEntity)");
    }

    @PostMapping("addAll")
    public Result addAll(@RequestBody CompanyEntity companyEntity) throws Exception {
        ArrayList<CompanyEntity> list = new ArrayList<CompanyEntity>();
        for (int i = 1; i < 13; i++) {
            CompanyEntity companyEntity1 = new CompanyEntity();
            companyEntity1.setId(i + "");
            String content = "";
            int i2 = new Random().nextInt(10);
            for (int j = 0; j < i2; j++) {
                content = content + hashMap.get(new Random().nextInt(10));
            }
            companyEntity1.setContentKey(IKAnalyzerUtil.cut(content).toString());
            companyEntity1.setContent(content);
            list.add(companyEntity1);
            if ((i % 10) == 0) {
                log.info("= = " + i);
                companyRepository.saveAll(list);
                list.clear();
            }
        }
        Thread.sleep(5000);
        return Result.success("addAll");
    }

    @PostMapping("del")
    public Result del(@RequestBody CompanyEntity companyEntity) {
        String result = "删除成功";
        try {
            companyRepository.delete(companyEntity);
        } catch (Exception e) {
            result = e.toString();
        }
        return Result.success(result);
    }

    @PostMapping("update")
    public Result update(@RequestBody CompanyEntity companyEntity) {
        return Result.success(companyRepository.save(companyEntity));
    }

    @PostMapping("query")
    public Result query(@RequestBody CompanyEntity companyEntity) {
        return Result.success(companyRepository.queryCompanyById(companyEntity.getId()));
    }

    @PostConstruct
    public void init() {
        WebFluxSecurityConfig.urlSkipList.add("/elastic/**");//设置token检证}

        hashMap.put(0, "据美联社报道，美国国务院朝鲜政策特别代表史蒂芬·比根16日在与韩国官员进行会谈时表示，美国不会接受朝鲜设定的在目前停滞的核谈判中做出让步的最后期限，并敦促朝鲜立即回到谈判桌上。\n" +
                "\n" +"比根称：“关于这一点，让我把话说清楚：对美国来说没有最后期限。我们充分意识到朝鲜在未来几天进行严重挑衅的更大可能。至少可以这样说，这样的行动对实现朝鲜半岛的持久和平将毫无帮助。”\n" +
                "\n" + "比根说： “让我直接与朝鲜的同行们谈谈：现在是我们做好工作的时候了。让我们完成这个。我们就在这儿。而且你们知道如何联系我们。”\n" +
                "\n" + "比根与韩国总统文在寅以及韩国统一部长官金炼铁分别举行会议。比根的办公室表示，比根在访问青瓦台期间表示，特朗普政府不会放弃寻求与朝鲜取得外交进展的意愿，但没有对此进行进一步阐述。目前尚不清楚朝方是否会与美方接触，以解决两国在实现无核化方面日益扩大的分歧。");
        hashMap.put(1, "据了解，12月9日联合国通过了一项重要的决议，那就是俄罗斯军队要从克里米亚地区撤离。该决议是由39个国家提出的，目的是解决克里米亚问题。据了解，联合国现场激烈交锋，63个国家联合“围攻”俄罗斯，集体投赞成票，尽管有19票的反对，但决议还是通过了，反对无效。\n" +
                "\n" + "而此时的俄罗斯总统普京正在参加法国举行的“诺曼底”会议，一同参会的还有乌克兰总统，此消息必然会影响会议，乌克兰将会有更足的底气来进行谈判，而俄罗斯将处于不利的位置。其实早在俄罗斯处理克里米亚问题时就留下了隐患，当时克里米亚共和国还没有加入俄罗斯，俄罗斯就向克里米亚出兵，西方国家就拿这大做文章，批评俄罗斯侵略他国土地。\n" +
                "\n" + "可实际上俄罗斯并没有干涉克里米亚内政，且克里米亚是通过公投的方式加入俄罗斯，完全符合西方国家所说的民主。但是西方国家拒不承认俄罗斯对克里米亚有主权，并以此为借口施加报复。尽管联合国有63票支持俄罗斯从克里米亚撤军，但是其中有大部分是北约国家，这些国家长期以来针对俄罗斯，所以票数多并不能代表正义。");
        hashMap.put(2, "12月9日，四川眉山，90后小伙胡华云被法院以故意杀人罪判处死刑，缓期两年执行，并剥夺政治权利终身，同时限制减刑。\n" +
                "\n" + "此前的2018年12月27日凌晨，胡华云因欠下巨额网贷无力偿还，在被网贷公司多次催款后，采取极端方式，意图杀人后骗取保险赔偿金偿还贷款。而被杀的对象则是其70多岁的叔祖父胡某某。\n" +
                "\n" + "据公安机关侦查，胡华云避开了所有摄像头，从叔祖父住地后门进入屋内，并用一根电源线，将叔祖父勒死在床上，后将现场伪造为“抢劫杀人”现场，将屋内物品翻出，丢弃在野外的水库中。");
        hashMap.put(3, "疑案\n" +
                "七旬老人死在彩票店内床上\n" +
                "店内监控被损坏 外围摄像头被封死\n" +
                "\n" + "70多岁的胡某某，居住在四川眉山洪雅县瓦屋山。他一辈子都未成家，没有子女，一直独来独往生活，平日在瓦屋山以养蜂、采药材为生。\n" +
                "\n" + "2018年12月，一次意外，胡某某不小心摔进火堆，全身多处烧伤。居住在山下的侄儿一家把他接到了山下，并将其安置在侄孙胡华云经营的彩票店内居住。\n" +
                "\n" + "悲剧正是在这个不大的彩票店内发生的。\n" +
                "\n" + "2018年12月27日早上，胡某某侄子——胡华云父亲胡某甲，去看望胡某某时，发现了不对。胡某某躺在床上已没有了动静，身体已经冰冷。\n" +
                "\n" + "胡某甲赶紧报了警。\n" +
                "\n" + "经当地公安刑侦人员侦查和法医鉴定，得出结论，胡某某并非正常死亡，系被人用绳索勒颈，导致机械性窒息死亡。而另一方面，彩票店现场也一片混乱，明显系人为。\n" +
                "\n" + "据当地群众反映，彩票店一般七八月份两个月的顾客较多，因为有很多外地游客去瓦屋山避暑。8月份之后，游人减少，彩票店生意不好，就关门了。据此，警方推定，劫财的可能并不大。而胡某某一直独来独往，基本不与外人接触，更不会跟谁发生矛盾。\n" +
                "\n" + "警方在调查过程中，还调取了案发周围所有的监控，发现彩票店里的监控已被人为损坏，案发时间段，没有发现可疑人的踪影。而且彩票店外面有几个摄像头也被封死了。很显然，凶手到了现场，并避开了所有摄像头。\n" +
                "\n" + "警方确定，这应该是熟人作案。且该作案人对周围环境熟悉，对监控设备本身也较为熟悉。而首先进入视线的，正是胡某某侄儿一家人。\n" +
                "\n" + "残忍");
        hashMap.put(4, "法院：\n" +
                "\n" + "犯故意杀人罪\n" +
                "\n" + "被判死缓且限制减刑\n" +
                "\n" + "12月9日，该案在眉山中级人民法院开庭审理。在整个开庭过程中，胡华云几乎一言不发，也没有正眼看过一眼旁听席上的父母和其他亲人。而在庭审之后，法院工作人员提出其是否有话要给家人讲，法院可以帮助为其录制一段视频带给家人，但胡华云直接拒绝了。庭审中，与家人全程几乎都没有对视。\n" +
                "\n" + "法院查明的事实为：2018年12月27日凌晨，胡华云从洪雅县驾车回家途中，因难以偿还网络贷款，妻子整容欠下钱款，遂想起曾给叔祖父胡某某买过三份人身意外保险，进而产生杀人骗保的想法。回家后，胡华云来到胡某某居住的彩票门市内将其杀害。将人杀害后，胡华云钳断门市内监控网线，伪造了抢劫杀人现场，将门市内彩票、零钱等物品带走，分三处丢弃于瓦屋山水库，两天后，胡华云被动到案，如实供述了自己的罪行。");
        hashMap.put(5, "我们基本每年都要给他钱花\n" +
                "\n" + "庭审现场，胡华云低着头，一言不发。法庭宣判，死缓！旁听席上的家人瞬时泪目。法警将胡某华带离法庭，在家人前方走过，他也没能正视家人。\n" +
                "\n" + "家人始终无法相信胡华云会做出如此凶残的行为，原本28岁的青春年华，却在一瞬的恶念之下，就此黯淡。\n" +
                "\n" + "父亲胡某甲介绍，案发后，其从未想到是儿子所为，因为平常正是儿子在照顾这位老人。其介绍，案发后，儿子被警方带走协助调查，之后便没能再回到家。而关于儿子骗保杀人，也是在警方告知后才知晓的。\n" +
                "\n" + "关于儿子网贷的问题，胡某甲对其具体详情也知道的并不多，不过儿子近年来似乎确实“钱不够花”，“他基本上也没有什么固定工作，就是这里打工那里打工，打工钱不够用，我们还要给他钱，基本上每年都要给他钱花。”\n" +
                "\n" + "更想不通的是胡华云母亲。其介绍，儿子是一个很勤快，很孝顺的人，家里爷爷奶奶的保险，每年都是儿子在买。“现在他出了这样的事，留下一个孙女，还要供他女儿，能供（养）到什么时候就到什么时候吧，没办法。”胡华云母亲说。\n" +
                "\n" + "“心里一直过不去，两个都气，一个是我兄弟，一个是我孙子。”同样经受痛苦的还有爷爷胡某乙，“当时这个事情发生后，都没有想到会是他干的，出了这样的事，心里特别难受。我心痛兄弟的死，但毕竟死了，也心疼孙子。”\n" +
                "\n" + "曾经回访现场的法院办案人员介绍：“家人对他（胡华云）还是比较娇惯，他欠下的钱他父亲也在给他还，还了好几万了。工作上也没有特别的要求，家里的条件也还算过得去，他们在瓦屋山有房子出租给别人开农家乐，总之条件是不算差的。”");
        hashMap.put(6, "东帝汶，这个东字不是音译出的字，而是实实在在的东边的意思。马来群岛南端的岛屿原本叫帝汶。西方殖民者发现这个小岛之后，荷兰人和葡萄牙人就在这个小岛争夺了好几个世纪，想要独霸帝汶岛。\n" +
                "\n" + "1859年，荷兰人和葡萄牙人终于达成了协议，将帝汶一分为二，荷兰人占领了帝汶的西面。连同印度尼西亚一起，西帝汶成了荷兰殖民地。而葡萄牙人则占领东帝汶。\n" +
                "\n" + "二战期间，日本人横扫东南亚，帝汶全岛被日军占领。日本人投降后，印尼取代原来的荷兰人，成为西帝汶的主人，将西帝汶变成了印尼的东努沙登加拉省的一部分。葡萄牙则恢复了对东帝汶的殖民统治。\n" +
                "\n" + "1974年，葡萄牙发生政变，史称“康乃馨革命”。简单的说，葡萄牙除了国内大乱外，还放弃了所有海外殖民地。东帝汶人在完全没有准备的情况，忽然独立，于是很快冒出一堆政党，争当国家的主人。主要包括主张独立的东帝汶独立革命阵线、主张同葡萄牙维持关系的民主联盟以及希望与印尼合并的帝汶人民民主协会。三家谈不拢，内战随即爆发。\n" +
                "\n" + "这一年11月28日，独立革命阵线宣布独立，成立东帝汶民主共和国。然而，东帝汶内战并未结束。与此同时，内战三方没有注意到，边上一直有一只老虎正在虎视眈眈。");
        hashMap.put(7, "需要注意的是，假要拼，但“姿势”得正确。2019年上半年，北京市海淀法院公布的一个案例显示，一位员工以“卧床病休”之名，行出境旅行之实，结果被辞退，丢了工作。\n" +
                "\n" + "那怎么拼假才算“光明正大”呢？\n" +
                "\n" + "当然是请带薪年休假。\n" +
                "\n" + "2008年1月1日实施的《职工带薪年休假条例》指出，职工累计工作已满1年不满10年的，年休假5天；已满10年不满20年的，年休假10天；已满20年的，年休假15天。如此看来，工作未满10年的职工，只需要休2天年假，便能达到“请2休5”的目的。\n" +
                "\n" +"不过也有人因为工作原因，需要在元旦节加班。别沮丧，既然选不了“诗和远方”，那就选择“金钱”——元旦节加班，有三倍工资可以拿。根据《中华人民共和国劳动法》规定，休息日安排劳动者工作又不能安排补休的，支付不低于工资的200%的工资报酬。法定休假日安排劳动者工作的，支付不低于工资300%的工资报酬。\n" +
                "\n" +"还有十几天就到元旦了！\n" +
                "\n" + "现在想请假的还不晚~\n" +
                "\n" + "2019年最后几天\n" +
                "\n" + "来一波说走就走的旅行吧~");
        hashMap.put(8, "每经记者：朱万平 每经编辑：陈俊杰\n" +
                "\n" + "12月11日，甘肃兰州阳光明媚，但笼罩在布鲁氏菌病（以下简称布病）阴影下的中国农业科学院兰州兽医研究所（以下简称兰州兽研所）较往日冷清不少。\n" +
                "\n" + "兰州兽研所，是国内著名的预防兽医学领域院所，近期因为布病事件备受关注。数据显示，截至12月7日12点，兰州兽研所317名师生进行了布鲁氏菌检测，其中96人呈血清学阳性，均为隐性感染，无明显症状。\n" +
                "\n" + "此外，据黑龙江省卫健委10日通报，中国农业科学院研究生院兽医学院（哈兽研）出现布病确诊病例1例、疑似2例、隐性感染10例。而37名该所学生今年8月在兰州兽研所实验室有短期动物接触研究工作，在哈兽研所学习期间无动物接触史。\n" +
                "\n" + "《每日经济新闻》记者11日实地探访兰州兽研所时发现，布病事件爆发后，兰州兽研所在管理上明显加强，但引起近百名师生布鲁氏菌抗体呈阳性的原因，依有待揭开。\n" +
                "\n" + "兰州兽研所管理加强\n" +
                "\n" + "“以前，兰州兽研所门口可以摆摊卖东西。而布病事件发生后，管理明显加强了。”一位附近居民称。\n" +
                "\n" + "兰州兽研所位于兰州市郊的徐家山上，从山下要想进入兰州兽研所，需经过两道门岗，每一道都需要刷卡。第一道门岗到第二道门岗间，有一段100多米长的柏油马路，一路向上，才能到兰州兽研所的正门。");
        hashMap.put(9, "文/陈嘉伟\n" +
                "\n" + "《快递行业投资分析》第二篇\n" +
                "\n" + "在上一篇我们搞清楚了快递行业的整体状况，回顾一下几个特点：\n" +
                "\n" + "1 快递行业目前已经从高速增长期进入了成熟稳定增长期，增长速度从过去50%将逐步降低到20%左右。\n" +
                "\n" + "2 行业的集中度明显开始提升，现在前8名的市场占有率为81%，参考美国快递行业前三占有90%的市场，未来快递行业会加快淘汰第三梯队的企业。\n" +
                "\n" + "3 头部企业的增长主要来自几个方面：1 行业本身的增长%，大概年20%左右，2 蚕食第三梯队的市场，3 ，来自西部，农村等落后地区的增长。");
    }

    @PostMapping("search")
    public Result search(@RequestBody CompanyEntity companyEntity) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("content", companyEntity.getContent());

        PageRequest pageRequest = PageRequest.of(1, 5);
        String[] fields = {"id"};

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();//
        searchSourceBuilder.query(queryBuilder).fetchSource(fields, null);//
        SearchRequest searchRequest = new SearchRequest();//
        searchRequest.source(searchSourceBuilder);

        // ActionFuture<SearchResponse> search = transportClient.search(searchRequest);
        ActionFuture<SearchResponse> search = null;
//        Page<CompanyEntity> page = companyRepository.search(queryBuilder, pageRequest);

//        return Result.success(page.get().collect(Collectors.toList()));
        return Result.success(search.actionGet().getHits());
    }

    @PostMapping("searchKey")
    public Result searchKey(@RequestBody CompanyEntity companyEntity) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("contentKey", companyEntity.getContent());
        PageRequest pageRequest = PageRequest.of(1, 5);
        Page<CompanyEntity> page = companyRepository.search(queryBuilder, pageRequest);
        return Result.success(page.get().collect(Collectors.toList()));
    }

    @PostMapping("getData")
    public Result getData(@RequestBody CompanyEntity companyEntity) throws IOException {
        SearchRequest searchRequest = new SearchRequest("company");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        BoolQueryBuilder queryBuilders = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(companyEntity.getTitle())) {
            QueryBuilder queryBuilder = QueryBuilders.termQuery("title", companyEntity.getTitle());
            queryBuilders.must(queryBuilder);
        }
        if (!StringUtils.isEmpty(companyEntity.getContent())) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("content", companyEntity.getContent());
//            QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("content", companyEntity.getContent());
            queryBuilders.must(queryBuilder);
        }
        if (!StringUtils.isEmpty(companyEntity.getStartEnd()[0]) && !StringUtils.isEmpty(companyEntity.getStartEnd()[1])) {
            RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("createTime").from(sdf.format(companyEntity.getStartEnd()[0])).to(sdf.format(companyEntity.getStartEnd()[1]));
            queryBuilders.must(rangequerybuilder);
        }
        String[] result = {"title", "content", "createTime"};

        sourceBuilder.query(queryBuilders).fetchSource(result, null);
        searchRequest.source(sourceBuilder);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        stopWatch.stop();
        System.out.println(" StopWatch :::: " + stopWatch.getTotalTimeMillis());
        System.out.println(searchResponse);
        return Result.success(Arrays.stream(searchResponse.getHits().getHits()).map(a -> {
            return a.getSourceAsString();
        }).collect(Collectors.toList()));
    }

    @PostMapping("addData")
    public Result addData(@RequestBody CompanyEntity companyEntity) throws IOException {
        IndexRequest indexRequest = new IndexRequest("company");
        indexRequest.id(companyEntity.getId());
        Map<String, Object> source = new HashMap<>();
        source.put("id", companyEntity.getId());
        source.put("title", companyEntity.getTitle());
        source.put("content", companyEntity.getContent());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        source.put("createTime", sdf.format(companyEntity.getCreateTime()));
        indexRequest.source(source);
        return Result.success(restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT).getId());
    }
}
