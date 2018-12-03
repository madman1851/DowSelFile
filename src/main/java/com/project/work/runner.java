package com.project.work;


import com.project.SpringConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = SpringConfigurator.class)
public class runner {

    @Autowired
    selenium selenium;



}
