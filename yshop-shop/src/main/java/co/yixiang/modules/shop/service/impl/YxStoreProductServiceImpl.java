/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.shop.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.yixiang.exception.BadRequestException;
import co.yixiang.modules.shop.domain.YxStoreProduct;
import co.yixiang.common.service.impl.BaseServiceImpl;
import co.yixiang.modules.shop.domain.YxStoreProductAttr;
import co.yixiang.modules.shop.domain.YxStoreProductAttrResult;
import co.yixiang.modules.shop.domain.YxStoreProductAttrValue;
import co.yixiang.modules.shop.service.YxStoreProductAttrResultService;
import co.yixiang.modules.shop.service.YxStoreProductAttrService;
import co.yixiang.modules.shop.service.YxStoreProductAttrValueService;
import co.yixiang.modules.shop.service.dto.*;
import co.yixiang.utils.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import co.yixiang.dozer.service.IGenerator;
import com.github.pagehelper.PageInfo;
import co.yixiang.common.utils.QueryHelpPlus;
import co.yixiang.modules.shop.service.YxStoreProductService;
import co.yixiang.modules.shop.service.mapper.StoreProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
* @author hupeng
* @date 2020-05-12
*/
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "yxStoreProduct")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxStoreProductServiceImpl extends BaseServiceImpl<StoreProductMapper, YxStoreProduct> implements YxStoreProductService {

    private final IGenerator generator;

    private final StoreProductMapper storeProductMapper;

    private final YxStoreProductAttrService yxStoreProductAttrService;

    private final YxStoreProductAttrValueService yxStoreProductAttrValueService;

    private final YxStoreProductAttrResultService yxStoreProductAttrResultService;
    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxStoreProductQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxStoreProduct> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxStoreProductDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxStoreProduct> queryAll(YxStoreProductQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxStoreProduct.class, criteria));
    }


    @Override
    public void download(List<YxStoreProductDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxStoreProductDto yxStoreProduct : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("商户Id(0为总后台管理员创建,不为0的时候是商户后台创建)", yxStoreProduct.getMerId());
            map.put("商品图片", yxStoreProduct.getImage());
            map.put("轮播图", yxStoreProduct.getSliderImage());
            map.put("商品名称", yxStoreProduct.getStoreName());
            map.put("商品简介", yxStoreProduct.getStoreInfo());
            map.put("关键字", yxStoreProduct.getKeyword());
            map.put("产品条码（一维码）", yxStoreProduct.getBarCode());
            map.put("分类id", yxStoreProduct.getCateId());
            map.put("商品价格", yxStoreProduct.getPrice());
            map.put("会员价格", yxStoreProduct.getVipPrice());
            map.put("市场价", yxStoreProduct.getOtPrice());
            map.put("邮费", yxStoreProduct.getPostage());
            map.put("单位名", yxStoreProduct.getUnitName());
            map.put("排序", yxStoreProduct.getSort());
            map.put("销量", yxStoreProduct.getSales());
            map.put("库存", yxStoreProduct.getStock());
            map.put("状态（0：未上架，1：上架）", yxStoreProduct.getIsShow());
            map.put("是否热卖", yxStoreProduct.getIsHot());
            map.put("是否优惠", yxStoreProduct.getIsBenefit());
            map.put("是否精品", yxStoreProduct.getIsBest());
            map.put("是否新品", yxStoreProduct.getIsNew());
            map.put("产品描述", yxStoreProduct.getDescription());
            map.put("添加时间", yxStoreProduct.getAddTime());
            map.put("是否包邮", yxStoreProduct.getIsPostage());
            map.put("是否删除", yxStoreProduct.getIsDel());
            map.put("商户是否代理 0不可代理1可代理", yxStoreProduct.getMerUse());
            map.put("获得积分", yxStoreProduct.getGiveIntegral());
            map.put("成本价", yxStoreProduct.getCost());
            map.put("秒杀状态 0 未开启 1已开启", yxStoreProduct.getIsSeckill());
            map.put("砍价状态 0未开启 1开启", yxStoreProduct.getIsBargain());
            map.put("是否优品推荐", yxStoreProduct.getIsGood());
            map.put("虚拟销量", yxStoreProduct.getFicti());
            map.put("浏览量", yxStoreProduct.getBrowse());
            map.put("产品二维码地址(用户小程序海报)", yxStoreProduct.getCodePath());
            map.put("淘宝京东1688类型", yxStoreProduct.getSoureLink());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void recovery(Integer id) {
        storeProductMapper.updateDel(0,id);
        storeProductMapper.updateOnsale(0,id);
    }

    @Override
    public void onSale(Integer id, int status) {
        if(status == 1){
            status = 0;
        }else{
            status = 1;
        }
        storeProductMapper.updateOnsale(status,id);
    }

    @Override
    public List<ProductFormatDTO> isFormatAttr(Integer id, String jsonStr) {
        if(ObjectUtil.isNull(id)) throw new BadRequestException("产品不存在");

        YxStoreProductDto yxStoreProductDTO = generator.convert(this.getById(id),YxStoreProductDto.class);
        DetailDTO detailDTO = attrFormat(jsonStr);
        List<ProductFormatDTO> newList = new ArrayList<>();
        for (Map<String, Map<String,String>> map : detailDTO.getRes()) {
            ProductFormatDTO productFormatDTO = new ProductFormatDTO();
            productFormatDTO.setDetail(map.get("detail"));
            productFormatDTO.setCost(yxStoreProductDTO.getCost().doubleValue());
            productFormatDTO.setPrice(yxStoreProductDTO.getPrice().doubleValue());
            productFormatDTO.setSales(yxStoreProductDTO.getSales());
            productFormatDTO.setPic(yxStoreProductDTO.getImage());
            productFormatDTO.setCheck(false);
            newList.add(productFormatDTO);
        }
        return newList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createProductAttr(Integer id, String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        //System.out.println(jsonObject);
        List<FromatDetailDTO> attrList = JSON.parseArray(
                jsonObject.get("items").toString(),
                FromatDetailDTO.class);
        List<ProductFormatDTO> valueList = JSON.parseArray(
                jsonObject.get("attrs").toString(),
                ProductFormatDTO.class);


        List<YxStoreProductAttr> attrGroup = new ArrayList<>();
        for (FromatDetailDTO fromatDetailDTO : attrList) {
            YxStoreProductAttr  yxStoreProductAttr = new YxStoreProductAttr();
            yxStoreProductAttr.setProductId(id);
            yxStoreProductAttr.setAttrName(fromatDetailDTO.getValue());
            yxStoreProductAttr.setAttrValues(StrUtil.
                    join(",",fromatDetailDTO.getDetail()));
            attrGroup.add(yxStoreProductAttr);
        }


        List<YxStoreProductAttrValue> valueGroup = new ArrayList<>();
        for (ProductFormatDTO productFormatDTO : valueList) {
            YxStoreProductAttrValue yxStoreProductAttrValue = new YxStoreProductAttrValue();
            yxStoreProductAttrValue.setProductId(id);
            //productFormatDTO.getDetail().values().stream().collect(Collectors.toList());
            List<String> stringList = productFormatDTO.getDetail().values()
                    .stream().collect(Collectors.toList());
            Collections.sort(stringList);
            yxStoreProductAttrValue.setSuk(StrUtil.
                    join(",",stringList));
            yxStoreProductAttrValue.setPrice(BigDecimal.valueOf(productFormatDTO.getPrice()));
            yxStoreProductAttrValue.setCost(BigDecimal.valueOf(productFormatDTO.getCost()));
            yxStoreProductAttrValue.setStock(productFormatDTO.getSales());
            yxStoreProductAttrValue.setUnique(IdUtil.simpleUUID());
            yxStoreProductAttrValue.setImage(productFormatDTO.getPic());

            valueGroup.add(yxStoreProductAttrValue);
        }

        if(attrGroup.isEmpty() || valueGroup.isEmpty()){
            throw new BadRequestException("请设置至少一个属性!");
        }

        //插入之前清空
        clearProductAttr(id,false);


        //保存属性
        yxStoreProductAttrService.saveBatch(attrGroup);

        //保存值
        yxStoreProductAttrValueService.saveBatch(valueGroup);

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("attr",jsonObject.get("items"));
        map.put("value",jsonObject.get("attrs"));

        //保存结果
        setResult(map,id);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setResult(Map<String, Object> map,Integer id) {
        YxStoreProductAttrResult yxStoreProductAttrResult = new YxStoreProductAttrResult();
        yxStoreProductAttrResult.setProductId(id);
        yxStoreProductAttrResult.setResult(JSON.toJSONString(map));
        yxStoreProductAttrResult.setChangeTime(OrderUtil.getSecondTimestampTwo());

        yxStoreProductAttrService.remove(new QueryWrapper<YxStoreProductAttr>().eq("product_id",id));

        yxStoreProductAttrResultService.save(yxStoreProductAttrResult);
    }

    @Override
    public String getStoreProductAttrResult(Integer id) {
        YxStoreProductAttrResult yxStoreProductAttrResult = yxStoreProductAttrResultService
                .getOne(new QueryWrapper<YxStoreProductAttrResult>().eq("product_id",id));
        if(ObjectUtil.isNull(yxStoreProductAttrResult)) return "";
        return  yxStoreProductAttrResult.getResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearProductAttr(Integer id,boolean isActice) {
        if(ObjectUtil.isNull(id)) throw new BadRequestException("产品不存在");

        yxStoreProductAttrService.remove(new QueryWrapper<YxStoreProductAttr>().eq("product_id",id));
        yxStoreProductAttrValueService.remove(new QueryWrapper<YxStoreProductAttrValue>().eq("product_id",id));

        if(isActice){
            yxStoreProductAttrResultService.remove(new QueryWrapper<YxStoreProductAttrResult>().eq("product_id",id));
        }
    }
    /**
     * 组合规则属性算法
     * @param jsonStr
     * @return
     */
    public DetailDTO attrFormat(String jsonStr){
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        List<FromatDetailDTO> fromatDetailDTOList = JSON.parseArray(jsonObject.get("items").toString(),
                FromatDetailDTO.class);
        List<String> data = new ArrayList<>();
        List<Map<String,Map<String,String>>> res =new ArrayList<>();
        if(fromatDetailDTOList.size() > 1){
            for (int i=0; i < fromatDetailDTOList.size() - 1;i++){
                if(i == 0) data = fromatDetailDTOList.get(i).getDetail();
                List<String> tmp = new LinkedList<>();
                for (String v : data) {
                    for (String g : fromatDetailDTOList.get(i+1).getDetail()) {
                        String rep2 = "";
                        if(i == 0){
                            rep2 = fromatDetailDTOList.get(i).getValue() + "_" + v + "-"
                                    + fromatDetailDTOList.get(i+1).getValue() + "_" + g;
                        }else{
                            rep2 = v + "-"
                                    + fromatDetailDTOList.get(i+1).getValue() + "_" + g;
                        }
                        tmp.add(rep2);
                        if(i == fromatDetailDTOList.size() - 2){
                            Map<String,Map<String,String>> rep4 = new LinkedHashMap<>();
                            Map<String,String> reptemp = new LinkedHashMap<>();
                            for (String h : Arrays.asList(rep2.split("-"))) {
                                List<String> rep3 = Arrays.asList(h.split("_"));

                                if(rep3.size() > 1){
                                    reptemp.put(rep3.get(0),rep3.get(1));
                                }else{
                                    reptemp.put(rep3.get(0),"");
                                }
                            }
                            rep4.put("detail",reptemp);
                            res.add(rep4);
                        }
                    }
                }
                //System.out.println("tmp:"+tmp);
                if(!tmp.isEmpty()){
                    data = tmp;
                }
            }
        }else{
            List<String> dataArr = new ArrayList<>();

            for (FromatDetailDTO fromatDetailDTO : fromatDetailDTOList) {

                for (String str : fromatDetailDTO.getDetail()) {
                    Map<String,Map<String,String>> map2 = new LinkedHashMap<>();
                    //List<Map<String,String>> list1 = new ArrayList<>();
                    dataArr.add(fromatDetailDTO.getValue()+"_"+str);
                    Map<String,String> map1 = new LinkedHashMap<>();
                    map1.put(fromatDetailDTO.getValue(),str);
                    //list1.add(map1);
                    map2.put("detail",map1);
                    res.add(map2);
                }
            }
            String s = StrUtil.join("-",dataArr);
            data.add(s);
        }
        DetailDTO detailDTO = new DetailDTO();
        detailDTO.setData(data);
        detailDTO.setRes(res);
        return detailDTO;
    }
}
