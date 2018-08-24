package com.yonyou.microservice.gate.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.HistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.max.InternalMax;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.InternalMin;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.microservice.gate.server.vo.UrlRequestStatisticVO;

@Component
public class ElasticsearchUtil {

private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchUtil.class);
private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

    @Autowired
    private TransportClient transportClient;

    private static TransportClient client;

    /**
     * @PostContruct是spring框架的注解
     * spring容器初始化的时候执行该方法
     */
    @PostConstruct
    public void init() {
       client = this.transportClient; 
    }

    /**
     * 创建索引
     *
     * @param index
     * @return
     */
    public static boolean createIndex(String index) {
       if (!isIndexExist(index)) {
    	   LOGGER.info("Index is not exits!");
        }
        CreateIndexResponse indexresponse = client.admin().indices().prepareCreate(index).execute().actionGet();
        LOGGER.info("执行建立成功？" + indexresponse.isAcknowledged());
        return indexresponse.isAcknowledged();
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
   public static boolean deleteIndex(String index) {
       if (!isIndexExist(index)) {
    	   LOGGER.info("Index is not exits!");
        }
        DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index).execute().actionGet();
        if (dResponse.isAcknowledged()) {
        	LOGGER.info("delete index " + index + "  successfully!");
        } else {
        	LOGGER.info("Fail to delete index " + index);
        }
        return dResponse.isAcknowledged();
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    public static boolean isIndexExist(String index) {
        IndicesExistsResponse inExistsResponse = client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
        if (inExistsResponse.isExists()) {
        	LOGGER.info("Index [" + index + "] is exist!");
        } else {
        	LOGGER.info("Index [" + index + "] is not exist!");
        }
        return inExistsResponse.isExists();
    }

    /**
     * 数据添加，正定ID
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     * @return
     */
    public static String addData(JSONObject jsonObject, String index, String type, String id) {

        IndexResponse response = client.prepareIndex(index, type, id).setSource(jsonObject).get();

        LOGGER.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());

        return response.getId();
    }


    /**
     * 数据添加
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @return
     */
    public static String addData(JSONObject jsonObject, String index, String type) {
    	return addData(jsonObject, index, type, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    }

    /**
     * 通过ID删除数据
     *
     * @param index 索引，类似数据库
     * @param type  类型，类似表
     * @param id    数据ID
     */
    public static void deleteDataById(String index, String type, String id) {

        DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();

        LOGGER.info("deleteDataById response status:{},id:{}", response.status().getStatus(), response.getId());
    }

    /**
     * 通过ID 更新数据
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     * @return
     */
    public static void updateDataById(JSONObject jsonObject, String index, String type, String id) {

        UpdateRequest updateRequest = new UpdateRequest();

        updateRequest.index(index).type(type).id(id).doc(jsonObject);

        client.update(updateRequest);

    }

    /**
     * 通过ID获取数据
     *
     * @param index  索引，类似数据库
     * @param type   类型，类似表
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return
     */
    public static Map<String, Object> searchDataById(String index, String type, String id, String fields) {

        GetRequestBuilder getRequestBuilder = client.prepareGet(index, type, id);

        if (StringUtils.isNotEmpty(fields)) {
            getRequestBuilder.setFetchSource(fields.split(","), null);
        }

        GetResponse getResponse =  getRequestBuilder.execute().actionGet();

        return getResponse.getSource();
    }


    /**
     * 使用分词查询,并分页
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param startPage    当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return
     */
    public static EsPage searchDataPage(String index, String type, int startPage, int pageSize, QueryBuilder query, String fields, String sortField, String highlightField) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(","));
        }
        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);

        // 需要显示的字段，逗号分隔（缺省为全部字段）
        if (StringUtils.isNotEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(","), null);
        }

        //排序字段
        if (StringUtils.isNotEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }

        // 高亮（xxx=111,aaa=222）
        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();

            //highlightBuilder.preTags("<span style='color:red' >");//设置前缀
            //highlightBuilder.postTags("</span>");//设置后缀

            // 设置高亮字段
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }

        //searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
        searchRequestBuilder.setQuery(query);

        // 分页应用
        searchRequestBuilder.setFrom(startPage).setSize(pageSize);

        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);

        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        LOGGER.info("\n{}", searchRequestBuilder);

        // 执行搜索,返回搜索响应信息
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        long totalHits = searchResponse.getHits().totalHits;
        long length = searchResponse.getHits().getHits().length;

        LOGGER.debug("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);

        if (searchResponse.status().getStatus() == 200) {
            // 解析对象
            List<Map<String, Object>> sourceList = setSearchResponse(searchResponse, highlightField);

            return new EsPage(startPage, pageSize, (int) totalHits, sourceList);
        }
        return null;
    }


    /**
     * 使用分词查询
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param query          查询条件
     * @param size           文档大小限制
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return
     */
    public static List<Map<String, Object>> searchListData(String index, String type, QueryBuilder query, Integer size, String fields, String sortField, String highlightField) {

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(","));
        }

        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            // 设置高亮字段
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }

        searchRequestBuilder.setQuery(query);

        if (StringUtils.isNotEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(","), null);
        }
        searchRequestBuilder.setFetchSource(true);

        if (StringUtils.isNotEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }

        if (size != null && size > 0) {
            searchRequestBuilder.setSize(size);
        }

        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        LOGGER.info("\n{}", searchRequestBuilder);

        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        long totalHits = searchResponse.getHits().totalHits;
        long length = searchResponse.getHits().getHits().length;

        LOGGER.info("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);

        if (searchResponse.status().getStatus() == 200) {
            // 解析对象
            return setSearchResponse(searchResponse, highlightField);
        }
        return null;
    }



    /**
     * 高亮结果集 特殊处理
     *
     * @param searchResponse
     * @param highlightField
     */
    private static List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
        List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();
        StringBuffer stringBuffer = new StringBuffer();

        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            searchHit.getSourceAsMap().put("id", searchHit.getId());

            if (StringUtils.isNotEmpty(highlightField)) {

                System.out.println("遍历 高亮结果集，覆盖 正常结果集" + searchHit.getSourceAsMap());
                Text[] text = searchHit.getHighlightFields().get(highlightField).getFragments();

                if (text != null) {
                	for (Text str : text) {
                        stringBuffer.append(str.string());
                    }
                    //遍历 高亮结果集，覆盖 正常结果集
                    searchHit.getSourceAsMap().put(highlightField, stringBuffer.toString());
                }
            }
            sourceList.add(searchHit.getSourceAsMap());
        }
        return sourceList;
    }
    public void test(){

    	 //构建查询请求体
    	        SearchRequestBuilder search = client.prepareSearch("gaterequest").setTypes("external");
    	 
    	        //分组字段是id，排序由多个字段排序组成
//    	        TermsAggregationBuilder tb= AggregationBuilders.terms("id").field("id").order(Terms.Order.compound(
//    	                Terms.Order.aggregation("sum_count",false)//先按count，降序排
//    	                ,
//    	                Terms.Order.aggregation("sum_code",true)//如果count相等情况下，使用code的和排序
//    	        ));
    	        TermsAggregationBuilder tb= AggregationBuilders.terms("id").field("id");
    	 
    	        //求和字段1
    	        SumAggregationBuilder sb= AggregationBuilders.sum("sum_count").field("count");
    	        //求和字段2
    	        SumAggregationBuilder sb_code= AggregationBuilders.sum("sum_code").field("code");
    	 
    	        tb.subAggregation(sb);//添加到分组聚合请求中
    	        tb.subAggregation(sb_code);//添加到分组聚合请求中
    	 
    	        //将分组聚合请求插入到主请求体重
    	        search.addAggregation(tb);
    	        //发送查询，获取聚合结果
    	       Terms tms=  search.get().getAggregations().get("id");
    	        //遍历每一个分组的key
    	        for(Terms.Bucket tbb:tms.getBuckets()){
    	            //获取count的和
    	            Sum sum= tbb.getAggregations().get("sum_count");
    	            //获取code的和
    	            Sum sum2=tbb.getAggregations().get("sum_code");
    	            System.out.println(tbb.getKey()+"  " + tbb.getDocCount() +"  "+sum.getValue()+"  "+sum2.getValue());
    	        }
    	        //释放资源
    	        client.close();
    }
    
    public List<UrlRequestStatisticVO> group(String timeField,String startTime,String stopTime,int size){
    	List<UrlRequestStatisticVO> result=new ArrayList();
    	RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("time").from(startTime).to(stopTime);
//    	FieldSortBuilder sortBuilder=new FieldSortBuilder(timeField);
    	SearchRequestBuilder requestBuilder = client.prepareSearch("gaterequest");
        MinAggregationBuilder min = AggregationBuilders.min("min").field("usedTime");
        MaxAggregationBuilder max = AggregationBuilders.max("max").field("usedTime");
        AvgAggregationBuilder avg = AggregationBuilders.avg("avg").field("usedTime");
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("agg").field(timeField)
        		.subAggregation(AggregationBuilders.terms("add").field("uri")
        		.subAggregation(min).subAggregation(max).subAggregation(avg)).size(size);
        SearchResponse response = requestBuilder.addAggregation(aggregationBuilder).setQuery(rangequerybuilder) //.addSort(sortBuilder)
                .setExplain(true).execute().actionGet();
        Terms agg = response.getAggregations().get("agg");
        for (Terms.Bucket bucket : agg.getBuckets()) {
        	LOGGER.info(bucket.getKey() + ":" + bucket.getDocCount());
            Terms tms = bucket.getAggregations().get("add");
            if(tms!=null){
                for (Terms.Bucket b1 : tms.getBuckets()) {
             	   InternalMin minv=b1.getAggregations().get("min");
             	   InternalMax maxv=b1.getAggregations().get("max");
             	   InternalAvg avgv=b1.getAggregations().get("avg");
             	    LOGGER.info(b1.getKey() + ":" + b1.getDocCount()+",min="+minv.getValueAsString()+
                 		   ",max="+maxv.getValueAsString()+",avg="+avgv.getValueAsString());
                    UrlRequestStatisticVO v=new UrlRequestStatisticVO();
                    v.setTime((String)bucket.getKey());
                    v.setUri((String)b1.getKey());
                    v.setCount(b1.getDocCount());
                    v.setMax(maxv.getValue());
                    v.setMin(minv.getValue());
                    v.setAvg(avgv.getValue());
                    result.add(v);
                }
            }
        }
        return result;
    }
    
    public List<UrlRequestStatisticVO> groupByTime(String timeField,String startTime,String stopTime,int size){
    	List<UrlRequestStatisticVO> result=new ArrayList();
    	RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("time").from(startTime).to(stopTime);

    	SearchRequestBuilder requestBuilder = client.prepareSearch("gaterequest");
        MinAggregationBuilder min = AggregationBuilders.min("min").field("usedTime");
        MaxAggregationBuilder max = AggregationBuilders.max("max").field("usedTime");
        AvgAggregationBuilder avg = AggregationBuilders.avg("avg").field("usedTime");
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("agg").field(timeField)
        		.subAggregation(min).subAggregation(max).subAggregation(avg).size(size);
        SearchResponse response = requestBuilder.addAggregation(aggregationBuilder).setQuery(rangequerybuilder)
                .setExplain(true).execute().actionGet();
        Terms agg = response.getAggregations().get("agg");
        for (Terms.Bucket bucket : agg.getBuckets()) {
           LOGGER.info(bucket.getKey() + ":" + bucket.getDocCount());
      	   InternalMin minv=bucket.getAggregations().get("min");
      	   InternalMax maxv=bucket.getAggregations().get("max");
      	   InternalAvg avgv=bucket.getAggregations().get("avg");
      	    LOGGER.info(bucket.getKey() + ":" + bucket.getDocCount()+",min="+minv.getValueAsString()+
          		   ",max="+maxv.getValueAsString()+",avg="+avgv.getValueAsString());
             UrlRequestStatisticVO v=new UrlRequestStatisticVO();
             v.setTime((String)bucket.getKey());
             v.setCount(bucket.getDocCount());
             v.setMax(maxv.getValue());
             v.setMin(minv.getValue());
             v.setAvg(avgv.getValue());
             result.add(v);
        }
        return result;
    }
    
    public List<UrlRequestStatisticVO> groupSec(String startTime,String stopTime,int size){
    	return group("time",startTime,stopTime,size);
    }
    
    public List<UrlRequestStatisticVO> groupMinu(String startTime,String stopTime,int size){
    	return group("timem",startTime,stopTime,size);

    }
    
    public List<UrlRequestStatisticVO> groupHour(String startTime,String stopTime,int size){
    	return group("timeh",startTime,stopTime,size);
    }
    
    public List<UrlRequestStatisticVO> groupDay(String startTime,String stopTime,int size){
    	return group("timed",startTime,stopTime,size);
    }
    
    public List<UrlRequestStatisticVO> trafficSec(String startTime,String stopTime,int size){
    	return groupByTime("time",startTime,stopTime,size);
    }
    
    public List<UrlRequestStatisticVO> trafficMinu(String startTime,String stopTime,int size){
    	return groupByTime("timem",startTime,stopTime,size);

    }
    
    public List<UrlRequestStatisticVO> trafficHour(String startTime,String stopTime,int size){
    	return groupByTime("timeh",startTime,stopTime,size);
    }
    
    public List<UrlRequestStatisticVO> trafficDay(String startTime,String stopTime,int size){
    	return groupByTime("timed",startTime,stopTime,size);
    }
    
    public List<UrlRequestStatisticVO> sort(List<UrlRequestStatisticVO> list,String field,boolean desc){
    	Comparator<UrlRequestStatisticVO> comparator=null;
    	if("count".equals(field)){
    		comparator=(UrlRequestStatisticVO v1, UrlRequestStatisticVO v2) 
    				->  ElasticsearchUtil.compareCount(v1,v2);
    	}else if("max".equals(field)){
    		comparator=(UrlRequestStatisticVO v1, UrlRequestStatisticVO v2) 
    				->  ElasticsearchUtil.compareMax(v1,v2);
    	}else if("min".equals(field)){
    		comparator=(UrlRequestStatisticVO v1, UrlRequestStatisticVO v2) 
    				->  ElasticsearchUtil.compareMin(v1,v2);
    	}else {//if("avg".equals(field))
    		comparator=(UrlRequestStatisticVO v1, UrlRequestStatisticVO v2) 
    				->  ElasticsearchUtil.compareAvg(v1,v2);
    	}
		if(desc){
			list.sort(comparator.reversed());
		}else{
			list.sort(comparator);
		}
    	return list;
    }
    public static int compareCount(UrlRequestStatisticVO v1, UrlRequestStatisticVO v2) {
    	   return Long.compare(v1.getCount(),v2.getCount());
    }
    public static int compareMax(UrlRequestStatisticVO v1, UrlRequestStatisticVO v2) {
	       return Double.compare(v1.getMax(),v2.getMax());
    }
    public static int compareMin(UrlRequestStatisticVO v1, UrlRequestStatisticVO v2) {
	       return Double.compare(v1.getMin(),v2.getMin());
    }
    public static int compareAvg(UrlRequestStatisticVO v1, UrlRequestStatisticVO v2) {
	       return Double.compare(v1.getAvg(),v2.getAvg());
    }
    public void compareDate(List<UrlRequestStatisticVO> list,boolean desc) {
    	if(desc){
        	Collections.sort(list, Comparator.comparing(UrlRequestStatisticVO::getTime).reversed());
    	}else{
        	Collections.sort(list, Comparator.comparing(UrlRequestStatisticVO::getTime));
    	}
    }
    
    public List<UrlRequestStatisticVO> groupByHistogram(String timeField,String startTime,String stopTime,
    		int intervalSecond) throws ParseException{
    	List<UrlRequestStatisticVO> result=new ArrayList();
    	if(startTime.length()<11){
    		startTime=startTime+" 00:00:00";
    	}
    	if(stopTime.length()<11){
    		stopTime=stopTime+" 00:00:00";
    	}
        Date date = formatter.parse(startTime);
        long minBound=date.getTime();
        date = formatter.parse(stopTime);
        long maxBound=date.getTime();
    	RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery(timeField).from(minBound).to(maxBound);
    	SearchRequestBuilder requestBuilder = client.prepareSearch("gaterequest");
        MinAggregationBuilder min = AggregationBuilders.min("min").field("usedTime");
        MaxAggregationBuilder max = AggregationBuilders.max("max").field("usedTime");
        AvgAggregationBuilder avg = AggregationBuilders.avg("avg").field("usedTime");
        
//        DateHistogramAggregationBuilder dhaBuilder=AggregationBuilders.dateHistogram("agg").field(timeField);    
        HistogramAggregationBuilder habBuilder= AggregationBuilders.histogram("agg").field(timeField);
        habBuilder.interval(intervalSecond*1000);
        AggregationBuilder aggregationBuilder = habBuilder //AggregationBuilders.terms("agg").field(timeField)
        		.subAggregation(AggregationBuilders.terms("add").field("uri")
        		.subAggregation(min).subAggregation(max).subAggregation(avg));//);//.size(size)
        SearchResponse response = requestBuilder.addAggregation(aggregationBuilder).setQuery(rangequerybuilder) //.addSort(sortBuilder)
                .setExplain(true).execute().actionGet();
        InternalHistogram agg = response.getAggregations().get("agg");
        for (InternalHistogram.Bucket bucket : agg.getBuckets()) {
//        	LOGGER.info(bucket.getKey() + ":" + bucket.getDocCount());
            Terms tms = bucket.getAggregations().get("add");
            if(tms!=null){
                for (Terms.Bucket b1 : tms.getBuckets()) {
             	   InternalMin minv=b1.getAggregations().get("min");
             	   InternalMax maxv=b1.getAggregations().get("max");
             	   InternalAvg avgv=b1.getAggregations().get("avg");
             	    LOGGER.info(b1.getKey() + ":" + b1.getDocCount()+",min="+minv.getValueAsString()+
                 		   ",max="+maxv.getValueAsString()+",avg="+avgv.getValueAsString());
                    UrlRequestStatisticVO v=new UrlRequestStatisticVO();
                    date=new Date(Math.round((Double)bucket.getKey()));
                    v.setTime(formatter.format(date));
                    v.setUri((String)b1.getKey());
                    v.setCount(b1.getDocCount());
                    v.setMax(maxv.getValue());
                    v.setMin(minv.getValue());
                    v.setAvg(avgv.getValue());
                    result.add(v);
                }
            }
        }
        return result;
    }
}
