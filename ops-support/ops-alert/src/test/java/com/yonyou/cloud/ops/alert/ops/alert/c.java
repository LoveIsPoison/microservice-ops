//package com.yonyou.cloud.ops.alert.ops.alert;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.Lock;
// 
//import javax.annotation.Resource;
// 
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.RedisOperations;
//import org.springframework.data.redis.core.SetOperations;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.data.redis.core.ZSetOperations;
//import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
// 
//import com.alibaba.fastjson.JSON;
//import com.transn.common.rediscache.CacheOperator;
//import com.transn.common.redislock.SimpleRedisLockFactoryBean;
//import com.transn.customer.dao.CustomerMapper;
//import com.transn.customer.model.Customer;
//import com.transn.customer.model.Invoice;
//import com.transn.message.model.MessageContent;
//import com.transn.message.model.MessageInfo;
//import com.transn.tr.sys.model.SysLan;
//import com.transn.tr.sys.model.SysPriceRange;
//import com.transn.tr.user.model.TruserInfo;
// 
//@SuppressWarnings("deprecation")
//@RunWith(SpringJUnit4ClassRunner.class) // 整合
//@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
//@TransactionConfiguration(defaultRollback = false)
//@Transactional
//public class RedisTest {
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//     
//    //试试注入这个lockFactory
///*  @Resource
//    private IRedisLockFactoryBean lockFactory;*/
//    @Resource
//    private SimpleRedisLockFactoryBean lockFactory;
//     
//    @Resource(name= "stringRedisTemplate")
//    private ValueOperations<String, String> opsForValue;
//     
//    @Resource(name= "stringRedisTemplate")
//    private HashOperations<String,String,String> opsForHash;
//     
//    @Resource(name= "stringRedisTemplate")
//    private ListOperations<String, String> opsForList;
// 
//    @Resource(name= "stringRedisTemplate")
//    private SetOperations<String,String> opsForSet;
// 
//    @Resource(name= "stringRedisTemplate")
//    private ZSetOperations<String,String> opsForZSet;
// 
//    
//    @Resource
//    private CustomerMapper mapper;
//     
//    @Resource
//    private CacheOperator cache;
//     
//    @Test
//    public void cacheInitTest() throws Exception {
//        cache.initPriceRangeToRedisCashe();
//        cache.initSysLanToRedisCashe();
//        cache.initTrAbilityToRedisCashe();
//    }
//     
//    @Test
//    public void cacheGetTest() throws Exception {
//        SysLan sysLanFromRedisCashe = cache.getSysLanFromRedisCashe("en-GB");
//        System.out.println(JSON.toJSONString(sysLanFromRedisCashe));
//        String count = cache.getTrAbilityFromRedisCashe("zh-CN", "ja");
//        System.out.println(count);
//        Set<SysPriceRange> priceRangeFromRedisCashe = cache.getPriceRangeFromRedisCashe("CNY");
//        System.out.println(JSON.toJSONString(priceRangeFromRedisCashe));
//    }
//     
//    @Test
//    public void test() throws Exception {
///*      ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
//        HashOperations<String, Object, Object> opsForHash = stringRedisTemplate.opsForHash();
//        ListOperations<String, String> opsForList = stringRedisTemplate.opsForList();
//        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();
//        ZSetOperations<String, String> opsForZSet = stringRedisTemplate.opsForZSet();*/
//         
//        Invoice invoice3 = new Invoice();
//        invoice3.setCid("WE60000000CU");
//        invoice3.setAmount(798d);
//        invoice3.setCurrency("USD");
//        invoice3.setTag("烽火集团");
//        invoice3.setType("1");
//        invoice3.setContent("2");
//        invoice3.setAddress("高新四路");
//        String invoice3String = JSON.toJSONString(invoice3);    
//        Boolean putIfAbsent = opsForHash.putIfAbsent("WETRANSN:INVOICE:CID", "WE60000000CU", invoice3String);
//        System.out.println(putIfAbsent);
//        String invoice = opsForHash.get("WETRANSN:INVOICE:CID", "WE60000000CU");
//        Invoice parseObject = JSON.parseObject(invoice, Invoice.class);
//        System.out.println(parseObject.getTag());
//    }
//     
//    @Test
//    public void testOpsForValue() throws Exception {
//        //测试分布式锁和opsForValue
//        Lock lock = lockFactory.createLock("WETRANSN:LOCK:TEST");
//        try {
//            lock.lock();
//            Customer customer = new Customer();
//            customer.setCid("WE20000000CU");
//            customer.setNickname("龙井");
//            customer.setPwd("123456");
//            customer.setPhone("15527411803");
//            customer.setEmail("green.long@transn.com");
//            customer.setCompanyName("传神语联网");
//            customer.setBusiness("翻译软件");
//            customer.setContacts("何恩培");
//            customer.setInformation("027-59738888");
//            customer.setcreateTime(1473734828l);
//            customer.setLoginTime(1474943729l);
//            customer.setSellId("WE16100036SA");
//            customer.setCfrom("CHN");
//            String customerString = JSON.toJSONString(customer);
//            //mapper.insert(customer);
//             
//            opsForValue.set("WETRANSN:CUSTOMER", customerString);
//            //设置"WETRANSN:CUSTOMER:CONTACTS"的值为"何恩培"，30秒后失效
//            opsForValue.set("WETRANSN:CUSTOMER:CONTACTS", "何恩培", 30, TimeUnit.SECONDS);
//            //=======暂时不知道含义=======
//            opsForValue.set("WETRANSN:CUSTOMER:PHONE", "15527411803", 5l);
//            //=======暂时不知道含义=======
//            Boolean setBit1 = opsForValue.setBit("WETRANSN:CUSTOMER:PWD1", 0L, true);
//            System.out.println("setBit1:"+setBit1);
//            //=======暂时不知道含义=======
//            Boolean setBit2 = opsForValue.setBit("WETRANSN:CUSTOMER:PWD2", 0L, false);
//            System.out.println("setBit2:"+setBit2);
//            //=======暂时不知道含义=======
//            Boolean getbit = opsForValue.getBit("WETRANSN:CUSTOMER:PWD2", 0l);
//            System.out.println("getbit:"+getbit);
//             
//            //如果"WETRANSN:CUSTOMER"存在返回false；如果"WETRANSN:CUSTOMER"不存在，就存入reidis返回true
//            Boolean setIfAbsent = opsForValue.setIfAbsent("WETRANSN:CUSTOMER2", customerString);
//            System.out.println("setIfAbsent:"+setIfAbsent);     
//            //获取"WETRANSN:CUSTOMER"的值
//            String get = opsForValue.get("WETRANSN:CUSTOMER");
//            System.out.println("get:"+get);
//            //获取"WETRANSN:CUSTOMER:CID"的值串的[2,5]
//            String get2 = opsForValue.get("WETRANSN:CUSTOMER:CID", 2l, 5l);
//            System.out.println("get2:"+get2);
//            //如果有“WE20000000CU”就返回“WE20000000CU”，并且赋值为“15527411803”
//            String getAndSet1 = opsForValue.getAndSet("WETRANSN:CUSTOMER:CID", "15527411803");
//            System.out.println("getAndSet1:"+getAndSet1);
//            //如果没有“张三”就返回null，并且赋值为“张三”
//            String getAndSet2 = opsForValue.getAndSet("WETRANSN:CUSTOMER:NICKNAME", "张三");
//            System.out.println("getAndSet2:"+getAndSet2);
//            //Long类型的数值累加
//            Long increment1 = opsForValue.increment("WETRANSN:CUSTOMER:CREATETIME", 1473734828l);
//            System.out.println("increment1:"+increment1);
//            //Double类型的数值累加
//            Double increment2 = opsForValue.increment("WETRANSN:CUSTOMER:MONEY", 5600d);
//            System.out.println("increment2:"+increment2);
//            //字符串连接，返回增加到第几个字节
//            Integer append1 = opsForValue.append("WETRANSN:CUSTOMER:COMPANYNAME", "chuanshen");
//            System.out.println("append1:"+append1);
//            Integer append2 = opsForValue.append("WETRANSN:CUSTOMER:COMPANYNAME", "yulianwang");
//            System.out.println("append2:"+append2);
//             
//        } finally {
//            lock.unlock();
//        }
//    }
//     
//    @Test
//    public void testOpsForHash() throws Exception {
//        //hashMap放入键值对
//        Invoice invoice1 = new Invoice();
//        invoice1.setCid("WE30000000CU");
//        invoice1.setAmount(488d);
//        invoice1.setCurrency("CHN");
//        invoice1.setTag("语联网（武汉）信息技术有限公司");
//        invoice1.setType("2");
//        invoice1.setContent("1");
//        invoice1.setAddress("光谷E城，E2栋5楼");
//        String invoice1String = JSON.toJSONString(invoice1);
//        Invoice invoice2 = new Invoice();
//        invoice2.setCid("WE40000000CU");
//        invoice2.setAmount(599d);
//        invoice2.setCurrency("SGP");
//        invoice2.setTag("天津蓝晶光电技术股份有限公司");
//        invoice2.setType("1");
//        invoice2.setContent("2");
//        invoice2.setAddress("光谷大道");
//        String invoice2String = JSON.toJSONString(invoice2);
//        Invoice invoice3 = new Invoice();
//        invoice3.setCid("WE50000000CU");
//        invoice3.setAmount(788d);
//        invoice3.setCurrency("USD");
//        invoice3.setTag("湖北东风汽车有限公司");
//        invoice3.setType("1");
//        invoice3.setContent("2");
//        invoice3.setAddress("神龙大道");
//        String invoice3String = JSON.toJSONString(invoice2);    
//        opsForHash.put("WETRANSN:INVOICE:CID", "WE30000000CU", invoice1String);
//        opsForHash.put("WETRANSN:INVOICE:CID", "WE40000000CU", invoice2String);
//        //如果不存在则返回true，并且存入redis；如果存在则返回false
//        Boolean putIfAbsent = opsForHash.putIfAbsent("WETRANSN:INVOICE:TAG", "WE30000000CU", "语联网（武汉）信息技术有限公司");
//        System.out.println(putIfAbsent);
//        Boolean putIfAbsent2 = opsForHash.putIfAbsent("WETRANSN:INVOICE:CID", "WE50000000CU", invoice3String);
//        System.out.println(putIfAbsent2);
//        //将map的键值对依次放入"WETRANSN:INVOICE:INVOICE"
//        Map<String, String> allMap = new HashMap<String, String>();
//        allMap.put("cid", "WE50000000CU");
//        allMap.put("amount", "699");
//        allMap.put("currency", "USD");
//        allMap.put("tag", "华为科技");
//        allMap.put("type", "1");
//        allMap.put("content", "1");
//        allMap.put("address", "未来城");   
//        opsForHash.putAll("WETRANSN:INVOICE:INVOICE", allMap);
//        //根据redis标识和主键取值
//        String get = opsForHash.get("WETRANSN:INVOICE:INVOICE", "address");
//        System.out.println(get);
//        //判断"WETRANSN:INVOICE:CID"中是否有存在的key
//        Boolean hasKey = opsForHash.hasKey("WETRANSN:INVOICE:CID", "WE90000000CU");
//        System.out.println(hasKey);
//        //删除"WETRANSN:INVOICE:INVOICE"中Key为"content"的记录
//        opsForHash.delete("WETRANSN:INVOICE:INVOICE", "content");
//        //根据Collection类型的多个key，获取一个List集合的值
//        List<String> cid = new ArrayList<String>(); 
//        cid.add("WE30000000CU");
//        cid.add("WE40000000CU");
//        List<String> multiGet = opsForHash.multiGet("WETRANSN:INVOICE:CID", cid);
//        System.out.println(multiGet);
//        Set<String> cid2 = new HashSet<String>();
//        cid2.add("WE30000000CU");
//        cid2.add("WE40000000CU");
//        List<String> multiGet2 = opsForHash.multiGet("WETRANSN:INVOICE:CID", cid2);
//        System.out.println(multiGet2);
//        //获取"WETRANSN:INVOICE:CID"下所有的key
//        Set<String> keys = opsForHash.keys("WETRANSN:INVOICE:CID");
//        System.out.println(keys);
//        //获取"WETRANSN:INVOICE:CID"下所有的value
//        List<String> values = opsForHash.values("WETRANSN:INVOICE:CID");
//        System.out.println(values);
//        //获取"WETRANSN:INVOICE:CID"的元素长度
//        Long size = opsForHash.size("WETRANSN:INVOICE:CID");
//        System.out.println(size);
//        //获取形如：{WE30000000CU={"address":"光谷E城，E2栋5楼","amount":488,"cid":"WE30000000CU","content":"1","currency":"CHN","tag":"语联网（武汉）信息技术有限公司","type":"2"}, WE50000000CU={"address":"光谷大道","amount":599,"cid":"WE40000000CU","content":"2","currency":"SGP","tag":"天津蓝晶光电技术股份有限公司","type":"1"}, WE40000000CU={"address":"光谷大道","amount":599,"cid":"WE40000000CU","content":"2","currency":"SGP","tag":"天津蓝晶光电技术股份有限公司","type":"1"}}
//        Map<String, String> entries = opsForHash.entries("WETRANSN:INVOICE:CID");
//        System.out.println(entries);
//        //累计long和double类型的数字
//        Double increment = opsForHash.increment("WETRANSN:INVOICE:AMOUNT", "WE30000000CU", 699d);
//        System.out.println(increment);
//        Long increment2 = opsForHash.increment("WETRANSN:INVOICE:AMOUNT", "WE40000000CU", 788l);
//        System.out.println(increment2);
//        //org.springframework.data.redis.core.StringRedisTemplate@53dad875
//        RedisOperations<String, ?> operations = opsForHash.getOperations();
//        System.out.println(operations);
//         
//    }
//     
//     
//    @Test
//    public void testOpsForList() throws Exception {
//        TruserInfo user = new TruserInfo();
//        user.setTr_id("WE16000159TR");
//        user.setNickname("祝思");
//        user.setPwd("e10adc3949ba59abbe56e057f20f883e");
//        user.setPhone("18900000000");
//        user.setEmail("juice.zhu@transn.com");
//        user.setCreate_time(1471105486l);
//        user.setCfrom("CHN");
//        user.setCurrency("CNY");
//        String userString = JSON.toJSONString(user);    
//        TruserInfo user2 = new TruserInfo();
//        user2.setTr_id("WE16000160TR");
//        user2.setNickname("熊晶");
//        user2.setPwd("e10adc3949ba59abbe56e057f20f883e");
//        user2.setPhone("19000000000");
//        user2.setEmail("webb.xiong@transn.com");
//        user2.setCreate_time(1471105486l);
//        user2.setCfrom("CHN");
//        user2.setCurrency("CNY");
//        String user2String = JSON.toJSONString(user2);  
//        //放入"WETRANSN:TRUSER:TRID"集合，返回行数（不排重）
//        Long leftPush = opsForList.leftPush("WETRANSN:TRUSER:TRID", userString);
//        Long leftPush2 = opsForList.leftPush("WETRANSN:TRUSER:TRID", user2String);
//        System.out.println(leftPush);
//        System.out.println(leftPush2);
//        //将N个值全部放入"WETRANSN:TRUSER:NICKNAME"，返回总个数（不排重）
//        Long leftPushAll = opsForList.leftPushAll("WETRANSN:TRUSER:NICKNAME", "祝思","熊晶","董畅");
//        System.out.println(leftPushAll);
//        List<String> phone = new ArrayList<String>(); 
//        phone.add("18900000000");
//        phone.add("19000000000");
//        phone.add("19100000000");
//        phone.add("19200000000");
//        //将集合全部放入"WETRANSN:TRUSER:PHONE"，返回总个数（不排重）
//        Long leftPushAll2 = opsForList.leftPushAll("WETRANSN:TRUSER:PHONE", phone);
//        System.out.println(leftPushAll2);
//        //如果"WETRANSN:TRUSER:NICKNAME2"存在，则放入"WETRANSN:TRUSER:NICKNAME2"中，返回行数
//        //如果"WETRANSN:TRUSER:NICKNAME2"不存在，则不操作，返回0
//        Long leftPushIfPresent = opsForList.leftPushIfPresent("WETRANSN:TRUSER:NICKNAME2", "蒋汉华");
//        System.out.println(leftPushIfPresent);
//        //从左边开始删除值，返回删除的值
//        String leftPop = opsForList.leftPop("WETRANSN:TRUSER:PHONE");
//        System.out.println(leftPop);
//        //删除超时弄不清楚
//        String leftPop2 = opsForList.leftPop("WETRANSN:TRUSER:PHONE", 1, TimeUnit.NANOSECONDS);
//        System.out.println(leftPop2);
//        //取从左往右[0,1]的所有值的集合
//        List<String> range = opsForList.range("WETRANSN:TRUSER:NICKNAME", 0, 1);
//        System.out.println(range);
//        //获取"WETRANSN:TRUSER:NICKNAME"的个数
//        Long size = opsForList.size("WETRANSN:TRUSER:NICKNAME");
//        System.out.println(size);
//        //删除除了[0,1]以外的所有元素
//        opsForList.trim("WETRANSN:TRUSER:NICKNAME", 0, 1);
//        //更新已有的元素（从0开始），如果没有则会出现越界异常，如果没有这个元素的key，也会出现找不到key的异常
//        opsForList.set("WETRANSN:TRUSER:NICKNAME", 0, "龙津");
//        opsForList.set("WETRANSN:TRUSER:NICKNAME2", 1, "明正");
//        //删除N个元素值为“XXXX”，返回删除的个数；如果没有这个元素则返回0
//        Long remove = opsForList.remove("WETRANSN:TRUSER:NICKNAME", 3, "龙津");
//        System.out.println(remove);
//        //列出index行的值，从0开始
//        String index = opsForList.index("WETRANSN:TRUSER:PHONE", 0);
//        System.out.println(index);
//        //将源key的队列的右边的一个值删除，然后塞入目标key的队列的左边，返回这个值
//        String rightPopAndLeftPush = opsForList.rightPopAndLeftPush("WETRANSN:TRUSER:PHONE", "WETRANSN:TRUSER:NICKNAME");
//        System.out.println(rightPopAndLeftPush);
//    }
//     
//    @Test
//    public void testOpsForSet() throws Exception {
//        SysLan sysLan = new SysLan();
//        sysLan.setCnname("中文");
//        sysLan.setCode("zh-CN");
//        sysLan.setEnname("Simplified Chinese");
//        sysLan.setId("100000");
//        String sysLanString = JSON.toJSONString(sysLan);
//        SysLan sysLan1 = new SysLan();
//        sysLan1.setCnname("英语");
//        sysLan1.setCode("en");
//        sysLan1.setEnname("English");
//        sysLan1.setId("100001");
//        String sysLan1String = JSON.toJSONString(sysLan1);
//        SysLan sysLan2 = new SysLan();
//        sysLan2.setCnname("法语");
//        sysLan2.setCode("fr");
//        sysLan2.setEnname("French");
//        sysLan2.setId("100002");
//        String sysLan2String = JSON.toJSONString(sysLan2);
//        SysLan sysLan3 = new SysLan();
//        sysLan3.setCnname("德语");
//        sysLan3.setCode("de");
//        sysLan3.setEnname("German");
//        sysLan3.setId("100003");
//        String sysLan3String = JSON.toJSONString(sysLan3);
//        //如果不存在这个字符串，将字符串存入set集合，返回存入元素的个数；如果存在这个字符串就不操作，返回0；
//        Long add = opsForSet.add("WETRANSN:SYSLAN2", sysLan3String);
//        Long addmore = opsForSet.add("WETRANSN:SYSLAN", sysLanString, sysLan1String, sysLan2String,sysLan3String);
//        System.out.println(add);
//        System.out.println(addmore);
//        //列出key为"WETRANSN:SYSLAN"的所有set集合
//        Set<String> members = opsForSet.members("WETRANSN:SYSLAN");
//        System.out.println(members);
//        //随机取key为"WETRANSN:SYSLAN"的一个set元素
//        String randomMember = opsForSet.randomMember("WETRANSN:SYSLAN");
//        System.out.println(randomMember);
//        //随机取N次key为"WETRANSN:SYSLAN"，组成一个list集合，可以重复取出
//        List<String> randomMembers = opsForSet.randomMembers("WETRANSN:SYSLAN", 3);
//        System.out.println(randomMembers);
//        //随机取N次key为"WETRANSN:SYSLAN"，组成一个set集合，不可以重复取出
//        Set<String> distinctRandomMembers = opsForSet.distinctRandomMembers("WETRANSN:SYSLAN", 2);
//        System.out.println(distinctRandomMembers);
//        //sysLan1String字符串是否是key为"WETRANSN:SYSLAN"set集合的元素
//        Boolean member = opsForSet.isMember("WETRANSN:SYSLAN", sysLan1String);
//        System.out.println(member);
//        //将字符串sysLanString从key为"WETRANSN:SYSLAN"的集合，移动到key为"WETRANSN:SYSLAN2"的集合,返回是否移动成功
////      Boolean move = opsForSet.move("WETRANSN:SYSLAN", sysLanString, "WETRANSN:SYSLAN2");
////      System.out.println(move);
//        //删除key为"WETRANSN:SYSLAN"的元素sysLan1String、sysLan2String的字符串，返回删除的个数
////      Long remove = opsForSet.remove("WETRANSN:SYSLAN", sysLan1String, sysLan2String);
////      System.out.println(remove);
//        //从左侧依次删除元素
////      String pop = opsForSet.pop("WETRANSN:SYSLAN");
////      System.out.println(pop);
//        //返回集合长度
//        Long size = opsForSet.size("WETRANSN:SYSLAN");
//        System.out.println(size);
//        //difference(K key, K otherKey);
//        //比较key与otherKey的set集合，返回与otherKey集合不一样的set集合
//        Set<String> difference = opsForSet.difference("WETRANSN:SYSLAN","WETRANSN:SYSLAN2");
//        System.out.println(difference);
//        Set<String> difference2 = opsForSet.difference("WETRANSN:SYSLAN2","WETRANSN:SYSLAN");
//        System.out.println(difference2);
//        //differenceAndStore(K key, K otherKey, K destKey);
//        //比较key与otherKey的set集合，取出与otherKey的set集合不一样的set集合，并存入destKey的set集合中，返回存入个数
//        Long differenceAndStore = opsForSet.differenceAndStore("WETRANSN:SYSLAN","WETRANSN:SYSLAN2", "WETRANSN:SYSLAN3");
//        System.out.println(differenceAndStore);
//        //intersect(K key, K otherKey);
//        //比较key与otherKey的set集合，取出二者交集，返回set交集合
//        Set<String> intersect = opsForSet.intersect("WETRANSN:SYSLAN","WETRANSN:SYSLAN2");
//        System.out.println(intersect);
//        Set<String> intersect2 = opsForSet.intersect("WETRANSN:SYSLAN2","WETRANSN:SYSLAN");
//        System.out.println(intersect2);
//        //intersectAndStore(K key, K otherKey, K destKey);
//        //比较key与otherKey的set集合，取出二者交集，并存入destKey集合，返回null
//        Long intersectAndStore = opsForSet.intersectAndStore("WETRANSN:SYSLAN","WETRANSN:SYSLAN3", "WETRANSN:SYSLAN4");
//        System.out.println(intersectAndStore);
//        //union(K key, K otherKey)
//        //比较key与otherKey的set集合，取出二者并集，返回set并集合
//        Set<String> union = opsForSet.union("WETRANSN:SYSLAN","WETRANSN:SYSLAN2");
//        System.out.println(union);
//        //unionAndStore(K key, K otherKey, K destKey)
//        //比较key与otherKey的set集合，取出二者并集，并存入destKey集合，返回destKey集合个数
//        Long unionAndStore = opsForSet.unionAndStore("WETRANSN:SYSLAN", "WETRANSN:SYSLAN2", "WETRANSN:SYSLAN5");
//        System.out.println(unionAndStore);
//    }
//     
//    @Test
//    public void testOpsForZSet() throws Exception {
//        MessageContent message1 = new MessageContent();
//        message1.setMsgId(1);
//        message1.setMsgContent("内容1");
//        message1.setReceiverType("TR");
//        message1.setCfrom("CHN");
//        String message1String = JSON.toJSONString(message1);
//        MessageContent message2 = new MessageContent();
//        message2.setMsgId(2);
//        message2.setMsgContent("内容2");
//        message2.setReceiverType("TQ");
//        message2.setCfrom("USD");
//        String message2String = JSON.toJSONString(message2);
//        MessageContent message3 = new MessageContent();
//        message3.setMsgId(3);
//        message3.setMsgContent("内容3");
//        message3.setReceiverType("ZJ");
//        message3.setCfrom("SGP");
//        String message3String = JSON.toJSONString(message3);
//        MessageContent message4 = new MessageContent();
//        message4.setMsgId(4);
//        message4.setMsgContent("内容4");
//        message4.setReceiverType("RT");
//        message4.setCfrom("CVB");
//        String message4String = JSON.toJSONString(message4);
////      Set<String> messageSet = new HashSet<String>();
////      messageSet.add(message1String);
//        //存入字符串和排序号到zset
//        Boolean add = opsForZSet.add("WETRANSN:MESSAGE2", message1String, 1);
//        Boolean add2 = opsForZSet.add("WETRANSN:MESSAGE2", message2String, 3);
//        Boolean add3 = opsForZSet.add("WETRANSN:MESSAGE2", message3String, 5);
//        Boolean add4 = opsForZSet.add("WETRANSN:MESSAGE2", message4String, 9);
//        System.out.println(add);
//        //取key为"WETRANSN:MESSAGE1"集合，从0到1，返回取出来的集合；0到-1是取全部；
//        Set<String> range = opsForZSet.range("WETRANSN:MESSAGE1",0,1);
//        System.out.println(range);
//        //删除一个或多个值，返回删除个数
////      Long remove = opsForZSet.remove("WETRANSN:MESSAGE1", message3String);
////      System.out.println(remove);
//        //删除[M,N]行号的值,返回删除个数
////      Long removeRange = opsForZSet.removeRange("WETRANSN:MESSAGE2", 0, 1);
////      System.out.println("removeRange:"+removeRange);
//        //删除[M,N]序列号的值,返回删除个数
////      Long removeRangeByScore = opsForZSet.removeRangeByScore("WETRANSN:MESSAGE2", 0, 4);
////      System.out.println("removeRangeByScore:"+removeRangeByScore);
//        //取行号，从0开始
//        Long rank = opsForZSet.rank("WETRANSN:MESSAGE1", message3String);
//        System.out.println(rank);
//        //取反转行号，与rank相反
//        Long reverseRank = opsForZSet.reverseRank("WETRANSN:MESSAGE1", message3String);
//        System.out.println(reverseRank);
//        //取double类型的序列号
//        Double score = opsForZSet.score("WETRANSN:MESSAGE1", message3String);
//        System.out.println(score);
//        //列出序列号从[N,M]的元素个数
//        Long count = opsForZSet.count("WETRANSN:MESSAGE1", 0, 3);
//        System.out.println(count);
//        //返回集合的长度
//        Long size = opsForZSet.size("WETRANSN:MESSAGE2");
//        System.out.println(size);
//        //返回集合的长度
//        Long zCard = opsForZSet.zCard("WETRANSN:MESSAGE2");
//        System.out.println(zCard);
//     
//     
//     
//     
//    }
//     
//}