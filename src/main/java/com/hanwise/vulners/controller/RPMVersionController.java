package com.hanwise.vulners.controller;

import com.hanwise.vulners.service.RPMVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/version-cmp")
public class RPMVersionController {

    @Autowired
    private RPMVersionService versionService;

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<Integer> compareVersion(@RequestParam(value = "v1") String v1,@RequestParam(value = "v2") String v2 ) {
        int result = versionService.compareVersion(v1, v2);
        if(result >0) result =1;
        if(result <0) result = -1;
        return new ResponseEntity<Integer>(Integer.valueOf(result), HttpStatus.OK);
    }
}
