package com.hk;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{

    //Spring Resource资源测试
    private static void firstResource() {
        Resource resource = new FileSystemResource(new File("D:\\Java\\openjdk\\README"));
        System.out.println(resource.getDescription());
        System.out.println(resource.getFilename());
        System.out.println(resource.exists());
        System.out.println(resource.isFile());
    }



    private static void firstContainer() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        Service service = ctx.getBean(Service.class);
        service.run();
    }



    private static void singleBean(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        Service service = (Service) ctx.getBean("sin");
        service.printId();
        service = (Service) ctx.getBean("sin2");
        service.printId();
    }

    //prototype scope测试
    private static void instantBean() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        Service service = (Service) ctx.getBean("pro");
        service.printId();
        service = (Service) ctx.getBean("pro");
        service.printId();

    }


    public static void main( String[] args )
    {
//        firstResource();
        singleBean();
//        instantBean();
    }
}
