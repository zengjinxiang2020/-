/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co

 */
package co.yixiang.gen.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ZipUtil;
import co.yixiang.gen.domain.ColumnConfig;
import co.yixiang.gen.service.mapper.ColumnInfoMapper;
import co.yixiang.gen.utils.GenUtil;
import co.yixiang.common.service.impl.BaseServiceImpl;
import co.yixiang.gen.domain.GenConfig;
import co.yixiang.gen.domain.vo.TableInfo;
import co.yixiang.exception.BadRequestException;
import co.yixiang.gen.service.GeneratorService;
import co.yixiang.utils.FileUtil;
import co.yixiang.utils.PageUtil;
import co.yixiang.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2019-01-02
 */
@Service
@SuppressWarnings({"unchecked","all"})
public class GeneratorServiceImpl extends BaseServiceImpl<ColumnInfoMapper, ColumnConfig> implements GeneratorService {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Object getTables() {
        // 使用预编译防止sql注入
        String sql = "select table_name ,create_time , engine, table_collation, table_comment from information_schema.tables " +
                "where table_schema = (select database()) " +
                "order by create_time desc";
        Query query = em.createNativeQuery(sql);
        return query.getResultList();
    }

    @Override
    public Object getTables(String name, Integer page, Integer size) {
        IPage<TableInfo> pages = null;
        Page<TableInfo> pageModel = new Page<>(page, size);
        pages = baseMapper.selectTables(pageModel,name);
        Integer totalElements = 0;
        return PageUtil.toPage(pages.getRecords(),pages.getTotal());
    }

    @Override
    public List<ColumnConfig> getColumns(String tableName) {
        List<ColumnConfig> columnInfos = this.list(new QueryWrapper<ColumnConfig>()
                .eq("table_name",tableName).orderByAsc("id"));
        if(CollectionUtil.isNotEmpty(columnInfos)){
            return columnInfos;
        } else {
            columnInfos = query(tableName);
            this.saveBatch(columnInfos);
            return columnInfos;
        }
    }

    @Override
    public List<ColumnConfig> query(String tableName){
        // 使用预编译防止sql注入
/*        String sql = "select column_name, is_nullable, data_type, column_comment, column_key, extra from information_schema.columns " +
                "where table_name = ? and table_schema = (select database()) order by ordinal_position";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1,tableName);
        List result = query.getResultList();
        List<ColumnConfig> columnInfos = new ArrayList<>();
        for (Object obj : result) {
            Object[] arr = (Object[]) obj;
            columnInfos.add(
                    new ColumnConfig(
                            tableName,
                            arr[0].toString(),
                            "NO".equals(arr[1]),
                            arr[2].toString(),
                            ObjectUtil.isNotNull(arr[3]) ? arr[3].toString() : null,
                            ObjectUtil.isNotNull(arr[4]) ? arr[4].toString() : null,
                            ObjectUtil.isNotNull(arr[5]) ? arr[5].toString() : null)
            );
        }*/
        List<ColumnConfig> columnInfos = new ArrayList<>();
        List result = baseMapper.queryByTableName(tableName);
        for (Object obj : result) {
            Object[] arr = (Object[]) obj;
            columnInfos.add(
                    new ColumnConfig(
                            tableName,
                            arr[0].toString(),
                            "NO".equals(arr[1]),
                            arr[2].toString(),
                            ObjectUtil.isNotNull(arr[3]) ? arr[3].toString() : null,
                            ObjectUtil.isNotNull(arr[4]) ? arr[4].toString() : null,
                            ObjectUtil.isNotNull(arr[5]) ? arr[5].toString() : null)
            );
        }
        return columnInfos;
    }

    @Override
    public void sync(List<ColumnConfig> columnInfos, List<ColumnConfig> columnInfoList) {
        // 第一种情况，数据库类字段改变或者新增字段
        for (ColumnConfig columnInfo : columnInfoList) {
            // 根据字段名称查找
            List<ColumnConfig> columns = new ArrayList<ColumnConfig>(columnInfos.stream().filter(c-> c.getColumnName().equals(columnInfo.getColumnName())).collect(Collectors.toList()));
            // 如果能找到，就修改部分可能被字段
            if(CollectionUtil.isNotEmpty(columns)){
                ColumnConfig column = columns.get(0);
                column.setColumnType(columnInfo.getColumnType());
                column.setExtra(columnInfo.getExtra());
                column.setKeyType(columnInfo.getKeyType());
                if(StringUtils.isBlank(column.getRemark())){
                    column.setRemark(columnInfo.getRemark());
                }
                this.saveOrUpdate(column);
            } else {
                // 如果找不到，则保存新字段信息
                this.save(columnInfo);
            }
        }
        // 第二种情况，数据库字段删除了
        for (ColumnConfig columnInfo : columnInfos) {
            // 根据字段名称查找
            List<ColumnConfig> columns = new ArrayList<ColumnConfig>(columnInfoList.stream().filter(c-> c.getColumnName().equals(columnInfo.getColumnName())).collect(Collectors.toList()));
            // 如果找不到，就代表字段被删除了，则需要删除该字段
            if(CollectionUtil.isEmpty(columns)){
                this.removeById(columnInfo.getId());
            }
        }
    }

    @Override
    public void save(List<ColumnConfig> columnInfos) {
        this.saveOrUpdateBatch(columnInfos);
    }

    @Override
    public void generator(GenConfig genConfig, List<ColumnConfig> columns) {
        if(genConfig.getId() == null){
            throw new BadRequestException("请先配置生成器");
        }
        try {
            GenUtil.generatorCode(columns, genConfig);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException("生成失败，请手动处理已生成的文件");
        }
    }

    @Override
    public ResponseEntity<Object> preview(GenConfig genConfig, List<ColumnConfig> columns) {
        if(genConfig.getId() == null){
            throw new BadRequestException("请先配置生成器");
        }
        List<Map<String,Object>> genList =  GenUtil.preview(columns, genConfig);
        return new ResponseEntity<>(genList, HttpStatus.OK);
    }

    @Override
    public void download(GenConfig genConfig, List<ColumnConfig> columns, HttpServletRequest request, HttpServletResponse response) {
        if(genConfig.getId() == null){
            throw new BadRequestException("请先配置生成器");
        }
        try {
            File file = new File(GenUtil.download(columns, genConfig));
            String zipPath = file.getPath()  + ".zip";
            ZipUtil.zip(file.getPath(), zipPath);
            FileUtil.downloadFile(request, response, new File(zipPath), true);
        } catch (IOException e) {
            throw new BadRequestException("打包失败");
        }
    }
}
