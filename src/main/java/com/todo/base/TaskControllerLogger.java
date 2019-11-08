package com.todo.base;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TaskControllerLogger {
  @Pointcut("execution(* com.todo.base.Dich.*())")
  public void getListingMethod() {
  }

  @Before("getListingMethod())")
  public void before() {
    System.out.println("I'm here! I'm @Before aspect!");
  }

  @After("getListingMethod())")
  public void after() {
    System.out.println("I'm here! I'm @After aspect!");
  }
}
