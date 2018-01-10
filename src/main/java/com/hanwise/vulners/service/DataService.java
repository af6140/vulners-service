package com.hanwise.vulners.service;

import com.hanwise.vulners.repository.CVEOSPackageRepository;
import com.hanwise.vulners.util.DataInfo;
import org.h2.store.fs.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class DataService {

    private static Logger logger = LoggerFactory.getLogger(DataService.class);

    @Autowired
    private Configuration configuration;

    @Autowired
    private CVEOSPackageRepository cveosPackageRepository;

    @CacheEvict(cacheNames="dataInfo", allEntries=true)
    public void downloadData() throws ServiceException{
        FileOutputStream fos = null;
        ReadableByteChannel rbc=null;
        logger.info("Dowloading data.");
        try {
            URL url = new URL(this.configuration.getDataUrl());
            rbc = Channels.newChannel(url.openStream());
            File f = new File(this.configuration.getDataPath());
            if (! FileUtils.exists(this.configuration.getDataPath())) {
                FileUtils.createFile(this.configuration.getDataPath());
            }
            if (! FileUtils.canWrite(this.configuration.getDataPath())){
                throw  new ServiceException("data file cannot be written to "+ this.configuration.getDataPath());
            }
            fos = new FileOutputStream(this.configuration.getDataPath());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            //FileUtil.copyFile(new File(this.configuration.getDataPath()), new File(this.configuration.getJsonSrc()));
        }  catch(FileNotFoundException e) {
            throw new ServiceException("Cannot find data file");
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new ServiceException("src url is not valid: "+ this.configuration.getDataUrl());

        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ServiceException("Cannot unzip/copy file to destination");

        }finally {
            if(fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
            if(rbc!=null) {
                try {
                    rbc.close();
                } catch (IOException e) {
                }
            }
        }
        // now unzip file
        ZipInputStream zipInputStream = null;
        try {

            zipInputStream = new ZipInputStream(new FileInputStream(this.configuration.getDataPath()));
            ZipEntry entry = zipInputStream.getNextEntry();
            if (entry!=null && !entry.isDirectory()) {
                this.extractFile(zipInputStream, this.configuration.getJsonSrc());
            }
            if(entry!=null) zipInputStream.closeEntry();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            throw new ServiceException("Cannot find file: "+this.configuration.getDataPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ServiceException("Cannot unzip file to destination");
        }finally {
            try {zipInputStream.close();} catch (Exception e){}
        }
    }

    @Cacheable("dataInfo")
    public DataInfo getCurrentDataInfo() throws ServiceException{
        DataInfo dataInfo= null;
        try {
            BasicFileAttributes attribute = Files.readAttributes(new File(this.configuration.getJsonSrc()).toPath(), BasicFileAttributes.class);
            dataInfo = new DataInfo();
            dataInfo.setSize(attribute.size());
            dataInfo.setModified(attribute.lastModifiedTime().toString());
            dataInfo.setAffectedOSes(this.cveosPackageRepository.geAvailableOSes());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ServiceException("Failure to access current json source info");
        }
        return dataInfo;
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(filePath));

            byte[] bytesIn = new byte[1024];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
            bos.flush();
        } finally {
            if(bos!=null) {
                try {
                    bos.close();
                }catch(Exception e){}
            }
        }
    }
}
