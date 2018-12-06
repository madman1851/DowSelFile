package com.project.work;


import com.project.SpringConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(classes = SpringConfigurator.class)
@Service
public class runner {

    @Autowired
    selenium Selenium;

    public static void main(String[] args)throws Exception{
        runner r = new runner();
        r.start(args);
    }

    private void start(String[] args) throws Exception{
        try{
            Selenium.wdRun();
        }catch(Exception e){
            e.printStackTrace();
        }catch(Throwable t){
            t.printStackTrace();
        }
    }


}
