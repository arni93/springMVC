package pl.spring.demo.web.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Groups all mvc controllers test and executes everyone
 * 
 * @author AWOZNICA
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ BookControllerTest.class, HomeControllerTest.class, LoginControllerTest.class })
public class AllControllerTests {

}