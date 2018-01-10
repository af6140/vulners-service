package com.hanwise.vulners.service;

import com.hanwise.vulners.entity.CVEEntry;
import com.hanwise.vulners.entity.CVESource;
import com.hanwise.vulners.repository.CVESourceRepository;
import com.hanwise.vulners.util.JSONSrcParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class DBService {

    private static Logger logger = LoggerFactory.getLogger(DBService.class);
    @Autowired
    private DBInitializer dbInitializer;

    @Autowired
    private Configuration configuration;

    @Autowired
    private JSONSrcParser jsonSrcParser;

    @Autowired
    private CVESourceRepository cveSourceRepository;

    public void init() {
        logger.debug("Initialize database");
        String jsonSrc = this.configuration.getJsonSrc();
        if(logger.isDebugEnabled()){
            logger.debug("Parsing json definitions");
        }
        List<CVEEntry> entries= this.jsonSrcParser.parseFile(jsonSrc);
        List<CVESource> cveSources=new LinkedList<>();
        for(CVEEntry entry: entries){
            CVESource source = entry.getSource();
            cveSources.add(source);
        }
        if(logger.isDebugEnabled()) logger.debug("Loading database");
            this.dbInitializer.loadDataBase(cveSources);
    }

    @PostConstruct
    public void loadDatabase() {
        this.init();

    }
}
