package com.hanwise.vulners.controller;


import com.hanwise.vulners.service.Configuration;
import com.hanwise.vulners.service.DBService;
import com.hanwise.vulners.service.DataService;
import com.hanwise.vulners.service.ServiceException;
import com.hanwise.vulners.util.DataInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/cve-data")
public class DataController {
    private static Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private DataService dataService;

    @Autowired
    private DBService dbService;

    @Autowired
    private Configuration configuration;

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<DataInfo>  getDataInfo(@RequestParam(value="refresh", required = false, defaultValue="false") String refresh ) throws ServiceException{
        if(refresh!=null && "true".equalsIgnoreCase(refresh)) {
            this.dataService.downloadData();
            this.dbService.init();
        }
        DataInfo dataInfo =  this.dataService.getCurrentDataInfo();
        return new ResponseEntity<>(dataInfo, this.configuration.getDefaultJsonResponseHttpHeaders(), HttpStatus.OK);
    }


}
